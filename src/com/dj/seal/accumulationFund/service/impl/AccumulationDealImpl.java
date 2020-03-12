package com.dj.seal.accumulationFund.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import srvSeal.SrvSealUtil;


import com.dj.seal.accumulationFund.service.api.IAccumulationDeal;
import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.hotel.dao.impl.NSHRecordDaoImpl;
import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.encrypt.FileUtil;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.ThreeDesHelper;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.sign.SignUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;




/**
 * 公积金回单处理
 * @author WB000520
 *保存盖章失败文件:①追加|0表示没有所需印章导致盖章失败
 *				  ②追加|1表示部门没有印章导致盖章失败
 *                ③追加|2表示保存盖章后的文件失败
 *                ④追加|3表示调用盖章接口失败
 *                ⑤追加|4表示打开未盖章文件失败
 */
public class AccumulationDealImpl extends BaseDAOJDBC implements IAccumulationDeal {//定时触发
	static Logger logger=LogManager.getLogger(AccumulationDealImpl.class.getName());
	SrvSealUtil srv_seal=srv_seal();
	
	public int addSealToAccumulation() {
		NSHRecordService recordService = (NSHRecordService) getBean("nshRecordService");
		ISealBodyService seal_srv = (ISealBodyService) getBean("ISealBodyService");
		SysDeptService deptService = (SysDeptService) getBean("ISysDeptService");
		CertSrv certsrv = (CertSrv) getBean("CertService");
		NSHRecordPo recordPo = new NSHRecordPo();
		DesUtils des=null;
		NSHRecordDaoImpl nshDaoImpl=(NSHRecordDaoImpl)getBean("nshRecordDao");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
		Date dt=new Date(System.currentTimeMillis());
		String nowTime= sdf.format(dt);//系统当前日期
		String pathNoSeal = DJPropertyUtil.getPropertyValue("accumulationFilePath_NoSeal");
		String pathAddSeal=DJPropertyUtil.getPropertyValue("accumulationFilePath_AddSeal");
		String filePathNoSeal=pathNoSeal+nowTime;//未盖章回单目录(由ftp系统创建日期文件夹,前面部分无纸化创建)
		File file1=new File(filePathNoSeal+File.separatorChar+"HDFileInfo.txt");//清单目录
		String saveConvertPdf=null;//txt转pdf保存绝对路径
		File file3=new File(pathAddSeal+nowTime);//盖章后pdf文件保存目录
		String savePdf=null;//盖章后pdf文件绝对路径
		boolean flag=false;//返回标志
		FileReader fr=null;//读
		BufferedReader br=null;//缓冲
		String tempString=null;//读取清单临时数据
		String[] split=null;//清单拆分后数据
		String str =null;//3DES加密后
		String valcode=null;//MD5加密后(即验证码)
		SysDepartment dept=null;// 根据机构号获取部门编号、父部门编号
		DeptForm dtForm = null;// 根据机构号查找机构名称
		String sealName=null;//印章名称
		String certName=null;//证书名称
		String certPwd=null;//证书密码
		int ret =0;//盖章返回值
		int fileRet=0;//保存pdf文件返回值
		StringBuffer sb=new StringBuffer();
		FileWriter out=null;//保存未盖章文件名称
		BufferedWriter bw=null;//缓冲流
		int i=0,j=0;//判断盖章成功数目和读取文件数目是否一致
		boolean flag1=false;//复制清单文件返回标识
		boolean flag2=false;//txt转pdf返回标识
		int openObj=0;//打开文件标识
		try {
			logger.info("清单文件目录:"+file1.getAbsolutePath());
			if(!file1.exists()){
				return -1;
			}
			if(!file3.exists()){
				logger.info("创建保存盖章回单文件夹...");
				file3.mkdirs();
			}
			flag1=copyFile(filePathNoSeal,file3.getAbsolutePath());//复制txt文件到盖章后文件保存目录
			if(flag1)
				flag2=txtToPdf(filePathNoSeal,filePathNoSeal);//txt转pdf
			else
				return -2;
//			if(!flag2)
//				return -3;
			if(flag2)
				return 0;
			fr=new FileReader(file1);
			br=new BufferedReader(fr);
			out=new FileWriter(filePathNoSeal+File.separatorChar+"addSealFailedFile.txt");
			bw=new BufferedWriter(out);
			while ((tempString = br.readLine()) != null) {// 读取回单清单
				List<SealBody> seals = null;//部门印章(置while内避免sealNum错乱)
				int sealNum = 0;//是否有请求印章标识(必须放在while循环内)
				i++;//每读一个文件加1
				split = tempString.split("\\|");
				str = new ThreeDesHelper().encrypt(split[1] + split[2]+ split[3]);
				valcode = new MD5Helper().encode8BitBySalt(str, "gznsh").toUpperCase();
				dtForm = recordService.getDeptNameByOrgUnit(split[4]);
				dept= deptService.getParentNo(split[4]);
				if (dept != null)
					seals = seal_srv.showSealBodyByDeptNo2(dept.getDept_no());// 根据部门编号查询
				else
					logger.info(split[4]+"机构号错误,不存在！");
				if (seals != null && seals.size() != 0) {
					for (SealBody seal : seals) {
						if (seal.getSeal_type().equals("公章-法人章")) {//此类型交易仅涉及总行业务公章
							sealName = seal.getSeal_name();
							Cert cert = certsrv.getObjByNo(seal.getKey_sn());
							certName = cert.getCert_name();
							des = new DesUtils();
							certPwd = des.decrypt(cert.getCert_psd());
						} else {
							sealNum++;
						}
						if (sealNum == seals.size()) {
							logger.info(split[0] + "交易,机构"+split[4]+"没有请求的印章!");
							sb.append(split[0]+"|0\r\n");
							flag=true;
						}
					}
				} else {
					logger.info(split[0] + "交易,机构"+split[4]+"部门没有印章!");//①部门没有印章；②传入部门核心机构号不存在
					sb.append(split[0]+"|1\r\n");
					continue;//没有印章中止执行当前文件盖章
				}
				if(flag)
					continue;//请求的印章没有,中止程序进行
				saveConvertPdf = filePathNoSeal + File.separatorChar
						+ split[0].substring(0, split[0].lastIndexOf(".")) + ".pdf";
				savePdf =file3.getAbsolutePath()+ File.separatorChar+ split[0].substring(0,split[0].lastIndexOf("."))+ ".pdf";

				if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {// windows系统
					openObj = openObj(filePathNoSeal+File.separatorChar+split[0], 0, 0);//直接打开txt文件盖章
					int login = srv_seal.login(openObj, 2, "nsh", "1");
					logger.info("loginRet:" + login);
					if(openObj>0){
						/* 印章上添加验证码 和机构号 */
						srv_seal.setDocProperty(openObj, "valcode", valcode);
						srv_seal.setDocProperty(openObj, "orgUnitName",
								dtForm.getDept_name());
						ret = addSealWin(srv_seal, openObj, certName, sealName);// 盖章
						if (ret == 1) {// 盖章成功
							fileRet = srv_seal.saveFile(openObj, savePdf, "pdf", 0); // 保存pdf文件并关闭打开的文件
							if (fileRet == 1) {
								j++;// 每盖章且保存成功一个文件加1
								SimpleDateFormat time = new SimpleDateFormat(
										"yyyyMMddHHmmss");
								String id = time.format(new Date()) + "-"
										+ UUID.randomUUID().toString();
								recordPo.setCid(id);
								recordPo.setCuploadtime(new Timestamp(System
										.currentTimeMillis()));// 上传时间
								recordPo.setCaseseqid(split[2]);// 流水号
								recordPo.setTrancode(split[3]);// 交易码
								recordPo.setD_trandt(Timestamp
										.valueOf(TrandeDate(split[1])));// 交易日期
								recordPo.setOrgunit(split[4]);// 机构号
								recordPo.setTranorgname(dtForm.getDept_name());// 机构名称
								recordPo.setD_tranname(split[0]);// 交易名称保存回单文件名
								recordPo.setValcode(valcode);// 验证码
								recordPo.setStatus("2");// 公积金回单标志
								nshDaoImpl.addRecordYZH(recordPo);// 保存数据
							} else {
								sb.append(split[0] + "|2\r\n");
							}
						} else {
							logger.info(split[0] + "盖章失败！");
							sb.append(split[0] + "|3\r\n");
						}	
					}else{
						logger.info("打开:" + split[0] + "失败,返回值:"+ openObj);
						sb.append(split[0] + "|4\r\n");
						continue;// 继续执行下一个文件盖章
					}
				} else {// Linux系统
					openObj = openObj(saveConvertPdf, 0, 0);//打开转换后的pdf文件盖章
					logger.info("openObj:" + openObj);
					if (openObj > 0) {
						srv_seal.setDocProperty(openObj, "valcode", valcode);
						srv_seal.setDocProperty(openObj, "orgUnitName",dtForm.getDept_name());
						int retLinux = addSealLinux(srv_seal, openObj,certName, certPwd, sealName, savePdf);
						if (retLinux == 1) {
							j++;// 每盖章且保存成功一个文件加1
							SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
							String id = time.format(new Date()) + "-"+ UUID.randomUUID().toString();
							recordPo.setCid(id);
							recordPo.setCuploadtime(new Timestamp(System.currentTimeMillis()));// 上传时间
							recordPo.setCaseseqid(split[2]);// 流水号
							recordPo.setTrancode(split[3]);// 交易码
							recordPo.setD_trandt(Timestamp.valueOf(TrandeDate(split[1])));// 交易日期
							recordPo.setOrgunit(split[4]);// 机构号
							recordPo.setTranorgname(dtForm.getDept_name());// 机构名称
							recordPo.setD_tranname(split[0]);// 交易名称保存回单文件名
							recordPo.setValcode(valcode);// 验证码
							recordPo.setStatus("2");// 公积金回单标志
							nshDaoImpl.addRecordYZH(recordPo);// 保存数据
						} else {
							logger.info(split[0] + "盖章失败！");
							sb.append(split[0] + "|3|"+retLinux+"\r\n");
						}
					} else {
						logger.info("打开:" + saveConvertPdf + "失败,返回值:"+ openObj);
						sb.append(split[0] + "|4\r\n");
						continue;// 继续执行下一个文件盖章
					}
				}
			}
			bw.write(sb.toString());//保存未盖章文件名的文件
			br.close();
			fr.close();
			bw.close();
			out.close();
		}catch (Exception e){//所有异常
			logger.info("回单处理异常,异常信息:");
			e.printStackTrace();
			boolean alive = Thread.currentThread().isAlive();
			if(alive){//线程如已销毁openObj对象可被其它的线程使用
				if(openObj>0)
					srv_seal.saveFile(openObj,"","",0);
			}	
		}
		return i-j;
		
	}
	
