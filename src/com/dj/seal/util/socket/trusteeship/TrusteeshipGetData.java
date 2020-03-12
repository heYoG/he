package com.dj.seal.util.socket.trusteeship;

import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;
import com.dj.seal.util.spring.MyApplicationContextUtil;

/**
 * 托管银行查询(暂未投20190520)
 * @author WB000520
 *
 */
public class TrusteeshipGetData {
	static Logger logger=LogManager.getLogger(TrusteeshipGetData.class.getName());
	public static String getData(String xmlData){
		logger.info("托管银行查询...");
		StringBuffer sb=null;
		try {
			Document doc = DocumentHelper.parseText(xmlData);
			Element element = doc.getRootElement();
			String ss = element.element("SYS_HEAD").elementText("ServiceScene");// 服务代码,同输入
			String sc = element.element("SYS_HEAD").elementText("ServiceCode");// 服务场景,同输入
			String ci = element.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号,同输入
			String oi = element.element("SYS_HEAD").elementText("OrgConsumerId");// 业务系统编号,同输入(渠道号)
			String cs = element.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = element.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = element.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = element.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = element.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)
			
			String valcode=element.element("BODY").elementText("EsalVerfNo");//验证码
			EleInternationalAccountDaoImpl daoImpl = (EleInternationalAccountDaoImpl) getBean("EleAccountCheckDao");
			EleInternationalAccountPo accountPo = daoImpl.getData(valcode.toUpperCase());
			sb=new StringBuffer();
			String result=null;//查询结果
			String returnCode=null;//返回码
			String returnMsg=null;//返回信息
			String sealDt=null;//盖章日期
			String sysCh=null;//盖章渠道
			String sealType=null;//盖章件种类
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			
			/*check trade status*/
			if(accountPo==null){
				result="F";
				returnCode="11";
				returnMsg="查询不到该笔业务的验证码信息，请核对输入信息是否正确！";	
			}else{
				result="S";
				returnCode="000000";
				returnMsg="交易成功";
				sysCh=accountPo.getChannelNo();
				sealDt=sdf.format(accountPo.getCreateTime());
				sealType=accountPo.getValcode();
			}

			/*public*/
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			sb.append(" <service version=\"2.0\">\r\n");
			sb.append("  <SYS_HEAD>\r\n");// 系统头
			sb.append("     <ServiceScene>" + ss+ "</ServiceScene>\r\n");// 服务代码,同输入
			sb.append("     <ServiceCode>" + sc+ "</ServiceCode>\r\n");// 服务场景,同输入
			sb.append("     <ConsumerId>" + ci+ "</ConsumerId>\r\n");// 消费系统编号,同输入
			sb.append("     <OrgConsumerId>" + oi+ "</OrgConsumerId>\r\n");// 业务系统编号,同输入(渠道号)
			sb.append("     <ConsumerSeqNo>" + cs+ "</ConsumerSeqNo>\r\n");// 消费系统流水号(同输入)
			sb.append("     <OrgConsumerSeqNo>" + os+ "</OrgConsumerSeqNo>\r\n");// 业务流水号(同输入)
			sb.append("     <ServSeqNo>" + sn + "</ServSeqNo>\r\n");// 服务系统流水号(同输入)
			sb.append("     <TranDate>" + td + "</TranDate>\r\n");// 交易日期(同输入)
			sb.append("     <TranTime>" + tt + "</TranTime>\r\n");// 交易时间(同输入)	
			
			/*BODY and ending*/
			sb.append("     <ReturnStatus>"+result+"</ReturnStatus>\r\n");
			sb.append("      <array>\r\n");
			sb.append("       <Ret>\r\n");
			sb.append("        <ReturnCode>"+returnCode+"</ReturnCode>\r\n");
			sb.append("        <ReturnMsg>"+returnMsg+"</ReturnMsg>\r\n");
			sb.append("       </Ret>\r\n");
			sb.append("      </array>\r\n");
			sb.append("  </SYS_HEAD>\r\n");
			sb.append("  <BODY>\r\n");
			sb.append("   <StmpDt>"+sealDt+"</StmpDt>\r\n");
			sb.append("   <CnlNo>"+sysCh+"</CnlNo>\r\n");
			sb.append("   <StmpFlTp>"+sealType+"</StmpFlTp>\r\n");
			sb.append("  </BODY>\r\n");
			sb.append(" </service>\r\n");	

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static Object getBean(String beanName){
		if(MyApplicationContextUtil.getContext()==null)
			return null;
		else
			return MyApplicationContextUtil.getContext().getBean(beanName);
	}
}
