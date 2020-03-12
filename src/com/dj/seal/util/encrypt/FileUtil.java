package com.dj.seal.util.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 文件处理工具
 * 
 * @author oyxy
 * @since 2010-03-25
 * 
 */
public class FileUtil {
	static Logger logger = LogManager.getLogger(FileUtil.class.getName());
	public static boolean reName(String path, String name) throws IOException {
		File file = new File(path);
		return file.renameTo(new File(name));
	}

	public static boolean copy(String from, String to) throws IOException {
		FileInputStream fosfrom = new java.io.FileInputStream(from);
		java.io.FileOutputStream fosto = new FileOutputStream(to);
		byte bt[] = new byte[1024];
		int c;
		while ((c = fosfrom.read(bt)) > 0) {
			fosto.write(bt, 0, c);
		}
		fosfrom.close();
		fosto.close();
		return false;
	}

	/**
	 * 通过文件得到文件的byte数组
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileByte(String fileName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(fileName);
		logger.info("文件大小：" + fileInputStream.available());
		return getFileByte(fileInputStream);
	}

	/**
	 * 通过文件路径得到文件的byte数组
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileByte(URL url) throws IOException {
		if (url != null) {
			return getFileByte(url.openStream());
		} else {
			return null;
		}
	}

	/**
	 * 通过文件流得到文件的byte数组
	 * 
	 * @param in
	 * @return
	 */
	public byte[] getFileByte(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try {
			copy(in, out);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return out.toByteArray();

	}

	/**
	 * 把入流复制到出流
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private void copy(InputStream in, OutputStream out) throws IOException {

		try {
			byte[] buffer = new byte[4096];
			int nrOfBytes = -1;
			while ((nrOfBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
		} catch (IOException e) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
			}
		}

	}

	/**
	 * 把byte数组保存到指定路径下
	 * 
	 * @param bts
	 * @param filepath
	 * @throws IOException
	 */
	public void save(byte[] bts, String filepath) throws IOException {
		OutputStream out = new FileOutputStream(filepath);
		try {
			byte[] buffer = bts;
			int nrOfBytes = -1;
			if ((nrOfBytes = bts.length) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage());
			}
		}
	}

}