	/*windows服务端盖章*/
	private static int addSealWin(SrvSealUtil srv_seal, int nObjID, String certname, String sealname) throws Exception{
		String bpath = bpath();
		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		
		File files = new File(bpath + "doc\\seals\\" + sealname + ".sel");// 读取印章文件
		logger.info(bpath + "doc\\seals\\" + sealname + ".sel");
		if (!files.isFile()) {// 判断印章文件是否存在
			logger.info("印章文件不存在,重新生成 ");
			SealBody obj = new SealBody();
			obj.setSeal_path(bpath + "doc\\seals\\" + sealname + ".sel");
			obj.setSeal_name(sealname);
			objSeal.dbToFile2(obj);//调用新增接口
		}
		
		/*绝对坐标盖章*/
		String sealData = objSeal.getSealData(sealname);
		//String pagesData = "0,21800,3,3,11900,"+sealData;//绝对坐标盖章(统一位置)
		
		/*规则信息盖章*/
		String ruleInfo = "AUTO_ADD:0,-1,4500,-700,255,机构名)|(8,";
		String pagesData = ruleInfo + bpath + "doc\\seals\\" + sealname+ ".sel";
		logger.info("规则信息:"+pagesData);
		File file = new File(bpath + "doc\\certs\\" + certname + ".pfx");
		if (!file.isFile()) {// 证书不存在重新从数据库下载
			logger.info("证书不存在 ");
			Cert obj = new Cert();
			obj.setCert_detail(bpath + "doc\\certs\\" + certname + ".pfx");
			obj.setCert_name(certname);
			CertSrv srv_file = (CertSrv) getBean("CertService");
			srv_file.dbToFile(obj);
		}
		certname = bpath + "doc\\certs\\" + certname + ".pfx";
		int ret = srv_seal.addSeal(nObjID, pagesData, certname, "AUTO_ADD_SEAL_FROM_PATH");
		if (ret == 1) {
			logger.info("加盖印章成功！");
		} else {
			logger.info("加盖印章失败：" + ret);
		}
		return ret;
	}
	
