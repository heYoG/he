package com.dj.seal.formatFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 1.读取文件并重新排版 2.当前功能要求数据以冒号作为标识符分隔
 * 
 * @author WB000559
 * 
 */
public class FormatFile {
	public static void main(String[] args) {
		
		String filePath = "D:\\test\\D0587010200103752382.txt";
		File file1 = new File(filePath);
		/*字符流读*/
//		FileReader file2 = null;
		/*字符流写*/
		FileWriter w =null;
		/*缓冲流读*/
		BufferedReader buffer = null;
		/*缓冲流写*/
		BufferedWriter bw = null;
		/*存储重新排版数据*/
		StringBuffer sb = null;
		
		/*字节流读*/
		FileInputStream fis=null;
		InputStreamReader isr=null;
		
		try {
//			file2 = new FileReader(file1);
			w = new FileWriter(new File("D:\\test\\format2.txt"));
			bw=new BufferedWriter(w);
			
			/*字节流读*/
			fis=new FileInputStream(file1);
			isr=new InputStreamReader(fis,"GB2312");//指定编码读，否则写入乱码
			
			buffer = new BufferedReader(isr);
			String str = "";// 字符流读
			sb = new StringBuffer();// 缓冲流
		
			while ((str = buffer.readLine()) != null) {
				if (str.contains(":") || str.contains("：")) {// 非空白行正文
					String trimStr = str.trim();
					sb.append("\t\t");//每行前加空格
					// 直接按空格拆分,自动去除空格并保留结果(前提是必须保证每项数据间不含空格,否则得到错误数据)
					String[] split1 = trimStr.split(" ");
					for (int i = 0; i < split1.length; i++) {
						if(split1[i].contains("\t")) {//含制表符再次按制表符拆分
							String[] split2 = split1[i].split("\t");
							for(int j=0;j<split2.length;j++){			
								if(!split2[j].equals(""))
									sb.append(split2[j]+"\t\t");//含制表符数据
							}
						}else{//else必须，否则含制表符的数据会重复追加到缓冲流中
							if(!split1[i].equals(""))
								sb.append(split1[i]+"\t\t");//不含制表符数据														
						}
					}
					sb.append("\n");//每行正文数据追加完后换行
				}else{//标题和空白行保留原格式,追加完后换行
					sb.append(str+"\n");
				}
			}
			bw.write(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				w.close();
				buffer.close();
//				file2.close();	
				isr.close();
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("转换完成...");
	}
}
