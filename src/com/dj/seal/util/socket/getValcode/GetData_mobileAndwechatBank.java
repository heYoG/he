package com.dj.seal.util.socket.getValcode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;
import com.dj.seal.hotel.service.impl.NSHRecordServiceImpl;
import com.dj.seal.structure.dao.po.QueryLog;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class GetData_mobileAndwechatBank {
	static Logger logger = LogManager
			.getLogger(GetData_mobileAndwechatBank.class.getName());

	public static String getData(String xmlData) {
		logger.info("移动&微信银行查询...");
		NSHRecordServiceImpl nshRs = (NSHRecordServiceImpl) getBean("nshRecordService");
		StringBuffer sb=null;
		try {
			Document doc = DocumentHelper.parseText(xmlData);
			Element element = doc.getRootElement();
			String ss = element.element("SYS_HEAD").elementText("ServiceScene");// 服务代码,同输入
			String sc = element.element("SYS_HEAD").elementText("ServiceCode");// 服务场景,同输入
			String ci = element.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号,同输入
			String oi = element.element("SYS_HEAD")
					.elementText("OrgConsumerId");// 业务系统编号,同输入(渠道号)
			String cs = element.element("SYS_HEAD")
					.elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = element.element("SYS_HEAD").elementText(
					"OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = element.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = element.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = element.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)

			String valcode = element.element("BODY").elementText("EsalVerfNo");// 验证码
			String tran_dt = element.element("BODY").elementText("TxnDt");// 交易日期
			String phone_num=element.element("BODY").elementText("MblNo");//手机号
			EleInternationalAccountDaoImpl daoImpl = (EleInternationalAccountDaoImpl) getBean("EleAccountCheckDao");
			EleInternationalAccountPo accountPo = daoImpl.getDataOfWechatAndMobileBank(valcode.toUpperCase(), tran_dt);

			sb = new StringBuffer();
			String result = null;// 查询结果
			String returnCode = null;// 返回码
			String returnMsg = null;// 返回信息
			String serialNum = null;// 流水号
			String tranNo = null;// 交易码
			String tranDate = null;// 交易日期
			String accountNo = null;// 帐户帐号
			String accountName = null;// 帐户名称
			String startDate = null;// 查询开始日期
			String endDate = null;// 查询结束日期
			String accumulationCreateDate = null;// 帐单生成日期
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			/* check trade status */
			if (accountPo == null) {
				result = "F";
				returnCode = "11";
				returnMsg = "查询不到该笔业务的验证码信息，请核对输入信息是否正确！";
			} else {
				result = "S";
				returnCode = "000000";
				returnMsg = "交易成功";
				serialNum = accountPo.getOrgConsumerSeqNo();
				tranNo = accountPo.getTranNo();
				tranDate =sdf.format(accountPo.getTranDate());
				accountNo = accountPo.getAccountNumber();
				accountName = accountPo.getAccountName();
				if(accountPo.getStartDate()!=null&&!accountPo.getStartDate().equals(""))
					startDate =sdf.format(accountPo.getStartDate());
				else
					startDate=null;
				if(accountPo.getEndDate()!=null&&!accountPo.getEndDate().equals(""))
					endDate = sdf.format(accountPo.getEndDate());
				else
					endDate=null;
				if(accountPo.getAccumulationCreateDate()!=null&&!accountPo.getAccumulationCreateDate().equals(""))
					accumulationCreateDate = sdf.format(accountPo.getAccumulationCreateDate());
				else
					accumulationCreateDate=null;
			}

			/* public */
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			sb.append(" <service version=\"2.0\">\r\n");
			sb.append("  <SYS_HEAD>\r\n");// 系统头
			sb.append("     <ServiceScene>" + ss + "</ServiceScene>\r\n");// 服务代码,同输入
			sb.append("     <ServiceCode>" + sc + "</ServiceCode>\r\n");// 服务场景,同输入
			sb.append("     <ConsumerId>" + ci + "</ConsumerId>\r\n");// 消费系统编号,同输入
			sb.append("     <OrgConsumerId>" + oi + "</OrgConsumerId>\r\n");// 业务系统编号,同输入(渠道号)
			sb.append("     <ConsumerSeqNo>" + cs + "</ConsumerSeqNo>\r\n");// 消费系统流水号(同输入)
			sb.append("     <OrgConsumerSeqNo>" + os
					+ "</OrgConsumerSeqNo>\r\n");// 业务流水号(同输入)
			sb.append("     <ServSeqNo>" + sn + "</ServSeqNo>\r\n");// 服务系统流水号(同输入)
			sb.append("     <TranDate>" + td + "</TranDate>\r\n");// 交易日期(同输入)
			sb.append("     <TranTime>" + tt + "</TranTime>\r\n");// 交易时间(同输入)

			/* BODY and ending */
			sb.append("     <ReturnStatus>" + result + "</ReturnStatus>\r\n");
			sb.append("      <array>\r\n");
			sb.append("       <Ret>\r\n");
			sb.append("        <ReturnCode>" + returnCode + "</ReturnCode>\r\n");
			sb.append("        <ReturnMsg>" + returnMsg + "</ReturnMsg>\r\n");
			sb.append("       </Ret>\r\n");
			sb.append("      </array>\r\n");
			sb.append("  </SYS_HEAD>\r\n");
			sb.append("  <BODY>\r\n");
			sb.append("   <SeqNo>" + serialNum + "</SeqNo>\r\n");
			sb.append("   <TxanNo>" + tranNo + "</TxanNo>\r\n");
			sb.append("   <Txn02Dt>" + tranDate + "</Txn02Dt>\r\n");
			sb.append("   <AcctAcctNo>" + accountNo + "</AcctAcctNo>\r\n");
			sb.append("   <AcctNm>" + accountName + "</AcctNm>\r\n");
			sb.append("   <EnqrStrtDt>" + startDate + "</EnqrStrtDt>\r\n");
			sb.append("   <EnqrEndDt>" + endDate + "</EnqrEndDt>\r\n");
			sb.append("   <StmtDt>" + accumulationCreateDate + "</StmtDt>\r\n");
			sb.append("  </BODY>\r\n");
			sb.append(" </service>\r\n");
			
			if(phone_num!=null&&!"".equals(phone_num)){//关于手机号查询才保存查询记录
				QueryLog ql=new QueryLog(serialNum,valcode,oi,phone_num,new Timestamp(System.currentTimeMillis()),"someone");
				nshRs.addQueryLog(ql);//查询成功保存查询记录					
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static Object getBean(String beanName) {
		if (MyApplicationContextUtil.getContext() == null)
			return null;
		return MyApplicationContextUtil.getContext().getBean(beanName);
	}
}
