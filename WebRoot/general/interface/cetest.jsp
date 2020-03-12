<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="com.grcb.pb.i.imageplatform.service.Transaction"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.grcb.pb.i.imageplatform.service.VoucherImage"%>
<%@page import="com.grcb.pb.i.imageplatform.service.CESession"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	   Logger logger = LogManager.getLogger(this.getClass().getName());

		String username = Constants.getProperty("username");
		String password = Constants.getProperty("password");
		CESession cesession = new CESession();
		long startTime = System.currentTimeMillis();
		//1. 登录
		cesession.logon(username, password);
		long endTime = System.currentTimeMillis();
		logger.info("useTime:"+(endTime - startTime));
		//2. 创建凭证影像
		VoucherImage vi1 = new VoucherImage(cesession);
		vi1.setVoucherNO("1001");
		vi1.setVoucherType("100");
		vi1.setVoucherSubType("1001");//未标明
		vi1.setTellerID("zh2081");//未标明
		vi1.setCreateDateTime(new Date(System.currentTimeMillis()));
		vi1.setPositiveFlag(true);
		vi1.setVerifyStampFlag(1);
		File vi1File = new File("D:/jinmi-hui.jpg");
		String vi1FileName = vi1File.getName();
		String vi1FileExt = "jpg";
		vi1.setFileName(vi1FileName);
		vi1.setFileExt(vi1FileExt);
		FileInputStream vi1FileIS = null;
		try {
			vi1FileIS = new FileInputStream(vi1File.getAbsolutePath());
			vi1.setImageStream(vi1FileIS);
			//System.out.println("zbtzbtzbt" + vi1FileIS);
		} catch (FileNotFoundException e) {
			System.out.println("Specified voucher image file is not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		//影像2
		VoucherImage vi2 = new VoucherImage(cesession);
		vi2.setVoucherNO("1002");
		vi2.setVoucherType("100");
		vi1.setVoucherSubType("1001");
		vi2.setTellerID("zh2081");
		vi2.setCreateDateTime(new Date(System.currentTimeMillis()));
		vi2.setPositiveFlag(true);
		vi2.setVerifyStampFlag(1);
		File vi2File = new File("D:/icka.jpg");
		String vi2FileName = vi2File.getName();
		String vi2FileExt = "jpg";
		vi2.setFileName(vi2FileName);
		vi2.setFileExt(vi2FileExt);
		FileInputStream vi2FileIS = null;
		try {
			vi2FileIS = new FileInputStream(vi2File.getAbsolutePath());
			vi2.setImageStream(vi2FileIS);
			//System.out.println(vi2FileIS);
		} catch (FileNotFoundException e) {
			System.out.println("Specified voucher image file is not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		ArrayList<VoucherImage> viList = new ArrayList<VoucherImage>();
		viList.add(vi1);
		viList.add(vi2);
		
		//3. 创建一笔交易
		Transaction tran = new Transaction(cesession);
		tran.setTransq("10010188072");
		tran.setTranCode("1111");
		tran.setTellerID("zh2081");
		tran.setOrgUnit("zh2");
		tran.setCreateDateTime(new Date(System.currentTimeMillis()));
		tran.setUpdateDateTime(new Date(System.currentTimeMillis()));
		tran.setImageCount(viList.size());
		tran.setVoucherImages(viList);
		
		//4. 保存该交易(包括影像)，返回CEID。
		tran.save();
		String tranCEID = tran.getCEID();
		System.out.println("The new transaction CEID is "  + tranCEID + ".");
		
		//5. 登出
		cesession.logoff();

%>

