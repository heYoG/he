package srvSeal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dj.seal.util.Constants;

public class SrvSealUtil {
	static Logger logger = LogManager.getLogger(SrvSealUtil.class.getName());
	private static String soname = "AutoServerSeal";
	private static String os;// 操作系统名称
	private static SrvSealUtil obj;
	static {
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {//Windows系统
			init();// 写入DLL
			os = "windows";
			soname = "AutoServerSeal";
		} else {// 如果是linux环境
			initLinux();// 写入so
			os = "";
			soname = "autoseal";
		}
		System.loadLibrary(soname);
		obj = new SrvSealUtil();// 实例化单例对象
	}
	/**
	 * 写入DLL到java的 bin目录下
	 */
	static void init() {
		// 得到类路径
		String path = System.getProperty("java.library.path");
		String is_type=Constants.getProperty("is_type");
		if(is_type.equals("1")){//从配置文件读取路径
			path=Constants.getProperty("jdk_path");
		}
		logger.info("path：" + path);
		// 得到第一个路径
		String dir = path.substring(0, path.indexOf(";"));
		logger.info("dll存放路径：" + dir);//dll存放路径
		// 得到dll的名称
		File dll = new File(dir + "/" + soname + ".dll");
		// 如果dll不存在则写入，如果存在则跳过

		 if (!dll.exists()) {
		 logger.info("不存在，覆盖");
		try {
			InputStream stream = new SrvSealUtil().getClass()
					.getResourceAsStream("/" + soname + ".dll");
			if (stream != null) {
				FileOutputStream fos = new FileOutputStream(dll);
				byte[] b = new byte[1000];
				int n;
				while ((n = stream.read(b)) != -1)
					fos.write(b, 0, n);
				stream.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());

		}
		 }
	}

		/**
		 * 写入so到java的 bin目录下
		 */
		static void initLinux() {
			// 得到类路径
			String path = System.getProperty("java.library.path");
			logger.info("java.library.path:"+path);
//			String dir = null;
//			if (path.indexOf(":") != -1) {
//				// 得到第一个路径
//				dir = path.substring(0, path.indexOf(":"));
//			} else {
//				dir = path;
//			}
//			logger.info("dir：" + dir);
//			//dir = "/usr/lib";
//			logger.info("so存放路径：" + dir);
//			// 得到so的名称
//			File so = new File(dir + "/" + soname + ".so");
//			// 如果so不存在则写入，如果存在则跳过
//
//			// if (!so.exists()) {
//			// logger.info("不存在，覆盖");
//			try {
//				InputStream stream = new SrvSealUtil().getClass()
//						.getResourceAsStream("/" + soname + ".so");
//				if (stream != null) {
//					FileOutputStream fos = new FileOutputStream(so);
//					byte[] b = new byte[1000];
//					int n;
//					while ((n = stream.read(b)) != -1)
//						fos.write(b, 0, n);
//					stream.close();
//					fos.close();
//				}
//			} catch (FileNotFoundException e) {
//				logger.error(e.getMessage());
//			} catch (IOException e) {
//				logger.error(e.getMessage());
//			}
	}
		
		/**
		 * 打开一个office文件
		 * 
		 * @param type  0:word; 1:excel
		 * @return <0错误 nOfficeID
		 */
		public native int openOffice(int type);
		/**
		 * office转换为pdf
		 * 
		 * @param nOfficeID   打开的office文件ID
		 * @param pcInFile    
		 * @param pcOutFile
		 * @return <0错误
		 */
		public native int officeToPdf(int nOfficeID,String pcInFile,String pcOutFile);
		
		/**
		 * 关闭打开的office文件
		 * 
		 * @param nOfficeID
		 * @return
		 */
		public native int closeOffice(int nOfficeID);
		
		
	/**
	 * 打开对象
	 * 
	 * @param openPath
	 *            打开路径，可为""
	 * @param nFS1
	 *            ForceSignType参照SDK
	 * @param nFS2
	 *            ForceSignType2参照SDK
	 * @return 对象ID <=0为失败，>0为成功
	 */
	public native int openObj(String openPath, int nFS1, int nFS2);

	/**
	 * 插入节点
	 * 
	 * @param nObjID
	 *            文档id
	 * @param nodeValue
	 *            节点值
	 * @param tagType
	 *            节点类型
	 * @param tagName
	 *            节点名称
	 * @return 1为成功，0为失败
	 */
	public native int insertNode(int nObjID, String nodeValue, int tagType,String tagName);
	public native int PrintDoc(int nObjID, String printName);
	/**
	 * getValue值
	 * 
	 * @param nObjID
	 * @return 
	 */
	public native String getValue(int nObjID, String name); 

