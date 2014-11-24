package com.dinglan.moffice.model;

import java.util.Date;
import java.util.List;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Notice extends Model<Notice> {
	public static final Notice me = new Notice();
	public Notice(){
		long time =new Date().getTime();
		this.set("createTime",time);
		this.set("startTime",time);
		this.set("endTime",time);
		this.set("state",0);
	}
	public List<Notice> listOuting(int pageNumber, int pageSize){
		Page<Notice> pages = paginate(pageNumber, pageSize, "select *", "from wx_notice where state=0");
		return pages.getList();
	}
}
