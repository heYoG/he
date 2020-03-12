package com.dj.seal.origunJob.job;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class DeleteNoSealPDF implements Job {
	Logger logger = LogManager.getLogger(DeleteNoSealPDF.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("-----开始删除未签章凭证文件-----");
		try {
			deleteOldFile(DJPropertyUtil.getPropertyValue("noseal_path").trim());
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
			Date nowTime = new Date(System.currentTimeMillis() + 24 * 60 * 60
					* 1000);
			String nowTimeStr = sdfFileName.format(nowTime);
			String fileName = DJPropertyUtil.getPropertyValue("noseal_path").trim() + File.separatorChar + nowTimeStr;
			// 创建文件夹
			File dirFile = new File(fileName);
			if (!dirFile.exists()) {
				logger.info("创建存放未签章凭证文件文件夹，filename=" + fileName);
				dirFile.mkdirs();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	/**
	 * 遍历文件删除7天之前的未签章PDF文件和JPG文件
	 * 
	 * @throws ParseException
	 */
	public static void deleteOldFile(String rootPath) throws ParseException {
		int savetime=Integer.parseInt(DJPropertyUtil.getPropertyValue("file_savetime").toString().trim());
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date(System.currentTimeMillis());
		File root = new File(rootPath);
		File[] files = root.listFiles();
		if(files.length>0){
			for (File file : files) {
				if (file.isDirectory()) {
					// Date fileDate = sdf1.parse(filename);
					/*Date time = new Date(file.lastModified());
					System.out.println((nowDate.getTime() - time.getTime())
							/ (24 * 60 * 60 * 1000));*/
					String str=file.getName().trim();
					//System.out.println(Integer.parseInt(sdf1.format(nowDate))-Integer.parseInt(str));
					if (Integer.parseInt(sdf1.format(nowDate))-Integer.parseInt(str)>=savetime) {
						File filelist = new File(file + "/");
						File[] filelists = filelist.listFiles();
						if(filelists.length>0){
							for (File file2 : filelists) {
								file2.delete();
							}
						}						
						file.delete();
					}
				}
			}
		}		
	}

	public static void main(String[] args) throws ParseException {
		deleteOldFile("C:/test.jpg");
	}
}
