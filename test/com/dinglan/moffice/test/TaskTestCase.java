package com.dinglan.moffice.test;

import org.junit.Test;

import com.dinglan.moffice.WeixinConfig;

public class TaskTestCase extends BaseTestCase<WeixinConfig> {
	
	private String token= "15991890112";
	@Test
	public void contact(){
		String url = "/task/contact";
		this.setParameter("token", token);
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}
	
	@Test
	public void createDetail(){
		String url = "/task/createDetail";
		this.setParameter("taskId","19");
		this.setParameter("token",token);
		this.setParameter("toUserId","15809228433");
		this.setParameter("content","大家好，我知道了！");
		
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
	
	@Test
	public void createTask(){
		String url = "/task/createTask";
		this.setParameter("token",token);
		this.setParameter("ids","15809228433|15102937865");
		this.setParameter("content","大家好，我是大侠！");
		
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
	
	@Test
	public void detail(){
		String url = "/task/detail";
		this.setParameter("token", token);
		this.setParameter("id", "1");
		
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
	
	@Test
	public void create(){
		String url = "/task/create";
		this.setParameter("token",token);
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
	
	
	@Test
	public void count(){
		String url = "/task/count";
		this.setParameter("token", token);
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
	
	@Test
	public void list(){
		String url = "/task/list";
		this.setParameter("token",token);
		this.setParameter("type","from");
		String resp=  use(url).post("").invoke();
		System.out.println(resp);
	}	
}
