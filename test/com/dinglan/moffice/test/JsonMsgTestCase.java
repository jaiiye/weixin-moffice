package com.dinglan.moffice.test;

import com.dinglan.moffice.WeixinConfig;
import com.dinglan.weixin.api.ApiResult;
import com.dinglan.weixin.api.MessageApi;
import com.dinglan.weixin.msg.JsonTextMsg;

import org.junit.Test;

public class JsonMsgTestCase extends BaseTestCase<WeixinConfig> {
	@Test
	public void getJsonTextMsg(){
		
		JsonTextMsg msg=new JsonTextMsg(); 
		msg.touser="15991890112";
		msg.content="123";
		System.out.println(msg.toString());
	}
	
	@Test
	public void sendJsonTextMsg(){
		JsonTextMsg msg=new JsonTextMsg(); 
		msg.touser="15991890112";
		msg.content="123";
		msg.msgtype="text";
		msg.safe="1";
		msg.agentid="10";
		ApiResult ret=MessageApi.sendMsg(msg);
		System.out.println(ret.getErrorMsg());
	}
}
