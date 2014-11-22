package com.dinglan.weixin.msg;

import java.util.Map;

import com.dinglan.weixin.kit.JsonHelper;
import com.jfinal.kit.JsonKit;

public class JsonMsg {
	public String touser;
	public String toparty;
	public String totag;
	public String msgtype;
	public String agentid;
	public String safe;
	
	@Override
	public  String toString(){
		Map<String,Object> map=JsonHelper.getFieldVlaue(this);
 		return JsonKit.toJson(map, 2);
	}
}
