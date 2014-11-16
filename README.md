# Jfinal 极速开发
JFinal Weixin 是基于 JFinal 的微信企业号快速 SDK，只需浏览 Demo 代码即可进行极速开发

## 1、WeixinConfig
```java
public class WeixinConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		// 配置微信 API 相关常量
		ApiConfig.setDevMode(me.getDevMode());
		ApiConfig.setUrl(getProperty("url"));
		ApiConfig.setToken(getProperty("token"));
		ApiConfig.setAppId(getProperty("appId"));
		ApiConfig.setAppSecret(getProperty("appSecret"));
	}
	
	public void configRoute(Routes me) {
		me.add("/qy", QyController.class);
		me.add("/api", ApiController.class);
		me.add("/test", TestController.class);
	}
	
	public void configPlugin(Plugins me) {}
	public void configInterceptor(Interceptors me) {}
	public void configHandler(Handlers me) {}
}
```
通过 ApiConfig 提供企业号所需的相关配置，并在configRoute 方法配置路由

## 2、DemoController

```
DemoController 通过继承自 WeixinController 便拥有了接收消息和发送消息的便利方法

## 3、ApiController
``` java
public class ApiController extends Controller {
	public void index() {
		render("/api/index.html");
	}
	
	/**
	 * 获取公众号菜单
	 */
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}
	
	/**
	 * 获取公众号关注用户
	 */
	public void getFollowers() {
		ApiResult apiResult = UserApi.getFollows();
		renderText(apiResult.getJson());
	}
}

```
通过调用 MenuApi、UserApi 等 Api 的相关方法即可获取封装成 ApiResult 对象的结果，使用 render 系列方法即可快捷输出结果


## 4、更多支持
演示环境正在搭建中  

