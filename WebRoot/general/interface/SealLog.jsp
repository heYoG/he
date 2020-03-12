<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>

<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("gb2312");
	String doc_id = request.getParameter("DOC_ID");
	String log_type = request.getParameter("LOG_TYPE");
	String seal_id = request.getParameter("SEAL_ID");
	String client_system = request.getParameter("CLIENT_SYSTEM");
	String key_sn = request.getParameter("KEY_SN");
	String key_dn = request.getParameter("KEY_DN");
	String mac_add = request.getParameter("MAC_ADD");
	String card_id = request.getParameter("CARD_ID");
	String log_value = request.getParameter("LOG_VALUE");
	String doc_title=request.getParameter("DOC_TITLE");
	String ip = request.getLocalAddr();
	//SealInterface.addSealLog( doc_id, log_type, seal_id, client_system, key_sn, key_dn, mac_add, card_id, log_value,doc_title, ip);
	out.print("DataBegin::0::DataEnd");
	//DataBegin::0::DataEnd::20110719213548
	//接服务器当前时间
%>