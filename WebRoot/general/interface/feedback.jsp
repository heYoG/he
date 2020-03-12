<%@page import="com.dj.seal.log.service.impl.FeedLogSysServiceImpl"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.dj.seal.structure.dao.po.FeedLogSys"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<jsp:useBean id="mySmartUpload" scope="page"
	class="com.jspsmart.upload.SmartUpload" />
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="xmlUtil.xml.XMLNote"%>
<%@page import="com.dj.seal.util.httpClient.IClient"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@page import="java.net.Socket"%>
<%@page import="com.dj.seal.util.socket.MySocket"%>
<%@page import="com.dj.seal.structure.dao.po.EvaluationPo"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	// ��ʼ���ϴ����
	mySmartUpload.initialize(pageContext);
	mySmartUpload.upload();
	String filename;
	com.jspsmart.upload.File myFile = null;
	String SN = mySmartUpload.getRequest().getParameter("SN");//��ˮ��
	String user = mySmartUpload.getRequest().getParameter("USER");//���۵Ĳ���Ա
	String feedback = mySmartUpload.getRequest().getParameter(
			"feedback");//���۽��
	feedback = feedback.substring(feedback.lastIndexOf(".")+1, feedback.length());
	String feedbackreason=mySmartUpload.getRequest().getParameter("reason");
	if(feedbackreason!=null){
		if(feedbackreason.equals("reason1"))
		feedbackreason="1";
		if(feedbackreason.equals("reason2"))
		feedbackreason="2";
		if(feedbackreason.equals("reason3"))
		feedbackreason="3";
		if(feedbackreason.equals("reason4"))
		feedbackreason="4";
	}else{
		feedbackreason="";
	}
	if((feedback.equals("YIBAN")||feedback.equals("BUMANYI"))&&(feedbackreason.equals(""))){
		feedbackreason="4";
	}
	logger.info("===================Ʊ��"+SN+"��̨��������======================");
	logger.info("������Ա:"+user);	
	logger.info("Ʊ��ID:"+SN);
	logger.info("���۽��:"+feedback);
	logger.info("����ԭ��"+feedbackreason);
	if(feedback.equals("FEICHANGMANYI")){
		feedback = "A";
	}else if(feedback.equals("MANYI")){
		feedback = "B";
	}else if(feedback.equals("YIBAN")){
	    feedback = "C";
	}else if(feedback.equals("BUMANYI")){
		feedback = "D";
	}
	Timestamp stimt = new Timestamp(System.currentTimeMillis());
    EvaluationPo evaPo = new EvaluationPo();
    FeedLogSys flogs = new FeedLogSys();
	evaPo.setSrctrancode(SN); 
	evaPo.setJdgrslt(feedback);
	evaPo.setReason(feedbackreason);
	String xmlData = SealInterface.getXMLString(evaPo);
	String address = Constants.getProperty("evaluation_ip").trim();//����ϵͳIP
	int port = Integer.valueOf(Constants.getProperty("evaluation_prot")
			.trim());//����ϵͳ�˿ں�
	try {
		Socket client = MySocket.getSocket(address, port);
		MySocket.seanMesage(client, xmlData);//������Ϣ
		String receiveStr = MySocket.receiveMesage(client);//������Ϣ
		logger.info("receiveStr::1"+receiveStr);
		logger.info("receiveStr::2"+new String(receiveStr.getBytes(),"gbk"));
		logger.info("receiveStr::gbk"+new String(receiveStr.getBytes("utf-8"),"gbk"));
		logger.info("receiveStr::utf-8"+new String(receiveStr.getBytes("gbk"),"utf-8"));
		logger.info("receiveStr::gb2312"+new String(receiveStr.getBytes("gb2312"),"utf-8"));
		receiveStr = receiveStr.substring(receiveStr.indexOf("<?xml"),receiveStr.length());
		XMLNote xml = null;
		xml = XMLNote.toNote(XMLNote.noHead(receiveStr));
		String rsp_no =  xml.getValue("msg.comm_head.rsp_no");//Ӧ����Ϣ
		String rsp_msg = xml.getValue("msg.comm_head.rsp_msg");//Ӧ����
		logger.info("Ӫ��ϵͳ���ؽ����Ϣ��" + receiveStr);
		logger.info("Ӧ����Ϣ:"+rsp_no);
		logger.info("Ӧ����:"+rsp_msg);
		flogs.setFeed_ret(feedback);
		flogs.setUser_id(user);
		flogs.setFeed_id(SN);
		flogs.setLog_remark(rsp_msg);
		flogs.setOpr_time(stimt);
		if(feedbackreason.equals("1")){
			flogs.setReason("����Ƿ��");
		}else if(feedbackreason.equals("2")){
			flogs.setReason("ҵ��Ч��Ƿ��");
		}else if(feedbackreason.equals("3")){
			flogs.setReason("����̬��Ƿ��");
		}else if(feedbackreason.equals("4")){
			flogs.setReason("����");
		}else{
			flogs.setReason("");
		}		
		SealInterface.addFeedSeal(flogs);
		logger.info("=================Ʊ��"+SN+"�������۽���======================");
	} catch (Exception e) {
		e.printStackTrace();
		logger.info(e.getMessage());
	}
%>
