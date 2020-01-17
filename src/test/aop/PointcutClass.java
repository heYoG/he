package test.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutClass {

	public PointcutClass() {
		super();
		System.out.println("切入点类初始化...");
	}
	
	public void add() {
		System.out.println("已执行到add方法...");
	}
}
