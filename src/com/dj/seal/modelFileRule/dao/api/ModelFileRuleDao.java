package com.dj.seal.modelFileRule.dao.api;

import java.util.List;

import com.dj.seal.modelFileRule.po.ModelFileRulePO;
import com.dj.seal.util.exception.DAOException;

public interface ModelFileRuleDao {
	
	/**
	 * 增加模板规则
	 * @param modelFileRule
	 */
	public void addModelFileRule(ModelFileRulePO modelFileRule) throws Exception;
	
	/**
	 * 修改模板规则
	 * @param modelFileRule
	 */
	public void updateModelFileRule(ModelFileRulePO modelFileRule) throws DAOException ;
	
	/**
	 * 删除模板规则
	 * @param mid
	 */
	public void delModelFileRule(String mid);
	
	/**
	 * 通过ID获得指定的模板规则
	 * @param mid
	 * @return
	 */
	public ModelFileRulePO getModelFileRuleById(String mid);
	
	/**
	 * 获得所有的模板规则
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<ModelFileRulePO> showAllModelFileRules(String sql) throws DAOException;
	
	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);
	
	/**
	 * 通过sql获得指定的模板规则
	 * @return
	 */
	public ModelFileRulePO getModelFileRuleBySql(String sql);

}
