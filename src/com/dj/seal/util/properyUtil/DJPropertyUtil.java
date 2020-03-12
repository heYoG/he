package com.dj.seal.util.properyUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 读写属性文件工具类
 * @author wj
 *
 */
public class DJPropertyUtil {
	static Logger logger = LogManager.getLogger(DJPropertyUtil.class.getName());
	private static Properties pro;
	//private static URL filepath1=DJPropertyUtil.class.getResourceAsStream("/config.properties");
	private static URL filepath=DJPropertyUtil.class.getClassLoader().getResource("/config.properties");
	static{
//		logger.info("filepath1::"+filepath1.getFile());
//		logger.info("filepath::"+filepath.getFile());
		if(pro==null){
			pro = new Properties();//属性集合对象 
			InputStream proIs;
			try {
//				proIs = new FileInputStream(new File(filepath.toURI()));
				proIs = DJPropertyUtil.class.getResourceAsStream("/config.properties");
				Reader reader=new InputStreamReader(proIs,"utf-8");
				pro.load(reader);
				proIs.close();
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			} 
		}
	}
	/**
	 * 读取属性值
	 * @param propetyName 属性名
	 * @return 属性值，如果不存在，返回null
	 */
	public static String getPropertyValue(String propetyName){
		return pro.getProperty(propetyName);
	}
	/**
	 * 读取属性值
	 * @param propetyName 属性名
	 * @param defaultValue 默认值
	 * @return 属性值，如果不存在，返回默认值
	 */
	public static String getPropertyValue(String propetyName,String defaultValue){
		return pro.getProperty(propetyName,defaultValue);
	}
	/**
	 * 添加或修改属性值，如果属性名存在 则修改，不存在，则新增。
	 * @param propetyName 属性名
	 * @param propetyValue 属性值
	 */
	public static void setPropery(String propetyName,String propetyValue){
		pro.setProperty(propetyName, propetyValue);
	}
	/**
	 * 保存修改到文件
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void saveToFile() throws IOException, URISyntaxException{
		OutputStream os=new FileOutputStream(new File(filepath.toURI()));
		java.io.Writer writer=new OutputStreamWriter(os,"utf-8");//如果不以带字符集的writer写会默认变成/ueft/oiuy这种书写格式。
		pro.store(writer, "");
		os.close();
	}
	public static Properties getPro() {
		return pro;
	}
	public static void setPro(Properties pro) {
		DJPropertyUtil.pro = pro;
	}
	public static URL getFilepath() {
		return filepath;
	}
	public static void setFilepath(URL filepath) {
		DJPropertyUtil.filepath = filepath;
	}
	
	
	
}
