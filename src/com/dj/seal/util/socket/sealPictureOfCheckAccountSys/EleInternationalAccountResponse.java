package com.dj.seal.util.socket.sealPictureOfCheckAccountSys;

/**
 * 	获取印章图片
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import oracle.sql.CLOB;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import srvSeal.SrvSealUtil;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.encrypt.Base64;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.table.SealBodyUtil;

public class EleInternationalAccountResponse {
	static Logger logger=LogManager.getLogger(EleInternationalAccountResponse.class.getName());
	private static BaseDAOJDBC base_dao;

	public BaseDAOJDBC getBase_dao() {
		return base_dao;
	}

	public void setBase_dao(BaseDAOJDBC base_dao) {
		this.base_dao = base_dao;
	}

	public static String getSealPic(String xmlData){
		SrvSealUtil srv_seal=srv_seal();
		Document doc;
		StringBuffer sb=null;
		int nObjID=0;
		try {
			doc = DocumentHelper.parseText(xmlData);
			Element ele = doc.getRootElement();
			/*获取系统头元素*/
			String ss = ele.element("SYS_HEAD").elementText("ServiceScene");// 服务代码(同输入)
			String sc = ele.element("SYS_HEAD").elementText("ServiceCode");// 服务场景(同输入)
			String ci = ele.element("SYS_HEAD").elementText("ConsumerId");// 消费系统编号(同输入)
			String oi = ele.element("SYS_HEAD").elementText("OrgConsumerId");// 发起方系统编号(渠道号)
			String cs = ele.element("SYS_HEAD").elementText("ConsumerSeqNo");// 消费系统流水号(同输入)
			String os = ele.element("SYS_HEAD").elementText("OrgConsumerSeqNo");// 业务流水号(同输入)
			String sn = ele.element("SYS_HEAD").elementText("ServSeqNo");// 服务系统流水号(同输入)
			String td = ele.element("SYS_HEAD").elementText("TranDate");// 交易日期(同输入)
			String tt = ele.element("SYS_HEAD").elementText("TranTime");// 交易时间(同输入)
			
			/*获取BODY元素*/
			String seqNo = ele.element("BODY").elementText("TxnSeqNo");//流水号(多个时以","分开)
			String tranNo = ele.element("BODY").elementText("EleRcnclTxnNo");//交易码(多个时以,分开)
			String tranDate = ele.element("BODY").elementText("TxnDt");//交易日期(多个时以,分开)
			String operator = ele.element("BODY").elementText("MnpltPrsnNm");//登录对账系统人名字
			String orgNo = ele.element("BODY").elementText("TxnBrId");//机构号
			String tranName = ele.element("BODY").elementText("TxnNm");//交易名称
			
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
			
			/*返回数组部分(动态扩展)*/
			SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");		
			ISealBodyService seal_srv = (ISealBodyService) getBean("ISealBodyService");
			EleInternationalAccountDaoImpl daoImpl = (EleInternationalAccountDaoImpl) getBean("EleAccountCheckDao");
			NSHRecordService recordService = (NSHRecordService) getBean("nshRecordService");
			SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
			EleInternationalAccountPo eac=new EleInternationalAccountPo();
			String sealName=null;//印章名称
			String sealData=null;//印章数据
			String threeDesEnc=null;//3DES加密后值
			String md5Enc=null;//MD5加密后值(即验证码)
			String base64Enc=null;//base64编码数据
			SysDepartment dept=null;// 根据第i+1个机构号获取部门编号、父部门编号
			String parent_No=null;//取得父部门编号
			int docProperty1=1;//添加验证码返回值
			int docProperty2=1;//添加机构名称返回值
			DeptForm dtForm=null;// 根据机构号查找机构名称
			SealBody obj=null;//印章对象引用
			String checkAccountChannel = DJPropertyUtil.getPropertyValue("checkAccountChannel").trim();//对账(网银)系统渠道号(modified 20181212)
			String internationalAccountChannel = DJPropertyUtil.getPropertyValue("internationalAccountChannel").trim();//国际结算系统渠道号(modified 20181212)
			String cashManageChannel = DJPropertyUtil.getPropertyValue("cashManageChannel").trim();//现金管理平台渠道号(modified 20181212)
			String outPath=DJPropertyUtil.getPropertyValue("electronicAccountPicture")+new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());//印章转为图片后保存路径
			File file=new File(outPath);
			if(!file.exists()){//创建文件夹
				file.mkdirs();
			}

			/* 拆分批量请求印章时数据(长度必一致) */
			String[] orgN = orgNo.split(",");
			String[] seqN = seqNo.split(",");
			String[] tranN = tranNo.split(",");
			String[] tranDt = tranDate.split(",");
			
			/*每次查询一个印章并生成对应验证码加到动态区域，将印章转为图片并放入返回数组以实现数组的动态扩展*/
			for(int i=0;i<orgN.length;i++){
				int j=0;//判断所有印章中是否存在对账系统所需印章(必须放在最外层for循环内,每次与seals.size()比较过后重置为0)
				dept= deptService.getParentNo(orgN[i]);	
				if(dept!=null){
					parent_No=dept.getDept_parent();					
				}else{
					logger.info("机构号:"+orgN[i]+"部门不存在!");
					return "00000001";				
				}
				SysDepartment byparent_No=deptService.getDeptNo(parent_No);//根据父部门编号向上查询信息				
				while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
					byparent_No=deptService.getDeptNo(byparent_No.getDept_parent());//父部门编号作为部门编号查询
				}
				List<SealBody> seals = seal_srv.showSealBodyByDeptNo2(byparent_No.getDept_no());//根据部门编号查询印章信息
				if(seals!=null&&!"".equals(seals)){
					for (SealBody seal : seals){					
						if(oi.equals(checkAccountChannel)&&seal.getSeal_type().equals("普通公章")){//电子对账系统保存印章类型,含村行
							sealName=seal.getSeal_name();
							break;
						}else if((oi.equals(internationalAccountChannel)||oi.equals(cashManageChannel))&&seal.getSeal_type().equals("公章-法人章")){//国结-仅总行(修改村行电子印章后包含村行)/现管系统
							sealName=seal.getSeal_name();
							break;
						}else{//没有系统需求的印章
							j++;	
							continue;	
						}
					}
				}else{//数据库没有对应机构印章
					sb.append("     <ReturnStatus>F</ReturnStatus>\r\n");// 交易状态
					sb.append("     <array>\r\n");// 交易返回代码数组
					sb.append("        <Ret>\r\n");
					sb.append("           <ReturnCode>55</ReturnCode>\r\n");// 交易返回代码
					sb.append("           <ReturnMsg>部门没有印章！</ReturnMsg>\r\n");// 交易返回信息
					sb.append("        </Ret>\r\n");
					sb.append("     </array>\r\n");
					sb.append("  </SYS_HEAD>\r\n");
					sb.append("  <BODY>\r\n");// 主体
					sb.append("   <array>\r\n");
					sb.append("		<ImgDataArry>");
					sb.append("			<SlPctrDataInf>null</SlPctrDataInf>");
					sb.append("		</ImgDataArry>");
					sb.append("   </array>\r\n");
					sb.append("  </BODY>\r\n");
					sb.append(" </service>\r\n");
					logger.info("【" + seqN[i]+"/"+byparent_No.getDept_no() + "】部门没有印章,请联系管理员配置!");
					return sb.toString();//返回失败报文
				}
				
				if(j==seals.size()){//遍历所有印章数据没有所需印章
					sb.append("     <ReturnStatus>F</ReturnStatus>\r\n");// 交易状态
					sb.append("     <array>\r\n");// 交易返回代码数组
					sb.append("        <Ret>\r\n");
					sb.append("           <ReturnCode>33</ReturnCode>\r\n");// 交易返回代码
					sb.append("           <ReturnMsg>没有请求系统需求的印章！</ReturnMsg>\r\n");// 交易返回信息
					sb.append("        </Ret>\r\n");
					sb.append("     </array>\r\n");
					sb.append("  </SYS_HEAD>\r\n");
					sb.append("  <BODY>\r\n");// 主体
					sb.append("   <array>\r\n");
					sb.append("		<ImgDataArry>");
					sb.append("			<SlPctrDataInf>null</SlPctrDataInf>");
					sb.append("		</ImgDataArry>");
					sb.append("   </array>\r\n");
					sb.append("  </BODY>\r\n");
					sb.append(" </service>\r\n");
					logger.info("【"+ seqN[i]+"/"+byparent_No.getDept_no()+"】部门没有请求系统需求的印章,请联系管理员配置!");
					return sb.toString();//返回失败报文
				}
				
				/*有所需印章则转换返回*/
				dtForm = recordService.getDeptNameByOrgUnit(orgN[i]);
				logger.info("【"+seqN[i]+"/"+oi+"】待加密数据:"+seqN[i]+tranN[i]+tranDt[i]);
				threeDesEnc=new ThreeDesHelper().encrypt(seqN[i]+tranN[i]+tranDt[i]);
				md5Enc=new MD5Helper().encode8BitBySalt(threeDesEnc,"gznsh").toUpperCase();//转为大写
				logger.info("【"+seqN[i]+"/"+oi+"】MD5加密生成验证码:"+md5Enc);
					
				File sealFile=null;
				if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")!=-1)
					sealFile=new File(Constants.getProperty("save_path") +File.separatorChar+ "doc\\seals\\" + sealName + ".sel");// 读取印章文件;
				else
					sealFile=new File(Constants.getProperty("saveseal")+File.separatorChar+sealName+".sel");
				if (!sealFile.isFile()) {// 判断印章文件是否存在
					logger.info("【"+seqN[i]+"/"+oi+"】印章文件:"+sealFile+"不存在,重新生成 ");
					obj = new SealBody();
					obj.setSeal_path(sealFile.getAbsolutePath());
					obj.setSeal_name(sealName);
					objSeal.dbToFile2(obj);//调用新增接口
				}
				/*印章添加验证码并转为jpg图片的base64值*/
				nObjID = srv_seal.openData(null);//打开空文档(默认成功)		
				logger.info("【"+seqN[i]+"/"+oi+"】nObjID:"+nObjID);
				if(nObjID<=0){//打开文档失败中止程序进行
					logger.info("【"+seqN[i]+"/"+oi+"】打开文档失败,返回值nObjID:"+nObjID);
					continue;					
				}
				docProperty1 = srv_seal.setDocProperty(nObjID, "valcode", md5Enc);//添加验证码
				docProperty2 = srv_seal.setDocProperty(nObjID, "orgUnitName", dtForm.getDept_name());//国际结算添加机构名称		
				logger.info("【"+seqN[i]+"/"+oi+"】添加验证码返回值:"+docProperty1+";添加机构名称返回值:"+docProperty2);
				sealData ="STRDATA:"+getSealData(sealName);//获取印章数据并以STRDATA打头(so接口要求)
