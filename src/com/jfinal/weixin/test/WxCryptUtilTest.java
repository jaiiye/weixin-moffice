package com.jfinal.weixin.test;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.jfinal.weixin.sdk.kit.WxCryptUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import junit.framework.Assert;

public class WxCryptUtilTest {

	String appId = "wx1d06e4106d12ef28";
	String appSecret = "343212d84c7530509ec6ba768e42fceb";
	String token = "oxxI";
	String encodingAesKey = "vuSWZYdBrwWqeFEHZQVM6kFngXaz6AhRXuTwO1b1MCH";
	
	
	String timestamp = "1416038262";
	String nonce = "1437809843";

	String randomStr = "aaaabbbbccccdddd";

	String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
	String replyMsg = "我是中文abcd123";

	String afterAesEncrypt = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";

	String replyMsg2 = "<xml><ToUserName><![CDATA[1212]]></ToUserName><FromUserName><![CDATA[1212]]></FromUserName><CreateTime>1416038263</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA ^_^]]></Content></xml>";
	String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";

	@Test
	public void testEncrpt(){
		WxCryptUtil pc = new WxCryptUtil(token, encodingAesKey, appId);
		String encryptedXml = pc.encrypt(replyMsg2);
		System.out.println(encryptedXml);

	}
	public void testEechostrDncrypt() {
		String echostr = "61uOvaJRYSWd3rmReR5QUTpHo7epx7sAs4WU6D1RpnJx2mpDWjs+b6nTLkdroMNtfI8v3SSEiaByD2yy8x8aTA==";
		WxCryptUtil pc = new WxCryptUtil("oxxI", encodingAesKey,
				"wxb21adacab9c87404");
		String sb = null;
		sb = pc.decrypt(echostr);
		System.out.println(sb);
	}

	public void testNormal() throws ParserConfigurationException, SAXException,
			IOException {
		WxCryptUtil pc = new WxCryptUtil(token, encodingAesKey, appId);
		String encryptedXml = pc.encrypt(replyMsg);

		System.out.println(encryptedXml);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document document = documentBuilder.parse(new InputSource(
				new StringReader(encryptedXml)));

		Element root = document.getDocumentElement();
		String cipherText = root.getElementsByTagName("Encrypt").item(0)
				.getTextContent();
		String msgSignature = root.getElementsByTagName("MsgSignature").item(0)
				.getTextContent();
		String timestamp = root.getElementsByTagName("TimeStamp").item(0)
				.getTextContent();
		String nonce = root.getElementsByTagName("Nonce").item(0)
				.getTextContent();

		String messageText = String.format(xmlFormat, cipherText);

		// 第三方收到企业号平台发送的消息
		String plainMessage = pc.decrypt(cipherText);

		System.out.println(plainMessage);
		Assert.assertEquals(plainMessage, replyMsg);
	}

	public void testAesDncrypt() {

		// msg_signature=7abd244cc75382840b2ae1ffa5e6369d446be816&timestamp=1415958709&nonce=442448040
		// &echostr=61uOvaJRYSWd3rmReR5QUTpHo7epx7sAs4WU6D1RpnJx2mpDWjs%2Bb6nTLkdroMNtfI8v3SSEiaByD2yy8x8aTA%3D%3D
		// corpID=wxb21adacab9c87404
		// corpSecret=Hvix3QfnYy690tvDdsJN7NkoEhj485X98H9Gpn8grU7VUxLOsFaAOGPrx9gTPF0s

		WxCryptUtil pc = new WxCryptUtil("oxxI", encodingAesKey,
				"wxb21adacab9c87404");
		String msgSignature = "7abd244cc75382840b2ae1ffa5e6369d446be816";
		String encryptedXml = "<xml><ToUserName><![CDATA[gh_cb9520809797]]></ToUserName><Encrypt><![CDATA[61uOvaJRYSWd3rmReR5QUTpHo7epx7sAs4WU6D1RpnJx2mpDWjs%2Bb6nTLkdroMNtfI8v3SSEiaByD2yy8x8aTA%3D%3D]]></Encrypt></xml>";

		String sb = null;
		// sb = pc.decrypt(msgSignature, "1415958709", "442448040",
		// encryptedXml);
		sb = pc.decrypt("61uOvaJRYSWd3rmReR5QUTpHo7epx7sAs4WU6D1RpnJx2mpDWjs%2Bb6nTLkdroMNtfI8v3SSEiaByD2yy8x8aTA%3D%3D");

		System.out.println(sb);

		/*
		 * StringReader sr = new StringReader(encryptedXml);
		 * System.out.println(sr);
		 * 
		 * InputSource is = new InputSource(sr); DocumentBuilder db =
		 * DocumentBuilderFactory.newInstance().newDocumentBuilder();
		 * 
		 * Document doc = db.newDocument();
		 * 
		 * System.out.println(doc.getDocumentElement());
		 */

		// Document doc = sb.build(is);

		// String
		// afterAesEncrypt="JB4A1cdIXp4IWH6SYdQ3vGFe+y6tNP9a8LEQysEq8QEv+8LkPbdzrctPd0WM8kdhuvonuzZ+onnlSpNW5YN/BmrKP2J/P90kIMb1d79b8qwwLOm7L39n65u0ZIHAQOBMDiWkQYKbKtKlNnElBvJce512G70bk2CUs7THks29+StNDjAHWCGlYRb3/Xan9LVjjmbzW4fM1jV3lRVb9Gm7i6gE36w9ihgHOdIdlQOh0Ax0rgc6UW8ZCG9TQiCa55ix93iXoZIn3Lk3Fj1MIPkl8iK7fIgKn3BHVI2cImETWROIXU8y6HUu3BfGUizeP5iN3SlgEM6yZCmCwqvFryzBKUx99stDs/+unSUMYxBC0fz8KZ+apXkuoZlGdutkgHQFBhTKp0BVUF7rEd3x8sEkkYiTgxdZFftCLiJm5KRofOc=";
		// String sb= pc.decrypt(afterAesEncrypt2);

	}

	public void testAesEncrypt() {
		WxCryptUtil pc = new WxCryptUtil(token, encodingAesKey, appId);
		// Assert.assertEquals(pc.encrypt(randomStr, replyMsg),
		// afterAesEncrypt);
	}

	public void testAesEncrypt2() {
		WxCryptUtil pc = new WxCryptUtil(token, encodingAesKey, appId);
		// Assert.assertEquals(pc.encrypt(randomStr, replyMsg2),
		// afterAesEncrypt2);
	}

	public void testValidateSignatureError()
			throws ParserConfigurationException, SAXException, IOException {
		try {
			WxCryptUtil pc = new WxCryptUtil(token, encodingAesKey, appId);
			String afterEncrpt = pc.encrypt(replyMsg);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(afterEncrpt);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");

			String encrypt = nodelist1.item(0).getNodeValue();
			String fromXML = String.format(xmlFormat, encrypt);
			pc.decrypt("12345", timestamp, nonce, fromXML); // 这里签名错误
		} catch (RuntimeException e) {
			return;
		}
	}

}
