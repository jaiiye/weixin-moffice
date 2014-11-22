package com.dinglan.moffice.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Task extends Model<Task> {
	private String sqlCount="SELECT SUM(fromUser) AS fromUser,SUM(toUSer) AS toUser FROM("
+" SELECT 'actor', COUNT(*) AS fromUser,0 AS toUser FROM wx_task WHERE State=0 AND parentId=0 AND toUserId='%1$s' UNION " 
+" SELECT 'actor',0 AS fromUSer, COUNT(*) AS toUser FROM wx_task WHERE State=0 AND parentId>0 AND toUserId='%1$s' )xxx GROUP BY 'actor' ";
	public static final Task me = new Task();
	
	public Task create(Actor from,List<Actor> actors,String title,String content)
	{
		Date date=new Date();
		String toUsers="";
		for(Actor a : actors){toUsers+=a.name+",";}
		
		Task host=new Task();
		host.initValue(from, toUsers, title, content, date);
		host.save();
		
		Task sub=null;
		for(Actor to : actors){
			sub=new Task();
			sub.initValue(from, toUsers, title, content, date);
			sub.set("toUserId", to.id);
			sub.set("parentId", host.getInt("id"));
			sub.save();
		}
		return host;
	}
	private void initValue(Actor from,String toUsers,String title,String content,Date date){
		this.set("fromUserId", from.id);
		this.set("toUserId", from.id);
		this.set("parentId",0);
		this.set("toUsers", toUsers);
		this.set("title", title);
		this.set("content",content);
		this.set("state", 0);
		this.set("createTime", date.getTime());
	}
	public Task findById(String id){
		Task model= super.findById(id);
		model.put("fromUserName", CacheHelper.getActorById(model.getStr("fromUserId")).name);
		model.put("createDate", CacheHelper.toDate(model.get("createTime")));
		return model;
	}
	
	public List<Task> listByParentId(String id){
		String sql="from wx_task where parentId='"+id+"'";
		Page<Task> pages = paginate(1, 1000, "select *",sql+" order by id desc" );
		return wrapList(pages);
	}
	
	public List<Task> list(int pageNumber, int pageSize,String id, String sign) {
		String sql="from wx_task ";
		if(sign.equals("all"))  sql+=" where state=0 and toUserId='"+id+"'";
		if(sign.equals("from")) sql+=" where state=0 and parentId=0 and fromUserId='"+id+"'";
		if(sign.equals("to"))   sql+=" where state=0 and parentId>0 and toUserId='"+id+"'";
		if(sign.equals("stop")) sql+=" where state=1 and toUserId='"+id+"'";
		
		Page<Task> pages = paginate(pageNumber, pageSize, "select *",sql+" order by id desc" );
		return wrapList(pages);
	}
	private static List<Task> wrapList(Page<Task> pages){
		List<Task> list=pages.getList();
		for(Task m : list){
			m.put("fromUserName",CacheHelper.getActorById(m.getStr("fromUserId")).name);
			m.put("toUserName",CacheHelper.getActorById(m.getStr("toUserId")).name);
			m.put("createDate", CacheHelper.toDate(m.get("createTime")));
		}
		return list;
	}
	public Task getCountById(String id){
		return this.findFirst(String.format(sqlCount,id));
	}
}

