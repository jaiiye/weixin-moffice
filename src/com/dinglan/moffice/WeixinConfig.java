package com.dinglan.moffice;

import com.dinglan.moffice.model.Contact;
import com.dinglan.moffice.model.Notice;
import com.dinglan.moffice.model.Share;
import com.dinglan.moffice.model.Stimulate;
import com.dinglan.moffice.model.Task;
import com.dinglan.moffice.model.TaskDetail;
import com.dinglan.moffice.model.User;
import com.dinglan.weixin.api.ApiConfig;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.cron.Cron4jPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;

public class WeixinConfig extends JFinalConfig {
	
	public void configConstant(Constants me) {
		super.loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		//配置统一异常页面
		me.setError404View("/common/404.html");
		
		// 配置微信 API 相关常量
		ApiConfig.setDevMode(me.getDevMode());
		ApiConfig.setUrl(getProperty("url"));
		ApiConfig.setToken(getProperty("token"));
		ApiConfig.setAppId(getProperty("appId"));
		ApiConfig.setAppSecret(getProperty("appSecret"));
		ApiConfig.setAgentId(getProperty("agentId"));
		ApiConfig.setEncodingAESKey(getProperty("encodingAESKey"));
		
	}
	
	public void configRoute(Routes me) {
		//me.add(new AutoBindRoutes());	//自动扫描
		
		me.add("/qy", QyController.class);
		me.add("/api", ApiController.class);
		me.add("/share", ShareController.class);
		me.add("/task", TaskController.class);
		
	}
	
	public void configPlugin(Plugins me) {
		// 配置缓存插件
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);
		
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("userName"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		
		arp.addMapping("core_employee", User.class);
		arp.addMapping("core_stimulate", Stimulate.class);
		
		arp.addMapping("wx_share", Share.class);	
		arp.addMapping("wx_contact", Contact.class);
		arp.addMapping("wx_task", Task.class);
		arp.addMapping("wx_taskDetail", TaskDetail.class);
		arp.addMapping("wx_notice", Notice.class);
		
		
		/*
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(c3p0Plugin);   
		atbp.autoScan(false);
		me.add(atbp);
		*/
		
		//配置任务
		Cron4jPlugin cron4jPlugIn = new Cron4jPlugin();
	    me.add(cron4jPlugIn);
	    
	    //sqlXml
	    SqlInXmlPlugin sqlInXmlPlugin = new SqlInXmlPlugin();
	    me.add(sqlInXmlPlugin);
	    
	}
	
	public void configInterceptor(Interceptors me) {
		
	}
	
	public void configHandler(Handlers me) {
		
	}
	
	public static void main(String[] args) {
		JFinal.start("webapp", 80, "/", 5);
	}
}
