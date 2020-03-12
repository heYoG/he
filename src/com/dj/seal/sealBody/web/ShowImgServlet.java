package com.dj.seal.sealBody.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.sealTemplate.service.impl.SealTemplateServiceImpl;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.spring.MyApplicationContextUtil;

import sun.misc.BASE64Decoder;

public class ShowImgServlet extends HttpServlet {
	

	static Logger logger = LogManager.getLogger();


	/**
	 * Constructor of the object.
	 */
	public ShowImgServlet() {
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
		String temp_id = request.getParameter("temp_id");
		//xmlImg="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";
		//xmlImg="R0lGODlhOAA4AOcAAAAAAIAAAACAAICAAAAAgIAAgACAgMDAwMDcwKbK8P/w1P/isf/Ujv/Ga/+4SP+qJf+qANySALl6AJZiAHNKAFAyAP/j1P/Hsf+rjv+Pa/9zSP9XJf9VANxJALk9AJYxAHMlAFAZAP/U1P+xsf+Ojv9ra/9ISP8lJf4AANwAALkAAJYAAHMAAFAAAP/U4/+xx/+Oq/9rj/9Ic/8lV/8AVdwASbkAPZYAMXMAJVAAGf/U8P+x4v+O1P9rxv9IuP8lqv8AqtwAkrkAepYAYnMASlAAMv/U//+x//+O//9r//9I//8l//4A/twA3LkAuZYAlnMAc1AAUPDU/+Kx/9SO/8Zr/7hI/6ol/6oA/5IA3HoAuWIAlkoAczIAUOPU/8ex/6uO/49r/3NI/1cl/1UA/0kA3D0AuTEAliUAcxkAUNTU/7Gx/46O/2tr/0hI/yUl/wAA/gAA3AAAuQAAlgAAcwAAUNTj/7HH/46r/2uP/0hz/yVX/wBV/wBJ3AA9uQAxlgAlcwAZUNTw/7Hi/47U/2vG/0i4/yWq/wCq/wCS3AB6uQBilgBKcwAyUNT//7H//47//2v//0j//yX//wD+/gDc3AC5uQCWlgBzcwBQUNT/8LH/4o7/1Gv/xkj/uCX/qgD/qgDckgC5egCWYgBzSgBQMtT/47H/x47/q2v/j0j/cyX/VwD/VQDcSQC5PQCWMQBzJQBQGdT/1LH/sY7/jmv/a0j/SCX/JQD+AADcAAC5AACWAABzAABQAOP/1Mf/sav/jo//a3P/SFf/JVX/AEncAD25ADGWACVzABlQAPD/1OL/sdT/jsb/a7j/SKr/Jar/AJLcAHq5AGKWAEpzADJQAP//1P//sf//jv//a///SP//Jf7+ANzcALm5AJaWAHNzAFBQAPLy8ubm5tra2s7OzsLCwra2tqqqqp6enpKSkoaGhnp6em5ubmJiYlZWVkpKSj4+PjIyMiYmJhoaGg4ODv/78KCgpICAgP8AAAD/AP//AAAA//8A/wD//////yH5BAEAAP8ALAAAAAA4ADgAAAj+AP8JHEiwoMGDCBMqXMiQRL6HECM+ZEixor2ICyNeqMiR4MWJHU88HNGx4kMTBUGmzGdQZcmDJ2EqLEGiJcuXKxOSrEkwnwh7/27mxCnQZUGHMsUFRSgS50WUCoUSLGEUJk+O+UxYKIpQKcKqIoLelMow34miVQVaeFFyhMOwSymqNAGRaEqqBJtmlJqVZdqK4sbyJduy8M0SdsXKTGhWZj6vRP/GXZzYbj6SNitXJjxZc+SNPVmKkOz5IFC9A2+GrYu4NMGN+ahmGJvyn0OJHTknREzXxVKpFlg6hDoiH1CTWPORuDA4NVSBUOEiz01YKGekJknXpqybenesqV/eShyPsaR1nOTHow+P8zb5q+bZby+rXm7Q41zzP56Ilz5uji74lV9nNW31XWr1MWSgBc+dt9xvyfmlHVqXTWbdWQJJJ5dyA91WVk0VytdZbpSZB9+BrmU0X4riDcUidYW9mBt+CMpYEV1fVQRaQTtOZRKNoTWkHF+dgYYijozZR6RUVJGAWVQLYXDgROcJWBQJ2DGGImlUbgmki3uV1dOIDCGZXY5k+WQbgnUZZGZyNv4j0pcbyjhhVCial2eUdzIk0p4bAmqToBHqSah4EGF4EF1txjloels5KumkrgUEADs=";
		//logger.info("xmlImg:"+xmlImg);
		SealTemplateServiceImpl sealTemp_srv = (SealTemplateServiceImpl) getBean("ISealTempService");
		String xmlImg = null;
		try {
			xmlImg = sealTemp_srv.showTempByTemp(temp_id).getTemp_data();
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(xmlImg != null && !xmlImg.trim().equals("")){
			xmlImg=xmlImg.replace(" ", "+");
			//logger.info("xmlImg:"+xmlImg);
			try {
				response.setContentType("image/*"); // 设置返回的文件类型
				OutputStream toClient = response.getOutputStream();
				GenerateImage(xmlImg, toClient);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.toString());
			}
		}
		
	}
	// 对字节数组字符串进行Base64解码并生成图片
	public static boolean GenerateImage(String imgStr, OutputStream out) {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			//byte[] b =Base64.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			File f = new File("C:/123.jpg");
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(b);
			fout.close();
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
		//logger.info("---------------");
	}
	
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
}
