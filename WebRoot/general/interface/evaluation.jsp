<%@page import="org.apache.logging.log4j.Logger"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.apache.axis2.transport.http.util.URIEncoderDecoder"%>
<%@page import="java.net.URL"%>
<%@page import="xmlUtil.xml.XMLNote"%>
<%@page import="com.dj.seal.util.httpClient.IClient"%>
<%@page import="com.dj.seal.util.Constants"%>
<%@page import="java.net.Socket"%>
<%@page import="com.dj.seal.util.socket.MySocket"%> 
<%@page import="com.dj.seal.structure.dao.po.EvaluationPo"%> 
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<% 
Logger logger = LogManager.getLogger(this.getClass().getName()); 
/**
*

	request.setCharacterEncoding("GBK");
String srctrancode = "WZH001";//������
String srcsysid = "WZH";//Դϵͳ
String srctrndt = request.getParameter("SRCTRNDT");//ϵͳ��������
String srcesqno = request.getParameter("SRCSEQNO");//Դϵͳ��ˮ�źͽ�����ˮ����ͬ
String tranbrchno = request.getParameter("TRANBRCHNO");//���׻���
String trantlr = request.getParameter("TRANTLR");//���׹�Ա
String trantrx = request.getParameter("TRANTRX");//������ˮ
String trannm = request.getParameter("TRANNM");//ҵ������
String jdgdttm = request.getParameter("JDGDTTM");//����ʱ��
String jdgrslt = request.getParameter("JDGRSLT");//���۽��
String trancode = request.getParameter("TRANCODE");//���״���
EvaluationPo evaPo = new EvaluationPo();
evaPo.setSrctrancode(srctrancode); 
evaPo.setTrancode(trancode);
evaPo.setSrcsysid(srcsysid);
evaPo.setSrctrndt(srctrndt);
evaPo.setSrcesqno(srcesqno);
evaPo.setTranbrchno(tranbrchno);
evaPo.setTrantlr(trantlr);
evaPo.setTrantrx(trantrx);
evaPo.setTrannm(trannm);
evaPo.setJdgdttm(jdgdttm);
evaPo.setJdgrslt(jdgrslt);
**/
String xmlData = SealInterface.getXMLString(null);
logger.info("data:"+xmlData.getBytes().length);
String address = Constants.getProperty("evaluation_ip").trim();
int port =Integer.valueOf(Constants.getProperty("evaluation_prot").trim());
try{
	Socket client = MySocket.getSocket(address, port);
	MySocket.seanMesage(client, xmlData);
	String receiveStr = MySocket.receiveMesage(client);
	System.out.println("���շ��ؽ����"+receiveStr);
}catch(Exception e){
	e.printStackTrace();
}

//receiveStr = receiveStr.substring(receiveStr.indexOf("<?xml"),receiveStr.length());

//XMLNote xml = null;
//xml = XMLNote.toNote(XMLNote.noHead(receiveStr));
//String TRNSTAT =  xml.getValue("ROOT.TRNSTAT");//���������� N������E����
//String RETURNCODE =  xml.getValue("ROOT.RETURNCODE");//Ӧ���� 000000����ʾ�������� �������������Ϊ��E����������� 
//String OSRCSEQNO =  xml.getValue("ROOT.OSRCSEQNO");//ԭԴϵͳ��ˮ��
//String OSRCTRNDT =  xml.getValue("ROOT.OSRCTRNDT");//ԭԴϵͳ��������
///String RETURNMESSAGE =  xml.getValue("ROOT.RETURNMESSAGE");//Ӧ����Ϣ
//System.out.println("���������Ƿ�ɹ�����"+TRNSTAT);
%>