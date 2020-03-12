package com.dj.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.Filer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import serv.MyOper;

import com.dj.seal.util.encrypt.Base64;
import com.dj.seal.util.encrypt.MD5Helper;
import com.dj.seal.util.encrypt.MD5Util;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.tools.javac.code.Attribute.Array;






public class TT {
	
	
	static Logger logger1=LogManager.getLogger(TT.class.getName());
	boolean checkedOut=false;
	
	public TT(){
		
	}
	
	TT(boolean checkOut){
		checkedOut=checkOut;
	}
	void checkIn(){
		checkedOut=false;
	}
	
	@Override
	protected void finalize(){//gc无法释放所有内存时调用
		if(checkedOut)
			System.out.println("Error:checked out");
	}
	
	public static void main(String[] args) throws Exception {
		/*check gc recycle resource*/
//		TT chk=new TT(true);
//		chk.checkIn();
//		new TT(true);
//		System.gc();
		
		/*base64ToPicture*/
//		File file=new File("C:\\sealData.txt");
//		String fileOutPath="C:\\sealPic.png";	
//		StringBuffer sb=new StringBuffer();
//		String encData="";
//		try {
//			FileReader fr=new FileReader(file);
//			BufferedReader br=new BufferedReader(fr);
//			while((encData=br.readLine())!=null){
//				sb.append(encData);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		dec(fileOutPath,sb.toString());
		
		/*convert picture*/
		String filePath="C:\\sealPic.jpg";
		convert(filePath);
		
		/*test log4j2*/
//		logger1.trace("我是trace信息");
//		logger1.debug("我是debug信息");
//		logger1.info("我是info信息");
//		logger1.warn("我是warn信息");
//		logger1.error("我是error信息");
//		logger1.fatal("我是fatal信息");
		
		
//		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println("val:"+sf.format(Timestamp.valueOf("")));
		
		Scanner sc=new Scanner(System.in);
		String flag=null;
		System.out.println("input a:");
		int a=sc.nextInt();
		System.out.println("input b:");
		int b=sc.nextInt();
		if(a>b)
			flag="";
		
		if(flag!=null){
			if(!flag.equals(""))
				System.out.println("1");
		}
		else
		System.out.println("2");
		
		
		
	
	}
	
	/**
	 *判断文件中是否存在某个机构号
	 * @param organazitionNo	 机构号
	 * @param resourcePath		文件路径
	 * @return
	 */
	public  static boolean isExist(String organazitionNo,String resourcePath){
		File file=new File(resourcePath);
		boolean flag=true;
		try {
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			StringBuffer  sb=new StringBuffer();
			String str="";
			while((str=br.readLine())!=null)
				sb.append(str);
			String[] split = sb.toString().split(",");
			@SuppressWarnings("rawtypes")
			List list = Arrays.asList(split);
			if(!list.contains(organazitionNo))
				flag=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static String enc(String filePath){//base64编码
		try {
			File file=new File(filePath);
			FileInputStream fis= new FileInputStream(file);
			byte[] data=new byte[fis.available()];
			fis.read(data);
			fis.close();
			return Base64.encodeToString(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void dec(String filePath,String imgStr){//base64解码
		byte[] decode = com.dj.seal.util.encrypt.Base64.decode(imgStr);
		for(int i=0;i<decode.length;i++){
			if(decode[i]<0){//调整异常数据
				decode[i]+=255;
			}
		}
		/*生成图片*/
		try {
			FileOutputStream out=new FileOutputStream(filePath);
			out.write(decode);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片白色部分变为透明
	 * @param path
	 */
	public static void convert(String path){
		try {
			BufferedImage image=ImageIO.read(new File(path));
			ImageIcon imageIcon=new ImageIcon(image);
			BufferedImage bImage=new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2D=(Graphics2D) bImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
			int alpha=0;
			for(int j1=bImage.getMinY();j1<bImage.getHeight();j1++){
				for(int j2=bImage.getMinX();j2<bImage.getWidth();j2++){
					int rgb=bImage.getRGB(j2, j1);
					if(colorInRange(rgb))
						alpha=0;
					else
						alpha=255;
					rgb=(alpha<<24)|(alpha&0x00ffffff);
					bImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bImage, 0, 0, imageIcon.getImageObserver());
			String outFile=path.substring(0, path.lastIndexOf("."))+"Convert";//转换后名称
			ImageIO.write(bImage, "png", new File(outFile+".png"));//生成png图片
			System.out.println("convert ending");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean colorInRange(int color){
		int red=(color&0xff0000)>>16;
		int green=(color&0x00ff00)>>8;
		int blue=(color&0x0000ff);
		if(red>=color_range&&green>=color_range&&blue>=color_range)
			return true;
		return false;
	}
	
	public static int color_range=210;
	public static Pattern pattern=Pattern.compile("[0-9]*");
	public static boolean isNo(String str){
		return pattern.matcher(str).matches();
	}

}
