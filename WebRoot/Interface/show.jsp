<%@page import="serv.AutoPdfSeal" pageEncoding="utf-8"%><%@page import="java.util.Date"%><% 

		String xml_data = request.getParameter("hc");
		System.out.println("xml:"+xml_data);
		xml_data=new String(xml_data.getBytes("iso8859-1"),"utf-8");
		System.out.println("xml:"+xml_data);
		String operate=request.getParameter("operate");
	     //xml_data=new String(xml_data.getBytes("iso8859-1"),"utf-8");
		//out.println("operate------"+operate);
		String ret = "ff";
		AutoPdfSeal ac = new AutoPdfSeal();// ws客户端封装对象
		try {
			if("gaizhang".equals(operate)){
				ret = ac.sealAutoPdf(xml_data);
				ret=new String(ret.getBytes("iso8859-1"),"utf-8");
				out.println(ret);
			}
			if("yanzheng".equals(operate)){
				ret = ac.pdfVarify(xml_data);
				ret=new String(ret.getBytes("iso8859-1"),"utf-8");
				out.println(ret);
			}
			if("androidPdf".equals(operate)){
				ret = ac.arAutoMerger(xml_data);
				//ret=new String(ret.getBytes("iso8859-1"),"utf-8");
				out.println(ret);
			}if("officeToPdf".equals(operate)){
//				ret = ac.officeToPdf(xml_data);
				ret=new String(ret.getBytes("iso8859-1"),"utf-8");
				out.println(ret);
			}
		} catch (Exception e) {
		   e.printStackTrace();
		   ret="服务器连接失败。";
		   out.println(ret);
	 }

%>
