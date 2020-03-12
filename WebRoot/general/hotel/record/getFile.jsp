<%@page contentType="text/html;charset=gbk"%>
<%@page import="com.dj.seal.util.properyUtil.DJPropertyUtil"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.OutputStream"%><%@page
	import="java.io.FileOutputStream"%>
<%
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
		String name=request.getParameter("name");
		System.out.println("name1:"+name);
		name=java.net.URLEncoder.encode(name,"gbk");
		response.setContentType("application/pdf");
        response.setHeader( "Content-Disposition", "attachment;filename=" + new String( name.getBytes("gbk"), "ISO8859-1" ) );
	   String ip = DJPropertyUtil.getPropertyValue("portray_ip");
	   String port = DJPropertyUtil.getPropertyValue("portray_prot");
	   String syscode =  DJPropertyUtil.getPropertyValue("portray_syscode");
	   String mkey = DJPropertyUtil.getPropertyValue("portray_mkey");
	   String url = "http://"+ip+":"+port+"/httpfiletrans/CommonFileDownload?syscode="+syscode+"&mkey="+mkey+"&FilePath="+name;
	   URLConnection connection;
		try {
			connection = new URL(url).openConnection();
			connection.setConnectTimeout(20000);
			InputStream input = connection.getInputStream();  
			OutputStream output = response.getOutputStream();
			try {          
				byte[] buffer = new byte[1024];    
				int i = 0;               
				while ((i = input.read(buffer)) != -1){   
					output.write(buffer, 0, i);   
					}        
				} catch (Exception e) {   
					e.printStackTrace();   
					} finally {          
						output.flush();      
						output.close();       
						input.close(); 
						connection = null;
					}
	} catch (Exception e) {
		e.printStackTrace();
		//out.clear();
		//out.flush();  
		//out = pageContext.pushBody();  
	}
%>