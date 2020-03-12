package com.dj.websignServer;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;




import com.dj.sign.Base64;
import com.dj.sign.CertUtil;
import com.dj.websignServer.po.SealData;
import com.dj.websignServer.po.SealDataForVerify;

import decSeal.DecSealUtil;
import sun.misc.BASE64Encoder;

public class DSign {
	static Logger logger = LogManager.getLogger(DSign.class.getName());
	private String errormessge;//错误描述
	private KeyStore keyStore=null;//证书库
	private Map SignDataMap=new HashMap();//印章名，印章数据
	private String oriInSignres;//签章中保护的原始数据
	private List  sealNameList=new ArrayList();//印章名称列表
	private Map SealDataMap=new HashMap();//签章结果
	
	/**
	 * 获取keystore
	 * 
	 * @param pfx_path
	 *            证书路径（pfx格式）
	 * @param pfx_psw
	 *            证书密码（不可为空）
	 * @return 证书库keystore
	 * @throws Exception
	 */
	private KeyStore getKeyStore(String pfx_path,String pfx_psw){
		if(keyStore!=null){
			return keyStore;
		}
		//得到KeyStore实例 
        try {
			keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream is = new FileInputStream(pfx_path); 
	        //从指定的输入流中加载此 KeyStore。 
	        keyStore.load(is, pfx_psw.toCharArray()); 
	        is.close(); 
		} catch (KeyStoreException e) {
			errormessge="无法创建PKCS12格式keystore";
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			errormessge="证书文件不存在";
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			errormessge="证书密码错误";
			logger.error(e.getMessage());
		} catch (CertificateException e) {
			errormessge="读取证书错误";
			logger.error(e.getMessage());
		} catch (IOException e) {
			errormessge="读取证书错误";
			logger.error(e.getMessage());
		} 
		return keyStore;
	}
	/**
	 * 获取keystore
	 * 
	 * @param pfx_path
	 *            证书路径（pfx格式）
	 * @param pfx_psw
	 *            证书密码（不可为空）
	 * @return 证书库keystore
	 * @throws Exception
	 */
	private KeyStore getKeyStore(byte[] pfx,String pfx_psw){
		if(keyStore!=null){
			return keyStore;
		}
		//得到KeyStore实例 
        try {
			keyStore = KeyStore.getInstance("PKCS12");
			ByteArrayInputStream is = new ByteArrayInputStream(pfx); 
	        //从指定的输入流中加载此 KeyStore。 
	        keyStore.load(is, pfx_psw.toCharArray()); 
	        is.close(); 
		} catch (KeyStoreException e) {
			errormessge="无法创建PKCS12格式keystore";
			logger.error(e.getMessage());
		} catch (FileNotFoundException e) {
			errormessge="证书文件不存在";
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			errormessge="证书密码错误";
			logger.error(e.getMessage());
		} catch (CertificateException e) {
			errormessge="读取证书错误";
			logger.error(e.getMessage());
		} catch (IOException e) {
			errormessge="读取证书错误";
			logger.error(e.getMessage());
		} 
		return keyStore;
	}
	/**
	 * 获取证书序列号带空格分隔的字符串格式
	 * 
	 * @param oridata
	 *            转换前字符串
	 * @param len
	 *            分隔长度
	 * @param hex
	 *            插入字符
	 * @return 转换后字符串
	 * @throws Exception
	 */
	private String getStringAddSplit(String oridata,int len,String hex){
		StringBuffer certno=new StringBuffer();
		int length=oridata.length();//原始串长度
		int mod=length%len;//余数
		int num;//安装len分段应该分为几段为循环依据。
		num=length/len;
		if(mod==0){
			int i;
			for(i=0;i<num-1;i++){
				certno.append(oridata.substring(i*len, (i+1)*len));
				certno.append(hex);
			}
			certno.append(oridata.substring(i*len, (i+1)*len));
		}else{
			int i;
			for(i=0;i<num;i++){
				certno.append(oridata.substring(i*len, (i+1)*len));
				certno.append(hex);
			}
			certno.append(oridata.substring(i*len, length));
		}
		return certno.toString();
	}
	
