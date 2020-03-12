package aop;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import service.logservice.impl.LogServiceImpl;
import util.CommenClass;
import vo.logVo.LogVo;
import vo.userVo.UserVo;

/**
 * 	注解模式的通用AOP切面
 * @author Administrator
 *
 */
@Aspect//声明为切面
@Component//声明为bean组件，必须
@Transactional
public class AnnotationCommenAop {
	
	static Logger log=LogManager.getLogger(AnnotationCommenAop.class.getName());
	@Autowired
	private LogServiceImpl logService;
	private UserVo userVo=null;
	@Autowired
	LogVo logVo;
	
	/*注解式环绕通知保存操作日志*/
	@Around("execution(* controller..*.*(..))")//切面表达式声明切入点，包及其子包下的所有接口、方法
	public Object writeLog(ProceedingJoinPoint pro){//切面方法(增强/通知),方法内不能再有其它参数
		log.info("开始保存操作日志...");//需要配置log4j日志才能在控制台打印出来
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long operateTime =System.currentTimeMillis();
		String format = simpleDateFormat.format(operateTime);
		Timestamp valueOf = Timestamp.valueOf(format);//操作时间
		logVo.setOperateTime(valueOf);
		Object proceed=null;
		int saveLog=0;
		try {
			proceed = pro.proceed();//执行被增强方法,当要取切入点返回值时必须执行且返回该值
			logVo.setStatus(1);//操作结果,无异常即成功
			ServletRequestAttributes requestAttr=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttr.getRequest();//获取request对象
			HttpSession session = request.getSession(false);
			String methodName = pro.getSignature().getName();//操作方法
			if(session!=null) {
				userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);				
			}
			if(userVo==null) {//登录信息由index.jsp处理
				if(methodName.contains("login")) {//登录失败
					request.setAttribute("login", "logFailed");
					logVo.setComment("登录失败,用户名或密码错误");
					saveLog = logService.saveLog(logVo);
					return "forward:/jsp/errorsPage.jsp";
				}
				/*session失效*/
				request.setAttribute("user", "outtime");
//				return "redirect:/jsp/errorsPage.jsp";//重定向
				logVo.setComment("登录已失效");
				saveLog = logService.saveLog(logVo);
				return "forward:/jsp/errorsPage.jsp";//转发
			}
			logVo.setUser(userVo);//获取当前用户唯一标识(id)
			logVo.setTheme(methodName);
			saveLog = logService.saveLog(logVo);
		} catch (Throwable e) {
			e.printStackTrace();
			logVo.setStatus(0);//操作结果，有异常
			saveLog = logService.saveLog(logVo);
		}
		if(saveLog>0)
			log.info("保存操作日志成功.");
		else
			log.info("保存日志失败.");
		return proceed;
	}
	
	public AnnotationCommenAop () {
		super();
		log.info("注解式切面已初始化...");
	}
}
