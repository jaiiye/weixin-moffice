package com.jfinal.weixin.test;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.msg.JsonMsg;
import org.junit.Test;

public class JsonMsgTestCase extends TestCase<WeixinConfig> {
	@Test
	public void getJsonMsg(){
		JsonMsg msg=new JsonMsg(); 
		msg.touser="15991890112";
		System.out.println(msg.toString());
	}
}
