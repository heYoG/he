package com.dj.seal.util.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 该类实现基于线程池的服务器
 */
public class serverPool implements ServletContextListener {
	static Logger logger = LogManager.getLogger(serverPool.class.getName());
	private ServerThread socketThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("Socket Destroy");
		if (socketThread != null && socketThread.isInterrupted()) {
			socketThread.closeServerSocket();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		final ServletContext serveContext = arg0.getServletContext();
		if (socketThread == null) {
			try {
				final int threadpoolSize = Integer.parseInt(serveContext
						.getInitParameter("threadpoolSize"));
				final ServerSocket server = new ServerSocket(
						Integer.parseInt(serveContext
								.getInitParameter("socketPort")));
				System.out.println("threadpoolSize:" + threadpoolSize);
				// 在线程池中一共只有THREADPOOLSIZE个线程，
				// 最多有THREADPOOLSIZE个线程在accept()方法上阻塞等待连接请求
				for (int i = 0; i < threadpoolSize; i++) {
					// 匿名内部类，当前线程为匿名线程，还没有为任何客户端连接提供服务
					Thread thread = new Thread() {
						@Override
						public void run() {
							// 线程为某连接提供完服务后，循环等待其他的连接请求
							while (true) {
								try {
									// 等待客户端的连接
									Socket client = server.accept();
									logger.info("与客户端连接成功！");
									// 一旦连接成功，则在该线程中与客户端通信
									ServerThread.execute2(client);
									// new
									// ProcessSocketData(client,serveContext).start();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					};
					// 先将所有的线程开启
					thread.start();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
