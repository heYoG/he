package util;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.InitializingBean;

import conn.ConnectDB;

public class InitializeSys implements InitializingBean{
	Connection conn= new ConnectDB().getConnection();

	@Override
	public void afterPropertiesSet() throws Exception {//需要spring容器加载才能启动执行
		System.out.println("已初始化此类，放心使用.............");
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("user", "He");
	}
	
	static{
		
	}

}
