package com.dj.seal.modelFileRule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.gaizhangRule.dao.impl.GaiZhangRuleDaoImpl;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.modelFile.dao.impl.ModelFileDaoImpl;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFileRule.dao.impl.ModelFileRuleDaoImpl;
import com.dj.seal.modelFileRule.form.ModelFileRuleForm;
import com.dj.seal.modelFileRule.po.ModelFileRulePO;
import com.dj.seal.modelFileRule.service.api.IModelFileRuleService;
import com.dj.seal.modelFileRule.vo.ModelFileRuleVO;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.ModelFileRuleUtil;
import com.dj.seal.util.web.SearchForm;

public class ModelFileRuleServiceImpl implements IModelFileRuleService {
	
	static Logger logger = LogManager.getLogger(ModelFileRuleServiceImpl.class.getName());
	
	private  ModelFileRuleDaoImpl modelFileRuleDao ;
	private  ModelFileDaoImpl modelFileDao;
	private  GaiZhangRuleDaoImpl gaiZhangRuleDao; 

	public ModelFileDaoImpl getModelFileDao() {
		return modelFileDao;
	}

	public void setModelFileDao(ModelFileDaoImpl modelFileDao) {
		this.modelFileDao = modelFileDao;
	}

	public GaiZhangRuleDaoImpl getGaiZhangRuleDao() {
		return gaiZhangRuleDao;
	}

	public void setGaiZhangRuleDao(GaiZhangRuleDaoImpl gaiZhangRuleDao) {
		this.gaiZhangRuleDao = gaiZhangRuleDao;
	}

	public ModelFileRuleDaoImpl getModelFileRuleDao() {
		return modelFileRuleDao;
	}

	public void setModelFileRuleDao(ModelFileRuleDaoImpl modelFileRuleDao) {
		this.modelFileRuleDao = modelFileRuleDao;
	}

	@Override
	public int addModelFileRule(ModelFileRuleForm f) throws DAOException {
		
		try {
			ModelFileRulePO obj = new ModelFileRulePO();
			SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
			String id = time.format(new Date()) + "-"
					+ UUID.randomUUID().toString();
				
			f.setMid(id);	
			BeanUtils.copyProperties(obj, f);
		
			modelFileRuleDao.addModelFileRule(obj);
			return 0;
		} catch (Exception e) {
			return 1;
		}
		
		
	}

	@Override
	public int delModelFileRuleDao(String mid) throws DAOException {
		try {
			modelFileRuleDao.delModelFileRule(mid);
			return 0;
		} catch (Exception e) {
			return 1;
		}
		
	}

	@Override
	public ModelFileRulePO getModelFileRuleByMid(String mid)
			throws DAOException {
		
		
		return modelFileRuleDao.getModelFileRuleById(mid);
	}

	@Override
	public List<ModelFileRulePO> showAllModelFileRules() throws DAOException {
		return null;
	}

	@Override
	public int updateModelFileRule(ModelFileRulePO medelFilleRule) throws DAOException {
		try {
			modelFileRuleDao.updateModelFileRule(medelFilleRule);
			return 0;
		} catch (Exception e) {
			return 1;
		}
		
	}
	
	public String getSqlByForm(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(ModelFileRuleUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		
		if(chk(f.getModel_id())){
			sb.append(" and ").append(ModelFileRuleUtil.MODEL_ID).append(" = '").append(f.getModel_id()).append("'");
		}
		if(chk(f.getRule_no())){
			sb.append(" and ").append(ModelFileRuleUtil.RULE_NO).append(" = '").append(f.getRule_no()).append("'");
		}
		sb.append(" order by ").append(ModelFileRuleUtil.MODEL_ID);
		return sb.toString();
	}
	
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}
	
	
	public PageSplit showMFRBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		//logger.info("showDicBySch:"+sql);
		List<ModelFileRuleVO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = modelFileRuleDao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	@SuppressWarnings("unchecked")
	private List<ModelFileRuleVO> listObjs(String sql) throws Exception {


		List<ModelFileRuleVO> list_obj;
		list_obj = new ArrayList<ModelFileRuleVO>();
		List<Map> list = modelFileRuleDao.select(sql);

		for (Map map : list) {
			ModelFileRulePO obj = new ModelFileRulePO();
			
			
			obj = (ModelFileRulePO) DaoUtil
					.setPo(obj, map, new ModelFileRuleUtil());
			list_obj.add(poToVo(obj));
		}
		return list_obj;
	}
	
	private ModelFileRuleVO poToVo(ModelFileRulePO obj) throws Exception {
		ModelFileRuleVO vo = new ModelFileRuleVO();
		BeanUtils.copyProperties(vo, obj);
		
		try {
			//模板名称
			ModelFile modelFile = modelFileDao.getModelFileById(Integer
					.parseInt(obj.getModel_id().toString()));
			//规则名称
			GaiZhangRule gaiZhangRule = gaiZhangRuleDao.getGaiZhangRuleById(obj
					.getRule_no());
			if (modelFile != null) {
				vo.setModel_name(modelFile.getModel_name());
			} else {
				vo.setModel_name("");
			}
			if (gaiZhangRule != null) {
				vo.setRule_name(gaiZhangRule.getRule_name());
			} else {
				vo.setRule_name("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return vo;
	}
	
	public boolean isModelFileRuleExist(String mode_id,String rule_no){
		
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(ModelFileRuleUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileRuleUtil.MODEL_ID);
		sb.append(" = '").append(mode_id).append("' and ");
		sb.append(ModelFileRuleUtil.RULE_NO).append(" ='").append(rule_no).append("'");
		
		
		logger.info(sb.toString()+"==判断模板规则是否重复");
		int num = modelFileRuleDao.count(sb.toString());
		
		
		if(num==0){
			return false;
		}
		
		return true;
	}

	

}
