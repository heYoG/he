package com.dj.seal.sealTemplate.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;

public interface ISealTemplateDao {

	/**
	 * 根据分页信息和sql得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SealTemplate> showTempList(String sql) throws DAOException;
	/**
	 * 根据印模Form新增印模
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void addSealTemp(SealTemplate sealtemp) throws DAOException;
	/**
	 * 判断是否存在印模名称为temp_name的印模
	 * 
	 * @param temp_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistTempName(String temp_name) throws DAOException;
	
	/**
	 * 批准印模
	 * 
	 * @param temp
	 * @param temps
	 * @throws DAOException
	 */
	public void approveTemp(SealTemplate temp, String[] temps)
			throws DAOException;
	
	/**
	 * 根据印模ID获得印模记录
	 * 
	 * @param temp_id
	 * @return
	 * @throws DAOException
	 */
	public String showTempByTemp_id(String temp_id) throws DAOException;
	
	
	/**
	 * 得到数据库某一列最大值
	 * @param tableName  表名
	 * @param colName   列名
	 * @return
	 * @throws DAOException
	 */
	public int getMaxId(String tableName,String colName) throws DAOException;

	
	/**
	 * 根据分页信息得到印模集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SealTemplate> showSealTempsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException;

	/**
	 * 根据印模ID获得印模集合
	 * 
	 * @param temp_id
	 * @return
	 * @throws DAOException
	 */
	public List<SealTemplate> getSealTempList(String sealId)throws DAOException;
	/**
	 * 根据印模ID更新印模
	 * 
	 * @param temp_id
	 * @return
	 * @throws DAOException
	 */
	public void updTemp(String tempid)throws DAOException;

	/**
	 * 根据印模ID删除印模
	 * 
	 * @param temp_id
	 * @return
	 * @throws DAOException
	 */
	public void DeleteTeam(String tempID)throws DAOException;
	/**
	 * 根据印模ID得到印模对象
	 * 
	 * @param temp_id
	 * @return
	 * @throws DAOException
	 */
	public SealTemplate showTempByTemp(String tempID)throws DAOException;
	/**
	 * 更新印模
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void updateTemp(String  sql) throws DAOException;
	/**
	 * 删除印模
	 * 
	 * @param temp
	 * @throws DAOException
	 */
	public void deleteTemp(String sql) throws DAOException;
	public String selTempNum(String sql)throws Exception;
	
}
