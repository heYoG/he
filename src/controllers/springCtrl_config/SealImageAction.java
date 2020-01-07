package controllers.springCtrl_config;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import service.sealImageService.impl.SealImageServiceImpl;
import test.spring.ApplicationContext_init;
import util.CommenClass;
import util.MyApplicationContext;
import util.PageUtil;
import util.UploadAndDownloadUtil;
import vo.sealImage.SealImageVo;
import vo.userVo.UserVo;

public class SealImageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private CommenClass cc;
	private SealImageVo siv;
	private SealImageServiceImpl ssi;
	private File sealImgFile;// struts2上传的文件
	private String sealImgFileFileName;// struts2上传的文件名称,格式固定：xxxFileName
	private HttpServletRequest request =null;
	UserVo userVo = null;// 用户对象引用

	/**
	 * 印模管理
	 */
	public String execute() {
		request= ServletActionContext.getRequest();
		cc=(CommenClass) MyApplicationContext.getContext().getBean("commenClass");
		ssi=(SealImageServiceImpl) MyApplicationContext.getContext().getBean("sealImageService");//获取bean
		HttpSession session=request.getSession(false);
		if(session!=null)
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if (userVo == null) {// 系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String status = request.getParameter("status");//
		int imageCount = ssi.getSealImageCount("0,1");//固定查询注销和正常印模
		String imgID=request.getParameter("id");
		cc.setTotalCount(imageCount);
		cc = PageUtil.pageMethod(cc, request);
		if(status!=null)
		if(status.equals("0"))//注销印模
			ssi.updateSealImage(imgID, status);
		else//激活印模
			ssi.updateSealImage(imgID, status);
		List<SealImageVo> pageList = ssi.pageSelectSealImage(cc,"0,1");//返回印模管理页面
		request.setAttribute("pageData", cc);
		request.setAttribute("sealImageList", pageList);
		return "sealImageManage";
	}
	
	/**
	 * 新增印模
	 * @return
	 */
	public String newSealImage() {
		request= ServletActionContext.getRequest();
		siv=(SealImageVo) MyApplicationContext.getContext().getBean("SIVo");//获取bean
		ssi=(SealImageServiceImpl) MyApplicationContext.getContext().getBean("sealImageService");//获取bean
		HttpSession session = request.getSession(false);// session未失效返回;否则返回null
		if (session != null)
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if (userVo == null) {// 系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String originalFileName = getSealImgFileFileName();// 原文件名称
		long millis = System.currentTimeMillis();
		String saveName = UploadAndDownloadUtil.uploadFile(getSealImgFile(), originalFileName);// 保存文件名称
		String format = dateFormat.format(millis);// 上传时间
		Timestamp valueOf = Timestamp.valueOf(format);// 转成Timestamp类型
		siv.setOriginalName(originalFileName);
		siv.setSaveName(saveName);
		siv.setImgSize(getSealImgFile().length());// 文件大小(byte)
		siv.setUploadtime(valueOf);
		int newSealImage = ssi.newSealImage(siv,userVo);
		if (newSealImage > 0)
			System.out.println("新增印模成功!");
		else
			System.out.println("新增印模失败!");
		return "addNewSealImage";
	}

	/**
	 * 	印模审批
	 * @return
	 */
	public String approveSealImage() {
		request= ServletActionContext.getRequest();
		cc=(CommenClass) MyApplicationContext.getContext().getBean("commenClass");
		ssi=(SealImageServiceImpl) MyApplicationContext.getContext().getBean("sealImageService");//获取bean
		HttpSession session=request.getSession(false);
		if(session!=null)
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if (userVo == null) {// 系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String status = request.getParameter("status");//状态,jsp直接传多个参数值(如status=0,1)
		String imgID = request.getParameter("id");
		int imageCount = ssi.getSealImageCount("2");//固定查询待审批状态的印模
		cc.setTotalCount(imageCount);
		cc = PageUtil.pageMethod(cc, request);
		if(status!=null)
		if(status.equals("1"))//印模审批通过
			ssi.updateSealImage(imgID, status);
		List<SealImageVo> pageList = ssi.pageSelectSealImage(cc,"2");//返回印模审批页面
		request.setAttribute("pageData", cc);
		request.setAttribute("sealImageList", pageList);
		return "sealImageApprove";
	}
	
	/**
	 * 删除印模
	 * @return
	 */
	public String deleteSealImage() {
		request= ServletActionContext.getRequest();
		cc=(CommenClass) MyApplicationContext.getContext().getBean("commenClass");
		ssi=(SealImageServiceImpl) MyApplicationContext.getContext().getBean("sealImageService");//获取bean
		HttpSession session=request.getSession(false);
		if(session!=null)
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if (userVo == null) {// 系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int imageCount = ssi.getSealImageCount("0,1");//固定查询注销和正常印模
		String imgID=request.getParameter("id");//要删除印模id
		cc.setTotalCount(imageCount);//总记录
		cc = PageUtil.pageMethod(cc, request);//返回分页数据
		int delRet = ssi.deleteSealImage(Integer.parseInt(imgID));
		if(delRet>0) {
			System.out.println("删除印模成功!");
			List<SealImageVo> pageList = ssi.pageSelectSealImage(cc,"0,1");//返回印模管理页面
			request.setAttribute("pageData", cc);
			request.setAttribute("sealImageList", pageList);
		}else {
			System.out.println("删除印模失败!");
			request.setAttribute("deleteError",CommenClass.DELETESEALIMAGEEXCEPTION);
		}
		return "sealImageManage";
	}
	
	public SealImageVo getSiv() {
		return siv;
	}

	public void setSiv(SealImageVo siv) {
		this.siv = siv;
	}

	public SealImageServiceImpl getSsi() {
		return ssi;
	}

	public void setSsi(SealImageServiceImpl ssi) {
		this.ssi = ssi;
	}

	public File getSealImgFile() {
		return sealImgFile;
	}

	public void setSealImgFile(File sealImgFile) {
		this.sealImgFile = sealImgFile;
	}

	public String getSealImgFileFileName() {
		return sealImgFileFileName;
	}

	public void setSealImgFileFileName(String sealImgFileFileName) {
		this.sealImgFileFileName = sealImgFileFileName;
	}

}
