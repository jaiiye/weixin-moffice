package com.dinglan.moffice;

import com.dinglan.weixin.api.ApiConfig;
import com.dinglan.weixin.api.ApiResult;
import com.dinglan.weixin.api.MenuApi;
import com.dinglan.weixin.api.UserApi;
import com.jfinal.core.Controller;

public class ApiController extends Controller {
	
	public void index() {
		this.setAttr("AppId", ApiConfig.getAppId());
		this.setAttr("AppSecret", ApiConfig.getAppSecret());
		this.setAttr("EncodingAESKey", ApiConfig.getEncodingAESKey());
		this.setAttr("Token", ApiConfig.getToken());
		render("/share/test.html");
	}
	/**
	 * 获取公众号菜单
	 */
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu(ApiConfig.getAgentId());
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}
	
	/**
	 * 获取公众号菜单
	 */
	public void createMenu() {
		String jsonStr=this.getPara("menu");
		ApiResult apiResult = MenuApi.createMenu(ApiConfig.getAgentId(),jsonStr);
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}
	
	/**
	 * 获取公众号关注用户
	 */
	public void getFollowers() {
		ApiResult apiResult = UserApi.getFollows(0);
		renderText(apiResult.getJson());
	}
}




