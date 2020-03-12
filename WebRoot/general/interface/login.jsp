<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.hotelApi.InterfaceClass"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
logger.info("login");
	String user_id = request.getParameter("UID");
	String pwd = request.getParameter("PWD");
	String ip = request.getRemoteAddr();
	String mac = request.getParameter("MAC_ADD");
	String version = request.getParameter("LVers");
	if(version!=null&&!version.equals("")){
		version=version.replaceAll(" ","+");
	}else{
		version="";
	}
	String result = InterfaceClass.loginVerify(user_id,pwd,ip,mac,version);
	if(result==null||result.equals("")||result.startsWith("error:")){
		out.print("DataBegin::1::" + result + "::DataEnd");
		logger.info("用户验证结束1:"+result);
	}else{
		out.print("DataBegin::0::" + result + "::DataEnd");
		logger.info("用户验证结束0");
	}
%>
