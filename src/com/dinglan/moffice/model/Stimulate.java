package com.dinglan.moffice.model;

import java.util.List;

import com.dinglan.ext.plugin.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
@TableBind(tableName="core_stimulate")
public class Stimulate extends Model<Stimulate>{
	public static final Stimulate me = new Stimulate();
	public List<Stimulate> list(int pageNumber, int pageSize) {
		Page<Stimulate> pages = paginate(pageNumber, pageSize, "SELECT id,title,content,publishdatetime AS createDate  ", "FROM core_stimulate ORDER BY publishdatetime desc");
		return pages.getList();
	}
}
