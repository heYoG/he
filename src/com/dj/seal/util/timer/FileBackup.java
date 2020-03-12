package com.dj.seal.util.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.util.file.FtpUtil;

public class FileBackup implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FtpUtil.readConfigFileUpload(null);
	}

}