	/**
	 * 获取证书序列号
	 * 
	 * @param keyStore
	 *            证书库
	 * @return 以空格分隔的16进制证书序列号
	 * @throws Exception
	 */
	private String getCertNo(KeyStore keyStore){
		String alias;
		String certno=null;
		try {
			alias = keyStore.aliases().nextElement();
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
			BigInteger serial = cert.getSerialNumber();
			String serial16=serial.toString(16);
			certno=getStringAddSplit(serial16, 4, " ");
		} catch (KeyStoreException e) {
			errormessge="读取证书序列号错误";
			logger.error(e.getMessage());
		} 
		return certno;
	}
	
	/**
	 * 获取证书使用者名称
	 * 
	 * @param keyStore
	 *            证书库
	 * @return 证书使用者名称
	 * @throws Exception
	 */
	private String getCertName(KeyStore keyStore){
		 String alias;
		 String user=null;
		try {
			alias = keyStore.aliases().nextElement();
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
			String subjectdn=cert.getSubjectDN().getName();
			String[] subjs=subjectdn.split(", ");
			Map map=new HashMap();
			for(int i=0;i<subjs.length;i++){
				String[] temp=subjs[i].split("=");
				map.put(temp[0], temp[1]);
			}
			user=(String)map.get("CN");
		} catch (KeyStoreException e) {
			errormessge="读取证书使用者错误";
			logger.error(e.getMessage());
		} 
	    
		return user;
	}
	/**
	 * 获取印章数据
	 * 
	 * @param seal_path
	 *            印章路径
	 * @return 印章base64数据
	 * @throws Exception
	 */
	private String getSealData(String seal_path){
		 String sealdata=null;;
		try {
			FileInputStream is=new FileInputStream(seal_path);
			byte[] seal=new byte[is.available()];
			is.read(seal);
			is.close();
			sealdata=Base64.encodeToString(seal);
		}catch (FileNotFoundException e) {
			errormessge="读取印章文件出错请确认印章文件是否存在";
			logger.error(e.getMessage());
		} catch (IOException e) {
			errormessge="读取印章文件出错，i/o异常";
			logger.error(e.getMessage());
		} 
	    
		return sealdata;
	}
	//盖章的基本方法
	private String getSealRes(KeyStore keyStore,String pfx_psw,String seal_data,String seal_id,String seal_name,String seal_position,String seal_x,String seal_y,String oriData){
		String sealRes=null;
		String cert_id=getCertNo(keyStore);//证书序列号（组成印章数据需要）
		if(cert_id==null){return null;}
		String user_id=getCertName(keyStore);//证书使用者（组成印章数据需要）
		if(user_id==null){return null;}
		MessageDigest digest;
		String oriData_sha1="";
		String signRes="";
		//String seal_data=getSealData(seal_path);
		if(seal_data==null||seal_data.equals("")){
			errormessge="无印章数据";
			return null;
		}
		try {
			digest = MessageDigest.getInstance("sha-1");
			digest.update(oriData.getBytes("gbk"));//更新数据
			byte[] encoder=digest.digest();
			oriData_sha1=new BASE64Encoder().encode(encoder);
			CertUtil util=new CertUtil();
			signRes=CertUtil.signP7(oriData_sha1, keyStore, pfx_psw);
		} catch (NoSuchAlgorithmException e) {
			errormessge="原始数据进行散列时出错，不支持sha-1算法";
			logger.error(e.getMessage());
			return null;
		} catch (UnsupportedEncodingException e) {
			errormessge="原始数据进行散列时出错，不支持gbk编码";
			logger.error(e.getMessage());
			return null;
		} catch (Exception e) {
			errormessge="获取p7签名出错";
			logger.error(e.getMessage());
			return null;
		}
		/*获取UTC时间开始*/ 
	    final java.util.Calendar cal = java.util.Calendar.getInstance();  
	    final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);   
	    final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET); 
	    cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
		/*获取UTC时间结束*/
		    
		SimpleDateFormat format=new SimpleDateFormat("yyyymmddhh24mmss");
		String seal_time=format.format(cal.getTime());
		StringBuffer sb=new StringBuffer();
		sb.append("WebSignDataBegin::");
		sb.append("cert_id:");
		sb.append(cert_id+";");
		sb.append("seal_x:");
		sb.append(seal_x+";");
		sb.append("seal_y:");
		sb.append(seal_y+";");
		sb.append("user_id:");
		sb.append(user_id+";");
		sb.append("oriData_sha1:");
		sb.append(oriData_sha1+";");
		sb.append("signRes:");
		sb.append(signRes+";");
		sb.append("seal_id:");
		sb.append(seal_id+";");
		sb.append("seal_data:");
		sb.append(seal_data+";");
		sb.append("seal_name:");
		sb.append(seal_name+";");
		sb.append("seal_position:");
		sb.append(seal_position+";");
		sb.append("seal_time:");
		sb.append(seal_time+";");
		sb.append("::WebSignDataEnd;");
		sealRes=sb.toString();
		sealRes=sealRes.replaceAll("\r\n","");
		sealRes=sealRes.replaceAll("\r","");
		sealRes=sealRes.replaceAll("\n","");
		return sealRes;
	}
	/**
	 * 服务器加盖印章
	 * 
	 * @param pfx_path
	 *            证书路径
	 * @param pfx_psw
	 *            证书密码
	 * @param seal_data
	 *            印章数据
	 * @param seal_id
	 *            印章id
	 * @param seal_name
	 *            盖章后唯一名称，用于当显示页面中有多个印章时，区别验证指定印章
	 * @param seal_position
	 *            页面中显示印章的div或td的id
	 * @param seal_x
	 *            偏离seal_position的左偏离
	 * @param seal_y
	 *            偏离seal_position的右偏离
	 * @param oriData
	 *            盖章时保护的原始数据
	 * @return 盖章结果数据，null为失败
	 * @throws Exception
	 */
	public String addSeal(String pfx_path,String pfx_psw,String seal_data,String seal_id,String seal_name,String seal_position,String seal_x,String seal_y,String oriData){
		String sealRes=null;
		KeyStore keyStore=getKeyStore(pfx_path, pfx_psw);
		if(keyStore==null){return null;}
		return getSealRes(keyStore, pfx_psw, seal_data, seal_id, seal_name, seal_position, seal_x, seal_y, oriData);
	}
	/**
	 * 服务器加盖印章
	 * 
	 * @param pfxBase64
	 *            pfx证书的base64
	 * @param pfx_psw
	 *            证书密码
	 * @param seal_data
	 *            印章数据
	 * @param seal_id
	 *            印章id
	 * @param seal_name
	 *            盖章后唯一名称，用于当显示页面中有多个印章时，区别验证指定印章
	 * @param seal_position
	 *            页面中显示印章的div或td的id
	 * @param seal_x
	 *            偏离seal_position的左偏离
	 * @param seal_y
	 *            偏离seal_position的右偏离
	 * @param oriData
	 *            盖章时保护的原始数据
	 * @return 盖章结果数据，null为失败
	 * @throws Exception
	 */
	public String addSealPfxBase64(String pfxBase64,String pfx_psw,String seal_data,String seal_id,String seal_name,String seal_position,String seal_x,String seal_y,String oriData){
		String sealRes=null;
		byte[] pfx=Base64.decode(pfxBase64);
		KeyStore keyStore=getKeyStore(pfx, pfx_psw);
		if(keyStore==null){return null;}
		return getSealRes(keyStore, pfx_psw, seal_data, seal_id, seal_name, seal_position, seal_x, seal_y, oriData);
	}
	
	/**
	 * 服务器验证部分，设置盖章结果数据
	 * 
	 * @param storeData
	 *            盖章结果，可能包含多个章
	 * @return 操作成功失败  true成功 
	 * @throws Exception
	 */
	public boolean setStoreData(String storeData){
		if(storeData!=null&&!storeData.equals("")){
			String[] temp1=storeData.split("::WebSignDataEnd;");
			int sealNum=temp1.length;
			if(sealNum<1){
				errormessge="印章数据被破坏";
				return false;
			}else{				
				for(int i=0;i<sealNum;i++){
					SealDataForVerify sealData=new SealDataForVerify();
					SealData signData=new SealData();
					Map seal1=new HashMap();
					String[] seal2=temp1[i].split("WebSignDataBegin::");
					int seal2length=seal2.length;
					if(seal2length<2){
						errormessge="印章数据被破坏";
						return false;
					}else{
						for(int j=0;j<seal2length;j++){
							String[] pros=seal2[1].split(";");
							int prosLength=pros.length;
							for(int k=0;k<prosLength;k++){
								String[] key_value=pros[k].split(":");
								if(key_value.length==2){
									seal1.put(key_value[0], key_value[1]);
								}else if(key_value.length==1){
									seal1.put(key_value[0],"");
								}else{
									errormessge="印章数据被破坏";
									return false;
								}
							}
						}
					}
					Date date=new Date();					
					sealNameList.add(seal1.get("seal_name"));
					sealData.setSealName((String)seal1.get("seal_name"));
					sealData.setP7SignRes((String)seal1.get("signRes"));
					sealData.setOridata((String)seal1.get("oriData_sha1"));
					sealData.setSealData((String)seal1.get("seal_data"));
					signData.setCert_id((String)seal1.get("cert_id"));
					signData.setOriData_sha1((String)seal1.get("oriData_sha1"));
					signData.setSeal_data((String)seal1.get("seal_data"));
					signData.setSeal_id((String)seal1.get("seal_id"));
					signData.setSeal_name((String)seal1.get("seal_name"));
					signData.setSeal_position((String)seal1.get("seal_position"));
					signData.setSeal_x((String)seal1.get("seal_x"));
					signData.setSeal_y((String)seal1.get("seal_y"));
					signData.setSignRes((String)seal1.get("signRes"));
					signData.setUser_id((String)seal1.get("user_id"));
					SignDataMap.put(seal1.get("seal_name"), sealData);		
					SealDataMap.put(seal1.get("seal_name"), signData);
				}
			}
		}
		return true;
		
	}
	/**
	 * 服务器验证部分，设置每个印章对应的待校验数据。
	 * 
	 * @param strSealName
	 *            盖章时取得印章名称
	 * @param strSignData
	 * 				待校验数据
	 * @return 操作成功失败  true成功 
	 * @throws Exception
	 */
	public boolean SetSealSignData(String strSealName,String strSignData){
		SealDataForVerify sealData=(SealDataForVerify)SignDataMap.get(strSealName);
		if(sealData==null){
			errormessge="不存在指定名称的印章";
			return false;
		}
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("sha-1");
			sha.update(strSignData.getBytes("gbk"));//更新数据
			byte[] encoder=sha.digest();
			String base64=new BASE64Encoder().encode(encoder);
			sealData.setDataToVerify(base64);
		} catch (NoSuchAlgorithmException e) {
			errormessge="获取sha-1出错";
			//logger.error(e.getMessage());
			return false;
		} 
		catch (UnsupportedEncodingException e) {
			errormessge="字符编码不支持gbk";
			return false;
			//logger.error(e.getMessage());
		}
		return true;
		
	}
	/**
	 * 服务器验证部分，验证文档。
	 * 
	 * @param sealName
	 *            盖章时取得印章名称
	 * @return 验证成功失败  true成功 
	 * @throws Exception
	 */
	public boolean verifyDoc(String sealName){
		SealDataForVerify sealData=(SealDataForVerify)SignDataMap.get(sealName);
		if(sealData==null){
			errormessge="不存在指定名称的印章";
			return false;
		}else{
			String oridata_sha1=sealData.getDataToVerify();
			String signRes=sealData.getP7SignRes();
			if(oridata_sha1==null){
				errormessge="请设置待验证数据";
				return false;
			}
			boolean res=true;
			try {
				res = CertUtil.verifyP7(signRes, oridata_sha1);
			} catch (Exception e) {
				errormessge="验证p7结果出错";
				logger.error(e.getMessage());
				return false;
			}
			if(!res){
				errormessge="数据验证不通过，文档被篡改";
				return false;
			}			
		}
		
		return true;
		
	}
	/**
	 * 服务器解析部分，解析签章结果（包含验证）
	 * 
	 * @param storeData
	 *            盖章结果，可能包含多个章
	 * @return 操作成功失败  true成功 
	 * @throws Exception
	 */
	public String parseAllSeal(boolean hasVerify){
		Document doc=new Document();
		Element Seals=new Element("Seals");
		doc.setRootElement(Seals);
		for(int i=0;i<sealNameList.size();i++){
			SealData sealData=(SealData)SealDataMap.get(sealNameList.get(i));
			Element Seal=new Element("Seal");
			Seals.addContent(Seal);
			Element cert_id=new Element("cert_id");
			cert_id.setText(sealData.getCert_id());
			Seal.addContent(cert_id);
			Element seal_x=new Element("seal_x");
			seal_x.setText(sealData.getSeal_x());
			Seal.addContent(seal_x);
			Element seal_y=new Element("seal_y");
			seal_y.setText(sealData.getSeal_y());
			Seal.addContent(seal_y);
			Element user_id=new Element("user_id");
			user_id.setText(sealData.getUser_id());
			Seal.addContent(user_id);
			Element seal_id=new Element("seal_id");
			seal_id.setText(sealData.getSeal_id());
			Seal.addContent(seal_id);
			Element seal_name=new Element("seal_name");
			seal_name.setText(sealData.getSeal_name());
			Seal.addContent(seal_name);
			Element seal_position=new Element("seal_position");
			seal_position.setText(sealData.getSeal_position());
			Seal.addContent(seal_position);
			String sealdata=sealData.getSeal_data();
			String pngData=getSealPng(sealdata);
			Element pngDataE=new Element("seal_picture");
			Seal.addContent(pngDataE);
			Element verifyE=new Element("verify");
			Element verifyResE=new Element("verifyRes");
			Element errorE=new Element("errorMsg");
			boolean verify;
			if(hasVerify){
				verify=verifyDoc(sealData.getSeal_name());
			}else{
				verify=true;
			}
			if(verify){
				verifyResE.setText("true");
				pngDataE.setText(pngData);
			}else{
				verifyResE.setText("false");
				errorE.setText(errormessge);
				try {
					pngData=PictrueMyUtil.gifOperate(pngData);
					pngDataE.setText(pngData);
				} catch (IOException e) {
					errormessge="验证失败时图片处理出错";
					errorE.setText(errormessge);
					pngDataE.setText("");
				}
			}
			verifyE.addContent(verifyResE);
			verifyE.addContent(errorE);
			Seal.addContent(verifyE);
			
		}
		return getXMLString(Seals);
			
	}
	/**
	 * 功能：将xml转为xml字符串输出，
	 * 参数 xml文档部分
	 * 返回值 xml字符串
	 */
	public String getXMLString(Element doc){
		Format format = Format.getCompactFormat(); 
		format.setEncoding("utf-8");
		format.setIndent("  ");
		XMLOutputter outputter=new XMLOutputter(format);
		return outputter.outputString(doc);
	}
	
	/**
	 * 从印章格式数据中解析出图片数据
	 * 参数 印章数据 返回值  gif的base64
	 */
	public String getSealPng(String sealData){
		DecSealUtil dec_seal=DecSealUtil.getDec_seal();
		String gifbase64="";
		gifbase64=dec_seal.getSealImg(sealData, "");
		//logger.info(sealData);
		return gifbase64;
	}
	
	public String getErrormessge() {
		return errormessge;
	}


	public void setErrormessge(String errormessge) {
		this.errormessge = errormessge;
	}
	

}
