package com.jfinal.weixin.demo;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.weixin.model.User;
import com.jfinal.weixin.sdk.msg.InTextMsg;
import com.jfinal.weixin.sdk.msg.OutMsg;
import com.jfinal.weixin.sdk.msg.OutTextMsg;
/*
 * 对关键字的处理
 */
public class KeywordKit {
	public static final String HelpTip = "小提示：" +
			"\n0.帮助" +
			"\n1.重要电话" +
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
		}
		return outMsg;
	}
	private static  String processUserList(Page<User> list){
		System.out.println(list.getList().size());
		String str = "";
		for (User user : list.getList()) {
			str += String.format("%1$s，\t %2$s\n", user.get("NAME"),user.get("PHONE"));
		}
		return str;
	}
}
