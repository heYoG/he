<%@page import="com.dj.seal.highcharts.form.ChartServiceForm"%>
<%@page import="com.dj.seal.api.SealInterface"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
response.setHeader("Pragma","No-cache");    

response.setHeader("Cache-Control","no-cache");    

response.setDateHeader("Expires", -10);  
Logger logger = LogManager.getLogger(this.getClass().getName());
String deptNo = request.getParameter("deptNo");
String sTime = request.getParameter("startTime");
String eTime = request.getParameter("endTime");
logger.info(deptNo);
logger.info(sTime);
logger.info(sTime);
ChartServiceForm form = new ChartServiceForm();
form.setEtime(eTime);
form.setStime(sTime);
form.setCounter(deptNo);
String jsonStr = SealInterface.getReportDataByForm(form);
logger.info(jsonStr);
out.print(jsonStr);
%>