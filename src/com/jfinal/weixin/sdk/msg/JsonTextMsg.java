package com.jfinal.weixin.sdk.msg;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.JsonKit;
import com.jfinal.weixin.sdk.kit.JsonHelper;

public class JsonTextMsg extends JsonMsg {
	public String content;
	@Override
	public String toString(){
		Map<String,String> text =new HashMap<String,String>();
		text.put("content", content);
		Map<String,Object> map =JsonHelper.getFieldVlaue(this);
		map.put("text",text);
 		return JsonKit.toJson(map);
	}
}
