package com.jfinal.weixin.test;


import org.junit.Ignore;
import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;

public class ShareTestCase extends TestCase<WeixinConfig> {
	@Ignore
	public void getCommitList(){
		String url = "/share/commentList";
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}
	
	@Test
	public void senMsg(){
		String url = "/share/sendMsg";
		this.setParameter("touser", "15991890112");
		this.setParameter("username", "郝利鹏");
		this.setParameter("content", "提示输入登记手机号/邮箱:企业小助手向则用户发消息提示用户输入在企业号下登记的手机号/邮箱？");
		
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}
}
