<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>

<%  request.setCharacterEncoding("gb2312");
	Logger logger = LogManager.getLogger(this.getClass().getName());
	String doc_no = request.getParameter("DOC_ID");//文档编号
	String num = request.getParameter("NUM");//请求打印份数
	String opt=request.getParameter("OPT");//操作类型	
	String key_sn=request.getParameter("KEY_SN");//证书序列号 
	String key_dn = request.getParameter("KEY_DN");//获得证书DN
	if(doc_no.equals("")||(key_sn.equals("")&&key_dn.equals(""))){
		out.print("DataBegin::1::收到信息不全::DataEnd");
	}else {
		if("get".equals(opt)){//是获取打印份数时
			String str=SealInterface.getPrintInfo(key_sn,key_dn,doc_no);
			if(str.startsWith("X-")){
				out.print("DataBegin::1::"+str+"::DataEnd");
			}else{
				out.print("DataBegin::0::"+str+"::DataEnd");
			}
		}else if("print".equals(opt)){
			SealInterface.setPrintInfo(key_sn,key_dn,doc_no,num);
			out.print("DataBegin::0::DataEnd");
		}
	}
%>