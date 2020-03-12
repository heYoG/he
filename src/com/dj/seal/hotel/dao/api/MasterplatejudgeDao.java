package com.dj.seal.hotel.dao.api;

import java.util.List;


import com.dj.seal.hotel.po.Masterplatejudgep;
import com.dj.seal.util.exception.DAOException;

public interface MasterplatejudgeDao {

	/**
	 * 新增判定条件
	 * @param mj
	 * @throws DAOException
	 */
	public void addMasterplatejudge(Masterplatejudgep mj) throws DAOException;
	/**
	 * 修改判定条件
	 * @param mj
	 * @throws DAOException
	 */
	public void updateMasterplatejudge(Masterplatejudgep mj) throws DAOException;
	
	/**
	 * 删除判定条件
	 * @param cid
	 * @throws DAOException
	 */
	public void delMasterplatejudge(String cid) throws DAOException;
	
	/**
	 * 通过ID获得指定判定条件
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public Masterplatejudgep getMasterplatejudge(String cid) throws DAOException;
	
	/**
	 * 获得所有的判定条件
	 * @return
	 * @throws DAOException
	 */
	public List<Masterplatejudgep> showAllMasterplatejudge(String sql) throws DAOException;
	
	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);
	
	/**
	 * 通过sql语句返回查询判定条件
	 * @return
	 * @throws DAOException
	 */
	public Masterplatejudgep getMasterplatejudgeBy_sql(String sql) throws DAOException;
}
