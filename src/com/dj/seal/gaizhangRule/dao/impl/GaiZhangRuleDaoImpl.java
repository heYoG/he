package com.dj.seal.gaizhangRule.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.gaizhangRule.dao.api.IGaiZhangRuleDao;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.GaiZhangRuleUtil;

public class GaiZhangRuleDaoImpl extends BaseDAOJDBC implements
		IGaiZhangRuleDao {
	
	static Logger logger = LogManager.getLogger(GaiZhangRuleDaoImpl.class.getName());
	
	GaiZhangRuleUtil table = new GaiZhangRuleUtil();

	@SuppressWarnings("unchecked")
	private GaiZhangRule form(String sql) {
		GaiZhangRule obj = new GaiZhangRule();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (GaiZhangRule) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<GaiZhangRule> formList(String sql) {
		List<GaiZhangRule> listObj = new ArrayList<GaiZhangRule>();
		List<Map> list = select(sql);
		logger.info("size:"+list.size());
		for (Map map : list) {
			GaiZhangRule form = new GaiZhangRule();
			form = (GaiZhangRule) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}

	public GaiZhangRuleUtil getTable() {
		return table;
	}

	public void setTable(GaiZhangRuleUtil table) {
		this.table = table;
	}

	@Override
	public void addGaiZhangRule(GaiZhangRule form) throws Exception {
		// TODO Auto-generated method stub
		String sql = ConstructSql.composeInsertSql(form, table);
		
		logger.info("sql:"+sql);
		add(sql);
	}

	@Override
	public GaiZhangRule getGaiZhangRuleById(String rule_id) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "select * from " + GaiZhangRuleUtil.TABLE_NAME + " where "
				+ GaiZhangRuleUtil.RULE_NO + "=" + rule_id;
		return form(sql);
	}

	@Override
	public GaiZhangRule getGaiZhangRuleBy_sql(String sql) throws DAOException {
		// TODO Auto-generated method stub
		return form(sql);
	}

	@Override
	public List<GaiZhangRule> showAllGaiZhangRule(String sql)
			throws DAOException {
		// TODO Auto-generated method stub
		return formList(sql);
	}

	@Override
	public int showCount(String sql) {
		// TODO Auto-generated method stub
		return count(sql);
	}
	
	@Override
	public void deleteGaiZhangRule(String rule_id) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "delete from " + GaiZhangRuleUtil.TABLE_NAME + " where "
				+ GaiZhangRuleUtil.RULE_NO + " = " + rule_id;
		delete(sql);
	}
	public void zhuxiaoGaiZhangRule(String rule_id) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "update " + GaiZhangRuleUtil.TABLE_NAME +" set "+GaiZhangRuleUtil.RULE_STATE+"='2'"+" where "
				+ GaiZhangRuleUtil.RULE_NO + " = " + rule_id;
		update(sql);
	}
	public void jihuoGaiZhangRule(String rule_id) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "update " + GaiZhangRuleUtil.TABLE_NAME +" set "+GaiZhangRuleUtil.RULE_STATE+"='1'"+" where "
		+ GaiZhangRuleUtil.RULE_NO + " = " + rule_id;
        update(sql);
	}
	@Override
	public void upateGaiZhangRule(GaiZhangRule obj) throws DAOException {
		// TODO Auto-generated method stub
		String pastr = GaiZhangRuleUtil.RULE_NO + " = " + obj.getRule_no();
		String sql = ConstructSql.composeUpdateSql(obj, table, pastr);
		update(sql);
	}

}
