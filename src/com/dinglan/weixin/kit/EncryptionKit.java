package com.dinglan.weixin.kit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptionKit {
	 /**
	   * 生成SHA1签名
	   * @param arr
	   * @return
	   */
	  public static String sha1Encrypt(String... arr) throws NoSuchAlgorithmException {
	    Arrays.sort(arr);
	    StringBuilder sb = new StringBuilder();
	    for(String a : arr) {
	      sb.append(a);
	    }

	    MessageDigest sha1 = MessageDigest.getInstance("SHA1");
	    sha1.update(sb.toString().getBytes());
	    byte[] output = sha1.digest();
	    return bytesToHex(output);
	  }


	  private static String bytesToHex(byte[] b) {
	    char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	    StringBuffer buf = new StringBuffer();
	    for (int j = 0; j < b.length; j++) {
	      buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	      buf.append(hexDigit[b[j] & 0x0f]);
	    }
	    return buf.toString();
	  }

	public static String md5Encrypt(String srcStr){
		return encrypt("MD5", srcStr);
	}
	
	public static String sha1Encrypt(String srcStr){
		return encrypt("SHA-1", srcStr);
	}
	
	public static String sha256Encrypt(String srcStr){
		return encrypt("SHA-256", srcStr);
	}
	
	public static String sha384Encrypt(String srcStr){
		return encrypt("SHA-384", srcStr);
	}
	
	public static String sha512Encrypt(String srcStr){
		return encrypt("SHA-512", srcStr);
	}
	
	public static String encrypt(String algorithm, String srcStr) {
		try {
			StringBuilder result = new StringBuilder();
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
			for (byte b :bytes) {
				String hex = Integer.toHexString(b&0xFF);
				if (hex.length() == 1)
					result.append("0");
				result.append(hex);
			}
			return result.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}





