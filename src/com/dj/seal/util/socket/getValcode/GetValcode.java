package com.dj.seal.util.socket.getValcode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.spring.MyApplicationContextUtil;


/*返回验证码统一接口,无查询*/
public class GetValcode {
	static Logger logger=LogManager.getLogger(GetValcode.class.getName());
	
	public static String getValcode(String xmlData){
		EleInternationalAccountPo eac=new EleInternationalAccountPo();
		EleInternationalAccountDaoImpl daoImpl = (EleInternationalAccountDaoImpl) getBean("EleAccountCheckDao");
		StringBuffer sb = null;
		try {
			Document doc;
			doc = DocumentHelper.parseText(xmlData);
			Element req = doc.getRootElement();


			/* 获取节点数据 */
			String ss = req.element("SYS_HEAD").elementText("ServiceScene");// 服务代码(同输入)
			String sc = req.element("SYS_HEAD").elementText("ServiceCode");// 服务场景(同输入)
			String ci = req.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号(同输入)
			String oi = req.element("SYS_HEAD").elementText("OrgConsumerId");// 发起方系统编号(渠道号)
			String cs = req.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = req.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = req.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = req.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = req.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)
			
			String TxnSeqNo = req.element("BODY").elementText("TxnSeqNo");// 流水号
			String TxnNo = req.element("BODY").elementText("TxnNo");// 交易码
			String TxnDt = req.element("BODY").elementText("TxnDt");// 交易日期
			String AcctAcctNo = req.element("BODY").elementText("AcctAcctNo");//账户账号
			String AcctNm = req.element("BODY").elementText("AcctNm");//账户名称
			String EnqrStrtDt = req.element("BODY").elementText("EnqrStrtDt");//查询开始日期
			String EnqrEndDt = req.element("BODY").elementText("EnqrEndDt");//查询结束日期
			String StmtDt = req.element("BODY").elementText("StmtDt");//账单生成日期

			
			/*返回报文公共部分*/
			sb=new StringBuffer();
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
			
			String encData=TxnSeqNo+TxnNo+TxnDt;
			if(encData.contains("null"))
				return "DataException,check data please!";
			
			logger.info("【"+TxnSeqNo+"】待加密数据:"+encData);
			String des = new ThreeDesHelper().encrypt(encData);
			String md5 = new MD5Helper().encode8BitBySalt(des,"gznsh").toUpperCase();//valcode
			logger.info("【"+TxnSeqNo+"】生成验证码:"+md5);
			
			StringBuffer sbf1=new StringBuffer(TxnDt);
			StringBuffer insert1 = sbf1.insert(4, "-").insert(7, "-");
			
			if(EnqrStrtDt!=null&&!EnqrStrtDt.equals("")){
				StringBuffer sbf2=new StringBuffer(EnqrStrtDt);
				StringBuffer insert2 = sbf2.insert(4, "-").insert(7, "-");				
				eac.setStartDate(Timestamp.valueOf(insert2+" 00:00:00"));
			}
			
			if(EnqrEndDt!=null&&!EnqrEndDt.equals("")){
				StringBuffer sbf3=new StringBuffer(EnqrEndDt);
				StringBuffer insert3 = sbf3.insert(4, "-").insert(7, "-");
				eac.setEndDate(Timestamp.valueOf(insert3+" 00:00:00"));
			}
			
			if(StmtDt!=null&&!StmtDt.equals("")){
				StringBuffer sbf4=new StringBuffer(StmtDt);
				StringBuffer insert4 = sbf4.insert(4, "-").insert(7, "-");
				eac.setAccumulationCreateDate(Timestamp.valueOf(insert4+" 00:00:00"));		
			}
			
			/*save data*/
			eac.setChannelNo(oi);
			eac.setOrgConsumerSeqNo(TxnSeqNo);
			eac.setTranNo(TxnNo);
			eac.setTranDate(Timestamp.valueOf(insert1+" 00:00:00"));
			eac.setValcode(md5);
			eac.setSealPictureData("nothing");
			eac.setCreateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())));
			eac.setAccountNumber(AcctAcctNo);
			eac.setAccountName(AcctNm);
			logger.info("【"+TxnSeqNo+"】保存数据...");					
			int accountCheckRecord = daoImpl.accountCheckRecord(eac);//save 
			
			/*whether save successfully*/
			if(accountCheckRecord>0){//true
				logger.info("【"+TxnSeqNo+"】保存数据成功!");
				sb.append("     <ReturnStatus>S</ReturnStatus>\r\n");
				sb.append("      <array>\r\n");
				sb.append("       <Ret>\r\n");
				sb.append("        <ReturnCode>000000</ReturnCode>\r\n");
				sb.append("        <ReturnMsg>交易成功</ReturnMsg>\r\n");
				sb.append("       </Ret>\r\n");
				sb.append("      </array>\r\n");
				sb.append("  </SYS_HEAD>\r\n");
				sb.append("  <BODY>\r\n");
				sb.append("   <VerfNo>"+md5+"</VerfNo>\r\n");
				sb.append("  </BODY>\r\n");
				sb.append(" </service>\r\n");
			}else{//false
				logger.info("【"+TxnSeqNo+"】保存数据失败!");
				sb.append("     <ReturnStatus>F</ReturnStatus>\r\n");
				sb.append("      <array>\r\n");
				sb.append("       <Ret>\r\n");
				sb.append("        <ReturnCode>00000100</ReturnCode>\r\n");
				sb.append("        <ReturnMsg>程序处理异常</ReturnMsg>\r\n");
				sb.append("       </Ret>\r\n");
				sb.append("      </array>\r\n");
				sb.append("  </SYS_HEAD>\r\n");
				sb.append("  <BODY>\r\n");
				sb.append("   <VerfNo>null</VerfNo>\r\n");
				sb.append("  </BODY>\r\n");
				sb.append(" </service>\r\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();//return
	}
	
	public static Object getBean(String beanName){
		if(MyApplicationContextUtil.getContext()==null)
			return null;
		else
			return MyApplicationContextUtil.getContext().getBean(beanName);
	}
}
