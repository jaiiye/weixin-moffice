/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import com.jfinal.weixin.model.Share;
import com.jfinal.weixin.sdk.msg.InImageMsg;
import com.jfinal.weixin.sdk.msg.InLocationEvent;
import com.jfinal.weixin.sdk.msg.InMsg;
import com.jfinal.weixin.sdk.msg.InTextMsg;
import com.jfinal.weixin.sdk.msg.InVideoMsg;
import com.jfinal.weixin.sdk.msg.InVoiceMsg;

/**
 * ShareAction
 */
public class ShareAction {
	public static void intercept(InMsg inmsg) {
		Share share = new Share();
		initMsg(share, inmsg);
		if (inmsg instanceof InTextMsg) {
			InTextMsg msg = (InTextMsg) inmsg;
			share.set("content", msg.getContent());
			share.save();
		} else if (inmsg instanceof InImageMsg) {
			InImageMsg msg = (InImageMsg) inmsg;
			share.set("mediaId", msg.getMediaId());
			share.set("msgId", msg.getMsgId());
			share.set("picUrl", msg.getPicUrl());
			share.save();
		}else if (inmsg instanceof InLocationEvent) {
			InLocationEvent msg = (InLocationEvent) inmsg;
			share.set("longitude", msg.getLongitude());
			share.set("latitude", msg.getLatitude());
			share.set("precision", msg.getPrecision());
			share.save();
		}
	}

	private static void initMsg(Share share, InMsg msg) {
		share.set("toUserName", msg.getToUserName());
		share.set("fromUserName", msg.getFromUserName());
		share.set("createTime", msg.getCreateTime());
		share.set("msgType", msg.getMsgType());
	}
}
