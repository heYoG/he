<%@page import="java.io.OutputStream"%><%@page import="com.dj.seal.util.spring.MyApplicationContextUtil"%><%@page import="java.io.FileOutputStream"%><%@page import="com.dj.seal.util.file.PostFIle"%><%
	try {
		System.out.println("here");
		String name=request.getParameter("name");
		System.out.println("name:"+name);
		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		//System.out.println("name1:"+name);
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ name+"\"");
	    PostFIle srv_file=(PostFIle)MyApplicationContextUtil.getContext().getBean("PostFileService");
	    byte[] b = srv_file.getSealByte(name);
		OutputStream os = response.getOutputStream();
		os.write(b, 0, b.length);
		os.close();
	
	} catch (Exception e) {
		e.printStackTrace();
		out.clear();
		out.flush();
	}
%>