package com.dj.sign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataStreamGenerator;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class CertUtil {
	static Logger logger = LogManager.getLogger(CertUtil.class.getName());
	/**
	 * 签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @return 签名结果的base64值
	 * @throws Exception
	 */
	public static String sign(String data, KeyStore store, String psd,
			String alg) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		Signature signer = Signature.getInstance(alg);// 签名工具
		signer.initSign(priKey);// 加载私钥
		signer.update(data.getBytes());// 加载需要签名的数据
		byte[] signedData = signer.sign();
		return Base64.encodeToString(signedData);
	}
	/**
	 * 签名,原文是base64
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @return 签名结果的base64值
	 * @throws Exception
	 */
	public static String signByBase64Data(String base64, KeyStore store, String psd,
			String alg) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		Signature signer = Signature.getInstance(alg);// 签名工具
		signer.initSign(priKey);// 加载私钥
		byte[] data=Base64.decode(base64);
		signer.update(data);// 加载需要签名的数据
		byte[] signedData = signer.sign();
		return Base64.encodeToString(signedData);
	}
	/**
	 * 签名,原文是base64
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */
	public static String signByBase64Data(String base64, KeyStore store, String psd) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		String alg = cert.getSigAlgName();
		return signByBase64Data(base64, store, psd, alg);
	}
	/**
	 * 签名
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */
	public static String sign(String data, KeyStore store, String psd)
			throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		String alg = cert.getSigAlgName();
		return sign(data, store, psd, alg);
	}

	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7(String data, KeyStore store, String psd)
			throws Exception {
		return signP7(data, store, psd,
				CMSSignedGenerator.DIGEST_SHA1);
	}
	/**
	 * 签名生成pkcs7结构数据,原文是base64 结成byte
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7ByBase64Data(String base64, KeyStore store, String psd)
			throws Exception {
		return signP7ByBase64Data(base64, store, psd,
				CMSSignedGenerator.DIGEST_SHA1,true);
	}
	/**
	 * 签名生成pkcs7结构数据,原文是base64 解密成byte
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7DettachByBase64Data(String base64, KeyStore store, String psd)
			throws Exception {
		return signP7ByBase64Data(base64, store, psd,
				CMSSignedGenerator.DIGEST_SHA1,false);
	}
	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7Dettach(String data, KeyStore store, String psd)
			throws Exception {
		return signP7Dettach(data, store, psd,
				CMSSignedGenerator.DIGEST_SHA1);
	}
	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7(String data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// 加入私钥
		Certificate[] certs = store.getCertificateChain(alias);// 得到证书链
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// 加入证书链
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, true);// false,不含原始数据，减轻传输负担
		// 对数据摘要进行签名
		// MessageDigest md = MessageDigest.getInstance("MD5",
		// new BouncyCastleProvider());
		// md.update(data.getBytes());
		// byte[] digestedData1 = md.digest();
		// sigOut.write(digestedData1);// 对原始数据的摘要进行签名
		// 对数据本身进行签名
		sigOut.write(data.getBytes("gbk"));// 对原始数据进行签名
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// 签名后数据
		bOut.close();
		return Base64.encodeToString(signedData);
	}
	/**
	 * 签名生成pkcs7结构数据,原文是base64，转成byte[]
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @param attach
	 *            attach true   detach  false
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7ByBase64Data(String base64, KeyStore store, String psd,
			String alg,boolean attach) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// 加入私钥
		Certificate[] certs = store.getCertificateChain(alias);// 得到证书链
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// 加入证书链
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, attach);// false,不含原始数据，减轻传输负担
		byte[] data=Base64.decode(base64);
		 //对数据摘要进行签名
//		 MessageDigest md = MessageDigest.getInstance("sha1",
//		 new BouncyCastleProvider());
//		 md.update(data);
//		 byte[] digestedData1 = md.digest();
//		 sigOut.write(digestedData1);// 对原始数据的摘要进行签名
		// 对数据本身进行签名
		sigOut.write(data);// 对原始数据进行签名
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// 签名后数据
		bOut.close();
		return Base64.encodeToString(signedData);
	}
	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7Dettach(String data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// 加入私钥
		Certificate[] certs = store.getCertificateChain(alias);// 得到证书链
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// 加入证书链
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, false);// false,不含原始数据，减轻传输负担
		// 对数据摘要进行签名
		// MessageDigest md = MessageDigest.getInstance("MD5",
		// new BouncyCastleProvider());
		// md.update(data.getBytes());
		// byte[] digestedData1 = md.digest();
		// sigOut.write(digestedData1);// 对原始数据的摘要进行签名
		// 对数据本身进行签名
		sigOut.write(data.getBytes("gbk"));// 对原始数据进行签名
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// 签名后数据
		bOut.close();
		return Base64.encodeToString(signedData);
	}

	/**
	 * 签名生成pkcs7结构数据
	 * 
	 * @param data
	 *            待签名数据
	 * @param store
	 *            证书库
	 * @param psd
	 *            密码
	 * @param alg
	 *            算法名称
	 * @return 签名结果的base64值
	 * @throws Exception
	 */

	public static String signP7(byte[] data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// 证书别名
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// 私钥
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// 加入私钥
		Certificate[] certs = store.getCertificateChain(alias);// 得到证书链
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// 加入证书链
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, true);// false,不含原始数据，减轻传输负担
		// 对数据摘要进行签名
		MessageDigest md = MessageDigest.getInstance("SHA1",// MD5,SHA1
				new BouncyCastleProvider());
		md.update(data);
		byte[] digestedData1 = md.digest();
		sigOut.write(digestedData1);// 对原始数据的摘要进行签名
		// 对数据本身进行签名
		// sigOut.write(data);// 对原始数据进行签名
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// 签名后数据
		bOut.close();
		String ret = Base64.encodeToString(signedData);
		ret = ret.replaceAll("\r\n", "");
		return ret;
	}

	/**
	 * 验证
	 * 
	 * @param data
	 *            待验数据
	 * @param signedData
	 *            签名数据的base64值
	 * @param keyStore
	 *            证书库
	 * @param alg
	 *            验证算法
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verify(String data, String signedData,
			KeyStore store, String alg) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		Signature sign = Signature.getInstance(alg, new BouncyCastleProvider());
		sign.initVerify(cert.getPublicKey());
		sign.update(data.getBytes());
		// sign.update((byte)0);//增加一个null byte。
		return sign.verify(Base64.decode(signedData));
	}

	/**
	 * 验证
	 * 
	 * @param data
	 *            待验数据
	 * @param signedData
	 *            签名数据的base64值
	 * @param store
	 *            证书库
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verify(String data, String signedData, KeyStore store)
			throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		String alg = cert.getSigAlgName();
		return verify(data, signedData, store, alg);
	}

	/**
	 * 验证pkcs7格式的签名值
	 * 
	 * @param signedData
	 *            签名数据的base64值
	 * @return 验证结果
	 * @throws Exception
	 */

	public static boolean verifyP7(String signedData) throws Exception {
		try {
			CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
			SignerInformationStore signers = sign.getSignerInfos();
			SignerInformation signer = (SignerInformation) signers.getSigners()
					.iterator().next();
			CertStore certs = sign.getCertificatesAndCRLs("Collection",
					new BouncyCastleProvider());
			Collection certCollection = certs.getCertificates(signer.getSID());
			Iterator certIt = certCollection.iterator();
			X509Certificate cert = (X509Certificate) certIt.next();// 证书链????
			// logger.info(signer.getDigestAlgOID());
			// logger.info(signer.getEncryptionAlgOID());
			boolean bl = signer.verify(cert.getPublicKey(),
					new BouncyCastleProvider());
//			byte[] b = signer.getContentDigest();
//			for (byte c : b) {
//				System.out.print(c + "\t");
//			}
			return bl;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 验证pkcs7格式的签名值
	 * 
	 * @param signedData
	 *            签名数据的base64值
	 * @return 验证结果
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData, KeyStore store)
			throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		return verifyP7ByCert(signedData, cert);
	}

	/**
	 * 验证pkcs7格式的签名值
	 * 
	 * @param signedData
	 *            签名数据的base64值
	 * @param checkData
	 *            待验数据
	 * @return 验证结果
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData,
			String checkData, KeyStore store) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 验证pkcs7格式的签名值
	 * 
	 * @param signedData
	 *            签名数据的base64值
	 * @param checkData
	 *            待验数据
	 * @return 验证结果
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData,
			byte[] checkData, KeyStore store) throws Exception {
		String alias = store.aliases().nextElement();// 证书别名
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// 公钥
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 验证pkcs7格式的签名数据，从外部传入待验证数据
	 * 
	 * @param signedData
	 *            pkcs7格式的签名数据
	 * @param signData
	 *            待验证数据
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7(String signedData, String signData)
			throws Exception {
		CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
		if (verifyP7(signedData)) {
//			MessageDigest md = MessageDigest.getInstance("SHA1",
//					new BouncyCastleProvider());
//			md.update(signData.getBytes("gbk"));
//			byte[] digestedData1 = md.digest();
			byte[] digestedData1=signData.getBytes("gbk");
			// byte[] digestedData1 = signData.getBytes();
			byte[] content = (byte[]) sign.getSignedContent().getContent();
			// logger.info("签名值：" + new String(content));
			// logger.info("验签值：" + new String(digestedData1));
			if (content.length != digestedData1.length)
				return false;
			for (int i = 0; i < content.length; i++) {
				if (digestedData1[i] != content[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证pkcs7格式的签名数据，从外部传入待验证数据
	 * 
	 * @param signedData
	 *            pkcs7格式的签名数据
	 * @param signData
	 *            待验证数据
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7(String signedData, byte[] signData)
			throws Exception {
		CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
		if (verifyP7(signedData)) {
			 MessageDigest md = MessageDigest.getInstance("SHA1",
			 new BouncyCastleProvider());
			 md.update(signData);
			 byte[] digestedData1 = md.digest();
			
//			byte[] digestedData1 = signData;
			byte[] content = (byte[]) sign.getSignedContent().getContent();
			// logger.info("签名值：" + new String(content));
			// logger.info("验签值：" + new String(digestedData1));
			if (content.length != digestedData1.length)
				return false;
			for (int i = 0; i < content.length; i++) {
				if (digestedData1[i] != content[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据证书公钥信息验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param certData
	 *            证书公钥信息
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertBase64(String signedData,
			String certData) throws Exception {
		InputStream in = new ByteArrayInputStream(Base64.decode(certData));
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, cert);
	}

	/**
	 * 根据证书公钥信息验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param certData
	 *            证书公钥信息
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertBase64(String signedData,
			String checkData, String certData) throws Exception {
		InputStream in = new ByteArrayInputStream(Base64.decode(certData));
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 根据证书公钥信息验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param certData
	 *            证书公钥信息
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertBase64(String signedData,
			byte[] checkData, String certData) throws Exception {
		InputStream in = new ByteArrayInputStream(Base64.decode(certData));
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 根据证书路径验证签名信息
	 * 
	 * @param signedData
	 *            签名数据
	 * @param certPath
	 *            cer证书路径
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertPath(String signedData, String certPath)
			throws Exception {
		InputStream in = new FileInputStream(certPath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, cert);
	}

	/**
	 * 根据证书路径验证签名信息
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param certPath
	 *            cer证书路径
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertPath(String signedData,
			String checkData, String certPath) throws Exception {
		InputStream in = new FileInputStream(certPath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 根据证书路径验证签名信息
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param certPath
	 *            cer证书路径
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCertPath(String signedData,
			byte[] checkData, String certPath) throws Exception {
		InputStream in = new FileInputStream(certPath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * 根据证书验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param cert
	 *            证书公钥
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCert(String signedData, X509Certificate cert)
			throws Exception {
		try {

			// logger.info("输出证书信息:\n"+cert.toString());

			// logger.info("版本号:"+cert.getVersion());
			// logger.info("序列号:"+cert.getSerialNumber().toString(16));
			// logger.info("主体名："+cert.getSubjectDN());
			// logger.info("签发者："+cert.getIssuerDN());
			// logger.info("有效期："+cert.getNotBefore());
			// logger.info("签名算法："+cert.getSigAlgName());
			// byte [] sig=cert.getSignature();//签名值
			// logger.info("签名值："+Base64.encodeToString(sig));
			// PublicKey pk=cert.getPublicKey();
			// byte [] pkenc=pk.getEncoded();
			// logger.info("公钥:"+Base64.encodeToString(pkenc));

			CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
			SignerInformationStore signers = sign.getSignerInfos();
			SignerInformation signer = (SignerInformation) signers.getSigners()
					.iterator().next();
			boolean bl = signer.verify(cert.getPublicKey(),
					new BouncyCastleProvider());
			// byte[] b = signer.getContentDigest();
			// for (byte c : b) {
			// System.out.print(c + "\t");
			// }
			return bl;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 根据证书验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param cert
	 *            证书公钥
	 * @return 验证结果
	 * @throws Exception
	 */

	public static boolean verifyP7ByCert(String signedData, String checkData,
			X509Certificate cert) throws Exception {
		CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
		if (verifyP7ByCert(signedData, cert)) {
			MessageDigest md = MessageDigest.getInstance("SHA1",
					new BouncyCastleProvider());
			md.update(checkData.getBytes());
			byte[] digestedData1 = md.digest();

			// byte[] digestedData1 = checkData.getBytes();
			byte[] content = (byte[]) sign.getSignedContent().getContent();
			// logger.info("签名值：" + new String(content));
			// logger.info("验签值：" + new String(digestedData1));
			if (content.length != digestedData1.length)
				return false;
			for (int i = 0; i < content.length; i++) {
				if (digestedData1[i] != content[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据证书验证
	 * 
	 * @param signedData
	 *            签名数据
	 * @param checkData
	 *            待验数据
	 * @param cert
	 *            证书公钥
	 * @return 验证结果
	 * @throws Exception
	 */
	public static boolean verifyP7ByCert(String signedData, byte[] checkData,
			X509Certificate cert) throws Exception {
		CMSSignedData sign = new CMSSignedData(Base64.decode(signedData));
		if (verifyP7ByCert(signedData, cert)) {
			MessageDigest md = MessageDigest.getInstance("SHA1",
					new BouncyCastleProvider());
			md.update(checkData);
			byte[] digestedData1 = md.digest();

			// byte[] digestedData1 = checkData;
			byte[] content = (byte[]) sign.getSignedContent().getContent();
			// logger.info("签名值：" + new String(content));
			// logger.info("验签值：" + new String(digestedData1));
			if (content.length != digestedData1.length)
				return false;
			for (int i = 0; i < content.length; i++) {
				if (digestedData1[i] != content[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
