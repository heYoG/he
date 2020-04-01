package com.dj.seal.util.socket.onlineBank.serverResponse;

import java.sql.Timestamp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.socket.onlineBank.dao.impl.OnlineBankAndEleSysDaoImpl;
import com.dj.seal.util.socket.onlineBank.po.OnlineBankAndEleSysPo;
import com.dj.seal.util.socket.onlineBank.util.ApplicationContextUtil;

public class OnlineBankAndEleSysResponseEntrance {
	public static String onlineBankAndEleSysSave(String xmlData) {
		Document doc=null;
		String VerfNo = null;// 新生成验证码
		String str = null;// 3DES加密串
		Timestamp ts =null;//交易日期
		StringBuffer sb=null;//缓冲流
		String rs = null;//返回ESB交易状态
		String tc = null;//返回ESB码
		String rm = null;//返回ESB提示信息
		
		OnlineBankAndEleSysPo op=(OnlineBankAndEleSysPo) ApplicationContextUtil.getBean("onlineBankAndEleSysPo");//实例bean
		OnlineBankAndEleSysDaoImpl onlineEleImpl=(OnlineBankAndEleSysDaoImpl) ApplicationContextUtil.getBean("onlineBAndEleSImpl");//daoImpl bean
		
		try {
			doc=DocumentHelper.parseText(xmlData);//解析传入报文
			Element rootElement = doc.getRootElement();//获取报文根节点对象
			String ss = rootElement.element("SYS_HEAD").elementText("ServiceScene");// 服务代码,同输入
			String sc = rootElement.element("SYS_HEAD").elementText("ServiceCode");// 服务场景,同输入
			String ci = rootElement.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号,同输入
			String oi = rootElement.element("SYS_HEAD").elementText("OrgConsumerId");// 业务系统编号,同输入(渠道号)
			String cs = rootElement.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = rootElement.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = rootElement.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = rootElement.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = rootElement.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)
			
			String TxnSeqNo = rootElement.element("BODY").elementText("TxnSeqNo");// 流水号
			String TxnNo = rootElement.element("BODY").elementText("TxnNo");// 交易码
			String TxnDt = rootElement.element("BODY").elementText("TxnDt");// 交易日期
			String attachInfo=rootElement.element("BODY").elementText("info_plus");//附加信息
			str = new ThreeDesHelper().encrypt(TxnSeqNo + TxnNo + TxnDt);
			VerfNo = new MD5Helper().encode8BitBySalt(str, "gznsh").toUpperCase();// MD5加密生成验证码(要求字母都为大写)
			
			/* yyyyMMdd----->yyyy-MM-dd*/
			if(TxnDt.length()==8) {
				StringBuffer sf = new StringBuffer(TxnDt);
				StringBuffer insert1 = sf.insert(4, "-");
				StringBuffer insert2 = insert1.insert(7, "-");
				String insert3 = insert2.toString();
				String TxnDt2 = insert3 + " 00:00:00";
				ts = Timestamp.valueOf(TxnDt2);
			}
			op.setChannelno(oi);
			op.setOrgconsumerseqno(TxnSeqNo);
			op.setTranno(TxnNo);
			op.setTrandate(ts);
			op.setValcode(VerfNo);
			op.setInfo_plus(attachInfo);
			onlineEleImpl.onlineBankSave(op);//保存数据
			
			/*无异常即表示成功*/
			rs = "S";
			tc = "000000";
			rm = "交易成功";
			
			/* 响应报文 */
			sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
			sb.append(" <service version=\"2.0\">\r\n");
			sb.append("  <SYS_HEAD>\r\n");
			sb.append("     <ServiceScene>" + ss + "</ServiceScene>\r\n");
			sb.append("     <ServiceCode>" + sc + "</ServiceCode>\r\n");
			sb.append("     <ConsumerId>" + ci + "</ConsumerId>\r\n");
			sb.append("     <OrgConsumerId>" + oi + "</OrgConsumerId>\r\n");

			sb.append("     <ConsumerSeqNo>" + cs + "</ConsumerSeqNo>\r\n");
			sb.append("     <OrgConsumerSeqNo>" + os
					+ "</OrgConsumerSeqNo>\r\n");
			sb.append("     <ServSeqNo>" + sn + "</ServSeqNo>\r\n");
			sb.append("     <TranDate>" + td + "</TranDate>\r\n");
			sb.append("     <TranTime>" + tt + "</TranTime>\r\n");

			sb.append("     <ReturnStatus>" + rs + "</ReturnStatus>\r\n");// 交易状态
			sb.append("     <array>\r\n");// 交易返回代码数组
			sb.append("        <Ret>\r\n");
			sb.append("           <ReturnCode>" + tc + "</ReturnCode>\r\n");// 交易返回代码
			sb.append("           <ReturnMsg>" + rm + "</ReturnMsg>\r\n");// 交易返回信息
			sb.append("        </Ret>\r\n");
			sb.append("     </array>\r\n");
			sb.append("   </SYS_HEAD>\r\n");

			sb.append("   <BODY>\r\n");// 主体
			sb.append("      <BnkNm>"+VerfNo+"</BnkNm>\r\n");//返回验证码
			sb.append("   </BODY>\r\n");
			sb.append("</service>\r\n");
		} catch (DocumentException e) {
			rs = "F";
			tc = "00";
			rm = "交易失败，数据处理异常！";
			e.printStackTrace();
			return rm;
		}
		return sb.toString();
	}
}
