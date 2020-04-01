package com.dj.seal.util.socket.onlineBank.serverResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.util.socket.onlineBank.dao.impl.OnlineBankAndEleSysDaoImpl;
import com.dj.seal.util.socket.onlineBank.po.OnlineBankAndEleSysPo;
import com.dj.seal.util.socket.onlineBank.util.ApplicationContextUtil;

public class OnlineBankAndEleSysResponseOut {
	public static String onlineBankAndEleSysQuery(String xmlData) {
		OnlineBankAndEleSysDaoImpl onlineEleImpl=(OnlineBankAndEleSysDaoImpl) ApplicationContextUtil.getBean("onlineBAndEleSImpl");//daoImpl bean
		Document doc=null;
		StringBuffer sb=null;
		String rs = null;//返回ESB交易状态
		String tc = null;//返回ESB码
		String rm = null;//返回ESB提示信息
		try {
			doc=DocumentHelper.parseText(xmlData);
			Element rootElement = doc.getRootElement();
			String ss = rootElement.element("SYS_HEAD").elementText("ServiceScene");// 服务代码,同输入
			String sc = rootElement.element("SYS_HEAD").elementText("ServiceCode");// 服务场景,同输入
			String ci = rootElement.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号,同输入
			String oi = rootElement.element("SYS_HEAD").elementText("OrgConsumerId");// 业务系统编号,同输入(渠道号)

			String cs = rootElement.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = rootElement.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = rootElement.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = rootElement.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = rootElement.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)

			String valcode = rootElement.element("BODY").elementText("EsalVerfNo");// 验证码
			String tran_dt = rootElement.element("BODY").elementText("TxnDt");// 交易日期(保留)
			String consumerSq = rootElement.element("BODY").elementText("TxnSeqNo");// 流水号(保留)
			OnlineBankAndEleSysPo data = onlineEleImpl.getOnlineBankData(consumerSq,tran_dt,valcode);
			
			sb=new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			sb.append(" <service version=\"2.0\">\r\n");
			sb.append("  <SYS_HEAD>\r\n");// 系统头
			sb.append("     <ServiceScene>" + ss
					+ "</ServiceScene>\r\n");// 服务代码,同输入
			sb.append("     <ServiceCode>" + sc
					+ "</ServiceCode>\r\n");// 服务场景,同输入
			sb.append("     <ConsumerId>" + ci
					+ "</ConsumerId>\r\n");// 消费系统编号,同输入
			sb.append("     <OrgConsumerId>" + oi
					+ "</OrgConsumerId>\r\n");// 业务系统编号,同输入(渠道号)
			sb.append("     <ConsumerSeqNo>" + cs
					+ "</ConsumerSeqNo>\r\n");// 消费系统流水号(同输入)
			sb.append("     <OrgConsumerSeqNo>" + os
					+ "</OrgConsumerSeqNo>\r\n");// 业务流水号(同输入)
			sb.append("     <ServSeqNo>" + sn + "</ServSeqNo>\r\n");// 服务系统流水号(同输入)
			sb.append("     <TranDate>" + td + "</TranDate>\r\n");// 交易日期(同输入)
			sb.append("     <TranTime>" + tt + "</TranTime>\r\n");// 交易时间(同输入)
			if(data!=null) {
				rs = "S";
				tc = "000000";
				rm = "交易成功";
			}else {
				rs = "F";
				tc = "00";
				rm = "交易失败，查询返回空！";
			}
			sb.append("     <ReturnStatus>" + rs + "</ReturnStatus>\r\n");// 交易状态
			sb.append("     <array>\r\n");// 交易返回代码数组
			sb.append("        <Ret>\r\n");
			sb.append("           <ReturnCode>" + tc + "</ReturnCode>\r\n");// 交易返回代码
			sb.append("           <ReturnMsg>" + rm + "</ReturnMsg>\r\n");// 交易返回信息
			sb.append("        </Ret>\r\n");
			sb.append("     </array>\r\n");
			sb.append("   </SYS_HEAD>\r\n");
			sb.append("   <BODY>\r\n");// 主体
			sb.append("      <BnkNm>"+data.getInfo_plus()+"</BnkNm>\r\n");//附加信息(大数据字段)
			sb.append("   </BODY>\r\n");
			sb.append("</service>\r\n");
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
