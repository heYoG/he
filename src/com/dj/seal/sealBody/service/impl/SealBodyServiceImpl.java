package com.dj.seal.sealBody.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sql.CLOB;
import xmlUtil.xml.XMLNote;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.sealBody.dao.api.ISealBodyDao;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealBody.web.form.SealBodyForm;
import com.dj.seal.structure.dao.po.RoleSeal;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SealBodyBak;
import com.dj.seal.structure.dao.po.SealUser;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.encrypt.Base64;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.RoleSealUtil;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SealTemplateUtil;
import com.dj.seal.util.table.SysUserRoleUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.table.UserSealUtil;
import com.mysql.jdbc.Clob;

public class SealBodyServiceImpl implements ISealBodyService {

	static Logger logger = LogManager.getLogger(SealBodyServiceImpl.class.getName());


	private ISealBodyDao seal_dao;
	private ISysDepartmentDao dept_dao;
	private ISysRoleDao role_dao;
	private ISysUserDao user_dao;
	private CertSrv cert_srv;
	private BaseDAOJDBC base_dao;

	public BaseDAOJDBC getBase_dao() {
		return base_dao;
	}

	public void setBase_dao(BaseDAOJDBC base_dao) {
		this.base_dao = base_dao;
	}

	public CertSrv getCert_srv() {
		return cert_srv;
	}

	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

	public ISysRoleDao getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(ISysRoleDao role_dao) {
		this.role_dao = role_dao;
	}

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	public ISealBodyDao getSeal_dao() {
		return seal_dao;
	}

	public void setSeal_dao(ISealBodyDao seal_dao) {
		this.seal_dao = seal_dao;
	}

	@Override
	public void addIsealBody(SealBody sealBody) {

		seal_dao.addIsealBody(sealBody);

	}
	
