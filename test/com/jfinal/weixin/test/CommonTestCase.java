package com.jfinal.weixin.test;

import org.junit.Ignore;
import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.test.util.MockServletContext;

public class CommonTestCase extends TestCase<WeixinConfig> {
	
	@Test
	public void getUserByCode(){
		String code="jvu__HxiuuuHeDA04xWpGdYCTmUq2kvV827iKWWnZXD3SvIxTkVarNsXgbIra3YisecAZjLRWVD-1YmKKakn_A";
		ApiResult ret=UserApi.getUserByCode("10", code);
		System.out.println(ret.getJson()+":"+ret.get("UserId"));
	}
	@SuppressWarnings("rawtypes")
	@Ignore
	public void getPath(){
		Class cls =MockServletContext.class;
		System.out.println(cls.getResource("").getFile());
		System.out.println(cls.getResource(".").getFile());
		System.out.println(cls.getResource("/").getFile());
	}
}
