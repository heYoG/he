<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("gb2312");
	String sign_mode = request.getParameter("SIGN_MODE");//ǩ��ģʽ��1 ��ǩ�� 2 attach  3 detach
	String oridata = request.getParameter("SRC_DATA");
	oridata=oridata.replaceAll(" ","+");
	oridata=oridata.replaceAll("\0","");
	String key_sn = request.getParameter("KEY_SN");
	String remote_sn = request.getParameter("REMOTE_SN");//key�ǹ���ǩ������֤��ʱʹ�ã����ݷ�����֤�����
	String s_id = request.getParameter("S_ID");
	String user_psw = request.getParameter("PWD");
	//logger.info(sign_mode+";"+oridata+";"+key_sn+";"+user_psw);
	String res="";
	if(remote_sn!=null&&!remote_sn.equals("")){
//		res=SealInterface.signDatabyServerPFX(key_sn,remote_sn,s_id,oridata,sign_mode);
	}else{
		res=SealInterface.signData(key_sn,user_psw,oridata,sign_mode);
	}
	//logger.info("res:"+res);
	if(res.startsWith("X-")){
		out.print("DataBegin::1::"+res+"::DataEnd");
	}else{
		out.print("DataBegin::0::"+res+"::DataEnd");
	}
%>