package com.dinglan.moffice;
import com.dinglan.moffice.model.Actor;
import com.dinglan.moffice.model.CacheHelper;

public class ActorKit {
	public static Actor getByToken(String token){
		Actor model=CacheHelper.getActorById(token);
		return model;
	}
}
