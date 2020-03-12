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
	 * ǩ��
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */
	public static String sign(String data, KeyStore store, String psd,
			String alg) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		Signature signer = Signature.getInstance(alg);// ǩ������
		signer.initSign(priKey);// ����˽Կ
		signer.update(data.getBytes());// ������Ҫǩ��������
		byte[] signedData = signer.sign();
		return Base64.encodeToString(signedData);
	}
	/**
	 * ǩ��,ԭ����base64
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */
	public static String signByBase64Data(String base64, KeyStore store, String psd,
			String alg) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		Signature signer = Signature.getInstance(alg);// ǩ������
		signer.initSign(priKey);// ����˽Կ
		byte[] data=Base64.decode(base64);
		signer.update(data);// ������Ҫǩ��������
		byte[] signedData = signer.sign();
		return Base64.encodeToString(signedData);
	}
	/**
	 * ǩ��,ԭ����base64
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */
	public static String signByBase64Data(String base64, KeyStore store, String psd) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		String alg = cert.getSigAlgName();
		return signByBase64Data(base64, store, psd, alg);
	}
	/**
	 * ǩ��
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */
	public static String sign(String data, KeyStore store, String psd)
			throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		String alg = cert.getSigAlgName();
		return sign(data, store, psd, alg);
	}

	/**
	 * ǩ������pkcs7�ṹ����
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7(String data, KeyStore store, String psd)
			throws Exception {
		return signP7(data, store, psd,
				CMSSignedGenerator.DIGEST_SHA1);
	}
	/**
	 * ǩ������pkcs7�ṹ����,ԭ����base64 ���byte
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7ByBase64Data(String base64, KeyStore store, String psd)
			throws Exception {
		return signP7ByBase64Data(base64, store, psd,
				CMSSignedGenerator.DIGEST_SHA1,true);
	}
	/**
	 * ǩ������pkcs7�ṹ����,ԭ����base64 ���ܳ�byte
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7DettachByBase64Data(String base64, KeyStore store, String psd)
			throws Exception {
		return signP7ByBase64Data(base64, store, psd,
				CMSSignedGenerator.DIGEST_SHA1,false);
	}
	/**
	 * ǩ������pkcs7�ṹ����
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7Dettach(String data, KeyStore store, String psd)
			throws Exception {
		return signP7Dettach(data, store, psd,
				CMSSignedGenerator.DIGEST_SHA1);
	}
	/**
	 * ǩ������pkcs7�ṹ����
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7(String data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// ����˽Կ
		Certificate[] certs = store.getCertificateChain(alias);// �õ�֤����
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// ����֤����
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, true);// false,����ԭʼ���ݣ����ᴫ�为��
		// ������ժҪ����ǩ��
		// MessageDigest md = MessageDigest.getInstance("MD5",
		// new BouncyCastleProvider());
		// md.update(data.getBytes());
		// byte[] digestedData1 = md.digest();
		// sigOut.write(digestedData1);// ��ԭʼ���ݵ�ժҪ����ǩ��
		// �����ݱ������ǩ��
		sigOut.write(data.getBytes("gbk"));// ��ԭʼ���ݽ���ǩ��
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// ǩ��������
		bOut.close();
		return Base64.encodeToString(signedData);
	}
	/**
	 * ǩ������pkcs7�ṹ����,ԭ����base64��ת��byte[]
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @param attach
	 *            attach true   detach  false
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7ByBase64Data(String base64, KeyStore store, String psd,
			String alg,boolean attach) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// ����˽Կ
		Certificate[] certs = store.getCertificateChain(alias);// �õ�֤����
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// ����֤����
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, attach);// false,����ԭʼ���ݣ����ᴫ�为��
		byte[] data=Base64.decode(base64);
		 //������ժҪ����ǩ��
//		 MessageDigest md = MessageDigest.getInstance("sha1",
//		 new BouncyCastleProvider());
//		 md.update(data);
//		 byte[] digestedData1 = md.digest();
//		 sigOut.write(digestedData1);// ��ԭʼ���ݵ�ժҪ����ǩ��
		// �����ݱ������ǩ��
		sigOut.write(data);// ��ԭʼ���ݽ���ǩ��
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// ǩ��������
		bOut.close();
		return Base64.encodeToString(signedData);
	}
	/**
	 * ǩ������pkcs7�ṹ����
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7Dettach(String data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// ����˽Կ
		Certificate[] certs = store.getCertificateChain(alias);// �õ�֤����
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// ����֤����
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, false);// false,����ԭʼ���ݣ����ᴫ�为��
		// ������ժҪ����ǩ��
		// MessageDigest md = MessageDigest.getInstance("MD5",
		// new BouncyCastleProvider());
		// md.update(data.getBytes());
		// byte[] digestedData1 = md.digest();
		// sigOut.write(digestedData1);// ��ԭʼ���ݵ�ժҪ����ǩ��
		// �����ݱ������ǩ��
		sigOut.write(data.getBytes("gbk"));// ��ԭʼ���ݽ���ǩ��
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// ǩ��������
		bOut.close();
		return Base64.encodeToString(signedData);
	}

	/**
	 * ǩ������pkcs7�ṹ����
	 * 
	 * @param data
	 *            ��ǩ������
	 * @param store
	 *            ֤���
	 * @param psd
	 *            ����
	 * @param alg
	 *            �㷨����
	 * @return ǩ�������base64ֵ
	 * @throws Exception
	 */

	public static String signP7(byte[] data, KeyStore store, String psd,
			String alg) throws Exception {
		CMSSignedDataStreamGenerator gen = new CMSSignedDataStreamGenerator();
		String alias = store.aliases().nextElement();// ֤�����
		PrivateKey priKey = (PrivateKey) store.getKey(alias, psd.toCharArray());// ˽Կ
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		gen.addSigner(priKey, cert, alg, new BouncyCastleProvider());// ����˽Կ
		Certificate[] certs = store.getCertificateChain(alias);// �õ�֤����
		CertStore certstore = CertStore.getInstance("Collection",
				new CollectionCertStoreParameters(Arrays.asList(certs)),
				new BouncyCastleProvider());
		gen.addCertificatesAndCRLs(certstore);// ����֤����
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();// "e:\JavaSignedData.txt"
		OutputStream sigOut = gen.open(bOut, true);// false,����ԭʼ���ݣ����ᴫ�为��
		// ������ժҪ����ǩ��
		MessageDigest md = MessageDigest.getInstance("SHA1",// MD5,SHA1
				new BouncyCastleProvider());
		md.update(data);
		byte[] digestedData1 = md.digest();
		sigOut.write(digestedData1);// ��ԭʼ���ݵ�ժҪ����ǩ��
		// �����ݱ������ǩ��
		// sigOut.write(data);// ��ԭʼ���ݽ���ǩ��
		sigOut.close();
		byte[] signedData = bOut.toByteArray();// ǩ��������
		bOut.close();
		String ret = Base64.encodeToString(signedData);
		ret = ret.replaceAll("\r\n", "");
		return ret;
	}

	/**
	 * ��֤
	 * 
	 * @param data
	 *            ��������
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @param keyStore
	 *            ֤���
	 * @param alg
	 *            ��֤�㷨
	 * @return ��֤���
	 * @throws Exception
	 */
	public static boolean verify(String data, String signedData,
			KeyStore store, String alg) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		Signature sign = Signature.getInstance(alg, new BouncyCastleProvider());
		sign.initVerify(cert.getPublicKey());
		sign.update(data.getBytes());
		// sign.update((byte)0);//����һ��null byte��
		return sign.verify(Base64.decode(signedData));
	}

	/**
	 * ��֤
	 * 
	 * @param data
	 *            ��������
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @param store
	 *            ֤���
	 * @return ��֤���
	 * @throws Exception
	 */
	public static boolean verify(String data, String signedData, KeyStore store)
			throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		String alg = cert.getSigAlgName();
		return verify(data, signedData, store, alg);
	}

	/**
	 * ��֤pkcs7��ʽ��ǩ��ֵ
	 * 
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @return ��֤���
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
			X509Certificate cert = (X509Certificate) certIt.next();// ֤����????
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
	 * ��֤pkcs7��ʽ��ǩ��ֵ
	 * 
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @return ��֤���
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData, KeyStore store)
			throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		return verifyP7ByCert(signedData, cert);
	}

	/**
	 * ��֤pkcs7��ʽ��ǩ��ֵ
	 * 
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @param checkData
	 *            ��������
	 * @return ��֤���
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData,
			String checkData, KeyStore store) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * ��֤pkcs7��ʽ��ǩ��ֵ
	 * 
	 * @param signedData
	 *            ǩ�����ݵ�base64ֵ
	 * @param checkData
	 *            ��������
	 * @return ��֤���
	 * @throws Exception
	 */

	public static boolean verifyP7ByKeyStore(String signedData,
			byte[] checkData, KeyStore store) throws Exception {
		String alias = store.aliases().nextElement();// ֤�����
		X509Certificate cert = (X509Certificate) store.getCertificate(alias);// ��Կ
		return verifyP7ByCert(signedData, checkData, cert);
	}

	/**
	 * ��֤pkcs7��ʽ��ǩ�����ݣ����ⲿ�������֤����
	 * 
	 * @param signedData
	 *            pkcs7��ʽ��ǩ������
	 * @param signData
	 *            ����֤����
	 * @return ��֤���
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
			// logger.info("ǩ��ֵ��" + new String(content));
			// logger.info("��ǩֵ��" + new String(digestedData1));
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
	 * ��֤pkcs7��ʽ��ǩ�����ݣ����ⲿ�������֤����
	 * 
	 * @param signedData
	 *            pkcs7��ʽ��ǩ������
	 * @param signData
	 *            ����֤����
	 * @return ��֤���
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
			// logger.info("ǩ��ֵ��" + new String(content));
			// logger.info("��ǩֵ��" + new String(digestedData1));
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
	 * ����֤�鹫Կ��Ϣ��֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param certData
	 *            ֤�鹫Կ��Ϣ
	 * @return ��֤���
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
	 * ����֤�鹫Կ��Ϣ��֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param certData
	 *            ֤�鹫Կ��Ϣ
	 * @return ��֤���
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
	 * ����֤�鹫Կ��Ϣ��֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param certData
	 *            ֤�鹫Կ��Ϣ
	 * @return ��֤���
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
	 * ����֤��·����֤ǩ����Ϣ
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param certPath
	 *            cer֤��·��
	 * @return ��֤���
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
	 * ����֤��·����֤ǩ����Ϣ
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param certPath
	 *            cer֤��·��
	 * @return ��֤���
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
	 * ����֤��·����֤ǩ����Ϣ
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param certPath
	 *            cer֤��·��
	 * @return ��֤���
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
	 * ����֤����֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param cert
	 *            ֤�鹫Կ
	 * @return ��֤���
	 * @throws Exception
	 */
	public static boolean verifyP7ByCert(String signedData, X509Certificate cert)
			throws Exception {
		try {

			// logger.info("���֤����Ϣ:\n"+cert.toString());

			// logger.info("�汾��:"+cert.getVersion());
			// logger.info("���к�:"+cert.getSerialNumber().toString(16));
			// logger.info("��������"+cert.getSubjectDN());
			// logger.info("ǩ���ߣ�"+cert.getIssuerDN());
			// logger.info("��Ч�ڣ�"+cert.getNotBefore());
			// logger.info("ǩ���㷨��"+cert.getSigAlgName());
			// byte [] sig=cert.getSignature();//ǩ��ֵ
			// logger.info("ǩ��ֵ��"+Base64.encodeToString(sig));
			// PublicKey pk=cert.getPublicKey();
			// byte [] pkenc=pk.getEncoded();
			// logger.info("��Կ:"+Base64.encodeToString(pkenc));

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
	 * ����֤����֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param cert
	 *            ֤�鹫Կ
	 * @return ��֤���
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
			// logger.info("ǩ��ֵ��" + new String(content));
			// logger.info("��ǩֵ��" + new String(digestedData1));
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
	 * ����֤����֤
	 * 
	 * @param signedData
	 *            ǩ������
	 * @param checkData
	 *            ��������
	 * @param cert
	 *            ֤�鹫Կ
	 * @return ��֤���
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
			// logger.info("ǩ��ֵ��" + new String(content));
			// logger.info("��ǩֵ��" + new String(digestedData1));
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
