/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.dinglan.moffice;

import com.dinglan.weixin.api.ApiConfig;
import com.dinglan.weixin.kit.HttpKit;
import com.dinglan.weixin.kit.WxCryptUtil;
import com.dinglan.weixin.msg.InFollowEvent;
import com.dinglan.weixin.msg.InImageMsg;
import com.dinglan.weixin.msg.InLinkMsg;
import com.dinglan.weixin.msg.InLocationEvent;
import com.dinglan.weixin.msg.InLocationMsg;
import com.dinglan.weixin.msg.InMenuEvent;
import com.dinglan.weixin.msg.InMsg;
import com.dinglan.weixin.msg.InMsgParaser;
import com.dinglan.weixin.msg.InQrCodeEvent;
import com.dinglan.weixin.msg.InSpeechRecognitionResults;
import com.dinglan.weixin.msg.InTextMsg;
import com.dinglan.weixin.msg.InVideoMsg;
import com.dinglan.weixin.msg.InVoiceMsg;
import com.dinglan.weixin.msg.OutMsg;
import com.dinglan.weixin.msg.OutMsgXmlBuilder;
import com.dinglan.weixin.msg.OutTextMsg;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

/**
 * 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法
 */
public abstract class CorpController extends Controller {
	
	private static final Logger log =  Logger.getLogger(CorpController.class);
	private String inMsgXml = null;		// 本次请求 xml数据
	private InMsg inMsg = null;			// 本次请求 xml 解析后的 InMsg 对象
	private WxCryptUtil cryptUtil=null;
	
	/**
	 * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
	 */
	@Before(CorpInterceptor.class)
	public void index() {
		// 开发模式输出微信服务发送过来的  xml 消息
		if (ApiConfig.isDevMode()) {
			log.info("接收消息:"+getInMsgXml());
		}
		
		// 解析消息并根据消息类型分发到相应的处理方法
		InMsg msg = getInMsg();
		if (msg instanceof InTextMsg)
			processInTextMsg((InTextMsg)msg);
		else if (msg instanceof InImageMsg)
			processInImageMsg((InImageMsg)msg);
		else if (msg instanceof InVoiceMsg)
			processInVoiceMsg((InVoiceMsg)msg);
		else if (msg instanceof InVideoMsg)
			processInVideoMsg((InVideoMsg)msg);
		else if (msg instanceof InLocationMsg)
			processInLocationMsg((InLocationMsg)msg);
		else if (msg instanceof InLinkMsg)
			processInLinkMsg((InLinkMsg)msg);
		else if (msg instanceof InFollowEvent)
			processInFollowEvent((InFollowEvent)msg);
		else if (msg instanceof InQrCodeEvent)
			processInQrCodeEvent((InQrCodeEvent)msg);
		else if (msg instanceof InLocationEvent)
			processInLocationEvent((InLocationEvent)msg);
		else if (msg instanceof InMenuEvent)
			processInMenuEvent((InMenuEvent)msg);
		else if (msg instanceof InSpeechRecognitionResults)
			processInSpeechRecognitionResults((InSpeechRecognitionResults)msg);
		else
			log.error("未能识别的消息类型。 消息 xml 内容为：\n" + getInMsgXml());
		
		ShareKit.process(msg);
	}
	
	/**
	 * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
	 */
	public void render(OutMsg outMsg) {
		String outMsgXml = OutMsgXmlBuilder.build(outMsg);
		// 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
		if (ApiConfig.isDevMode()) {
			log.info("发送消息:"+outMsgXml);
			log.info("--------------------------------------------------------------------------------\n");
		}
		
		String destMsgXml=outMsgXml;
		WxCryptUtil pc = getWxCryptUtil();
		destMsgXml= pc.encrypt(outMsgXml);
		log.info("加密消息:"+destMsgXml);
		log.info("--------------------------------------------------------------------------------\n");
		
		renderText(destMsgXml, "text/xml");
	}
	
	public void renderOutTextMsg(String content) {
		OutTextMsg outMsg= new OutTextMsg(getInMsg());
		outMsg.setContent(content);
		render(outMsg);
	}
	
	@Before(NotAction.class)
	public String getInMsgXml() {
		if (inMsgXml == null)
			inMsgXml = HttpKit.readIncommingRequestData(getRequest());
		return inMsgXml;
	}
	
	@Before(NotAction.class)
	public InMsg getInMsg() {
		String xml=getInMsgXml();
		
		WxCryptUtil pc = getWxCryptUtil();
		String msgSignature = getPara("msg_signature");
		String timestamp = getPara("timestamp");
		String nonce = getPara("nonce");
		xml= pc.decrypt(msgSignature, timestamp, nonce, xml);
		log.info("脱密消息:"+xml);
		log.info("--------------------------------------------------------------------------------\n");
		
		inMsg = InMsgParaser.parse(xml); 
		return inMsg;
	}	
	private WxCryptUtil getWxCryptUtil(){
		if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
	
	// 处理接收到的文本消息
	protected abstract void processInTextMsg(InTextMsg inTextMsg);
	
	// 处理接收到的图片消息
	protected abstract void processInImageMsg(InImageMsg inImageMsg);
	
	// 处理接收到的语音消息
	protected abstract void processInVoiceMsg(InVoiceMsg inVoiceMsg);
	
	// 处理接收到的视频消息
	protected abstract void processInVideoMsg(InVideoMsg inVideoMsg);
	
	// 处理接收到的地址位置消息
	protected abstract void processInLocationMsg(InLocationMsg inLocationMsg);

	// 处理接收到的链接消息
	protected abstract void processInLinkMsg(InLinkMsg inLinkMsg);
	
	// 处理接收到的关注/取消关注事件
	protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);
	
	// 处理接收到的扫描带参数二维码事件
	protected abstract void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent);
	
	// 处理接收到的上报地理位置事件
	protected abstract void processInLocationEvent(InLocationEvent inLocationEvent);
	
	// 处理接收到的自定义菜单事件
	protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);
	
	// 处理接收到的语音识别结果
	protected abstract void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults);
}













