package com.dj.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.SysDepartmentUtil;


public class Test extends BaseDAOJDBC{
	
	public void getDN(String orgunit) {
		String sql = "";
		sql = "select*from " + SysDepartmentUtil.TABLE_NAME
				+ " where C_HXDEPTID='" + orgunit + "'";
		List queryForList = getJdbcTemplate().queryForList(sql);
		System.err.println(queryForList.size());
	}

	/**
	 * @param args
	 * @throws GeneralException
	 * @throws GeneralException
	 */
	public static void main(String[] args)  throws Exception {
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		long nowTime=System.currentTimeMillis();
		Timestamp valueOf = Timestamp.valueOf("2017-02-10 00:00:00");
		  String format = df.format(valueOf);
		System.out.println(format);
		
//		AppSystemForm app = new AppSystemForm();
//		app.setApp_name("fff");
//		app.setApp_no("fff");
//		app.setApp_ip("aaasdf");
//		app.setApp_pwd("1234");
//		app.setCreate_date(new Timestamp(new java.util.Date().getTime()));
//		app.setCreate_username("admin");
//		ApplicationContext	ac = new ClassPathXmlApplicationContext(new String[]{"ApplicationContext-config.xml","ApplicationContext-dao.xml","ApplicationContext-service.xml"});
//		IAppSysService appa = (IAppSysService)ac.getBean("appSysService");
//		System.out.println(appa.isExistServer("1111"));
//		FtpUtil.readConfigFileUpload("C://ftpconfig.properties");
//		DateFormat df = new SimpleDateFormat("yyy-MM-dd");                 
//		java.util.Date d1 = df.parse("2013-08-01");    
//		Date date2=new Date();
		//String d2=df.format(date2);
//		System.out.println("d1:"+d1);
//		System.out.println("d2:"+date2);
		//java.util.Date d2 = df.parse(date2);    
//		if (d1.getTime() > date2.getTime()){
//			
		
//		String date="2013-06-27 00:00:00";
//		date=date.replace("-", "");
//		System.out.println(date);
//		date=date.replace(" ", "");
//		System.out.println(date);
//		date=date.replace(":", "");
//		System.out.println(date);
		
//		int pageIndex = 0;
//		int pageSize = 10;
//		String sql = null;
//		RecordDaoImpl r = new RecordDaoImpl();
		
		/*RecordServiceImpl r = new RecordServiceImpl();
		RecordPO po = new RecordPO();
		po.setCid("1");
		po.setCstatus("1");
		r.poToVo(po);*/
		
//		Timestamp localtime = new Timestamp(new java.util.Date().getTime());
//		System.out.println("localtime"+localtime);
//		String date=localtime.toString();
//		date=date.split("\\.")[0];
//		date=date.replace("-", "");
//		System.out.println(date);
//		date=date.replace(" ", "");
//		System.out.println(date);
//		date=date.replace(":", "");
//		//System.out.println(date);
//		Date date1=new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		
//		System.out.println(formatter.format(date1));
		}
	public void run() throws SchedulerException{
		Logger log = LoggerFactory.getLogger(Test.class);
		
		log.info("初始化开始");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		log.info("初始化完成");
	}
	private Object newJob(Class<HelloJob> class1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
