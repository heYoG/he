package aop;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hibernate.utils.SessionClass;
import util.MyApplicationContext;
import vo.logVo.LogVo;

/**
 * 	无接口aop
 * @author Administrator
 *
 */
public class CommenAop extends SessionClass{
	static Logger log=LoggerFactory.getLogger(CommenAop.class);
	
	/*环绕通知保存操作日志*/
	public Object writeLog(ProceedingJoinPoint pro){//方法内不能再有其它参数，ProceedingJoinPoint pro
		LogVo logVo = MyApplicationContext.getContext().getBean("log",LogVo.class);
		Session session = getOpenedSession();
//		log.info("开始保存操作日志...");//需要配置log4j日志才能在控制台打印出来
		System.out.println("开始保存操作日志...");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String methodName = pro.getSignature().getName();//操作方法
		logVo.setTheme(methodName);
		Transaction beginTransaction = session.beginTransaction();
		Serializable save = session.save(logVo);
		beginTransaction.commit();
		session.close();
		if(!save.equals("0"))
//			log.info("保存操作日志成功.");
			System.out.println("保存操作日志成功.");
		else
			System.out.println("保存操作日志失败.");
		return proceed;
	}
	
	public CommenAop () {
		super();
//		log.info("切面已初始化...");
		System.out.println("切面已初始化...");
	}
	
}
