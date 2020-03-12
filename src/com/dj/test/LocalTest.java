package com.dj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import cn.com.jit.assp.client.util.StringUtils;

@SuppressWarnings("all")
public class LocalTest {
	public static void main(String[] args) throws Exception{
		File filePath=new File("E:\\test\\xml.txt");
		StringBuffer sb=new StringBuffer();
		FileReader fr=new FileReader(filePath);
		BufferedReader br=new BufferedReader(fr);
		String str;
		while((str=br.readLine())!=null){
			sb.append(str);
		}
		System.out.println("readData:\n"+sb+"\n");
		Document doc =DocumentHelper.parseText(sb.toString());
		Element element = doc.getRootElement();
		boolean b = getNodes(element);
		System.out.println("returnValue:"+b);
		
	}
	
	
	public static boolean getNodes(Element element) throws Exception{
		boolean flag = false;
		try {
			System.out.println("name:" + element.getName() + ",value:"+ element.getText());
			List<Element> list2 = element.elements();
			System.out.println("size:"+list2.size());
			for (Element e : list2) {
				getNodes(e);
//				System.out.println("name2:" + element.getName() + ",value2:"+ element.getText());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
