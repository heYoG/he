package serv;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
public class EncoderAndDecoder {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(EncoderAndDecoder.class.getName());
	  /**
	   * DES加密  以byte[]明文输入,byte[]密文输出
	   * @param MingWen 明文
	   * @param strKey 密钥
	   * @return  密文字符数组
	   */
	public byte[] getEncBytesByDES(byte[] MingWen,String strKey){
		byte[] miWen=null;
		Key key;
		KeyGenerator key_generator;	    
		Cipher cipher;

		try {
			key_generator = KeyGenerator.getInstance("DES");
			key_generator.init(new SecureRandom(strKey.getBytes()));
	        key = key_generator.generateKey();
	        key_generator = null;
	        cipher = Cipher.getInstance("DES");
	       // cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        miWen = cipher.doFinal(MingWen);
	        
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			return null;
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
			return null;
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
			return null;
		}finally{
			 cipher = null;
		}
        
		return miWen;
	}
	
	
	 /**
	   * DES加密  以String明文输入,base64密文输出
	   * @param mingwen 明文
	   * @param strKey 密钥
	   * @return  密文base64字符串
	   */
	public String getEncBase64StringByDES(String mingwen,String strKey){
		String miwen=null;
		byte[] byteMi = null;
	    byte[] byteMing = null;
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing=mingwen.getBytes("utf-8");
	        byteMi=this.getEncBytesByDES(byteMing, strKey);
	        miwen = base64en.encode(byteMi);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
		}finally{
			base64en = null;
	        byteMing = null;
	        byteMi = null;
		}
		
		return miwen;
	}
	
	
	/**
	 * 对文件进行des加密，
	 * @param src  明文文件
	 * @param des 密文文件
	 * @param strKey 密钥 
	 */
	public void getEncFileByDES(File src,File des,String strKey){
		Key key;
		KeyGenerator key_generator;	    
		Cipher cipher;
		try {
			key_generator = KeyGenerator.getInstance("DES");
			key_generator.init(new SecureRandom(strKey.getBytes()));
	        key = key_generator.generateKey();
	        key_generator = null;
	        cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        InputStream is=new FileInputStream(src);
	        OutputStream os=new FileOutputStream(des);
	        CipherInputStream cis=new CipherInputStream(is,cipher);
	        byte[] buffer=new byte[1024];
	        int r;
	        while((r=cis.read(buffer))>0){
	        	os.write(buffer, 0, r);
	        }
	        cis.close();
	        is.close();
	        os.close();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	
	
	  /**
	   * 解密  以byte[]密文输入,以byte[]明文输出
	   * @param miwen  密文
	   * @param strKey  密钥
	   * @return  明文字符数组
	   */
	  public byte[] getDecBytesByDES(byte[] miwen,String strKey) {
		  Key key;
		  KeyGenerator key_generator;	    
		  Cipher cipher;
		  byte[] mingwen=null;
	      try{
	    	key_generator = KeyGenerator.getInstance("DES");
	    	key_generator.init(new SecureRandom(strKey.getBytes()));
	    	key = key_generator.generateKey();
	    	key_generator = null;
	    	cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        //疑问：key与加密的不同时为何会抛异常
	        mingwen = cipher.doFinal(miwen);
	      } catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
				return null;
	      } catch (NoSuchPaddingException e) {
				logger.error(e.getMessage());
				return null;
	      } catch (InvalidKeyException e) {
				logger.error(e.getMessage());
				return null;
	      } catch (IllegalBlockSizeException e) {
				logger.error(e.getMessage());
				return null;
	      } catch (BadPaddingException e) {
				logger.error(e.getMessage());
				return null;
	      }finally{
				 cipher = null;
	      }
	      return mingwen;
	  }

	  
	  /**
	   * DES解密  以base64密文输入,string原字符串明文输出
	   * @param  base64Miwen  base64格式密文
	   * @param  strKey 密钥
	   * @return  明文字符串
	   */
	public String getDecStringByDES(String base64Miwen,String strKey){
		String mingwen=null;
		byte[] byteMi = null;
	    byte[] byteMing = null;
	    BASE64Decoder base64De = new BASE64Decoder();
		try {
			byteMi=base64De.decodeBuffer(base64Miwen);
			byteMing=this.getDecBytesByDES(byteMi, strKey);
			mingwen = new String(byteMing,"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}finally{
			base64De = null;
	        byteMing = null;
	        byteMi = null;
		}
		
		return mingwen;
	}
	
	
	/**
	 * 对文件进行des解密，
	 * @param src  密文文件
	 * @param des  明文文件
	 * @param strKey 密钥 
	 */
	public void getDecFileByDES(File src,File des,String strKey){
		Key key;
		KeyGenerator key_generator;	    
		Cipher cipher;
		try {
			key_generator = KeyGenerator.getInstance("DES");
			key_generator.init(new SecureRandom(strKey.getBytes()));
	        key = key_generator.generateKey();
	        key_generator = null;
	        cipher = Cipher.getInstance("DES");
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        InputStream is=new FileInputStream(src);
	        OutputStream os=new FileOutputStream(des);
	        CipherInputStream cis=new CipherInputStream(is,cipher);
	        byte[] buffer=new byte[1024];
	        int r;
	        while((r=cis.read(buffer))>0){
	        	os.write(buffer, 0, r);
	        }
	        cis.close();
	        is.close();
	        os.close();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
	

}

