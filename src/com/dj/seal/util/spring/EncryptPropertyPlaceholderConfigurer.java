package com.dj.seal.util.spring;

import java.security.Security;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.dj.seal.util.encrypt.ThreeDESUtils;

public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	static Logger logger = LogManager.getLogger(EncryptPropertyPlaceholderConfigurer.class.getName());
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		try {
			String password = props.getProperty("jdbc.password");
			if (password != null) {
				Security.addProvider(new com.sun.crypto.provider.SunJCE());
				props.setProperty(
						"jdbc.password",
						new String(ThreeDESUtils.decryptMode(ThreeDESUtils.HexString2Bytes(password))));
			}
			super.processProperties(beanFactory, props);
		} catch (Exception e) {
			logger.error(e.getMessage());;
			throw new BeanInitializationException(e.getMessage());
		}
	}
}
