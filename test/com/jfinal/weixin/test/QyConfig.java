/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.test;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.weixin.demo.ApiController;
import com.jfinal.weixin.demo.QyController;
import com.jfinal.weixin.sdk.api.ApiConfig;

public class QyConfig extends JFinalConfig {
	
	public void configConstant(Constants me) {

		// 配置微信 API 相关常量
		ApiConfig.setDevMode(true);
		ApiConfig.setUrl("http://www.dinglantech.com.cn/webapp/qy/");
		ApiConfig.setToken("oxxI");
		ApiConfig.setAppId("wxb21adacab9c87404");
		ApiConfig.setAppSecret("Hvix3QfnYy690tvDdsJN7NkoEhj485X98H9Gpn8grU7VUxLOsFaAOGPrx9gTPF0s");
		ApiConfig.setEncodingAESKey("vuSWZYdBrwWqeFEHZQVM6kFngXaz6AhRXuTwO1b1MCH");
	}
	
	public void configRoute(Routes me) {
		me.add("/qy", QyController.class);
		me.add("/api", ApiController.class);
	}
	
	public void configPlugin(Plugins me) {
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
	}
	
	public void configInterceptor(Interceptors me) {
		
	}
	
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
		
	}
}
