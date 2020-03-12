<%@page import="org.apache.logging.log4j.LogManager"%><%@page import="org.apache.logging.log4j.Logger"%><%@page contentType="text/html;charset=gb2312"%><%@page import="com.dj.hotelApi.InterfaceClass"%><%@page import="java.io.OutputStream"%><%@page import="com.dj.seal.util.Constants"%><%@page import="java.io.File"%><%@page import="java.io.FileInputStream"%><%@page import="java.io.ByteArrayOutputStream"%><%
Logger logger = LogManager.getLogger(this.getClass().getName());
String file_id = request.getParameter("no");
if(file_id.contains("/")){
	String[] strs=file_id.split("/");
	if(!strs[0].equals("images")){
		file_id=file_id.substring(file_id.lastIndexOf("/")+1,file_id.length());
	}else{
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
			String dir=bpath+"/images/clientImages/";
			file_id=file_id.substring(file_id.lastIndexOf("/")+1,file_id.length());
			logger.info("filePath:"+dir+file_id);
			File file=new File(dir+file_id);
			FileInputStream fin=new FileInputStream(file);
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = fin.read(buff, 0, 100)) > 0) {
					swapStream.write(buff, 0, rc);
			}
			byte[] b = swapStream.toByteArray();	
			OutputStream os = response.getOutputStream();
			os.write(b, 0, b.length);
			//os.flush();
			os.close();
			out.clear();   
			out = pageContext.pushBody();
			return ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return ;
		}
		
	}
}
byte[] bytes=InterfaceClass.downFile(file_id,"");
OutputStream os = response.getOutputStream();
os.write(bytes);
os.close();
out.clear();  
out = pageContext.pushBody(); 


%>