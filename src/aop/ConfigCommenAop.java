package aop;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate.utils.SessionClass;
import util.MyApplicationContext;
import vo.logVo.LogVo;

/**
 * 	配置文件模式的通用aop切面
 * @author Administrator
 *
 */
public class ConfigCommenAop extends SessionClass{
	static Logger log=LogManager.getLogger(ConfigCommenAop.class.getName());
	
	/*环绕通知保存操作日志*/
	public Object writeLog(ProceedingJoinPoint pro){//方法内不能再有其它参数，ProceedingJoinPoint pro
		/*通过spring容器配置的aop,必须获取spring管理的bean去调用方法aop才有效*/
		LogVo logVo = MyApplicationContext.getContext().getBean("log",LogVo.class);
		Session session = getOpenedSession();
		log.info("开始保存操作日志...");//需要配置log4j日志才能在控制台打印出来
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long operateTime =System.currentTimeMillis();
		String format = simpleDateFormat.format(operateTime);
		Timestamp valueOf = Timestamp.valueOf(format);//操作时间
		logVo.setOperateTime(valueOf);
		Object proceed=null;
		try {
			proceed = pro.proceed();//执行被增强方法,当要取切入点返回值时必须执行且返回该值
			logVo.setStatus(1);//操作结果,无异常即成功
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String methodName = pro.getSignature().getName();//操作方法
		logVo.setTheme(methodName);
		Transaction beginTransaction = session.beginTransaction();
		Serializable save = session.save(logVo);
		beginTransaction.commit();
		session.close();
		if(!save.equals("0"))
			log.info("保存操作日志成功.");
		else
			log.info("保存操作日志失败.");
		return proceed;
	}
	
	public ConfigCommenAop () {
		super();
		log.info("切面已初始化...");
	}
	
}
