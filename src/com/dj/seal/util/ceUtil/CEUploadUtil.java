package com.dj.seal.util.ceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import com.grcb.pb.i.imageplatform.service.CESession;
import com.grcb.pb.i.imageplatform.service.Transaction;
import com.grcb.pb.i.imageplatform.service.VoucherImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CEUploadUtil {
	static Logger logger = LogManager.getLogger(CEUploadUtil.class.getName());
	//private static String  username = Constants.getProperty("username");
	//private static String password = Constants.getProperty("password");
	/**
	 * 上传文件到CE系统
	 * @param fileSavePath   pdf文件保存路径(pdf文件来源20180104)
	 * @param imageSavePath  jpg图片保存路径(jpg文件来源20180104)
	 * @param cepo			 CE对象
	 * @return
	 */
	public static String uploadToCe(String fileSavePath,String imageSavePath,TransCePo cepo){
		//1. 登录
		CESession cesession = new CESession();
		long loginstartTime = System.currentTimeMillis();
		logger.info("已进入CE上传方法uploadToCe，【"+cepo.getCaseSeqID()+"】CE账号和密码："+cepo.getTellerId()+";"+cepo.getCeUserPS());
		try {
			cesession.logon(cepo.getTellerId(), cepo.getCeUserPS());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			logger.info("【"+cepo.getCaseSeqID()+"】CE登录失败");
			return "";
		}		
		long loginendTime = System.currentTimeMillis();
		logger.info("loginTime:"+(loginendTime-loginstartTime));
		ArrayList<VoucherImage> viList = new ArrayList<VoucherImage>();
		//影像1 保存jpg
		if(imageSavePath!=null && !imageSavePath.equals("")){
			VoucherImage vi1 = new VoucherImage(cesession);
			vi1.setVoucherNO(cepo.getVoucherNo());
			vi1.setVoucherType(cepo.getVoucherType());
			vi1.setVoucherSubType("200");//未标明
			vi1.setTellerID(cepo.getTellerId());//未标明
			vi1.setCreateDateTime(new Date(System.currentTimeMillis()));
			vi1.setPositiveFlag(true);
			vi1.setVerifyStampFlag(1);
			File vi1File = new File(imageSavePath);
			String vi1FileName = vi1File.getName();
			String vi1FileExt = "jpg";
			vi1.setFileName(vi1FileName);
			vi1.setFileExt(vi1FileExt);
			FileInputStream vi1FileIS = null;
			try {
				vi1FileIS = new FileInputStream(vi1File.getAbsolutePath());
				vi1.setImageStream(vi1FileIS);
				viList.add(vi1);
				//System.out.println("zbtzbtzbt" + vi1FileIS);
			} catch (FileNotFoundException e) {
				System.out.println("Specified voucher image file is not found.");
				e.printStackTrace();
				logger.error(e.getMessage());
				//System.exit(0);
				return "";
			}
		}
		
		//影像2 保存pdf
		VoucherImage vi2 = new VoucherImage(cesession);
		vi2.setVoucherNO(cepo.getVoucherNo());
		vi2.setVoucherType(cepo.getVoucherType());
		vi2.setVoucherSubType("200");
		vi2.setTellerID(cepo.getTellerId());
		vi2.setCreateDateTime(new Date(System.currentTimeMillis()));
		vi2.setPositiveFlag(true);
		vi2.setVerifyStampFlag(1);
		File vi2File = new File(fileSavePath);
		String vi2FileName = vi2File.getName();
		String vi2FileExt = "pdf";
		vi2.setFileName(vi2FileName);
		vi2.setFileExt(vi2FileExt);
		FileInputStream vi2FileIS = null;
		try {
			vi2FileIS = new FileInputStream(vi2File.getAbsolutePath());
			vi2.setImageStream(vi2FileIS);
			//System.out.println(vi2FileIS);
		} catch (FileNotFoundException e) {
			logger.info("Specified voucher image file is not found.");
			e.printStackTrace();
			logger.error(e.getMessage());
			//System.exit(0);
			return "";
		}
		viList.add(vi2);
		
		//创建一笔交易
		Transaction tran = new Transaction(cesession);
		logger.info(cepo.getCaseSeqID()+"::"+cepo.getTranCode());
		tran.setTransq(cepo.getCaseSeqID());
		tran.setTranCode(cepo.getTranCode());
		tran.setTellerID(cepo.getTellerId());
		tran.setOrgUnit(cepo.getOrgUnit());
		tran.setCreateDateTime(new Date(System.currentTimeMillis()));
		tran.setUpdateDateTime(new Date(System.currentTimeMillis()));
		tran.setImageCount(viList.size());
		tran.setVoucherImages(viList);
		//4. 保存该交易(包括影像)，返回CEID
		tran.save();
		String tranCEID = tran.getCEID();
		System.out.println("The new transaction CEID is "  + tranCEID + ".");
		logger.info("The new transaction CEID is "  + tranCEID + "."+cepo.getCaseSeqID()+"::"+cepo.getTranCode());
		
		//5. 登出
		cesession.logoff();
		
		return tranCEID;
	}
	
	public static void main(String[] args) {
		uploadToCe("","",null);
//		//1. 登录
//		CESession cesession = new CESession();
//		long loginstartTime = System.currentTimeMillis();
//		cesession.logon(username, password);
//		long loginendTime = System.currentTimeMillis();
//		logger.info("loginTime:"+(loginendTime-loginstartTime));
//		cesession.logoff();
	}
}
