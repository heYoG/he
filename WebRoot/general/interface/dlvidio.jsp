<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger,java.util.Date"%>
<%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.hotelApi.InterfaceClass"%>
<%
	long startTime = new Date().getTime();
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("gbk");
	String deptid = request.getParameter("UID");
	String ip = request.getRemoteAddr();
	String mac = request.getParameter("MAC_ADD");
	String result = InterfaceClass.adLoginVerfy(deptid,ip,mac);//��¼������֤�û�:���ɰ汾�ļ�
	//logger.info(result);
	if(result==null||result.equals("")||result.startsWith("error:")){
		out.print("DataBegin::1::" + result + "::DataEnd");
	}else{
		out.print("DataBegin::0::" + result + "::DataEnd");
	}
	long endTime = new Date().getTime();
	logger.info("�û���֤����,��ʱ��" +(endTime - startTime));
%>