<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>

<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("gb2312");
	//获得证书编号
	String key_sn = request.getParameter("KEY_SN");
	//获得证书DN
	String key_dn = request.getParameter("KEY_DN");
	String domain_port = request.getParameter("DOMAIN_PORT");
	String SEAL_TYPE=request.getParameter("SEAL_TYPE");//获取关键字  //章的类型 
	String seal_str = "";
	if(key_dn!=null&&!key_dn.equals("")){
		seal_str=SealInterface.sealListbyDN(SEAL_TYPE,key_dn);
	}else if(key_sn!=null&&!key_sn.equals("")){
		seal_str=SealInterface.sealListbySN(SEAL_TYPE,key_sn);//"1\r\n测试一V*+2\r\n测试二V*+5\r\n测试五V*+";
	}else{
		out.print("DataBegin::1::未收到证书信息::DataEnd");
	}
	if(seal_str.startsWith("X-")){
		out.print("DataBegin::1::"+seal_str+"::DataEnd");
	}else{
		//seal_str="1\r\n测试一V*+2\r\n测试二V*+5\r\n测试五V*+";
		out.print("DataBegin::0::" + seal_str + "::DataEnd");
	}
%>