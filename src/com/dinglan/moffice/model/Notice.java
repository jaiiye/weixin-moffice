package com.dinglan.moffice.model;

import java.util.Date;
import java.util.List;

import com.dinglan.ext.plugin.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
@TableBind(tableName="wx_notice")
public class Notice extends Model<Notice> {
	public static final Notice me = new Notice();
	public Notice(){}
	
	public Notice create(){
		Notice model = new Notice();
		long time =new Date().getTime();
		model.set("createTime",time);
		model.set("startTime",time);
		model.set("endTime",time);
		model.set("state",0);
		return model;
	}
	public List<Notice> listOuting(int pageNumber, int pageSize){
		Page<Notice> pages = paginate(pageNumber, pageSize, "select *", "from wx_notice where state=0");
		return pages.getList();
	}
}
