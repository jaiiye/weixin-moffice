package com.jfinal.weixin.demo;

import java.util.List;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.model.*;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;

public class ShareController extends Controller{
	public void index() {
		List<Share> list =DbHelper.getTopShare();
		this.setAttr("shares", list.toArray());
		this.render("/share/index.html");
	}
	public void chat() {
		ApiResult ret = UserApi.getFollows(1);
		this.setAttr("users",ret.getList("userlist"));
		this.render("/share/chat.html");
	}
	public void getNew() {
		String id=this.getPara("id");
		Stimulate model =Stimulate.me.findById(id);
		this.setAttr("new", model);
		this.render("/share/new.html");
	}
	
	public void gpsList() {
		Page<Share> shares =Share.me.paginateGps(1, 50);
		this.setAttr("shares", shares.getList());
		this.render("/share/gpsList.html");
	}
	
	public void commentList() {
		Page<Share> shares =Share.me.paginateText(1, 50);
		this.setAttr("shares", shares.getList());
		this.render("/share/commentList.html");
	}
}
