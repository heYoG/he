package com.dj.seal.sysmgr.util.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * java外部系统命令模式备份数据库
 * 
 * @author lzl
 * @version 2013-4-16
 */
public class BackupOracleDatabase {
	
	static Logger logger = LogManager.getLogger(BackupOracleDatabase.class.getName());

	private String userName;
	private String userPass;
	private String serverAddr;
	private String outFilePath;
	private String dbName;
	private String filename;

	public BackupOracleDatabase(String userName, String userPass,
			String serverAddr, String port, String dbName, String outFilePath,
			String filename) {
		File f = new File(outFilePath);
		f.mkdirs();
		this.userName = userName;
		this.userPass = userPass;
		this.dbName = dbName;
		this.serverAddr = serverAddr + ":" + port + "/" + this.dbName;
		this.filename = filename;
		this.outFilePath = outFilePath + this.filename;

	}

	public void doWork() {
		File tmpOutFile = new File(this.outFilePath);
		File outDir = tmpOutFile.getParentFile();
		ProcessBuilder pbInst = buildProcessBuilder(outDir);
		try {
			Process proInst = pbInst.start();
			final InputStream ins = proInst.getInputStream();
			File outFile = new File(outDir, "exp.log");
			outFile.createNewFile();
			final FileChannel focInst = new FileOutputStream(outFile)
					.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			ReadableByteChannel rbcObj = Channels.newChannel(ins);
			try {
				while (rbcObj.read(byteBuffer) != -1) {
					byteBuffer.flip();
					focInst.write(byteBuffer);
					byteBuffer.clear();
				}
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	public void doRCWork() {
		File tmpOutFile = new File(this.outFilePath);
		File outDir = tmpOutFile.getParentFile();
		ProcessBuilder pbInst = buildRCProcessBuilder(outDir);
		try {
			Process proInst = pbInst.start();
			final InputStream ins = proInst.getInputStream();
			File outFile = new File(outDir, "exp.log");
			outFile.createNewFile();
			final FileChannel focInst = new FileOutputStream(outFile)
					.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			ReadableByteChannel rbcObj = Channels.newChannel(ins);
			try {
				while (rbcObj.read(byteBuffer) != -1) {
					byteBuffer.flip();
					focInst.write(byteBuffer);
					byteBuffer.clear();
				}
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 提供执行的命令串
	 * 
	 * @return commStr 命令串
	 */
	private List doBuildCommand() {
		List rtnList = new ArrayList();
		rtnList.add("EXP");
		rtnList.add("@USER@/@PASSWORD@@@SERVER@".replaceAll("@USER@",
				this.userName).replaceAll("@PASSWORD@", this.userPass)
				.replaceAll("@SERVER@", this.serverAddr));
		rtnList.add("FILE=@FILE@".replaceAll("@FILE@", this.outFilePath));
		// rtnList.add("TABLES=(SY_TABLE_DEF)");
		return rtnList;
	}

	/**
	 * 提供执行恢复的命令串
	 * 
	 * @return commStr 命令串
	 */
	private List doBuildRCCommand() {
		List rtnList = new ArrayList();
		rtnList.add("IMP");
		rtnList.add("@USER@/@PASSWORD@@@SERVER@".replaceAll("@USER@",
				this.userName).replaceAll("@PASSWORD@", this.userPass)
				.replaceAll("@SERVER@", this.serverAddr));
		rtnList.add("FILE=@FILE@".replaceAll("@FILE@", this.outFilePath));
		rtnList.add("FULL=Y");
		return rtnList;
	}

	/**
	 * 构建ProcessBuilder实例
	 * 
	 * @param workDir
	 *            当前进程工作目录
	 * @return pbInst ProcessBuilder实例
	 * @see ProcessBuilder
	 */
	private ProcessBuilder buildProcessBuilder(File workDir) {
		new ProcessBuilder();
		List commandArray = (doBuildCommand());
		logger.info(commandArray);
		ProcessBuilder pbInst = new ProcessBuilder(commandArray);
		pbInst.command(commandArray);
		Map envMap = pbInst.environment();
		envMap.clear();
		envMap.putAll(System.getenv());
		pbInst.directory(workDir);
		pbInst.redirectErrorStream(true);
		return pbInst;
	}

	/**
	 * 构建ProcessBuilder实例
	 * 
	 * @param workDir
	 *            当前进程工作目录
	 * @return pbInst ProcessBuilder实例
	 * @see ProcessBuilder
	 */
	private ProcessBuilder buildRCProcessBuilder(File workDir) {
		new ProcessBuilder();
		List commandArray = (doBuildRCCommand());
		logger.info(commandArray);
		ProcessBuilder pbInst = new ProcessBuilder(commandArray);
		pbInst.command(commandArray);
		Map envMap = pbInst.environment();
		envMap.clear();
		envMap.putAll(System.getenv());
		pbInst.directory(workDir);
		pbInst.redirectErrorStream(true);
		return pbInst;
	}

	public static void main(String[] args) {
		new BackupOracleDatabase("bzseal", "seal", "127.0.0.1", "1521", "orcl",
				"D:/backup/", "12345677.DMP").doWork();
	}
}
