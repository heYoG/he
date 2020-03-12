<%@ page contentType="text/html;charset=GB2312"%><jsp:useBean
	id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%@page import="java.io.OutputStream"%><%@page import="com.dj.seal.util.spring.MyApplicationContextUtil"%><%@page import="java.io.FileOutputStream"%>
<%
	try {
		System.out.println("here");
		// 初始化上传组件
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		String path=mySmartUpload.getRequest().getParameter("path");
		String name=mySmartUpload.getRequest().getParameter("name")+".aip";
        System.out.println(name);
		com.jspsmart.upload.File myFile = null;
		myFile = mySmartUpload.getFiles().getFile(0);
		if (!myFile.isMissing()) {
			myFile.saveAs("docs/"+path+"/" + name,
					mySmartUpload.SAVE_VIRTUAL);
		}
		out.print("kkkkk");
	} catch (Exception e) {
		out.clear();
		out.print("failed");//返回控件HttpPost()方法值。
		out.flush();
	}
%>