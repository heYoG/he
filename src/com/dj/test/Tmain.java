package com.dj.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class Tmain {
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
		Date dt=new Date(System.currentTimeMillis());
		String nowTime= sdf.format(dt);//系统当前日期
		String pathNoSeal = DJPropertyUtil.getPropertyValue("accumulationFilePath_NoSeal");
		String filePathNoSeal=pathNoSeal+nowTime;
		File file1=new File(filePathNoSeal+File.separatorChar+"HDFileInfo.txt");//清单目录
		System.out.println(file1.getAbsolutePath());
		
	}
}
