package com.dj.seal.hotel.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.MasterplatejudgeDao;
import com.dj.seal.hotel.po.Masterplatejudgep;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTmasterplateJudgeUtil;

public class MasterplatejudgeDaoImpl extends BaseDAOJDBC implements
		MasterplatejudgeDao {

	static Logger logger = LogManager.getLogger(MasterplatejudgeDaoImpl.class.getName());
	
	private HotelTmasterplateJudgeUtil table = new HotelTmasterplateJudgeUtil();

	public HotelTmasterplateJudgeUtil getTable() {
		return table;
	}

	public void setTable(HotelTmasterplateJudgeUtil table) {
		this.table = table;
	}
	@SuppressWarnings("unchecked")
	public Masterplatejudgep form(String sql) {

		Masterplatejudgep mj = new Masterplatejudgep();

		List list = select(sql);// 调用父类方法

		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			mj = (Masterplatejudgep) DaoUtil.setPo(mj, map, table);
			return mj;
		} else {
			return null;
		}

	}
	@SuppressWarnings("unchecked")
	public List<Masterplatejudgep> formList(String sql) {
		List<Masterplatejudgep> listObj = new ArrayList<Masterplatejudgep>();
		List<Map> list = select(sql);
		for (Map map : list) {
			Masterplatejudgep form = new Masterplatejudgep();
			form = (Masterplatejudgep) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}

	@Override
	public void addMasterplatejudge(Masterplatejudgep mj) throws DAOException {

		String sql = ConstructSql.composeInsertSql(mj, table);
		add(sql);

	}

	@Override
	public void delMasterplatejudge(String cid) throws DAOException {
		String sql = "delete from "
				+ HotelTmasterplateJudgeUtil.getTABLE_NAME() + " where "
				+ HotelTmasterplateJudgeUtil.getC_ID() + "=" + cid;

		delete(sql);

	}

	@Override
	public Masterplatejudgep getMasterplatejudge(String cid) throws DAOException {
		String sql = "select * from "
				+ HotelTmasterplateJudgeUtil.getTABLE_NAME() + " where "
				+ HotelTmasterplateJudgeUtil.getC_ID() + " = " + cid;

		return form(sql);
	}

	@Override
	public Masterplatejudgep getMasterplatejudgeBy_sql(String sql)
			throws DAOException {
		return form(sql);
	}

	@Override
	public List<Masterplatejudgep> showAllMasterplatejudge(String sql)
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
	public void updateMasterplatejudge(Masterplatejudgep mj) throws DAOException {
		// TODO Auto-generated method stub
		String pastr = HotelTmasterplateJudgeUtil.getC_ID() + " = "
				+ mj.getC_id();
		String sql = ConstructSql.composeUpdateSql(mj, table, pastr);
		update(sql);
	}

}
