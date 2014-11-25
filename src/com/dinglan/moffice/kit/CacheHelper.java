package com.dinglan.moffice.kit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dinglan.moffice.model.Actor;
import com.dinglan.moffice.model.User;
import com.jfinal.plugin.ehcache.CacheKit;

public class CacheHelper {
	@SuppressWarnings("unchecked")
	private static Map<String,Actor> getActors(){
		if(CacheKit.get("actor", "list") ==null ){
			List<User> list= User.me.paginate(1, 100000).getList();
			Map<String,Actor> actors=new HashMap<String,Actor>();
			for(User m : list){
				Actor a=new Actor(m.get("phone").toString(),m.get("name").toString(),"");
				if(!actors.containsKey(a.id)){
					actors.put(a.id,a);
				}
			}
			CacheKit.put("actor", "list", actors);
		}
		return (Map<String,Actor>)CacheKit.get("actor", "list");
		
	}
	
	public static Date toDate(Object obj){
        Date date = new Date();  
        try {
        	long lng= Long.valueOf(obj.toString());
        	Timestamp ts = new Timestamp(lng);  
            date = ts;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return date;
	}
	
	
	public static Actor getActorById( String id){
		Map<String,Actor> actors=getActors();
		if(actors.containsKey(id)) return actors.get(id);
		return Actor.blank;
	} 
}
