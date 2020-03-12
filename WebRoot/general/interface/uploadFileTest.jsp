<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@ page contentType="text/html;charset=GB2312" %><jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	try {
		logger.info("uploadFileTest.jsp");
		// 初始化上传组件
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		com.jspsmart.upload.File myFile = null;
		String filename = mySmartUpload.getRequest().getParameter("filename");
		logger.info("filename:"+filename);
		for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
			myFile = mySmartUpload.getFiles().getFile(i);//得到文件
			logger.info("myFile.getBytes().length:"+myFile.getBytes().length);
//			filename = myFile.getFileName();
			if (!myFile.isMissing()) {
				myFile.saveAs("C:/"+"111111122222222222333333333333"+".pdf",mySmartUpload.SAVE_PHYSICAL);
				logger.info("保存文件完成");
			}
		}
		out.clear();
		out.print("upload ok");
		out.flush();
	} catch (Exception e) {
		logger.error(e.getMessage());
		out.clear();
		out.print("FAILED");//返回控件HttpPost()方法值。
		out.flush();
	}
%>