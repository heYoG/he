<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>

<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("gb2312");
	//»ñµÃÓ¡ÕÂID
	String s_id = request.getParameter("S_ID");
	//System.out.println(s_id);
	String seal_data="";
	seal_data=SealInterface.sealDataById(s_id);
	if(seal_data!=null&&!seal_data.startsWith("X-")){
		out.print(seal_data);
		//out.print("DataBegin::0::"+seal_data+"::DataEnd");
	}else{
		out.print("DataBegin::1::"+seal_data+"::DataEnd");
	}
%>