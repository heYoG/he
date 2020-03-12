package com.dj.seal.util.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket {
	
	/**
	 * 获取socket客户端连接
	 * @param address
	 * @param port
	 * @return
	 */
	public static Socket getSocket(String address,int port){
		Socket client = null;
		try {
			
			client = new Socket(address, port);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	/**
	 * 发送消息到建立好的socket
	 * @param client
	 * @param xmlData
	 */
	public static void seanMesage(Socket client,String xmlData){
		try {
			PrintWriter os=new PrintWriter(client.getOutputStream());
			System.out.println(xmlData);
			 //发送消息  
			os.print(xmlData);  
			os.flush(); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 从服务器端接收返回的信息
	 * @param client
	 * @return
	 */
	public static String receiveMesage(Socket client){
		InputStream in = null;
		StringBuilder sb = null; 
		try {
			in = client.getInputStream();
			byte[] b = new byte[1024];//开1key的内存
			sb = new StringBuilder();
			int i = 0;
			while((i = in.read(b))!=-1){
				String mesage = new String(b);
//				int messageLength = Integer.parseInt(mesage.substring(0, 6));
				sb.append(mesage);
//				System.out.println(messageLength+":::"+sb.toString().getBytes().length);
//				if(messageLength == sb.toString().getBytes().length){
//					in.close();
//					client.close();
//					break;
//				}
				int indexQ = sb.toString().indexOf("</msg>");
				if(indexQ > 0){
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				client.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return sb.toString();
	}

}
