<%@page import="com.dj.seal.util.encrypt.Base64"%><%@page import="com.dj.seal.hotel.po.AdvertImagePO"%><%@page import="com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl"%><%@page import="com.dj.hotelApi.InterfaceClass"%><%@page import="org.apache.logging.log4j.LogManager"%><%@page import="org.apache.logging.log4j.Logger"%><%@page contentType="text/html;charset=utf-8"%><%@page import="com.dj.seal.util.Constants"%><%@page import="java.io.ByteArrayOutputStream"%><%@page import="java.io.OutputStream"%><%@page import="java.io.FileInputStream"%><%@page import="java.io.File"%><%
	Logger logger = LogManager.getLogger(this.getClass().getName());
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("gbk");
	logger.info(request.getCharacterEncoding());
	try {
		String basePath = System.getProperty("user.dir").replaceAll(
				"bin", "webapps")
				+ "\\gongwen\\docs\\";//tomcat
		//		String basePath = System.getProperty("user.dir")+ "\\autodeploy\\WebOfficeDemo\\doc\\";// weblogic
		//String basePath = "c:/";
		String basePath1 = Constants.getProperty("hotelAdvertSavePath");
		//request.setCharacterEncoding("GBK");
		String name = request.getParameter("fileName");
		String user_id = request.getParameter("UID");//所属部门核心编号
		logger.info("user_id:"+user_id);
		logger.info("file:" + name);
		//String filePath="D:/seal4java_stand/app/gongwen/docs/"+name+".doc";
		if (name.indexOf(".aip") != -1) {
			String file_id = name.substring(0, name.lastIndexOf("."));//模板名称
			logger.info(file_id);
			byte[] bytes = InterfaceClass.downFile(user_id,file_id);
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.close();
			//out.clear(); 
		} else {//版本文件判断不是aip模板则下载广告
			AdvertImagePO advertImage =  InterfaceClass.getAdvertImage(name);
				if (advertImage != null) {
					response.setContentType("application/pdf");//设置为下载 application/pdf
					response.setHeader("Content-Disposition",
							"attachment;fileName=\"" + name);//客户端得到的文件名 
					byte[] b = Base64.decode(advertImage.getAi_imagedata().getBytes());
					OutputStream os = response.getOutputStream();
					os.write(b);
					//os.flush();
					os.close();
				} else {
					OutputStream os = response.getOutputStream();
					os.close();
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage());
		out.clear();
		out.print("error");
		out.flush();
	} finally {
	}
%>