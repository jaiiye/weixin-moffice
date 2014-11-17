/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Logger;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.kit.SignatureCheckKit;
import com.jfinal.weixin.sdk.kit.WxCryptUtil;

/**
 * 微信拦截器
 * 1：响应开发者中心服务器配置 URL 与 Token 请求
 * 2：签名检测
 * 注意： WeixinController 的继承类如果覆盖了 index 方法，则需要对该 index 方法声明该拦截器
 * 		因为子类覆盖父类方法会使父类方法配置的拦截器失效，从而失去本拦截器的功能
 */
public class CorpInterceptor implements Interceptor {
	
	private static final Logger log =  Logger.getLogger(CorpInterceptor.class);
	
	public void intercept(ActionInvocation ai) {
		// 如果是服务器配置请求，则配置服务器并返回
		Controller controller = ai.getController();
		HttpServletRequest request= controller.getRequest();
		
		String url=request.getRequestURL().toString()+"?" + request.getQueryString();
		log.info("request url:"+url);
		
		
		if (isConfigServerRequest(controller)) {
			configServer(controller);
			return ;
		}
		
		// 签名检测
		if (checkSignature(controller)) {
			ai.invoke();
		}
	}
	
	/**
	 * 检测微信加密签名
	 * msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
	 */
	private boolean checkSignature(Controller controller) {
		String msg_signature = controller.getPara("msg_signature");
		String timestamp = controller.getPara("timestamp");
		String nonce = controller.getPara("nonce");

		if (StrKit.isBlank(msg_signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
			controller.renderText("check signature failure. (msg_sigature|timestamp|none) not exist ");
			return false;
		}
		
		return true;
	
	}
	
	/**
	 * 是否为开发者中心保存服务器配置的请求
	 */
	private boolean isConfigServerRequest(Controller controller) {
		return StrKit.notBlank(controller.getPara("echostr"));
	}
	
	/**
	 * 配置开发者中心微信服务器所需的 url 与 token
	 * @return true 为config server 请求，false 正式消息交互请求
	 */
	public void configServer(Controller c) {
		String msg_signature = c.getPara("msg_signature");
        String timestamp = c.getPara("timestamp");
        String nonce = c.getPara("nonce");
        String echostr = c.getPara("echostr");
        
        //非加密验证
        boolean isOk = SignatureCheckKit.me.checkSignature(msg_signature, timestamp, nonce,echostr);
        
		if (isOk){
			WxCryptUtil pc = getWxCryptUtil();
			echostr= pc.decrypt(echostr);
			c.renderText(echostr);
		}
		else
			log.error("验证失败：configServer!");
	}
	
	//内部缓存
	private WxCryptUtil cryptUtil=null;
	private WxCryptUtil getWxCryptUtil(){
		if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
}




