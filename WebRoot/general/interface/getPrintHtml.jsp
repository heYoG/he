<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.hotelApi.InterfaceClass"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	String htmlCode = InterfaceClass.getPrintHtmlCode(request.getParameter("UID"));
	if(htmlCode==null||htmlCode.equals("")||htmlCode.startsWith("error:")){
		out.print("DataBegin::1::" + htmlCode + "::DataEnd");
		logger.info("获取打印页面结束1");
	}else{
		out.print("DataBegin::0::" + htmlCode + "::DataEnd");
		logger.info("获取打印页面结束0");
	}
%>