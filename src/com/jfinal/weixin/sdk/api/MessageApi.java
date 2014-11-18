/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.api;

import com.jfinal.weixin.sdk.kit.HttpKit;
import com.jfinal.weixin.sdk.msg.JsonMsg;

/**
 * message api
 */
public class MessageApi {
	private static String msgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	
	/**
	 * 发送消息
	 */
	public static ApiResult sendMsg(JsonMsg outMsg) {
		String data=outMsg.toString();
		String jsonResult = HttpKit.post(msgUrl + AccessTokenApi.getAccessToken().getAccessToken(),data);
		return new ApiResult(jsonResult);
	}
}