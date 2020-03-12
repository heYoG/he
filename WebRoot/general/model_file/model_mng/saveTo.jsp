<%@ page contentType="text/html;charset=GB2312"%>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	try {
		System.out.println("here");
		// 初始化上传组件
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		String FilePath=mySmartUpload.getRequest().getParameter("name")+".aip";
		System.out.println(FilePath);
		com.jspsmart.upload.File myFile = null;
		myFile = mySmartUpload.getFiles().getFile(0);
		if (!myFile.isMissing()) {
			myFile.saveAs("upload/" + FilePath,
					mySmartUpload.SAVE_VIRTUAL);
		}
		out.clear();
		out.print("kkkkk");
		out.flush();
	} catch (Exception e) {
		out.clear();
		out.print("failed");//返回控件HttpPost()方法值。
		out.flush();

	}
%>