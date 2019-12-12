package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;


/**
 * 文件的上传下载
 * @author Administrator
 *
 */
public class UploadAndDownloadUtil {
	/**
	 * 上传
	 * 只能上传可识别文件类型
	 * @param sourceFile
	 *            源文件路径
	 * @param fileName
	 *            源文件名称
	 * @return 保存文件路径
	 */
	public static String uploadFile(File sourceFile, String fileName) {
		fileName = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
		String destPath2 = "F:\\upload";
		String destFile = destPath2 + File.separatorChar + fileName;
		System.out.println("上传文件保存路径:" + destFile);
		File destFile2 = new File(destFile);
		try {
			FileUtils.copyFile(sourceFile, destFile2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile2.getAbsolutePath();// 返回文件保存的绝对路径,用于下载
	}

	/**
	 * 下载
	 * @param sourcePath	要下载文件
	 * @param request		request对象
	 * @param response		response对象
	 */
	public static void downloadFile(String sourcePath, HttpServletRequest request, HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(sourcePath));
			bos = new BufferedOutputStream(response.getOutputStream());
			String fileName = sourcePath.substring(sourcePath.lastIndexOf("\\") + 1);
			/**
			 *  这个就是弹出下载对话框的关键代码
			 *  Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件,
			 *  接收到头时，它会激活文件下载对话框,attachment(附件)必不可少
			 */
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));// 弹出下载框
			byte[] downloadData = new byte[1024 * 100];
			int r = 0;
			while ((r = bis.read(downloadData, 0, downloadData.length)) != -1) {
				bos.write(downloadData, 0, r);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {// bos
			e.printStackTrace();
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
