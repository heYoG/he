package com.dj.seal.origunJob.job;


import java.io.File;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.accountCheckSys.dao.impl.EleInternationalAccountDaoImpl;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;

/**
 * 删除服务器印章图片定时任务
 * @author WB000520
 * @time 20180727
 */
public class DelEleInternationalAccountSealPicJob implements Job {
	static Logger logger=LogManager.getLogger(DelEleInternationalAccountSealPicJob.class.getName());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EleInternationalAccountDaoImpl daoImpl = (EleInternationalAccountDaoImpl) getBean("EleAccountCheckDao");
		try {
			String rootPath=DJPropertyUtil.getPropertyValue("electronicAccountPicture");
			boolean sealPic = daoImpl.delEleInternationalAccountSealPic(rootPath);
			if(sealPic){
				logger.info("删除印章图片成功!");
			}else{
				logger.info("删除印章图片失败!");
			}
			/*删除图片完成后新建第二天的文件夹*/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String nextDate = sdf.format(System.currentTimeMillis()+24*60*60*1000);
			File fileName=new File(rootPath+nextDate);
			if(!fileName.exists()){
				boolean mkdirs = fileName.mkdirs();
				if(mkdirs)
					logger.info("创建保存印章图片文件夹成功!");
				else
					logger.info("创建保存印章图片文件夹失败!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 工具方法
	 * @param bean_name
	 * @return
	 */
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
}
