package com.dj.seal.organise.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.structure.dao.po.PsdLastTime;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.AppSystemUtil;
import com.dj.seal.util.table.PsdLastTimeUtil;
import com.dj.seal.util.table.SysDepartmentUtil;
import com.dj.seal.util.table.SysUnitUtil;
import com.dj.seal.util.table.SysUserRoleUtil;
import com.dj.seal.util.table.SysUserUtil;

public class SysUserDaoImpl extends BaseDAOJDBC implements ISysUserDao {

	static Logger logger = LogManager.getLogger(SysUserDaoImpl.class.getName());
	
	private SysUserUtil table = new SysUserUtil();
	private SysUnitUtil table1 = new SysUnitUtil();
	private SysDepartmentUtil table2 = new SysDepartmentUtil();
	private PsdLastTimeUtil psdTable=new PsdLastTimeUtil();
	
	@SuppressWarnings("unchecked")
	private SysUser sysUser(String sql) {
		SysUser sysUser = new SysUser();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sysUser = (SysUser) DaoUtil.setPo(sysUser, map, table);
			return sysUser;
		} else {
			return null;
		}
	}

	@Override
	public SysUser showSysUserByUser_id(String user_id) throws DAOException {
		// 过滤单引号，防止无效的SQL语句
		// user_id=DaoUtil.quotes(user_id);
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "'";
		// sql +="' and "+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
		// + "'";
//		logger.info("getSql:"+sql);
		return sysUser(sql);
	}

	@Override
	public SysUser showSysUserByUser_id_JH(String user_id) throws DAOException {
		// 过滤单引号，防止无效的SQL语句
		// user_id=DaoUtil.quotes(user_id);
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "' and "
				+ SysUserUtil.USER_TYPE + "='" + Constants.ZXUSER_STATUS + "'";
		return sysUser(sql);
	}

	@Override
	public List<SysUser> showSysUserByUserList(String user_id)
			throws DAOException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "'";
		List<SysUser> objList = listSysUser(sql);
		return objList;
	}

	/**
	 * 根据证书dn获得用户信息
	 * 
	 * @param key_dn
	 *            证书DN值
	 * @return
	 * @throws DAOException
	 */
	@Override
	public SysUser getUserBy_keyDN(String key_dn) throws DAOException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.KEY_DN + " ='" + key_dn + "' and "
				+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS + "'";
		return sysUser(sql);
	}

	/**
	 * 根据证书sn获得用户信息
	 * 
	 * @param key_sn
	 *            证书sN值
	 * @return
	 * @throws DAOException
	 */
	@Override
	public SysUser getUserBy_keySN(String key_sn) throws DAOException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.KEY_SN + " ='" + key_sn + "' and "
				+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS + "'";
		return sysUser(sql);
	}

	@Override
	public void updateSysUser(String dept_no, String new_no)
			throws DAOException {
		// 过滤单引号，防止无效的SQL语句
		dept_no = DaoUtil.quotes(dept_no);
		new_no = DaoUtil.quotes(new_no);
		String sql = "update " + SysUserUtil.TABLE_NAME + " set "
				+ SysUserUtil.DEPT_NO + "='" + new_no + "' where "
				+ SysUserUtil.DEPT_NO + "='" + dept_no + "'";
		update(sql);

	}

	@Override
	public void deleteSysUser(String user_id) throws DAOException {
		user_id = DaoUtil.quotes(user_id);// 过滤单引号，防止无效的SQL语句
		String sql = "delete from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "'";
		String userRole="";
		userRole = "delete from " + SysUserRoleUtil.TABLE_NAME
				+ " where " + SysUserRoleUtil.USER_ID + "='" + user_id + "'";
		delete(sql);//删除用户表记录
		delete(userRole);//删除用户角色表记录
	}

	@Override
	public List<SysUser> showSysUsersByDept_no(String isJouner, String dept_no)
			throws DAOException {
		// 过滤单引号，防止无效的SQL语句
		// dept_no = DaoUtil.quotes(dept_no);
		String sql = null;
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			if (isJouner.equals("1")) {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + " like'%" + dept_no + "%' and "
						+ SysUserUtil.USER_ID + " not in '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			} else {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + " ='" + dept_no + "' and "
						+ SysUserUtil.USER_ID + " not in '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			}
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			if (isJouner.equals("1")) {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + " like'%" + dept_no + "%' and "
						+ SysUserUtil.USER_ID + " != '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			} else {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + "='" + dept_no + "' and "
						+ SysUserUtil.USER_ID + " != '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			}
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
			if (isJouner.equals("1")) {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + " like'%" + dept_no + "%' and "
						+ SysUserUtil.USER_ID + " != '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			} else {
				sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
						+ SysUserUtil.DEPT_NO + "='" + dept_no + "' and "
						+ SysUserUtil.USER_ID + " != '"
						+ Constants.USER_NAME_LOGGER + "' and "
						+ SysUserUtil.USER_TYPE + "='" + Constants.USER_STATUS
						+ "'";
			}
		}
		return listSysUser(sql);
	}

	@SuppressWarnings("unchecked")
	private List<SysUser> listSysUser(String sql) {
		List<SysUser> list_user = new ArrayList<SysUser>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SysUser user = new SysUser();
			user = (SysUser) DaoUtil.setPo(user, map, table);
			list_user.add(user);
		}
		return list_user;
	}

	@Override
	public List<SysUser> showSysUserByRole_id(Integer role_id)
			throws DAOException {
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysUserUtil.TABLE_NAME + "," + SysUserRoleUtil.TABLE_NAME
				+ " where " + SysUserUtil.USER_ID + "="
				+ SysUserRoleUtil.USER_ID + " and " + SysUserRoleUtil.ROLE_ID
				+ "='" + role_id + "' and " + SysUserRoleUtil.USER_ROLE_STATUS
				+ "='1'";
		return listSysUser(sql);
	}

	@Override
	public void updateSysUser(SysUser sysUser) throws DAOException {
		String parasStr = SysUserUtil.USER_ID + "='" + sysUser.getUser_id()
				+ "'";
		//System.out.println("birth:" + sysUser.getUser_birth());
		// sysUser = setTableByfunc(sysUser);
		String sql = ConstructSql.composeUpdateSql(sysUser, table, parasStr);
		update(sql);
	}

	@Override
	public void updateSysUser(String user_id_old, String user_name_old,
			SysUser sysUser) throws DAOException {
		String parasStr = SysUserUtil.USER_ID + "='" + user_id_old + "' and "
				+ SysUserUtil.USER_NAME + "='" + user_name_old + "'";
		// sysUser = setTableByfunc(sysUser);
		String sql = ConstructSql.composeUpdateSql(sysUser, table, parasStr);
		update(sql);
	}

	@Override
	public List<SysUser> showSysUsersBySql(String sql) throws DAOException {
		return listSysUser(sql);
	}

	@Override
	public List<SysUser> showSysUserByPageSplit(int pageIndex, int pageSize,
			String sql) throws DAOException {
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<SysUser> list = listSealTemplate(str);
		return list;
	}

	@SuppressWarnings("unchecked")
	private List<SysUser> listSealTemplate(String sql) {
		List<SysUser> list_temp = new ArrayList<SysUser>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SysUser obj = new SysUser();
			obj = (SysUser) DaoUtil.setPo(obj, map, table);
			list_temp.add(obj);
		}
		return list_temp;
	}

	@Override
	public int getUserRoleNum(String user_id) {
		String sql = "select * from " + SysUserRoleUtil.TABLE_NAME + " where "
				+ SysUserRoleUtil.USER_ID + "='" + user_id + "'";
		List<Map> list = select(sql);
		return list.size();
	}

	@Override
	public String getDeptName(String dept_no) {
		String dept_name = "";
		if (dept_no.equals(Constants.UNIT_DEPT_NO)) {// 单位
			String sql = "select " + SysUnitUtil.UNIT_NAME + " from "
					+ SysUnitUtil.TABLE_NAME + " where " + SysUnitUtil.DEPT_NO
					+ "='" + dept_no + "'";
			List<Map> list = select(sql);
			for (Map map : list) {
				SysUnit obj = new SysUnit();
				obj = (SysUnit) DaoUtil.setPo(obj, map, table1);
				dept_name = obj.getUnit_name();
			}
		} else {// 部门
			String sql = "select " + SysDepartmentUtil.DEPT_NAME + " from "
					+ SysDepartmentUtil.TABLE_NAME + " where "
					+ SysDepartmentUtil.DEPT_NO + "='" + dept_no + "'";
			List<Map> list = select(sql);
			for (Map map : list) {
				SysDepartment obj = new SysDepartment();
				obj = (SysDepartment) DaoUtil.setPo(obj, map, table2);
				dept_name = obj.getDept_name();
			}
		}
		return dept_name;
	}

	@Override
	public int showCount(String sql) throws DAOException {
		return count(sql);
	}

	@Override
	public void AddUser(SysUser user) throws DAOException {
		try {
			String unique_id = getNo(SysUserUtil.TABLE_NAME,
					SysUserUtil.UNIQUE_ID);
			user.setUnique_id(unique_id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String sql = ConstructSql.composeInsertSql(user, table);
		logger.info("sql:" + sql);
		add(sql);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isExistUser(String user_id) throws DAOException {
		user_id = DaoUtil.quotes(user_id);// 过滤单引号，防止无效的SQL语句
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id+"'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isExistorPwd(String user_id, String pwd) throws DAOException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "' and "
				+ SysUserUtil.USER_PSD + "='" + pwd + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isExistServer(String sys_id) throws DAOException {
		String sql = "select * from " + AppSystemUtil.TABLE_NAME + " where "
				+ AppSystemUtil.APP_NO + "='" + sys_id + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean zhuxiaoList(String sql) {
		try {
			update(sql); // 用户表
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void quzhuxiaoList(String user_id) {
		String sql = "update " + SysUserUtil.TABLE_NAME + " set "
				+ SysUserUtil.IS_ACTIVE + "='1' where " + SysUserUtil.USER_ID
				+ "='" + user_id + "' ";
		update(sql); // 用户表

	}

	@Override
	public SysUser editSysUser(String user_id) {
		String sql = "select *  from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + "='" + user_id + "' ";
		return sysUser(sql);
	}

	@Override
	public void updateSysUser(SysUser sysuser, String usertype2) {
		String condition = SysUserUtil.USER_ID + "='" + sysuser.getUser_id()
				+ "' and " + SysUserUtil.USER_TYPE + "='" + usertype2 + "'";
		String sql = ConstructSql.composeUpdateSql(sysuser, table, condition);
		update(sql);
	}

	@Override
	public void deleteUserDetp(String sql) {
		delete(sql);
	}

	@Override
	public void AddSysUserDEPT(String sql) {
		add(sql);
	}

	@Override
	public void updPWDByID(String sql) {
		update(sql);
	}

	@Override
	public int approveUserByUniId(String id, String flag,String state,String current_user) {// 审批
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "";
//		if ("flag1".equals(flag)) {// 同意
//			sql = "update " + SysUserUtil.TABLE_NAME + " set "
//					+ SysUserUtil.IS_APPROVE + "='1',"+SysUserUtil.OPERATE_USER+"='"+current_user+"' where "
//					+ SysUserUtil.UNIQUE_ID + "=" + id;
//		}
//		if ("flag2".equals(flag)) {// 拒绝
//			sql = "update " + SysUserUtil.TABLE_NAME + " set "
//					+ SysUserUtil.IS_APPROVE + "='2',"+SysUserUtil.STATE+"='"+state+"',"+SysUserUtil.OPERATE_USER+"='"+current_user+ "' where "
//					+ SysUserUtil.UNIQUE_ID + "=" + id;
//		}
//		logger.info("审批sql:"+sql);
//		return getJdbcTemplate().update(sql);
		return 0;
	}

	@Override
	public String getPasswordLastTime(String sql) {
		PsdLastTime psdTime = new PsdLastTime();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			psdTime = (PsdLastTime) DaoUtil.setPo(psdTime, map, psdTable);
			return psdTime.getCanssj();
		} else {
			return null;
		}
	}

}