//				String outPath2=outPath+File.separatorChar+seqN[i]+"-"+UUID.randomUUID()+".jpg";
				String outPath2=outPath+File.separatorChar+seqN[i]+"-"+System.currentTimeMillis()+".jpg";//图片保存路径							
				String sealEx = srv_seal.decSealEx(nObjID, sealData, "", outPath2, 0);//印章保存为jpg图片
				logger.info("【"+seqN[i]+"/"+oi+"】decSealEx:"+sealEx);
				srv_seal.saveFile(nObjID, "", "", 0);//关闭打开的文档
							
				/*将图片转为base64值*/
				base64Enc = base64Enc(outPath2);
				
				/*动态组装数组部分报文-此部分可能有多条*/
				sb.append("		<ImgDataArry>");
				sb.append("			<SlPctrDataInf>"+base64Enc+"</SlPctrDataInf>");
				sb.append("		</ImgDataArry>");	
				
				StringBuffer sbf=new StringBuffer(tranDt[i]);
				StringBuffer insert1 = sbf.insert(4, "-");
				StringBuffer insert2 = insert1.insert(7, "-");
				
				eac.setChannelNo(oi);
				eac.setOrgConsumerSeqNo(seqN[i]);
				eac.setTranNo(tranN[i]);			
				eac.setTranOrgName(dtForm.getDept_name());
				eac.setTranDate(Timestamp.valueOf(insert2+" 00:00:00"));
				eac.setValcode(md5Enc);
				eac.setOperator(operator);
				eac.setTranOrg(orgN[i]);
				eac.setTranName(tranName);
				eac.setSealPictureData("nothing");//不保存大数据字段
				eac.setCreateTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())));
				logger.info("【"+seqN[i]+"/"+oi+"】保存数据...");					
				int accountCheckRecord = daoImpl.accountCheckRecord(eac);//保存电子对账系统记录
				if(accountCheckRecord>0)
					logger.info("【"+seqN[i]+"/"+oi+"】保存数据成功!");
				else
					logger.info("【"+seqN[i]+"/"+oi+"】保存数据失败!");
			}
			
			/*报文尾部*/
			sb.append("   </array>\r\n");
			sb.append("  </BODY>\r\n");
			sb.append(" </service>\r\n");
			
			/*补全成功交易报文*/
			int indexOf = sb.indexOf("		<ImgDataArry>");
			sb.insert(indexOf,"   <array>\r\n");//BODY中
			sb.insert(indexOf,"  <BODY>\r\n");	
			sb.insert(indexOf,"  </SYS_HEAD>\r\n");
			sb.insert(indexOf,"     </array>\r\n");//SYS_HEAD中
			sb.insert(indexOf,"        </Ret>\r\n");
			sb.insert(indexOf,"           <ReturnMsg>交易成功</ReturnMsg>\r\n");
			sb.insert(indexOf,"           <ReturnCode>000000</ReturnCode>\r\n");
			sb.insert(indexOf,"        <Ret>\r\n");
			sb.insert(indexOf,"     <array>\r\n");//SYS_HEAD中
			sb.insert(indexOf,"     <ReturnStatus>S</ReturnStatus>\r\n");
		} catch (DocumentException e) {
			e.printStackTrace();
			boolean alive = Thread.currentThread().isAlive();
			if(alive&&nObjID>0){
				srv_seal.saveFile(nObjID, "", "", 0);// 关闭打开的文档			
			}
			return "00000010";//程序处理异常
		} catch (Exception e) {// 获取印章数据
			e.printStackTrace();
			return "00000011";//获取印章数据异常
		}
		return sb.toString();//返回成功报文
	}
	
	/**
	 * 工具方法
	 * @param bean_name
	 * @return
	 */
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	
	/**
	 * 获取数据库印章数据
	 * @param sealName 印章名称
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private static String getSealData(String sealName) throws IOException, SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(SealBodyUtil.SEAL_DATA);
		sb.append(" from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.SEAL_NAME);
		sb.append("='").append(sealName).append("'");
		//logger.info("getClobSql:"+sb.toString());
		CLOB clob = null;
		InputStream input=null;
		BufferedInputStream bis=null;
		String baseV=null;
		try {
			clob = base_dao.getClob2(sb.toString(), SealBodyUtil.SEAL_DATA);
			input = clob.getAsciiStream();
			bis=new BufferedInputStream(input);//使用缓冲流,新增
			int len = (int)clob.length();
			byte[] by = new byte[len];
			int i ;
			while(-1 != (i = bis.read(by, 0, by.length))) {
				input.read(by, 0, i);
			}
		 baseV = new String(by);//印章base64值
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (input != null)
				input.close();
			if (bis != null)
				bis.close();
		}
		return baseV;
	}
	
	/**
	 * 工具类
	 * @return
	 */
	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		String fontPath = Constants.getProperty("fontspath");
		int setFont = srv_seal.setValue(0,"SET_FONTFILES_PATH", fontPath);
		logger.info("setFont:" + setFont);
		return srv_seal;
	}
	
	/**
	 * base64编码
	 * @param filePath 图片路径
	 * @return	base64编码数据
	 */
	private static String base64Enc(String filePath){
		File file=new File(filePath);
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(file);
			byte[] data=new byte[fis.available()];
			fis.read(data);
//			fis.close();//旧方式释放
			return Base64.encodeToString(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {//data	
			e.printStackTrace();
		}finally{//在finally中释放资源避免产生异常后资源对象(fis)无法释放20181018(未投)
			try {
				if(fis!=null)
					fis.close();				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
}
