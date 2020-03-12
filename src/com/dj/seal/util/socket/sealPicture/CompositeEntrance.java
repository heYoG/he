package com.dj.seal.util.socket.sealPicture;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.util.socket.getValcode.GetValcode;
import com.dj.seal.util.socket.sealPictureOfCheckAccountSys.EleInternationalAccountResponse;


public class CompositeEntrance {
	static Logger logger=LogManager.getLogger(CompositeEntrance.class.getName());
	
	public static String ComEntrance(String xmlData){
		Document doc=null;
		Element ele=null;
		try {
			doc=DocumentHelper.parseText(xmlData);
			ele=doc.getRootElement();
			String flag=ele.element("BODY").elementText("SysInd");//获取验证码标识(系统渠道号)
			String seqNo=ele.element("BODY").elementText("TxnSeqNo");//流水号
			logger.info("【"+seqNo+"】flag:"+flag);
			if(flag!=null){//返回印章验证码
				logger.info("获取印章验证码...");
				return GetValcode.getValcode(xmlData);
			}else{//返回印章图片
				logger.info("获取印章图片...");
				return EleInternationalAccountResponse.getSealPic(xmlData);				
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return "调用接口异常";
		}
	}
}
