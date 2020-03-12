<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<%@page import="com.dj.seal.structure.dao.po.SysUser"%>
<%@page import="com.dj.seal.structure.dao.po.Cert"%>
<%@page import="com.dj.sign.Base64"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dj.seal.structure.dao.po.SealBody"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	//���У��
	request.setCharacterEncoding("gb2312");
	//���֤����
	String key_sn = request.getParameter("KEY_SN");
	//���֤��DN
	String key_dn = request.getParameter("KEY_DN");
	String key_name = request.getParameter("KEY_NAME");
	String KEY_ISSUER = request.getParameter("KEY_ISSUER");
	String s_id = request.getParameter("S_ID");
	String s_name = request.getParameter("S_NAME");
	String s_sha1= request.getParameter("S_SHA");
	if(s_sha1!=null){
		s_sha1=s_sha1.replaceAll(" ","+");
		s_sha1=s_sha1.replaceAll("\0","");
	}
	String mac_add = request.getParameter("MAC_ADD");
	String client_system = request.getParameter("CLIENT_SYSTEM");
	SealBody seal=SealInterface.sealBodyById(s_id);
	/*��ȡUTCʱ�俪ʼ*/ 
    final java.util.Calendar cal = java.util.Calendar.getInstance();  
    final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);   
    final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET); 
    cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
   /*��ȡUTCʱ�����*/
    
    SimpleDateFormat format=new SimpleDateFormat("yyyymmddhh24mmss");
    String strDate=format.format(cal.getTime());
	if(seal!=null){
		//byte[] seal=Base64.decode(seal_data);		
		//FileOutputStream os=new FileOutputStream("c:/1.txt");
		//os.write(seal);
		//os.close();
		//logger.info(seal.length);
		//MessageDigest digest = MessageDigest.getInstance("sha-1");
		//digest.update(seal);//��������
		//byte[] seal_sha1=digest.digest();
		//byte[] seal_sha1Ori=Base64.decode(s_sha1);
		//boolean yuliu=Arrays.equals(seal_sha1, seal_sha1Ori);
		//if(yuliu){
		//	out.print("DataBegin::0::DataEnd::"+strDate);
		//}else{
		//	out.print("DataBegin::1::ӡ�²�һ��::DataEnd");
		//}
		if((seal.getSeal_id()+"").equals(s_id)&&seal.getSeal_name().equals(s_name)){
			out.print("DataBegin::0::DataEnd::"+strDate);
		}else{
			out.print("DataBegin::1::ӡ�²�һ��::DataEnd");
		}
	}else{
		out.print("DataBegin::1::��ȡӡ�����ݳ���::DataEnd");
	}
%>