package com.jfinal.weixin.test;

import org.junit.Ignore;
import org.junit.Test;

import com.jfinal.weixin.test.util.MockServletContext;

public class VewPathTest {
	@SuppressWarnings("rawtypes")
	@Ignore
	public void getPath(){
		Class cls =MockServletContext.class;
		System.out.println(cls.getResource("").getFile());
		System.out.println(cls.getResource(".").getFile());
		System.out.println(cls.getResource("/").getFile());
	}
}
