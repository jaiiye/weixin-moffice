/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.api;

import com.jfinal.weixin.sdk.kit.HttpKit;
import com.jfinal.weixin.sdk.msg.OutMsg;
import com.jfinal.weixin.sdk.msg.OutMsgXmlBuilder;

/**
 * message api
 */
public class MessageApi {
	private static String msgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN=";
	
	/**
	 * 发送消息
	 */
	public static ApiResult sendMsg(OutMsg outMsg) {
		String outMsgXml = OutMsgXmlBuilder.build(outMsg);
		String data=outMsgXml;
		String jsonResult = HttpKit.post(msgUrl + AccessTokenApi.getAccessToken().getAccessToken(),data);
		return new ApiResult(jsonResult);
	}
}