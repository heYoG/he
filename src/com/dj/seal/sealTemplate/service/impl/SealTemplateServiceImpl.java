package com.dj.seal.sealTemplate.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.service.impl.SysFuncService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealTemplate.dao.api.ISealTemplateDao;
import com.dj.seal.sealTemplate.service.api.ISealTemplateService;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.SealTemplateUtil;


public class SealTemplateServiceImpl implements ISealTemplateService {
	
	static Logger logger = LogManager.getLogger(SealTemplateServiceImpl.class.getName());

	private ISealTemplateDao dao;

	private ISysRoleDao role_dao;

	private ISysUserDao user_dao;  

	private ISysDepartmentDao dept_dao;

	private ISealBodyService seal_body;
	
	private SysFuncService func_srv;
	

	public SysFuncService getFunc_srv() {
		return func_srv;
	}

	public void setFunc_srv(SysFuncService func_srv) {
		this.func_srv = func_srv;
	}

	public ISealBodyService getSeal_body() {
		return seal_body;
	}

	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	public ISealTemplateDao getDao() {
		return dao;
	}

	public void setDao(ISealTemplateDao dao) {
		this.dao = dao;
	}

	public ISysRoleDao getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(ISysRoleDao role_dao) {
		this.role_dao = role_dao;
	}

	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	@Override
	public PageSplit LocalshowTempList(String user_id, int pageSize,
			int pageIndex) throws GeneralException {
		StringBuffer sb=new StringBuffer();
		sb.append("select * from " + SealTemplateUtil.TABLE_NAME );
		if(user_id.equals("admin")){
			sb.append(" order by "+SealTemplateUtil.CREATE_TIME+" desc");
		}else{
			sb.append(" where "	+SealTemplateUtil.TEMP_CREATOR + "='" + user_id + "' order by "+SealTemplateUtil.CREATE_TIME+" desc");
		}
		/*String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_CREATOR + "='" + user_id + "' order by "+SealTemplateUtil.CREATE_TIME+" desc";*/
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}

	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	@Override
	public PageSplit showTempList(String is_junior, String user_id,
			int pageSize, int pageIndex) throws GeneralException {
		SysUser user = user_dao.showSysUserByUser_id(user_id);
		StringBuffer sb = new StringBuffer();
		sb.append("select * from " + SealTemplateUtil.TABLE_NAME
				+ " where  1=1");
		if (is_junior.equals("1")) {
			sb.append(" and " + SealTemplateUtil.DEPT_NO + " like '%"
					+ user.getDept_no() + "%'");
		}
		if (is_junior.equals("0")) {
			sb.append(" and " + SealTemplateUtil.DEPT_NO + " ='"
					+ user.getDept_no() + "'");
		}
		sb.append(" order by "+SealTemplateUtil.CREATE_TIME+" desc");
		PageSplit pageSplit = new PageSplit();
		List<SealTemplate> list = dao.showTempList(sb.toString());
		List<SealTemplate> listAll = new ArrayList<SealTemplate>();
		for (SealTemplate sealTemp : list) {
			String deptName = dept_dao.getDepartName(sealTemp.getDept_no());
			sealTemp.setDept_name(deptName);
			SysUser objuser = user_dao.showSysUserByUser_id(sealTemp
					.getTemp_creator());
			sealTemp.setUser_name(objuser.getUser_name());
			if (sealTemp.getSeal_bit() == 1) {
				sealTemp.setBit_name("单色");
			} else if (sealTemp.getSeal_bit() == 4) {
				sealTemp.setBit_name("16色");
			} else if (sealTemp.getSeal_bit() == 8) {
				sealTemp.setBit_name("256色");
			} else if (sealTemp.getSeal_bit() == 24) {
				sealTemp.setBit_name("24位真彩色");
			}
			if (sealTemp.getTemp_status().equals("2")) {
				sealTemp.setStatus_name("批准");
			} else if (sealTemp.getTemp_status().equals("3")) {
				sealTemp.setStatus_name("拒绝");
			}
			listAll.add(sealTemp);
		}
		pageSplit.setDatas(list);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sb.toString()));
		return pageSplit;
	}

	@Override
	public void addSealTemp(SealTempForm form) throws GeneralException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp localtime = new Timestamp(new java.util.Date().getTime());
		form.setTemp_status(Constants.TEMP_STATUS_REG);// 申请状态
		try {
			SealTemplate temp = new SealTemplate();
			String id = getMaxIDs(SealTemplateUtil.TABLE_NAME,
					SealTemplateUtil.TEMP_ID) ;
			form.setTemp_id(Integer.valueOf(id));
			form.setTemp_status(Constants.TEMP_STATUS_REG);
			form.setCreate_time(localtime.toString().substring(0, 19));
			form.setApprove_time(localtime.toString().substring(0, 19));
			if (form.getSeal_type().equals("gzfrz")) {// 公章
				form.setSeal_type("公章-法人章");
			}else if(form.getSeal_type().equals("ptgz")){
				form.setSeal_type("普通公章");
			}else if(form.getSeal_type().equals("djyz")){
				form.setSeal_type("冻结印章");
			}else if(form.getSeal_type().equals("jdyz")){
				form.setSeal_type("解冻印章");
			}
			if(form.getAble_btime() == null || form.getAble_btime().trim().equals("")){
				form.setAble_btime(sdf.format(localtime));
			}
			if(form.getAble_etime() == null || form.getAble_etime().trim().equals("")){
				form.setAble_etime(sdf.format(localtime));
			}
//			logger.info(sdf.format(localtime));
			//form.setAble_btime(form.getAble_btime()+" 00:00:00");
			//form.setAble_etime(form.getAble_etime()+" 23:59:59");
			BeanUtils.copyProperties(temp, form);
			temp.setIs_maked(Constants.NO_MAKED);// 默认是未制章状态
			dao.addSealTemp(temp); // mysql插入语句
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	@Override
	public int isExistTempName(String temp_name) throws GeneralException {
		if (dao.isExistTempName(temp_name)) {
			return 1;
		} else
			return 0;
	}

	@Override
	public List<SealTemplate> showSealTempsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void approveTemp(SealTempForm tempForm, String temps)
			throws GeneralException {
		String[] t = temps.split(",");
		SealTemplate temp = new SealTemplate();
		temp.setApprove_text(tempForm.getApprove_text());
		temp.setTemp_status(tempForm.getTemp_status());
		temp.setApprove_user(tempForm.getApprove_user());
		temp.setApprove_time(new Timestamp(new java.util.Date().getTime()));

		//logger.info(temp.getApprove_user());
		dao.approveTemp(temp, t);
	}

	@Override
	public String showTempByTemp_id(String temp_id) throws GeneralException {
		return dao.showTempByTemp_id(temp_id);
	}

	@Override
	public PageSplit showSealTempByApp(int pageIndex, int pageSize,
			String user_id) throws GeneralException {
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.APPROVE_USER + "='" + user_id + "' and "
				+ SealTemplateUtil.TEMP_STATUS + " = "
				+ Constants.TEMP_STATUS_REG + " order by "
				+ SealTemplateUtil.APPROVE_TIME + " desc";
			return showPageSplit(pageIndex, pageSize, sql);
	}

	@Override
	public PageSplit showSealTempByAppHis(int pageIndex, int pageSize,
			String user_id) throws GeneralException {
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.APPROVE_USER + "='" + user_id + "' and "
				+ SealTemplateUtil.TEMP_STATUS + " in("
				+ Constants.TEMP_STATUS_OK + "," + Constants.TEMP_STATUS_FAIL
				+ ") order by " + SealTemplateUtil.APPROVE_TIME + " desc";
		return showPageSplit(pageIndex, pageSize, sql);
	}

	@Override
	public PageSplit showTempsByDeptManageSel(int pageIndex, int pageSize,
			String dept_no) throws GeneralException {
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.DEPT_NO + " ='" + dept_no + "' and "
				+ SealTemplateUtil.TEMP_STATUS + " ='"
				+ Constants.TEMP_STATUS_OK + "' order by "
				+ SealTemplateUtil.APPROVE_TIME + " desc";
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
		List<SealTemplate> tempList = dao.showSealTempsByPageSplit(pageIndex,
				pageSize, sql);
		List<SealTemplate> listAll = new ArrayList<SealTemplate>();
		for (SealTemplate sealTemplate : tempList) {
			String dept_name = dept_dao
					.getDepartName(sealTemplate.getDept_no());
			sealTemplate.setDept_name(dept_name);
			SysUser objuser = user_dao.showSysUserByUser_id(sealTemplate
					.getTemp_creator());
			sealTemplate.setUser_name(objuser.getUser_name());

			if (sealTemplate.getSeal_bit() == 1) {
				sealTemplate.setBit_name("单色");
			} else if (sealTemplate.getSeal_bit() == 4) {
				sealTemplate.setBit_name("16色");
			} else if (sealTemplate.getSeal_bit() == 8) {
				sealTemplate.setBit_name("256色");
			} else if (sealTemplate.getSeal_bit() == 24) {
				sealTemplate.setBit_name("24位真彩色");
			}
			if (sealTemplate.getTemp_status().equals("2")) {
				sealTemplate.setStatus_name("批准");
			} else if (sealTemplate.getTemp_status().equals("3")) {
				sealTemplate.setStatus_name("拒绝");
			} else if (sealTemplate.getTemp_status().equals("1")) {
				sealTemplate.setStatus_name("申请中");
			}
			listAll.add(sealTemplate);
		}
		pageSplit.setDatas(listAll);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}

	@Override
	public PageSplit showTempsByDeptManage(int pageIndex, int pageSize,
			List<SysDepartment> list) throws GeneralException {
		String dept_no = "";
		for (SysDepartment sysDepartment : list) {
			dept_no += "'" + sysDepartment.getDept_no() + "',";
		}
		String dept_no2 = "";
		int lastIndex = dept_no.lastIndexOf(',');
		if (lastIndex > -1) {
			dept_no2 = dept_no.substring(0, lastIndex)
					+ dept_no.substring(lastIndex + 1, dept_no.length());
		}
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.DEPT_NO + " in(" + dept_no2 + ") and "
				+ SealTemplateUtil.TEMP_STATUS + " ='"
				+ Constants.TEMP_STATUS_OK + "' order by "
				+ SealTemplateUtil.APPROVE_TIME + " desc";
		return showPageSplit(pageIndex, pageSize, sql);
	}

	@Override
	public PageSplit findSealTemplate(int pageIndex, int pageSize,
			SealTempForm form, String is_junior) throws GeneralException {
		String temp_name = form.getTemp_name();// 印模名称
		String seal_type = form.getSeal_type();// 印章类型
		String dept_no = form.getDept_no();// 部门编号
		String start_time = form.getStart_time();// 开始时间
		String end_time = form.getEnd_time();// 结束时间
		StringBuffer sb = new StringBuffer("select * from "
				+ SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_STATUS + "='"
				+ Constants.TEMP_STATUS_OK + "'");
		if (temp_name != null && !"".equals(temp_name)) {
			sb.append(" and " + SealTemplateUtil.TEMP_NAME + " like '%"
					+ temp_name + "%'");
		}
		if (seal_type != null && !"".equals(seal_type)) {
			sb.append(" and " + SealTemplateUtil.SEAL_TYPE + " like '" + seal_type
					+ "%'");
		}
		if (dept_no != null && !"".equals(dept_no)) {
			if (is_junior.equals("1")) {
				sb.append(" and " + SealTemplateUtil.DEPT_NO + " like '"
						+ dept_no + "%'");
			}
			if (is_junior.equals("0")) {
				sb.append(" and " + SealTemplateUtil.DEPT_NO + " ='" + dept_no
						+ "'");
			}
		}
		if (start_time != null && !"".equals(start_time.trim())) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME
						+ " > to_date('" + start_time
						+ " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
		}
		if (end_time != null && !"".equals(end_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME
						+ " < to_date('" + end_time
						+ " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
		}
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}

	@Override
	public PageSplit showTempQueryList(SealTempForm sealtemp, int pageSize,
			int pageIndex, String is_junior) throws GeneralException {
		String temp_name = sealtemp.getTemp_name();// 印模名称
		String seal_type = sealtemp.getSeal_type();// 印章类型
		String dept_no = sealtemp.getDept_no();// 部门编号
		String start_time = sealtemp.getStart_time();// 开始时间
		String end_time = sealtemp.getEnd_time();// 结束时间
		StringBuffer sb = new StringBuffer("select * from "
				+ SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_STATUS + "='"
				+ Constants.TEMP_STATUS_OK + "'");
		if (temp_name != null && !"".equals(temp_name.trim())) {
			sb.append(" and " + SealTemplateUtil.TEMP_NAME + " like '%"
					+ temp_name + "%'");
		}
		if (seal_type != null && !"".equals(seal_type.trim())) {
			sb.append(" and " + SealTemplateUtil.SEAL_TYPE + " like '" + seal_type
					+ "%'");
		}
		if (dept_no != null && !"".equals(dept_no.trim())) {
			if (is_junior.equals("1")) {
				sb.append(" and " + SealTemplateUtil.DEPT_NO + " like '"
						+ dept_no + "%'");
			}
			if (is_junior.equals("0")) {
				sb.append(" and " + SealTemplateUtil.DEPT_NO + " ='" + dept_no
						+ "'");
			}
		}
		if (start_time != null && !"".equals(start_time.trim())) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME
						+ " > to_date('" + start_time
						+ " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
		}
		if (end_time != null && !"".equals(end_time.trim())) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME
						+ " < to_date('" + end_time
						+ " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SealTemplateUtil.CREATE_TIME + " < '"
						+ end_time + " 23:59:59'");
			}
		}
		sb.append(" order by "+SealTemplateUtil.CREATE_TIME+" desc");
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}

	public void CreateSeal(String tempid, String userid)
			throws GeneralException {
		Timestamp localtime = new Timestamp(new java.util.Date().getTime());
		List<SealTemplate> tempList = dao.getSealTempList(tempid);
		for (SealTemplate sealTemp : tempList) {
			dao.updTemp(String.valueOf(sealTemp.getTemp_id()));// 更新印模是否制章状态
			// logger.info("sealTemp.getTemp_data():"+sealTemp.getTemp_data());
			SealBody sealbody = new SealBody();
			sealbody.setSeal_id(sealTemp.getTemp_id());// 印章id
			sealbody.setTemp_id(sealTemp.getTemp_id());// 印模id
			sealbody.setSeal_name(sealTemp.getTemp_name());// 印章名称
			sealbody.setSeal_type(sealTemp.getSeal_type());// 印章类型
			sealbody.setSeal_status(Constants.IS_MAKED);// 已制章
			sealbody.setSeal_data(sealTemp.getTemp_data());// 印章数据
			sealbody.setSeal_creator(userid);// 制章人
			sealbody.setSeal_applytime(sealTemp.getCreate_time());// 申请印模时间
			sealbody.setApprove_begintime(sealTemp.getCreate_time());
			sealbody.setApprove_endtime(sealTemp.getCreate_time());
			sealbody.setCreate_time(sealTemp.getCreate_time());
			sealbody.setApply_user(sealTemp.getApprove_user());// 审批人
			sealbody.setCreate_time(localtime);// 制章时间；
			sealbody.setDept_no(sealTemp.getDept_no());// 部门名称
			sealbody.setPreview_img(sealTemp.getPreview_img());//印章缩略图
			sealbody.setAble_btime(sealTemp.getAble_btime());//印章生效时间
			sealbody.setAble_etime(sealTemp.getAble_etime());//印章失效时间
			sealbody.setKey_words("");
			seal_body.addIsealBody(sealbody);
		}
	}

	public void DeleteTeam(String tempID) throws GeneralException {
		dao.DeleteTeam(tempID);
	}

	@Override
	public SealTemplate showTempByTemp(String tempID) throws GeneralException {
		SealTemplate st = dao.showTempByTemp(tempID);
		String dept_name = dept_dao.getDepartName(st.getDept_no());
		st.setDept_name(dept_name);
		return st;
	}

	// 印模管理修改印模信息 EditInfoAction
	@Override
	public void updateTemp(SealTempForm form) throws GeneralException {
		String sql="update t_ba set C_BAB='"+form.getTemp_name()+"',C_BAE='"+form.getDept_no()+"',C_BAD='"+form.getSeal_type()+"'" +
		",C_BAO='"+form.getTemp_remark()+"',C_BAH='"+form.getSeal_width()+"',C_BAI='"+form.getSeal_height()+"',C_BAJ='"+form.getSeal_bit()
		+"',C_BAK='"+Constants.TEMP_STATUS_REG+"' where C_BAA='"+form.getTemp_id()+"'";
		dao.updateTemp(sql);
	}

	@Override
	public PageSplit LocalshowTempListSu(String user_id, int pageSize,
			int pageIndex) throws GeneralException {
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_CREATOR + "='" + user_id + "' and "
				+ SealTemplateUtil.TEMP_STATUS + " = "
				+ Constants.TEMP_STATUS_OK+" order by "+SealTemplateUtil.APPROVE_TIME+" desc";
		 return showPageSplit(pageIndex, pageSize, sql);
	}

	@Override
	public void deleteTemp(String tempId) throws GeneralException {
		//logger.info("tempId:" + tempId);
		String sql = "delete from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_ID + "='" + tempId + "'";
		dao.deleteTemp(sql);
	}

	@Override
	public String getMaxIDs(String tableName, String colName) throws GeneralException {
		int id = dao.getMaxId(tableName, colName);
		String ID = id + 1 + "";
		return ID;
	}

	@Override
	public SysUser getAppMan(String user_id) throws Exception {
		if(!user_id.equals(Constants.USER_NAME_ADMIN)){
			SysUser objuser = user_dao.showSysUserByUser_id(user_id);
			List<SysUser> users = user_dao.showSysUsersByDept_no(objuser.getIs_junior(),objuser.getDept_no());
			List<SysUser> users_app = new ArrayList<SysUser>();
			if(users!=null){
				for (SysUser user : users) {
					boolean func = func_srv.isFuncAble( 1,0x100, user.getUser_id());
					if(func){
						users_app.add(user);
					}
				}
				try {
					return users_app.get(0);
				} catch (Exception e) {
			//		return null;
					SysUser sysUser = user_dao.showSysUserByUser_id("admin");
					return sysUser;
				}
				
			}
			else{
				return null;
			}
		}
		else{
			SysUser sysUser = user_dao.showSysUserByUser_id("admin");
			return sysUser;
		}
	}
	
	/**
	 * 从印章的bmp路径得到印章的初始默认名称
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getBmpName(String path) throws Exception{
		//logger.info("path:"+path);
		int begin = path.lastIndexOf("\\");
		//int end = path.indexOf(".bmp");
		String bmpName=path.substring(begin+1);
		String sealName=bmpName.split("\\.")[0];
		//logger.info("sealName:"+sealName);
		return sealName;
	}
	/**
	 * 获取印章最大数量
	 * @param path
	 * @return false:已超出最大数量，true在数量之内
	 * @throws Exception
	 */
    public String selTempNum()throws Exception{
    	//String sql="select count(*) from "+SealTemplateUtil.TABLE_NAME;
    	String sql="select "+SealTemplateUtil.TEMP_ID+" from "+SealTemplateUtil.TABLE_NAME;
    	String tempNum=dao.selTempNum(sql);
    	return tempNum;
    }
}
