package com.dinglan.moffice.model;

import java.util.List;
import java.util.Date;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class Notice extends Model<Notice> {
	public Notice(){
		long time =new Date().getTime();
		this.set("createTime",time);
		this.set("startTime",time);
		this.set("endTime",time);
		this.set("state",0);
	}
	public static final Notice me = new Notice();

	public List<Notice> paginateImage(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *",
				"from wx_Schedule where state=0 order by id desc").getList();
	}
}
