package com.dinglan.moffice.kit;

import java.util.Date;
import java.util.List;
import com.dinglan.moffice.model.Notice;
import com.dinglan.weixin.api.MessageApi;
import com.dinglan.weixin.api.ApiResult;
import com.jfinal.log.Logger;

/*
 * 发送任务
 */
public class NoticeTask implements Runnable {
	private static final Logger log =  Logger.getLogger(NoticeTask.class);
    public void run() {
    	List<Notice> list=Notice.me. listOuting(1,10);
    	for(Notice m : list){
    	   String msg=m.getStr("content");
    	   Date start=new Date();
    	   ApiResult ret=MessageApi.sendMsg(msg);
    	   m.set("startTime", start.getTime());
    	   m.set("startTime", new Date().getTime());
    	   m.set("result", ret.getJson());
    	   m.set("state", 1);
    	   m.update();
    	}
    	if(list.size()>0)
    		log.info(String.format("本次发送了%1$s条信息！",list.size()));
    }
}
