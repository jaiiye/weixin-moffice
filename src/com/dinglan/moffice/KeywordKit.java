package com.dinglan.moffice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.dinglan.moffice.model.Share;
import com.dinglan.moffice.model.Stimulate;
import com.dinglan.moffice.model.User;
import com.dinglan.weixin.api.ApiConfig;
import com.dinglan.weixin.api.ApiResult;
import com.dinglan.weixin.api.UserApi;
import com.dinglan.weixin.msg.InTextMsg;
import com.dinglan.weixin.msg.OutMsg;
import com.dinglan.weixin.msg.OutNewsMsg;
import com.dinglan.weixin.msg.OutTextMsg;
import com.jfinal.plugin.activerecord.Page;

/*
 * 对关键字的处理
 */
public class KeywordKit {
	public static final String HelpTip = "小提示：" + "\n0.帮助" + "\n1.重要电话"
			+ "\n2.最新动态" + "\n3.最新分享" + "\n11.通信录" + "\n上传图片、音视频、位置即可分享 ^_^";
	private static KeywordKit me = new KeywordKit();

	public static OutMsg Process(InTextMsg inMsg) {
		OutTextMsg outMsg = new OutTextMsg(inMsg);
		outMsg.setContent("您输入的关键字不存在！" + HelpTip);

		String content = inMsg.getContent().trim();
		if (content.equals("0") || content.equals("?")) { // 总监或经理电话
			outMsg.setContent(HelpTip);
			
		} else if (content.equals("1")) { //常用电话
			Page<User> list = User.me.paginate();
			String str = processUserList(list);
			outMsg.setContent(str);
			
		} else if (content.equals("11")) {//通讯录
			Page<User> list = User.me.paginate(1, 100);
			String str = processUserList(list);
			outMsg.setContent(str);
			
		} else if (content.equals("2")) { //最新公告
			Page<Stimulate> list = Stimulate.me.paginate(1, 5);
			return processStimulateList(list, inMsg);
			
		} else if (content.equals("3")) { //最新动态
			Page<Share> list = Share.me.paginateImage(1, 5);
			return processShareList(list, inMsg);
			
		}else if (content.equals("4")) { //最新留言
			Page<Share> list = Share.me.paginateText(1, 30);
			return processShareList(list, inMsg);
			
		} else if (content.equals("9")) { // 我是谁
			ApiResult ret = UserApi.getFollows(0);
			LinkedHashMap<String, Object> map = getByUserId(
					ret.getList("userlist"), inMsg.getFromUserName());
			long count=Share.me.count(inMsg.getFromUserName());
			
			String str = String.format("姓名：%1$s\n电话：%2$s\n点击数：%3$s\n谚云：众人拾柴火焰高，加油测试啦 ^_^",
					map.get("name"), map.get("userid"),count);
			outMsg.setContent(str);
			
		} else if (content.equals("01")) { //数据统计
			ApiResult ret1 = UserApi.getFollows(0);
			ApiResult ret2 = UserApi.getFollows(1);
			Page<User> ret3 = User.me.paginate(1, 100);
			String str = String.format("成员数：%1$s\n关注数：%2$s\n员工数：%3$s", ret1
					.getList("userlist").size(), ret2.getList("userlist")
					.size(), ret3.getList().size());
			outMsg.setContent(str);
			
		} else if (content.equals("02")) { //成员同步
			List<Actor> list = getEmptyList();
			int count = createUsers(list);
			String str=String.format("需要同步%1$s\n实际同步%2$s",list.size(),count);
			outMsg.setContent(str);
		}
		return outMsg;
	}

	/*
	 * 创建用户
	 */
	private static int createUsers(List<Actor> actors) {
		String data = "{\"userid\": \"%1$s\",\"name\": \"%2$s\",\"department\": [1],\"position\": \"员工\",\"mobile\": \"%1$s\",\"gender\": 1,\"tel\": \"\",\"email\": \"\",\"weixinid\": \"\",\"enable\": 1, \"extattr\": {\"attrs\":[]}}";

		ApiResult ret = null;
		int num = 0;
		for (Actor m : actors) {
			ret = UserApi.createUser(String.format(data, m.Id,m.Name));
			if (!ret.isSucceed()) {
				System.out.println(ret.getJson()+","+m.Name+","+m.Id);
			} else
				num++;
		}
		return num;
	}

	/*
	 * 取得空置用户
	 */
	@SuppressWarnings("unchecked")
	private static List<Actor> getEmptyList() {

		Page<User> pages = User.me.paginate(1, 100);
		List<Actor> actors = new ArrayList<Actor>();
		for (User m : pages.getList()) {
			Actor a = me.new Actor(m.get("phone").toString(), m.get("name")
					.toString());
			actors.add(a);
		}

		ApiResult ret = UserApi.getFollows(0);
		ArrayList<Object> list = ret.getList("userlist");

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object obj : list) {
			map = (LinkedHashMap<String, Object>) obj;
			actors.remove(me.new Actor(map.get("userid").toString(), ""));
		}
		return actors;
	}

	@SuppressWarnings("unchecked")
	private static LinkedHashMap<String, Object> getByUserId(
			ArrayList<Object> list, String userid) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object obj : list) {
			map = (LinkedHashMap<String, Object>) obj;
			if (map.get("userid").equals(userid))
				return map;
		}
		return map;
	}

	private static OutMsg processShareList(Page<Share> list, InTextMsg inMsg) {
		OutNewsMsg outMsg = new OutNewsMsg(inMsg);
		for (Share m : list.getList()) {
			outMsg.addNews(m.get("fromUserName").toString(),
					m.get("fromUserName").toString() + "分享了一张图片",
					m.get("picUrl").toString(), m.get("picUrl").toString());
		}
		return outMsg;
	}

	private static OutMsg processStimulateList(Page<Stimulate> list,
			InTextMsg inMsg) {
		String url=ApiConfig.getUrl()+"share/getNew?id=";
		OutNewsMsg outMsg = new OutNewsMsg(inMsg);
		for (Stimulate m : list.getList()) {
			outMsg.addNews(m.get("TITLE").toString(),
					m.get("TITLE").toString(),
					"http://upload.df.cnhubei.com/2012/1226/1356464487344.jpg",
					url+m.getStr("ID"));
		}
		return outMsg; 
	}

	private static String processUserList(Page<User> list) {
		String str = "";
		for (User user : list.getList()) {
			str += String.format("%1$s，\t %2$s\n", user.get("name"),
					user.get("phone"));
		}
		return str;
	}

	class Actor {
		public String Id = "";
		public String Name = "";

		public Actor(String id, String name) {
			this.Id = id;
			this.Name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			Actor actor = (Actor) obj;
			return this.Id.equals(actor.Id);
		}
	}
}
