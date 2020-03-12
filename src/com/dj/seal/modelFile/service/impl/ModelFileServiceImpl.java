package com.dj.seal.modelFile.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import serv.rules.GetRuleList;
import serv.rules.GetRules;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.gaizhangRule.dao.impl.GaiZhangRuleDaoImpl;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.hotel.po.MasterplateRole;
import com.dj.seal.hotel.service.impl.VersionServiceImpl;
import com.dj.seal.modelFile.dao.api.ModelFileDao;
import com.dj.seal.modelFile.po.ModelDept;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.po.ModelXieyi;
import com.dj.seal.modelFile.service.api.IModelFileService;
import com.dj.seal.modelFile.vo.ModelFileVo;
import com.dj.seal.modelFile.vo.ModelXieyiVo;
import com.dj.seal.organise.dao.impl.SysDepartmentDaoImpl;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.encrypt.Base64;
import com.dj.seal.util.spring.MyApplicationContextUtil;
import com.dj.seal.util.table.GaiZhangRuleUtil;
import com.dj.seal.util.table.HotelTmasterplateJudgeUtil;
import com.dj.seal.util.table.HotelTmasterplateTRoleUtil;
import com.dj.seal.util.table.HotelTversionUtil;
import com.dj.seal.util.table.ModelDeptUtil;
import com.dj.seal.util.table.ModelFileUtil;
import com.dj.seal.util.table.ModelXieyiUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.web.SearchForm;
import com.mysql.jdbc.Clob;

public class ModelFileServiceImpl implements IModelFileService {
	
	static Logger logger = LogManager.getLogger(ModelFileServiceImpl.class.getName());

	private UserService user_srv;
	private ModelFileDao modelFile_dao;
	private BaseDAOJDBC dao;
	private SealBodyServiceImpl sealbody_srv;
	private GaiZhangRuleDaoImpl rule_dao;
	private CertSrv cert_srv;
	private VersionServiceImpl version_srv;
	private SysDepartmentDaoImpl sysDepartmentDao;

