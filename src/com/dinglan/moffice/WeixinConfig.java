package com.dinglan.moffice;

import com.dinglan.ext.plugin.AutoBindRoutes;
import com.dinglan.ext.plugin.AutoTableBindPlugin;
import com.dinglan.ext.plugin.Cron4jPlugin;
import com.dinglan.ext.plugin.SqlInXmlPlugin;
import com.dinglan.ext.plugin.TableBind;
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
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

public class WeixinConfig extends JFinalConfig {

	public void configConstant(Constants me) {
		super.loadPropertyFile("a_little_config.txt");
		//me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setDevMode(false);
		
		// 配置统一异常页面
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
		//自动路由插件
		AutoBindRoutes routes = new AutoBindRoutes();
		me.add(routes);
	}

	public void configPlugin(Plugins me) {
		
		// 配置缓存插件
		EhCachePlugin ecp = new EhCachePlugin();
		me.add(ecp);

		
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
				getProperty("userName"), getProperty("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
	
		//Model自动绑定
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(c3p0Plugin);
		me.add(atbp);

		// 配置任务
		Cron4jPlugin cron4jPlugIn = new Cron4jPlugin();
		me.add(cron4jPlugIn);

		// sqlXml
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
