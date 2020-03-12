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

public class DeletePDF implements Job {
	Logger logger = LogManager.getLogger(DeletePDF.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("-----开始删除凭证文件-----");
		try {
			deleteOldFile(DJPropertyUtil.getPropertyValue("savepdf").trim());
			SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
			Date nowTime = new Date(System.currentTimeMillis()+24 * 60 * 60* 1000);
			String nowTimeStr = sdfFileName.format(nowTime);
			String fileName =DJPropertyUtil.getPropertyValue("savepdf").trim()+File.separatorChar+nowTimeStr;
			//创建文件夹
			File dirFile = new File(fileName);
			if(!dirFile.exists()){
				logger.info("创建存放凭证文件文件夹，filename="+fileName);
				dirFile.mkdirs();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	/**
	 * 遍历文件删除2天之前的PDF文件和JPG文件
	 * 修正将日期转为整数直接相减错误，跨月相减的天数错误
	 * modify 20180802
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
				if (file.isDirectory()) {//根据路径判断是否为文件夹
					String str=file.getName().trim();//文件夹名称
					Date delDate=sdf1.parse(str);
					if((nowDate.getTime()-delDate.getTime())/(24*60*60*1000)>=savetime){
						File filelist=new File(file+"/");
						File[] filelists=filelist.listFiles();
						if(filelists.length>0){//文件夹不为空先删除里面文件,再删除文件夹
							for (File file2 : filelists) {
								file2.delete();
							}
						}						
						file.delete();//文件夹为空，直接删除此文件夹
					}		
				}else {//不是文件夹直接删除文件
					file.delete();
				}
			}
		}		
	}

	public static void main(String[] args) throws ParseException {
		deleteOldFile("C:/test.jpg");
	}

}