	//用于模板添加时，检查模板名称是否存在
	public boolean isExist(String name) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_NAME);
		sb.append("='").append(name).append("'");
		int num=dao.count(sb.toString());
		if(num==0){
			return false;
		}
		return true;
	}
	
	//判断是否为需要二次签字的模板 是则返回true
	public boolean isFlowModeFile(String id) throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("select ").append(ModelFileUtil.ISFLOW);
		sb.append(" from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_ID);
		sb.append("='").append(id).append("'");
		sb.append(" and ").append(ModelFileUtil.ISFLOW).append("='1'");
		int num=dao.count(sb.toString());
		if(num==0){
			return false;
		}
		return true;
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
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	/**
	 * 定时（每隔1小时）把模板数据保存到本地
	 * 
	 * @param 
	 * @return
	 */
    public void selBakModel()throws Exception{
    	//logger.info("定时开始");
    	dbToFile();
    	GetRuleListP();
    }
    /**
	 * 定时（每隔1小时）把规则信息保存到内存中
	 * 
	 * @param 
	 * @return
	 */
    public void GetRuleListP()throws Exception{
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
				// TODO Auto-generated catch block
			//	logger.error(e.getMessage());
			}
  	}
    public static void main(String str[]) throws Exception{
    	ModelFileServiceImpl obj=new ModelFileServiceImpl();
    	obj.dbToFile();
    }
	/**
	 * 把存在数据库中的文件拿出放回本地路径下
	 * 
	 * @param obj
	 * @return
	 */
	public void dbToFile() throws Exception {
		logger.info("定时写模板");
		String bpath = bpath();
		String file_path=bpath+"upload/";
		String sql="select "+ModelFileUtil.MODEL_NAME+" from "+ModelFileUtil.TABLE_NAME;
		List<ModelFile> list = modelFile_dao.getmodelFileList(sql);
		if (list.size()>0) {
			for (ModelFile modelFile : list) {
				String modelName=modelFile.getModel_name();
				FileOutputStream fout = new FileOutputStream(file_path+modelName+".aip");
				StringBuffer sb = new StringBuffer();
				sb.append("select ").append(ModelFileUtil.CONTENT_DATA);
				sb.append(" from ").append(ModelFileUtil.TABLE_NAME);
				sb.append(" where ").append(ModelFileUtil.MODEL_NAME);
				sb.append("='"+modelName+"'");
				if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){
				CLOB clob = dao.getClob(sb.toString(), ModelFileUtil.CONTENT_DATA);
				InputStream input = clob.getAsciiStream();
		        int len = (int)clob.length();
		        byte[] by = new byte[len];
		        int i ;
		        while(-1 != (i = input.read(by, 0, by.length))) {
		          input.read(by, 0, i);
		        }
				fout.write(by, 0, by.length);
				fout.close();
				logger.info("写"+modelName+".aip成功");
				}else if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){
					Clob clob = dao.getMysqlClob(sb.toString(),ModelFileUtil.CONTENT_DATA);
					InputStream input = clob.getAsciiStream();
		            int len = (int)clob.length();
		            byte[] by = new byte[len];
		            int i ;
		            while(-1 != (i = input.read(by, 0, by.length))) {
		              input.read(by, 0, i);
		            }
		            String model_data = new String(by);
					byte[] baseV = Base64.decode(model_data);
					fout.write(baseV, 0, baseV.length);
					fout.close();
					logger.info("写"+modelName+".aip成功");
				}
			}
			
		}
	}
	public void dbToFileS(ModelFile obj,String file_path) throws Exception {
		FileOutputStream fout = new FileOutputStream(file_path);
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(ModelFileUtil.CONTENT_DATA);
		sb.append(" from ").append(ModelFileUtil.TABLE_NAME+" where "+ModelFileUtil.MODEL_NAME+"='"+obj.getModel_name()+"'");
		logger.info("sql:"+sb.toString());
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			CLOB clob = dao.getClob(sb.toString(), ModelFileUtil.CONTENT_DATA);
			InputStream input = clob.getAsciiStream();
			int len = (int) clob.length();
			byte[] by = new byte[len];
			int i;
			while (-1 != (i = input.read(by, 0, by.length))) {
				input.read(by, 0, i);
			}
			String model_data = new String(by);
			byte[] baseV = Base64.decode(model_data);
			fout.write(baseV, 0, baseV.length);
			fout.close();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			Clob clob = dao.getMysqlClob(sb.toString(),
					ModelFileUtil.CONTENT_DATA);
			InputStream input = clob.getAsciiStream();
			int len = (int) clob.length();
			byte[] by = new byte[len];
			int i;
			while (-1 != (i = input.read(by, 0, by.length))) {
				input.read(by, 0, i);
			}
			String model_data = new String(by);
			byte[] baseV = Base64.decode(model_data);
			fout.write(baseV, 0, baseV.length);
			fout.close();
		}
	}
	@Override
	public int addModelFile(ModelFile obj) throws Exception{
		try {
			int model_id = modelFile_dao.addModelFile(obj);
			if(obj.getIshotel()!=null&&obj.getIshotel().equals("1")){
				//如果是无纸化系统模板，则添加模板角色
				if(obj.getRole_nos()!=null&&!obj.getRole_nos().equals("")){
					String[] role_nos = obj.getRole_nos().split(",");
					for(String role_no : role_nos){
						MasterplateRole mpr = new MasterplateRole();
						SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
						String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
						mpr.setId(id);
						mpr.setMatercid(Integer.toString(model_id));
						mpr.setRoleid(role_no);
						String sql = ConstructSql.composeInsertSql(mpr, new HotelTmasterplateTRoleUtil());
						dao.add(sql);
					}
				}
				version_srv.add(Integer.toString(model_id));
			}
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}


	@Override
	public int delModelFile(Integer id) throws Exception {
		try {
			modelFile_dao.deleteModelFile(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public int delHotelModelFile(Integer id) throws Exception {
		try {
			modelFile_dao.deleteModelFile(id);
			String roledeletesql = "delete from "+HotelTmasterplateTRoleUtil.TABLE_NAME+" where "+HotelTmasterplateTRoleUtil.MATERCID+"='"+id+"'";
			dao.delete(roledeletesql);
			String judgedeletesql = "delete from "+HotelTmasterplateJudgeUtil.TABLE_NAME+" where "+HotelTmasterplateJudgeUtil.MASTER_PLATECID+"='"+id+"'";
			dao.delete(judgedeletesql);
			version_srv.deleteByMasterplateId(Integer.toString(id));
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	//注销模板
	public int zhuxiaoModelFile(Integer id) throws Exception {
		try {
			modelFile_dao.zhuxiaoModelFile(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	//激活模板
	public int jihuoModelFile(Integer id) throws Exception {
		try {
			modelFile_dao.jihuoModelFile(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public PageSplit showObjBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" order by ").append(ModelFileUtil.CREATE_TIME);
			sb.append(" desc");
			String sql = DBTypeUtil.splitSql(sb.toString(), pageIndex, pageSize,
					Constants.DB_TYPE);
			List<ModelFileVo> datas = listObjs(sql);
			ps.setDatas(datas);
			ps.setNowPage(pageIndex);
			ps.setPageSize(pageSize);
			int totalCount = dao.count(sb.toString());
			ps.setTotalCount(totalCount);
		} catch (Exception e) {
			logger.error(e.getMessage());// TODO: handle exception
		}
		return ps;
	}
	//将modelDept表添加数据
	public void AddFeiqiXieyi(Integer id,String dept_no ){
		ModelDept modeldept=new ModelDept();
		modeldept.setDept_no(dept_no);
		modeldept.setModel_id(id);
		String sql = ConstructSql.composeInsertSql(modeldept, new ModelDeptUtil());
		dao.add(sql);
		
	}
	/**
	 * 查找模版或协议，按照条件
	 * @param pageIndex
	 * @param pageSize
	 * @param modelorxieyi  0 模版 1协议
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public PageSplit showObjBySch(int pageIndex, int pageSize,String modelorxieyi,String dept_no,String user_no,String model_name,String status, SearchForm f)
	throws Exception {
		PageSplit ps = new PageSplit();
		try {
			String dpet_no_ori=dept_no;
			StringBuffer sb=new StringBuffer();
			sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" where "+ModelFileUtil.MODELORXIEYI+" ='"+modelorxieyi+"' ");
			if(user_no!= null && !"".equals(user_no)){
				if(status.equals("0")){
					if(!user_no.equals("admin")){
						sb.append(" and ").append(ModelFileUtil.APPROVER).append(" = '")
						.append(user_no).append("'");
					}
				}/*if(status.equals("1")){
					if(!user_no.equals("admin")){
						sb.append(" and ").append(ModelFileUtil.CREATE_USER).append(" = '")
						.append(user_no).append("'");
					}
				}	*/			
			}
			if(model_name!= null && !"".equals(model_name)){
				sb.append(" and ").append(ModelFileUtil.MODEL_NAME).append(" = '")
				.append(model_name).append("'");
			}
			if(dept_no!=null&&!dept_no.equals("")){
				sb.append(" and ").append(ModelFileUtil.DEPT_NAME+" like'%"+dept_no+"%' ");
			}
			if(status!=null&&!status.equals("")){
				sb.append(" and ").append(ModelFileUtil.APPROVE_STATUS).append(" = '")
				.append(status).append("'");
			}
			sb.append(" and ( 1=1  ");
			sb.append(")");
			sb.append(" order by ").append(ModelFileUtil.CREATE_TIME);
			sb.append(" desc");
			logger.info("+----"+sb.toString());
			String sql = DBTypeUtil.splitSql(sb.toString(), pageIndex, pageSize,
					Constants.DB_TYPE);
			List<ModelFileVo> datas = listObjs(sql,dpet_no_ori);
			ps.setDatas(datas);
			ps.setNowPage(pageIndex);
			ps.setPageSize(pageSize);
			int totalCount = dao.count(sb.toString());
			ps.setTotalCount(totalCount);
		} catch (Exception e) {
			logger.error(e.getMessage());// TODO: handle exception
		}
		return ps;
	}

	@SuppressWarnings("unchecked")
	private List<ModelFileVo> listObjs(String sql){
		List<ModelFileVo> list_obj = new ArrayList<ModelFileVo>();
		List<Map> list = dao.select(sql);
		for (Map map : list) {
			ModelFile obj = new ModelFile();
			obj = (ModelFile) DaoUtil.setPo(obj, map, new ModelFileUtil());
			obj.setCreateTime(obj.getCreate_time().toString());
			obj.setModifyTime(obj.getModify_time().toString());			
			list_obj.add(poToVo(obj));
		}
		return list_obj;
	}
	private List<ModelFileVo> listObjs(String sql,String dept_no) throws Exception {
		List<ModelFileVo> list_obj = new ArrayList<ModelFileVo>();
		List<Map> list = dao.select(sql);
		for (Map map : list) {
			ModelFile obj = new ModelFile();
			obj = (ModelFile) DaoUtil.setPo(obj, map, new ModelFileUtil());
			obj.setCreateTime(obj.getCreate_time().toString());
			obj.setModifyTime(obj.getModify_time().toString());		
			ModelFileVo vo=poToVo(obj);
			ModelDept modeldept=getModelDept(obj.getModel_id(), dept_no);
			if(modeldept==null){
				vo.setFeiqi_status("0");
			}else{
				vo.setFeiqi_status("1");
			}
			list_obj.add(vo);
		}
		return list_obj;
	}
	
	public ModelDept getModelDept(Integer id,String dept_no){
		ModelDept obj=null;
		String sql="select * from "+ModelDeptUtil.TABLE_NAME+" where "+ModelDeptUtil.DEPT_NO+" ='"+dept_no+"' and "+ModelDeptUtil.MODEL_ID+" ='"+id+"'";
		List<Map> list = dao.select(sql);
		for (Map map : list) {
			obj = new ModelDept();
			obj = (ModelDept) DaoUtil.setPo(obj, map, new ModelDeptUtil());
		}
		return obj;
	}

	@Override
	public ModelFileVo getModelFile(Integer id) throws Exception {
		if(id==null){
			return null;
		}
		ModelFile obj = modelFile_dao.getModelFileById(id);
		return poToVo(obj);
	}
	
	/**
	 * in 查询模板 
	 * add by liuph
	 * 20171220
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<ModelFile> getModelFileRW(String ids)throws Exception {
		String sql = "select a.c_ia,a.c_ib,b.cversionno as model_version from "+ModelFileUtil.TABLE_NAME+" a,"+HotelTversionUtil.TABLE_NAME+" b where a.c_ia = b.masterplateid and a."+ModelFileUtil.MODEL_ID+" in("+ids+")";
		List<Map> list = modelFile_dao.selects(sql);//新增
		List<ModelFile> ModelFiles = new ArrayList<ModelFile>();
		for(Map object:list){
			ModelFile obj = new ModelFile();
			obj = (ModelFile) DaoUtil.setPo(obj, object, new ModelFileUtil());
			ModelFiles.add(obj);
		}
		//List<ModelFile> ModelFiles = modelFile_dao.getModelFilesBySql(sql);
		return ModelFiles;
	}
	
	
	/**
	 * 根据版本文件的版本号和模板名称下载模板
	 * @param deptid 部门机构号
	 * @param name   模板名称
	 * @return
	 * @throws Exception
	 */
	public ModelFileVo getModelFileByName(String deptid,String name) throws Exception {
		String sql="";
		if(name==null){//name为空，则deptid作为模板ID查找模板(模板审批查看模板时使用)
			String modelId=deptid.toString();
			sql = "select * from " + ModelFileUtil.TABLE_NAME + " where "
					+ ModelFileUtil.MODEL_ID + "='" + modelId + "'";
		}else{
			SysDepartment dept = sysDepartmentDao.getDeptByBankId(deptid);
			if(dept == null){
				return null;
			}		
			SysDepartment villageDeptNo = sysDepartmentDao.getParentNo(Constants.VILLAGE_DEPT_NO);//虚拟机构
			SysDepartment byparent_No=sysDepartmentDao.getDeptNo(dept.getDept_parent());//根据父部门编号向上查询信息	
			while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
				byparent_No=sysDepartmentDao.getDeptNo(byparent_No.getDept_parent());//父部门编号作为部门编号查询
			}
			String deptno = dept.getDept_no();
			String deptParent = "";
			int mark = 4;
			String deptParentl = "";
			for (int i = 0; i < deptno.length() / mark; i++) {
				deptParent = deptno.substring(0, (i + 1) * mark);
				deptParentl += "'" + deptParent + "',";
			}
			if (!"01".equals(byparent_No.getBank_dept())){//村镇银行新增虚拟机构判断是否有模板
				deptParentl+="'"+villageDeptNo.getDept_no()+"',";
			}	
			deptParent = deptParentl.substring(0, deptParentl.length() - 1);
			logger.info("getModelFileByName:"+name);
			sql = "select * from " + ModelFileUtil.TABLE_NAME + " where "
					+ ModelFileUtil.MODEL_NAME + "='" + name + "'"+" and "+ModelFileUtil.DEPT_NO+" in ("+deptParent+")";
		}
		logger.info(sql);
		ModelFile obj = modelFile_dao.getModelFileBySql(sql);
		return poToVo(obj);
	}

	//显示所有模板(去除协议)  modelorxieyi属性值为0
	@Override
	public List<ModelFileVo> showModelFiles() throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_STATE).append("='1'");
		sb.append(" and ").append(ModelFileUtil.MODELORXIEYI).append("='0'");
		return listObjs(sb.toString());
	}
	
	public List<ModelFileVo> showModelFiles(String dept_no,String modelorxieyi){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_STATE).append("='1'");
		sb.append(" and ").append(ModelFileUtil.MODELORXIEYI).append("='"+modelorxieyi+"'");
		sb.append(" and ( ");
		if(dept_no!=null&&!dept_no.equals("")){
			sb.append(ModelFileUtil.DEPT_NO+" like '"+dept_no+"%' ");
			int num=dept_no.length()/4-1;
			for(int i=0;i<num;i++){
				dept_no=dept_no.substring(0, dept_no.length()-3);
				sb.append(" or "+ModelFileUtil.DEPT_NO+" ='"+dept_no+"' ");
			}
		}
		sb.append(")");
		return listObjs(sb.toString());
	}
	//从模版协议表根据协议找到关联的模版
	public List<ModelFileVo> showModelFilesbyXieyi(Integer xieyi_id)  {
		String sql="select * from "+ModelXieyiUtil.TABLE_NAME+" where "+ModelXieyiUtil.XIEYI_ID+" ='"+xieyi_id+"'";
		List<Map> maps=dao.select(sql);
		List<ModelXieyi> modelXieyis=new ArrayList<ModelXieyi>();
		for (Map map : maps) {
			ModelXieyi obj = new ModelXieyi();
			obj = (ModelXieyi) DaoUtil.setPo(obj, map, new ModelXieyiUtil());
			modelXieyis.add(obj);
		}
		if(modelXieyis.size()<=0){
			return null;
		}
		ModelXieyi modelxieyi=modelXieyis.get(0);
		StringBuffer ids=new StringBuffer("");
		ids.append("'"+modelxieyi.getModel_id()+"'");
		for(int i=1;i<modelXieyis.size();i++){
			modelxieyi=modelXieyis.get(i);
			ids.append(",'"+modelxieyi.getModel_id()+"'");
		}
		StringBuffer sb=new StringBuffer();
		sb.append("select * from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_STATE).append("='1'");
		sb.append(" and "+ModelFileUtil.MODELORXIEYI+" ='0' ");
		sb.append(" and "+ModelFileUtil.MODEL_ID+" in("+ids.toString()+")");

		return listObjs(sb.toString());
	}
	public void setModelFileXieyi(Integer xieyi_id,String model_ids,String xieyi_deptno){
		String sql="delete from "+ModelXieyiUtil.TABLE_NAME+" where "+ModelXieyiUtil.XIEYI_DEPT_NO+" ='"+xieyi_deptno+"' and "+ModelXieyiUtil.XIEYI_ID+" ='"+xieyi_id+"'";
		dao.delete(sql);
		if(model_ids!=null&&!model_ids.equals("")){
			String[] ids=model_ids.split(",");
			if(ids.length>0){
				ModelXieyi obj=new ModelXieyi();
				obj.setXieyi_id(xieyi_id);
				obj.setXieyi_dept_no(xieyi_deptno);
				for(int i=0;i<ids.length;i++){
					obj.setModel_id(Integer.valueOf(ids[i]));
					modelFile_dao.addModelXieyi(obj);
				}
			}			
		}
	}
	@Override
	public int updModelFile(ModelFile obj) throws Exception{
		obj.setModel_state("1");
		modelFile_dao.updateModelFile(obj);
		String path=Constants.getProperty("savepdf") + "/"+obj.getModel_name() + ".aip";
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		if(obj.getIshotel()!=null&&obj.getIshotel().equals("1")){
			//如果是无纸化系统模板，则更新模板角色表
			int model_id = obj.getModel_id();
			String deletesql = "delete from "+HotelTmasterplateTRoleUtil.TABLE_NAME+" where "+HotelTmasterplateTRoleUtil.MATERCID+"='"+model_id+"'";
			dao.delete(deletesql);
			if(obj.getRole_nos()!=null&&!obj.getRole_nos().equals("")){
				String[] role_nos = obj.getRole_nos().split(",");
				for(String role_no : role_nos){
					MasterplateRole mpr = new MasterplateRole();
					SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
					String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
					mpr.setId(id);
					mpr.setMatercid(Integer.toString(model_id));
					mpr.setRoleid(role_no);
					String sql = ConstructSql.composeInsertSql(mpr, new HotelTmasterplateTRoleUtil());
					dao.add(sql);
				}
			}
			version_srv.update(Integer.toString(model_id));
		}
		return 1;
	}


	public ModelFileVo poToVo(ModelFile obj){
		if(obj==null){
			return null;
		}
		ModelFileVo objVo = new ModelFileVo();
		try {
			BeanUtils.copyProperties(objVo, obj);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
//		SysUser suser = user_srv.showSysUserByUser_id(obj.getCreate_user());
//		if(suser!=null){
//			objVo.setCreate_username(suser.getUser_name());
//		}else{
//			objVo.setCreate_username("");
//		}
		return objVo;
	}
	public ModelXieyiVo poToVo(ModelXieyi obj){
		if(obj==null){
			return null;
		}
		ModelXieyiVo objVo = new ModelXieyiVo();
		try {
			BeanUtils.copyProperties(objVo, obj);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		try {
			ModelFileVo file=getModelFile(obj.getXieyi_id());
			objVo.setXieyi_name(file.getModel_name());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return objVo;
	}
	
	public String getRolesByModelId(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(HotelTmasterplateTRoleUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append("='").append(id).append("'");
		List<Map> list = dao.select(sb.toString());
		String role_nos = "";
		String role_names = "";
		if(list.size()==0){
			return null;
		}
		for (Map map : list) {
			String role_no = (String)map.get(HotelTmasterplateTRoleUtil.ROLEID);
			StringBuffer sb2 = new StringBuffer();
			sb2.append("select * from ").append(SysRoleUtil.TABLE_NAME);
			sb2.append(" where ").append(SysRoleUtil.ROLE_ID);
			sb2.append("=").append(role_no);
			List<Map> list2 = dao.select(sb2.toString());
			String role_name = "";
			if(list2.size()==0){
				role_name="已删除";
			}else{
				role_name = (String)list2.get(0).get(SysRoleUtil.ROLE_NAME);
			}
			role_nos += role_no+",";
			role_names += role_name+",";
		}
		return role_nos +"???"+role_names;
	}
	
	
	public String getModelFileIdsByRoleIds(String role_nos) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append(" from ").append(HotelTmasterplateTRoleUtil.TABLE_NAME);
		sb.append(","+ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTmasterplateTRoleUtil.MATERCID).append(" = ").append(ModelFileUtil.MODEL_ID);
		sb.append(" and ").append(ModelFileUtil.MODELORXIEYI+"='0'");
		sb.append(" and ").append(HotelTmasterplateTRoleUtil.ROLEID);
		sb.append(" in(");
		String[] rnos = role_nos.split(",");
		for(String role_no : rnos){
			sb.append("'").append(role_no).append("',");
		}
		sb.append("''");
		sb.append(")");
	//logger.info("getModelFileIdsByRoleIds:"+sb.toString());
		return dao.getStr(sb.toString(), HotelTmasterplateTRoleUtil.MATERCID);
	}
	
	public String getAllModels(String deptid) throws Exception{//获取模板ID
		SysDepartment dept = sysDepartmentDao.getDeptByBankId(deptid);		
		if(dept == null){
			return null;
		}
		SysDepartment villageDeptNo = sysDepartmentDao.getParentNo(Constants.VILLAGE_DEPT_NO);//虚拟机构
		SysDepartment byparent_No=sysDepartmentDao.getDeptNo(dept.getDept_parent());//根据父部门编号向上查询信息	
		while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
			byparent_No=sysDepartmentDao.getDeptNo(byparent_No.getDept_parent());//父部门编号作为部门编号查询
		}
		String deptno = dept.getDept_no();
		String deptParent = "";
		int mark = 4;
		String deptParentl = "";
		for (int i = 0; i < deptno.length() / mark; i++) {
			deptParent = deptno.substring(0, (i + 1) * mark);
			deptParentl += "'" + deptParent + "',";
		}
		
		if (!"01".equals(byparent_No.getBank_dept())){//村镇银行新增虚拟机构判断是否有模板
			deptParentl+="'"+villageDeptNo.getDept_no()+"',";
		}
		deptParent = deptParentl.substring(0, deptParentl.length() - 1);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append(" from ").append(HotelTmasterplateTRoleUtil.TABLE_NAME);
		sb.append(","+ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTmasterplateTRoleUtil.MATERCID).append(" = ").append(ModelFileUtil.MODEL_ID);
		sb.append(" and ").append(ModelFileUtil.MODELORXIEYI+"='0'");
		sb.append(" and ").append(ModelFileUtil.MODEL_STATE+"='1'");
		sb.append(" and ").append(ModelFileUtil.DEPT_NO+" in ("+deptParent+")");
		logger.info("getAllModelsql:"+sb.toString());
		return dao.getStr(sb.toString(), HotelTmasterplateTRoleUtil.MATERCID);
	}
	
	
	/**
	 * 2017-12-21
	 * add by liun
	 * @return
	 * @throws Exception
	 */
	public String getAllModels() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append(" from ").append(HotelTmasterplateTRoleUtil.TABLE_NAME);
		sb.append("," + ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTmasterplateTRoleUtil.MATERCID).append("=").append(ModelFileUtil.MODEL_ID);
		sb.append(" and ").append(ModelFileUtil.MODELORXIEYI+"='0'");
		sb.append(" and ").append(ModelFileUtil.MODEL_STATE+"='1'");
		logger.info("getModelFiledsByRoleIds:" + sb.toString());
		return dao.getStr(sb.toString(), HotelTmasterplateTRoleUtil.MATERCID);
	}
	
	public List<ModelFileVo> getModelFilesByModelXie(List<ModelXieyiVo> modelXieyis){
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from ").append(ModelFileUtil.TABLE_NAME);
		sb.append(" where ").append(ModelFileUtil.MODEL_ID);
		sb.append(" in(");
		for(ModelXieyiVo role_no : modelXieyis){
			sb.append("'").append(role_no.getModel_id()).append("',");
		}
		sb.append("''");
		sb.append(")");
		List<Map> maps=dao.select(sb.toString());
		List<ModelFileVo> objs=new ArrayList<ModelFileVo>();
		for(Map map:maps){
			ModelFile obj=new ModelFile();
			obj=(ModelFile)DaoUtil.setPo(obj, map, new ModelFileUtil());
			objs.add(poToVo(obj));
		}
		return objs;
	}
	public String getXieyiIdsByModelIDs(String masterplateIds,String dept_no) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct ").append(ModelXieyiUtil.XIEYI_ID);
		sb.append(" from ").append(ModelXieyiUtil.TABLE_NAME);
		sb.append(" where ").append(ModelXieyiUtil.MODEL_ID);
		sb.append(" in(");
		String[] modelids = masterplateIds.split(",");
		for(String modelid : modelids){
			sb.append("'").append(modelid).append("',");
		}
		sb.append("''");
		sb.append(")");
		sb.append(" and ( ");
		if(dept_no!=null&&!dept_no.equals("")){
			sb.append(ModelXieyiUtil.XIEYI_DEPT_NO+" like'"+dept_no+"%' ");
			int num=dept_no.length()/4-1;
			for(int i=0;i<num;i++){
				dept_no=dept_no.substring(0, dept_no.length()-4);
				sb.append(" or "+ModelXieyiUtil.XIEYI_DEPT_NO+" ='"+dept_no+"' ");
			}
		}
		sb.append(")");
		sb.append(" and "+ModelXieyiUtil.XIEYI_ID+" not in(select "+ModelDeptUtil.MODEL_ID+" from "+ModelDeptUtil.TABLE_NAME+" where "+ModelDeptUtil.DEPT_NO+" ='"+dept_no+"' )");
	//logger.info("getModelFileIdsByRoleIds:"+sb.toString());
		return dao.getStrOfInt(sb.toString(), ModelXieyiUtil.XIEYI_ID);
	}
	//得到模版协议对应关系
	public List<ModelXieyiVo> getModelXies(String masterplateIds,String dept_no){
		StringBuffer sb = new StringBuffer();
		sb.append("select * ");
		sb.append(" from ").append(ModelXieyiUtil.TABLE_NAME);
		sb.append(" where ").append(ModelXieyiUtil.MODEL_ID);
		sb.append(" in(");
		String[] modelids = masterplateIds.split(",");
		for(String modelid : modelids){
			sb.append("'").append(modelid).append("',");
		}
		sb.append("''");
		sb.append(")");
		sb.append(" and ( ");
		if(dept_no!=null&&!dept_no.equals("")){
			sb.append(ModelXieyiUtil.XIEYI_DEPT_NO+" like'"+dept_no+"%' ");
			int num=dept_no.length()/4-1;
			for(int i=0;i<num;i++){
				dept_no=dept_no.substring(0, dept_no.length()-4);
				sb.append(" or "+ModelXieyiUtil.XIEYI_DEPT_NO+" ='"+dept_no+"' ");
			}
		}
		sb.append(")");
		sb.append(" and "+ModelXieyiUtil.XIEYI_ID+" not in(select "+ModelDeptUtil.MODEL_ID+" from "+ModelDeptUtil.TABLE_NAME+" where "+ModelDeptUtil.DEPT_NO+" ='"+dept_no+"' )");
		sb.append(" order by "+ModelXieyiUtil.MODEL_ID+","+ModelXieyiUtil.XIEYI_ID);
		List<Map> maps=dao.select(sb.toString());
		List<ModelXieyiVo> objs=new ArrayList<ModelXieyiVo>();
		for(Map map:maps){
			ModelXieyi obj=new ModelXieyi();
			obj=(ModelXieyi)DaoUtil.setPo(obj, map, new ModelXieyiUtil());
			objs.add(poToVo(obj));
		}
	//logger.info("getModelFileIdsByRoleIds:"+sb.toString());
		return objs;
	}
	
	public List<ModelFileVo> getEditModelFileByRoleIds(String role_nos) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.* from ");
		sb.append(ModelFileUtil.TABLE_NAME).append(" t1,");
		sb.append(HotelTmasterplateTRoleUtil.TABLE_NAME).append(" t2 ");
		sb.append(" where ");
		sb.append(" t1.").append(ModelFileUtil.MODEL_ID);
		sb.append(" = t2.").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append(" and ").append("t1.").append(ModelFileUtil.PRINTOREDIT).append(" in('1','3')");
		sb.append(" and t2.").append(HotelTmasterplateTRoleUtil.ROLEID);
		sb.append(" in(");
		String[] rnos = role_nos.split(",");
		for(String role_no : rnos){
			sb.append("'").append(role_no).append("',");
		}
		sb.append("''");
		sb.append(")");
		sb.append(" order by t1.").append(ModelFileUtil.CREATE_TIME);
	logger.info("getEditModelFileByRoleIds:"+sb.toString());
		return listObjs(sb.toString());
	}
	
	public List<ModelFileVo> getPrintModelFileByRoleIds(String role_nos) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.* from ");
		sb.append(ModelFileUtil.TABLE_NAME).append(" t1,");
		sb.append(HotelTmasterplateTRoleUtil.TABLE_NAME).append(" t2 ");
		sb.append(" where ");
		sb.append(" t1.").append(ModelFileUtil.MODEL_ID);
		sb.append(" = t2.").append(HotelTmasterplateTRoleUtil.MATERCID);
		sb.append(" and ").append("t1.").append(ModelFileUtil.PRINTOREDIT).append(" in('1','2')");
		sb.append(" and t2.").append(HotelTmasterplateTRoleUtil.ROLEID);
		sb.append(" in(");
		String[] rnos = role_nos.split(",");
		for(String role_no : rnos){
			sb.append("'").append(role_no).append("',");
		}
		sb.append("''");
		sb.append(")");
		sb.append(" order by t1.").append(ModelFileUtil.CREATE_TIME);
	logger.info("getPrintModelFileByRoleIds:"+sb.toString());
		return listObjs(sb.toString());
	}
	
	public void approveModel(String id,String text,String approver,String status){
		ModelFile modelPO=new ModelFile();
		modelPO.setApprove_time(new Timestamp(new java.util.Date().getTime()));
		modelPO.setApprove_reason(text);
		modelPO.setApprover(approver);
		modelPO.setApprove_status(status);
		modelFile_dao.approveModel(modelPO, id);
	}
	
	public ModelFileDao getModelFile_dao() {
		return modelFile_dao;
	}
	public void setModelFile_dao(ModelFileDao modelFile_dao) {
		this.modelFile_dao = modelFile_dao;
	}
	public BaseDAOJDBC getDao() {
		return dao;
	}
	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}
	public UserService getUser_srv() {
		return user_srv;
	}
	public void setUser_srv(UserService user_srv) {
		this.user_srv = user_srv;
	}

	public SealBodyServiceImpl getSealbody_srv() {
		return sealbody_srv;
	}

	public void setSealbody_srv(SealBodyServiceImpl sealbody_srv) {
		this.sealbody_srv = sealbody_srv;
	}

	public GaiZhangRuleDaoImpl getRule_dao() {
		return rule_dao;
	}

	public void setRule_dao(GaiZhangRuleDaoImpl rule_dao) {
		this.rule_dao = rule_dao;
	}

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

	public VersionServiceImpl getVersion_srv() {
		return version_srv;
	}

	public void setVersion_srv(VersionServiceImpl version_srv) {
		this.version_srv = version_srv;
	}
	
	public SysDepartmentDaoImpl getSysDepartmentDao() {
		return sysDepartmentDao;
	}

	public void setSysDepartmentDao(SysDepartmentDaoImpl sysDepartmentDao) {
		this.sysDepartmentDao = sysDepartmentDao;
	}

	@Override
	public int showCount(String sql) throws Exception {
		// TODO Auto-generated method stub
		return modelFile_dao.showCount(sql);
	}
	
	/*@Override
	public int backupModel() {
		return modelFile_dao.backupModel();
	}*/

	/*@Override
	public int delModel() {
		return modelFile_dao.delModel();
	}*/
}
