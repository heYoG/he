package com.dj.seal.gaizhangRule.dao.api;


import java.util.List;

import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.util.exception.DAOException;

public interface IGaiZhangRuleDao {
	/**
	 * 
	 * @param form
	 * @throws DAOException
	 */
	public void addGaiZhangRule(GaiZhangRule obj) throws Exception;

	/**
	 * 
	 * @param rule_id
	 * @return
	 * @throws DAOException
	 */
	public GaiZhangRule getGaiZhangRuleById(String rule_id) throws DAOException;

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public GaiZhangRule getGaiZhangRuleBy_sql(String sql) throws DAOException;

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<GaiZhangRule> showAllGaiZhangRule(String sql) throws DAOException;

	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);
	
	/**
	 * 
	 * @param parent_no
	 * @throws DAOException
	 */
	public void upateGaiZhangRule(GaiZhangRule obj) throws DAOException;
	
	/**
	 * 
	 * @param app_id
	 * @throws DAOException
	 */
	public void deleteGaiZhangRule(String app_id) throws DAOException;
}
