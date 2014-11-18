/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.demo;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.weixin.model.*;

public class WeixinConfig extends JFinalConfig {
	
	public void configConstant(Constants me) {
		super.loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		// 配置微信 API 相关常量
		ApiConfig.setDevMode(me.getDevMode());
		ApiConfig.setUrl(getProperty("url"));
		ApiConfig.setToken(getProperty("token"));
		ApiConfig.setAppId(getProperty("appId"));
		ApiConfig.setAppSecret(getProperty("appSecret"));
		ApiConfig.setEncodingAESKey(getProperty("encodingAESKey"));
	}
	
	public void configRoute(Routes me) {
		me.add("/qy", QyController.class);
		me.add("/api", ApiController.class);
		me.add("/user", UserController.class);
		me.add("/share", ShareController.class);
		me.add("/test", TestController.class);
	}
	
	public void configPlugin(Plugins me) {
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
		
		// 配置C3p0数据库连接池插件
		System.out.println(getProperty("url"));
		System.out.println(getProperty("jdbcUrl"));
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("userName"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("wx_share", Share.class);	// 映射blog 表到 Blog模型
	}
	
	public void configInterceptor(Interceptors me) {
		
	}
	
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
		
	}
}
