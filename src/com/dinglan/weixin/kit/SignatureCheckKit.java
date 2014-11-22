/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.dinglan.weixin.kit;

import java.util.Arrays;

import com.dinglan.weixin.api.ApiConfig;
import com.jfinal.core.Controller;

/**
 * 测试用的账号：
 * appID = wx9803d1188fa5fbda
 * appsecret = db859c968763c582794e7c3d003c3d87
 * url = http://www.jfinal.com/weixin
 * token = __my__token__
 */
public class SignatureCheckKit {
	
	public static final SignatureCheckKit me = new SignatureCheckKit();
	
	/*
	 * 生成签名
	 */
	private String genSignature(String timestamp, String nonce,String msg_encrypt) {
		
		String TOKEN = ApiConfig.getToken();
		String array[] = {TOKEN, timestamp, nonce,msg_encrypt};
		Arrays.sort(array);
	    StringBuilder sb = new StringBuilder();
	    for(String a : array) {
	      sb.append(a);
	    }
	    String tempStr = EncryptionKit.sha1Encrypt(sb.toString());
	    
		/*
		String tempStr = new StringBuilder().append(array[0] + array[1] + array[2] + array[3]).toString();
		tempStr = EncryptionKit.sha1Encrypt(tempStr);
		*/
		
		return tempStr;
	}
	
	public boolean checkSignature(String signature, String timestamp, String nonce,String msg_encrypt) {		
		String tempStr =this.genSignature(timestamp,nonce,msg_encrypt);
		return tempStr.equalsIgnoreCase(signature);
	}
	
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		String TOKEN = ApiConfig.getToken();
		String array[] = {TOKEN, timestamp, nonce};
		Arrays.sort(array);
		String tempStr = new StringBuilder().append(array[0] + array[1] + array[2]).toString();
		tempStr = EncryptionKit.sha1Encrypt(tempStr);
		return tempStr.equalsIgnoreCase(signature);
	}
	public boolean checkSignature(Controller c) {
        return checkSignature(c.getPara("signature"), c.getPara("timestamp"), c.getPara("nonce"));
	}
}



