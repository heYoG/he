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
String srctrancode = "WZH001";//交易码
String srcsysid = "WZH";//源系统
String srctrndt = request.getParameter("SRCTRNDT");//系统交易日期
String srcesqno = request.getParameter("SRCSEQNO");//源系统流水号和交易流水号相同
String tranbrchno = request.getParameter("TRANBRCHNO");//交易机构
String trantlr = request.getParameter("TRANTLR");//交易柜员
String trantrx = request.getParameter("TRANTRX");//交易流水
String trannm = request.getParameter("TRANNM");//业务类型
String jdgdttm = request.getParameter("JDGDTTM");//评价时间
String jdgrslt = request.getParameter("JDGRSLT");//评价结果
String trancode = request.getParameter("TRANCODE");//交易代码
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
	System.out.println("接收返回结果："+receiveStr);
}catch(Exception e){
	e.printStackTrace();
}

//receiveStr = receiveStr.substring(receiveStr.indexOf("<?xml"),receiveStr.length());

//XMLNote xml = null;
//xml = XMLNote.toNote(XMLNote.noHead(receiveStr));
//String TRNSTAT =  xml.getValue("ROOT.TRNSTAT");//返回码类型 N正常，E错误
//String RETURNCODE =  xml.getValue("ROOT.RETURNCODE");//应答码 000000：表示正常返回 如果返回码类型为’E’，填错误码 
//String OSRCSEQNO =  xml.getValue("ROOT.OSRCSEQNO");//原源系统流水号
//String OSRCTRNDT =  xml.getValue("ROOT.OSRCTRNDT");//原源系统交易日期
///String RETURNMESSAGE =  xml.getValue("ROOT.RETURNMESSAGE");//应答消息
//System.out.println("交易评价是否成功：："+TRNSTAT);
%>