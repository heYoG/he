<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("gb2312");
	//���֤����
	String key_sn = request.getParameter("KEY_SN");
	String key_dn = request.getParameter("KEY_DN");
	String doc_id=request.getParameter("DOC_ID");//�ĵ����
	if (doc_id == null||doc_id.equals("")) {
		out.print("DataBegin::1::��Ч���ĵ����::DataEnd");
	} else {
		String doc_type = request.getParameter("DOC_TYPE");//�ĵ�����
		String doc_name = request.getParameter("DOC_NAME");//�ĵ�����
		String doc_title = request.getParameter("DOC_TITLE");//�ĵ�����
		String doc_content = request.getParameter("DOC_CONTENT");//�ĵ�����
		String doc_keys = request.getParameter("DOC_KEYS");//�ĵ��ؼ���
		String mac_add = request.getParameter("MAC_ADD");//�ĵ�λ��
		String doc_ip = request.getLocalAddr();//�ĵ�ip
		String res=SealInterface.addDoc(key_sn,key_dn,doc_id,doc_type,doc_name,doc_title,doc_content,doc_keys,mac_add,doc_ip);
		if("".equals(res)){
			out.print("DataBegin::0::DataEnd");
		}else{
			out.print("DataBegin::1::"+res+"::DataEnd");		
		}
	}
	out.print("DataBegin::0::DataEnd");
%>