package com.dj.seal.util.socket.sealPicture;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CompositeServerPool implements ServletContextListener {
	static Logger logger=LogManager.getLogger(CompositeServerPool.class.getName());
	private ServerSocket sc;
	private CompositeServerThread cst;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {//销毁socket
		logger.info("destroy socket");
		if(!sc.isClosed()&&sc!=null){
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		if(cst==null){//没有线程
			try {
			int port =Integer.parseInt(servletContext.getInitParameter("socketPort_com"));
			int pool =Integer.parseInt(servletContext.getInitParameter("threadPool_com"));
				final ServerSocket serverSocket = new ServerSocket(port);//匿名内部类引用外部类变量要求是final类型
				for(int i=0;i<pool;i++){//开启定义的所有线程
					Thread thread=new Thread(){//匿名内部类(继承Thread,类引用对象向上转型)
						@Override
						public void run(){
							while(true){
								try {
									Socket socket = serverSocket.accept();
									logger.info("连接服务端成功");
									CompositeServerThread.compositeThread(socket);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					};
					thread.start();//开启线程
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
