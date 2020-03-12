package com.dj.seal.util.encrypt;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ThreeDESUtils {
	static Logger logger = LogManager.getLogger(ThreeDESUtils.class.getName());
	private final static byte[] hex = "0123456789ABCDEF".getBytes();
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用  DES,DESede,Blowfish
    private static final String srcKey = "32FE103B204F5EF868BF19B661F7F820370E4F8C2A92E6BC";										
	

    /**
     * 加密/解密算法/工作模式/填充方式
     * */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 
     * 生成密钥
     * 
     * @return byte[] 二进制密钥
     * */
    public static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(Algorithm);
        // 初始化密钥生成器
        kg.init(168);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        
        byte[] key = secretKey.getEncoded();
        BufferedOutputStream keystream = 
                new BufferedOutputStream(new FileOutputStream("DESedeKey.dat"));
        keystream.write(key, 0, key.length);
        keystream.flush();
        keystream.close();
        
        return key;
    }

	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	// keybyte为加密密钥，长度为24字节
		// src为加密后的缓冲区
		public static byte[] decryptMode(byte[] src) {
			try {
				// 生成密钥
				SecretKey deskey = new SecretKeySpec(HexString2Bytes(srcKey), Algorithm);
				// 解密
				Cipher c1 = Cipher.getInstance(Algorithm);
				c1.init(Cipher.DECRYPT_MODE, deskey);
				return c1.doFinal(src);
			} catch (java.security.NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (javax.crypto.NoSuchPaddingException e2) {
				e2.printStackTrace();
			} catch (java.lang.Exception e3) {
				e3.printStackTrace();
			}
			return null;
		}
	
	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}
	
	// 从十六进制字符串到字节数组转换
	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}
	
	// 从字节数组到十六进制字符串转换
	public static String Bytes2HexString(byte[] b) {
		byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff);
	}

	public static void main(String[] args) throws Exception {
		//final byte[] keyBytes2 = initkey();
		//logger.info("key:" + Bytes2HexString(keyBytes2));
		// 添加新安全算法,如果用JCE就要把它添加进去
		//String str = Bytes2HexString(keyBytes2);
		String str = "32FE103B204F5EF868BF19B661F7F820370E4F8C2A92E6BC";
		
		//logger.info(new String(keyBytes));
		String szSrc = "root";

		logger.info("加密前的字符串:" + szSrc);

		byte[] encoded = encryptMode(HexString2Bytes(str), szSrc.getBytes());
		logger.info("加密后的字符串:" + Bytes2HexString(encoded));

		byte[] srcBytes = decryptMode(HexString2Bytes(str), encoded);
		logger.info("解密后的字符串:" + (new String(srcBytes)));
	}

}
