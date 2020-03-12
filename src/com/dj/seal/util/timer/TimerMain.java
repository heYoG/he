package com.dj.seal.util.timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TimerMain implements ServletContextListener {
	Logger logger = LogManager.getLogger(TimerMain.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		TimerUtil.main(null);//同步组织机构
		
		PDFUtil.main(null);//删除签章凭证文件
		
		NoSealPDFUtil.main(null);//删除未签章凭证文件
		
		LogUtil.main(null);//删除评价日志
	
		LogFileUtil.main(null);//删除操作日志
		
		SealLogBackup.main(null);//备份盖章日志
		
		DelServerSealLog.main(null);//删除服务端盖章日志历史表数据
		
		NSHRecordBackup.main(null);//备份单据数据
	
		DelRecordOfHistory.main(null);//删除单据历史表数据
		
		DelEleInternationalAccountSealPic.main(null);//删除印章图片
		
		DelEleInternationalAccountSealPicData.main(null);//删除对账系统历史表数据
		
		AccumulationAddSeal.main(null);//公积金回单盖章
		
		ClearAccumulation.main(null);//公积金回单文件清理(含盖章和未盖章文件)
		
		BackEleAccData.main(null);//备份同时删除对账系统原始表旧数据
		
		
	}

}
