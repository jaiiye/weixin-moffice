package com.jfinal.weixin.demo;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.model.*;;

public class ShareController extends Controller{
	public void index() {
		Page<Share> shares= Share.me.paginate(getParaToInt(0, 1), 10);
		this.renderJson(shares);
	}
}
