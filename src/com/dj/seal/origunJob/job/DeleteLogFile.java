package com.dj.seal.origunJob.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class DeleteLogFile implements Job{
	Logger logger = LogManager.getLogger(DeleteNoSealPDF.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("-----开始删除日志文件-----");
		String rootPath=DJPropertyUtil.getPropertyValue("saveLogFilePath").trim();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date(System.currentTimeMillis());
		File root = new File(rootPath);
		File[] files = root.listFiles();
		if(files.length>0){
			for (File file : files) {
				if (!file.isDirectory()) {
					if(!file.getName().equals("error.log")){
						// Date fileDate = sdf1.parse(filename);
						Date time = new Date(file.lastModified());
						//logger.info(file.getName()+"----------"+(nowDate.getTime() - time.getTime())/ (24 * 60 * 60 * 1000));
						if ((nowDate.getTime() - time.getTime())/ (24 * 60 * 60 * 1000) >= 1) {
							file.delete();
						}
					}					
				}
			}
		}
	}
}
