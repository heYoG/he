<%@page import="java.io.OutputStream"%><%@page import="com.dj.seal.util.spring.MyApplicationContextUtil,java.net.URLEncoder"%><%@page import="java.io.FileOutputStream"%><%@page import="com.dj.seal.util.file.PostFIle"%><%
	try {
	    String url = request.getQueryString();
		String name=new String(url.split("=")[1].getBytes("ISO8859-1"),"gb2312");
		System.out.println("name:"+name);
		response.setContentType("application/pdf");
		//.setHeader("Content-Disposition", "attachment;filename=\""
		//		+ name+"\"");
	//	response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(name, "UTF-8"));
        response.setHeader( "Content-Disposition", "attachment;filename=" + new String( name.getBytes("gb2312"), "ISO8859-1" ) );
	    PostFIle srv_file=(PostFIle)MyApplicationContextUtil.getContext().getBean("PostFileService");
	    byte[] b = srv_file.getByte(name);
		OutputStream os = response.getOutputStream();
		os.write(b, 0, b.length);
		os.close();
	
	} catch (Exception e) {
		e.printStackTrace();
		out.clear();
		out.flush();
	}
%>