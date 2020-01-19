package test.log;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog4j {
//	static Logger log=LoggerFactory.getLogger(TestLog4j.class);//缺少jar
	static Logger log=LogManager.getLogger(TestLog4j.class.getName());
	public static void main(String[] args) {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");
	}
}
