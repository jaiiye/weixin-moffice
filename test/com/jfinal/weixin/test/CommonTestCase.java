package com.jfinal.weixin.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.jfinal.weixin.demo.WeixinConfig;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.kit.WxCryptUtil;
import com.jfinal.weixin.test.util.MockServletContext;

public class CommonTestCase extends TestCase<WeixinConfig> {
	String timestamp="1416187783";
	String nonce="1766739666";
	String msg_signature="c4eb00b041686352e79086a41431eebe60b682c0";
	String echostr="PPlWjtSU58hBx/LjqqC+sGp3vYw3pvj+s5piC9IcwLrXMoATImSOX9+0Mo8eCW80UDEEUOX21WWVHZf6dtNfQw==";
	/*
	 * 获取加密工具
	 */
	private WxCryptUtil getWxCryptUtil(){
    	WxCryptUtil cryptUtil=null;
    	if(cryptUtil==null){
			cryptUtil=new WxCryptUtil(ApiConfig.getToken(),ApiConfig.getEncodingAESKey(),ApiConfig.getAppId());
		}
		return cryptUtil;
	}
	/*
	 * 测试验证
	 * 1.目前用WxCryptUtil加密的结果和微信的结果不一致，但能验证通过，奇怪！
	 * 2.增加验证正确log提示；
	 */
	@Test 
    public void testYanZheng() {
        String url = "/qy?msg_signature="+msg_signature;
        url+="&timestamp="+timestamp+"&nonce="+nonce+"&echostr="+echostr;
        
        String resp=  use(url).post("").invoke();
        Assert.assertEquals("3813775250553528279",resp);
    }  
	/*
	 * 测试签名
	 */
	@Test
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
	@Test
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
	 * 通过code获得用户
	 */
	@Test
	public void getUserByCode(){
		String code="jvu__HxiuuuHeDA04xWpGdYCTmUq2kvV827iKWWnZXD3SvIxTkVarNsXgbIra3YisecAZjLRWVD-1YmKKakn_A";
		ApiResult ret=UserApi.getUserByCode("10", code);
		System.out.println(ret.getJson()+":"+ret.get("UserId"));
	}
	
	@SuppressWarnings("rawtypes")
	@Ignore
	public void getPath(){
		Class cls =MockServletContext.class;
		System.out.println(cls.getResource("").getFile());
		System.out.println(cls.getResource(".").getFile());
		System.out.println(cls.getResource("/").getFile());
	}
}
