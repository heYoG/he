package com.dj.seal.util.socket.socketImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.impl.NSHRecordServiceImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.structure.dao.po.QueryLog;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.unitAndLevel.impl.UnitAndLevelDaoImpl;
import com.dj.seal.unitAndLevel.vo.UnitAndLevelVo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class GetTradeData_res {
	static Logger logger = LogManager.getLogger(GetTradeData_res.class
			.getName());

	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	public static String tradeData_response(String xmlData) {
		NSHRecordServiceImpl nshRs = (NSHRecordServiceImpl) getBean("nshRecordService");
		UnitAndLevelDaoImpl ud=(UnitAndLevelDaoImpl)getBean("UnitAndLevelDaoImpl");
		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		/* 转换日期格式:年-月-日*/ 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Document doc;
		StringBuilder sb = null;
		String valcd = null;
		String valcode2 = null;// 转换后valcode
		String tellerid=null;//柜员号
		int TxnTotNum=0;
		NSHRecordPo requiredNum=null;
		UnitAndLevelVo vo=null;
//		String leveNo="";//查询级别表为空时返回级别号空值

		try {
			doc = DocumentHelper.parseText(xmlData);
			Element req = doc.getRootElement();
			String ss = req.element("SYS_HEAD").elementText("ServiceScene");// 服务代码,同输入
			String sc = req.element("SYS_HEAD").elementText("ServiceCode");// 服务场景,同输入
			String ci = req.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号,同输入
			String oi = req.element("SYS_HEAD").elementText("OrgConsumerId");// 业务系统编号,同输入(渠道号)

			String cs = req.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = req.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = req.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = req.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = req.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)

			String valcode = req.element("BODY").elementText("EsalVerfNo");// 柜面传入验证码valcodeconsumerSq
			String tran_dt = req.element("BODY").elementText("TxnDt");// 交易日期
			String consumerSq = req.element("BODY").elementText("TxnSeqNo");// 流水号
			String phone_num=req.element("BODY").elementText("MblNo");//手机号
			
			if (valcode != null && !"".equals(valcode)) {
				valcode2 = valcode.toUpperCase();// valcode输入不区分大小写(数据库存的是大写)
			}

			/*查询单据表*/
			List<NSHRecordPo> list = nshRs.TradeData(valcode2,tran_dt, consumerSq);
			
			sb = new StringBuilder();
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
			
			if (list!=null) {
				TxnTotNum=list.size();//交易总笔数
				for (NSHRecordPo np : list) {
					valcd = np.getValcode();// 验证码
					//通用电子打印使用相同的三要素,无纸接口走内部交易不产生valcode,查询仅要含valcode数据
					if(TxnTotNum>1){//add 20180424
						if(valcd== null || "".equals(valcd))
							continue;						
					}
					if (valcd == null || "".equals(valcd)) {// 内部交易无验证码，直接终止交易的继续
						sb.append("     <ReturnStatus>F</ReturnStatus>\r\n");// 交易状态
						sb.append("     <array>\r\n");// 交易返回代码数组
						sb.append("        <Ret>\r\n");
						sb.append("           <ReturnCode>11</ReturnCode>\r\n");// 交易返回代码
						sb.append("           <ReturnMsg>查询不到该笔业务的验证码信息，请核对输入信息是否正确！</ReturnMsg>\r\n");// 交易返回信息
						sb.append("        </Ret>\r\n");
						sb.append("     </array>\r\n");
						sb.append("  </SYS_HEAD>\r\n");

						sb.append("  <BODY>\r\n");// 主体
						sb.append("   <array>\r\n");
						sb.append("    <RetInfArry>\r\n");//信息数组
						sb.append("      <EsalVerfNo>" + valcd+ "</EsalVerfNo>\r\n");// 验证码
						sb.append("      <TxnDt>null</TxnDt>\r\n");// 交易日期
						sb.append("      <TxnNm>null</TxnNm>\r\n");// 交易名称
						sb.append("      <TxnBrId>null</TxnBrId>\r\n");// 机构号
						sb.append("      <TxnBrIdNm>null</TxnBrIdNm>\r\n");// 机构名称
						sb.append("      <AgntTlrNo>null</AgntTlrNo>\r\n");// 交易柜员号
						sb.append("      <ChkAhrTlrNo>null</ChkAhrTlrNo>\r\n");// 授权柜员号
						sb.append("      <BsnVchrNo>null</BsnVchrNo>\r\n");// 交易凭证
						sb.append("      <TxnNo>null</TxnNo>\r\n");// 交易码
						sb.append("      <RqsTms>0</RqsTms>\r\n");// 打印次数
						sb.append("      <TxnSeqNo>null</TxnSeqNo>\r\n");//交易流水号
						sb.append("      <PlsInf>null</PlsInf>\r\n");//附加信息
						sb.append("      <BnkTp>null</BnkTp>\r\n");//行别机构号(移动银行和微信银行查询新增)
						sb.append("      <BnkNm>null</BnkNm>\r\n");//电子印章名称
//						sb.append("      <BrLvl>null</BrLvl>\r\n");//行别级别号
						sb.append("    </RetInfArry>\r\n");
						logger.info("valcode:"+valcode+",consumerSq:"+consumerSq+"交易失败,查询返回值为空！");	
					} else {// 外部交易		
						tellerid=np.getTellerid();//操作柜员号,用于保存查询记录
						requiredNum = nshRs.getMaxRequired_printNum(valcd);// 根据验证码查询最大请求次数
//						vo = ud.getUnitAndLevel(np.getOrgunit());//查询级别表
//						if(vo!=null){//暂时屏蔽级别号返回20191212
//							leveNo=vo.getLevelno().toString();	//获取机构级别号					
//						}
						
						/**********************************
						 * 获取行别号(移动银行和微信银行查询新增)
						 * ********************************/
						SysDepartment dept= deptService.getParentNo(np.getOrgunit());// 根据机构号获取部门编号、父部门编号
						SysDepartment byparent_No=null;
						String orgUnit=null;//行别号
						if(!dept.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//查询出非1级行别机构
							byparent_No=deptService.getDeptNo(dept.getDept_parent());//父部门编号作为部门编号查询	
							while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
								byparent_No=deptService.getDeptNo(byparent_No.getDept_parent());
							}
							orgUnit=byparent_No.getBank_dept();
						}else{//查询出是1级行别机构
							orgUnit=np.getOrgunit();
						}
						
						sb.append("    <RetInfArry>\r\n");
						sb.append("      <EsalVerfNo>" + valcd+ "</EsalVerfNo>\r\n");// 验证码
						sb.append("      <TxnDt>" + df.format(np.getD_trandt())+ "</TxnDt>\r\n");// 交易日期
						sb.append("      <TxnNm>" + np.getD_tranname() + "</TxnNm>\r\n");// 交易名称
						sb.append("      <TxnBrId>" + np.getOrgunit()+ "</TxnBrId>\r\n");// 机构号
						sb.append("      <TxnBrIdNm>" + np.getTranorgname()+ "</TxnBrIdNm>\r\n");// 机构名称
						sb.append("      <AgntTlrNo>" + np.getTellerid()+ "</AgntTlrNo>\r\n");// 交易柜员号
						sb.append("      <ChkAhrTlrNo>" + np.getAuthtellerno()+ "</ChkAhrTlrNo>\r\n");// 授权柜员号
						sb.append("      <BsnVchrNo>" + np.getBp1()+ "</BsnVchrNo>\r\n");// 交易凭证
						sb.append("      <TxnNo>" + np.getTrancode() + "</TxnNo>\r\n");// 交易码
						sb.append("      <RqsTms>" + requiredNum.getRequirednum().toString() + "</RqsTms>\r\n");// 最大请求次数
						sb.append("      <TxnSeqNo>"+np.getCaseseqid()+"</TxnSeqNo>\r\n");//交易流水号
						sb.append("      <PlsInf>"+np.getInfo_plus()+"</PlsInf>\r\n");//附加信息
						sb.append("      <BnkTp>"+orgUnit+"</BnkTp>\r\n");//行别号(移动银行和微信银行查询新增)
						sb.append("      <BnkNm>"+np.getBp3()+"</BnkNm>\r\n");//电子印章名称
//						sb.append("      <BrLvl>"+leveNo+"</BrLvl>\r\n");//行别级别号
						sb.append("    </RetInfArry>\r\n");
					}
				}
			} else {// 条件查询返回为空
				sb.append("     <ReturnStatus>F</ReturnStatus>\r\n");// 交易状态
				sb.append("     <array>\r\n");// 交易返回代码数组
				sb.append("        <Ret>\r\n");
				sb.append("           <ReturnCode>11</ReturnCode>\r\n");// 交易返回代码
				sb.append("           <ReturnMsg>查询不到该笔业务的验证码信息，请核对输入信息是否正确！</ReturnMsg>\r\n");// 交易返回信息
				sb.append("        </Ret>\r\n");
				sb.append("     </array>\r\n");
				sb.append("  </SYS_HEAD>\r\n");

				sb.append("  <BODY>\r\n");// 主体
				sb.append("   <array>\r\n");
				sb.append("    <RetInfArry>\r\n");//信息数组
				sb.append("      <EsalVerfNo>" + valcd + "</EsalVerfNo>\r\n");// 验证码
				sb.append("      <TxnDt>null</TxnDt>\r\n");// 交易日期
				sb.append("      <TxnNm>null</TxnNm>\r\n");// 交易名称
				sb.append("      <TxnBrId>null</TxnBrId>\r\n");// 机构号
				sb.append("      <TxnBrIdNm>null</TxnBrIdNm>\r\n");// 机构名称
				sb.append("      <AgntTlrNo>null</AgntTlrNo>\r\n");// 交易柜员号
				sb.append("      <ChkAhrTlrNo>null</ChkAhrTlrNo>\r\n");// 授权柜员号
				sb.append("      <BsnVchrNo>null</BsnVchrNo>\r\n");// 交易凭证
				sb.append("      <TxnNo>null</TxnNo>\r\n");// 交易码
				sb.append("      <RqsTms>0</RqsTms>\r\n");// 请求次数
				sb.append("      <TxnSeqNo>null</TxnSeqNo>\r\n");//交易流水号
				sb.append("      <PlsInf>null</PlsInf>\r\n");//附加信息
				sb.append("      <BnkTp>null</BnkTp>\r\n");//行别(移动银行和微信银行查询新增)
				sb.append("      <BnkNm>null</BnkNm>\r\n");//电子印章名称
//				sb.append("      <BrLvl>null</BrLvl>\r\n");//行别级别号
				sb.append("    </RetInfArry>\r\n");
				logger.info("valcode:"+valcode+",consumerSq:"+consumerSq+"交易失败,查询返回值为空！");
			}
			
			/*公共部分*/
			sb.append("   </array>\r\n");
			sb.append("   <TxnTotNum>"+TxnTotNum+"</TxnTotNum>\r\n");
			sb.append("  </BODY>\r\n");
			sb.append(" </service>\r\n");	
			
			if(!sb.toString().contains("查询不到该笔业务的验证码信息")){//交易成功补全报文
				int indexOf = sb.indexOf("    <RetInfArry>");	
				sb.insert(indexOf,"   <array>\r\n");
				sb.insert(indexOf,"  <BODY>\r\n");	
				sb.insert(indexOf,"  </SYS_HEAD>\r\n");
				sb.insert(indexOf,"     </array>\r\n");
				sb.insert(indexOf,"        </Ret>\r\n");
				sb.insert(indexOf,"           <ReturnMsg>交易成功</ReturnMsg>\r\n");
				sb.insert(indexOf,"           <ReturnCode>000000</ReturnCode>\r\n");
				sb.insert(indexOf,"        <Ret>\r\n");
				sb.insert(indexOf,"     <array>\r\n");
				sb.insert(indexOf,"     <ReturnStatus>S</ReturnStatus>\r\n");
				
				if(phone_num!=null&&!"".equals(phone_num)){//关于手机号查询才保存查询记录
					QueryLog ql=new QueryLog(consumerSq,valcode,oi,phone_num,new Timestamp(System.currentTimeMillis()),tellerid);
					nshRs.addQueryLog(ql);//查询成功保存查询记录					
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.info("交易信息处理异常，请联系后台人员！");
		}
		return sb.toString();
	}

}
