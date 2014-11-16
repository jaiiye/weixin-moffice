package com.jfinal.weixin.test;


import org.junit.BeforeClass;
import org.junit.Test;

import com.jfinal.ext.test.ControllerTestCase;

/*
 * 所有对Controller的测试必须继承ControllerTestCase,此类中方法说明如下
 * use	需要调用的url
 * post	post数据包,支持String和File
 * writeTo	response数据写入文件
 * invoke	调用url
 * findAttrAfterInvoke	action调用之后getAttr的值
 */
public class PostTestCase extends ControllerTestCase<MyConfig> {
	@Test
    public void test3() {
        String url = "/api/getMenu";
        String body = "<root>中文</root>";
        use(url).post(body).invoke();
    }  
	/*
	@Test
    public void line() throws Exception {
        String url = "/post";
        String filePath = Thread.currentThread().getContextClassLoader().getResource("dataReq.xml").getFile();
        String fileResp = "/home/kid/git/jfinal-ext/resource/dataResp.xml";
        String resp = use(url).post(new File(filePath)).writeTo(new File(fileResp)).invoke();
        System.out.println(resp);
    }
    */
}
