package com.xpsoft.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 用于密码加密 加密盐必须是 8 bytes
 * 算法密钥说明：
 * 	DES：算法DES要求密钥长度为64位密钥, 有效密钥56位。64bits=8*8*1，即8个ascii字符。
 *	DESede：算法DESede要求的密钥位数为192位，即192bits=64*3=8*8*3，即24个ascii字符。
 *	Blowfish：算法Blowfish要求密钥长度为8--448字位，即8--448(bits)。即：1个到56个ascii字符
 *
 * @Class Name MD5Util
 * @Author likang
 * @Create In Jul 27, 2010
 */
public class MD5Util {
	
	private static String Algorithm = "DES"; // 定义 加密算法,可用 DES,DESede,Blowfish
	
	//DES 算法 8个ascii字符
	private static String key = "中软万维";
	
	/**
	 * 生成密钥, 注意此步骤时间比较长
	 * @Methods Name getKey
	 * @Create In Jul 27, 2010 By likang
	 * @return
	 * @throws Exception byte[]
	 */ 
	private static byte[] getKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 加密方法
	 * @Methods Name encodeMd5
	 * @Create In Jul 27, 2010 By likang
	 * @param inputString
	 * @param keyString
	 * @return
	 * @throws Exception String
	 */
	public static String encodeMd5(String inputString, String keyString) throws Exception {
		byte[] input = inputString.getBytes();
		byte[] key = keyString.getBytes();
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return new String(byte2hex(cipherByte));
	}
	
	
	/**
	 * 加密方法
	 * @Methods Name encodeMd5ForGalaxy
	 * @Create In Jul 27, 2010 By likang
	 * @param inputString
	 * @param keyString
	 * @return
	 * @throws Exception String
	 */
	public static String encodeMd5ForGalaxy(String inputString) throws Exception {
		String keyString = "\u94F6\u6CB3\u8BC1\u5238";
		byte[] input = inputString.getBytes();
		byte[] key = keyString.getBytes();
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return new String(byte2hex(cipherByte));
	}
	
	
	/**
	 * 解密方法
	 * @Methods Name decodeMd5
	 * @Create In Jul 27, 2010 By likang
	 * @param inputString
	 * @param keyString
	 * @return
	 * @throws Exception String
	 */
	public static String decodeMd5ForGalaxy(String inputString) throws Exception {
		String keyString = "\u94F6\u6CB3\u8BC1\u5238";
		byte[] input = hex2byte(inputString);
		byte[] key = keyString.getBytes();
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return new String(clearByte);

	}
	
	/**
	 * 解密方法
	 * @Methods Name decodeMd5
	 * @Create In Jul 27, 2010 By likang
	 * @param inputString
	 * @param keyString
	 * @return
	 * @throws Exception String
	 */
	public static String decodeMd5(String inputString, String keyString) throws Exception {
		byte[] input = hex2byte(inputString);
		byte[] key = keyString.getBytes();
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return new String(clearByte);

	}

	/**
	 * 此加密不可逆
	 * @Methods Name md5
	 * @Create In Jul 27, 2010 By likang
	 * @param input
	 * @return
	 * @throws Exception byte[]
	 */
	private static byte[] md5(byte[] input) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5"); // or "SHA-1"
		alg.update(input);
		byte[] digest = alg.digest();
		return digest;

	}

	/**
	 *  字节码转换成16进制字符串
	 * @Methods Name byte2hex
	 * @Create In Jul 27, 2010 By likang
	 * @param b
	 * @return String
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs;
		}
		return hs.toUpperCase();
	}
	
	
	/**
	 * 16进制字符串转换成字节码
	 * @Methods Name hex2byte
	 * @Create In Jul 27, 2010 By likang
	 * @param h
	 * @return byte[]
	 */
	private static byte[] hex2byte(String h) {
	    byte[] ret = new byte[h.length()/2];
	    for(int i=0; i<ret.length; i++){
	        ret[i] = Integer.decode("#"+h.substring(2*i, 2*i+2)).byteValue();
	    }
	    return ret;
	}
	 
	/**
	 * 用法示例
	 * @Methods Name main
	 * @Create In Jul 27, 2010 By likang
	 * @param args void
	 */
	public static void main(String[] args) {
		try {
			//用户密码
			String password = "aabbcc啊";
			//用于保存到数据库的加密密码
			String afterEncodeString = encodeMd5(password,key);
			//解码后的密码
			String passwordOlder = 	decodeMd5(afterEncodeString,key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 涉及到银河的USER_BASIC_INFO 请使用该方法
	 * @Methods Name encrypt
	 * @Create In Aug 11, 2010 By likang
	 * @param src
	 * @return String
	 */
	public synchronized static String encrypt(String src) {
		src = src.trim();
		String Digest = "";
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
			currentAlgorithm.reset();
			byte[] mess = src.getBytes();
			byte[] hash = currentAlgorithm.digest(mess);
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i];
				if (v < 0)
					v = 256 + v;
				if (v < 16)
					Digest += "0";
				Digest += Integer.toString(v, 16).toUpperCase() + "";
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Digest;
	}

}