<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page language="java" import="java.util.*" pageEncoding="gb2312"%><%@page import="com.dj.hotelApi.LicenseDJ;"%>
<%
Logger logger = LogManager.getLogger(this.getClass().getName());
String licNo=request.getParameter("LICCODE");
String ip=request.getRemoteAddr();
String user=request.getParameter("LUSER");
String mac=request.getParameter("MAC");
String flag=request.getParameter("FLAG");
String lic=request.getParameter("LICKEY");
String newCount=request.getParameter("LICCNT");
String token=request.getParameter("LASTINFO");
logger.info(licNo+"  "+newCount+"  "+ip+"  "+user+"  "+mac+"  "+flag+"  "+lic+"  "+token);
String licText="iSluSWsHfckz5fcJ8v4xnn4wPlIHHvhuroayANAFkTlatP+excnPOMUU5yr6F+yXTkXl5mqaRz++IP5repV0kCtDJRfPwnDkVHAP/zJBGjy8Qk6HmlTLA7dB4uzlkCyRRHEovFmKYuSmrzNM/N8r6+kWHH8a6T1HJdH7pwkqmAwDZNn/uARYcqRPXDLsLCOrrrQQqvNAua2pjJHVG2ur8G09umJCH5EZ7/okDlNIIsSWzufd6Q+/PX9okY/+H9HR3n8yi9wA3zjv0CBOrfhPe2cY/uQMjxLMVpIY0E9S9F9ofYViCwBE52MA3zDkZi9QJW5Ba5ySyjnxwlRIWEOn+2dhRU3r/Ed0Ap2DwuSbyU8=";
String res=LicenseDJ.licOper(licNo,newCount,ip,user,mac,flag,lic,token,licText);
logger.info("res:"+res);
out.print(res);
%>