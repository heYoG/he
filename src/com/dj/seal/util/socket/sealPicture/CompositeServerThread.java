package com.dj.seal.util.socket.sealPicture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CompositeServerThread extends Thread {
	static Logger logger=LogManager.getLogger(CompositeServerThread.class.getName());
	
	public static void compositeThread(Socket socket){
		BufferedReader br=null;//缓冲流
		PrintWriter pw=null;//输出
		String xmlData=null;//客户端请求报文
		String response=null;//响应报文
		String newResponse=null;//含长度头响应报文
		
		try {
			br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));//设置编码格式确保本地不乱码
			pw=new PrintWriter(socket.getOutputStream());
			char []c1=new char[8];
			br.read(c1, 0, 8);//读取ESB报文长度头并保存到c1数组
			if(new String(c1).contains("test")){
				logger.info("F5探测报文:"+new String(c1));
				pw.print("1");//响应
			}else{
				int xmlLength=Integer.parseInt(new String(c1));
				char []c2=new char[xmlLength];//读取xml报文
				br.read(c2, 0, xmlLength);
				xmlData=new String(c1)+new String(c2);
				logger.info("客户端完整请求报文:\n"+xmlData);
				logger.info("C_Length:"+xmlData.length());
				response = CompositeEntrance.ComEntrance(new String(c2).trim());
				newResponse=String.format("%08d",response.length())+response;
				if(response.length()<3000)//异常或交易失败时输出信息(成功报文含大字段)
					logger.info("服务端响应报文:"+newResponse);
				logger.info("S_Length:"+response.length());
				pw.print(newResponse);//响应
			}				
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null)
				pw.close();
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(socket.isConnected())
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
