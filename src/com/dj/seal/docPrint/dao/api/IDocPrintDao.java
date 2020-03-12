package com.dj.seal.docPrint.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.DocPrintRoleUser;
import com.dj.seal.util.exception.DAOException;

public interface IDocPrintDao {
	/**
	 * 添加文档打印设置，用户表
	 * @param doc_no 文档编号
	 * @param user_id 用户id
	 * @param printNum 可打印份数
	 */
	public void addDocUser(String doc_no,String user_id,int printNum);
	/**
	 * 添加文档打印设置，用户表
	 * @param doc_no 文档编号
	 * @param role_id 角色编号
	 * @param printNum 可打印份数
	 */
	public void addDocRole(String doc_no,String role_id,int printNum);
	/**
	 * 根据sql得到文档打印设置对象列表
	 * @param sql 
	 */
	public List<DocPrintRoleUser> getRoleUserList(String sql);
	/**
	 * 根据SQL语句得到数据
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int showCount(String sql) throws DAOException;
	/**
	 * 更新打印情况
	 * @return
	 */
	public void updateDocPrintRoleUser(DocPrintRoleUser printInfo);
}
