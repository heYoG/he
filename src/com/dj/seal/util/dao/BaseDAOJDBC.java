package com.dj.seal.util.dao;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dj.seal.util.Constants;
import com.dj.seal.util.table.SysDepartmentUtil;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Clob;

public class BaseDAOJDBC extends JdbcDaoSupport {
	static Logger logger = LogManager.getLogger(BaseDAOJDBC.class.getName());
	public BaseDAOJDBC() {
	}

	public void add(String sql) {
//		logger.info(sql+";");
		getJdbcTemplate().update(sql);
		getJdbcTemplate().execute("commit");		
	}
	public void update(String sql) {
		getJdbcTemplate().update(sql);
		getJdbcTemplate().execute("commit");
	}

	public void delete(String sql) {
		getJdbcTemplate().update(sql);
		getJdbcTemplate().execute("commit");
	}

	@SuppressWarnings("unchecked")
	public List select(String sql) {
		List list = getJdbcTemplate().queryForList(sql);
		getJdbcTemplate().execute("commit");		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List selectByPageSplit(int pageIndex, int pageSize, String sql) {
		/*
		 * StringBuffer sb=new StringBuffer(sql); int
		 * left=(pageIndex-1)*pageSize; sb.append(" limit
		 * ").append(left).append(",").append(pageSize);
		 */
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,
				Constants.DB_TYPE);
		// return getJdbcTemplate().queryForList(str);
		List list = getJdbcTemplate().queryForList(str);
		getJdbcTemplate().execute("commit");
		return list;
	}

	// 分页类（严川）
	@SuppressWarnings("unchecked")
	public List selectByPageSplit1(PageSplit pageSplit, String[] sql) {
		List list = this.select(sql[0]);
		Object count = 0;
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			count = map.get("count(*)");
		}
		pageSplit.setTotalCount(Integer.parseInt(count.toString()));// 总记录数
		StringBuffer sb = new StringBuffer(sql[1]);
		int left = (pageSplit.getNowPage() - 1) * pageSplit.getPageSize();

