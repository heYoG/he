<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.hotelApi.InterfaceClass"%>
<%@page import="java.util.List"%>
<%@page import="com.dj.seal.hotel.po.HotelAdvertPO"%>
<%@page import="java.util.ArrayList"%>
<%
	Logger logger = LogManager.getLogger(this.getClass().getName());
logger.info("advert");
	String user_id = request.getParameter("UID");
	String ip = request.getRemoteAddr();
	List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
	String advertVersion = InterfaceClass.getDeptAdsVersion(user_id);
	logger.info("数据个数"+advertVersion);
	
%>
