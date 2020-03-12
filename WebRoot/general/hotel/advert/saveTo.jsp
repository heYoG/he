<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.dj.seal.util.encrypt.Base64"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@page import="java.io.File"%><jsp:useBean id="mySmartUpload"
	scope="page" class="com.jspsmart.upload.SmartUpload" />
<%
	com.jspsmart.upload.File myFile = null;
	String dept = "";
	String title = "";
	String name = "";
	String approve_user = "";
	String start_time="";
	String end_time="";
	String scorlltime="";
	String id="";
	request.setAttribute("start_time", "");
	request.setAttribute("end_time", "");
	request.setAttribute("dept", "");
	request.setAttribute("title", "");
	request.setAttribute("approve_user", "");
	request.setAttribute("scorlltime", "");
	request.setAttribute("id", "");
	String basePath = System.getProperty("user.dir").replaceAll("bin",
			"webapps")
			+ "\\Seal\\doc\\adverts\\";//tomcat
	//		String basePath = System.getProperty("user.dir")+ "\\autodeploy\\Seal\\doc\\adverts\\";// weblogic
	try {
		//String savePath = "doc/adverts/";//��欢淇��璺��
		String savePath = Constants.getProperty("hotelAdvertSavePath");
		
		
		title = request.getParameter("title");
		title=new String(title.getBytes("iso-8859-1"),"utf-8");
		approve_user = request.getParameter("approve_user");
		scorlltime=request.getParameter("scorlltime");
		id=request.getParameter("id");
		dept=request.getParameter("dept");
		
		mySmartUpload.initialize(pageContext);
		mySmartUpload.upload(); 
		
		start_time = mySmartUpload.getRequest().getParameter("starttime");
		end_time = mySmartUpload.getRequest().getParameter("endtime");
		
		//String name = request.getParameter("ad_name");
		//String FileSaveName=name.substring(name.lastIndexOf("\\")+1);
		String oldName = request.getParameter("old_file");
		String advertdata="";
		String imagename="";
		String fileName="";
		try {
			if(mySmartUpload.getFiles().getCount()>1){
			System.out.println(mySmartUpload.getFiles().getCount());
				for(int i=1;i<mySmartUpload.getFiles().getCount();i++){
					myFile = mySmartUpload.getFiles().getFile(i);
					if(!myFile.getFieldName().equals("")){
						fileName = myFile.getFileName();
						name=name+fileName+",";					
						imagename=imagename+fileName+",";
						advertdata=advertdata+Base64.encodeToString(myFile.getBytes())+",";
					}
				} 
				if(advertdata != null && !advertdata.equals("")){
					advertdata=advertdata.substring(0,advertdata.length()-1);
					imagename=imagename.substring(0,imagename.length()-1);
					name=name.substring(0,name.length()-1);
				}
			}else{
				name=oldName;
				imagename=null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("name", name);
		request.setAttribute("dept", dept);
		request.setAttribute("title", title);
		request.setAttribute("ret", "succes");
		request.setAttribute("approve_user", approve_user);
		request.setAttribute("start_time", start_time);
		request.setAttribute("end_time", end_time);
		request.setAttribute("scorlltime", scorlltime);
		request.setAttribute("id", id);
		if(imagename!=null&&!"".equals(imagename)){
			request.setAttribute("imagename", imagename);
			request.setAttribute("ad_advertdata", advertdata);
		}else{
			request.setAttribute("imagename", "1");
		}
		request.getRequestDispatcher("/updAdvert.do").forward(request,
				response);
	} catch (Exception e) {
		e.printStackTrace();
		out.clear();
	}
%>