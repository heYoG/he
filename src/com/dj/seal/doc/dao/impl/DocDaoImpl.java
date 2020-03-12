package com.dj.seal.doc.dao.impl;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.doc.dao.api.IDocDao;
import com.dj.seal.structure.dao.po.DocmentBody;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.DocmentBodyUtil;

public class DocDaoImpl extends BaseDAOJDBC implements
	IDocDao {
	
	static Logger logger = LogManager.getLogger(DocDaoImpl.class.getName());
	
	private DocmentBodyUtil table = new DocmentBodyUtil();


	/**
	 * 新增文档
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	@Override
	public void addDoc(DocmentBody doc){
		String sql = ConstructSql.composeInsertSql(doc, table);
		if(DBTypeUtil.DT_ORCL==Constants.DB_TYPE){
			if(doc.getDoc_content().length()>0){
				String str1 = sql
				.substring(0, sql.indexOf(doc.getDoc_content()) - 1);
				String str2 = sql.substring(sql.indexOf(doc.getDoc_content())
						+ doc.getDoc_content().length() + 1, sql.length());
				sql = str1 + "?" + str2;
				try {
					Connection conn = getDataSource().getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql);
					Reader clobReader = new StringReader(doc.getDoc_content()); // 将text转成流形式
					stmt.setCharacterStream(1, clobReader, doc.getDoc_content().length());// 替换sql语句中的？
					stmt.executeUpdate();// 执行SQL
					stmt.execute("commit");
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}else{
				add(sql);
			}
		}else if(DBTypeUtil.DT_MYSQL==Constants.DB_TYPE){
			add(sql);
		}else{
			add(sql);
		}
		
	}
	
	/**
	 * 根据文档编号判断文档是否在系统中已存在
	 * @param doc_id 文档编号
	 * @return
	 */
	@Override
	public boolean isExitDoc(String doc_id){
		String sql = "select * from " + DocmentBodyUtil.TABLE_NAME + " where "
		+ DocmentBodyUtil.DOC_NO+ "='" + doc_id + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 根据分页信息得到用户集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	@Override
	public List<DocmentBody> showDocByPageSplit(int pageIndex,int pageSize, String sql){
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,Constants.DB_TYPE);
		List<DocmentBody> list_temp = new ArrayList<DocmentBody>();
		List<Map> list = select(str);
		for (Map map : list) {
			DocmentBody obj = new DocmentBody();
			obj = (DocmentBody) DaoUtil.setPo(obj, map, table);
			list_temp.add(obj);
		}
		return list_temp;
	}
	/**
	 * 根据SQL语句得到数据
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	@Override
	public int showCount(String sql){
		return count(sql);
	}
}
