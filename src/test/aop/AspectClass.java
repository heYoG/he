package test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect//声明为切面,必须
@Component//声明为bean组件,必须
public class AspectClass {
	
	/*构造器*/
	public AspectClass() {
		super();
		System.out.println("切面类初始化...");
	}

	/*前置通知*/
	@Before("execution(* test.aop.PointcutClass.*(..))")//前置增强
	public void before1() {
		System.out.println("前置增强...");
	}

	/*后置通知*/
	//@After("execution(* test.aop.PointcutClass.*(..)")//后置增强
	public void after1() {
		System.out.println("后置增强...");
	}
	
	/*环绕通知*/
	//@Around("execution(* test.aop.PointcutClass.*(..)")//环绕增强
	public void aroud1(ProceedingJoinPoint pro) throws Throwable {
		System.out.println("执行方法前...");
		
		pro.proceed();//执行被增强方法
		
		System.out.println("执行方法后...");
	}
}
