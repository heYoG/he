package com.dj.seal.util.IP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;



import org.apache.logging.log4j.LogManager;

import com.dj.seal.util.Constants;

public class CheckIP {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(CheckIP.class.getName());
	public void checkAndModifyIP(){
		String main_ip = Constants.getProperty("main_ip");
		logger.info("待检验IP："+main_ip);
		boolean connect = false;
		try {
			InetAddress address = InetAddress.getByName(main_ip);
			connect = address.isReachable(3000);//超时时间为3秒
			logger.info("判断结果: " + connect);
		} catch (UnknownHostException e) {
			logger.info("连接host出错,IP不存在");
			logger.error(e.getMessage());
		}catch (IOException e) {
			logger.info("检查连接出错,无法连通");
			logger.error(e.getMessage());
		}
		if(!connect){
			logger.info("无法连通主服务器IP，开始修改本机IP");
			String connect_type = Constants.getProperty("connect_type");//连接名称
			String main_subnetmask = Constants.getProperty("main_subnetmask");//子网掩码
			String main_gateway = Constants.getProperty("main_gateway");//网关
			String cmdstr = "Netsh interface ip set address "+connect_type+" static "+main_ip+" "+main_subnetmask+" "+main_gateway; 
			logger.info(cmdstr);
			try {
				Runtime.getRuntime().exec(cmdstr);
			} catch (IOException e) {
				logger.info("修改IP出错");
				logger.error(e.getMessage());
			}
		}
		
		
		
	}
	
	
}