	// 根据印章ID获取印章数据
	public String getSealDataBySealId(String seal_id) {
		String seal_data = "";
		String sql = "select " + SealBodyUtil.SEAL_DATA + " from "
				+ SealBodyUtil.TABLE_NAME + " where " + SealBodyUtil.SEAL_ID
				+ "=" + seal_id ;
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL||Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
			try {
				seal_data = base_dao.getClobStr(sql, SealBodyUtil.SEAL_DATA);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			try {
				seal_data = base_dao.getStr(sql, SealBodyUtil.SEAL_DATA);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return seal_data;
	}
	
	// 根据印章ID获取印章缩略图数据
	public String getPreviewImgDataBySealId(String seal_id) {
		String seal_data = "";
		String sql = "select " + SealBodyUtil.PREVIEW_IMG + " from "
				+ SealBodyUtil.TABLE_NAME + " where " + SealBodyUtil.SEAL_ID
				+ "=" + seal_id ;
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			try {
				seal_data = base_dao.getClobStr(sql, SealBodyUtil.PREVIEW_IMG);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			try {
				seal_data = base_dao.getStr(sql, SealBodyUtil.PREVIEW_IMG);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}else if(Constants.DB_TYPE == DBTypeUtil.DT_DB2){
//			   try {
//				seal_data = base_dao.getDB2Str(sql, SealBodyUtil.PREVIEW_IMG);
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}
		}
		return seal_data;
	}
	
	
	/**
	 * 根据部门编号得到部门下的印章列表
	 * 
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public List<SealBody> showSealsByDept(String dept_no)
			throws GeneralException {
		//logger.info("dept_no:"+dept_no);
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.DEPT_NO).append("='");
		sb.append(dept_no).append("'");
		sb.append(" and ").append(SealBodyUtil.SEAL_TYPE).append(" like '%公章%'");// 公章
		return seal_dao.showBodyList(sb.toString());
	}
	/**
	 * 根据部门编号得到部门下的印章列表
	 * 
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public List<SealBody> showSealsByDept5(String dept_no)
			throws GeneralException {
		//logger.info("dept_no:"+dept_no);
		StringBuffer sb = new StringBuffer();
		sb.append("select C_BBA, C_BBC,C_BBD from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.DEPT_NO).append(" ='");
		sb.append(dept_no).append("'");
		sb.append(" and "+SealBodyUtil.SEAL_STATUS+"='5'");
		sb.append(" and ").append(SealBodyUtil.SEAL_TYPE).append(" like '%公章%'");// 公章		
		return seal_dao.showBodyList(sb.toString());
	}
	/**
	 * 根据部门编号得到部门下的公章列表
	 * 
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public List<SealBody> showPublicSealsByDept(String dept_no)
			throws GeneralException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.DEPT_NO).append("='");
		sb.append(dept_no).append("'");
		sb.append(" and ").append(SealBodyUtil.SEAL_TYPE).append(" like '%公章%'");// 公章
		//logger.info(sb.toString());
		return seal_dao.showBodyList(sb.toString());
	}
		
	
	/**
	 * 根据用户名得到用户可使用的印章列表
	 * 
	 * @param user_id
	 *            用户id
	 * @return 印章列表
	 * @throws GeneralException
	 */
	@Override
	public List<SealBody> getSealListByUser_id(String user_id) {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_ID + " in (" + "select distinct("
				+ UserSealUtil.SEAL_ID + ") from " + UserSealUtil.TABLE_NAME
				+ " where " + UserSealUtil.USER_ID + " = '" + user_id + "')";
		List<SealBody> seals = seal_dao.showBodyList(sql);
		return seals;
	}
	/**
	 * 根据用户名得到用户可使用的印章列表
	 * 
	 * @param user_id
	 *            用户id
	 * @return 印章列表
	 * @throws GeneralException
	 */
//	public List<SealBody> getSealListByUser_id(String key_word,String user_id) {
////		if(key_word!=null&&!key_word.equals("")){
////			key_word=key_word+",";
////		}
////		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
////				+ SealBodyUtil.SEAL_ID + " in (" + "select distinct("
////				+ UserSealUtil.SEAL_ID + ") from " + UserSealUtil.TABLE_NAME
////				+ " where " + UserSealUtil.USER_ID + " = '" + user_id + "') and "+SealBodyUtil.KEY_WORDS+"='"+seal_type+"'";
//		String sealId=getSealId(user_id);
//		if(sealId!=""){
//			List<SealBody> seals=new ArrayList();
//			if(key_word==null||key_word.equals("")){
//				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
//				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")";
//				seals = seal_dao.showBodyList(sql);
//			}else if(key_word.equals("1")){
//				//取公章
//				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
//				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")"+ " and "+ SealBodyUtil.SEAL_TYPE + " like '%公章%'";
//				seals = seal_dao.showBodyList(sql);
//			}else if(key_word.equals("2")){
//				//取个人章
//				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
//				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")"+ " and "+ SealBodyUtil.SEAL_TYPE + " like '%个人章%'";
//				seals = seal_dao.showBodyList(sql);
//			}else{
//				return null;
//			}
//			return seals;
//		}
//		return null;
//	}
	@Override
	public List<SealBody> getSealListByUser_id(String key_word,String user_id) {
		if(key_word!=null){
			key_word=key_word+",";
			String sealId=getSealId(user_id);
			List<SealBody> sbody=new ArrayList<SealBody>();
			if(sealId!="" || sealId!=null){	
				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")";
				List<SealBody> seals = seal_dao.showBodyList(sql);
				for(SealBody sealbody:seals){
		           if(sealbody.getKey_words().indexOf(key_word)!=-1){	        	  
		        	   sbody.add(sealbody);
		           }			
				}
				 return sbody;
			}
		}else{
			String sealId=getSealId(user_id);
			if(sealId!="" || sealId!=null){	
				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")";
				List<SealBody> seals = seal_dao.showBodyList(sql);
				return seals;
			}
		}
		return null;
	}
    public String getSealId(String user_id){
    	StringBuffer s1=new StringBuffer();
    	String sql="select distinct("
				+ UserSealUtil.SEAL_ID + ") from " + UserSealUtil.TABLE_NAME
				+ " where " + UserSealUtil.USER_ID + " = '" + user_id + "'";
		List<Map> list = base_dao.select(sql);
		if(list.size()>0){
			for (Map map : list) {
				SealUser userseal = new SealUser();
				userseal = (SealUser) DaoUtil.setPo(userseal, map, new UserSealUtil());
				s1.append("'"+userseal.getSeal_id()+"',");
			}
			s1.deleteCharAt(s1.lastIndexOf(","));
			//logger.info("list_sealbody:"+list_sealbody.size());
			return s1.toString();
		}else{
			return "";
		}
	
    }
	/**
	 * 根据角色获取可使用的印章列表
	 * 
	 * @param roles
	 *            角色列表
	 * @return 印章列表
	 */
	@Override
	public List<SealBody> getSealListByRoles(List<SysRole> roles) {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		for (int i = 0; i < roles.size() - 1; i++) {
			sb.append(roles.get(i).getRole_id() + ",");
		}
		sb.append(roles.get(roles.size() - 1).getRole_id());
		sb.append("");
		String sealId=getSealIdByRoleId(sb.toString());
		if(sealId!=""){
			String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
			+ SealBodyUtil.SEAL_ID + " in ("+sealId+")";
	        List<SealBody> seals = seal_dao.showBodyList(sql);
	        return seals;
		}
		return null;
	}
	
	@Override
	public List<SealBody> getSealListByRoles(String seal_type,List<SysRole> roles) {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		for (int i = 0; i < roles.size() - 1; i++) {
			sb.append(roles.get(i).getRole_id() + ",");
		}
		sb.append(roles.get(roles.size() - 1).getRole_id());
		sb.append("");
		String sealId=getSealIdByRoleId(sb.toString());
		if(sealId!=""){
			List<SealBody> seals=new ArrayList<SealBody>();
			if(seal_type==null||seal_type.equals("")){
				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")";
				seals = seal_dao.showBodyList(sql);
			}else if(seal_type.equals("1")){
				//取公章
				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")" + " and "+ SealBodyUtil.SEAL_TYPE + " like '%公章%'";
				seals = seal_dao.showBodyList(sql);
			}else if(seal_type.equals("2")){
				//取个人章
				String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "+SealBodyUtil.SEAL_STATUS+" = '5' and "
				+ SealBodyUtil.SEAL_ID + " in ("+sealId+")" + " and "+ SealBodyUtil.SEAL_TYPE + " like '%个人章%'";
				seals = seal_dao.showBodyList(sql);
			}else{
				return null;
			}
	        return seals;
		}
		return null;
	}
	
	  public String getSealIdByRoleId(String role_id){
	    	StringBuffer s1=new StringBuffer();
	    	String sql="select distinct("
					+ RoleSealUtil.SEAL_ID + ") from " + RoleSealUtil.TABLE_NAME
					+ " where " + RoleSealUtil.ROLE_ID + " in('" + role_id + "')";
	    	//logger.info("sql--"+sql);
			List<Map> list = base_dao.select(sql);
			if(list.size()>0){
				for (Map map : list) {
					RoleSeal roleseal = new RoleSeal();
					roleseal = (RoleSeal) DaoUtil.setPo(roleseal, map, new RoleSealUtil());
					s1.append("'"+roleseal.getSeal_id()+"',");
				}
				s1.deleteCharAt(s1.lastIndexOf(","));
				//logger.info("list_sealbody:"+list_sealbody.size());
				return s1.toString();
			}else{
				return "";
			}
			
	    }
	@Override
	public PageSplit showBodyList(String is_junior, String user_id,
			int pageSize, int pageIndex) throws GeneralException {
		SysUser user = user_dao.showSysUserByUser_id(user_id);
		StringBuffer sb = new StringBuffer();
		sb.append("select * from " + SealBodyUtil.TABLE_NAME + " where  1=1 ");
		if (is_junior.equals("1")) {
			sb.append(" and " + SealBodyUtil.DEPT_NO + " like '"
					+ user.getDept_no() + "%'");
		}
		if (is_junior.equals("0")) {
			sb.append(" and " + SealBodyUtil.DEPT_NO + " ='"
					+ user.getDept_no() + "'");
		}
		sb.append("  order by "+SealBodyUtil.CREATE_TIME+" desc");
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}

	@Override
	public PageSplit showBodyQueryList(SealBodyForm sealtemp, int pageSize,
			int pageIndex, String is_junior) throws GeneralException {
		String seal_name = sealtemp.getSeal_name();// 印章名称
		String seal_type = sealtemp.getSeal_type();// 印章类型
		String dept_no = sealtemp.getDept_no();// 部门编号
		String start_time = sealtemp.getApprove_begintime();// 开始时间
		String end_time = sealtemp.getApprove_endtime();// 结束时间
		StringBuffer sb = new StringBuffer("select * from "
				+ SealBodyUtil.TABLE_NAME + " where 1=1 ");
		if (seal_name != null && !"".equals(seal_name)) {
			sb.append(" and " + SealBodyUtil.SEAL_NAME + " like '%" + seal_name
					+ "%'");
		}
		if (seal_type != null && !"".equals(seal_type)) {
			sb.append(" and " + SealBodyUtil.SEAL_TYPE + " like'" + seal_type
					+ "%'");
		}
		if (dept_no != null && !"".equals(dept_no)) {
			if (is_junior.equals("1")) {
				sb.append(" and " + SealBodyUtil.DEPT_NO + " like '" + dept_no
						+ "%'");
			}
			if (is_junior.equals("0")) {
				sb.append(" and " + SealBodyUtil.DEPT_NO + " ='" + dept_no
						+ "'");
			}
		}
		if (start_time != null && !"".equals(start_time.trim())) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " > to_date('"
						+ start_time + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
			if(Constants.DB_TYPE == DBTypeUtil.DT_DB2){
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " > to_date('"
						+ start_time + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
		}
		if (end_time != null && !"".equals(end_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " < to_date('"
						+ end_time + " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SealBodyUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
		}
        sb.append("  order by "+SealBodyUtil.CREATE_TIME+"  desc");
//        logger.info("getSealSql:"+sb);
		List<SealBody> listAll=new ArrayList<SealBody>();
		List<SealBody> list = seal_dao.showBodyList(sb.toString());
		long t1=System.currentTimeMillis();
		for (SealBody sealBody : list) {		
			String dept_name = dept_dao.getDepartName(sealBody.getDept_no());		
			sealBody.setDept_name(dept_name);
			String create_name = user_dao.showSysUserByUser_id(sealBody.getSeal_creator()).getUser_name();
			sealBody.setCreate_name(create_name);
			sealBody.setDept_name(dept_name);
			SysUser objuser = user_dao.showSysUserByUser_id(sealBody.getSeal_creator());
			sealBody.setSeal_creator(objuser.getUser_name());
			try {
				if(sealBody.getKey_sn()!=""){			
					String key_name = cert_srv.CertName(sealBody.getKey_sn());				
					sealBody.setKey_name(key_name);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			listAll.add(sealBody);
		}
		logger.info("getDeprtNameTime:"+(System.currentTimeMillis()-t1));
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sb.toString()));
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}

	@Override
	public SealBody getSealBodys(Integer seal_id) throws GeneralException {
		SealBody sealBody = seal_dao.getSealBodys(seal_id);
		if(sealBody.getSeal_type().contains("公章")){//判断印章是否为公章 的大类
			sealBody.setSeal_types("1");  
		}else {
			sealBody.setSeal_types("2");
		}
		String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
		sealBody.setDept_name(dept_name);
		SysUser objuser = user_dao.showSysUserByUser_id(sealBody
				.getSeal_creator());
		sealBody.setSeal_creator(objuser.getUser_name());
		return sealBody;
	}
//	/**
//	 * 根据印章id查找印章信息
//	 * 
//	 * @param seal_id
//	 * @return
//	 * @throws GeneralException
//	 */
//	public SealBody getSealBodysBySealCZId(String seal_id) throws GeneralException {
//		SealBody sealBody = seal_dao.getSealBodysBYSealCZid(seal_id);
//		if(sealBody==null){
//			return null;
//		}
//		if(sealBody.getSeal_type().contains("公章")){//判断印章是否为公章 的大类
//			sealBody.setSeal_types("1");  
//		}else {
//			sealBody.setSeal_types("2");
//		}
//		String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
//		sealBody.setDept_name(dept_name);
//		SysUser objuser = user_dao.showSysUserByUser_id(sealBody
//				.getSeal_creator());
//		sealBody.setSeal_creator(objuser.getUser_name());
//		return sealBody;
//	}
	@Override
	public SealBody getSealBodyID(String seal_id) throws GeneralException {
		SealBody sealBody = seal_dao.getSealBodyID(seal_id);
		if(sealBody.getSeal_type().contains("公章")){//判断印章是否为公章 的大类
			sealBody.setSeal_types("1");  
		}else {
			sealBody.setSeal_types("2");
		}
		String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
		sealBody.setDept_name(dept_name);
		SysUser objuser = user_dao.showSysUserByUser_id(sealBody
				.getSeal_creator());
		sealBody.setSeal_creator(objuser.getUser_name());
		return sealBody;
	}

	@Override
	public void addRoleUser(String seal_id, String user_id, String role_id)
			throws GeneralException {
		String[] struser = null;
		String[] strrole = null;
		if (user_id != "") {
			struser = user_id.split(",");
		}
		seal_dao.deleteUserSeal(seal_id);
		if (role_id != "") {
			strrole = role_id.split(",");
		}
		seal_dao.deleteRoleSeal(seal_id);
		if(struser!=null){
			for (int i = 0; i < struser.length; i++) {
				if (!struser[i].equals("") || !struser[i].equals(null)) {
					seal_dao.addUserSeal(seal_id, struser[i]);
				}
				
			}
		}
		if(strrole!=null){
			for (int i = 0; i < strrole.length; i++) {
				if (!strrole[i].equals("") || !strrole[i].equals(null)) {
					seal_dao.addRoleSeal(seal_id, strrole[i]);
				}
			}
		}
		
	}

	@Override
	public List<SysUser> getUser(String seal_id) throws GeneralException {
		String sql = "select * from " + UserSealUtil.TABLE_NAME + " where "
				+ UserSealUtil.SEAL_ID + "='" + seal_id + "'";
		return seal_dao.getUser(sql);
	}

	@Override
	public List<SysRole> getRole(String seal_id) throws GeneralException {
		String sql = "select * from " + RoleSealUtil.TABLE_NAME + " where "
				+ RoleSealUtil.SEAL_ID + "='" + seal_id + "'";
		return seal_dao.getRole(sql);
	}
	@Override
	public PageSplit showBodyListById(String user_id, int pageSize,
			int pageIndex) throws GeneralException {
		SysUser user = user_dao.showSysUserByUser_id(user_id);
		String sql = "";
		if (user.getIs_junior().equals("1")) {// 允许下级
			sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
					+ SealBodyUtil.DEPT_NO + " like'%" + user.getDept_no()
					+ "%' order by "+SealBodyUtil.CREATE_TIME+"  desc";
		} else {
			sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
					+ SealBodyUtil.DEPT_NO + "='" + user.getDept_no() + "'  order by "+SealBodyUtil.CREATE_TIME+" desc";
		}
		List<SealBody> listAll = new ArrayList<SealBody>();
		List<SealBody> list = seal_dao.showBodyList(sql);
		for (SealBody sealBody : list) {
			String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
			sealBody.setDept_name(dept_name);
			;
			SysUser objuser = user_dao.showSysUserByUser_id(sealBody
					.getSeal_creator());
			sealBody.setSeal_creator(objuser.getUser_name());
			listAll.add(sealBody);
		}
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}
	
	@Override
	public PageSplit showSealBySeal_Id(int pageSize,
			int pageIndex,String seal_id) throws GeneralException {
		String sql = "";
			sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
					+ SealBodyUtil.SEAL_ID + "='" + seal_id + "'";
		List<SealBody> listAll = new ArrayList<SealBody>();
		List<SealBody> list = seal_dao.showBodyList(sql);
		for (SealBody sealBody : list) {
			String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
			sealBody.setDept_name(dept_name);
			;
			SysUser objuser = user_dao.showSysUserByUser_id(sealBody
					.getSeal_creator());
			sealBody.setSeal_creator(objuser.getUser_name());
			listAll.add(sealBody);
		}
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}

	@Override
	public PageSplit showBodyQueryListByDeptNO(String dept_no, int pageSize,
			int pageIndex) throws GeneralException {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.DEPT_NO + "='" + dept_no + "'";
		List<SealBody> listAll = new ArrayList<SealBody>();
		List<SealBody> list = seal_dao.showBodyList(sql);
		for (SealBody sealBody : list) {
			String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
			sealBody.setDept_name(dept_name);
			;
			SysUser objuser = user_dao.showSysUserByUser_id(sealBody
					.getSeal_creator());
			sealBody.setSeal_creator(objuser.getUser_name());
			listAll.add(sealBody);
		}
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}

	@Override
	public PageSplit showAllSealsOfMine(int pageSize, int pageIndex,
			String user_id) throws Exception {
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			// sql="select * from "+SealBodyUtil.TABLE_NAME+" where
			// "+SealBodyUtil.SEAL_ID+" in (select "+RoleSealUtil.SEAL_ID+" from
			// "+RoleSealUtil.TABLE_NAME+" where "
			// +RoleSealUtil.ROLE_ID+" in (select "+SysUserRoleUtil.ROLE_ID+"
			// from "+SysUserRoleUtil.TABLE_NAME+" where
			// "+SysUserRoleUtil.USER_ID+" = '"+user_id+"')) or "
			// +SealBodyUtil.SEAL_ID+" in (select "+UserSealUtil.SEAL_ID+" from
			// "+UserSealUtil.TABLE_NAME+" where "+UserSealUtil.USER_ID+" =
			// '"+user_id+"') or "+
			// SealBodyUtil.DEPT_NO+" in (select "+SysUserDeptUtil.DEPT_NO+"
			// from "+SysUserDeptUtil.TABLE_NAME+" where
			// "+SysUserDeptUtil.USER_ID+" = '"+user_id+
			// "') and "+SealBodyUtil.SEAL_STATUS+" = '"+Constants.IS_MAKED+"'
			// order by "+SealBodyUtil.CREATE_TIME+" desc";
			sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
					+ SealBodyUtil.SEAL_ID + " in (select "
					+ RoleSealUtil.SEAL_ID + " from " + RoleSealUtil.TABLE_NAME
					+ " where " + RoleSealUtil.ROLE_ID + " in (select "
					+ SysUserRoleUtil.ROLE_ID + " from "
					+ SysUserRoleUtil.TABLE_NAME + " where "
					+ SysUserRoleUtil.USER_ID + " = '" + user_id + "')) or "
					+ SealBodyUtil.SEAL_ID + " in (select "
					+ UserSealUtil.SEAL_ID + " from " + UserSealUtil.TABLE_NAME
					+ " where " + UserSealUtil.USER_ID + " = '" + user_id
					+ "') or " + SealBodyUtil.DEPT_NO + " in (select "
					+ SysUserUtil.DEPT_NO + " from " + SysUserUtil.TABLE_NAME
					+ " where " + SysUserUtil.USER_ID + " = '" + user_id
					+ "') and " + SealBodyUtil.SEAL_STATUS + " = '"
					+ Constants.IS_MAKED + "' order by "
					+ SealBodyUtil.CREATE_TIME + " desc";
			//logger.info("---ORCL---" + sql);

		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			// String roleseal = "select * from t_rbd inner join t_af on
			// t_rbd.c_rdc=t_af.c_afb and t_af.c_afa='"
			// + user_id + "'";
			// String roleSealId = dao.getStr(roleseal, "c_rdb");
			// //得到角色印章表里的印章ID
			//
			// String userseal = "select * from t_ubd where c_udc='" + user_id
			// + "'";
			// String userSealId = dao.getStr(userseal, "C_UDB");//得到用户印章表里的印章ID
			// String sealId = roleSealId + userSealId;
			// String sealID = "";
			// if(!sealId.equals("")){
			// sealID = sealId.substring(0, sealId.length() - 1);
			//					
			// }sealID = "";
			//			
			//			
			// String dept = "select * from t_ac where c_aca = '" + user_id +
			// "'";
			// List<SysUser> userDeptList = user_dao.showSysUsersBySql(dept);
			// String userDeptId = "";// 部门ID
			// for (SysUser user : userDeptList) {
			// userDeptId += user.getDept_no() + ",";
			// }
			// String userDeptID = userDeptId
			// .substring(0, userDeptId.length() - 1);
			//
			// sql = "select * from t_bb where c_bba in ('" + sealID
			// + "') or c_bbc in ('" + userDeptID + "')";
			String roleseal = "select t_rbd.c_rdb from t_rbd inner join t_af on t_rbd.c_rdc=t_af.c_afb and t_af.c_afa='"
					+ user_id + "'";
			
			String seal_id = "";
			seal_id = base_dao.getStr(roleseal, "c_rdb");
			String userseal = "select C_UDB from t_ubd where c_udc='" + user_id
					+ "'";
			seal_id +=","+ base_dao.getStr(userseal, "C_UDB");
			
			if(seal_id.endsWith(",")){
				seal_id = seal_id.substring(0, seal_id.length()-1);
			}
			if(seal_id.startsWith(",")){
				seal_id = seal_id.substring(1);
			}
			String dept = "select * from t_ac where c_aca = '" + user_id +"'"; //得到用户所在部门ID
			List<SysUser> userDeptList = user_dao.showSysUsersBySql(dept);
			String userDeptId = "";// 部门ID
			for (SysUser user : userDeptList) {
				userDeptId += user.getDept_no() + ",";
			}
			String userDeptID = userDeptId.substring(0, userDeptId.length() - 1);
			if (!seal_id.equals("")) {
				sql = "select * from t_bb where c_bba in(" + seal_id
						+ ") and C_BBK='1' or (c_bbc in ('" + userDeptID + "') and c_bbe like '公章%') ";
			} else {
				sql = "select * from t_bb where c_bba in('') and C_BBK='1' or (c_bbc in ('" + userDeptID + "') and c_bbe like '公章%' )";
			}
			//logger.info("---MYSQL---" + sql);
		}
		return showPageSplit(pageIndex, pageSize, sql);
	}

	/**
	 * 根据分页信息返回满足条件的记录
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws GeneralException
	 */
	private PageSplit showPageSplit(int pageIndex, int pageSize, String sql)
			throws GeneralException {
		PageSplit pageSplit = new PageSplit();
		List<SealBody> sealBodys = seal_dao.showSealBodysByPageSplit(pageIndex,
				pageSize, sql);
		List<SealBody> listAll = new ArrayList<SealBody>();
		for (SealBody sealBody : sealBodys) {
			String dept_name = dept_dao.getDepartName(sealBody.getDept_no());
			sealBody.setDept_name(dept_name);
			String apply_name = user_dao.showSysUserByUser_id(
					sealBody.getApply_user()).getUser_name();
			sealBody.setApply_name(apply_name);
			if(sealBody.getSeal_type().contains("公章")){//判断印章是否为公章 的大类
				sealBody.setSeal_types("1");  
			}else {
				sealBody.setSeal_types("2");
			}
			SysUser objuser = user_dao.showSysUserByUser_id(sealBody
					.getSeal_creator());
			sealBody.setCreate_name(objuser.getUser_name());

			try {
				String key_name = cert_srv.CertName(sealBody.getKey_sn());
				sealBody.setKey_name(key_name);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			listAll.add(sealBody);
		}
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}
	@Override
	public SealBody getSealBodys(String seal_name) throws GeneralException{
		SealBody sealBody =seal_dao.getSealBodys(seal_name);
		return sealBody;
	}
	@Override
	public void updateSealBody(String cert_no,String seal_id) throws GeneralException {
		String sql = "";
			sql = "update T_BB set C_ACE='"
				+ cert_no
				+ "' where C_BBA='" + seal_id + "'";
			logger.info("sql==="+sql);
		seal_dao.updateSealBody(sql);
	}
	public void saveSealData(String seal_id,String seal_data)throws Exception{
		seal_dao.updateIsealBody(seal_id,seal_data);
	}
	public void upKeyWord(String seal_id,String key_word)throws Exception{
		String sql="update "+SealBodyUtil.TABLE_NAME+" set "+SealBodyUtil.KEY_WORDS+"='"+key_word+"' where "+SealBodyUtil.SEAL_ID+"='"+seal_id+"'";
		seal_dao.updateSealBody(sql);
	}
	@Override
	public void deleteSeal(String sealId,String sealName)throws Exception{		
		String sql3="delete from "+SealTemplateUtil.TABLE_NAME+" where "+SealTemplateUtil.TEMP_NAME+"='"+sealName+"'";
		seal_dao.deleteSeal(sql3);
		String sql="delete from "+SealBodyUtil.TABLE_NAME+" where "+SealBodyUtil.SEAL_ID+"='"+sealId+"'";
		seal_dao.deleteSeal(sql);
		String sql1="delete from "+UserSealUtil.TABLE_NAME+" where "+UserSealUtil.SEAL_ID+"='"+sealId+"'";
		seal_dao.deleteSeal(sql1);
		String sql2="delete from "+RoleSealUtil.TABLE_NAME+" where "+RoleSealUtil.SEAL_ID+"='"+sealId+"'";
		seal_dao.deleteSeal(sql2);
	}
	public String importSeal(String filepath)throws Exception{
		File fe = new File(filepath);
		InputStream is = new FileInputStream(fe);
		byte[] by = new byte[(int) fe.length()];
		is.read(by);
		is.close();
		String xmlStr=new String(by);
		XMLNote xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		XMLNote sealInfo = xml.getByName("SEAL_INFO");
		SealBodyBak sealbody_bak = new SealBodyBak();
		logger.info("seal_id"+sealInfo.getValue("SEAL_ID"));
		sealbody_bak.setSeal_id(Integer.parseInt(sealInfo.getValue("SEAL_ID")));// 印章id
		sealbody_bak.setSeal_name(sealInfo.getValue("SEAL_NAME"));// 印章名称
		sealbody_bak.setSeal_type(sealInfo.getValue("SEAL_TYPE"));// 印章类型
		sealbody_bak.setSeal_creator(sealInfo.getValue("SEAL_CREATOR"));// 制章人
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Timestamp ts = new Timestamp(df.parse(sealInfo.getValue("CREATE_TIME")).getTime());
			sealbody_bak.setCreate_time(ts);// 制章时间；
			sealbody_bak.setDept_name(sealInfo.getValue("DEPT_NAME"));// 部门名称
			sealbody_bak.setSeal_czid(sealInfo.getValue("SEAL_CZID"));
			sealbody_bak.setSeal_base64(sealInfo.getValue("SEAL_BASE64"));
			seal_dao.addIsealBodyBak(sealbody_bak);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			return "0";
		}
		return "1";
	}
	public int isExistSealName(String temp_name) throws GeneralException {
		if (seal_dao.isExistSealName(temp_name)) {
			return 1;
		} else
			return 0;
	}
	
	public void dbToFile(SealBody obj) throws Exception {
		FileOutputStream fout = new FileOutputStream(obj.getSeal_path());
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(SealBodyUtil.SEAL_DATA);
		sb.append(" from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.SEAL_NAME);
		sb.append("='").append(obj.getSeal_name()).append("'");
		//logger.info("sb:"+sb.toString());
		if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){
			CLOB clob = base_dao.getClob(sb.toString(), SealBodyUtil.SEAL_DATA);
			InputStream input = clob.getAsciiStream();
            int len = (int)clob.length();
            byte[] by = new byte[len];
            int i ;
            while(-1 != (i = input.read(by, 0, by.length))) {
              input.read(by, 0, i);
            }
            String seal_data = new String(by);
           // logger.info("seal_data:"+seal_data);
			byte[] baseV=Base64.decode(seal_data);
			fout.write(baseV, 0, baseV.length);
			fout.close();	
		}else if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){
			Clob clob = base_dao.getMysqlClob(sb.toString(),SealBodyUtil.SEAL_DATA);
			InputStream input = clob.getAsciiStream();
            int len = (int)clob.length();
            byte[] by = new byte[len];
            int i ;
            while(-1 != (i = input.read(by, 0, by.length))) {
              input.read(by, 0, i);
            }
            String seal_data = new String(by);
           // logger.info("seal_data:"+seal_data);
			byte[] baseV=Base64.decode(seal_data);
			fout.write(baseV, 0, baseV.length);
			fout.close();	
		}
	}
	
	/**
	 * by hyg 20171228
	 * @param obj
	 * @throws Exception
	 */
	public void dbToFile2(SealBody obj) throws Exception {
		FileOutputStream fout = new FileOutputStream(obj.getSeal_path());
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(SealBodyUtil.SEAL_DATA);
		sb.append(" from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.SEAL_NAME);
		sb.append("='").append(obj.getSeal_name()).append("'");
		CLOB clob = base_dao.getClob2(sb.toString(), SealBodyUtil.SEAL_DATA);//新增getClob2接口
		InputStream input = clob.getAsciiStream();
		BufferedInputStream bis=new BufferedInputStream(input);//使用缓冲流,新增
        int len = (int)clob.length();
        byte[] by = new byte[len];
        int i ;
        while(-1 != (i = bis.read(by, 0, by.length))) {
              input.read(by, 0, i);
         }
        String seal_data = new String(by);
        byte[] baseV=Base64.decode(seal_data);
		fout.write(baseV, 0, baseV.length);
		fout.close();	
		input.close();//新增20171228
		bis.close();//add 20180830
		
	}
	
	/**
	 * 根据印章名称获取印章数据
	 * @param sealName 印章名称
	 * @return 印章数据
	 * @throws Exception
	 */
	public String getSealData(String sealName) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(SealBodyUtil.SEAL_DATA);
		sb.append(" from ").append(SealBodyUtil.TABLE_NAME);
		sb.append(" where ").append(SealBodyUtil.SEAL_NAME);
		sb.append("='").append(sealName).append("'");
		//logger.info("sb:"+sb);
		return base_dao.getClobStr(sb.toString(), SealBodyUtil.SEAL_DATA);
		
	}
	
	@Override
	public String exportSeal(String sealId)throws Exception{
		 SealBody sealbody=seal_dao.getSealBodyID(sealId);
		 String deptName=dept_dao.getDepartName(sealbody.getDept_no());
		 StringBuffer s1=new StringBuffer();
		 s1.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\r\n");
		 s1.append("<SEAL>").append("\r\n");
		 s1.append("<SEAL_INFO>").append("\r\n");
         s1.append("<SEAL_ID>");
		 s1.append(sealbody.getSeal_id());
		 s1.append("</SEAL_ID>").append("\r\n");
		 s1.append("<DEPT_NAME>");
		 s1.append(deptName);
		 s1.append("</DEPT_NAME>").append("\r\n");
		 s1.append("<SEAL_NAME>");
		 s1.append(sealbody.getSeal_name());
		 s1.append("</SEAL_NAME>").append("\r\n");
		 s1.append("<SEAL_TYPE>");
		 s1.append(sealbody.getSeal_type());
		 s1.append("</SEAL_TYPE>").append("\r\n");
		 s1.append("<SEAL_CREATOR>");
		 s1.append(sealbody.getSeal_creator());
		 s1.append("</SEAL_CREATOR>").append("\r\n");
		 s1.append("<CREATE_TIME>");
		 s1.append(sealbody.getCreate_time());
		 s1.append("</CREATE_TIME>").append("\r\n");
		 s1.append("</SEAL_INFO>").append("\r\n");
		 s1.append("</SEAL>").append("\r\n");
		 logger.info(s1.toString());
		 try{
     	    byte b[] = s1.toString().getBytes();
     	    String bpath = bpath();
     	    logger.info(bpath+"bakSeal\\"+sealbody.getSeal_name()+".xml");
 		    FileOutputStream ou = new FileOutputStream(new File(bpath+"bakSeal\\"+sealbody.getSeal_name()+".xml"));
 		    ou.write(b);
 		    ou.close();
 		    logger.info("导出印章信息成功！");
 		    return "1";
		 }catch(Exception e){
			return "0";
		 }
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
	/*注销印章 */
	public String logOff(String seal_id){
		return  seal_dao.logOff(seal_id);
	}
	/*激活印章 */
	public String activate(String seal_id){
		return  seal_dao.activate(seal_id);
	}

	@Override
	public List<SealBody> showSealBodyByDeptNo(String deptNo) {
		// TODO Auto-generated method stub
		String sql = "select * from " + SealBodyUtil.TABLE_NAME+" where  "+SealBodyUtil.DEPT_NO + " ='"+deptNo+"' and "+SealBodyUtil.SEAL_STATUS +" ='5'";
		List<SealBody> list = seal_dao.showBodyList(sql);
		if(list.size() == 0 && !deptNo.equals(Constants.UNIT_DEPT_NO)){
			deptNo = dept_dao.showSysDepartmentByDept_no(deptNo).getDept_parent();//获取该部门的父部门
			list = showSealBodyByDeptNo(deptNo);
		}
		return list;
	}

	@Override
	public List<SealBody> showSealBodyByDeptNo2(String deptNo) {		
		String sql = "select C_BBE,C_BBD,C_BBC,C_ACE from " + SealBodyUtil.TABLE_NAME+" where  "+SealBodyUtil.DEPT_NO + " ='"+deptNo+"' and "+SealBodyUtil.SEAL_STATUS +" ='5'";
		List<SealBody> list = seal_dao.showBodyList(sql);
		//logger.info("getSealSql:"+sql);
		if(list.size() == 0 && !deptNo.equals(Constants.UNIT_DEPT_NO)){
			deptNo = dept_dao.getDeptNo(deptNo).getDept_parent();//获取该部门的父部门
			list = showSealBodyByDeptNo2(deptNo);//父部门编号作为部门编号向上查询
		}
		return list;
	}
}
