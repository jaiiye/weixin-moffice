package com.jfinal.weixin.test;


import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.kit.WxCryptUtil;
import com.jfinal.weixin.sdk.api.ApiConfig;

/*
 * 所有对Controller的测试必须继承ControllerTestCase,此类中方法说明如下
 * use	需要调用的url
 * post	post数据包,支持String和File
 * writeTo	response数据写入文件
 * invoke	调用url
 * findAttrAfterInvoke	action调用之后getAttr的值
 */
public class QyTestCase extends BaseTestCase<WeixinConfig> {
	
	String tpl="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName>\n<Encrypt><![CDATA[%1$s]]></Encrypt>\n<AgentID><![CDATA[10]]></AgentID>\n</xml>";
	
	
	/*
	 * 接收文本消息
	 */ 
    @Test
	public void testTextMsg() {
    	this.processTextMsg("1");
    	this.processTextMsg("11");
    	this.processTextMsg("2");
    	this.processTextMsg("11");
    	//this.processTextMsg("01");
    	//this.processTextMsg("02");
    	//this.processTextMsg("9");
    	//this.processTextMsg("2");
    }  
    
    /*
	 * 接收XML消息
	 */
    @Test
    public void testXmlMsg(){
    	//String xml="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName><CreateTime>1408090502</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[scancode_push]]></Event><EventKey><![CDATA[6]]></EventKey><ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType><ScanResult><![CDATA[http://www.baidu.com/]]></ScanResult></ScanCodeInfo><AgentID>10</AgentID></xml>";
    	//String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416367972</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[1ikq1zsvYs4mCTc1FCCY3J444CHIdEbY-TYRgQnHubO4dM2d933eHbGOgfGsqHoX37ZK8clIVrhR9jGIK47LeBg]]></MediaId><ThumbMediaId><![CDATA[1gQ3-zyIfS0ggXiyL6MFQjtOakZEsB932xv9P4Zt5TQ8vvQAx8IN7x2JRPydCAKqy]]></ThumbMediaId><MsgId>4587033601933050021</MsgId><AgentID>10</AgentID></xml>";
    	//String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416372754</CreateTime><MsgType><![CDATA[event]]></MsgType><AgentID>10</AgentID><Event><![CDATA[click]]></Event><EventKey><![CDATA[1]]></EventKey></xml>";
    	
    	//菜单事件
    	String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[13991230605]]></FromUserName><CreateTime>1416477051</CreateTime><MsgType><![CDATA[event]]></MsgType><AgentID>10</AgentID><Event><![CDATA[view]]></Event><EventKey><![CDATA[http://dinglantech.com.cn/weixin/share/gpsList]]></EventKey></xml>";
    	this.processXmlMsg(xml);
    }  
    
    private void processTextMsg(String text) {
    	String tpl="<xml><Content><![CDATA[%1$s]]></Content><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416190315</CreateTime><MsgType><![CDATA[text]]></MsgType><MsgId>4587033601933049922</MsgId><AgentID>10</AgentID></xml>";
    	String xml=String.format(tpl,text);
    	this.processXmlMsg(xml);
    }
    private void processXmlMsg(String xml) {
    	String timestamp="1416187783";
    	String nonce="1766739666";
    	String msg_signature="c4eb00b041686352e79086a41431eebe60b682c0";
    	
    	WxCryptUtil wcu= this.getWxCryptUtil();
    	String secretXml=wcu.encrypt(nonce,xml);
    	String body=String.format(tpl, secretXml);
    	
    	msg_signature=wcu.signature(timestamp, nonce, secretXml);
    	msg_signature=wcu.signature(timestamp, nonce, secretXml);
    	
        String url = String.format("/qy?msg_signature=%1$s&timestamp=%2$s&nonce=%3$s",msg_signature,timestamp,nonce);
        String resp=  use(url).post(body).invoke();
        
        if(!resp.equals(null) && !resp.equals("")){//需要解析
	        String dest = innerDecrypt(resp);
	        System.out.println("解密结果dest："+dest);
        }
    }
    
    /*
     * 内部验证解密
     */
    private String  innerDecrypt( String xml){
    	WxCryptUtil wcu= this.getWxCryptUtil();
    	String msgSignature = this.extractEncryptPart(xml, "MsgSignature");
    	String timeStamp = this.extractEncryptPart(xml, "TimeStamp");
    	String nonce = this.extractEncryptPart(xml, "Nonce");
    	
    	return wcu.decrypt(msgSignature, timeStamp, nonce, xml);
    }
    private String extractEncryptPart(String xml,String name) {
		try {
			DocumentBuilder db = builderLocal.get();
			Document document = db
					.parse(new InputSource(new StringReader(xml)));

			Element root = document.getDocumentElement();
			return root.getElementsByTagName(name).item(0)
					.getTextContent();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    private final ThreadLocal<DocumentBuilder> builderLocal = new ThreadLocal<DocumentBuilder>() {
		@Override
		protected DocumentBuilder initialValue() {
			try {
				return DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException exc) {
				throw new IllegalArgumentException(exc);
			}
		}
	};
	private WxCryptUtil getWxCryptUtil(){
    	WxCryptUtil cryptUtil=null;
    	if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
}
