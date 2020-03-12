package com.dj.seal.gaizhangRule.service.api;


import java.util.List;

import com.dj.seal.gaizhangRule.po.GaiZhangRule;


public interface IGaiZhangRuleSrv {
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<GaiZhangRule> showRules() throws Exception;

	/**
	 * 
	 * @param a_id
	 * @return
	 * @throws Exception
	 */
	public List<GaiZhangRule> showSubsById(String a_id) throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GaiZhangRule getRule(String a_id) throws Exception;

	/**
	 * 
	 * @param obj
	 * @return 1为成功，0为失败
	 * @throws Exception
	 */
	public int addRule(GaiZhangRule obj) throws Exception;
	
	/**
	 * 
	 * @param obj
	 * @return 1为成功，0为失败
	 * @throws Exception
	 */
	public int updRule(GaiZhangRule obj) throws Exception;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delRule(String id) throws Exception;
	
	/**
	 * linux签章
	 * @param rule_type
	 * @param arg_desc
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public String getRuleArgLinux(Integer rule_type,String arg_desc,int pagesize) throws Exception;

}
