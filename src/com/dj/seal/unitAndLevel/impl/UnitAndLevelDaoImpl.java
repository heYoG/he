package com.dj.seal.unitAndLevel.impl;

import java.util.List;
import java.util.Map;

import com.dj.seal.unitAndLevel.dao.IUnitAndLevelDao;
import com.dj.seal.unitAndLevel.vo.UnitAndLevelVo;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.table.UnitAndLevelUtil;

public class UnitAndLevelDaoImpl extends BaseDAOJDBC implements IUnitAndLevelDao {
	private UnitAndLevelUtil uu=new UnitAndLevelUtil();//单据表对象
	private UnitAndLevelVo uv=new UnitAndLevelVo();//实例化
	@Override
	public int addUnitAndLevel(UnitAndLevelVo uv) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UnitAndLevelVo getUnitAndLevel(String unitNo) {
		String sql = "select unitNo,levelNo from "
				+ UnitAndLevelUtil.TABLE_NAME + " where unitNo='" + unitNo
				+ "'";
		List<UnitAndLevelVo> list=select(sql);
		Map map=null;
		if(list.size()>0){
			map=(Map)list.get(0);
			uv = (UnitAndLevelVo) DaoUtil.setPo(uv, map, uu);
			return uv;
		}
		return null;
	}

}
