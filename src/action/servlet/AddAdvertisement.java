package action.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.adDao.impl.AdDaoImpl;
import util.CommenClass;
import vo.adVo.AdVo;
import vo.userVo.UserVo;

/**
 * Servlet处理广告模块
 * @author Administrator
 */
/*regEx指定后缀,避免struts2拦截action*/
@WebServlet(name="addAdServlet",urlPatterns = "*.servlet")
public class AddAdvertisement extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private File myFile;// 文件绝对路径
	private String myFileFileName;// 文件名称
	AdVo adVo=new AdVo();
	AdDaoImpl adDaoImpl=new AdDaoImpl();
	UserVo userVo=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {// 系统登录过期
			userVo= (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			out.print("<script type='text/javascript'>alert('登录已失效,请重新登录!')</script>");
			out.print("<script type='text/javascript'>window.open('../../index.jsp','_parent','')</script>");
			return;//中止程序继续
		}
		String adDestPath=CommenClass.getProperty("adDestPath").trim();//保存目录
		String filePath = request.getParameter("uploadPath");//获取上传文件路径
		String saveName=adDestPath+File.separatorChar+UUID.randomUUID()+filePath.substring(filePath.lastIndexOf("."));//绝对路径
		long adSize=new File(filePath).length();//文件大小
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long millis = System.currentTimeMillis();//上传日期
		String format2 = format.format(millis);
		Timestamp valueOf = Timestamp.valueOf(format2);
		String userNo = userVo.getUserNo();//操作者
		
		adVo.setAdName(filePath);//用于获取读取路径
		adVo.setSaveName(saveName);
		adVo.setAdSize(adSize);
		adVo.setUploadtime(valueOf);
		adVo.setOperator(userNo);
		int ad = uploadAd(request, response,adVo);
		if(ad==0) {//图片上传失败
			out.print("<script type='text/javascript'>alert('上传广告图片失败!')</script>");
			out.print("<script>window.history.go(-1)</script>");
		}else {//图片上传成功
			adVo.setAdName(new File(filePath).getName());//用于保存名称
			int i = adDaoImpl.newAd(adVo);
			if(i>0)
				System.out.println("保存广告图片成功!");
			else
				System.out.println("保存广告图片失败!");
			out.print("<script type='text/javascript'>alert('上传广告图片成功!')</script>");
			out.print("<script>window.history.go(-1)</script>");//返回上一页			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}

	/*上传图片*/
	private static int uploadAd(HttpServletRequest request, HttpServletResponse response,AdVo adVo) {
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		try {
			FileInputStream fis=new FileInputStream(new File(adVo.getAdName()));//图片读取路径
			bis=new BufferedInputStream(fis);//输入流
			FileOutputStream fos=new FileOutputStream(new File(adVo.getSaveName()));//图片保存路径
			bos=new BufferedOutputStream(fos);//输出流
			int i=-1;
			while((i=bis.read())!=-1) {
				bos.write(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(bis!=null)
					bis.close();
				if(bos!=null)
					bos.close();
			} catch (Exception e2) {
				System.out.println("关闭流发生异常!");
			}			
		}
		
		return 1;
	}
	
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

}
