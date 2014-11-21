package com.jfinal.weixin.test;

import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;

public class DbTestCase extends BaseTestCase<WeixinConfig> {
	@Test
	public void getShareList() {
		String url = "/share/?id=1";
		String resp = use(url).post("").invoke();
		System.out.println(resp);
	}
}
