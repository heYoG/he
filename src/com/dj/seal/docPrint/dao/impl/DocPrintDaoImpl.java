package com.dj.seal.docPrint.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.docPrint.dao.api.IDocPrintDao;
import com.dj.seal.structure.dao.po.DocPrintRoleUser;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.DocPrintRoleUserUtil;

public class DocPrintDaoImpl extends BaseDAOJDBC implements IDocPrintDao{
	
	static Logger logger = LogManager.getLogger(DocPrintDaoImpl.class.getName());
	/**
	 * 添加文档打印设置，用户表
	 * @param doc_no 文档编号
	 * @param user_id 用户id
	 * @param printNum 可打印份数
	 */
	@Override
	public void addDocRole(String doc_no, String role_id, int printNum) {
		String sql="insert into "+DocPrintRoleUserUtil.TABLE_NAME+" values ('"+doc_no+"',1,'"+role_id+"',"+printNum+",0)";
		add(sql);		
	}
	/**
	 * 添加文档打印设置，关联用户表
	 * @param doc_no 文档编号
	 * @param user_id 用户编号
	 * @param printNum 可打印份数
	 */
	@Override
	public void addDocUser(String doc_no, String user_id, int printNum) {
		String sql="insert into "+DocPrintRoleUserUtil.TABLE_NAME+" values ('"+doc_no+"',0,'"+user_id+"',"+printNum+",0)";
		add(sql);
	}

	@Override
	public List<DocPrintRoleUser> getRoleUserList(String sql) {
		// TODO Auto-generated method stub
		List<DocPrintRoleUser> list=new ArrayList<DocPrintRoleUser>();
		List<Map> maps=select(sql);
		for (Map map : maps) {
			DocPrintRoleUser user = new DocPrintRoleUser();
			user = (DocPrintRoleUser) DaoUtil.setPo(user, map, new DocPrintRoleUserUtil());
			list.add(user);
		}
		return list;
	}
	/**
	 * 根据SQL语句得到数据
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	@Override
	public int showCount(String sql) throws DAOException {
		// String last_sql = "select count(*) "
		// + sql.substring(sql.indexOf("from"));
		// List list = select(last_sql);
		// Map map = (Map) list.get(0);
		// return (Integer)map.get("count(*)");
		return count(sql);
	}
	/**
	 * 更新打印情况
	 * @return
	 */
	@Override
	public void updateDocPrintRoleUser(DocPrintRoleUser printInfo){
		String param=DocPrintRoleUserUtil.DOC_NO+"='"+printInfo.getDoc_no()+"' and "+DocPrintRoleUserUtil.TYPE+"="+printInfo.getType()
		+" and "+DocPrintRoleUserUtil.USER_ID+"='"+printInfo.getUser_id()+"'";
		String sql=ConstructSql.composeUpdateSql(printInfo, new DocPrintRoleUserUtil(),param);
		update(sql);
	}

}
