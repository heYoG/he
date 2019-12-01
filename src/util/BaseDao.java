package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.ConnectDB;

public class BaseDao<T> {
	Connection conn = new ConnectDB().getConnection();//创建连接对象
	PreparedStatement statement = null;//创建预编译对象
	ResultSet rs=null;//创建查询结果对象
	
	/*增、删、改*/
	public int executeUpdate(String sql,Object... orgs){
		int ret=0;
		try {
			statement=conn.prepareStatement(sql);
			for(int i=0;i<orgs.length;i++){//设置占位符
				statement.setObject(i+1, orgs[i]);
			}
			System.out.println("sql:"+statement);
			ret=statement.executeUpdate();//执行Sql
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/* 查 */
	public ResultSet executeQuery(String sql,int flag,Object... orgs) {
		try {
			statement = conn.prepareStatement(sql);
			if (flag==1)//设置占位符参数
				for (int i = 0; i < orgs.length; i++) {
					statement.setObject(i + 1, orgs[i]);
				}
			System.out.println("sql:"+statement);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/*关闭连接*/
	public void close(){
		try {
			if(statement!=null)
				statement.close();
			if(rs!=null)
				rs.close();
			if(conn!=null)
				conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