	/**
	 * 设置属性
	 * 
	 * @param nObjID
	 *            文档id
	 * @param name
	 *            属性名 
	 *            //SET_SEAL_BORDER_TEXT 设置印章边框文字 
	 *            //SET_GRAYDATA_STATE	这个值传31即可 
	 *            //ADD_FORCETYPE_VALUE 
	 *            //ADD_FORCETYPE_VALUE2
	 *            //ADD_FORCETYPE_VALUE3 
	 *            //DEL_FORCETYPE_VALUE
	 *            //DEL_FORCETYPE_VALUE2 
	 *            //DEL_FORCETYPE_VALUE3
	 *            //0x400,盖上印章就雾化,需要转换成十进制数1024
	 * @param value
	 *            属性值
	 * @return
	 */
	public native int setValue(int nObjID, String name, String value);
	/**
	 * 登录
	 * 
	 * @param nObjID
	 *            对象ID，可由openObj方法获取
	 * @param nLoginType
	 *            登录类型
	 * @param userID
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 0为成功，1为失败
	 */
	public native int login(int nObjID, int nLoginType, String userID,
			String pwd);
	
	/**
	 * 登录linux
	 * 
	 * @param nObjID
	 *            对象ID，可由openObj方法获取
	 * @param nLoginType
	 *            登录类型
	 * @param userID
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 0为成功，1为失败
	 */
	public native int login(int nObjID, String userID, int nLoginType,
			String pwd);

	/**
	 * 数据模板合成、文档追加合成
	 * 
	 * @param nObjID
	 *            对象ID，可由openObj方法获取
	 * @param tempPath
	 *            模板路径或文档路径
	 * @param docData
	 *            “STRDATA:”+业务数据或“”
	 * @return 1为成功，0为失败
	 */
	public native int addPage(int nObjID, String tempPath, String docData);

	/**
	 * 加盖印章
	 * 
	 * @param nObjID
	 *            对象ID，可由openObj方法获取
	 * @param pagesData
	 *            盖章位置及印章数据
	 * @param oriSealName
	 *            pfx证书路径或"SERVER_CERT:"+签名服务器证书名
	 * @param sealName
	 *            印章名
	 *            随机命名:"AUTO_ADD_SEAL_FROM_PATH"或指定命名："AUTO_ADD_SEAL_FROM_PATH:印章名称"
	 * @return 1为成功，0为失败
	 */
	public native int addSeal(int nObjID, String pagesData, String oriSealName,
			String sealName);
	
	/**
	 * 返回对象页数
	 * 
	 * @param nObjID
	 *            对象ID
	 * @return 页数
	 */
	public native int getPageCount(int nObjID);

	/**
	 * 文档保存
	 * 
	 * @param nObjID
	 *            对象ID
	 * @param savePath
	 *            文档保存路径
	 * @param saveType
	 *            文档保存类型
	 * @return 1为成功，0为失败
	 */
	public native int saveFile(int nObjID, String savePath, String saveType);
	public native int saveFile(int nObjID, String savePath, String saveType,int keepObj);
	//设置ocx路径
	public native int setCtrlPath(String ctrlPath);

	/**
	 * 打开对象
	 * 
	 * @param openPath
	 *            打开路径，可为""
	 * @param nFS1
	 *            ForceSignType参照SDK
	 * @return 对象ID <=0为失败，>0为成功
	 */
	public native int openObj(String openPath, int nFS1);
	public native int verifyLic(String licCode, String licFile);
	public native int openData(byte[] pdfData);
	//设置印章标识符
	public native int setDocProperty(int nObjID, String name, String value);
	public native String encFile(int nObjID, String pcInFile, String pcOutFile);
	//public native int officeToPdf(int nOfficeIDString pcInFile , String pcOutFile);
	/**
	 * 文档保存
	 * 
	 * @param nObjID
	 *            对象ID
	 * @param savePath
	 *            文档保存路径
	 * @return 1为成功，0为失败
	 */
	public native int saveFile(int nObjID, String savePath);
	/**
	 * 文档印章验证
	 * 
	 * @param nObjID
	 * @return <+NodeName=%s/;NodeID=%s/;CertSerial=%s/;CertSubject=%s/;CertIssuer=%s/;RetCode=%d/;->
	 */
	public native String verify(int nObjID);
	/**
	 * 获取PDF文档数据
	 * 
	 * @param nObjID
	 * @return 
	 */
	public native byte[] getData(int nObjID);
	/**
	 * 获取待签名数据的SHA1
	 * 
	 * @param nObjID
	 * @return 
	 */
	public native byte[] getSignSHAData(int nObjID);
	/**
	 * 获取p7在PDF文档数据中的预留起始位置
	 * 
	 * @param nObjID
	 * @return 
	 */
	public native int getSignPos(int nObjID);
	/**
	 * 插入图片
	 * 
	 * @param nObjID
	 * @return 
	 */
	public native int insertPicture(int nObjID,String picture,int nPageIndex,int nLeft,int nTop,int nZoom);
	/**
	 * 得到文件的类型
	 * 
	 * @param strFile
	 * @return 0为未知文件，31为pdf，52为txt或xml,11为doc，128为aip
	 */
	public native int getDocType(String strFile);

