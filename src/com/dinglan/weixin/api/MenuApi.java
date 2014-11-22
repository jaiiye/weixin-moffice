package com.dinglan.weixin.api;

import com.dinglan.weixin.kit.HttpKit;

/**
 * menu api
 */
public class MenuApi {
	private static String getMenu = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?agentid=%1$s&access_token=%2$s";
	private static String createMenu = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?agentid=%1$s&access_token=%2$s";
	
	/**
	 * 查询菜单
	 */
	public static ApiResult getMenu(String agentId) {
		String token=AccessTokenApi.getAccessToken().getAccessToken();
		String jsonResult = HttpKit.get(String.format(getMenu,agentId,token));
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 创建菜单
	 */
	public static ApiResult createMenu(String agentId,String jsonStr) {
		String token=AccessTokenApi.getAccessToken().getAccessToken();
		String jsonResult = HttpKit.get(String.format(createMenu,agentId,token));
		return new ApiResult(jsonResult);
	}
}


