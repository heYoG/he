package controller.notice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.noticeservice.impl.NoticeServiceImpl;
import util.CommenClass;
import util.PageUtil;
import vo.notice.NoticeVo;
import vo.userVo.UserVo;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	static Logger log=LogManager.getLogger(NoticeController.class.getName());
	@Autowired
	private NoticeServiceImpl noticeServieImpl;//公告服务层
	@Autowired
	private NoticeVo nv;//公告实例变量
	@Autowired
	private CommenClass cc;//通用类实例变量
	private UserVo userVo=null;//用户实例变量
	
	/**
	 * 发送公告
	 * @param theme		主题
	 * @param text		发送内容 
	 * @param request	服务请求对象
	 * @return
	 */
	@RequestMapping(value="/sendNotice")
	public String sendNotice(@RequestParam(value="theme",required=false) String theme,@RequestParam(value="text",required=false) String text,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(session!=null) {//获取当前登录用户信息
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {//session失效返回登录页面
			request.setAttribute("user", "outtime");
			return "forward:/jsp/errorsPage.jsp";//转发
		}
		nv.setTheme(theme);
		nv.setText(text);
		nv.setSender(userVo.getUserName());
		String format = simpleDateFormat.format(System.currentTimeMillis());
		Timestamp valueOf = Timestamp.valueOf(format);
		nv.setCreateTime(valueOf);
		nv.setSendStatus(1);//默认发送成功
		nv.setNoticeStatus(1);//默认公告正常
		nv.setUser(userVo);
		int sendNotice = noticeServieImpl.sendNotice(nv);//发送
		if(sendNotice>0) 
			request.setAttribute("saveNoticeData", "0000");//发送成功
		else 
			request.setAttribute("saveNoticeData", "0001");//发送失败
		return "notice/sendNotice";//返回到发送公告页面
	}
	
	/**
	 * 	公告分页展示
	 * @param request		服务请求对象
	 * @return
	 */
	@RequestMapping(value="/noticeList")
	public String noticeList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "forward:/jsp/errorsPage.jsp";//转发
		}
		int noticeCount = noticeServieImpl.getNoticeCount();
		cc.setTotalCount(noticeCount);//总记录数
		cc = PageUtil.pageMethod(cc, request);//获取分页数据
		/*当前页和显示数目不能写在mapper.xml中，否则无法计算导致报异常*/
		List<NoticeVo> noticePageList = noticeServieImpl.noticePageList((cc.getCurrentPage()-1)*cc.getPageSize(),cc.getPageSize());//得到公告数据
		request.setAttribute("noticeList", noticePageList);//用于展示公告信息
		request.setAttribute("pageData", cc);//用户分页处理
		return "notice/noticeList";
	}
	
	/**
	 * 	修改公告状态
	 * @param noticeID	公告id
	 * @param type		修改类型 0:注销;1:激活
	 * @param request	服务请求对象
	 * @return
	 */
	@RequestMapping("/updateNoticeStatus")
	public String updateNoticeStatus(int noticeID,int type,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "forward:/jsp/errorsPage.jsp";//转发
		}
		int i = noticeServieImpl.updateNoticeStatus(noticeID,type);
		if(type==0) {
			if(i>0)
				log.info("注销成功!");	
			else
				log.info("注销失败!");
		}else {
			if(i>0)
				log.info("激活成功!");	
			else
				log.info("激活失败!");
		}
		int noticeCount = noticeServieImpl.getNoticeCount();
		cc.setTotalCount(noticeCount);//总记录数
		cc = PageUtil.pageMethod(cc, request);//获取分页数据
		/*当前页和显示数目不能写在mapper.xml中，否则无法计算导致报异常*/
		List<NoticeVo> noticePageList = noticeServieImpl.noticePageList((cc.getCurrentPage()-1)*cc.getPageSize(),cc.getPageSize());//得到公告数据
		request.setAttribute("noticeList", noticePageList);//用于展示公告信息
		request.setAttribute("pageData", cc);//用户分页处理
		return "notice/noticeList";	
	}
	
	/**
	 * 	彻底删除公告
	 * @param request	服务请求对象
	 * @param noticeID	公告id
	 * @return
	 */
	@RequestMapping("/deleteCompletely")
	public String deleteCompletely(HttpServletRequest request,int noticeID) {
		HttpSession session = request.getSession(false);
		if(session!=null) {
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "forward:/jsp/errorsPage.jsp";//转发
		}
		int deleteNotice = noticeServieImpl.deleteNotice(noticeID);
		if(deleteNotice>0)
			log.info("删除公告成功！");
		else
			log.info("删除公告失败!");
		int noticeCount = noticeServieImpl.getNoticeCount();
		cc.setTotalCount(noticeCount);//总记录数
		cc = PageUtil.pageMethod(cc, request);//获取分页数据
		/*当前页和显示数目不能写在mapper.xml中，否则无法计算导致报异常*/
		List<NoticeVo> noticePageList = noticeServieImpl.noticePageList((cc.getCurrentPage()-1)*cc.getPageSize(),cc.getPageSize());//得到公告数据
		request.setAttribute("noticeList", noticePageList);//用于展示公告信息
		request.setAttribute("pageData", cc);//用户分页处理
		return "notice/noticeList";	
	}
}
