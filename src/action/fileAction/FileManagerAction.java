package action.fileAction;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.fileDao.impl.FileManageDaoImpl;
import util.CommenClass;
import util.PageUtil;
import util.UploadAndDownloadUtil;
import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;


public class FileManagerAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletResponse response=ServletActionContext.getResponse();
	FileManageVo fm=new FileManageVo();//实例化文件属性类
	FileManageDaoImpl fdi=new FileManageDaoImpl();//实例化文件实现类
	CommenClass cc=new CommenClass();//实例化工具类
	private File myFile;//上传文件(tmp临时文件)
	private String myFileFileName;//文件名称
	UserVo uv =null;//创建空用户对象
	
	/*未删除文件信息*/
	public String execute(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);//会话存在则返回，不存在返回null，解决session过期调用session提示Session already invalidated问题
		
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String type=request.getParameter("type");//查询类型
		int totalCount=fdi.getCount(type,uv);//获取总记录
		cc.setTotalCount(totalCount);//公用方法pageMethod含泛型参数,无法获取此值
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);//重新设置cc对象
		List<FileManageVo> listFile = fdi.getFileInfo(fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("pageData", cc);
		return "fileInfo";//返回文件管理页面
	}
	
	/*上传*/
	public String uploadFile(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fm.setUser(new UserVo());//必须设置，否则在后面fm.getUser()为空
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
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
		return "uploadFileSuccess";////返回文件备份页面
	}
	
	/*下载*/
	public String downloadFile(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		FileManageVo vo = fdi.getFileInfo(id);
		UploadAndDownloadUtil.downloadFile(vo.getMyFile(), request, response);
		return null;//下载完成后留在当前页面
		
	}
	
	/*虚拟删除*/
	public String delete_update() throws IOException{//删除文件
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		fdi.deleteFile(id,1);//虚拟删除
		String type=request.getParameter("type");//查询类型
		int totalCount=fdi.getCount(type,uv);//获取总记录
		cc.setTotalCount(totalCount);//公用方法pageMethod含泛型参数,无法获取此值
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);//重新设置cc对象
		List<FileManageVo> listFile = fdi.getFileInfo(fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("pageData", cc);
		return "fileInfo";//返回文件管理页面
	}
	
	/*彻底删除*/
	public String delete_complete() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		fdi.deleteFile(id,0);//彻底删除
		String type=request.getParameter("type");//查询类型
		int totalCount=fdi.getCount(type,uv);//获取总记录
		cc.setTotalCount(totalCount);//公用方法pageMethod含泛型参数,无法获取此值
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);//重新设置cc对象
		List<FileManageVo> listFile = fdi.getFileInfo(fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("pageData", cc);
		return "recoveryFileList";//返回可恢复列表页面
	}
	
	/*查询出可恢复文件列表*/
	public String recoveryFileList() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		fm.setOperator(request.getParameter("userNo"));
		fm.setOriginalFileName(request.getParameter("fileName"));
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String date1 = request.getParameter("date");
//		Timestamp date2 = Timestamp.valueOf(date1);
//		fm.setUploadTime(date2);
		String type=request.getParameter("type");//查询类型
		int totalCount=fdi.getCount(type,uv);//获取总记录
		cc.setTotalCount(totalCount);//公用方法pageMethod含泛型参数,无法获取此值
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);//重新设置cc对象
		List<FileManageVo> listFile = fdi.getFileInfo(fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("pageData", cc);
		return "recoveryFileList";//返回可恢复列表页面
	}
	
	/*修改文件状态,恢复文件*/
	public String recoveryFile() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		UserVo uv=null;
		if(session!=null)
		 uv= (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String id = request.getParameter("id");
		fdi.updateFileStatus(Integer.parseInt(id));
		String type=request.getParameter("type");//查询类型
		int totalCount=fdi.getCount(type,uv);//获取总记录
		cc.setTotalCount(totalCount);//公用方法pageMethod含泛型参数,无法获取此值
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);//重新设置cc对象
		List<FileManageVo> listFile = fdi.getFileInfo(fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("pageData", cc);
		return "fileInfo";//返回文件管理页面
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
