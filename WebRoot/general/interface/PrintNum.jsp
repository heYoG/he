<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>

<%  request.setCharacterEncoding("gb2312");
	Logger logger = LogManager.getLogger(this.getClass().getName());
	String doc_no = request.getParameter("DOC_ID");//�ĵ����
	String num = request.getParameter("NUM");//�����ӡ����
	String opt=request.getParameter("OPT");//��������	
	String key_sn=request.getParameter("KEY_SN");//֤�����к� 
	String key_dn = request.getParameter("KEY_DN");//���֤��DN
	if(doc_no.equals("")||(key_sn.equals("")&&key_dn.equals(""))){
		out.print("DataBegin::1::�յ���Ϣ��ȫ::DataEnd");
	}else {
		if("get".equals(opt)){//�ǻ�ȡ��ӡ����ʱ
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