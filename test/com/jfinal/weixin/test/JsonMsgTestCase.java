package com.jfinal.weixin.test;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MessageApi;
import com.jfinal.weixin.sdk.msg.JsonMsg;
import com.jfinal.weixin.sdk.msg.JsonTextMsg;

import org.junit.Test;

public class JsonMsgTestCase extends BaseTestCase<WeixinConfig> {
	@Test
	public void getJsonMsg(){
		JsonMsg msg=new JsonMsg(); 
		msg.touser="15991890112";
		System.out.println(msg.toString());
	}
	
	@Test
	public void getJsonTextMsg(){
		JsonTextMsg msg=new JsonTextMsg(); 
		msg.touser="15991890112";
		msg.content="123";
		System.out.println(msg.toString());
	}
	
	@Test
	public void sendJsonTextMsg(){
		for(int i=0;i<100;i++){
		JsonTextMsg msg=new JsonTextMsg(); 
			msg.touser="15991890112";
			msg.content="123"+i;
			msg.msgtype="text";
			msg.safe="1";
			msg.agentid="10";
			ApiResult ret=MessageApi.sendMsg(msg);
			System.out.println(ret.getErrorMsg());
		}
	}
}
