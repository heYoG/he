package com.dj.seal.gaizhangRule.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import serv.rules.GetRuleList;
import serv.rules.GetRules;
import srvSeal.SrvSealUtil;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.gaizhangRule.dao.impl.GaiZhangRuleDaoImpl;
import com.dj.seal.gaizhangRule.form.GaiZhangRuleForm;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.gaizhangRule.service.api.IGaiZhangRuleSrv;
import com.dj.seal.gaizhangRule.vo.GaiZhangRuleVo;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.table.GaiZhangRuleUtil;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.web.SearchForm;

public class GaiZhangRuleSrvImpl implements IGaiZhangRuleSrv {
	
	static Logger logger = LogManager.getLogger(GaiZhangRuleSrvImpl.class.getName());
	
	private SealBodyServiceImpl sealbody_srv;
	private GaiZhangRuleDaoImpl rule_dao;
	private GaiZhangRuleUtil table = new GaiZhangRuleUtil();
    private CertSrv cert_srv;
    
	public List<GaiZhangRule> getRulesBySealId(int seal_id) throws Exception {
		String sql = "select * from " + GaiZhangRuleUtil.TABLE_NAME + " where "
				+ GaiZhangRuleUtil.SEAL_ID + " ='" + seal_id + "' and "+GaiZhangRuleUtil.RULE_STATE+"='1'";
		return rule_dao.showAllGaiZhangRule(sql);
	}
	public boolean isNameExist(String name) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(GaiZhangRuleUtil.TABLE_NAME);
		sb.append(" where ").append(GaiZhangRuleUtil.RULE_NAME);
		sb.append("='").append(name).append("'");
		int num = rule_dao.count(sb.toString());
		if (num == 0) {
			return false;
		}
		return true;
	}

	public boolean isRuleNoExist(String rule_no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(GaiZhangRuleUtil.TABLE_NAME);
		sb.append(" where ").append(GaiZhangRuleUtil.RULE_NO);
		sb.append("='").append(rule_no).append("'");
		int num = rule_dao.count(sb.toString());
		if (num == 0) {
			return false;
		}
		return true;
	}
	
	private int jianYi(String str) {
		int i = Integer.valueOf(str);
		if (i != -1) {
			i = i - 1;
		}
		return i;
	}

	private String qiFengYe(String bgn, String end) {
		int b = Integer.valueOf(bgn);
		int e = Integer.valueOf(end);
		StringBuffer sb = new StringBuffer();
		if (e != -1) {
			int r = e - b;
			for (int i = 1; i <= r; i++) {
				sb.append(i).append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
		} else {
			sb.append("end" + bgn);
		}
		return sb.toString();
	}

	/**
	 * 根据规则号得到控件调用的参数接口
	 * 
	 * @param rule_no
	 * @return
	 * @throws Exception
	 */
	
	public String getRuleArg(String rule_no,int pagesize) throws Exception {
		GaiZhangRule rule = getRule(rule_no);
		Integer rule_type = rule.getRule_type();
		String[] arg = rule.getArg_desc().split(",");
		StringBuffer sb = new StringBuffer();
		logger.info("arg[0]:--------------"+arg[0]);
		if (false) {
		} else if (rule_type == 1) {// 绝对坐标
			if(arg[0].equals("-1")){//如果是盖到最后一页-1那么就把最后一页赋给arg[0]
				arg[0]=pagesize+""; 
			}
			sb.append(jianYi(arg[0])).append(",");// 盖在第几页
			sb.append(arg[1]).append(",");// 横坐标
			sb.append("4,");// 
			sb.append(arg[2]).append(",");//纵坐标
		} else if (rule_type == 2) {// 书签
			sb.append("AUTO_ADD:0,-1,0,0,1,DJ_BMK_AUTO_SET_");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 3) {// 骑缝章
			if(pagesize==1){
				return "yiyeqifeng";//一页骑缝不加盖
			}
		// pagesData="0,20000,5,3,50,1,2,"+seal_data;//3.骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			if(pagesize==1){
				sb.append("1").append(",");// 文档一页，第一页1%
			}else if(pagesize>10){
				sb.append("20").append(",");// 第一页20%
			}else{
				int per = 100/pagesize;
				sb.append(per).append(",");// 
			}
			sb.append(qiFengYe(arg[3], arg[4])).append(",");
		} else if (rule_type == 4) {// 文字-覆盖
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",0,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 5) {// 文字-后面
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",50000,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 6) {// 多面绝对坐标
			// arg[0]:1奇数，2偶数,3指定间隔； arg[1]:间隔数; arg[2]:横坐标； arg[3]:纵坐标
			sb.append("2".equals(arg[0]) ? 1 : 0).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("5,");// 骑缝章类型
			sb.append("5,");// 内部骑缝
			sb.append(arg[3]).append(",");// 纵坐标
			sb.append(arg[0] + "jiange" + arg[1]).append(",");
			logger.info("多面绝对坐标"+sb.toString());
		} else if (rule_type == 7) {// 多面骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页;
			// arg[5]:间隔数
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			sb.append("50").append(",");// 百分之五十
			sb.append("3jiange" + arg[5]).append(",");
			
		} else if (rule_type == 8) {// 手动盖章
		}
		String rule_arg = sb.toString();
		if (rule_arg.indexOf("jiange") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			int len = temp.indexOf(",");
			String jStr = rule_arg.substring(
					rule_arg.indexOf("jiange") - 1, rule_arg
							.indexOf("jiange")
							+ len);
			rule_arg = rule_arg.replaceFirst(jStr, jiangeStr(jStr,pagesize));
		}
		return rule_arg;
	}
	public String getRuleArgS(Integer rule_type,String arg_desc,int pagesize) throws Exception {
		String[] arg = arg_desc.split(",");
		StringBuffer sb = new StringBuffer();
		if (false) {
		} else if (rule_type == 1) {// 绝对坐标
			if(arg[0].equals("-1")){//如果是盖到最后一页-1那么就把最后一页赋给arg[0]
				arg[0]=pagesize+""; 
			}
			sb.append(jianYi(arg[0])).append(",");// 盖在第几页
			sb.append(arg[1]).append(",");// 横坐标
			sb.append("8,");// 
			sb.append(arg[2]).append(",");
		} else if (rule_type == 2) {// 书签
			sb.append("AUTO_ADD:0,-1,0,0,1,DJ_BMK_AUTO_SET_");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 3) {// 骑缝章
			if(pagesize==1){
				return "yiyeqifeng";//一页骑缝不加盖
			}
		// pagesData="0,20000,5,3,50,1,2,"+seal_data;//3.骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "9" : "10").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			if(pagesize==1){
				sb.append("1").append(",");// 文档一页，第一页1%
			}else if(pagesize>10){
				sb.append("20").append(",");// 第一页20%
			}else{
				int per = 100/pagesize;
				sb.append(per).append(",");// 
			}
			sb.append(qiFengYe(arg[3], arg[4])).append(",");
		} else if (rule_type == 4) {// 文字-覆盖
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",");// 结束页
			sb.append(arg[3]).append(",");//上下偏移量
			sb.append(arg[4]).append(",");//左右偏移量
			sb.append("255,");
			sb.append(arg[0]).append(")|(8,");
		} else if (rule_type == 5) {// 文字-后面
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",50000,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(8,");
		} else if (rule_type == 6) {// 多面绝对坐标
			// arg[0]:1奇数，2偶数,3指定间隔； arg[1]:间隔数; arg[2]:横坐标； arg[3]:纵坐标
			sb.append("2".equals(arg[0]) ? 1 : 0).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("9,");// 骑缝章类型
			sb.append("5,");// 内部骑缝
			sb.append(arg[3]).append(",");// 纵坐标
			sb.append(arg[0] + "jiange" + arg[1]).append(",");
			logger.info("多面绝对坐标"+sb.toString());
		} else if (rule_type == 7) {// 多面骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页;
			// arg[5]:间隔数
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "9" : "10").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			sb.append("50").append(",");// 百分之五十
			sb.append("3jiange" + arg[5]).append(",");
			
		} else if (rule_type == 8) {// 手动盖章
		}
		String rule_arg = sb.toString();
		if (rule_arg.indexOf("jiange") != -1) {
			//logger.info("rule_arg:"+rule_arg);
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			//logger.info("temp:"+temp);
			int len = temp.indexOf(",");
			//logger.info("len:"+len);
			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1, rule_arg.indexOf("jiange")+ len);
			//logger.info("jStr:"+jStr);
			//logger.info("pagesize:"+pagesize);
			rule_arg = rule_arg.replaceFirst(jStr, jiangeStr(jStr,pagesize));
			//logger.info("rule_arg:"+rule_arg);
		}
		return rule_arg;
	}
	/**
	 * 根据规则号得到控件调用的参数接口
	 * 
	 * @param rule_no
	 * @return
	 * @throws Exception
	 */
	public String getRuleArgSr(String rule_no,int pagesize) throws Exception {
		GaiZhangRule rule = getRule(rule_no);
		Integer rule_type = rule.getRule_type();
		String[] arg = rule.getArg_desc().split(",");
		StringBuffer sb = new StringBuffer();
		logger.info("arg[0]:--------------"+arg[0]);
		if (false) {
		} else if (rule_type == 1) {// 绝对坐标
			if(arg[0].equals("-1")){//如果是盖到最后一页-1那么就把最后一页赋给arg[0]
				arg[0]=pagesize+""; 
			}
			sb.append(jianYi(arg[0])).append(",");// 盖在第几页
			sb.append(arg[1]).append(",");// 横坐标
			sb.append("4,");// 
			sb.append(arg[2]).append(",");//纵坐标
		} else if (rule_type == 2) {// 书签
			sb.append("AUTO_ADD:0,-1,0,0,1,DJ_BMK_AUTO_SET_");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 3) {// 骑缝章
			if(pagesize==1){
				return "yiyeqifeng";//一页骑缝不加盖
			}
		// pagesData="0,20000,5,3,50,1,2,"+seal_data;//3.骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			if(pagesize==1){
				sb.append("1").append(",");// 文档一页，第一页1%
			}else if(pagesize>10){
				sb.append("20").append(",");// 第一页20%
			}else{
				int per = 100/pagesize;
				sb.append(per).append(",");// 
			}
			sb.append(qiFengYe(arg[3], arg[4])).append(",");
		} else if (rule_type == 4) {// 文字-覆盖
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",0,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(8,");
		} else if (rule_type == 5) {// 文字-后面
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",50000,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(8,");
		} else if (rule_type == 6) {// 多面绝对坐标
			// arg[0]:1奇数，2偶数,3指定间隔； arg[1]:间隔数; arg[2]:横坐标； arg[3]:纵坐标
			sb.append("2".equals(arg[0]) ? 1 : 0).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("5,");// 骑缝章类型
			sb.append("5,");// 内部骑缝
			sb.append(arg[3]).append(",");// 纵坐标
			sb.append(arg[0] + "jiange" + arg[1]).append(",");
			logger.info("多面绝对坐标"+sb.toString());
		} else if (rule_type == 7) {// 多面骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页;
			// arg[5]:间隔数
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			sb.append("50").append(",");// 百分之五十
			sb.append("3jiange" + arg[5]).append(",");
			
		} else if (rule_type == 8) {// 手动盖章
		}
		String rule_arg = sb.toString();
		if (rule_arg.indexOf("jiange") != -1) {
			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
			int len = temp.indexOf(",");
			String jStr = rule_arg.substring(
					rule_arg.indexOf("jiange") - 1, rule_arg
							.indexOf("jiange")
							+ len);
			rule_arg = rule_arg.replaceFirst(jStr, jiangeStr(jStr,pagesize));
		}
		return rule_arg;
	}
	
	public String getSealDataByRule(String rule_no) throws Exception {
		if ("".equals(rule_no)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
//		String data = "";//modify 20180815
		sb.append("select t1.").append(SealBodyUtil.SEAL_DATA);
		sb.append(" from ").append(SealBodyUtil.TABLE_NAME).append(" t1,");
		sb.append(GaiZhangRuleUtil.TABLE_NAME).append(" t2 where t1.");
		sb.append(SealBodyUtil.SEAL_ID).append("=t2.");
		sb.append(GaiZhangRuleUtil.SEAL_ID).append(" and t2.");
		sb.append(GaiZhangRuleUtil.RULE_NO).append("='").append(rule_no);
		sb.append("'");
//		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {//modify 20180815
//			data = rule_dao.getClobStr(sb.toString(), SealBodyUtil.SEAL_DATA);
//		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
//			data = rule_dao.getStr(sb.toString(), SealBodyUtil.SEAL_DATA);
//		}
		return rule_dao.getClobStr(sb.toString(), SealBodyUtil.SEAL_DATA);
//		return data;//modify 20180815
	}
	
	public String getSealIdByRuleNo(String rule_no) {
		if ("".equals(rule_no)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String seal_id = "";
		sb.append("select ").append(GaiZhangRuleUtil.SEAL_ID);
		sb.append(" from ").append(GaiZhangRuleUtil.TABLE_NAME);
		sb.append(" where ").append(GaiZhangRuleUtil.RULE_NO);
		sb.append(" = '").append(rule_no).append("'");
		try {
			seal_id = rule_dao.getStr(sb.toString(), GaiZhangRuleUtil.SEAL_ID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return seal_id;
	}
	
  
	@Override
	public int addRule(GaiZhangRule obj) throws Exception {
		try {
			rule_dao.addGaiZhangRule(obj);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int addRule(GaiZhangRuleForm f) throws Exception {
		GaiZhangRule obj = new GaiZhangRule();
		f.setRule_state("1");// 可用盖章规则
		obj.setVersion_no(1);
		BeanUtils.copyProperties(obj, f);
		obj.setRule_no(rule_dao.getNo(GaiZhangRuleUtil.TABLE_NAME,
				GaiZhangRuleUtil.RULE_NO));
		return addRule(obj);
	}

	@Override
	public int delRule(String id) throws Exception {
		try {
			rule_dao.deleteGaiZhangRule(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public GaiZhangRule getRule(String a_id) throws Exception {
		return rule_dao.getGaiZhangRuleById(a_id);
	}

	@Override
	public List<GaiZhangRule> showRules() throws Exception {
		String sql = "select * from "+ GaiZhangRuleUtil.TABLE_NAME;
		return rule_dao.showAllGaiZhangRule(sql);
	}

	@Override
	public List<GaiZhangRule> showSubsById(String a_id) throws Exception {
		String sql = "select * from " + GaiZhangRuleUtil.TABLE_NAME + " where "
				+ GaiZhangRuleUtil.RULE_NO + " in(" + a_id + ")";

		return rule_dao.showAllGaiZhangRule(sql);
	}



	public List<GaiZhangRuleVo> showRulesBySel(String sel) throws Exception {
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select * from ");
		sb2.append(GaiZhangRuleUtil.TABLE_NAME);
		sb2.append(" where 1=2");
		if (!"".equals(sel)) {
			String[] str = sel.split(",");
			for (String no : str) {
				sb2.append(" or ").append(GaiZhangRuleUtil.RULE_NO);
				sb2.append("='").append(no).append("'");
			}
		}
		List<GaiZhangRuleVo> rules = listObjs(sb2.toString());
		return rules;
	}

	public int updRuleVo(GaiZhangRuleVo vo) throws Exception {
		GaiZhangRule obj = new GaiZhangRule();
		obj=rule_dao.getGaiZhangRuleById(vo.getRule_no());
		obj.setVersion_no(obj.getVersion_no()+1);
		BeanUtils.copyProperties(obj, vo);
		return updRule(obj);
	}
	private static String endStr(String eStr, SrvSealUtil srv_seal, int nObjID) {
		int bgn = Integer.valueOf(eStr.substring(3));
		int end = srv_seal.getPageCount(nObjID);
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= end - bgn; i++) {
			sb.append(i).append(",");
		}
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}
	private static String jiangeStr(String jStr, SrvSealUtil srv_seal,
			int nObjID) {
		char c = jStr.charAt(0);
		int bgn = c == '2' ? 2 : 1;
		int jiange = c == '3' ? Integer.valueOf(jStr.substring(7)) : 2;
		int end = srv_seal.getPageCount(nObjID);
		StringBuffer sb = new StringBuffer();
		for (int i = jiange; i <= end - bgn; i = i + jiange) {
			sb.append(i).append(",");
		}
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}
	private static String jiangeStr(String jStr, int pagesize) {
		char c = jStr.charAt(0);
		int bgn = c == '2' ? 2 : 1;
		int jiange = c == '3' ? Integer.valueOf(jStr.substring(7)) : 2;
		int end = pagesize;
		StringBuffer sb = new StringBuffer();
		logger.info("bgn:"+bgn);
		logger.info("jiange:"+jiange);
		logger.info("end:"+end);
		if(end==1&&bgn==1){
			sb.append("1").append(",");
		}else{
			for (int i = jiange; i <= end - bgn; i = i + jiange) {
				sb.append(i).append(",");
			}
		}
		logger.info(sb.toString());
		logger.info(sb.indexOf(","));
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	}
	private static String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}
	
    public int getRuleInfo(SrvSealUtil srv_seal,int nObjID,String rule_no)throws Exception{   	
    	String bpath = bpath();
    	GaiZhangRule gaizhang=rule_dao.getGaiZhangRuleById(rule_no);
    	int pagesize=srv_seal.getPageCount(nObjID);
    	String rule_arg=getRuleArgSr(rule_no, pagesize);
    	int version_no=gaizhang.getVersion_no();
    	File file=new File(bpath+"doc\\rules\\"+rule_no+"_"+version_no+".txt");
    	if(!file.exists()){//版本号
    		SealBody sealbody=sealbody_srv.getSealBodyID(gaizhang.getSeal_id());
        	String certName="";
        	String certPwd="";
        	if(gaizhang.getUse_cert() == 1){
        		Cert objcert=cert_srv.getObjByNo(gaizhang.getCert_no());
        		certName=objcert.getCert_name();
        		certPwd=objcert.getCert_psd();
        	}else{
        		if(sealbody.getKey_sn()!=""){
        			Cert objcert = cert_srv.getObjByNo(sealbody.getKey_sn());
            		certName=objcert.getCert_name();
            		certPwd=objcert.getCert_psd();
        		}else{
        			return 5;
        		}
        	} 	
        	if (rule_arg.indexOf("end") != -1) {
    			String temp = rule_arg.substring(rule_arg.indexOf("end"));
    			int len = temp.indexOf(",");
    			String eStr = rule_arg.substring(rule_arg.indexOf("end"), rule_arg
    					.indexOf("end")
    					+ len);
    			rule_arg = rule_arg.replaceFirst(eStr, endStr(eStr, srv_seal,
    					nObjID));
    		} else if (rule_arg.indexOf("jiange") != -1) {
    			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
    			int len = temp.indexOf(",");
    			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1,
    					rule_arg.indexOf("jiange") + len);
    			rule_arg = rule_arg.replaceFirst(jStr, jiangeStr(jStr, srv_seal,
    					nObjID));
    		}
        	StringBuffer s1=new StringBuffer();
        	s1.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\r\n");
        	s1.append("<rules>").append("\r\n");
        	s1.append("<rule_no>");
        	s1.append(gaizhang.getRule_no());
        	s1.append("</rule_no>").append("\r\n");
        	s1.append("<seal_name>");
        	s1.append(sealbody.getSeal_name());
        	s1.append("</seal_name>").append("\r\n");
        	s1.append("<cert_name>");
        	s1.append(certName);
        	s1.append("</cert_name>").append("\r\n");
        	s1.append("<cert_pwd>");
        	s1.append(certPwd);
        	s1.append("</cert_pwd>").append("\r\n");
        	s1.append("<rule_desc>");
        	s1.append(rule_arg);
        	s1.append("</rule_desc>").append("\r\n");
        	s1.append("</rules>").append("\r\n");
        	logger.info(s1.toString());
        	byte b[] = s1.toString().getBytes();
    		 //将规则信息保存到指定目录
    		FileOutputStream ou = new FileOutputStream(new
    		File(bpath+"doc\\rules\\"+rule_no+"_"+version_no+".txt"));
    		ou.write(b);
    		ou.close();
    		logger.info("写入规则信息成功！");
    		version_no--;
    		File delfile = new File(bpath+"doc\\rules\\"+rule_no+"_"+version_no+".txt");
    		delfile.delete();
    	}
    	return 0;
    }

	@Override
	public int updRule(GaiZhangRule obj) throws Exception {
		rule_dao.upateGaiZhangRule(obj);
		return 1;
	}

	@SuppressWarnings("unchecked")
	public PageSplit showRuleBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		logger.info("showRuleBySch:"+sql);
		List<GaiZhangRuleVo> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = rule_dao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}

	public String getSqlByForm(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(GaiZhangRuleUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if(chk(f.getRule_name())){
			sb.append(" and ").append(GaiZhangRuleUtil.RULE_NAME).append(" like '%").append(f.getRule_name()).append("%'");
		}
		if(chk(f.getRule_state())){
			if(!f.getRule_state().equals("0")){
				sb.append(" and ").append(GaiZhangRuleUtil.RULE_STATE).append(" ='").append(f.getRule_state()).append("'");
			}
		}
		return sb.toString();
	}

	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}
	
	@SuppressWarnings("unchecked")
	private List<GaiZhangRuleVo> listObjs(String sql) throws Exception {
		List<GaiZhangRuleVo> list_obj = new ArrayList<GaiZhangRuleVo>();
		List<Map> list = rule_dao.select(sql);
		for (Map map : list) {
			GaiZhangRule obj = new GaiZhangRule();
			obj = (GaiZhangRule) DaoUtil.setPo(obj, map, new GaiZhangRuleUtil());
			list_obj.add(poToVo(obj));
		}
		return list_obj;
	}


	// 注销规则
	public void ruleZhuxiao(String no) throws Exception {
		rule_dao.zhuxiaoGaiZhangRule(no);
	}

	// 激活规则
	public void rulejihuo(String no) throws Exception {
		rule_dao.jihuoGaiZhangRule(no);
	}

	public void plDelRule(String selStr) throws Exception {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(GaiZhangRuleUtil.TABLE_NAME).append(" where 1=2 ");
		for (String str : r_nos) {
			sb.append(" or ").append(GaiZhangRuleUtil.RULE_NO);
			sb.append("='").append(str).append("'");
		}
		rule_dao.delete(sb.toString());
	}

	public GaiZhangRuleVo getRuleVo(String no) throws Exception {
		GaiZhangRule obj = getRule(no);
		return poToVo(obj);
	}

	private GaiZhangRuleVo poToVo(GaiZhangRule obj) throws Exception {
		GaiZhangRuleVo vo = new GaiZhangRuleVo();
		BeanUtils.copyProperties(vo, obj);
		//获取印章名称
		SealBody sealbody = sealbody_srv.getSealBodys(Integer.parseInt(obj.getSeal_id()));
		if(sealbody!=null){
			vo.setSeal_name(sealbody.getSeal_name());
		}else{
			vo.setSeal_name("");
		}
		return vo;
	}


	
	public GaiZhangRuleDaoImpl getRule_dao() {
		return rule_dao;
	}

	public void setRule_dao(GaiZhangRuleDaoImpl rule_dao) {
		this.rule_dao = rule_dao;
	}
	
	public GaiZhangRuleUtil getTable() {
		return table;
	}

	public void setTable(GaiZhangRuleUtil table) {
		this.table = table;
	}


	public SealBodyServiceImpl getSealbody_srv() {
		return sealbody_srv;
	}


	public void setSealbody_srv(SealBodyServiceImpl sealbody_srv) {
		this.sealbody_srv = sealbody_srv;
	}


	public CertSrv getCert_srv() {
		return cert_srv;
	}


	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	  /**
	 * 定时（每隔1小时）把规则信息保存到内存中
	 * 
	 * @param 
	 * @return
	 */
    public String GetRuleListP()throws Exception{
    	logger.info("重新加载规则信息");
        GetRuleList getrule = (GetRuleList) getBean("objRuelList");
        logger.info("之前的map:"+getrule.getMap().size());
		getrule.cleanMap();// 先清空内存
		logger.info("清空map");
		String sql = "select * from " + GaiZhangRuleUtil.TABLE_NAME;
		List<GaiZhangRule> gaizhang = rule_dao.showAllGaiZhangRule(sql);
		Map<String,GetRules> map=new HashMap<String,GetRules>();
		try {		
			if(gaizhang.size()>0){
				for (GaiZhangRule obj : gaizhang) {
					Cert objcert = new Cert();
					SealBody sealbody = sealbody_srv
							.getSealBodyID(obj.getSeal_id());
					if (obj.getUse_cert() == 1) {
						objcert = cert_srv.getObjByNo(obj.getCert_no());
					} else {
						objcert = cert_srv.getObjByNo(sealbody.getKey_sn());
					}
					GetRules objRules = new GetRules();
					objRules.setRule_no(obj.getRule_no());
					objRules.setSeal_name(sealbody.getSeal_name());
					objRules.setCert_name(objcert.getCert_name());
					objRules.setCert_pwd(objcert.getCert_psd());
					objRules.setRule_desc(obj.getArg_desc());
					objRules.setRule_type(obj.getRule_type());
					logger.info("rule_no:"+obj.getRule_no());
					logger.info("seal_name:"+sealbody.getSeal_name());
					map.put(obj.getRule_no(), objRules);
					getrule.setMap(map);
					logger.info("添加成功");
					logger.info("添加成功map:"+getrule.getMap().size());
				 }
			  }
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "1";
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			return "0";
    	}
    
    @Override
	public String getRuleArgLinux(Integer rule_type,String arg_desc,int pagesize) throws Exception {
		String[] arg = arg_desc.split(",");
		StringBuffer sb = new StringBuffer();
		if (false) {
		} else if (rule_type == 1) {// 绝对坐标
			if(arg[0].equals("-1")){//如果是盖到最后一页-1那么就把最后一页赋给arg[0]
				arg[0]=pagesize+""; 
			}
			sb.append(jianYi(arg[0])).append(",");// 盖在第几页
			sb.append(arg[1]).append(",");// 横坐标
			sb.append("4,");// 
			sb.append(arg[2]).append(",");
		} else if (rule_type == 2) {// 书签
			sb.append("AUTO_ADD:0,-1,0,0,1,DJ_BMK_AUTO_SET_");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 3) {// 骑缝章
			if(pagesize==1){
				return "yiyeqifeng";//一页骑缝不加盖
			}
		// pagesData="0,20000,5,3,50,1,2,"+seal_data;//3.骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			if(pagesize==1){
				sb.append("1").append(",");// 文档一页，第一页1%
			}else if(pagesize>10){
				sb.append("20").append(",");// 第一页20%
			}else{
				int per = 100/pagesize;
				sb.append(per).append(",");//
			}
			sb.append(qiFengYe(arg[3], arg[4])).append(",");
		} else if (rule_type == 4) {// 文字-覆盖
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",");// 结束页
			sb.append(arg[3]).append(",");//上下偏移量
			sb.append(arg[4]).append(",");//左右偏移量
			sb.append("255,");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 5) {// 文字-后面
			sb.append("AUTO_ADD:").append(jianYi(arg[1])).append(",");// 起始页
			sb.append(jianYi(arg[2])).append(",50000,");// 结束页
			if(arg.length==4){
				sb.append(arg[3]).append(",");//上下偏移量
			}else{
				sb.append("0").append(",");//上下偏移量
			}
			sb.append("255,");
			sb.append(arg[0]).append(")|(4,");
		} else if (rule_type == 6) {// 多面绝对坐标
			// arg[0]:1奇数，2偶数,3指定间隔； arg[1]:间隔数; arg[2]:横坐标； arg[3]:纵坐标
			sb.append("2".equals(arg[0]) ? 1 : 0).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("5,");// 骑缝章类型
			sb.append("5,");// 内部骑缝
			sb.append(arg[3]).append(",");// 纵坐标
			sb.append(arg[0] + "jiange" + arg[1]).append(",");
			System.out.println("多面绝对坐标"+sb.toString());
		} else if (rule_type == 7) {// 多面骑缝章
			// arg[0]:0单面，10双面; arg[1]:3右骑缝; arg[2]:坐标； arg[3]:起始页; arg[4]:结束页;
			// arg[5]:间隔数
			sb.append(jianYi(arg[3])).append(",");// 盖章起始页
			sb.append(arg[2]).append(",");// 坐标
			sb.append("0".equals(arg[0]) ? "5" : "6").append(",");// 骑缝章类型
			sb.append(arg[1]).append(",");// 骑缝章方向
			sb.append("50").append(",");// 百分之五十
			sb.append("3jiange" + arg[5]).append(",");
			System.out.println(sb.toString());
		} else if (rule_type == 8) {// 手动盖章
		}
		String rule_arg = sb.toString();
//		if (rule_arg.indexOf("jiange") != -1) {
//			//System.out.println("rule_arg:"+rule_arg);
//			String temp = rule_arg.substring(rule_arg.indexOf("jiange"));
//			//System.out.println("temp:"+temp);
//			int len = temp.indexOf(",");
//			//System.out.println("len:"+len);
//			String jStr = rule_arg.substring(rule_arg.indexOf("jiange") - 1, rule_arg.indexOf("jiange")+ len);
//			//System.out.println("jStr:"+jStr);
//			//System.out.println("pagesize:"+pagesize);
//			rule_arg = rule_arg.replaceFirst(jStr, jiangeStr(jStr,pagesize));
//			//System.out.println("rule_arg:"+rule_arg);
//		}
		return rule_arg;
	}
    
}
