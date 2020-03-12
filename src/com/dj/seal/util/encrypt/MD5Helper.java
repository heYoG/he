package com.dj.seal.util.encrypt;

import java.security.MessageDigest;

/**
 * MD5工具类
 * 
 * @author tzyh
 * 
 */
public class MD5Helper {

	/**
	 * md5 盐值加密 , 默认32位，生成16位加密字符串
	 * 
	 * @param string
	 * @param salt
	 * @return
	 */
	public String encode16BitBySalt(String string, String salt) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			string = string + salt;
			byte[] btInput = string.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).substring(8, 24);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * MD5 普通加密
	 * 
	 * @param string
	 * @return
	 */

	public String encode(String string) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = string.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J",
			"K", "L", "M", "N", "O","P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z","a","b","c","d","e","f","g","h","i","j","k","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 57;
		int d2 = n % 57;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src
	 *            byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5");
		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String).
	 * 
	 * @param src
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}

	/**
	 * md5 盐值加密 8 bit
	 * 
	 * @param string
	 * @param salt
	 * @return
	 */
	public String encode8BitBySalt(String string, String salt) {
		// char
		// hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};
		string = string + salt;
		String sign = null;
		try {
			sign = md5Digest(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign.substring(0, 16);// 返回16位
	}

}
