<%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.seal.api.SealInterface"%><%@page import="com.dj.seal.structure.dao.po.SysUser"%><%@page import="com.dj.seal.structure.dao.po.Cert"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	logger.info("getVerifyData");
	//���У��
	request.setCharacterEncoding("gb2312");
	//���֤����
	String key_sn = request.getParameter("KEY_SN");
	//���֤��DN
	String key_dn = request.getParameter("KEY_DN");
	//����û���¼��
	String user_name = request.getParameter("USER_ID");
	String user_psw = request.getParameter("PWD");
	logger.info(key_sn+";"+key_dn+";"+user_name);
	SysUser user=SealInterface.getUser(key_sn,key_dn,user_name);
	if(user==null){
		out.print("DataBegin::1::�û�������::DataEnd");
	}else{
	    if(user_psw!=null&&(key_dn==null||key_dn.equals(""))&&(key_sn==null||key_sn.equals(""))){
	      if(user.getUser_psd().equals(user_psw)){
			Cert cert=SealInterface.getCert(user.getKey_sn());
			if(user.getUseing_key().equals("2")){
				out.print("DataBegin::0::"+cert.getFile_content()+"::DataEnd-REMOTE_PFX");
			}else if(user.getUseing_key().equals("1")||user.getUseing_key().equals("3")){
				out.print("DataBegin::0::"+cert.getFile_content()+"::DataEnd");
			}else{
				out.print("DataBegin::0::::DataEnd");
			}
		  }else{
			out.print("DataBegin::1::�������::DataEnd");
		   }
	    }else{
	      Cert cert=SealInterface.getCert(user.getKey_sn());
		  if(user.getUseing_key().equals("2")){
				out.print("DataBegin::0::"+cert.getFile_content()+"::DataEnd-REMOTE_PFX");
		   }else if(user.getUseing_key().equals("1")||user.getUseing_key().equals("3")){
				out.print("DataBegin::0::"+cert.getFile_content()+"::DataEnd");
		   }else{
				out.print("DataBegin::0::::DataEnd");
		   }
	    }
	}
%>