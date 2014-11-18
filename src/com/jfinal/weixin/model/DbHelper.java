package com.jfinal.weixin.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({ "serial", "rawtypes" })
public class DbHelper{
	/*
	 * 取得最新分享
	 */
	public static List<Share> getTopShare() {
		String sql="SELECT  * FROM wx_share WHERE  msgType='image'  ORDER BY id DESC LIMIT 0,20";
		return Share.me.find(sql);
	}
	/*
	 * 取得通信录
	 */
	public static List getTopShare(int i) {
		return null; 
	}
}
