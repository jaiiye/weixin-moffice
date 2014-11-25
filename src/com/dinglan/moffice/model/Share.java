package com.dinglan.moffice.model;

import java.util.List;

import com.dinglan.ext.plugin.TableBind;
import com.dinglan.moffice.kit.CacheHelper;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
@TableBind(tableName="wx_share")
public class Share extends Model<Share> {
	public static final Share me = new Share();
	public Page<Share> paginateImage(int pageNumber, int pageSize) {
		Page<Share> pages = paginate(pageNumber, pageSize, "select *", "from wx_share where msgType='image' order by id desc");
		return wrapList(pages);
	}
	public Page<Share> paginateText(int pageNumber, int pageSize) {
		Page<Share> pages = paginate(pageNumber, pageSize, "select *", "from wx_share where msgType='text' AND LENGTH(content)>3  AND fromUserName!='15991890112'");
		return wrapList(pages);
	}
	public Page<Share> paginateGps(int pageNumber, int pageSize) {
		Page<Share> pages = paginate(pageNumber, pageSize, "select *", "from wx_share WHERE msgType='location' AND fromUserName!='15991890112'");
		return wrapList(pages);
	}
	public long count(String fromUserName) {
		return Db.queryLong(String.format("SELECT COUNT(*) FROM wx_share WHERE fromUserName='%1$s'",fromUserName));
	}
	
	private Page<Share> wrapList(Page<Share> pages){
		List<Share> list=pages.getList();
		for(Share m : list){
			m.put("createDate", CacheHelper.toDate(m.get("createTime")));
			m.put("fromNickName",CacheHelper.getActorById(m.getStr("fromUserName")).name);
		}
		return pages;
	}
}
