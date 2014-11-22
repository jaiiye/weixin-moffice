package com.dinglan.moffice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dinglan.moffice.model.Actor;
import com.dinglan.moffice.model.CacheHelper;
import com.dinglan.moffice.model.Contact;
import com.dinglan.moffice.model.Task;
import com.dinglan.moffice.model.TaskDetail;
import com.dinglan.weixin.api.ApiResult;
import com.dinglan.weixin.api.UserApi;

public class TaskController extends com.jfinal.core.Controller {
	public void index(){
		this.render("/task/index.html");
	}
	
	private Actor current(){
		String token=this.getPara("token");
		Actor actor=ActorKit.getByToken(token);
		return actor;
	}
	
	public void detail(){
		String id=this.getPara("id");
		Actor actor=this.current();
		Task model= Task.me.findById(id);
		if(model.get("fromUserId").equals(actor.id)) model.put("own","true");
		if(model.get("state").equals(1)) model.put("stop","true");
			
		List<TaskDetail> list= TaskDetail.me.list(model.get("id").toString());
		
		this.setAttr("model", model);
		this.setAttr("list", list);
		
		this.render("/task/detail.html");
	}
	
	public void list(){
		Actor actor=this.current();
		String type=this.getPara("type");
		
		List<Task> list= Task.me.list(1,20,actor.id,type);
		
		this.setAttr("list", list.toArray());
		this.render("/task/list.html");
	}
	
	public void create(){
		Actor actor=this.current();
		List<Contact> list= Contact.me.list(1,100,actor.id);
		this.setAttr("list", list.toArray());
		this.render("/task/create.html");
	}
	
	public void createTask(){
		Actor actor=this.current();
		String[] ids=this.getPara("ids").split("\\|");
		String content=this.getPara("content");
		List<Actor> actors=new ArrayList<Actor>();
		for(String id : ids){
			if(!id.equals(null) && !id.equals(""))
				actors.add(CacheHelper.getActorById(id));
		}
		Task model=Task.me.create(actor,actors,content,content);
		this.renderJson(model);
	}
	
	public void stopTask(){
		//Actor actor=this.current();
		
		String id=this.getPara("id");
		Task model= Task.me.findById(id);
		if(!model.equals(null)){
			model.set("state", 1);
			model.update();
		}
		this.renderJson(model);
	}
	public void createDetail(){
		Actor actor=this.current();
		String taskId=this.getPara("taskId");
		String fromUserId=actor.id;
		String toUserId=this.getPara("toUserId");
		String content=this.getPara("content");
		TaskDetail detail = TaskDetail.me.create(taskId,fromUserId, toUserId, content, content);
		this.renderJson(detail);
	}
	public void count(){
		Actor actor=this.current();
		Task model= Task.me.getCountById(actor.id);
		model.put("actor", actor);
		
		this.renderJson(model);
	}
	/*
	 * 联系人
	 */
	public void contact(){
		Actor actor=this.current();
		this.setAttr("list",this.getContactList(actor));
		this.render("/task/contact.html");
	}
	/*
	 * 设置联系人
	 */
	public void setContact(){
		Actor actor=this.current();
		String toUserId=this.getPara("toUserId");
		String state=this.getPara("state");
		Contact.me.saveOrUpdate(actor.id,toUserId,state);
		this.renderText("{}");
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> getContactList(Actor actor){
		ApiResult ret = UserApi.getFollows(1);
		
		List<Contact> contacts = Contact.me.list(1,1000,actor.id);
		List<Object> list=ret.getList("userlist");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object obj : list) {
			map = (Map<String, Object>) obj;
			String id=map.get("userid").toString();
			map.put("id",id);
			map.put("state", this.exists(contacts, id)?"1":"0");
		}		
		return list;
	}
	private boolean exists(List<Contact> contacts,String id){
		for(Contact m :contacts){
			if(id.equals(m.get("toUserId"))) return true;
		}
		return false;
	}
}
