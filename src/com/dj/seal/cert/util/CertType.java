package com.dj.seal.cert.util;
/**
 * 用于枚举证书来源类型
 * @author wj
 *
 */
public class CertType {
	
	public static String clientCert="clientCert";//客户端公钥证书(clientCert)
	public static String clientPFX="clientPFX";//客户端pfx证书(clientPFX)
	public static String server="server";//服务器文件证书
	public static String sign="sign";//签名服务器证书
	public static String IEPFX="iepfx";//IE证书
}
