package com.jfinal.weixin.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class User  extends Model<User>{

	public static final User me = new User();
	public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from core_employee where STATETYPE=0");
	}
	public Page<User> paginate() {
		return paginate(1, 100, "select *", "from core_employee  WHERE stateType=0 AND (job LIKE '%经理%' OR job LIKE '%总监%')");
	}
}
