package com.dj.seal.bakupFile;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class BakupFile extends HttpServlet {
	
	static Logger logger = LogManager.getLogger(BakupFile.class.getName());

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			{
//		try {
//			request.setCharacterEncoding("utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		response.setCharacterEncoding("utf-8");
//		String  ftpNums = request.getParameter("ftpNum");
//		try {
//			StringBuffer sb = new StringBuffer();
//			if(!"".equals(ftpNums)){
//				int ftpNum = Integer.parseInt(ftpNums);
//				sb.append("ftpNum=").append(ftpNum).append("\n");
//				StringBuffer sb1 = new StringBuffer();
//				for (int i = 1; i < ftpNum + 1; i++) {
//					String ftpIp = request.getParameter("ftpIp" + i);
//					sb.append("ftp" + i + "ip=").append(ftpIp).append("\n");
//					String ftpPort = request.getParameter("ftpPort" + i);
//					sb.append("ftp" + i + "port=").append(ftpPort).append("\n");
//					String ftpUserName = request.getParameter("ftpUserName" + i);
//					sb.append("ftp" + i + "username=").append(ftpUserName).append("\n");
//					String ftpUserPwd = request.getParameter("ftpUserPwd" + i);
//					sb.append("ftp" + i + "password=").append(ftpUserPwd).append("\n");
//					String ftpPath = request.getParameter("ftpPath" + i);
//					String[] ftpPaths = ftpPath.split(";");
//					for (int j = 0; j < ftpPaths.length; j++) {
//						String[] path = ftpPaths[j].split(",");
//						if(path.length>1){
//							sb.append("source" + i + "_" + (j + 1) + "path=").append(
//									path[0].replace("\n", "")).append("\n");
//							sb.append("ftpbakup" + i + "_" + (j + 1) + "path=").append(
//									path[1].replace("\n", "")).append("\n");
//						}
//					}
//					sb1.append(i + "_" + (ftpPaths.length) + ",").append("\n");
//				}
//				String bakupNumStr = sb1.substring(0, sb1.length() - 1);
//				sb.append("bakupNum=").append(bakupNumStr).append("\n");
//				
//			}
//			
//			StringBuffer sb2 = new StringBuffer();
//			String localNumStr = request.getParameter("localNum");
//			if(!"".equals(localNumStr.trim())){
//				int localNum = Integer.parseInt(localNumStr);
//				int localBakupNum=0;
//				for(int k=1;k<localNum+1;k++){
//					String localSourcePath = request.getParameter("sourcePath"+k);
//					sb2.append("local"+k+"path=").append(localSourcePath).append("\n");
//					String localbakupPath = request.getParameter("bakupPath"+k);
//					sb2.append("bakup"+k+"path=").append(localbakupPath).append("\n");
//					localBakupNum = k;
//				}
//				sb.append("localBakupNum=").append(localBakupNum).append("\n");
//				sb.append(sb2);
//			}
//			
//			File file = new File("c://ftpconfig.properties");
//			FileOutputStream fos = null;
//			file.createNewFile();
//			fos = new FileOutputStream(file);
//			fos.write(sb.toString().getBytes());
//			fos.flush();
//			fos.close();
//			FtpUtil.readConfigFileUpload("C://ftpconfig.properties");
//			PrintWriter out = response.getWriter();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("result", "备份设置失败");
//			try {
//				response.sendRedirect("general/bakupFile/result.jsp");
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		request.setAttribute("result", "备份设置成功");
//		try {
//			response.sendRedirect("general/bakupFile/result.jsp");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String cstatussel = request.getParameter("cstatussel");//状态 ，0启用，1禁用
		String cfrequencysel = request.getParameter("cfrequencysel");// 0 每天，1每周，2每月
		String cdatesel = request.getParameter("cdatesel");//日期
		String ctimesel = request.getParameter("ctimesel");//时间
		if("0".equals(cstatussel)){//启用
			DJPropertyUtil.setPropery("fileBakupStatus", cstatussel);
			DJPropertyUtil.setPropery("rate", cfrequencysel);
			DJPropertyUtil.setPropery("datesel", cdatesel);
			DJPropertyUtil.setPropery("timesel", ctimesel);
			try {
				DJPropertyUtil.saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
	}
}
