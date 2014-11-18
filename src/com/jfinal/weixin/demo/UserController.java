package com.jfinal.weixin.demo;

import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;

public class UserController extends Controller{
	/*企业号中的用户*/
	public void index() {
		ApiResult apiResult = UserApi.getFollows();
		setAttr("users", apiResult);
		render("/api/index.html");
	}
	
	/*重要联系人*/
	public void major() {
		render("/api/index.html");
	}
}
