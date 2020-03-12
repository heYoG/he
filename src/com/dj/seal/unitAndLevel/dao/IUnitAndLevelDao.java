package com.dj.seal.unitAndLevel.dao;

import com.dj.seal.unitAndLevel.vo.UnitAndLevelVo;

public interface IUnitAndLevelDao {
	
	/**
	 * 同步机构号和级别号到数据库
	 * @param uv	机构号和级别javabean对象
	 * @return		执行成功数
	 */
	public int addUnitAndLevel(UnitAndLevelVo uv);
	
	/**
	 * 查询机构号和级别号
	 * @param unitNo	机构号，查询条件
	 * @return			机构号和级别javabean对象
	 */
	public UnitAndLevelVo getUnitAndLevel(String unitNo);
}
