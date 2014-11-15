package com.jfinal.weixin.demo;

import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.api.ApiConfig;

public class TestController extends Controller {
	public void index(){
		this.setAttr("AppId", ApiConfig.getAppId());
		this.setAttr("AppSecret", ApiConfig.getAppSecret());
		this.setAttr("EncodingAESKey", ApiConfig.getEncodingAESKey());
		this.setAttr("Token", ApiConfig.getToken());
		this.render("/test/index.html");
	}
}
