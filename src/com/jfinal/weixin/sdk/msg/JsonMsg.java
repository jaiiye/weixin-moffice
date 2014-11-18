package com.jfinal.weixin.sdk.msg;

import java.util.Map;

import com.jfinal.kit.JsonKit;
import com.jfinal.weixin.sdk.kit.JsonHelper;

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
