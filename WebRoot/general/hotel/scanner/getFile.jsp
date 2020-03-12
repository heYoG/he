<%@page import="java.io.OutputStream"%><%@page import="java.io.FileOutputStream"%><%@page import="com.dj.hotelApi.util.HotelFileUtil"%><%
	try {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String name=request.getParameter("name");
		System.out.println("name:"+name);
		response.setContentType("application/pdf");
        response.setHeader( "Content-Disposition", "attachment;filename=" + new String( name.getBytes("gb2312"), "ISO8859-1" ) );
	    HotelFileUtil hfutil = new HotelFileUtil();
	    byte[] b = hfutil.getHotelFileByte(name);
		OutputStream os = response.getOutputStream();
		os.write(b, 0, b.length);
		os.close();
	} catch (Exception e) {
		e.printStackTrace();
		out.clear();
		out.flush();
	}
%>