		if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sb.append(" limit ").append(left).append(",").append(
					pageSplit.getPageSize());
			return getJdbcTemplate().queryForList(sb.toString()); // mysql查询语句
		}
		return getJdbcTemplate().queryForList(
				DBTypeUtil.splitSql(sql[1], pageSplit.getNowPage(), pageSplit
						.getPageSize(), 2));
		// oracle查询语句
	}

	// 分页类（严川）
	@SuppressWarnings("unchecked")
	public List selectByPageSplit2(PageSplit pageSplit, String[] sql) {
		List list = this.select(sql[0]);
		Object count = 0;
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			count = map.get("count(*)");
		}
		pageSplit.setTotalCount(Integer.parseInt(count.toString()));// 总记录数
		StringBuffer sb = new StringBuffer(sql[1]);
		int left = (pageSplit.getNowPage() - 1) * pageSplit.getPageSize();

		if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sb.append(" limit ").append(left).append(",").append(
					pageSplit.getPageSize());
			return getJdbcTemplate().queryForList(sb.toString()); // mysql查询语句
		}
		// if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){
		return getJdbcTemplate().queryForList(
				DBTypeUtil.splitSql(sql[1], pageSplit.getNowPage(), pageSplit
						.getPageSize(), 2));
		// oracle查询语句
		// }
	}
	
	@SuppressWarnings("unchecked")
	public int count(String sql) {
		String last_sql = "select count(*) "
				+ sql.substring(sql.indexOf("from"));
		int i = getJdbcTemplate().queryForInt(last_sql);
		//System.out.println("countSql:"+sql);
		return i;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getMaxNo(String t_str, String c_str) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select max(").append(c_str);
		sb.append("+0) max_no from ").append(t_str);
		Object obj = getObject(sb.toString(), "max_no", false);
		if (obj == null) {
			return "1";
		}else{
			if (obj.getClass().equals(Integer.class)) {
				Integer no = (Integer) obj;
				return new Integer(no).toString();
			} else if (obj.getClass().equals(Double.class)) {
				Double no = (Double) obj;
				return new Integer(no.intValue()).toString();
			} else if (obj.getClass().equals(BigDecimal.class)) {
				BigDecimal no = (BigDecimal) obj;
				return new Integer(no.intValue()).toString();
			} else if(obj.getClass().equals(Long.class)){
				Long no = (Long)obj;
				return new Integer(no.intValue()).toString();
			}
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getNo(String t_str, String c_str) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select max(").append(c_str);
		sb.append("+0) as MAX_NO from ").append(t_str);

		Object obj = getObject(sb.toString(), "MAX_NO", false);

		if (obj == null) {	
			return "1";
		} else {
			if (obj.getClass().equals(Integer.class)) {
				Integer no = (Integer) obj;
				return new Integer(no + 1).toString();
			} else if (obj.getClass().equals(Double.class)) {
				Double no = (Double) obj;
				return new Integer(no.intValue() + 1).toString();
			} else if (obj.getClass().equals(BigDecimal.class)) {
				BigDecimal no = (BigDecimal) obj;
				return new Integer(no.intValue() + 1).toString();
			} else if(obj.getClass().equals(Long.class)){
				Long no = (Long)obj;
				return new Integer(no.intValue() + 1).toString();
			}
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getDeptMaxTabNo(String t_str,String dept_no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select max(").append(t_str);
		sb.append("+0) as MAX_NO from ").append(SysDepartmentUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(SysDepartmentUtil.DEPT_NO);
		sb.append(" like '").append(dept_no).append("%'");
		Object obj = getObject(sb.toString(), "MAX_NO", false);
		if (obj == null) {
			return "1";
		} else {
			if (obj.getClass().equals(Integer.class)) {
				Integer no = (Integer) obj;
				return new Integer(no + 1).toString();
			} else if (obj.getClass().equals(Double.class)) {
				Double no = (Double) obj;
				return new Integer(no.intValue() + 1).toString();
			} else if (obj.getClass().equals(BigDecimal.class)) {
				BigDecimal no = (BigDecimal) obj;
				return new Integer(no.intValue() + 1).toString();
			} else if(obj.getClass().equals(Long.class)){
				Long no = (Long)obj;
				return new Integer(no.intValue() + 1).toString();
			}
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	public Object getObject(String sql, String field, boolean bool)
			throws Exception {
		try {
			Map map = getJdbcTemplate().queryForMap(sql);
			getJdbcTemplate().execute("commit");
			if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){ //mysql5得到的map没有key值 map为 {=最大值} key为null
				Set<Entry<Object, Object>>  entrySet = map.entrySet();
				Object value = null;
				 
				for (Entry object : entrySet) {
					 value = object.getValue();
				}
				return value;
			}else{
				return map.get(field);
			}	
		} catch (Exception e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public String getStr(String sql, String field) throws Exception {
		List<Map> list = getJdbcTemplate().queryForList(sql);
		getJdbcTemplate().execute("commit");
		if (list.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map map : list) {
			String str = (String) map.get(field);
			sb.append(str).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getStrList(String sql, String field) throws Exception {
		List<Map> maps = getJdbcTemplate().queryForList(sql);
		getJdbcTemplate().execute("commit");
		List<String> list = new ArrayList<String>();
		for (Map map : maps) {
			String str = (String) map.get(field);
			list.add(str);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getStrOfInt(String sql, String field) throws Exception {
		List<Map> list = getJdbcTemplate().queryForList(sql);
		getJdbcTemplate().execute("commit");
		if (list.size() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Map map : list) {
			Object f = map.get(field);
			Integer str = null;
			if (f.getClass().equals(BigDecimal.class)) {
				BigDecimal bd = (BigDecimal) f;
				str = bd.intValue();
			} else if (f.getClass().equals(Integer.class)) {
				str = (Integer) f;
			}
			sb.append(str).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}

	public String getClobStr(String sql, String c_name) throws Exception {
		CLOB clob = getClob(sql, c_name);
		if(clob==null){
			return null;
		}
		Reader inStream = clob.getCharacterStream();
		char[] c = new char[(int) clob.length()];
		inStream.read(c);
		String data = new String(c);
		inStream.close();
		inStream = null;
		return data;
	}
	public String getClobStrMysql(String sql, String c_name) throws Exception {
		Clob clob = getMysqlClob(sql, c_name);
		if(clob==null){
			return null;
		}
		Reader inStream = clob.getCharacterStream();
		char[] c = new char[(int) clob.length()];
		inStream.read(c);
		String data = new String(c);
		inStream.close();
		inStream = null;
		return data;
	}
	public String MysqlClobToString1(String sql, String c_name) throws Exception {
		Clob clob = getMysqlClob(sql, c_name);
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
//		BLOB blob = getBlob(sql,c_name);
//		byte[] b = blob.getBytes(1, (int) blob.length());
//		return Base64.encodeToString(b);
	}
	// 将字CLOB转成STRING类型
	public String MysqlClobToString(Clob clob) throws Exception {
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	Connection getConn() throws Exception {
		return getJdbcTemplate().getDataSource().getConnection();
	}

	public CLOB getClob(String sql, String c_name) throws Exception {
		CLOB clob = null;
		Connection conn = getConn();
		Statement stm = conn.createStatement();
		ResultSet rset = stm.executeQuery(sql);
		if (rset.next()) {
			clob = (oracle.sql.CLOB) rset.getClob(c_name);
		}
		rset.close();
		stm.close();
		rset = null;
		stm = null;
		conn.close();
		return clob;
	}
	
	/**
	 * by hyg 20171228
	 * @param sql
	 * @param c_name
	 * @return
	 * @throws Exception
	 */
	public CLOB getClob2(String sql, String c_name) throws Exception {//预编译Sql
		CLOB clob = null;
		Connection conn = getConn();
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		ResultSet rset = preparedStatement.executeQuery(sql);
		
		if (rset.next()) {
			clob = (oracle.sql.CLOB) rset.getClob(c_name);
		}
		rset.close();
		preparedStatement.close();
		rset = null;
		preparedStatement = null;
		conn.close();
		return clob;
	}
	
	public Blob getMysqlBlob(String sql, String c_name) throws Exception{
		Blob blob = null;
		Connection conn = getConn();
		Statement stm = conn.createStatement();
		ResultSet rset = stm.executeQuery(sql);
		if (rset.next()) {
			blob = (com.mysql.jdbc.Blob) rset.getBlob(c_name);
		}
		rset.close();
		stm.close();
		rset = null;
		stm = null;
		conn.close();
		return blob;
	}
	public Clob getMysqlClob(String sql, String c_name) throws Exception {
		Clob clob = null;
		Connection conn = getConn();
		Statement stm = conn.createStatement();
		ResultSet rset = stm.executeQuery(sql);
		if(rset.next()){
			clob = (com.mysql.jdbc.Clob) rset.getClob(c_name);
		}
		rset.close();
		stm.close();
		rset = null;
		stm = null;
		conn.close();
		return clob;
	}
	public BLOB getBlob(String sql, String c_name) throws Exception{
		BLOB blob = null;
		Connection conn = getConn();
		Statement stm = conn.createStatement();
		ResultSet rset = stm.executeQuery(sql);
		if(rset.next()){
			blob = (oracle.sql.BLOB) rset.getBlob(c_name);
		}
		rset.close();
		stm.close();
		rset = null;
		stm = null;
		conn.close();
		return blob;
	}

	// 将字CLOB转成STRING类型
	public String ClobToString(CLOB clob) throws Exception {
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		  sb.append(s);
		  s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	
}
