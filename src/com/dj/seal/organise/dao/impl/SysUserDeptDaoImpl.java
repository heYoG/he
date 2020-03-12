package com.dj.seal.organise.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysUserDeptDao;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUserDept;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SysUserDeptUtil;

public class SysUserDeptDaoImpl extends BaseDAOJDBC implements ISysUserDeptDao {
	
	static Logger logger = LogManager.getLogger(SysUserDeptDaoImpl.class.getName());

	private SysUserDeptUtil table = new SysUserDeptUtil();
	private ISysDepartmentDao dept_dao;

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	/**
	 * 判断数据库是否已经包含这个管理范围
	 * @param userDept
	 * @return
	 * @throws DAOException
	 */
	private boolean isContants(SysUserDept userDept) throws DAOException{
		List<SysDepartment> depts=dept_dao.showSysDepartmentsByUser_id(userDept.getUser_id());
		for (SysDepartment dept : depts) {
			if(userDept.getDept_no().indexOf(dept.getDept_no())!=-1){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void addSysUserDept(SysUserDept userDept) throws DAOException {
//		if(isContants(userDept)){
//			return;
//		}
		String sql = ConstructSql.composeInsertSql(userDept, table);
		add(sql);
	}

	@Override
	public void updateSysUserDept(String sql) throws DAOException {
		update(sql);
	}

	@Override
	public void updateSysUserDept(String dept_no, String new_no)
			throws DAOException {
		String sql_user_dept = "update " + SysUserDeptUtil.TABLE_NAME + " set "
				+ SysUserDeptUtil.DEPT_NO + "='" + new_no + "' where "
				+ SysUserDeptUtil.DEPT_NO + "='" + dept_no + "'";
		update(sql_user_dept);
	}

	@Override
	public void deleteSysUserDept(String dept_no) throws DAOException {
		String sql = "delete from " + SysUserDeptUtil.TABLE_NAME + " where "
				+ SysUserDeptUtil.DEPT_NO + "='" + dept_no + "'";
		delete(sql);
	}

	@Override
	public void addSysUserDept(String user_id, String dept_no)
			throws DAOException {
		String dept_noAll[]=dept_no.split(",");
		for(int i=0;i<dept_noAll.length;i++){
		 if((dept_noAll[i]!="")||(dept_noAll[i]!=null)){
			 String sql="insert into "+SysUserDeptUtil.TABLE_NAME+" values('"+user_id+"','"+dept_noAll[i]+"')";
		     add(sql); 
		 }	
		}
//		SysUserDept userDept=new SysUserDept();
//		userDept.setDept_no(dept_no);
//		userDept.setUser_id(user_id);
	//	addSysUserDept(userDept);	
	}

	@Override
	public void deleteSysUserDeptByUser_id(String user_id) throws DAOException {
		String sql = "delete from " + SysUserDeptUtil.TABLE_NAME + " where "
				+ SysUserDeptUtil.USER_ID + "='" + user_id + "'";
		delete(sql);
	}

}
