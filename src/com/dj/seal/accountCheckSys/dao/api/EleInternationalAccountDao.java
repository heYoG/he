package com.dj.seal.accountCheckSys.dao.api;

import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;


public interface EleInternationalAccountDao {
	/**
	 * 保存对账系统、国结系统等请求记录
	 * @param  eac
	 * @return 返回>0保存成功
	 */
	public int accountCheckRecord(EleInternationalAccountPo eac);

	/**
	 * 定时删除服务器印章图片
	 * @param  rootPath 印章图片路径
	 * @return 返回true删除成功
	 */
	public boolean delEleInternationalAccountSealPic(String rootPath);

	/**
	 * 定时删除数据库印章图片原始表旧数据
	 * @return 返回>0删除了数据
	 */
	public int delEleInternationalAccountSealPicData();
	
	/**
	 * 备份对账系统原始表数据
	 * @return 返回>0备份了数据
	 */
	public int backEleInternationalAccountSealPicData();
	
	/**
	 * 根据验证码查询数据
	 * @param  valcode 验证码
	 * @return
	 */
	public EleInternationalAccountPo getData(String valcode);
	
	/**
	 * 删除对账系统历史表数据
	 * @return >0,删除了数据;=0,没有可删除数据
	 */
	public int delEleInternationalAccountOldTabData();
	
	/**
	 * 移动银行和微信银行查询
	 * @param valcode      验证码
	 * @param TranDate	        交易日期
	 * @return
	 */
	public EleInternationalAccountPo getDataOfWechatAndMobileBank(String valcode,String TranDate);
}
