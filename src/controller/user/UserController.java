package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.userservice.impl.UserServiceImpl;
import util.CommenClass;
import vo.userVo.UserVo;

@Controller
@RequestMapping("user") // 请求父路径
public class UserController {
	static Logger log = LogManager.getLogger(UserController.class.getName());
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private UserVo userVo;

	/*用户登录*/
	@RequestMapping("/login")//子路径
	public String login(@RequestParam("userNo") String userName, @RequestParam("pwd") String pwd,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		userVo = userService.login(userName, pwd);
		if (userVo != null) {
			if (session.getAttribute(CommenClass.CURRENTUSERSESSION) != null) {
				session.removeAttribute(CommenClass.CURRENTUSERSESSION);
				session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
			} else {
				session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
			}
//			session.setMaxInactiveInterval(30);//session有效时长
			log.info("登录成功!");
			return "mainPage";
		} else {// 返回登录界面
			log.info("登录失败,用户名或密码错误!");
			/*必须移除，否则登录成功后，再次登录失败时AOP中session仍会有用户数据,导致无法跳转到错误页面*/
			session.removeAttribute(CommenClass.CURRENTUSERSESSION);
			return null;//登录失败由AOP决定返回页面
		}
	}
}
