package webservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebServiceInterface2 {
	static Logger log=LogManager.getLogger(WebServiceInterface.class.getName());
	
	public String testWebService3(String data) {
		return data+",测试2";
	}
	
	 public void testWebService4() {
		log.info("无参、无返回值方法,测试2");
	}
	
	public WebServiceInterface2() {
		log.info("spring管理web service2");
	}
}
