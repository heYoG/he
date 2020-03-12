<%@page import="com.dj.seal.util.encrypt.Base64"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.log4j.lf5.util.DateFormatManager"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=utf-8"%>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	com.jspsmart.upload.File myFile = null;
	String message = "";
	String dept = "";
	String title = "";
	String dept_no = "";
	String name = "";
	String approve_user = "";
	String approve_name = "";
	String start_time="";
	String end_time="";
	String scorlltime="";
	request.setAttribute("start_time", "");
	request.setAttribute("end_time", "");
	request.setAttribute("dept", "");
	request.setAttribute("title", "");
	request.setAttribute("message", "");
	request.setAttribute("dept_no", "");
	request.setAttribute("approve_user", "");
	request.setAttribute("approve_name", "");
	request.setAttribute("scorlltime", "");
	String path = request.getContextPath();
	try {

		String savePath = Constants.getProperty("hotelAdvertSavePath");//文件保存路径
		//String savePath = "doc/adverts/";
		File fileAdvert = new File(savePath);
		if (!fileAdvert.exists() && !fileAdvert.isDirectory()) {
			fileAdvert.mkdirs();
		}
		
		dept = request.getParameter("dept");
		title = request.getParameter("title");
		dept_no = request.getParameter("dept_no");
		approve_user = request.getParameter("approve_user");
		approve_name = request.getParameter("approve_name");
		scorlltime=request.getParameter("scorlltime");
		
		//start_time=request.getParameter("start_time");
		//end_time=request.getParameter("end_time");

		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		//dept=new String(dept.getBytes("iso-8859-1"),"utf-8");
		// 初始化上传组件
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload();
		//title = mySmartUpload.getRequest().getParameter("ad_title");
		title=new String(title.getBytes("iso-8859-1"),"utf-8");
		start_time = mySmartUpload.getRequest().getParameter("starttime");
		end_time = mySmartUpload.getRequest().getParameter("endtime");
		//System.out.print("starttime:"+start_time+";endtime:"+end_time);
		String advertdata="";
		try {
			 for(int i=1;i<mySmartUpload.getFiles().getCount();i++){
				myFile = mySmartUpload.getFiles().getFile(i);
				if(!myFile.getFieldName().equals("")){
					String fileName = myFile.getFileName();
					System.out.println("fileName:"+fileName);
					String FileSaveName = fileName;
					//if(!myFile.isMissing()){
					//	System.out.println("savePath----" + savePath + FileSaveName);
					//	myFile.saveAs(savePath + FileSaveName,mySmartUpload.SAVE_AUTO);
					//	name=name+fileName+",";
					//	System.out.println("name:"+name);
					//}
					name=name+fileName+",";
					advertdata=advertdata+Base64.encodeToString(myFile.getBytes())+",";
			}
			} 
			advertdata=advertdata.substring(0,advertdata.length()-1);//多文件时去掉最后","
			name=name.substring(0,name.length()-1);//多文件时去掉最后","
			System.out.println("name:"+name);
			/* myFile = mySmartUpload.getFiles().getFile(1);
			String fileName = myFile.getFileName();
			System.out.println("fileName:"+fileName);
			name=fileName;
			System.out.println("name:"+name); */
		} catch (Exception e) {
			session.setAttribute("name", name);
			request.setAttribute("dept", dept);
			request.setAttribute("title", title);
			message = "<img src='" + path + "/images/error.gif'/>";
			request.setAttribute("ret", "failed");
			request.setAttribute("dept_no", dept_no);
			request.setAttribute("approve_user", approve_user);
			request.setAttribute("approve_name", approve_name);
			request.setAttribute("start_time", start_time);
			request.setAttribute("end_time", end_time);
			request.setAttribute("scorlltime", scorlltime);
			request.setAttribute("ad_advertdata", advertdata);
		}
		/* String FileSaveName = name;
		System.out.println(myFile.isMissing());
		if (!myFile.isMissing()) {
			System.out
					.println("savePath----" + savePath + FileSaveName);
			myFile.saveAs(savePath + FileSaveName,
					mySmartUpload.SAVE_AUTO);
		} */
		message = "<img src='" + path + "/images/correct.gif'/>";
		//System.out.print("path:"+path+" message:"+message);
		session.setAttribute("name", name);
		request.setAttribute("dept", dept);
		request.setAttribute("title", title);
		request.setAttribute("message", message);
		request.setAttribute("ret", "succes");
		request.setAttribute("dept_no", dept_no);
		request.setAttribute("approve_user", approve_user);
		request.setAttribute("approve_name", approve_name);
		request.setAttribute("start_time", start_time);
		request.setAttribute("end_time", end_time);
		request.setAttribute("ad_dept", dept_no);
		request.setAttribute("scorlltime", scorlltime);
		request.setAttribute("ad_advertdata", advertdata);
		request.getRequestDispatcher("/newsAdvert.do").forward(request,response);
		//request.getRequestDispatcher("new_ad.jsp").forward(request,response);

	} catch (Exception e) {
		e.printStackTrace();
		session.setAttribute("name", name);
		request.setAttribute("dept", dept);
		request.setAttribute("title", title);
		message = "<img src='" + path + "/images/error.gif'/>";
		request.setAttribute("ret", "failed");
		request.setAttribute("dept_no", dept_no);
		request.setAttribute("message", message);
		request.setAttribute("approve_user", approve_user);
		request.setAttribute("approve_name", approve_name);
		request.setAttribute("start_time", start_time);
		request.setAttribute("end_time", end_time);
		request.setAttribute("scorlltime", scorlltime);
		//request.setAttribute("ad_advertdata", advertdata);
	}
%>