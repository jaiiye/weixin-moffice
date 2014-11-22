package com.dinglan.moffice.model;

import java.io.Serializable;


public class Actor implements Serializable{
	private static final long serialVersionUID = 1L;
	public String id;
	public String name;
	public String token;
	public static Actor blank=new Actor("0","匿名","匿名");
	public Actor(String id,String name ,String token){
		this.id=id;
		this.name=name;
		this.token=token;
	}
}
