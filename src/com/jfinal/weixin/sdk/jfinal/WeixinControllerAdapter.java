/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.jfinal;

import com.jfinal.weixin.sdk.msg.InFollowEvent;
import com.jfinal.weixin.sdk.msg.InImageMsg;
import com.jfinal.weixin.sdk.msg.InLinkMsg;
import com.jfinal.weixin.sdk.msg.InLocationEvent;
import com.jfinal.weixin.sdk.msg.InLocationMsg;
import com.jfinal.weixin.sdk.msg.InMenuEvent;
import com.jfinal.weixin.sdk.msg.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.InTextMsg;
import com.jfinal.weixin.sdk.msg.InVideoMsg;
import com.jfinal.weixin.sdk.msg.InVoiceMsg;

/**
 * WeixinControllerAdapter
 */
public abstract class WeixinControllerAdapter extends WeixinController {
	
	protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);
	
	protected abstract void processInTextMsg(InTextMsg inTextMsg);
	
	protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);
	
	protected void processInImageMsg(InImageMsg inImageMsg) {
		
	}
	
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		
	}
	
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		
	}
	
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		
	}
	
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		
	}
	
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		
	}
	
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		
	}
	
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		
	}
}


