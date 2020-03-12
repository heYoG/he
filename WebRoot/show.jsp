<%@page import="autoUtil.AutoClient" pageEncoding="utf-8"%><%@page import="java.util.Date"%><% 
		String xml_data = request.getParameter("hc");
		String operate=request.getParameter("operate");
		System.out.println("operate:"+operate);
		String address = request.getParameter("address").toString().trim();
		String port = request.getParameter("port").toString().trim();
		String ret = null;
		AutoClient ac = new autoUtil.AutoClient();// ws客户端封装对象
		ac.setIp(address);
		ac.setPort(port);
		xml_data=new String(xml_data.getBytes("iso8859-1"),"utf-8");
        long n1=new Date().getTime();
		try {
			if("sealpdf".equals(operate)){
				ret = ac.sealAutoPdf(xml_data);
			}
			else if("megerpdf".equals(operate)){
				ret = ac.modelAutoMerger(xml_data);
			}
			else if("varitypdf".equals(operate)){
				ret = ac.pdfVarify(xml_data);
			}else if("html5syn".equals(operate)){
				ret = ac.html5syn(xml_data);
			}else if("html5save".equals(operate)){
				ret = ac.html5save(xml_data);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			StringBuffer sb=new StringBuffer();
			sb.append("调用接口发生异常："+e.toString());
		    out.println(sb.toString());
	 }
     out.println(ret);
%>
