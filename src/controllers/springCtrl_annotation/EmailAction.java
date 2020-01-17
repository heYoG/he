package controllers.springCtrl_annotation;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.service.emailService.impl.EmailServiceImpl;
import util.CommenClass;
import util.MyApplicationContextAnnotion;
import util.PageUtil;
import util.UploadAndDownloadUtil;
import vo.emailVo.EmailVo;
import vo.userVo.UserVo;

@Controller(value="emailAction")//注解指定action值
public class EmailAction extends ActionSupport implements ModelDriven<EmailVo> {

	private static final long serialVersionUID = 1L;

	private static Logger log=LogManager.getLogger(EmailAction.class.getName());
	@Resource(name="emailVo")//按名称注入,name为EmailVo中Component注解值
	private EmailVo emailVo;//邮件实例变量
	
	@Autowired//按类型注入或@Resource(name="emailService")-name为EmailServiceImpl中的@Service注解值
	private EmailServiceImpl emailServiceImp;//服务层
	
	@Autowired
	private CommenClass cc;//通用类变量
	
	private UserVo userVo=null;//用户变量
	private File accessoryFile;//上传文件
	private String accessoryFileFileName;//上传文件名称
	
	/**
	 * 	发送邮件
	 */
	public String execute() {
		HttpServletRequest request=ServletActionContext.getRequest();//不能作全局变量,注解时启动就会扫描此文件，无请求返回空指针异常
		HttpSession session = request.getSession(false);
			if(session!=null) {
				userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
			}
			if(userVo==null) {
				request.setAttribute("user", "outtime");
				return ERROR;
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = sdf.format(System.currentTimeMillis());
			Timestamp valueOf = Timestamp.valueOf(format);
			emailVo.setSender(userVo.getUserNo());//发送者
			emailVo.setSendTime(valueOf);//发送时间
			emailVo.setStatus(1);//默认发送成功
			emailVo.setUser(userVo);//用于设置外键
			String savePath=null;
			if(accessoryFile!=null)
				savePath= UploadAndDownloadUtil.uploadFile(accessoryFile, accessoryFileFileName);//返回文件保存路径
			emailVo.setAccessory(savePath);
			emailVo.setType(1);//标识1表示发送邮件
			Serializable saveRet = emailServiceImp.newEmail(emailVo);//保存数据
			if(!saveRet.equals(0)) {
				log.info("邮件发送成功");
				request.setAttribute("saveEmailData",CommenClass.NORMAL_RETURN);				
			}else {
				log.info("邮件发送失败");
				request.setAttribute("saveEmailData",CommenClass.SAVEDATA_EXCEPTION);
			}
		return "newEmail";
	}
	
	/**
	 * 	查询收件箱、发件箱、草稿箱和已删除邮件数据
	 * @return
	 */
	public String boxManage() {
		HttpServletRequest request = ServletActionContext.getRequest();// 不能作全局变量,注解时启动就会扫描此文件，无请求返回空指针异常
		HttpSession session = request.getSession(false);
		if (session != null) {
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if (userVo == null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String type = request.getParameter("type");//确定查询类型
		long emailCount = emailServiceImp.getEmailCount(type);
		cc.setTotalCount((int) emailCount);
		cc.setType(type);
		cc = PageUtil.pageMethod(cc, request);
		List<EmailVo> listPage = new ArrayList<EmailVo>();
		listPage = emailServiceImp.emailPageList(emailVo, cc, type);
		request.setAttribute("emailList", listPage);
		request.setAttribute("pageData", cc);
		return "boxManage";//发件箱页面
	}
	
	/**
	 * 	删除发件箱数据
	 * @return
	 */
	public String deleteEmail() {
		HttpServletRequest request = ServletActionContext.getRequest();// 不能作全局变量,注解时启动就会扫描此文件，无请求返回空指针异常
		HttpSession session = request.getSession(false);
		if (session != null) {
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if (userVo == null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String deleteID = request.getParameter("id");
		String type=request.getParameter("type");
		int update = emailServiceImp.update(emailVo, Integer.parseInt(deleteID));
		if(update>0) {
			log.info("删除邮件成功!");
			request.setAttribute("deleteRet", CommenClass.NORMAL_RETURN);
		}else {
			log.info("删除邮件失败!");
			request.setAttribute("deleteRet", CommenClass.DELETEEMAIL_FAIL);
		}
		long emailCount = emailServiceImp.getEmailCount(type);// 发件箱记录数
		cc.setTotalCount((int) emailCount);
		cc = PageUtil.pageMethod(cc, request);
		List<EmailVo> listPage = new ArrayList<EmailVo>();
		listPage = emailServiceImp.emailPageList(emailVo, cc, type);
		request.setAttribute("emailList", listPage);
		request.setAttribute("pageData", cc);
		return "boxManage";//返回发件箱页面
	}
	
	/**
	 * 	彻底删除邮件
	 * @return
	 */
	public String deleteEmailComplete() {
		HttpServletRequest request = ServletActionContext.getRequest();// 不能作全局变量,注解时启动就会扫描此文件，无请求返回空指针异常
		HttpSession session = request.getSession(false);
		if (session != null) {
			userVo = (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if (userVo == null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String deleteID = request.getParameter("id");
		String type=request.getParameter("type");
		int update = emailServiceImp.deleteEmail(emailVo, Integer.parseInt(deleteID));
		if(update>0) {
			log.info("彻底删除邮件成功!");
			request.setAttribute("deleteRet", CommenClass.NORMAL_RETURN);
		}else {
			log.info("彻底删除邮件失败!");
			request.setAttribute("deleteRet", CommenClass.DELETEEMAIL_FAIL);
		}
		long emailCount = emailServiceImp.getEmailCount(type);// 发件箱记录数
		cc.setTotalCount((int) emailCount);
		cc = PageUtil.pageMethod(cc, request);
		List<EmailVo> listPage = new ArrayList<EmailVo>();
		listPage = emailServiceImp.emailPageList(emailVo, cc, type);
		request.setAttribute("emailList", listPage);
		request.setAttribute("pageData", cc);
		return "boxManage";//返回发件箱页面
	}
	
	public File getAccessoryFile() {
		return accessoryFile;
	}

	public void setAccessoryFile(File accessoryFile) {
		this.accessoryFile = accessoryFile;
	}
	
	@Override
	public EmailVo getModel() {//模型驱动获取前端值
		return emailVo;
	}
	
	public String getAccessoryFileFileName() {
		return accessoryFileFileName;
	}

	public void setAccessoryFileFileName(String accessoryFileFileName) {
		this.accessoryFileFileName = accessoryFileFileName;
	}


}
