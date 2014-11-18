package com.jfinal.weixin.test;


import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;

public class ShareTestCase extends TestCase<WeixinConfig> {
	@Test
	public void getShares(){
		String url = "/share";
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}
}
