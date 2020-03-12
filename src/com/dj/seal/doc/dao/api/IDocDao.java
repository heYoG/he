package com.dj.seal.doc.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.DocmentBody;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;

public interface IDocDao {

	
	/**
	 * 新增文档
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void addDoc(DocmentBody doc);
	/**
	 * 根据文档编号判断文档是否在系统中已存在
	 * @param doc_id 文档编号
	 * @return
	 */
	public boolean isExitDoc(String doc_id);
	/**
	 * 根据分页信息得到用户集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<DocmentBody> showDocByPageSplit(int pageIndex,int pageSize, String sql);
	/**
	 * 根据SQL语句得到数据
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public int showCount(String sql);
	
}
