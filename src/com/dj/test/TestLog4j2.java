package com.dj.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class TestLog4j2 {
	static Logger logger=LogManager.getLogger(TestLog4j2.class.getName());
	public static void testFun(){
		logger.trace("我是trace信息");
		logger.debug("我是debug信息");
		logger.info("我是info信息");
		logger.warn("我是warn信息");
		logger.error("我是error信息");
		logger.fatal("我是fatal信息");
	}
	public static void main(String[] args) {
//		while(true)
			testFun();
	}
}
