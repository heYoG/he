package util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 * 使用spring中的JdbcTemplate连接数据库
 * @author Administrator
 *
 */
public class BaseDaoJDBC extends JdbcTemplate {
	
	/**
	 * 未使用预编译sql增、删和改
	 */
	@Override
	public int update(String sql) throws DataAccessException {
//		System.out.println("sql2:"+sql);
		return super.update(sql);
	}

	/**
	 * 	查记录
	 */
	@Override
	public List queryForList(String sql) throws DataAccessException {
		return super.queryForList(sql);
	}
	
	/**
	 * 统计
	 */
	@Override
	public int queryForInt(String sql) throws DataAccessException {
		return super.queryForInt(sql);
	}
}
