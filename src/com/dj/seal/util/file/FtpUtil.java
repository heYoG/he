package com.dj.seal.util.file;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.properyUtil.DJPropertyUtil;

import java.io.*;
import java.net.SocketException;
import java.util.Properties;

/**
 * 
 * @author zbl 2013-3-27 10:32:39 QQ : 740222820
 */
public class FtpUtil {
	static Logger logger = LogManager.getLogger(FtpUtil.class.getName());
	private static String userName; // FTP 登录用户名
	private static String password; // FTP 登录密码
	private static String ip; // FTP 服务器地址IP地址
	private static int port; // FTP 端口
	private static Properties property = null; // 属性集
	private static String configFile; // 配置文件的路径名
	private static FTPClient ftpClient = null; // FTP 客户端代理
	private static StringBuffer strBuf = new StringBuffer();
	private static int fileType = FTP.BINARY_FILE_TYPE;// 设置传输二进制文件

	private static FileInputStream FIS;
	private static FileOutputStream FOS;

	// FTP状态码
	public static int i = 1;

	/**
	 * port 20数据端口
	 * port 21命令端口
	 * @param args
	 */
	public static void main(String[] args) {
		// setConfig("C:\\ftpconfig.properties");
		//connectServer("dj", "dj", 21, "192.168.1.55");
		connectServer("weblogic","weblogic",21,"128.160.1.181");
		uploadManyFile(new File("F:\\xiangmu"), new File("\\upggg222"));
		// uploadOneFile(new File("c:\\aaa\\bbb\\ccc\\temp.pdf"), new
		// File("\\up2222"),"");
		// loadFile("\\up2222\\SSHPRJ", "c://aaa//aaa","temp.pdf");
		// deleteFile("\\up2222\\hibernate注解技术解析\\Hibernate Annotation.rar");
		// deleteAllFileAndDirectory("\\test\\新建文本文档.txt.bak", "aaa");
		closeConnect();// 关闭连接
	}

