package com.dinglan.moffice.model;

import java.util.Date;
import com.jfinal.plugin.activerecord.Model;

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
	
}
