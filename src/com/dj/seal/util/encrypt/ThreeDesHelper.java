package com.dj.seal.util.encrypt;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.net.util.Base64;

public class ThreeDesHelper {
	/** 
     * @param args在java中调用sun公司提供的3DES加密解密算法时，需要使 
     * 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包： 
     *jce.jar 
     *security/US_export_policy.jar 
     *security/local_policy.jar 
     *ext/sunjce_provider.jar  
     */  
      
    private static final String Algorithm = "DESede"; //定义加密算法,可用 DES,DESede,Blowfish  
    //keybyte为加密密钥，长度为24字节      
    //src为被加密的数据缓冲区（源）  
    public static byte[] encryptMode(byte[] keybyte,byte[] src){  
         try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //加密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            return c1.doFinal(src);//在单一方面的加密或解密  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            // TODO: handle exception  
             e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
        return null;  
    }  
      
    //keybyte为加密密钥，长度为24字节      
    //src为加密后的缓冲区  
    public static byte[] decryptMode(byte[] keybyte,byte[] src){  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);  
            //解密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            return c1.doFinal(src);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            // TODO: handle exception  
            e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
        return null;          
    }  
      
    //转换成十六进制字符串  
    public static String byte2Hex(byte[] b){  
        String hs="";  
        String stmp="";  
        for(int n=0; n<b.length; n++){  
            stmp = (java.lang.Integer.toHexString(b[n]& 0XFF));  
            if(stmp.length()==1){  
                hs = hs + "0" + stmp;                 
            }else{  
                hs = hs + stmp;  
            }  
            if(n<b.length-1)hs=hs+":";  
        }  
        return hs.toUpperCase();          
    }  
    
    public static byte[] hex(String key){  
        String f = DigestUtils.md5Hex(key);  
        byte[] bkeys = new String(f).getBytes();  
        byte[] enk = new byte[24];  
        for (int i=0;i<24;i++){  
            enk[i] = bkeys[i];  
        }  
        return enk;  
    }  
    
    
    /**
     * 
     * @param encryptStr 待加密的字符串
     * @return
     */
    public String encrypt(String encryptStr){
    	 //添加新安全算法,如果用JCE就要把它添加进去  
        byte[] enk = hex("GZNSH");//用户名  
        Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
        byte[] encoded = encryptMode(enk,encryptStr.getBytes());  
//        String encodedStr = Base64.encodeBase64String(encoded);
        String encodedStr = Base64.encodeToString(encoded);

        return encodedStr;
    }
    
    /**
     * 
     * @param encodedStr
     * @return
     */
    public String decrypt(String encodedStr){
    	byte[] enk = hex("GZNSH");//用户名  
        Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
//    	byte[] reqPassword = Base64.decodeBase64(encodedStr);
    	byte[] reqPassword = Base64.decode(encodedStr);
        byte[] srcBytes = decryptMode(enk,reqPassword);  
        return new String(srcBytes); 
    }
    
}
