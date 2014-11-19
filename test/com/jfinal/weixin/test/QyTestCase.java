package com.jfinal.weixin.test;


import java.io.StringReader;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Ignore;
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
public class QyTestCase extends TestCase<WeixinConfig> {
	private String timestamp="1416187783";
	private String nonce="1766739666";
	String msg_signature="c4eb00b041686352e79086a41431eebe60b682c0";
	String echostr="PPlWjtSU58hBx/LjqqC+sGp3vYw3pvj+s5piC9IcwLrXMoATImSOX9+0Mo8eCW80UDEEUOX21WWVHZf6dtNfQw==";
	
	String tpl="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName>\n<Encrypt><![CDATA[%1$s]]></Encrypt>\n<AgentID><![CDATA[10]]></AgentID>\n</xml>";
	
	/*
	 * 测试验证
	 * 1.目前用WxCryptUtil加密的结果和微信的结果不一致，但能验证通过，奇怪！
	 * 2.增加验证正确log提示；
	 */
	@Ignore 
    public void testYanZheng() {
        String url = "/qy?msg_signature="+msg_signature;
        url+="&timestamp="+timestamp+"&nonce="+nonce+"&echostr="+echostr;
        
        String resp=  use(url).post("").invoke();
        Assert.assertEquals("3813775250553528279",resp);
    }  
	/*
	 * 测试签名
	 */
	@Ignore 
	public void testSignature(){
		WxCryptUtil cryptUtil=this.getWxCryptUtil();
		String signature=cryptUtil.signature(timestamp, nonce,echostr);
		Assert.assertEquals(msg_signature, signature);
	}
	
	/*
	 * 测试加解密
	 * 1.加密算法有问题（加密后再解密，和原名文，不一致）
	 * 2.自己增加了方法，进行修正，微信自己的加密方法仍然不知道；
	 */
	@Ignore 
	public void testEncryptAndDecrypt(){		
		WxCryptUtil cryptUtil=this.getWxCryptUtil();
		
		String dest0="PPlWjtSU58hBx/LjqqC+sGp3vYw3pvj+s5piC9IcwLrXMoATImSOX9+0Mo8eCW80UDEEUOX21WWVHZf6dtNfQw==";
		String dest1="urpJLoY7ZOl6g/BRh97eXE13hbsb2hmK9XPkQDfa3R/tShuhCrJNeYmZoIjijx+GJlGPnTkHbx0bjBHdIxWNAw==";
		
		String src0=cryptUtil.decrypt(dest0);
		String src1=cryptUtil.decrypt(dest1);
		Assert.assertEquals( src0, src1);
		
		String src=String.valueOf(new Date().getTime());
		String randomStr="1766739666";
		
		//先加密再解密
		String dest =cryptUtil.decrypt(cryptUtil.encrypt(randomStr, src));
		Assert.assertEquals( src, dest);
	}
	
	/*
	 * 接收文本消息
	 */ 
    @Test
	public void testTextMsg() {
    	//this.processTextMsg("1");
    	//this.processTextMsg("11");
    	//this.processTextMsg("2");
    	//this.processTextMsg("11");
    	//this.processTextMsg("01");
    	//this.processTextMsg("02");
    	
    	this.processTextMsg("9");
    }  
    
    /*
	 * 接收XML消息
	 */
    @Test
    public void testXmlMsg(){
    	//String xml="<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName><CreateTime>1408090502</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[scancode_push]]></Event><EventKey><![CDATA[6]]></EventKey><ScanCodeInfo><ScanType><![CDATA[qrcode]]></ScanType><ScanResult><![CDATA[http://www.baidu.com/]]></ScanResult></ScanCodeInfo><AgentID>10</AgentID></xml>";
    	//String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416367972</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[1ikq1zsvYs4mCTc1FCCY3J444CHIdEbY-TYRgQnHubO4dM2d933eHbGOgfGsqHoX37ZK8clIVrhR9jGIK47LeBg]]></MediaId><ThumbMediaId><![CDATA[1gQ3-zyIfS0ggXiyL6MFQjtOakZEsB932xv9P4Zt5TQ8vvQAx8IN7x2JRPydCAKqy]]></ThumbMediaId><MsgId>4587033601933050021</MsgId><AgentID>10</AgentID></xml>";
    	//String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416372754</CreateTime><MsgType><![CDATA[event]]></MsgType><AgentID>10</AgentID><Event><![CDATA[click]]></Event><EventKey><![CDATA[1]]></EventKey></xml>";
    	//this.processXmlMsg(xml);
    }  
    
    private void processTextMsg(String text) {
    	String tpl="<xml><Content><![CDATA[%1$s]]></Content><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416190315</CreateTime><MsgType><![CDATA[text]]></MsgType><MsgId>4587033601933049922</MsgId><AgentID>10</AgentID></xml>";
    	String xml=String.format(tpl,text);
    	this.processXmlMsg(xml);
    }
    private void processXmlMsg(String xml) {
    	WxCryptUtil wcu= this.getWxCryptUtil();
    	String secretXml=wcu.encrypt(nonce,xml);
    	String body=String.format(tpl, secretXml);
    	msg_signature=wcu.signature(timestamp, nonce, secretXml);
    	
    	msg_signature=wcu.signature(timestamp, nonce, secretXml);
    	
        String url = String.format("/qy?msg_signature=%1$s&timestamp=%2$s&nonce=%3$s",msg_signature,timestamp,nonce);
        String resp=  use(url).post(body).invoke();
        String dest = innerDecrypt(resp);
        System.out.println("解密结果dest："+dest);
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
