package test.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AspectClass {
	
	/*构造器*/
	public AspectClass() {
		super();
		System.out.println("切面类初始化...");
	}
	
	/*前置通知*/
	public void before1() {
		System.out.println("前置增强...");
	}

	/*后置通知*/
	public void after1() {
		System.out.println("后置增强...");
	}
	
	/*环绕通知*/
	public void aroud1(ProceedingJoinPoint pro) throws Throwable {
		System.out.println("执行方法前...");
		
		pro.proceed();//执行被增强方法
		
		System.out.println("执行方法后...");
	}
}
