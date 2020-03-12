package com.dj.hotelApi.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import com.dj.hotelApi.InterfaceClass;
import com.dj.seal.util.Constants;

public class HotelFileUtil {

	public static String getFileSavePath() {
		String bpath = Constants.basePath;
		try {
			String is_type = Constants.getProperty("is_type");
			if (is_type.equals("1")) {// 从配置文件读取路径
				bpath = Constants.getProperty("hotelFileSavePath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpath;
	}

	public static String saveHotelFile(String filename, byte[] filebyte)
			throws IOException {
		String returnMesage = "";
		String filePath = getFileSavePath();
		if (!filePath.endsWith("/")) {
			System.out.println("路径配置有误!!!");
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		String yearStr = Integer.toString(year);
		String monthStr = Integer.toString(month);
		String dayStr = Integer.toString(day);
		String lastPath = yearStr + "/" + monthStr + "/" + dayStr + "/";
		String muluStr = filePath + lastPath;
		File filemulu = new File(muluStr);
		// 如果文件夹不存在则创建
		if (!filemulu.exists() && !filemulu.isDirectory()) {
			System.out.println("//目录不存在");
			filemulu.mkdirs();
		}
		filePath = muluStr + filename;
		String pdfContent = "";
		if (Constants.ISOCR == 1) {
			pdfContent = InterfaceClass.ocrPdfImg(filebyte, filePath);
			returnMesage = lastPath + filename+"!/>"+pdfContent;
		} else {
			File file = new File(filePath);
			if (!file.exists()) {
				FileOutputStream fileoutputstream = new FileOutputStream(file);
				fileoutputstream.write(filebyte);
				fileoutputstream.close();
			} else {
				System.out.println("文件已存在");
				return "";
			}
			returnMesage = lastPath + filename;
		}
		// saveHotelLocalFile(filename,filebyte);
		return returnMesage;
	}

	public static String saveScannerFile(String filename, byte[] filebyte)
			throws IOException {
		String filePath = getFileSavePath();
		if (!filePath.endsWith("/")) {
			System.out.println("路径配置有误!!!");
		}
		String lastPath = "scannerFile/";
		String muluStr = filePath + lastPath;
		File filemulu = new File(muluStr);
		// 如果文件夹不存在则创建
		if (!filemulu.exists() && !filemulu.isDirectory()) {
			System.out.println("//目录不存在");
			filemulu.mkdirs();
		}
		filePath = muluStr + filename;
		File file = new File(filePath);
		if (!file.exists()) {
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			fileoutputstream.write(filebyte);
			fileoutputstream.close();
		} else {
			System.out.println("文件已存在");
			return "";
		}
		// saveHotelLocalFile(filename,filebyte);
		return lastPath + filename;
	}

	public static void saveHotelLocalFile(String filename, byte[] filebyte)
			throws IOException {
		String filePath = Constants.getProperty("hotelFileLocSavePath");
		if (!filePath.endsWith("/")) {
			System.out.println("路径配置有误!!!");
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		String yearStr = Integer.toString(year);
		String monthStr = Integer.toString(month);
		String dayStr = Integer.toString(day);
		String lastPath = yearStr + "/" + monthStr + "/" + dayStr + "/";
		String muluStr = filePath + lastPath;
		File filemulu = new File(muluStr);
		// 如果文件夹不存在则创建
		if (!filemulu.exists() && !filemulu.isDirectory()) {
			System.out.println("//目录不存在");
			filemulu.mkdirs();
		}
		filePath = muluStr + filename;
		File file = new File(filePath);
		if (!file.exists()) {
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			fileoutputstream.write(filebyte);
			fileoutputstream.close();
		} else {
			System.out.println("文件已存在");
		}
	}

	public byte[] getHotelFileByte(String name) throws Exception {
		byte[] f = getFileByte(getFileSavePath() + name);
		return f;
	}

	public byte[] getFileByte(String filePath) throws FileNotFoundException {
		System.out.println(filePath);
		FileInputStream fileInputStream = new FileInputStream(filePath);
		return getFileByte(fileInputStream);
	}

	public byte[] getFileByte(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		try {
			copy(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

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

	public static void main(String[] args) throws Exception {
		HotelFileUtil hfutil = new HotelFileUtil();
		String path = "2015\\7\\6\\国内_admin_20157618816.pdf";
		byte[] b = hfutil.getHotelFileByte(path);
		System.out.println(b.length);
	}

}
