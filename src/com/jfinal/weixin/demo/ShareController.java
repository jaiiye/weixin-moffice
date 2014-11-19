package com.jfinal.weixin.demo;

import java.util.List;
import com.jfinal.core.Controller;
import com.jfinal.weixin.model.*;

public class ShareController extends Controller{
	public void index() {
		List<Share> list =DbHelper.getTopShare();
		this.setAttr("shares", list.toArray());
		this.render("/share/index.html");
	}
	
	public void getNew() {
		String id=this.getPara("id");
		Stimulate model =Stimulate.me.findById(id);
		this.setAttr("new", model);
		this.render("/share/new.html");
	}
}
