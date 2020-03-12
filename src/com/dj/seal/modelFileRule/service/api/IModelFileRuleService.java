package com.dj.seal.modelFileRule.service.api;

import java.util.List;

import com.dj.seal.modelFileRule.form.ModelFileRuleForm;
import com.dj.seal.modelFileRule.po.ModelFileRulePO;
import com.dj.seal.util.exception.DAOException;

public interface IModelFileRuleService {
	
	/**
	 * 增加模板规则
	 * @return 0 成功 1失败
	 * @throws DAOException
	 */
	public int addModelFileRule(ModelFileRuleForm f) throws DAOException;
	
	/**
	 * 删除模板规则
	 * @return 0成功 1失败
	 * @throws DAOException
	 */
	public int delModelFileRuleDao(String mid)throws DAOException;
	
	public int updateModelFileRule(ModelFileRulePO medelFilleRule)throws DAOException;
	
	/**
	 * 获得所有的模板规则
	 * @return
	 * @throws DAOException
	 */
	public List<ModelFileRulePO> showAllModelFileRules() throws DAOException;
	
	public ModelFileRulePO getModelFileRuleByMid(String mid) throws DAOException;

}
