package com.dj.seal.util.socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerSocketListener implements ServletContextListener{
	
	private ServerThread socketThread;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if(socketThread != null){
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ServletContext serveContext = arg0.getServletContext();
		if(serveContext == null && socketThread == null){
//			socketThread = new ServerThread(client);
		}
	}

}
