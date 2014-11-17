package com.jfinal.weixin.test;


import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.kit.EncryptionKit;
import com.jfinal.weixin.sdk.kit.SignatureCheckKit;
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
public class QyTestCase extends ControllerTestCase<WeixinConfig> {
	private String timestamp="1416187783";
	private String nonce="1766739666";
	String msg_signature="c4eb00b041686352e79086a41431eebe60b682c0";
	String echostr="PPlWjtSU58hBx/LjqqC+sGp3vYw3pvj+s5piC9IcwLrXMoATImSOX9+0Mo8eCW80UDEEUOX21WWVHZf6dtNfQw==";
	
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
	
	@Test 
	public void testDecrypt() {
		WxCryptUtil cryptUtil=this.getWxCryptUtil();
		String dest="d86AT8h8YT3uu7GFX0IafC/Vgm+en40riIe+/K/oiT24ZjDj1IhpJD3Hf6JoFaGwhvCBfIwz4iY1RoX/sTSq5h2dUagyio8thX/mRY6OBHFyqS0HtGJCzsh2KuDGqZKn/F8qks/8LfkC87vbXJGn+Yt94RvWx29pouE3fzR09kmlWVA3J9kP+InL+lbelTJ3eVzpbV9ToyczWHI0Hdmk7YGzRB0mXAIaArmODV0CXx/h0PcsDbb/DZzuIs1PQ7EqGWiOqL61E88/Q2vIvmiqD73fzJymOPZh1svpXV18qTjpbKkYe8pBO5njtD1wQ1nZ5jKGoTfhMRUhas5BY5l84FSPC3orAjZVxwKdtoESRi4NkZSA/y14tsTTRHzYd50huSPtrzqicEk4QgIVkC9PEcEBZib4Ifr5NYJNvv/YZdhgdbOF7k3ettr++RT6ONNYcldKrexfovcx3/lgSLvKug==";
		//String str="eZtPb8E6OmC4vPE35do3Xaxmz3li6SCOtMtQ6MUSFqYNCGYGRW7SaCgUsYfttTmR/8z8AcLguAx4IXCPBOEfPYSJVmVcqocHGbGvgZqDy1NqnFJ3M9vpbC1Mi1FAjQKQxLQWFQm4Kbv/lBkNr6lGl/psSo40/37dThAlc4aAFm38aj5s1tEZXUK0MuhJj59B8fqoOpi0DrrXcV5jnbOCiuDFYssWU5dx9Y4iNMIs/z1lsHfz7jUVrhyqS6na2tiSh79JxiRmKXX8jSEnE+MduxSQR1o4iL+wQBcCn2HLFORIh4/HqKJUUS0AuhgVbVwQ8uvriZ93+qGekifXpX+w4GTKxScNCFme13GBUU7Ji6neMBGq1JJ6I1FajvfH3F7uUOcK2Lgd3eV1sC/QdbnJzRgAxwcNHLmgfhr3KG8yZ9k=";
		//msg_signature=5e50cfc94f2f0f64f64cdb425cc323e641038a90 timestamp=1416190315  nonce=456641363 
		
		String signature=cryptUtil.signature("1416190315", "456641363",dest);
		Assert.assertEquals("5e50cfc94f2f0f64f64cdb425cc323e641038a90", signature);
		
		String src=cryptUtil.decrypt(dest);
		System.out.println(src);
		System.out.println("---------------------------------------------------");
		
		String dest2=src=cryptUtil.encrypt("456641363", src);
		src=cryptUtil.decrypt(dest2);
		System.out.println(src);
		
		System.out.println("---------------------------------------------------");
		
		System.out.println("dest="+dest);
		System.out.println("dest2="+dest2);
	}
	
	/*
	 * 接收文本消息
	 */ 
    @Ignore
	public void testGetMsg() throws NoSuchAlgorithmException {
    	WxCryptUtil wcu= this.getWxCryptUtil();
    	
    	String xml="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName><FromUserName><![CDATA[15991890112]]></FromUserName><CreateTime>1416190315</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>4587033601933049922</MsgId><AgentID>10</AgentID></xml>";
    	String secretXml=wcu.encrypt(nonce,xml);
    	
    	String body="<xml><ToUserName><![CDATA[wxb21adacab9c87404]]></ToUserName>\n<Encrypt><![CDATA[";
    	body +=secretXml; 
    	body += "]]></Encrypt>\n<AgentID><![CDATA[10]]></AgentID>\n</xml>";
    	
    	msg_signature=wcu.signature(timestamp, nonce, secretXml);
    	
        String url = String.format("/qy?msg_signature=%1$s&timestamp=%2$s&nonce=%3$s",msg_signature,timestamp,nonce);
        String resp=  use(url).post(body).invoke();
        System.out.println(resp);
    }  
    
    private WxCryptUtil getWxCryptUtil(){
    	WxCryptUtil cryptUtil=null;
    	if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
}
