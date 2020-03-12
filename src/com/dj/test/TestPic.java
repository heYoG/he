package com.dj.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.plugins.bmp.BMPImageWriteParam;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.bmp.BMPConstants;

public class TestPic {
	public static void main(String[] args) throws IOException {
		/*缩放图片比例*/
		String sealPic="C:\\sealPic.jpg";//原图
		String outPic="C:\\sealPic2.jpg";
		FileInputStream fis=new FileInputStream(new File(sealPic));
		Image srcImg=ImageIO.read(fis);
//		int width=200;//宽的缩放像素点
//		int height=srcImg.getHeight(null)*width/srcImg.getWidth(null);//高的缩放像素点
		int width=srcImg.getWidth(null);
		int height=srcImg.getHeight(null);
		System.out.println("Width:"+srcImg.getWidth(null)+"\nHeight:"+srcImg.getHeight(null));
		System.out.println("Width2:"+width+"\nHeigth2:"+height);
		Image smallImg=srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);//缩放
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(smallImg, 0, 0, null);
		File destFile=new File(outPic);
		FileOutputStream fous=new FileOutputStream(destFile);
		JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(fous);
		encoder.encode(image);
		fous.close();
		fis.close();
	}
}
