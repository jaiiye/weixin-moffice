package com.dinglan.moffice.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Contact extends Model<Contact> {
	public static final Contact me = new Contact();
	private String sql="from wx_contact where fromUserId='%1$s' order by id desc";
	
	public void saveOrUpdate(String fromUserId,String toUserId,String state){
		Db.update(String.format("delete from wx_contact where fromUserId='%1$s' and toUserId='%2$s'",fromUserId,toUserId));
		if(state.equals("1")){
			Contact model=new Contact();
			model.set("fromUserId", fromUserId);
			model.set("toUserId", toUserId);
			model.set("createTime", new Date().getTime());
			model.save();
		}
	}
	public List<Contact> list(int pageNumber, int pageSize,String id) {
		List<Contact> list = super.paginate(pageNumber, pageSize, "select *",String.format(sql, id)).getList();
		for(Contact m : list){
			m.put("fromUserName",CacheHelper.getActorById(m.getStr("fromUserId")).name);
			m.put("toUserName",CacheHelper.getActorById(m.getStr("toUserId")).name);
		}
		return list;
	}
}
