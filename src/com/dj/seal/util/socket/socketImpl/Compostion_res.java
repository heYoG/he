package com.dj.seal.util.socket.socketImpl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.util.socket.getValcode.GetData_mobileAndwechatBank;

public class Compostion_res {
	static Logger logger = LogManager.getLogger(Compostion_res.class.getName());
	
	public static String compostion_Response(String xmlData) {
		Document doc = null;
		Element el = null;
		try {
			doc = DocumentHelper.parseText(xmlData);
			el = doc.getRootElement();
			String sc = el.element("SYS_HEAD").elementText("ServiceCode");// 服务名称
			String ss = el.element("SYS_HEAD").elementText("ServiceScene");// 服务场景
			String flag=el.element("BODY").elementText("EnqrInd");//查询标识
			if (sc.equals("12002000052") && ss.equals("01")) {// 打印接口
				logger.info("进入的是打印接口....");
				return PrintSeal_res.printSeal_response(xmlData);
			} else if (sc.equals("12003000034") && ss.equals("01")) {// 查询接口
				logger.info("进入的是查询接口....");
				if(flag!=null&&!flag.equals(""))//查询标识存在且不为空查询对账系统表
					return GetData_mobileAndwechatBank.getData(xmlData);//微信/移动银行查询																					
				else//查询标识不存在或为空值查询单据表
					return GetTradeData_res.tradeData_response(xmlData);//微信/柜面查询
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "调用接口异常";
	}
}
