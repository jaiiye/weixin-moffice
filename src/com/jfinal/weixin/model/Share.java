package com.jfinal.weixin.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
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
	public Page<Share> paginateText(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share where msgType='text' AND LENGTH(content)>3  AND fromUserName!='15991890112'");
	}
	public Page<Share> paginateGps(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from wx_share WHERE msgType='location' AND fromUserName!='15991890112'");
	}
	public long count(String fromUserName) {
		return Db.queryLong(String.format("SELECT COUNT(*) FROM wx_share WHERE fromUserName='%1$s'",fromUserName));
	}
}
