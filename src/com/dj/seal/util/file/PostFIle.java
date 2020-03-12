package com.dj.seal.util.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.Constants;
public class  PostFIle{
    static Logger logger = LogManager.getLogger(PostFIle.class.getName());
	private static String bpath() {
		String bpath = Constants.basePath;	
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}
	
	 public byte[] getByte(String name)throws Exception {
		 String bpath = bpath();
		// logger.info(bpath + "sealdoc/"+name);
		 byte[] f = new FileUtil().getFileByte(bpath + "sealdoc/"+name);
		 return f;
	 }
	 public byte[] getSealByte(String name)throws Exception {
		 String bpath = bpath();
		 //logger.info(bpath + "bakSeal/"+name);
		 byte[] f = new FileUtil().getFileByte(bpath + "bakSeal/"+name);
		 deletefile(bpath + "bakSeal/",name);
		 return f;
	 }
	 public static boolean deletefile(String delpath, String file_name)
	   throws Exception {
		File file = new File(delpath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(delpath + "\\" + filelist[i]);
				String filname = filelist[i];
				//logger.info("file_name:" + file_name);
				//logger.info("filname:" + filname);
				if (filname.equals(file_name)) {
					delfile.delete();
				}
			}
		}
    return true;
   }
	 public byte[] getMakeByte(String name)throws Exception {
		 String bpath = bpath();
		 logger.info(bpath + "docs/"+name);
		 byte[] f = new FileUtil().getFileByte(bpath + "docs/"+name);
		 return f;
	 }
	 public byte[] getprintByte(String name)throws Exception {
		 String bpath = bpath();
		 logger.info(bpath + "printsealDocs/"+name);
		 byte[] f = new FileUtil().getFileByte(bpath + "printsealDocs/"+name);
		 return f;
	 }
}
class FileUtil {
	static Logger logger = LogManager.getLogger(FileUtil.class.getName());
	 public byte[] getFileByte(String filePath) throws FileNotFoundException {
	  FileInputStream fileInputStream = new FileInputStream(filePath);
	  return getFileByte(fileInputStream);
	 }

	 public byte[] getFileByte(URL url) throws IOException {
	  if (url != null) {
	   return getFileByte(url.openStream());
	  } else {
	   return null;
	  }
	 }

	 public byte[] getFileByte(InputStream in) {
	  ByteArrayOutputStream out = new ByteArrayOutputStream();
	  //ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
	  try {
	   copy(in, out);
	  } catch (IOException e) {
		  logger.error(e.getMessage());
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
	}