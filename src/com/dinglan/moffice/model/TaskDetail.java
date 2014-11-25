package com.dinglan.moffice.model;

import java.util.Date;
import java.util.List;

import com.dinglan.ext.plugin.TableBind;
import com.dinglan.moffice.kit.CacheHelper;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
@TableBind(tableName="wx_taskDetail")
public class TaskDetail extends Model<TaskDetail> {
	public static final TaskDetail me = new TaskDetail();
	public TaskDetail  create(String taskId,String fromUserId,String toUserId,String title,String content)
	{
		TaskDetail model= new TaskDetail();
		model.set("taskId", taskId);
		model.set("fromUserId", fromUserId);
		model.set("toUserId", toUserId);
		model.set("title", title);
		model.set("content", content);
		model.set("createTime", (new Date()).getTime());
		model.save();
		return model;
	}
	public  List<TaskDetail> list(String id) {
		List<TaskDetail> list = paginate(1,1000, "select *", "from wx_taskDetail where taskId='"+id+"' order by id desc").getList();
		return wrapList(list);
	}
	private  List<TaskDetail> wrapList(List<TaskDetail> list){
		for(TaskDetail m : list){
			m.put("fromUserName",CacheHelper.getActorById(m.getStr("fromUserId")).name);
			m.put("toUserName",CacheHelper.getActorById(m.getStr("toUserId")).name);
			m.put("createDate", CacheHelper.toDate(m.get("createTime")));
		}
		return list;
	}
}