	/**
	 * 上传单个文件，并重命名
	 * 
	 * @param localFile--本地文件路径
	 * @param localRootFile--ftp文件保存路径
	 * @param fileName--新的文件名,可以命名为空"",为空则默认为上传文件名
	 * @return true 上传成功，false 上传失败
	 */
	public static boolean uploadOneFile(File localFile,
			final File localRootFile, final String fileName) {
		boolean flag = true;
		try {
			// connectServer();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			InputStream input = new FileInputStream(localFile);
			if (input == null) {
				logger.info("本地文件" + localFile.getPath() + "不存在!");
			}
			String furi2 = localRootFile.getPath();
			String objFolder = File.separator + furi2;
			ftpClient.changeWorkingDirectory("/");
			ftpClient.makeDirectory(objFolder);
			ftpClient.changeWorkingDirectory(objFolder);
			if (fileName == null || "".equals(fileName)) {
				flag = ftpClient.storeFile(localFile.getName(), input);
			} else {
				flag = ftpClient.storeFile(fileName, input);
			}
			if (flag) {
				logger.info("-----上传成功！-----");
			} else {
				logger.info("------上传失败！------");
			}
			input.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 上传多个文件
	 * 
	 * @param localFile,--本地文件夹路径
	 * @param localRootFile--ftp文件保存路径
	 * @return 上传成功的文件名
	 */
	public static String uploadManyFile(File localFile, final File localRootFile) {
		boolean flag = true;
		String strBuf = "";
		int n = 0;
		try {
			// connectServer();
			logger.info(File.separator + localFile.getPath());
			ftpClient.makeDirectory(File.separator + localFile.getPath());
			File fileList[] = localFile.listFiles();
			int i = 0;
			for (File upfile : fileList) {
				if (upfile.isDirectory()) {// 文件夹中还有文件夹
					File file = new File(localRootFile.getPath()
							+ "//"
							+ upfile.getPath().substring(
									upfile.getPath().lastIndexOf("\\") + 1,
									upfile.getPath().length()));
					ftpClient.makeDirectory(File.separator
							+ localRootFile.getPath()
							+ "//"
							+ upfile.getPath().substring(
									upfile.getPath().lastIndexOf("\\") + 1,
									upfile.getPath().length()));
					uploadManyFile(upfile, file);
				} else {
					flag = uploadOneFile(upfile, localRootFile, null);
					if (flag == true) {
						i++;
					}
					if (flag) {
						strBuf = upfile.getName();
					}
				}
			}

		} catch (NullPointerException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return strBuf.toString();
	}

	/**
	 * 下载文件
	 * 
	 * @param remoteFileName
	 *            --服务器上的文件名
	 * @param localFileName--本地文件夹名称
	 * @param fileName
	 *            --下载文件名称 如果为""，会把所有文件备份到本地目录中
	 * @return true 下载成功，false 下载失败
	 */
	public static boolean loadFile(String remoteFilePath, String localFilePath,
			String fileName) {
		boolean flag = true;
		// connectServer();
		// 下载文件
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.changeWorkingDirectory(remoteFilePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftpClient.listFiles();

			for (FTPFile ff : fs) {
				if (!ff.isFile()) {
					String f = new String(ff.getName().getBytes("ISO-8859-1"),
							"GBK");
					logger.info(localFilePath + File.separator + f);
					File f0 = new File(localFilePath + File.separator + f);
					f0.mkdirs();
					// ftpClient.changeWorkingDirectory(remoteFilePath+File.separator+f);
					loadFile(remoteFilePath + File.separator + f, f0.getPath(),
							"");
				} else {
					String f = new String(ff.getName().getBytes("ISO-8859-1"),
							"GBK");
					if ("".equals(fileName)) {
						File localFile = new File(localFilePath + "/" + f);
						OutputStream is = new FileOutputStream(localFile);
						ftpClient.retrieveFile(ff.getName(), is);
						is.close();
					}
					if (f.equals(fileName)) {
						File localFile = new File(localFilePath + "/" + f);
						OutputStream is = new FileOutputStream(localFile);
						ftpClient.retrieveFile(ff.getName(), is);
						is.close();
					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 读取配置文件信息从ftp服务器中下载文件
	 * 
	 * @param configFilePath
	 *            配置文件路径
	 * @param#从ftp上恢复文件
	 * @paramreFtpUserName=ftptest ---ftp用户名
	 * @paramreFtpPassWord=123456weboff ---ftp密码
	 * @paramreFtpIP=192.168.1.72 ---ftpip
	 * @paramreFtpPort=21 ---ftp端口号
	 * @param
	 * @paramreFtpFilePath=\\upggg\\中文222 ---ftp要下载文件的路径
	 * @paramlocalFilePath=d:\\test ---备份路径
	 */
	public static boolean readConfigFileLoadFiles(String configFilePath) {
		property = new Properties();
		BufferedInputStream inBuff = null;
		String reFtpFilePath;
		String localFilePath;
		try {
			File file = new File(configFilePath);
			inBuff = new BufferedInputStream(new FileInputStream(file));
			property.load(inBuff);

			String userName = property.getProperty("reFtpUserName");
			String password = property.getProperty("reFtpPassWord");
			int port = Integer.parseInt(property.getProperty("reFtpPort"));
			String ip = property.getProperty("reFtpIP");
			reFtpFilePath = property.getProperty("reFtpFilePath");
			reFtpFilePath = new String(reFtpFilePath.getBytes("ISO-8859-1"),
					"GBK");
			localFilePath = property.getProperty("localFilePath");
			localFilePath = new String(localFilePath.getBytes("ISO-8859-1"),
					"GBK");
			File f = new File(localFilePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			FtpUtil.connectServer(userName, password, port, ip);
			FtpUtil.loadFile(reFtpFilePath, localFilePath, "");
			FtpUtil.closeConnect();
		} catch (FileNotFoundException e1) {
			logger.info("配置文件  不存在！");
		} catch (IOException e) {
			logger.info("配置文件  无法读取！");
		}
		return true;
	}

	/**
	 * 删除一个文件
	 * 
	 * @param filename
	 *            ftp路径+文件名
	 * @return true or false 删除成功或者失败
	 */
	public static boolean deleteFile(String filename) {
		boolean flag = true;
		try {
			// connectServer();
			flag = ftpClient.deleteFile(filename);
			if (flag) {
				logger.info("删除文件成功！");
			} else {
				logger.info("删除文件失败！");
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
		return flag;
	}

	/**
	 * 删除指定文件夹下的所有文件及文件夹
	 * 
	 * @param pathname
	 *            指定要删除的文件夹路径 或 者文件路径+文件名称
	 * @param 说明：当pathname指定到文件夹,并且savePath为""
	 *            时,连pathname以及以下目录全部删掉 ;
	 *            当pathname指定到文件夹,并且savePath==pathname时,清空pathnamed,但会保留pathbname ;
	 *            当pathname指定到文件时,并且savepath为""时,删除这个文件。
	 */

	public static void deleteAllFileAndDirectory(String pathname,
			String saveFilePath) {
		try {
			// connectServer();
			String files[] = ftpClient.listNames(pathname);
			if (files == null || files.length == 0) {
				if (!pathname.equals(saveFilePath)) {
					deleteEmptyDirectory(pathname);
				}
				logger.info("没有任何文件!");
			} else {
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
					logger.info(files[i]);
					if (!pathname.equals(saveFilePath)) {
						deleteEmptyDirectory(pathname);
					}
					deleteAllFileAndDirectory(files[i], saveFilePath);
				}
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 删除空目录
	 */
	public static void deleteEmptyDirectory(String pathname) {
		try {
			connectServer();
			ftpClient.removeDirectory(pathname);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 列出服务器上文件和目录
	 * 
	 * @param regStr
	 *            --匹配的正则表达式
	 */
	public static void listRemoteFiles(String regStr) {
		connectServer();
		try {
			String files[] = ftpClient.listNames(regStr);
			if (files == null || files.length == 0)
				logger.info("没有任何文件!");
			else {
				for (int i = 0; i < files.length; i++) {
					logger.info(files[i]);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 列出Ftp服务器上的所有文件和目录
	 */
	public static void listRemoteAllFiles() {
		connectServer();
		try {
			String[] names = ftpClient.listNames();
			for (int i = 0; i < names.length; i++) {
				logger.info(names[i]);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 关闭连接
	 */
	public static void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
				ftpClient = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 设置配置文件
	 * 
	 * @param configFile
	 */
	public static void setConfigFile(String configFile) {
		FtpUtil.configFile = configFile;
	}

	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 * 
	 * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
	 * 
	 */
	public static void setFileType(int fileType) {
		try {

			connectServer();
			ftpClient.setFileType(fileType);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 扩展使用
	 * 
	 * @return ftpClient
	 */
	protected static FTPClient getFtpClient() {
		connectServer();
		return ftpClient;
	}

	/**
	 * 设置参数
	 * 
	 * @param configFile
	 *            --参数的配置文件
	 * @param 配置文件参数说明：username=XXXX，password=XXXX，ip=XXXX，port=XXXX
	 */
	public static void setConfig(String configFile) {
		property = new Properties();
		BufferedInputStream inBuff = null;
		try {
			File file = new File(configFile);
			inBuff = new BufferedInputStream(new FileInputStream(file));
			property.load(inBuff);
			userName = property.getProperty("username");
			password = property.getProperty("password");
			ip = property.getProperty("ip");
			port = Integer.parseInt(property.getProperty("port"));
		} catch (FileNotFoundException e1) {
			logger.info("配置文件 " + configFile + " 不存在！");
		} catch (IOException e) {
			logger.info("配置文件 " + configFile + " 无法读取！");
		}
	}

	/**
	 * 连接到服务器
	 * 
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	public static boolean connectServer() {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
				// setArg(configFile);
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("GBK");
				ftpClient.setDefaultPort(port);
				ftpClient.configure(getFtpConfig());
				ftpClient.connect(ip);
				ftpClient.login(userName, password);
				ftpClient.setDefaultPort(port);
				// System.out.print(ftpClient.getReplyString());
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(120000);

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					System.err.println("FTP server refused connection.");
					// logger.debug("FTP 服务拒绝连接！");
					flag = false;
				}
				// logger.info(i);
				i++;
			} catch (SocketException e) {
				flag = false;
				logger.error(e.getMessage());
				System.err.println("登录ftp服务器 " + ip + " 失败,连接超时！");
			} catch (IOException e) {
				flag = false;
				logger.error(e.getMessage());
				System.err.println("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
			}
		}
		return flag;
	}

	/**
	 * 连接到服务器
	 * 
	 * @param userName
	 *            登录名
	 * @param password
	 *            连接密码
	 * @param port
	 *            端口号
	 * @param ip
	 *            连接ip地址
	 * @return true 连接服务器成功，false 连接服务器失败
	 */
	public static boolean connectServer(String userName, String password,
			int port, String ip) {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
				// setArg(configFile);
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("GBK");
				ftpClient.setDefaultPort(port);
				ftpClient.configure(getFtpConfig());
				ftpClient.connect(ip);
				ftpClient.login(userName, password);
				ftpClient.setDefaultPort(port);
				// System.out.print(ftpClient.getReplyString());
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(120000);

				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					System.err.println("FTP server refused connection.");
					// logger.debug("FTP 服务拒绝连接！");
					flag = false;
				}
				// logger.info(i);
				i++;
			} catch (SocketException e) {
				flag = false;
				//logger.error(e.getMessage());
				e.printStackTrace();
				//System.err.println("登录ftp服务器 " + ip + " 失败,连接超时！");
			} catch (IOException e) {
				flag = false;
				//logger.error(e.getMessage());
				e.printStackTrace();
				System.err.println("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
			}
		}
		return flag;
	}

	/**
	 * 进入到服务器的某个目录下
	 * 
	 * @param directory
	 */
	public static void changeWorkingDirectory(String directory) {
		try {
			connectServer();
			ftpClient.changeWorkingDirectory(directory);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 返回到上一层目录
	 */
	public static void changeToParentDirectory() {
		try {
			connectServer();
			ftpClient.changeToParentDirectory();
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param oldFileName
	 *            --原文件名
	 * @param newFileName
	 *            --新文件名
	 */
	public static void renameFile(String oldFileName, String newFileName) {
		try {
			connectServer();
			ftpClient.rename(oldFileName, newFileName);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}

	/**
	 * 设置FTP客服端的配置--一般可以不设置
	 * 
	 * @return ftpConfig
	 */
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}

	/**
	 * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
	 * 
	 * @param obj
	 * @return ""
	 */
	private static String iso8859togbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 在服务器上创建一个文件夹
	 * 
	 * @param dir
	 *            文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
	 */
	public static boolean makeDirectory(String dir) {
		connectServer();
		boolean flag = true;
		try {
			// logger.info("dir=======" dir);
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				logger.info("make Directory " + dir + " succeed");

			} else {

				logger.info("make Directory " + dir + " false");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 文件夹中文件的转移操作
	 * 
	 * @param SrcDirectoryPath
	 *            待copy文件夹路径
	 * @param DesDirectoryPath
	 *            目标文件夹路径
	 * @return true or false 成功或者失败
	 */
	public static boolean copyDirectory(String SrcDirectoryPath,
			String DesDirectoryPath) {

		try {
			// 創建不存在的目錄
			File F0 = new File(DesDirectoryPath);
			if (!F0.exists()) {
				F0.mkdirs();
			}
			File F = new File(SrcDirectoryPath);
			if (!F.exists()) {
				F.mkdirs();
			}
			File[] allFile = F.listFiles(); // 取得當前目錄下面的所有文件，將其放在文件數組中
			int totalNum = allFile.length; // 取得當前文件夾中有多少文件（包括文件夾）
			String srcName = "";
			String desName = "";
			int currentFile = 0;
			// 一個一個的拷貝文件
			for (currentFile = 0; currentFile < totalNum; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					// 如果是文件是采用處理文件的方式
					srcName = allFile[currentFile].toString();
					desName = DesDirectoryPath + "\\"
							+ allFile[currentFile].getName();
					copyFile(srcName, desName);
				}
				// 如果是文件夾就采用遞歸處理
				else {
					// 利用遞歸讀取文件夾中的子文件下的內容，再讀子文件夾下面的子文件夾下面的內容...
					if (copyDirectory(
							allFile[currentFile].getPath().toString(),
							DesDirectoryPath + "\\"
									+ allFile[currentFile].getName().toString())) {
						// logger.info("D Copy Successfully!");
					} else {
						logger.info("SubDirectory Copy Error!");
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public static boolean copyFile(String src, String des) {
		try {
			FIS = new FileInputStream(src);
			FOS = new FileOutputStream(des);
			byte[] bt = new byte[1024];
			int readNum = 0;
			while ((readNum = FIS.read(bt)) != -1) {
				FOS.write(bt, 0, bt.length);
			}
			FIS.close();
			FOS.close();
			return true;
		} catch (Exception e) {
			try {
				FIS.close();
				FOS.close();
			} catch (IOException f) {
				// TODO
			}
			return false;
		} finally {
		}
	}

	/**ccccc
	 * 读取配置文的方式，备份到本地另一个盘符或者上传到ftp(可配置多个ftp连接，可配置多个本地磁盘目录,具体配置可参考ftpconfig.properties)
	 * 
	 * @param #ftp连接数量
	 * @param ftpNum=2
	 * 
	 * @param ftp1username=dj
	 * @param ftp1password=dj
	 * @param ftp1ip=192.168.1.55
	 * @param ftp1port=21
	 * 
	 * @param ftp2username=ftptest
	 * @param ftp2password=123456weboff
	 * @param ftp2ip=192.168.1.72
	 * @param ftp2port=21
	 * 
	 * 
	 * @param #备份文件键值对数量(键值对包含：source+数值+path
	 *            ---源文件路径 ，(ftp)bakup+数值+path ---待备份路径
	 *            ,注：当带备份路径参数的前三个字母为'ftp'时，就是ftp端的备份，不带'ftp'的为本地文件不同盘符之间的备份)
	 * @param bakupNum=3
	 * 
	 * @param source1path=e:\\abc
	 * @param bakup1path=\\upggg\\中文222
	 * 
	 * @param source3path=e:\\abc
	 * @param bakup3path=\\bbbbb\\中文cccc
	 * 
	 * @param source2path=C:\\abc
	 * @param ftpbakup2path=D://abc//ftp测试中文
	 * 
	 * @param configFilePath
	 *            配置文件的路径 return true or false; 备份成功 或者 失败
	 */
	public static boolean readConfigFileUpload(String configFilePath) {
		try {
			property = DJPropertyUtil.getPro();
			
			String ftpNumStr = property.getProperty("ftpNum");// 获取连接ftp的数量
			if (ftpNumStr == null) {
				ftpNumStr = "0";
			}
			int ftpNumInt = Integer.parseInt(ftpNumStr);
			if (ftpNumInt > 0) {
				for (Integer j = 1; j < ftpNumInt + 1; j++) {

					String userName = property.getProperty("ftp" + j
							+ "username");
					String password = property.getProperty("ftp" + j
							+ "password");
					String portStr = property.getProperty("ftp" + j + "port");
					if ("".equals(portStr.trim())) {
						portStr = "0";
					}
					int port = Integer.parseInt(portStr);
					String ip = property.getProperty("ftp" + j + "ip");
					String sourcePath;// 源文件夹路径
					String bakupPath;// 备份文件夹路径
					String bakupNumStr = property.getProperty("bakupNum");// 获取备份文件键值对的数量"1_2,2_3,3_1"
					String[] bakupNumStrs = bakupNumStr.split(",");// [1_2,2_3,3_1]
					for (int k = 0; k < bakupNumStrs.length; k++) {
						String[] baks = bakupNumStrs[k].split("_");// [1,2]
						if (baks[0].equals(j.toString())) {
							for (int i = 1; i < Integer.parseInt(baks[1]) + 1; i++) {
								sourcePath = property.getProperty("source" + j
										+ "_" + i + "path");
								sourcePath = new String(sourcePath
										.getBytes("ISO-8859-1"), "GBK");// 解决读取配置文件中文乱码问题
								bakupPath = property.getProperty("ftpbakup" + j
										+ "_" + i + "path");
								if (bakupPath != null) {
									bakupPath = new String(bakupPath
											.getBytes("ISO-8859-1"), "GBK");
									
									System.out.print(FtpUtil.connectServer(userName, password,
											port, ip));
									String returnVal = FtpUtil.uploadManyFile(
											new File(sourcePath), new File(
													bakupPath));
									if (returnVal != null
											&& !"".equals(returnVal)) {
										FtpUtil.closeConnect();
									}
								} else {
									bakupPath = property.getProperty("bakup"
											+ j +"_"+i+ "path");
									bakupPath = new String(bakupPath
											.getBytes("ISO-8859-1"), "GBK");
									FtpUtil
											.copyDirectory(sourcePath,
													bakupPath);
								}

							}
						}
					}
				}
			}
			String sourcePath;// 源文件夹路径
			String bakupPath;// 备份文件夹路径
			// property.load(inBuff);
			String localBakupNum = property.getProperty("localBakupNum");
			if (localBakupNum == null) {
				localBakupNum = "0";
			}
			Integer localNum = Integer.parseInt(localBakupNum);
			if (!"".equals(localBakupNum)) {
				for (int n = 1; n < (localNum + 1); n++) {
					bakupPath = property.getProperty("local" + n + "path");
					bakupPath = new String(bakupPath.getBytes("ISO-8859-1"),
							"GBK");
					sourcePath = property.getProperty("bakup" + n + "path");
					sourcePath = new String(sourcePath.getBytes("ISO-8859-1"),
							"GBK");
					FtpUtil.copyDirectory(bakupPath, sourcePath);
				}
			}
			// }
		} catch (IOException e) {
			logger.info("配置文件  无法读取！");
		}

		return true;
	}

	public static String getConfigFile() {
		return configFile;
	}

	public static int getFileType() {
		return fileType;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		FtpUtil.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		FtpUtil.password = password;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		FtpUtil.ip = ip;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		FtpUtil.port = port;
	}

	public static Properties getProperty() {
		return property;
	}

	public static void setProperty(Properties property) {
		FtpUtil.property = property;
	}

	public static int getI() {
		return i;
	}

	public static void setI(int i) {
		FtpUtil.i = i;
	}

	public static void setFtpClient(FTPClient ftpClient) {
		FtpUtil.ftpClient = ftpClient;
	}

	public static StringBuffer getStrBuf() {
		return strBuf;
	}

	public static void setStrBuf(StringBuffer strBuf) {
		FtpUtil.strBuf = strBuf;
	}

	public static FileInputStream getFIS() {
		return FIS;
	}

	public static void setFIS(FileInputStream fis) {
		FIS = fis;
	}

	public static FileOutputStream getFOS() {
		return FOS;
	}

	public static void setFOS(FileOutputStream fos) {
		FOS = fos;
	}

}