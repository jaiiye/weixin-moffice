package com.jfinal.weixin.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Share extends Model<Share> {
	public static final Share me = new Share();
	public Page<Share> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share order by id desc");
	}
	public Page<Share> paginateImage(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share where msgType='image' order by id desc");
	}
}