	/**
	 * 打印文件
	 * 
	 * @param nObjID
	 * @param printName
	 * @param docName
	 * @param prnCopys
	 *            打印页数
	 * @return
	 */
	public native int PrintDoc(int nObjID, String printName, String docName,
			int prnCopys);

	/**
	 * 得到打印机列表
	 * 
	 * @return
	 */
	public native String getPrinterList();

	/**
	 * 根据打印机名称得到打印机状态信息
	 * 
	 * @param printName
	 * @return
	 */
	public native String getPrinterStatusByStr(String printName);

	/**
	 * 根据打印机名称和jobID得到job状态
	 * 
	 * @param printerName
	 * @param nJobID
	 * @return
	 */
	public native String getJobInfoByStr(String printerName, int nJobID);

	/**
	 * 重置打印机
	 * 
	 * @param printerName
	 * @return
	 */
	public native int resetPrinterByStr(String printerName);

	/**
	 * 根据印章数据得到图片数据
	 * @param sealValue 为路径或者以STRDATA:打头的base64
	 * @return 
	 */
	public native byte[] getSealBmpData(String sealValue);
	
	/**
	 * 设置印章图片信息
	 * @param nObjID
	 * @param sealBmpValue 为路径或者以STRDATA:打头的base64
	 * @return
	 */
	public native int setSealBmpData(int nObjID, String sealBmpValue);
	
	/**
	 * 设置印章图片信息
	 * @param nObjID
	 * @param sealBmpValue 为路径或者以STRDATA:打头的base64,sealZoom为缩放比例，默认为0是按照图片大小定义
	 * @return
	 */
	public native int setSealBmpData(int nObjID, String sealBmpValue,int sealZoom);
	//获取下一个印章
	public native String getNextSeal(int nObjID,String sealID); 
	//获取印章位置
	public native int getSealPos(int nObjID,String sealID); 
	//得到p7签名
	public native byte[] getSealP7(int nObjID,String sealID); 
	//得到签名数据
	public native byte[] getSealSignSHAData(int nObjID,String sealID);
	
	public native int openTempData(byte[] auoData);
	
	public static String change(String str) {
		String[] strs = str.split("/");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append("\\");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}
	
	/**
	 * 将PDF转为图片
	 * @param nJobID 文档ID
	 * @param pageNo 页码
	 * @param pageW 图片宽度
	 * @param imgPath 图片输出路径
	 * @param imgType 图片类型
	 * @return
	 */
	public native int getPageImg(int nJobID, int pageNo, int pageW, String imgPath, String imgType);

	/**
	 * 
	 * @param nObjID 打开文件标识
	 * @param inPath STRDATA:打头的印章base64数据
	 * @param pwd 
	 * @param outBmpPath 图片输出路径
	 * @param bModified 修改标识,0:不画
	 * @return
	 */
	public native String decSealEx(int nObjID,String inPath,String pwd,String outBmpPath,int bModified);
	
	public static void main(String[] args) {
//		String str = change("D:/tomcat\\apache-tomcat-6.0.18\\webapps\\Seal\\upload\\eee.aip");
//		logger.info(str);
		String filePath="d:"+File.separatorChar+"test.doc";
		//File file = new File(filePath);
		//InputStream in = new FileInputStream(file);
		SrvSealUtil srv_seal=new SrvSealUtil();
//		String v = s1.verify(nObjID);
//		logger.info("v:" + v);
//		String pcInFile = "d://123.xlsx";
//		String pcOutFile = "d://test.pdf";
//		int result = s1.officeToPdf(nObjID,pcInFile, pcOutFile);
		
	}
}
