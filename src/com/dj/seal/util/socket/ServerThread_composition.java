package com.dj.seal.util.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dj.seal.util.socket.socketImpl.Compostion_res;
import com.dj.seal.util.socket.socketImpl.GetTradeData_res;
import com.dj.seal.util.socket.socketImpl.PrintSeal_res;

public class ServerThread_composition extends Thread {
	static Logger logger = LogManager.getLogger(ServerThread_composition.class
			.getName());
	private ServerSocket serverSocket;
	private Socket client = null;
	private ServletContext servlertContext;

	public ServerThread_composition(Socket client, ServletContext servlertContext) {
		super();
		this.client = client;
		this.servlertContext = servlertContext;
	}

	public static void composition(Socket socket) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			// 获取客户端请求信息(编码设置于20191125解决本地插入数据库中文乱码,未投)
						br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			pw = new PrintWriter(socket.getOutputStream());// 对客户端响应
			StringBuffer sb = new StringBuffer();
			String xmlData = "";
			String str="";
			boolean flag = true;
			// 获取报文信息
			while (flag) {
				// 接收从客户端发 送过来的数据
				str = br.readLine();
				if (str == null || "".equals(str)) {
					flag = false;
				} else {
					sb.append(str);
				}
			}
			logger.info("客户端完整请求报文：" + sb.toString());
			if(sb.length()<9){//屏蔽F5探测异常，探测报文为长度6的空字符串
				logger.info("F探测报文:"+sb);//还原打印、查询接口报文接收返回处理方式并添加此输出语句 20181023
				pw.println("");
			}else{
				xmlData = sb.toString().substring(8);//去除报文头部分
				logger.info("C_length:" + sb.length());
				String response = "";
				response = Compostion_res.compostion_Response(xmlData);// 执行自定义请求解析方法，生成相应的response
				String newString = String.format("%08d",response.getBytes("UTF-8").length) + response;//报文头补充8位长度头
				logger.info("返回客户端报文(含长度头)：" + newString);
				logger.info("S_length:" + newString.length());
				pw.println(newString);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (pw != null) {
				pw.flush();// 刷新缓冲区
				pw.close();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket.isConnected()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void closeServerSocket() {
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

	}
}
