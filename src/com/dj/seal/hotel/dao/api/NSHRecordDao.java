package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.QueryLog;
import com.dj.seal.util.exception.DAOException;

public interface NSHRecordDao {

	/**
	 * 新增单据记录
	 */
	public void addRecord(NSHRecordPo record);

	/**
	 * 新增单据记录：月表
	 * 
	 * @param record
	 */
	public void addRecordNew(NSHRecordPo record);

	/**
	 * 通过sql语句获得recordpo对象
	 * 
	 * @param sql
	 * @return
	 */
	public NSHRecordPo getRecordPoBySql(String sql) throws DAOException;

	public List<NSHRecordPo> getAllRecordPoBy(String sql) throws DAOException;

	public NSHRecordPo getRecordPOById(String cid) throws DAOException;

	public int showCount(String sql);

	/**
	 * 获取单据表名称
	 * 
	 * @return
	 */
	public String getTableName(String tbName);

	public int isRecordExist(String fName);

	/**
	 * 单据数据备份
	 */
	public int backupRecords();

	/**
	 * 删除原表已备份单据
	 */
	public int delRecords();

	/**
	 * 查询接口
	 * @param valcode     验证码
	 * @param date        交易日期
	 * @param consumerSq  流水号
	 * @return
	 */
	public List<NSHRecordPo> TradeData(String valcode, String date,String consumerSq);
	
	/**
	 * 新增单据记录-有纸化
	 * @param rdp
	 * @return
	 */
	public void addRecordYZH(NSHRecordPo rdp);
	
	/**
	 * 打印验证请求是否是第一次
	 * @param valcode 验证码
	 * @return        >表示是，=0表示否
	 */
	public NSHRecordPo checkValcode(String valcode);
	
	/**
	 * 根据sql获取部门信息
	 * @param sql 
	 */
	public DeptForm getDeptFormBySql(String sql);
	
	/**
	 * 根据机构号查询部门信息
	 * @param orgunit 机构号
	 * @return
	 */
	public DeptForm getDeptNameByOrgUnit(String orgunit);

	/**
	 * 删除>1年的单据历史表数据
	 * @return 
	 */
	public int delRecordOfHistory();
	
	/**
	 * 根据验证码查询最大打印份数、最大请求次数
	 * @param valcode 条件验证码
	 * @return
	 */
	public NSHRecordPo getMaxRequired_printNum(String valcode);
	
	/**
	 * ①首先调用有纸接口printSeal保存数据-无CEID
	 * ②再调用无纸接口addNSHSeal,旧三要素更新CEID和voucherNo-有纸接口没这两个字段
	 * @param CEID       上传到CE系统文档标识
	 * @param voucherNo  未知
	 */
	public void updateCEID(String CEID,String voucherNo,String caseseqid);
	
	/**
	 * 添加查询记录
	 * @param querylog 查询记录对象
	 */
	public void addQueryLog(QueryLog querylog);
	
	/**
	 * 删除服务端盖章日志(历史表)
	 * @return
	 */
	public int delServerSealLog();
	
	/**
	 * 更新最大请求次数、最大打印份数记录
	 * @param valcode   验证码,更新条件
	 *@param  printNum  打印份数 ,要更新数据
	 *@param  caseseqid 流水号,用于输出日志
	 *@param  requiredNum 同时更新请求次数
	 */
	public void updatePrintNum(String valcode,Number printNum,String caseseqid,Number requiredNum);
	
	/**
	 * 根据流水号更新附件信息
	 * @param caseseqid 流水号
	 * @param info_plus 新的附件信息
	 */
	public void updateInfo_plus(String caseseqid,String info_plus,Number requiredNum);
	
}
