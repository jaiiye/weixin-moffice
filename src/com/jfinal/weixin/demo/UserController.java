package com.jfinal.weixin.demo;

import com.jfinal.core.Controller;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;

public class UserController extends Controller{
	public void index() {
		ApiResult apiResult = UserApi.getFollows();
		setAttr("users", apiResult);
		render("/api/index.html");
	}
}
