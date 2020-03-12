package com.dj.seal.util.socket;

import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServerPool_composition implements ServletContextListener {
	static Logger logger = LogManager.getLogger(ServerPool_composition.class.getName());
	private ServerThread_composition st_c;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {// 销毁socket服务
		logger.info("Socket Destory");
		if (st_c != null && st_c.isInterrupted())
			st_c.closeServerSocket();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		if (st_c == null) {//没有线程则创建
			try {
				final int threadPoolSize = Integer.parseInt(context.getInitParameter("threadpoolSize_composition"));// 获取线程池大小
				final ServerSocket serverSocket = new ServerSocket(Integer.parseInt(context.getInitParameter("socketPort_composition")));
				for (int i = 0; i < threadPoolSize; i++) {	// 最多有threadPoolSize个线程在accept()方法上阻塞等待连接请求
					Thread thread = new Thread() {// 匿名内部类，当前线程为匿名线程，还没有为任何客户端连接提供服务
						@Override
						public void run() {
							while (true) {// 线程为某连接提供完服务后，循环等待其他的连接请求
								try {
									Socket accept = serverSocket.accept();// 等待客户端连接
									logger.info("与客户端连接成功");
									ServerThread_composition.composition(accept);// 响应客户端
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					};
					thread.start();// 开启所有线程
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