	/*linux服务端盖章*/
	private static int addSealLinux(SrvSealUtil srv_seal, int nObjID, String certname,String certpwd,String sealname,String savePath) throws Exception{
		SealBodyServiceImpl objSeal = (SealBodyServiceImpl) getBean("ISealBodyService");
		File files = new File(Constants.getProperty("saveseal")+ File.separatorChar + sealname + ".sel");// 读取印章文件
		if (!files.isFile()) {// 判断印章文件是否存在
			logger.info("印章文件不存在,重新生成 ");
			SealBody obj = new SealBody();
			obj.setSeal_path(Constants.getProperty("saveseal")+ File.separatorChar + sealname + ".sel");
			obj.setSeal_name(sealname);
			objSeal.dbToFile2(obj);//调用新增接口
		}
		/*绝对坐标盖章*/
//		String sealData = objSeal.getSealData(sealname);//印章数据
//		String ruleInfo = "0,21800,5,5,11900,"+sealData;// 绝对坐标盖章(统一位置)
		
		/*规则信息盖章*/
		String ruleInfo = "AUTO_ADD:0,-1,4500,-700,255,机构名)|(8,";
		String pagesData = ruleInfo +Constants.getProperty("saveseal")+ File.separatorChar + sealname + ".sel";
		logger.info("规则信息:"+pagesData);
		certname = Constants.getProperty("savecert") + File.separatorChar+ certname + ".pfx";
		int as = srv_seal.addSeal(nObjID, pagesData, "","AUTO_ADD_SEAL_FROM_PATH");
		logger.info("addSeal返回结果：" + as);
		if(as!=1){
			srv_seal.saveFile(nObjID, "", "", 0);//盖章失败关闭打开的文档
			return as;
		}
		byte[] pdfbs = srv_seal.getData(nObjID);
		byte[] data = srv_seal.getSignSHAData(nObjID);
		int pos = srv_seal.getSignPos(nObjID);
		KeyStore store = getKeyStore(certname, certpwd);
		byte[] singedData = SignUtil.signP7Bytes(data, store, certpwd);
		byte[] savebs = chgBs(pdfbs, pos, singedData);
		FileUtil fu = new FileUtil();
		fu.save(savebs, savePath);
		int s = srv_seal.saveFile(nObjID, "", "",0);//关闭打开的文档
		if (s == 1) {
			logger.info("linux after seal-closeDoc success0");
			return 1;
		} else {
			logger.info("linux after seal-closeDoc error0");
			return 0;
		}
		
	}
	
