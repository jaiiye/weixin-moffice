package com.dinglan.weixin.api;

import com.dinglan.weixin.kit.HttpKit;
import com.dinglan.weixin.kit.ParaMap;

/**
 * 用户管理 API
 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 */
public class UserApi {
	
	private static String getUserInfo =  "https://qyapi.weixin.qq.com/cgi-bin/user/get?userid=lisi";
	private static String getFollowers = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?fetch_child=1&department_id=1&status=";
	private static String createUser = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=";
	private static String getUserByCode = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?agentid=%1$s&code=%2$s&access_token=%3$s";
		
	public static ApiResult getUserInfo(String openId) {
		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessToken().getAccessToken()).put("openid", openId).put("lang", "zh_CN");
		return new ApiResult(HttpKit.get(getUserInfo, pm.getData()));
	}
	
	public static ApiResult getUserByCode(String agentid,String code) {
		String url=String.format(getUserByCode,agentid,code,AccessTokenApi.getAccessToken().getAccessToken());
		return new ApiResult(HttpKit.get(url));
	}
	
	public static ApiResult getFollowers(String nextOpenid,int status) {
		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessToken().getAccessToken());
		if (nextOpenid != null)
			pm.put("next_openid", nextOpenid);
		return new ApiResult(HttpKit.get(getFollowers+status, pm.getData()));
	}
	
	public static ApiResult getFollows(int status) {
		return getFollowers(null,status);
	}
	
	public static ApiResult createUser(String user) {
		String url=createUser+AccessTokenApi.getAccessToken().getAccessToken();
		return new ApiResult(HttpKit.post(url,user));
	}
}
