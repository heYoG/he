package webservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebServiceInterface {
	static Logger log=LogManager.getLogger(WebServiceInterface.class.getName());
	
	public String testWebService1(String data) {
		return data+",测试1";
	}
	
	 public void testWebService2() {
		log.info("无参、无返回值方法,测试1");
	}
	
	public WebServiceInterface() {
		log.info("spring管理web service1");
	}

}
