package com.dj.seal.modelFileRule.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.modelFileRule.dao.api.ModelFileRuleDao;
import com.dj.seal.modelFileRule.po.ModelFileRulePO;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.ModelFileRuleUtil;

public class ModelFileRuleDaoImpl extends BaseDAOJDBC implements ModelFileRuleDao {
	
	static Logger logger = LogManager.getLogger(ModelFileRuleDaoImpl.class.getName());
	
	
	
	private ModelFileRuleUtil table = new  ModelFileRuleUtil();


	public ModelFileRuleUtil getTable() {
		return table;
	}

	public void setTable(ModelFileRuleUtil table) {
		this.table = table;
	}
	
	
	@SuppressWarnings("unchecked")
	public ModelFileRulePO form(String sql){
		
		ModelFileRulePO obj = new ModelFileRulePO();
		
		List list = select(sql);//调用父类方法
		
		if(list.size()!=0){
			Map map = (Map)list.get(0);
			obj = (ModelFileRulePO) DaoUtil.setPo(obj, map, table);
			return obj;
		}else{
			return null;
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public List<ModelFileRulePO> formList(String sql){
		List<ModelFileRulePO>  listObj = new ArrayList<ModelFileRulePO>();
		List<Map> list = select(sql);
		for(Map map : list){
			ModelFileRulePO form = new ModelFileRulePO();
			form = (ModelFileRulePO) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}
	
	

	@Override
	public void addModelFileRule(ModelFileRulePO modelFileRule)
			throws Exception {
		
		String sql = ConstructSql.composeInsertSql(modelFileRule, table);
		add(sql);

	}

	@Override
	public void delModelFileRule(String mid) {
		String sql = "delete from " + ModelFileRuleUtil.TABLE_NAME + " where "
		+ ModelFileRuleUtil.MID + "= '" + mid+"'";
		delete(sql);
	}

	@Override
	public ModelFileRulePO getModelFileRuleById(String mid) {
		String sql = "select * from " + ModelFileRuleUtil.TABLE_NAME +" where "
		+ ModelFileRuleUtil.MID + "='" + mid+"'";
		
		//logger.info("获得模板规则：=="+sql);
		return form(sql);
	}

	@Override
	public ModelFileRulePO getModelFileRuleBySql(String sql) {
		return form(sql);
	}

	@Override
	public List<ModelFileRulePO> showAllModelFileRules(String sql)
			throws DAOException {
		return formList(sql);
	}

	@Override
	public int showCount(String sql) {
		return count(sql);
	}

	@Override
	public void updateModelFileRule(ModelFileRulePO modelFileRule)
			throws DAOException {
		
		String pastr = ModelFileRuleUtil.MID + " = '" + modelFileRule.getMid()+"'";
		String sql = ConstructSql.composeUpdateSql(modelFileRule,table,pastr);
		
		//logger.info("更新sql==="+sql);
		update(sql);

	}

}
