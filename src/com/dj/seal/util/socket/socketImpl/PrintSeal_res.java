package com.dj.seal.util.socket.socketImpl;

import java.sql.Timestamp;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.unitAndLevel.impl.UnitAndLevelDaoImpl;
import com.dj.seal.unitAndLevel.vo.UnitAndLevelVo;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class PrintSeal_res {
	static Logger logger = LogManager.getLogger(PrintSeal_res.class.getName());

	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}

	public static String printSeal_response(String xmlData) {
		NSHRecordService recordService = (NSHRecordService) getBean("nshRecordService");
		UnitAndLevelDaoImpl ud=(UnitAndLevelDaoImpl)getBean("UnitAndLevelDaoImpl");
		StringBuilder sb = null;
		try {
			NSHRecordPo ny = new NSHRecordPo();
			Document doc;
			doc = DocumentHelper.parseText(xmlData);
			Element req = doc.getRootElement();
			String VerfNo = null;// 新生成验证码
			String str = null;
			int n = 0;
			NSHRecordPo checkValcode2 = null;// 验证传入验证码是否存在

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
			String TxnTlrNo = req.element("BODY").elementText("TxnTlrNo");// 交易柜员
			String TxnBrId = req.element("BODY").elementText("TxnBrId");// 交易机构号
			String TxnNm = req.element("BODY").elementText("TxnNm");// 交易名称
			String ChkAhrTlrNo = req.element("BODY").elementText("ChkAhrTlrNo");// 授权柜员
			String BsnVchrNo = req.element("BODY").elementText("BsnVchrNo");// 凭证号
			String VerfNo1 = req.element("BODY").elementText("VerfNo");// 柜面请求传入的验证码
			String PlsInf = req.element("BODY").elementText("PlsInf");// 附加信息
			String printNum=req.element("BODY").elementText("PrtNum");//打印份数
			String bp3=req.element("BODY").elementText("PreRsrvFld3Nm");//行别名称
//			String bp4=req.element("BODY").elementText("PreRsrvFld4Nm");//行别级别号
			int PrtNum=0;
			String sealName="";
			if(bp3!=null&&!bp3.equals(""))
				sealName=bp3+"股份有限公司";
			
			if(printNum!=null&&!"".equals(printNum)){
				 PrtNum = Integer.parseInt(printNum);// 打印份数
			}
			
			DeptForm df = recordService.getDeptNameByOrgUnit(TxnBrId);
			String TxnBrIdNm = null;

			logger.info(TxnSeqNo+"待加密数据：" + TxnSeqNo + TxnNo + TxnDt);
			str = new ThreeDesHelper().encrypt(TxnSeqNo + TxnNo + TxnDt);// 3DES加密
			VerfNo = new MD5Helper().encode8BitBySalt(str, "gznsh").toUpperCase();// MD5加密生成验证码(要求字母都为大写)
			logger.info(TxnSeqNo+"有纸接口开始根据三要素生成的验证码:" + VerfNo);

			NSHRecordPo checkValcode = recordService.checkValcode(VerfNo);// 验证是否为旧三要素
			
			/*r_pNum可查询到历史表,checkValcode仅查询原始表,后者不为空则前者必不为空*/
			NSHRecordPo r_pNum = recordService.getMaxRequired_printNum(VerfNo);// 获取最大打印份数/请求次数

			if (checkValcode != null && !"".equals(checkValcode)) {// 旧三要素,更新printNum
				n = (Integer) r_pNum.getRequirednum();// 非第一次请求返回最大请求次数
				logger.info(TxnSeqNo+"旧三要素更新记录打印份数、请求次数");
				logger.info(VerfNo+"原最大打印份数："+r_pNum.getPrintnum()+"，最大请求次数："+n);
				n+=1;//请求次数同时更新
				if (PrtNum == 0) {// 未传打印份数,数据库份数+1,同时更新数据库printNum、requirednum
					PrtNum = (Integer) r_pNum.getPrintnum()+1;
					recordService.updatePrintNum(VerfNo,PrtNum, TxnSeqNo,n);// 更新requirednum、printNum,仅对原始表操作
				} else {// 传入了打印份数
					if(PrtNum==-1){//仅旧三要素更新附加信息
						logger.info(TxnSeqNo+"旧三要素更新附加信息");
						recordService.updateInfo_plus(TxnSeqNo, PlsInf,n);//可对原始表、历史表操作
						PrtNum = (Integer) r_pNum.getPrintnum();//用于返回给柜面

					}else{//与数据库的累加
						PrtNum=PrtNum + (Integer) r_pNum.getPrintnum();
						recordService.updatePrintNum(VerfNo, PrtNum, TxnSeqNo,n);// 旧三要素更新requirednum、printNum,仅对原始表操作					
					}

				}
				logger.info(TxnSeqNo + "有纸接口验证码:" + VerfNo + ",已存在，非第一次请求！！！");
			} else {// 新三要素
				if (VerfNo1 == null || "".equals(VerfNo1)||"null".equals(VerfNo1)) {// 如果没有传验证码则表示第一次办理此业务,屏蔽传入值为"null"情况
					ny.setRequirednum(1);// 初始请求次数为1
					ny.setValcode(VerfNo);// 第一次请求验证码
					n = 1;// 第一次请求返回次数为1
					if (PrtNum == 0) {// 未传打印份数,初始值默认1
						ny.setPrintnum(1);// 用户保存
						PrtNum = 1;// 用于返回柜面
					} else {// 传入了则保存为初始值,第一次请求不会传入打印份数为-1
						ny.setPrintnum(PrtNum);// 用户保存
					}
					logger.info(TxnSeqNo + "有纸接口新三要素未传入验证码,新生成:" + VerfNo);
				} else {// 传入验证码
					logger.info(TxnSeqNo + "有纸接口传入的验证码:" + VerfNo1);
					checkValcode2 = recordService.checkValcode(VerfNo1.toUpperCase());// 验证码是否存在
					if (checkValcode2 != null && !"".equals(checkValcode2)) {//限制查询范围仅在原始表
						logger.info(TxnSeqNo + "有纸传入的验证码"+VerfNo1+"存在");
						r_pNum = recordService.getMaxRequired_printNum(VerfNo1.toUpperCase());// 以传入valcode为准获取请求次数
						n = (Integer) r_pNum.getRequirednum();// 非第一次请求返回最大请求次数
						n += 1;// 递增1
						ny.setRequirednum(n);// 更新请求次数
						ny.setValcode(VerfNo1.toUpperCase());// 更新验证码(以传入的为准)
						VerfNo = VerfNo1.toUpperCase();// 返回传入的验证码

						if (PrtNum == 0 ) {// 未传打印份数,以数据库为准
							PrtNum = (Integer) r_pNum.getPrintnum();//返回值不含当前请求打印份数(即1) added comment on 20190321
							ny.setPrintnum(PrtNum+1);
						} else {// 传入了打印份数,与数据库的累加		
							PrtNum=PrtNum + (Integer) r_pNum.getPrintnum();
							ny.setPrintnum(PrtNum);// 与数据库的累加保存								
						}

					} else {
						logger.info(TxnSeqNo + "有纸接口传入的验证码:" + VerfNo1+ ",不存在,请核对是否输入正确！");
						VerfNo = VerfNo1.toUpperCase();// 任然保存传入的验证码
					}
				}
			}

			/* yyyyMMdd----->yyyy-MM-dd(未判断传入日期是否是8位-由柜面控制) */
			StringBuffer sf = new StringBuffer(TxnDt);
			StringBuffer insert1 = sf.insert(4, "-");
			StringBuffer insert2 = insert1.insert(7, "-");

			/* 保存有纸化单据 */
			String insert3 = insert2.toString();
			String TxnDt2 = insert3 + " 12:00:00";
			Timestamp ts = Timestamp.valueOf(TxnDt2);
			String rs = null;
			String tc = null;
			String rm = null;

			TxnBrIdNm = df.getDept_name();// 交易机构名称
			ny.setTranorgname(TxnBrIdNm);
			if (checkValcode == null || "".equals(checkValcode)) {// 如果是新三要素
				if (VerfNo1 != null && !VerfNo1.equals("")&&!"null".equals(VerfNo1)) {// 传入了验证码,屏蔽传入值为"null"情况
					logger.info(TxnSeqNo + "有纸接口新三要素且传入了验证码：" + VerfNo1);
					if (checkValcode2 != null && !"".equals(checkValcode2)) {// 验证传入验证码存在
						logger.info(TxnSeqNo + "有纸接口传入的验证码存在,保存数据！");
						ny.setCaseseqid(TxnSeqNo);
						ny.setTrancode(TxnNo);
						ny.setD_trandt(ts);
						ny.setTellerid(TxnTlrNo);
						ny.setOrgunit(TxnBrId);
						ny.setD_tranname(TxnNm);
						ny.setAuthtellerno(ChkAhrTlrNo);
						ny.setBp1(BsnVchrNo);
						ny.setBp2(oi);
						ny.setBp3(sealName);//电子印章名称,行别以柜面传入为准
						ny.setInfo_plus(PlsInf);
					    recordService.addRecordYZH(ny);// 保存有纸化单据到数据库
						rs = "S";
						tc = "000000";
						rm = "交易成功";
					} else {
						logger.info(TxnSeqNo + "有纸接口传入的验证码:" + VerfNo1+ "不存在,不保存数据！");
						rs = "F";
						tc = "00";
						rm = "交易失败，新三要素且传入的验证码不存在！";

						/* 机构不存在返回空值 */
						VerfNo = null;
						TxnSeqNo = null;
						TxnNo = null;
						insert3 = null;
						TxnTlrNo = null;
						TxnBrId = null;
						TxnBrIdNm = null;
						n = 0;
						PrtNum = 0;
					}
				} else {// 未传验证码,直接返回传入的数据并保存
					logger.info(TxnSeqNo + "有纸接口新三要素且未传入验证码,保存数据！");
					ny.setCaseseqid(TxnSeqNo);
					ny.setTrancode(TxnNo);
					ny.setD_trandt(ts);
					ny.setTellerid(TxnTlrNo);
					ny.setOrgunit(TxnBrId);
					ny.setD_tranname(TxnNm);
					ny.setAuthtellerno(ChkAhrTlrNo);
					ny.setBp1(BsnVchrNo);
					ny.setBp2(oi);
					ny.setBp3(sealName);
					ny.setInfo_plus(PlsInf);
					recordService.addRecordYZH(ny);// 保存有纸化单据到数据库
					rs = "S";
					tc = "000000";
					rm = "交易成功";
				}
			} else {// 如果是旧三要素查出数据返回,三要素都与数据库相同不需要查询出的
				logger.info(TxnSeqNo+"有纸接口旧三要素更新信息");
				rs = "S";
				tc = "000000";
				rm = "交易成功";
				
			}
			UnitAndLevelVo vo = ud.getUnitAndLevel(TxnBrId);
			if(vo!=null){
				if(vo.getLevelno().equals(0))//级别号为0的机构不返回机构名称					
					TxnBrIdNm="";
			}
			
			/* 响应报文 */
			sb = new StringBuilder();
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
			sb.append("      <VerfNo>" + VerfNo + "</VerfNo>\r\n");// 验证码
			sb.append("      <TxnSeqNo>" + TxnSeqNo + "</TxnSeqNo>\r\n");// 流水号
			sb.append("      <TxnNo>" + TxnNo + "</TxnNo>\r\n");// 交易码
			sb.append("      <TxnDt>" + insert3 + "</TxnDt>\r\n");// 交易日期
			sb.append("      <TxnTlrNo>" + TxnTlrNo + "</TxnTlrNo>\r\n");// 交易柜员
			sb.append("      <TxnBrId>" + TxnBrId + "</TxnBrId>\r\n");// 交易机构号
			sb.append("      <TxnBrIdNm>" + TxnBrIdNm + "</TxnBrIdNm>\r\n");// 交易机构名称
			sb.append("      <RqsTms>" + n + "</RqsTms>\r\n");// 请求次数
			sb.append("      <PrtNum>" + PrtNum + "</PrtNum>\r\n");// 打印份数
			sb.append("      <BnkNm>"+sealName+"</BnkNm>\r\n");//电子印章名称
			sb.append("   </BODY>\r\n");
			sb.append("</service>\r\n");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("交易信息处理异常，请联系后台人员！");
		}
		return sb.toString();
	}
}
