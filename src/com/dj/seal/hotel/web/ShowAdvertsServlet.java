package com.dj.seal.hotel.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dj.seal.hotel.po.AdvertImagePO;
import com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl;
import com.dj.seal.util.Constants;
import com.dj.seal.util.spring.MyApplicationContextUtil;


public class ShowAdvertsServlet extends HttpServlet {
	/**
	 * Constructor of the object.
	 */
	public ShowAdvertsServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public String bpath() {
		String savePath = "";
		savePath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				savePath=Constants.getProperty("hotelAdvertSavePath");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return savePath;
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*String savePath=bpath();
		String filename=savePath+request.getParameter("filename");
		String xmlImg=getImageBinary(filename);
		xmlImg=xmlImg.replace(" ", "+");
		//logger.info("xmlImg:"+xmlImg);
		try {
			response.setContentType("image/*"); // 设置返回的文件类型
			ServletOutputStream toClient = response.getOutputStream();
			GenerateImage(xmlImg, toClient);
		} catch (Exception ex) {
			// TODO: handle exception
		}*/
		//String id=request.getParameter("id");
		String filename=request.getParameter("filename");
		HotelAdvertServiceImpl hotelAdvertImp=(HotelAdvertServiceImpl) getBean("hotelAdverService");
		ServletOutputStream toClient = response.getOutputStream();//写
		try {
			AdvertImagePO advertimagepo=hotelAdvertImp.getAdvertImage(filename);//广告图片表数据
			GenerateImage(advertimagepo.getAi_imagedata(), toClient);//图片数据写入客户端
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getImageBinary(String filename){
		File f=new File(filename);
		BufferedImage bi;
		try {
			bi=ImageIO.read(f);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes=baos.toByteArray();
			return new sun.misc.BASE64Encoder().encodeBuffer(bytes).trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static boolean GenerateImage(String imgStr, ServletOutputStream toClient) {
		if (imgStr == null) // 图像数据为空
			return false;
		try {
			// Base64解码
			byte[] b = new sun.misc.BASE64Decoder().decodeBuffer(imgStr);//解码base64图片数据
			//byte[] b =Base64.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			/*File f = new File("C:/123.jpg");
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(b);
			fout.close();*/
			toClient.write(b);//解码后写入数据
			//toClient.flush();
			toClient.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
}
