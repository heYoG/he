package com.dj.seal.util.socket;
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;

import serv.MyOper;

/** 
 * 该类为多线程类，用于服务端 
 */  
public class ServerThread extends Thread{
	static Logger logger = LogManager.getLogger(ServerThread.class.getName());
	private ServerSocket serverSocket;
	private Socket client = null;  
	private ServletContext servlertContext;
    public ServerThread(ServletContext servletContext, Socket client){  
    	this.servlertContext = servletContext;
        this.client = client;  
    }  
       
    //处理通信细节的静态方法，这里主要是方便线程池服务器的调用  
    public static void execute(Socket client){  
        try{  
            //获取Socket的输出流，用来向客户端发送数据    
            PrintStream out = new PrintStream(client.getOutputStream());  
            //获取Socket的输入流，用来接收从客户端发送过来的数据  
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));  
            boolean flag =true;  
            while(flag){  
                //接收从客户端发送过来的数据    
                String str =  buf.readLine();  
                if(str == null || "".equals(str)){  
                    flag = false;  
                }else{  
                    if("bye".equals(str)){  
                        flag = false;  
                    }else{  
                        //将接收到的字符串前面加上echo，发送到对应的客户端    
                        out.println("echo:" + str);  
                    }  
                }  
            }  
            out.close();  
            buf.close();  
            client.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    } 
    
    public static void execute2(Socket socket){
    	BufferedReader br = null;
    	PrintWriter pw = null;
    	try {
			 br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 pw = new PrintWriter(socket.getOutputStream());
			char c[] = new char[1024*100*5];
			int xmlLength = 0;
			StringBuffer sb = new StringBuffer();
			String xmlData = "";
		    boolean flag = true;
			//获取报文信息
			while(flag){
				br.read(c);		
				sb.append(c);
				if(sb.toString().trim().length()>0){//新增判断 ,屏蔽空格符串 20180207
				xmlLength =Integer.parseInt(sb.substring(0,6));
				if(sb.length()>=xmlLength){
					xmlData = sb.substring(6, xmlLength);
					flag = false;
				}
				}else{//add 20180207
					flag=false;
				}
			}
			logger.info("请求报文："+sb.toString());
			if(sb.toString().trim().length()>6){//屏蔽空格符串 add 20180206
				logger.info("C_len:"+sb.toString().trim().length());
				xmlLength =Integer.parseInt(sb.substring(0,6));
				if(sb.length()>=xmlLength){
					xmlData = sb.substring(6, xmlLength);
				}
				xmlData=sb.substring(6);
				logger.info("xmlData:"+xmlData);
				String response = "";
				try {
					response = MyOper.haiGuanSealPdf(xmlData, getClientIp());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				//格式化数据,自动补零到6位数字
				//执行自定义请求解析方法，生成相应的response
				String newString = String.format("%06d",response.getBytes("UTF-8").length+6)+response; 
				logger.info("报文返回信息："+newString);
				pw.println(newString);				
			}else{//add 20180207
				System.out.println("传入报文为空!");
				pw.println("");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(pw != null){
				pw.flush();//刷新缓冲区
				pw.close();
			}
			if(br!= null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(socket.isConnected()){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
    }
    
    
    public void closeServerSocket(){
    	try{
    		if(serverSocket != null && !serverSocket.isClosed()){
    			serverSocket.close();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
 // 得到客户端ip
 	public static String getClientIp() {
 		MessageContext mc = MessageContext.getCurrentMessageContext();
 		if (mc == null) {
 			return null;
 		}
 		HttpServletRequest request = (HttpServletRequest) mc.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
 		String ip = request.getHeader("x-forwarded-for");
 		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("Proxy-Client-IP");
 		}
 		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getHeader("WL-Proxy-Client-IP");
 		}
 		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
 			ip = request.getRemoteAddr();
 		}
 		return ip;
 	}
    
    @Override  
    public void run() {  
        execute(client);  
    }  
}
