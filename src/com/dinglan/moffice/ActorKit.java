package com.dinglan.moffice;
import com.dinglan.moffice.model.Actor;
import com.dinglan.moffice.model.CacheHelper;

public class ActorKit {
	
	//通过点击菜单来快捷认证，回避多次请求腾讯数据导致满的问题
	public static String CURENT="";
	public static Actor getByToken(String token){
		if(token==null || token.equals("")) token=ActorKit.CURENT;
		Actor model=CacheHelper.getActorById(token);
		return model;
	}
}
