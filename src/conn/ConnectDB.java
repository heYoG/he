package conn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 创建连接数据库方法
 * @author Administrator
 *
 */
public class ConnectDB {
	Connection conn1=null;
	Connection conn2=null;
	public Connection getConnection(){
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("DBPro.properties");
		Properties p=new Properties();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/ssh?useUnicode=true&characterEncoding=utf-8";
			String user="root";
			String password="root";
			p.load(inStream);
//			conn1 = DriverManager.getConnection(url, user, password);
			conn2 =DriverManager.getConnection(url, p);
		} catch (ClassNotFoundException e) {//load driver
			e.printStackTrace();
		} catch (SQLException e) {//create connection
			e.printStackTrace();
		}catch (IOException e) {//load properties
			e.printStackTrace();
		}
		return conn2;
	}
	
}
