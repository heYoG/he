package com.dj.seal.util.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dj.seal.sysmgr.service.impl.BackupService;

/**
 * 在系统启动时，检测定时任务是否开启
 * 
 * @author lzl
 * 
 */
public class CheckTaskListener implements ServletContextListener,
		ServletContextAttributeListener, HttpSessionListener,
		HttpSessionAttributeListener, HttpSessionActivationListener,
		HttpSessionBindingListener, ServletRequestListener,
		ServletRequestAttributeListener {
	static Logger logger = LogManager.getLogger(CheckTaskListener.class.getName());
	private static ApplicationContext ctx = null;

	public CheckTaskListener() {
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent evt) {
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt
				.getServletContext());
		BackupService backupService = (BackupService) ctx.getBean("IBackupService");
		backupService.checkTask();
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent arg0) {
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent evt) {
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void attributeAdded(ServletRequestAttributeEvent evt) {
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent arg0) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
	}

}
