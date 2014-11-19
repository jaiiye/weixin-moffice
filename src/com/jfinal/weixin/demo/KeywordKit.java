package com.jfinal.weixin.demo;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.model.Stimulate;
import com.jfinal.weixin.model.User;
import com.jfinal.weixin.sdk.msg.InTextMsg;
import com.jfinal.weixin.sdk.msg.OutMsg;
import com.jfinal.weixin.sdk.msg.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.OutTextMsg;
/*
 * 对关键字的处理
 */
public class KeywordKit {
	public static final String HelpTip = "小提示：" +
			"\n0.帮助" +
			"\n1.重要电话" +
			"\n2.最新动态" +
			"\n3.最新分享" +
			"\n11.通信录" +
			"\n上传图片、音视频、位置即可分享 ^_^";
	
	public static OutMsg Process(InTextMsg inMsg) {
		OutTextMsg outMsg = new OutTextMsg(inMsg);
		outMsg.setContent("您输入的关键字不存在！"+HelpTip);
		
		String content = inMsg.getContent().trim();
		if (content.equals("0") || content.equals("?")) { //总监或经理电话
			outMsg.setContent(HelpTip);
		}
		else if (content.equals("1")) { //总监或经理电话
			Page<User> list = User.me.paginate();
			String str = processUserList(list);
			outMsg.setContent(str);
		}else if (content.equals("11")) {
			Page<User> list = User.me.paginate(1, 100);
			String str = processUserList(list);
			outMsg.setContent(str);
		}else if (content.equals("2")) { //总监或经理电话
			Page<Stimulate> list = Stimulate.me.paginate(1,5);
			return processStimulateList(list,inMsg);
		}
		return outMsg;
	}
	private static  OutMsg processStimulateList(Page<Stimulate> list,InTextMsg inMsg){
		OutNewsMsg outMsg = new OutNewsMsg(inMsg);
		for (Stimulate m : list.getList()) {
			outMsg.addNews(m.get("TITLE").toString(), m.get("TITLE").toString(), "http://upload.df.cnhubei.com/2012/1226/1356464487344.jpg", "http://upload.df.cnhubei.com/2012/1226/1356464487344.jpg");
		}
		return outMsg;
	}
	private static  String processUserList(Page<User> list){
		String str = "";
		for (User user : list.getList()) {
			str += String.format("%1$s，\t %2$s\n", user.get("NAME"),user.get("PHONE"));
		}
		return str;
	}
}