	/*从配置文件读取印章路径*/
	public static String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type = Constants.getProperty("is_type");
			if (is_type.equals("1")) {// 从配置文件读取路径
				bpath = Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bpath;
	}
	
	/*获取控件接口方法*/
	private static SrvSealUtil srv_seal() {
		SrvSealUtil srv_seal = (SrvSealUtil) getBean("srvSeal");
		if (srv_seal == null) {
			srv_seal = new SrvSealUtil();
		}
		return srv_seal;
	}
	
	/*工具方法*/
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	
	/*yyyyMMdd转yyyy-MM-dd*/
	public String TrandeDate(String ori){
		StringBuffer sf = new StringBuffer(ori);
		StringBuffer insert1 = sf.insert(4, "-");
		StringBuffer insert2 = insert1.insert(7, "-");
		String TxnDt2 = insert2 + " 12:00:00";
		return TxnDt2;
	}
	
	private static KeyStore getKeyStore(String pfxPath, String psd)
			throws Exception {
		KeyStore store = KeyStore.getInstance("PKCS12",
				new BouncyCastleProvider());
		InputStream in = new FileInputStream(pfxPath);
		store.load(in, psd.toCharArray());// 加载证书库
		in.close();
		return store;
	}
	
	private static byte[] chgBs(byte[] pdfbs, int pos, byte[] singedData)throws Exception {
		byte[] insertBs = new byte[singedData.length * 2];
		for (int i = 0; i < singedData.length; i++) {
			byte[] temp = bt2bt(singedData[i]);
			insertBs[i * 2] = temp[0];
			insertBs[i * 2 + 1] = temp[1];
		}
		int length = insertBs.length;
		for (int i = pos; i < pos + length; i++) {
			pdfbs[i] = insertBs[i - pos];
		}
		return pdfbs;
	}
	
