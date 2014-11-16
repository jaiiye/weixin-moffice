package com.jfinal.weixin.test;


import org.junit.Test;


/*
 * 所有对Controller的测试必须继承ControllerTestCase,此类中方法说明如下
 * use	需要调用的url
 * post	post数据包,支持String和File
 * writeTo	response数据写入文件
 * invoke	调用url
 * findAttrAfterInvoke	action调用之后getAttr的值
 */
public class GzTestCase extends ControllerTestCase<GzConfig> {
	@Test //测试关注
    public void test3() {
        String url = "/qy?msg_signature=92d78117ec30e9e55db25e343111be8a80c1186b&timestamp=1416139301&nonce=1318888434";
        String body = "<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><Encrypt><![CDATA[x+cecPmzYQEu6NKD0F5j7n2uDwksrOWWl50dpbY5jMH3vmRrc6nhJScxj27ZztDjauQ++tTUPr4jeUk/5VqlBA1OOR4iICh67pGhEAoB0firFlikMTtj53B7nYNsCPYmfggr8g2sxqReQTfXpWa5z7CqPPNSevl0l8uc5cuy7lAinRsXFvbiT/msSGRXkrDfkZ+/DZAyA9IwwddR38B9yCXFoMX+2JkkVImBpoYHMl+n/pAFUN/zi4gIOCxeUkPaYynoHZfWCMYY+f/dXovU85saDKuWol0YEqf66WxPlwFMCIh+M5eqDKB6p5+1MOyjKrIXFja35BAlsiisPi66PQR6yMaD3w3aBN54Qbl3U0BGebM8AY/czVKgGwl711vMmYuduXWkWJxlkgfwjDLd3Z0XBNfQdkdkXEL1Bt1XIzeaeOdMkZFkhk671jU4OFTB0aaeEPM0ltQPPcprdTjarw==]]></Encrypt><AgentID><![CDATA[10]]></AgentID></xml>";
        String resp=  use(url).post(body).invoke();
        System.out.println(resp);
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
