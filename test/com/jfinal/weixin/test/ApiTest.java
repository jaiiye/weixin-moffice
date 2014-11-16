package com.jfinal.weixin.test;


import java.net.URL;

import org.junit.Test;

import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;

public class ApiTest {
	@Test
	public void getPath(){
		ClassLoader cll = Thread.currentThread().getContextClassLoader();
		URL url=cll.getResource("a_little_config.txt");		
		String path = url.getPath();
		System.out.println(path);
	}
	@Test
	public void getFollers(){
		ApiResult result= UserApi.getFollows();
		System.out.println(result.getJson());
	}
}
