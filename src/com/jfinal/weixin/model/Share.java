package com.jfinal.weixin.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Share extends Model<Share> {
	public static final Share me = new Share();
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<Share> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share order by id desc");
	}
	
	public Page<Share> paginateImage(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share where msgType='image' order by id desc");
	}
}
