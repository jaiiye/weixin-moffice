package com.jfinal.weixin.demo;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.model.*;;

public class ShareController extends Controller{
	public void index() {
		Page<Share> shares= Share.me.paginateImage(getParaToInt(0, 1), 20);
		this.setAttr("shares", shares.getList());
		this.render("/share/index.htm");
	}
}
