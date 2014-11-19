package com.jfinal.weixin.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Stimulate extends Model<Stimulate>{

	public static final Stimulate me = new Stimulate();
	public Page<Stimulate> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "SELECT * ", "FROM core_stimulate ORDER BY publishdatetime desc");
	}
}