	private static byte[] bt2bt(byte b) throws Exception {
		byte[] rb = new byte[2];
		int i1 = (0xf0 & b) >> 4;
		int i2 = 0x0f & b;
		rb[0] = toByte(i1);
		rb[1] = toByte(i2);
		return rb;
	}
	
	private static byte toByte(int i) throws Exception {
		byte b;
		switch (i) {
		case 0:
			b = '0';
			break;
		case 1:
			b = '1';
			break;
		case 2:
			b = '2';
			break;
		case 3:
			b = '3';
			break;
		case 4:
			b = '4';
			break;
		case 5:
			b = '5';
			break;
		case 6:
			b = '6';
			break;
		case 7:
			b = '7';
			break;
		case 8:
			b = '8';
			break;
		case 9:
			b = '9';
			break;
		case 10:
			b = 'a';
			break;
		case 11:
			b = 'b';
			break;
		case 12:
			b = 'c';
			break;
		case 13:
			b = 'd';
			break;
		case 14:
			b = 'e';
			break;
		default:
			b = 'f';
			break;
		}
		return b;
	}
	
	public int openObj(String openPath,int nFS1,int nFS2){
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1){
			return srv_seal.openObj(openPath, nFS1, nFS2);
		}
		return srv_seal.openObj(openPath, nFS1);
	}
	
	/**
	 *复制清单文件到盖章文件保存目录
	 *@param originPath 源文件路径
	 *@param savePath 文件保存路径
	 */
	@Override
	public boolean copyFile(String originPath,String savePath) {
		File file1=new File(originPath);
		File file2=new File(savePath);
		FileReader fr = null;
		try {
			BufferedReader	br=null;
			String str=null;
			FileWriter fw =null;
			BufferedWriter bw=null;
			if (file1.exists()) {//文件夹存在
				logger.info("复制txt文件开始...");
				String[] list = file1.list();
				for (int i = 0; i < list.length; i++) {
					if (!isExist(file1.getAbsolutePath(),list[i])&&list[0].contains(".txt")) {//仅复制清单文件
						originPath =file1.getAbsolutePath()+ File.separatorChar + list[i];// 读取的文件
						fr = new FileReader(originPath);
						br = new BufferedReader(fr);
						savePath =file2.getAbsolutePath()+ File.separatorChar + list[i];// 保存文件路径
						fw = new FileWriter(savePath);//覆盖原有内容重新生成文件
						bw = new BufferedWriter(fw);
						while ((str = br.readLine()) != null) {
							bw.write(str+"\r\n");// 写入文件
							bw.flush();
						}
					}
				}
				bw.close();
				fw.close();
				br.close();
				fr.close();
			}
		} catch (FileNotFoundException e) {//fr
			e.printStackTrace();
			return false;
		} catch (IOException e) {//fw
			e.printStackTrace();
			return false;
		}	
		logger.info("复制txt文件完成!");
		return true;
	}

	/**
	 * 判断文件是否存在
	 * @param filePathNoSeal  文件当前目录
	 * @param fileName		     文件名
	 * @return
	 */
	public boolean isExist(String filePathNoSeal,String fileName){
		File file=new File(filePathNoSeal+File.separatorChar+"HDFileInfo.txt");//固定
		FileReader fr=null;
		BufferedReader br=null;
		String str[]=null;
		String tempStr=null;
		boolean flag=false;
			try {
				fr=new FileReader(file);
				br=new BufferedReader(fr);
				while((tempStr=br.readLine())!=null){
					str=tempStr.split("\\|");
					if(str[0].equals(fileName)){//在清单中找到了目录下的文件名称
						flag=true;
						break;						
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return flag;
	}
	
	//设置中文
	private Font setChineseFont(){
		String font = DJPropertyUtil.getPropertyValue("fontspath");
		BaseFont base=null;
		Font fontChinese=null;
		try {
			base=BaseFont.createFont(font+"simfang.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
//			base=BaseFont.createFont(font+"simsun.ttc,0",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			fontChinese=new Font(base,10.5f,Font.NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fontChinese;
	}
	
	/**txt回单转pdf
	 * @param openPath 打开txt文件相对目录
	 * @param savePath 保存pdf文件相对目录
	 */
	@Override
	public boolean txtToPdf(String openPath, String savePath) {
		File file1=new File(openPath);
		File file2=new File(savePath);
		FileInputStream fis=null;
		InputStreamReader isr=null;
		BufferedReader br2=null;
		FileOutputStream fos=null;
		
		try {
			if (file1.exists()) {
				logger.info("txt回单转pdf开始...");
				String[] list = file1.list();
				for (int i = 0; i < list.length; i++) {
					if (isExist(file1.getAbsolutePath(), list[i])) {// 存在清单中的文件则转为pdf
						openPath = file1.getAbsolutePath() + File.separatorChar+ list[i];// 读取的文件
						savePath = file2.getAbsolutePath()
								+ File.separatorChar
								+ list[i]
										.substring(0, list[i].lastIndexOf("."))
								+ ".pdf";// 保存文件路径
						fis = new FileInputStream(openPath);
						isr = new InputStreamReader(fis, "gb2312");// 指定编码(最好在读时指定,读后乱码,写时指定编码也会乱码)
//						isr=new InputStreamReader(fis,"GBK");
						br2 = new BufferedReader(isr);
						fos = new FileOutputStream(savePath);
						Rectangle rect = new Rectangle(PageSize.A4);
						Document doc = new Document(rect, 50, 50, 50, 50);
						PdfWriter.getInstance(doc, fos);
						String tempStr = "";
						doc.open();// open pdf
						while ((tempStr = br2.readLine()) != null) {
							doc.add(new Paragraph(tempStr, this.setChineseFont()));
						}
						doc.close();
						fos.close();
						br2.close();
						isr.close();
						fis.close();
					}
				}
			}
		} catch (FileNotFoundException e) {// fis
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {// isr
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {// getInstance
			e.printStackTrace();
			return false;
		} catch (IOException e) {// br2.readLine
			e.printStackTrace();
			return false;
		}
		logger.info("txt回单转pdf完成!");
		return true;
	}
	
	/**
	 * 定时清理未盖章公积金回单
	 */
	@Override
	public boolean clearNoSealAccumulation() {
		int savedDays =Integer.parseInt(DJPropertyUtil.getPropertyValue("accumulationSavedDays"));
		String noSealFile = DJPropertyUtil.getPropertyValue("accumulationFilePath_NoSeal");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String fileName=null;//文件夹名称
		long time1=0;//文件夹时间
		long time2=0;//当前时间
		File file1=new File(noSealFile);
		if (file1.exists()) {
			File[] listFiles1 = file1.listFiles();
			if (listFiles1.length > 0)
				for (File file2 : listFiles1) {
					if (file2.isDirectory()) {
						fileName = file2.getName().trim();
						try {
							time1 = sdf.parse(fileName).getTime();
							time2 = System.currentTimeMillis();
							if ((time2 - time1) / (24 * 60 * 60 * 1000) >= savedDays) {
								File[] listFiles2 = file2.listFiles();
								if (listFiles2.length > 0) {// 删除文件再删除文件夹
									for (File file3 : listFiles2)
										file3.delete();
								}
								file2.delete();// 直接删除文件夹
							}
						} catch (ParseException e) {
							//e.printStackTrace();
							logger.info("日期文件夹转换失败,文件名:"+fileName);
							return false;
						}
					}
				}
		}else{
			logger.info("公积金回单未盖章保存目录不存在!");
			return false;
		}
		return true;
	}

	/**
	 * 定时清理盖章后公积金回单
	 */
	@Override
	public boolean clearSealAccumulation() {
		int savedDays =Integer.parseInt(DJPropertyUtil.getPropertyValue("accumulationSavedDays"));
		String sealFile = DJPropertyUtil.getPropertyValue("accumulationFilePath_AddSeal");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		File file1=new File(sealFile);
		String fileName=null;
		long time1=0;
		long time2=0;
		
		if (file1.exists()) {
			File[] listFiles1 = file1.listFiles();
			if (listFiles1.length > 0)
				for (File file2 : listFiles1) {
					if (file2.isDirectory()) {
						File[] listFiles2 = file2.listFiles();
						fileName = file2.getName().trim();
						try {
							time1 = sdf.parse(fileName).getTime();
							time2 = System.currentTimeMillis();
							if ((time2 - time1) / (24 * 60 * 60 * 1000) >= savedDays) {
								for (File file3 : listFiles2) {
									file3.delete();
								}
							}
						} catch (ParseException e) {
							logger.info("日期文件夹转换失败,文件名:"+fileName);
							return false;
						}
					}
					file2.delete();
				}
		}else{
			logger.info("公积金回单盖章保存目录不存在!");
			return false;
		}		
		return true;
	}
}
