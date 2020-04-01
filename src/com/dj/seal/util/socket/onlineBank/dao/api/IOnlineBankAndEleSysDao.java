package com.dj.seal.util.socket.onlineBank.dao.api;

import java.util.List;

import com.dj.seal.util.socket.onlineBank.po.OnlineBankAndEleSysPo;
/**
 * 	企业网银和电子对账系统需求保存和查询数据
 * @author Administrator
 *
 */
public interface IOnlineBankAndEleSysDao<T> {
	/**
	 * 	保存企业网银和电子对账系统数据
	 * @return
	 */
	public abstract void onlineBankSave(T t);
	
	/**
	 * 	按条件查询返回单条记录
	 * @param seqNo		流水号
	 * @param date		交易日期
	 * @param valcode	唯一验证码
	 * @return
	 */
	OnlineBankAndEleSysPo  getOnlineBankData(String seqNo,String date,String valcode);
	
	/**
	 * 	查询所有记录(备用)
	 * @return
	 */
	List<OnlineBankAndEleSysPo> getOnlineBankList();
	
	/**
	 * 	条件查询多条记录(用于1个验证码对应多条记录情形)
	 * @param valcode	唯一验证码
	 * @return
	 */
	List<OnlineBankAndEleSysPo> getOnlineBankList(String valcode);
	
	/**
	 * 	类型转换工具类
	 * @param sql	查询判断类型sql
	 * @return
	 */
	Object form(String sql);
}
