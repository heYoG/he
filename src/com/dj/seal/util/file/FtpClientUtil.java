package com.dj.seal.util.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

/**
 * 
 * @author laihuanhui
 * 
 */
public class FtpClientUtil {
	static Logger logger = LogManager.getLogger(FtpClientUtil.class.getName());
	FtpClient ftpClient;
	private String server;
	private String port;
	private String userName;
	private String userPassword;

	public FtpClientUtil(String server, String userName, String userPassword,
			String port) {
		this.server = server;
		// this.port = port;
		this.userName = userName;
		this.userPassword = userPassword;
		this.port = port;
	}

	/**
	 * 链接到服务器
	 * 
	 * @return
	 */
	public boolean open() {
		if (ftpClient != null && ftpClient.serverIsOpen())
			return true;
		try {
			ftpClient = new FtpClient();
			if (port.equals("")) {
				ftpClient.openServer(server);// 连接ftp服务器
			} else {
				ftpClient.openServer(server, Integer.parseInt(port));
			}
			ftpClient.login(userName, userPassword);
			ftpClient.binary();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ftpClient = null;
			return false;
		}
	}

	/**
	 * 链接到服务器
	 * 
	 * @return
	 */
	public boolean openALL(String ftpport) {
		if (ftpClient != null && ftpClient.serverIsOpen())
			return true;
		try {
			ftpClient = new FtpClient();
			if (ftpport.equals("") || ftpport.equals(null)) {
				ftpClient.openServer(server);// 连接ftp服务器
			} else {
				ftpClient.openServer(server, Integer.parseInt(ftpport));
			}
			ftpClient.login(userName, userPassword);
			ftpClient.binary();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ftpClient = null;
			return false;
		}
	}

