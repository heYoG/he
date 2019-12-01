package action.fileAction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dao.fileDao.impl.FileManageDaoImpl;
import util.CommenClass;
import util.UploadAndDownloadUtil;
import vo.CommenVo;
import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;

@SuppressWarnings(value={"all"})
public class FileManagerAction extends ActionSupport{
	private HttpServletResponse response=ServletActionContext.getResponse();
	FileManageVo fm=new FileManageVo();
	FileManageDaoImpl fdi=new FileManageDaoImpl();
	CommenClass cc=new CommenClass();
	private File myFile;//上传文件(tmp临时文件)
	private String myFileFileName;//文件名称
	
	public String execute(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String type = request.getParameter("type");
		String currentPage = request.getParameter("currentPage");
		String jumpPage = request.getParameter("jumpPage");
		currentPage=currentPage==null?"1":currentPage;
		String pageSize = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(Integer.parseInt(currentPage));
		cc.setPageSize(Integer.parseInt(pageSize));
		cc.setTotalCount(totalCount);
		int []item=new int[totalCount];
		int j=1;
		for(int i=0;i<totalCount;i++) {//填充页码
			item[i]=i+j;
		}
		cc.setItem(item);
		
		List<FileManageVo> list = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc);
		request.setAttribute("fileList", list);
		return "fileInfo";
	}
	
	public String uploadFile(){//上传
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fm.setUser(new UserVo());//必须设置，否则在后面fm.getUser()为空
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		long length = myFile.length();//文件大小(bytes)
		String filePath = UploadAndDownloadUtil.uploadFile(myFile,getMyFileFileName());//文件保存路径
		String date = format.format(System.currentTimeMillis());//上传时间
		int status=1;//状态,默认正常可用
		fm.setMyFile(filePath);
		fm.setUploadTime(Timestamp.valueOf(date));
		fm.setOperator((String) uv.getUserNo());
		fm.setStatus(status);
		fm.getUser().setId(uv.getId());
		fm.setFileSize(length+"");
		fm.setOriginalFileName(getMyFileFileName());
		fdi.uploadFile(fm);
		return "uploadFileSuccess";
	}
	
	public String downloadFile(){//下载
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		FileManageVo vo = fdi.getFileInfo(id);
		UploadAndDownloadUtil.downloadFile(vo.getMyFile(), request, response);
		return null;//下载完成后留在当前页面
		
	}
	
	public String deleteFile() throws IOException{//删除文件
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		fdi.deleteFile(id);
		String type=request.getParameter("type");
		String currentPage = request.getParameter("currentPage");
		String jumpPage = request.getParameter("jumpPage");
		currentPage=currentPage==null?"1":currentPage;
		String pageSize = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(Integer.parseInt(currentPage));
		cc.setPageSize(Integer.parseInt(pageSize));
		cc.setTotalCount(totalCount);
		int []item=new int[totalCount];
		int j=1;
		for(int i=0;i<totalCount;i++) {//填充页码
			item[i]=i+j;
		}
		cc.setItem(item);
		List<FileManageVo> list = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc);
		request.setAttribute("fileList", list);
		return "fileInfo";
	}
	
	public String recoveryFileList() {//查询出可恢复文件列表
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		fm.setOperator(request.getParameter("userNo"));
		fm.setOriginalFileName(request.getParameter("fileName"));
		String type=request.getParameter("type");
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String date1 = request.getParameter("date");
//		Timestamp date2 = Timestamp.valueOf(date1);
//		fm.setUploadTime(date2);
		String currentPage = request.getParameter("currentPage");
		String jumpPage = request.getParameter("jumpPage");
		currentPage=currentPage==null?"1":currentPage;
		String pageSize = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(Integer.parseInt(currentPage));
		cc.setPageSize(Integer.parseInt(pageSize));
		cc.setTotalCount(totalCount);
		int []item=new int[totalCount];
		int j=1;
		for(int i=0;i<totalCount;i++) {//填充页码
			item[i]=i+j;
		}
		cc.setItem(item);
		List<FileManageVo> list = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc);
		request.setAttribute("fileList", list);
		
		return "recoveryFileList";
	}
	
	public String recoveryFile() {//修改文件状态,恢复文件
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String id = request.getParameter("id");
		fdi.updateFileStatus(Integer.parseInt(id));
		String type=request.getParameter("type");
		String currentPage = request.getParameter("currentPage");
		String jumpPage = request.getParameter("jumpPage");
		currentPage=currentPage==null?"1":currentPage;
		String pageSize = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(Integer.parseInt(currentPage));
		cc.setPageSize(Integer.parseInt(pageSize));
		cc.setTotalCount(totalCount);
		int []item=new int[totalCount];
		int j=1;
		for(int i=0;i<totalCount;i++) {//填充页码
			item[i]=i+j;
		}
		cc.setItem(item);
		List<FileManageVo> list = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc);
		request.setAttribute("fileList", list);
		return "fileInfo";//跳转到文件列表
	}
	
	
	public FileManageVo getFm() {
		return fm;
	}

	public void setFm(FileManageVo fm) {
		this.fm = fm;
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
