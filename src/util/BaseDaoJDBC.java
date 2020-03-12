package util;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * 使用spring中的JdbcTemplate连接数据库
 * @author Administrator
 *
 */
public class BaseDaoJDBC extends JdbcTemplate {
	
	/**
	 * 未使用占位符sql增、删和改
	 */
	@Override
	public int update(String sql) throws DataAccessException {
//		System.out.println("sql2:"+sql);
		return super.update(sql);
	}

	/**
	 * 使用占位符sql增、删和改
	 */
	@Override
	public int update(String sql, Object... args) throws DataAccessException {
		// TODO Auto-generated method stub
		return super.update(sql, args);
	}
	
	/**
	 * 	查记录
	 */
	@Override
	public List queryForList(String sql) throws DataAccessException {
		return super.queryForList(sql);
	}
	
	/**
	 * 使用占位符查记录
	 */
	@Override
	public List queryForList(String sql, Object... args) throws DataAccessException {
		return super.queryForList(sql, args);
	}
	
	/**
	 * 统计
	 */
	@Override
	public int queryForInt(String sql) throws DataAccessException {
		return super.queryForInt(sql);
	}
	
	/**
	 * 使用占位符统计
	 */
	@Override
	public long queryForLong(String sql, Object... args) throws DataAccessException {
		return super.queryForLong(sql, args);
	}
}