	public static String uploadDZSW(String server, String user,
			String password, String path, String filepath, String filename)
			throws Exception {
		FtpClient ftpClient = new FtpClient();
		try {
			ftpClient.openServer(server);
			ftpClient.login(user, password);
			logger.info("path:" + path);
			// path是ftp服务下主目录的子目录
			if (path.length() != 0) {
				ftpClient.cd(path);
			}
			ftpClient.binary();
		} catch (Exception e) {
			throw new Exception("ftp server连接失败。");
		}
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			// "upftpfile"用ftp上传后的新文件名
			os = ftpClient.put(filename);
			java.io.File file_in = new java.io.File(filepath);
			if (file_in.length() == 0) {
				return "上传文件为空!";
			}
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return "上传文件成功!";

	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localPathAndFileName
	 *            本地文件目录和文件名
	 * @param ftpFileName
	 *            上传后的文件名
	 * @param ftpDirectory
	 *            FTP目录如:/path1/pathb2/,如果目录不存在会自动创建目录
	 * @throws Exception
	 */
	public boolean upload(String localDirectoryAndFileName, String ftpFileName,
			String ftpDirectory) throws Exception {
		if (!open())
			return false;
		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			char ch = ' ';
			if (ftpDirectory.length() > 0)
				ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
			for (; ch == '/' || ch == '\\'; ch = ftpDirectory
					.charAt(ftpDirectory.length() - 1))
				ftpDirectory = ftpDirectory.substring(0,
						ftpDirectory.length() - 1);

			int slashIndex = ftpDirectory.indexOf(47);
			int backslashIndex = ftpDirectory.indexOf(92);
			int index = slashIndex;
			String dirall = ftpDirectory;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
			String directory = "";
			while (index != -1) {
				if (index > 0) {
					String dir = dirall.substring(0, index);
					directory = directory + "/" + dir;
					logger.info("directory5:" + directory);
					ftpClient.sendServer("XMKD " + directory + "\r\n");
					ftpClient.readServerResponse();
				}
				dirall = dirall.substring(index + 1);
				slashIndex = dirall.indexOf(47);
				backslashIndex = dirall.indexOf(92);
				index = slashIndex;
				if (backslashIndex != -1
						&& (index == -1 || index > backslashIndex))
					index = backslashIndex;
			}
			ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
			ftpClient.readServerResponse();

			os = ftpClient.put(ftpDirectory + "/" + ftpFileName);
			File file_in = new File(localDirectoryAndFileName);
			is = new FileInputStream(file_in);
			byte bytes[] = new byte[1024];
			int i;
			while ((i = is.read(bytes)) != -1)
				os.write(bytes, 0, i);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localPathAndFileName
	 *            本地文件目录和文件名
	 * @param ftpFileName
	 *            上传后的文件名
	 * @param ftpDirectory
	 *            FTP目录如:/path1/pathb2/,如果目录不存在回自动创建目录
	 * @throws Exception
	 */
	public boolean uploadALL_LINUXDZSW(String localDirectoryAndFileName,
			String ftpport, String ftpFileName, String ftpDirectory)
			throws Exception {
		if (!openALL(ftpport))
			return false;
		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			char ch = ' ';
			if (ftpDirectory.length() > 0)
				ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
			for (; ch == '/' || ch == '\\'; ch = ftpDirectory
					.charAt(ftpDirectory.length() - 1))
				ftpDirectory = ftpDirectory.substring(0,
						ftpDirectory.length() - 1);

			int slashIndex = ftpDirectory.indexOf(47);
			int backslashIndex = ftpDirectory.indexOf(92);
			int index = slashIndex;
			String dirall = ftpDirectory;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
			String directory = "";
			while (index != -1) {
				if (index > 0) {
					String dir = dirall.substring(0, index);
					directory = directory + "/" + dir;
					logger.info("directory5:" + directory);
					// ftpClient.sendServer("XMKD " + directory + "\r\n");
					// ftpClient.readServerResponse();
					ftpClient.sendServer("PASV");
				}
				dirall = dirall.substring(index + 1);
				slashIndex = dirall.indexOf(47);
				backslashIndex = dirall.indexOf(92);
				index = slashIndex;
				if (backslashIndex != -1
						&& (index == -1 || index > backslashIndex))
					index = backslashIndex;
			}
			// ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
			// ftpClient.readServerResponse();
			ftpClient.sendServer("PASV");
			// os = ftpClient.put(ftpDirectory + "/" + ftpFileName);
			os = ftpClient.put(ftpFileName);
			File file_in = new File(localDirectoryAndFileName);
			is = new FileInputStream(file_in);
			byte bytes[] = new byte[1024];
			int i;
			while ((i = is.read(bytes)) != -1)
				os.write(bytes, 0, i);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localPathAndFileName
	 *            本地文件目录和文件名
	 * @param ftpFileName
	 *            上传后的文件名
	 * @param ftpDirectory
	 *            FTP目录如:/path1/pathb2/,如果目录不存在回自动创建目录
	 * @throws Exception
	 */
	public boolean uploadALL_LINUX(String localDirectoryAndFileName,
			String ftpport, String ftpFileName, String ftpDirectory)
			throws Exception {
		if (!openALL(ftpport))
			return false;
		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			char ch = ' ';
			if (ftpDirectory.length() > 0)
				ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
			for (; ch == '/' || ch == '\\'; ch = ftpDirectory
					.charAt(ftpDirectory.length() - 1))
				ftpDirectory = ftpDirectory.substring(0,
						ftpDirectory.length() - 1);

			int slashIndex = ftpDirectory.indexOf(47);
			int backslashIndex = ftpDirectory.indexOf(92);
			int index = slashIndex;
			String dirall = ftpDirectory;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
			String directory = "";
			while (index != -1) {
				if (index > 0) {
					String dir = dirall.substring(0, index);
					directory = directory + "/" + dir;
					logger.info("directory5:" + directory);
					ftpClient.sendServer("XMKD " + directory + "\r\n");
					ftpClient.readServerResponse();
				}
				dirall = dirall.substring(index + 1);
				slashIndex = dirall.indexOf(47);
				backslashIndex = dirall.indexOf(92);
				index = slashIndex;
				if (backslashIndex != -1
						&& (index == -1 || index > backslashIndex))
					index = backslashIndex;
			}
			ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
			ftpClient.readServerResponse();
			os = ftpClient.put(ftpDirectory + "/" + ftpFileName);
			File file_in = new File(localDirectoryAndFileName);
			is = new FileInputStream(file_in);
			byte bytes[] = new byte[1024];
			int i;
			while ((i = is.read(bytes)) != -1)
				os.write(bytes, 0, i);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localPathAndFileName
	 *            本地文件目录和文件名
	 * @param ftpFileName
	 *            上传后的文件名
	 * @param ftpDirectory
	 *            FTP目录如:/path1/pathb2/,如果目录不存在回自动创建目录
	 * @throws Exception
	 */
	public boolean uploadALL_WIN(String localDirectoryAndFileName,
			String ftpport, String ftpFileName, String ftpDirectory)
			throws Exception {
		if (!openALL(ftpport))
			return false;
		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			char ch = ' ';
			if (ftpDirectory.length() > 0)
				ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
			for (; ch == '/' || ch == '\\'; ch = ftpDirectory
					.charAt(ftpDirectory.length() - 1))
				ftpDirectory = ftpDirectory.substring(0,
						ftpDirectory.length() - 1);

			int slashIndex = ftpDirectory.indexOf(47);
			int backslashIndex = ftpDirectory.indexOf(92);
			int index = slashIndex;
			String dirall = ftpDirectory;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
			String directory = "";
			while (index != -1) {
				if (index > 0) {
					String dir = dirall.substring(0, index);
					directory = directory + "\\" + dir;
					logger.info("directory5:" + directory);
					ftpClient.sendServer("XMKD " + directory + "\r\n");
					ftpClient.readServerResponse();
				}
				dirall = dirall.substring(index + 1);
				slashIndex = dirall.indexOf(47);
				backslashIndex = dirall.indexOf(92);
				index = slashIndex;
				if (backslashIndex != -1
						&& (index == -1 || index > backslashIndex))
					index = backslashIndex;
			}
			ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
			ftpClient.readServerResponse();
			logger.info(ftpDirectory + "\\" + ftpFileName);
			os = ftpClient.put(ftpDirectory + "\\" + ftpFileName);
			File file_in = new File(localDirectoryAndFileName);
			is = new FileInputStream(file_in);
			byte bytes[] = new byte[1024];
			int i;
			while ((i = is.read(bytes)) != -1)
				os.write(bytes, 0, i);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localPathAndFileName
	 *            本地文件目录和文件名
	 * @param ftpFileName
	 *            上传后的文件名
	 * @param ftpDirectory
	 *            FTP目录如:/path1/pathb2/,如果目录不存在回自动创建目录
	 * @throws Exception
	 */
	public boolean upload_DZSW(String localDirectoryAndFileName,
			String ftpFileName, String ftpDirectory) throws Exception {
		if (!open())
			return false;
		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			char ch = ' ';
			if (ftpDirectory.length() > 0)
				ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
			for (; ch == '/' || ch == '\\'; ch = ftpDirectory
					.charAt(ftpDirectory.length() - 1))
				ftpDirectory = ftpDirectory.substring(0,
						ftpDirectory.length() - 1);

			int slashIndex = ftpDirectory.indexOf(47);
			int backslashIndex = ftpDirectory.indexOf(92);
			int index = slashIndex;
			String dirall = ftpDirectory;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
			String directory = "";
			while (index != -1) {
				if (index > 0) {
					String dir = dirall.substring(0, index);
					directory = directory + "\\" + dir;
					logger.info("directory5:" + directory);
					ftpClient.sendServer("XMKD " + directory + "\r\n");
					ftpClient.readServerResponse();
				}
				dirall = dirall.substring(index + 1);
				slashIndex = dirall.indexOf(47);
				backslashIndex = dirall.indexOf(92);
				index = slashIndex;
				if (backslashIndex != -1
						&& (index == -1 || index > backslashIndex))
					index = backslashIndex;
			}
			ftpClient.sendServer("XMKD " + ftpDirectory + "\r\n");
			ftpClient.readServerResponse();

			os = ftpClient.put(ftpDirectory + "\\" + ftpFileName);
			File file_in = new File(localDirectoryAndFileName);
			is = new FileInputStream(file_in);
			byte bytes[] = new byte[1024];
			int i;
			while ((i = is.read(bytes)) != -1)
				os.write(bytes, 0, i);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}

	/**
	 * 从FTP服务器上下载文件并返回下载文件长度
	 * 
	 * @param ftpDirectoryAndFileName
	 * @param localDirectoryAndFileName
	 * @return
	 * @throws Exception
	 */
	public long download(String ftpDirectoryAndFileName,
			String localDirectoryAndFileName) throws Exception {
		long result = 0;
		if (!open())
			return result;
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			is = ftpClient.get(ftpDirectoryAndFileName);
			java.io.File outfile = new java.io.File(localDirectoryAndFileName);
			os = new FileOutputStream(outfile);

			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result = result + c;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();

		}
		return result;
	}

	/**
	 * 返回FTP目录下的文件列表
	 * 
	 * @param ftpDirectory
	 * @return
	 */
	public List<String> getFileNameList(String ftpDirectory) {
		List<String> list = new ArrayList<String>();
		if (!open())
			return list;
		try {
			DataInputStream dis = new DataInputStream(
					ftpClient.nameList(ftpDirectory));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	/**
	 * 删除FTP上的文件
	 * 
	 * @param ftpDirAndFileName
	 */
	public boolean deleteFile(String ftpDirAndFileName) {
		if (!open())
			return false;
		ftpClient.sendServer("DELE " + ftpDirAndFileName + "\r\n");
		return true;
	}

	/**
	 * 删除FTP目录
	 * 
	 * @param ftpDirectory
	 */
	public boolean deleteDirectory(String ftpDirectory) {
		if (!open())
			return false;
		ftpClient.sendServer("XRMD " + ftpDirectory + "\r\n");
		return true;
	}

	/**
	 * 创建FTP目录
	 * 
	 * @param ftpDirectory
	 */
	public boolean createDirectory(String ftpDirectory) {
		if (!open())
			return false;
		ftpClient.sendServer("MKD " + ftpDirectory + "\r\n");
		return true;
	}

	/**
	 * 重命名FTP目录
	 * 
	 * @param ftpDirectory
	 */
	public boolean renameDirectoryAndFileName(String oldftpDirectory,
			String newftpDirectory) {
		if (!open())
			return false;
		ftpClient.sendServer("RNFR " + oldftpDirectory + "\r\n");
		ftpClient.sendServer("RNTO " + newftpDirectory + "\r\n");
		return true;
	}

	/**
	 * 往文件中写内容
	 * 
	 * @param ftpDirectory
	 */
	public boolean writeContent(String Directoryfile) {
		if (!open())
			return false;
		try {
			PrintWriter pw = new PrintWriter(ftpClient.put(Directoryfile)); // 写入的文件名.文件名可以不存在
			pw.write(" this is a test \r\n");
			pw.write(" this is a test \r\n");
			pw.write(" this is a test \r\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	/**
	 * 关闭链接
	 */
	public void close() {
		try {
			if (ftpClient != null && ftpClient.serverIsOpen())
				ftpClient.closeServer();
		} catch (Exception e) {

		}
	}

	/**
	 * 读文件数据流
	 * 
	 * @param ftpDirectoryAndFileName
	 * @return
	 * @throws Exception
	 */
	public TelnetInputStream readRemoteStream(String ftpDirectoryAndFileName)
			throws Exception {
		if (open()) {
			TelnetInputStream is = null;
			try {
				is = ftpClient.get(ftpDirectoryAndFileName);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return is;
		} else {
			return null;
		}
	}

	/**
	 * port 20数据端口
	 * port 21命令端口
	 * @param args
	 */
	public static void main(String[] args) {
		// // TODO Auto-generated method stub
		// FtpClientUtil obj = new FtpClientUtil("192.168.1.123", 22,"root",
		// "123456");
		// FtpClientUtil obj = new FtpClientUtil("192.168.1.123","root",
		// "123456","21");
		FtpClientUtil obj = new FtpClientUtil("128.160.1.181", "weblogic","weblogic", "22");
		obj.open();
		try {
			boolean returnVaule = obj.upload("c:\\DZSWsys_testNo15.pdf",
					"DZSWsys_testNo15.pdf", "/usr/dianju/123");
			logger.info("返回信息：" + returnVaule);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
}
