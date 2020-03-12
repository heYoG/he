package com.dj.seal.organise.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.DeptUtil;
import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysUnitDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.dao.api.ISysUserDeptDao;
import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.SysUserDept;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SysDepartmentUtil;
import com.dj.seal.util.table.SysUserDeptUtil;
import com.dj.seal.util.table.SysUserUtil;

public class SysDeptService implements ISysDeptService {
	
	static Logger logger = LogManager.getLogger(SysDeptService.class.getName());

	private BaseDAOJDBC dao;
	private ISysUnitDao unit_dao;
	private ISysUserDao user_dao;
	private ISysDepartmentDao dept_dao;
	private ISysUserDeptDao user_dept_dao;
	private ISysRoleService role_dao;
	
	public ISysRoleService getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(ISysRoleService role_dao) {
		this.role_dao = role_dao;
	}

	public ISysUserDeptDao getUser_dept_dao() {
		return user_dept_dao;
	}

	public void setUser_dept_dao(ISysUserDeptDao user_dept_dao) {
		this.user_dept_dao = user_dept_dao;
	}

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	
	@Override
	public String piLiangImport(String dishiname,String yingyetingname) throws Exception{
		SysDepartment shifen = getDeptByName(dishiname);
		if(shifen==null){
			SysDepartment dishi = new SysDepartment();
			dishi.setDept_name(dishiname);
			dishi.setDept_parent(Constants.UNIT_DEPT_NO);
			dishi.setDept_tab("1");
			String deptno = addDept(dishi);
			SysDepartment yingyeting = getDeptByName(yingyetingname);
			if(yingyeting==null){
				SysDepartment ting = new SysDepartment();
				ting.setDept_name(yingyetingname);
				ting.setDept_parent(deptno);
				ting.setDept_tab("1");
				addDept(ting);
			}else{
				return "失败:已存在:"+yingyetingname;
			}
		}else{
			SysDepartment yingyeting = getDeptByName(yingyetingname);
			if(yingyeting==null){
				SysDepartment ting = new SysDepartment();
				ting.setDept_name(yingyetingname);
				ting.setDept_parent(shifen.getDept_no());
				ting.setDept_tab("1");
				addDept(ting);
			}else{
				return "失败:已存在:"+yingyetingname;
			}
		}
		return "成功";
	}
	
	
	
	@Override
	public List<SysDepartment> showAllDepts() throws GeneralException {
		List<SysDepartment> list = dept_dao
				.showSubSysDepartmentsByDept_no(Constants.UNIT_DEPT_NO);
		for (SysDepartment dept : list) {
			dept.setInManage(true);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SysDepartment> showAll() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME);
		sb.append(" where 1=1 order by length(");
		sb.append(SysDepartmentUtil.DEPT_NO).append(") ,");
		sb.append(SysDepartmentUtil.DEPT_NO);
		List<SysDepartment> list = dept_dao.listDepartment(sb.toString());
		return list;
	}



	@Override
	public SysDepartment showDeptByNo(String dept_no) throws GeneralException {
		if (Constants.UNIT_DEPT_NO.equals(dept_no)) {
			SysDepartment dept = new SysDepartment();
			dept.setDept_lever(0);
			dept.setDept_name(unit_dao.showSysUnit().getUnit_name());
			dept.setDept_no(Constants.UNIT_DEPT_NO);
			dept.setFax_no(unit_dao.showSysUnit().getFax_no());
			dept.setTel_no(unit_dao.showSysUnit().getTel_no());
			return dept;
		}
		return dept_dao.showSysDepartmentByDept_no(dept_no);
	}
	

	//简单查询  只获取简单的部门对象
	public SysDepartment showDeptByNoSimple(String dept_no) throws GeneralException {
		if (Constants.UNIT_DEPT_NO.equals(dept_no)) {
			SysDepartment dept = new SysDepartment();
			dept.setDept_lever(0);
			dept.setDept_name(unit_dao.showSysUnit().getUnit_name());
			dept.setDept_no(Constants.UNIT_DEPT_NO);
			dept.setFax_no(unit_dao.showSysUnit().getFax_no());
			dept.setTel_no(unit_dao.showSysUnit().getTel_no());
			return dept;
		}
		return dept_dao.showSysDepartmentByDept_noSimple(dept_no);
	}
	
	
	@Override
	public void addDept(String user_id, DeptForm deptForm)
			throws GeneralException {
		try {
			SysDepartment sysDepartment = new SysDepartment();
			BeanUtils.copyProperties(sysDepartment, deptForm);
			// 设置剩下三个属性，系统自动生成 部门自身编号，部门编号，部门所在级别
			sysDepartment.setPriv_no(dept_dao.newPriv_no());
			sysDepartment.setDept_no(sysDepartment.getDept_parent()
					+ sysDepartment.getPriv_no());
			sysDepartment
					.setDept_lever(sysDepartment.getDept_parent().length() / 4);
			// 调用DAO方法新增部门
			sysDepartment.setIs_detpflow("1");//没有下级
			dept_dao.addSysDepartment(sysDepartment);
			// 为用户部门表新增记录
			// addUserDept(user_id, sysDepartment.getDept_no());

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}

	public String addDept(SysDepartment sysDepartment){
		sysDepartment.setPriv_no(dept_dao.newPriv_no());
		sysDepartment.setDept_no(sysDepartment.getDept_parent() + sysDepartment.getPriv_no());
		sysDepartment.setDept_lever(sysDepartment.getDept_parent().length() / 4);
		// 调用DAO方法新增部门
		sysDepartment.setIs_detpflow("1");//没有下级
		dept_dao.addSysDepartment(sysDepartment);
		return sysDepartment.getDept_no();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SysDepartment> deptTreeByUser(String user_id)
			throws GeneralException {
		Map map = new HashMap<String, SysDepartment>();
		// 得到用户管理的所有部门列表
		List<SysDepartment> list_dept = dept_dao
				.showSysDepartmentsByUser_id(user_id);
		// 构造用户管理的最大范围部门名称与部门的map
		for (SysDepartment dept : list_dept) {
			dept.setInManage(true);
			map.put(dept.getDept_no(), dept);
		}
		// 向上递归到单位，并加入部门树
		for (SysDepartment dept : list_dept) {
			map = diGui(map, dept);
		}
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		Collection<SysDepartment> col = map.values();
		for (SysDepartment sysDepartment : col) {
			list.add(sysDepartment);
		}
		// 向下递归到叶子部门，并加入到部门树
		for (SysDepartment dept : list_dept) {
			List<SysDepartment> subs = dept_dao
					.showSubSysDepartmentsByDept_no(dept.getDept_no());
			for (SysDepartment d : subs) {
				d.setInManage(true);
				list.add(d);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private Map diGui(Map map, SysDepartment dept) throws GeneralException {
		if (dept.getDept_parent().equals(Constants.UNIT_DEPT_NO)) {
			return map;
		} else {
			// 如果map里没有这个部门的父部门
			if (map.get(dept.getDept_parent()) == null) {
				SysDepartment sysDept = dept_dao
						.showSysDepartmentByDept_no(dept.getDept_parent());
				map.put(sysDept.getDept_no(), sysDept);
				map = diGui(map, sysDept);
			}
			return map;
		}
	}

	@Override
	public List<SysDepartment> deptsByUser(String user_id)
			throws GeneralException {
		return dept_dao.showSysDepartmentsByUser_id(user_id);
	}

	@Override
	public void addUserDept(String user_id, String dept_no)
			throws GeneralException {
		SysUserDept userDept = new SysUserDept();
		userDept.setDept_no(dept_no);
		userDept.setUser_id(user_id);
		user_dept_dao.addSysUserDept(userDept);

	}

	@Override
	public void updateDept(DeptForm deptForm) throws GeneralException {
		try {
			SysDepartment dept = dept_dao.showSysDepartmentByDept_no(deptForm
					.getDept_no());
			BeanUtils.copyProperties(dept, deptForm);
			String no = dept.getDept_no();
			String parent = dept.getDept_parent();
			if (no.substring(0, no.length() - 4).equals(parent)) {
				dept_dao.updateSysDepartment(dept);
			} else {
				afterChangerParent(dept);
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}
	@Override
	public void updateDept(SysDepartment dept) throws GeneralException {
		try {
			String no = dept.getDept_no();
			String parent = dept.getDept_parent();
			if (no.substring(0, no.length() - 4).equals(parent)) {
				dept_dao.updateSysDepartment(dept);
			} else {
				afterChangerParent(dept);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} 
	}
	
	public boolean getIsnull(String id){
		int count=dept_dao.getIsnull(id);
		if(count==0){
			return true;
		}
		return false;
	}
	
	public SysDepartment getDeptById(String id){
		SysDepartment dept=new SysDepartment();
		dept=dept_dao.getDeptByBankId(id);
		return dept;
	}
	
	public void updDeptById(SysDepartment dept) throws GeneralException{
		dept.setDept_no(dept.getDept_parent() + dept.getPriv_no());
		dept.setDept_lever(dept.getDept_parent().length() / 4);
		dept_dao.updateSysDepartment(dept);
	}

	/**
	 * 当更改父部门时调用，更新与部门表相关的信息 （包括部门和子部门编号信息，部门和子部门下的用户，角色，印模，印章，文档等）
	 * 
	 * @param dept
	 * @throws GeneralException
	 */
	private void afterChangerParent(SysDepartment dept) throws GeneralException {
		// 更新子部门相关信息
		List<SysDepartment> list_next = dept_dao.showNextLeverDepts(dept);
		String oldDept_no = dept.getDept_no();
		String newDept_no = dept.getDept_parent() + dept.getPriv_no();
		for (SysDepartment sysDept : list_next) {
			sysDept.setDept_parent(newDept_no);
			// 调用自身，递归思想，结束条件为list_next大小为0
			afterChangerParent(sysDept);
		}
		// 更新部门编号和级别信息
		dept.setDept_no(newDept_no);
		dept.setDept_lever(dept.getDept_parent().length() / 2);
		dept_dao.updateSysDepartment(dept, oldDept_no);
		// 更新部门下用户信息及用户部门表信息
		user_dao.updateSysUser(oldDept_no, newDept_no);
		user_dept_dao.updateSysUserDept(oldDept_no, newDept_no);
		// 更新部门下角色信息
		// role_dao.updateSysRole(oldDept_no, newDept_no);
		// 更新部门下印模信息
		
		// 更新部门下印章信息（包括印章所属部门和印章授权部门）
		// 更新部门下文档信息及文档部门表信息
		// 更新文档打印信息（被授权部门）
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

	@Override
	public String parentName(String dept_no) throws GeneralException {
		SysDepartment dept = dept_dao.showSysDepartmentByDept_no(dept_no);
		if (dept == null) {
			return null;
		}
		if (dept.getDept_parent().equals(Constants.UNIT_DEPT_NO)) {
			SysUnit unit = unit_dao.showSysUnit();
			return unit.getUnit_name();
		}
		SysDepartment parent = dept_dao.showSysDepartmentByDept_no(dept
				.getDept_parent());
		return parent.getDept_name();
	}

	public ISysUnitDao getUnit_dao() {
		return unit_dao;
	}

	public void setUnit_dao(ISysUnitDao unit_dao) {
		this.unit_dao = unit_dao;
	}
	
	public SysDepartment getDeptByName(String dept_name) throws GeneralException {
		SysDepartment dept = dept_dao.showSysDepartmentByDept_name(dept_name);
		return dept;
	}

	@Override
	public String getDeptNoByName(String dept_name) throws GeneralException {
		SysDepartment dept = dept_dao.showSysDepartmentByDept_name(dept_name);
		if (dept == null) {
			SysUnit unit = unit_dao.showSysUnit();
			return unit.getDept_no();
		}
		return dept.getDept_no();
	}

	@Override
	public int isExistDept(String dept_name) throws GeneralException {
		if (dept_dao.isExistDept(dept_name)) {
			return 1;
		} else
			return 0;
	}

	@Override
	public int isExistDept(String dept_name, String old_name)
			throws GeneralException {
		if (dept_dao.isExistDept(dept_name, old_name)) {
			return 1;
		} else
			return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int isInManageList(String dept_no, String user_id)
			throws GeneralException {
		Map map = new HashMap<String, SysDepartment>();
		// 得到用户管理的所有部门列表
		List<SysDepartment> list_dept = dept_dao
				.showSysDepartmentsByUser_id(user_id);
		for (SysDepartment dept : list_dept) {
			map.put(dept.getDept_no(), dept);
			for (SysDepartment dept2 : dept_dao
					.showSubSysDepartmentsByDept_no(dept.getDept_no())) {
				map.put(dept2.getDept_no(), dept2);
			}
		}
		if (map.get(dept_no) != null) {
			return 1;
		}
		if (Constants.USER_NAME_ADMIN.equals(user_id)
				|| Constants.USER_NAME_LOGGER.equals(user_id)) {
			return 1;
		}
		return 0;
	}

	@Override
	public void deleteDept(String dept_no) throws GeneralException {
		SysDepartment sysDepartment = dept_dao
				.showSysDepartmentByDept_no(dept_no);
		if (sysDepartment == null) {
			return;
		}
		List<SysDepartment> list = dept_dao.showNextLeverDepts(sysDepartment);
		for (SysDepartment dept : list) {
			deleteDept(dept.getDept_no());
		}
		dept_dao.deleteSysDepartment(sysDepartment);
		// 更新部门下用户信息及用户部门表信息
		user_dao.deleteSysUser(sysDepartment.getDept_no());
		user_dept_dao.deleteSysUserDept(sysDepartment.getDept_no());
		// 更新部门下角色信息
		// role_dao.updateSysRole(oldDept_no, newDept_no);
		// 更新部门下印模信息
		// 更新部门下印章信息（包括印章所属部门和印章授权部门）
		// 更新部门下文档信息及文档部门表信息
		// 更新文档打印信息（被授权部门）
	}

	@Override
	public String getDeptsByUser(String user_id) throws GeneralException {
		StringBuffer sb = new StringBuffer();
		List<SysDepartment> depts = dept_dao
				.showSysDepartmentsByUser_id(user_id);
		for (SysDepartment dept : depts) {
			sb.append(dept.getDept_name()).append(",");
		}
		return sb.toString();
	}

	@Override
	public int isExistSubDept(String dept_name, String parent_no)
			throws DAOException {
		List<SysDepartment> subs = null;
		if (Constants.UNIT_DEPT_NO.equals(parent_no)) {
			subs = dept_dao.showNextLeverDepts();
		} else {
			SysDepartment dept = dept_dao.showSysDepartmentByDept_no(parent_no);
			subs = dept_dao.showNextLeverDepts(dept);
		}
		for (SysDepartment sub : subs) {
			if (sub.getDept_name().equals(dept_name)) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int isExistSubDept(String dept_name, String parent_no,
			String old_name) throws DAOException {
		
		List<SysDepartment> subs = null;
		if (Constants.UNIT_DEPT_NO.equals(parent_no)) {
			subs = dept_dao.showNextLeverDepts();
		} else {
			SysDepartment dept = dept_dao.showSysDepartmentByDept_no(parent_no);
			subs = dept_dao.showNextLeverDepts(dept);
		}
		for (SysDepartment sub : subs) {
			
			if (sub.getDept_name().equals(dept_name)
					&& !sub.getDept_name().equals(old_name)) {
				return 1;
			}
		}
		return 0;
	}

	public BaseDAOJDBC getDao() {
		return dao;
	}

	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}

	/**
	 * 根据指定的用户列表得到其所在部门及根部门集合
	 * 
	 * @param sel
	 * @return
	 * @throws Exception
	 */
	public List<SysDepartment> showDeptByUsers(String sel) throws Exception {
		// 根据用户列表得到其所在部门列表
//		logger.info("sel:"+sel);
		//long start1 = System.currentTimeMillis();
		List<String> depts = deptNosByUsers(sel);
		//long start2 = System.currentTimeMillis();
		//logger.info("showDeptByUsers-deptNosByUsers useTime:"+(start2-start1));
		// 根据部门列表得到寻根集合
		List<String> all = DeptUtil.all(depts);
		//long start3 = System.currentTimeMillis();
		//logger.info("showDeptByUsers-all useTime:"+(start3-start2));
		// 根据部门列表得到部门对象
//		logger.info("all:"+all.size());
		return deptsByNos(all);
	}
	
	public List<SysDepartment> showDeptBySeals(String sel) throws Exception {
		// 根据印章列表得到其所在部门列表
		List<String> depts = deptNosBySeals(sel);
		// 根据部门列表得到寻根集合
		List<String> all = DeptUtil.all(depts);
		// 根据部门列表得到部门对象
		return deptsByNos(all);
	}

	/**
	 * 根据用户列表得到其所在部门列表
	 * 
	 * @param sel
	 * @return
	 * @throws Exception
	 */
	private List<String> deptNosByUsers(String sel) throws Exception {
		String[] users = sel.split(",");
		StringBuffer sb = new StringBuffer();
		String field = SysUserUtil.DEPT_NO;
		String t_name = SysUserUtil.TABLE_NAME;
		sb.append("select distinct ").append(field);
		sb.append(" from ").append(t_name).append(" where 1=2 ");
		for (String user : users) {
			sb.append(" or ").append(SysUserUtil.USER_ID);
			sb.append("='").append(user).append("'");
		}
		return dao.getStrList(sb.toString(), field);
	}
	
	private List<String> deptNosBySeals(String sel) throws Exception {
		String[] seals = sel.split(",");
		StringBuffer sb = new StringBuffer();
		String field = SealBodyUtil.DEPT_NO;
		String t_name = SealBodyUtil.TABLE_NAME;
		sb.append("select distinct ").append(field);
		sb.append(" from ").append(t_name).append(" where 1=2 ");
		for (String seal : seals) {
			sb.append(" or ").append(SealBodyUtil.SEAL_ID);
			sb.append("='").append(seal).append("'");
		}
		return dao.getStrList(sb.toString(), field);
	}

	/**
	 * 根据部门编号列表得到部门对象集合
	 * 
	 * @param nos
	 * @return
	 * @throws Exception
	 */
	private List<SysDepartment> deptsByNos(List<String> nos) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME);
		sb.append(" where 1=2 ");
		for (String no : nos) {
			sb.append(" or ").append(SysDepartmentUtil.DEPT_NO).append("='");
			sb.append(no).append("'");
		}
		sb.append(" order by length(");
		sb.append(SysDepartmentUtil.DEPT_NO).append(") ,");
		sb.append(SysDepartmentUtil.DEPT_NO);
		List<SysDepartment> list = dept_dao.listDepartment(sb.toString());
		SysUnit unit = unit_dao.showSysUnit();
		SysDepartment top = new SysDepartment();
		top.setDept_no(unit.getDept_no());
		top.setPriv_no(unit.getDept_no());
		top.setDept_lever(0);
		top.setDept_name(unit.getUnit_name());
		top.setDept_parent("-1");
		list.add(0, top);
		return list;
	}
	/**
	 * 根据用户id得到用户管理范围下的部门列表
	 * @param user_id
	 * @return SysDepartment
	 * @throws GeneralException
	 */
	@Override
	public List<SysDepartment> getDeptList(String user_id){
		List<SysDepartment> list=new ArrayList<SysDepartment>();
		SysUser user=user_dao.showSysUserByUser_id(user_id);
		String rang_type=user.getRang_type();
		if(rang_type.equals("0")){
			if(user.getIs_junior().equals("1")){
				String sql="select * from"+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+" like '%"+user.getDept_no()+"%'";
				list=dept_dao.listDepartment(sql);
				
			}if(user.getIs_junior().equals("0")){
				String sql="select * from"+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+"='"+user.getDept_no()+"'";
				list=dept_dao.listDepartment(sql);
				
			}
			
		}if(rang_type.equals("1")){
			String sql="select * from"+SysDepartmentUtil.TABLE_NAME;
		    list = dept_dao.listDepartment(sql);
			SysUnit unit = unit_dao.showSysUnit();
			SysDepartment top = new SysDepartment();
			top.setDept_no(unit.getDept_no());
			top.setPriv_no(unit.getDept_no());
			top.setDept_lever(0);
			top.setDept_name(unit.getUnit_name());
			top.setDept_parent("-1");
			list.add(0, top);
			
		}if(rang_type.equals("2")){
			if(user.getIs_junior().equals("1")){
				String sqlUserDep="select * from"+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
				String dept_no=dept_dao.listSysUserDept(sqlUserDep);
				String dept_no3[]=dept_no.split(",");
				StringBuffer sb=new StringBuffer();
				sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME+" where ");
				for(int i=0;i<dept_no3.length;i++){
					if(dept_no3.length==1){
						sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
					}else{
						if(i==0){
							sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
						}if(i>0){
							if(!dept_no3[i].trim().equals("")){
								sb.append(" or "+SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
							}						
						}		
						
					}
				}
				list=dept_dao.listDepartment(sb.toString());
				
			}if(user.getIs_junior().equals("0")){
				String sqlUserDep="select * from"+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
				String dept_no=dept_dao.listSysUserDept(sqlUserDep);
				String dept_no2="";
				int lastIndex = dept_no.lastIndexOf(',');
				if (lastIndex > -1) {
					dept_no2= dept_no.substring(0, lastIndex) + dept_no.substring(lastIndex + 1, dept_no.length());
			    }
				String sql="select * from"+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+" in('"+dept_no2+"')";
				list=dept_dao.listDepartment(sql);
			}
			
		}
		return list;
	}
	public List<SysDepartment> showDeptByUser2(String user_id) throws Exception {

			List<SysDepartment> list=new ArrayList<SysDepartment>();
			SysUser user=user_dao.showSysUserByUser_id(user_id);
			String rang_type=user.getRang_type();
			if(rang_type.equals("0")){ // 本部门       	
	    			if(user.getIs_junior().equals("1")){
	    				String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+" like '%"+user.getDept_no()+"%' order by length(C_ABA),length(C_ABF),C_ABF";
	    				list=dept_dao.listDepartment(sql);  				
	    			}else{
	    				String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+"='"+user.getDept_no()+"' order by length(C_ABA),length(C_ABF),C_ABF";
	    				list=dept_dao.listDepartment(sql);				
	    			}
	    			if(user.getDept_no().equals(Constants.UNIT_DEPT_NO)){ 
	    			SysUnit unit = unit_dao.showSysUnit();
	    			SysDepartment top = new SysDepartment();
	    			top.setDept_no(unit.getDept_no());
	    			top.setPriv_no(unit.getDept_no());
	    			top.setDept_lever(0);
	    			top.setDept_name(unit.getUnit_name());
	    			top.setDept_parent("-1");
	    			list.add(0, top);
	    			}
			}if(rang_type.equals("2")){ // 指定部门
				if(user.getIs_junior().equals("1")){
					String sqlUserDep="select * from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";					
					String dept_no=dept_dao.listSysUserDept(sqlUserDep);
					String dept_no3[]=dept_no.split(",");
					StringBuffer sb=new StringBuffer();
					sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME+" where ");
					for(int i=0;i<dept_no3.length;i++){
						if(dept_no3.length==1){
							sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
						}else{
							if(i==0){
								sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
							}if(i>0){
								if(dept_no3[i]!=""){
									sb.append(" or "+SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
								}						
							}	
						}
					}
					list=dept_dao.listDepartment(sb.toString());
					for(int i=0;i<dept_no3.length;i++){
						if(dept_no3[i].equals(Constants.UNIT_DEPT_NO)){
							SysUnit unit = unit_dao.showSysUnit();
							SysDepartment top = new SysDepartment();
							top.setDept_no(unit.getDept_no());
							top.setPriv_no(unit.getDept_no());
							top.setDept_lever(0);
							top.setDept_name(unit.getUnit_name());
							top.setDept_parent("-1");
							list.add(0, top);
						}
					}
				}else if(user.getIs_junior().equals("0")){
					String sqlUserDep="select * from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
					String dept_no=dept_dao.listSysUserDept(sqlUserDep);
					String dept_noAll[]=dept_no.split(",");
					String dept_no2="";
					String dept_no3="";
					for(int i=0;i<dept_noAll.length;i++){
						if((dept_noAll[i]!="")||(dept_noAll[i]!=null)){
							dept_no3+="'"+dept_noAll[i]+"',";
						}
					}
					int lastIndex = dept_no3.lastIndexOf(',');
					if (lastIndex > -1) {
						dept_no2= dept_no3.substring(0, lastIndex) + dept_no3.substring(lastIndex + 1, dept_no3.length());
				    }
					String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+" in("+dept_no2+") order by length(C_ABA),length(C_ABF),C_ABF";
					list=dept_dao.listDepartment(sql);
					for(int i=0;i<dept_noAll.length;i++){
						if(dept_noAll[i].equals(Constants.UNIT_DEPT_NO)){
							SysUnit unit = unit_dao.showSysUnit();
							SysDepartment top = new SysDepartment();
							top.setDept_no(unit.getDept_no());
							top.setPriv_no(unit.getDept_no());
							top.setDept_lever(0);
							top.setDept_name(unit.getUnit_name());
							top.setDept_parent("-1");
							list.add(0, top);
						}
					}					
				}
			}if(rang_type.equals("1")){ // 全体
				String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" order by length(C_ABA),length(C_ABF),C_ABF";	
			    list = dept_dao.listDepartment(sql);
				SysUnit unit = unit_dao.showSysUnit();
				SysDepartment top = new SysDepartment();
				top.setDept_no(unit.getDept_no());
				top.setPriv_no(unit.getDept_no());
				top.setDept_lever(0);
				top.setDept_name(unit.getUnit_name());
				top.setDept_parent("-1");
				list.add(0, top);
			}
			return list;
		}
	@Override
	public List<SysDepartment> showDeptByUser(String user_id) throws Exception {
		long startTime = System.currentTimeMillis();
		List<SysDepartment> list=new ArrayList<SysDepartment>();
		SysUser user=user_dao.showSysUserByUser_id(user_id);
		String rang_type=user.getRang_type();
		if(rang_type.equals("0")){ // 本部门        	
    			if (user.getIs_junior().equals("1")) {
				String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
						+ " where " + SysDepartmentUtil.DEPT_NO + " like '%"
						+ user.getDept_no()
						+ "%' order by length(C_ABA),length(C_ABF),C_ABF";
				list = dept_dao.listDepartment(sql);
			} else {
				String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
						+ " where " + SysDepartmentUtil.DEPT_NO + "='"
						+ user.getDept_no()
						+ "' order by length(C_ABA),length(C_ABF),C_ABF";
				list = dept_dao.listDepartment(sql);
			}
			if (user.getDept_no().equals(Constants.UNIT_DEPT_NO)) {
				SysUnit unit = unit_dao.showSysUnit();
				SysDepartment top = new SysDepartment();
				top.setDept_no(unit.getDept_no());
				top.setPriv_no(unit.getDept_no());
				top.setDept_lever(0);
				top.setDept_name(unit.getUnit_name());
				top.setDept_parent("-1");
				list.add(0, top);
			}
		}if(rang_type.equals("2")){ // 指定部门
			if(user.getIs_junior().equals("1")){
				String sqlUserDep="select * from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
				String dept_no=dept_dao.listSysUserDept(sqlUserDep);
				String dept_no3[]=dept_no.split(",");
				StringBuffer sb=new StringBuffer();
				sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME+" where ");
				for(int i=0;i<dept_no3.length;i++){
					if(dept_no3.length==1){
						sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
					}else{
						if(i==0){
							sb.append(SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
						}if(i>0){
							if(dept_no3[i]!=""){
								sb.append(" or "+SysDepartmentUtil.DEPT_NO+" like '%"+dept_no3[i]+"%'");
							}						
						}								
					}
				}		
				sb.append(" order by length(C_ABA),length(C_ABF),C_ABF");
				list=dept_dao.listDepartment(sb.toString());
				for(int i=0;i<dept_no3.length;i++){
					if(dept_no3[i].equals(Constants.UNIT_DEPT_NO)){
						SysUnit unit = unit_dao.showSysUnit();
						SysDepartment top = new SysDepartment();
						top.setDept_no(unit.getDept_no());
						top.setPriv_no(unit.getDept_no());
						top.setDept_lever(0);
						top.setDept_name(unit.getUnit_name());
						top.setDept_parent("-1");
						list.add(0, top);
					}
				}
			}else if(user.getIs_junior().equals("0")){
				String sqlUserDep="select * from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
				String dept_no=dept_dao.listSysUserDept(sqlUserDep);
				String dept_noAll[]=dept_no.split(",");
				String dept_no2="";
				String dept_no3="";
				for(int i=0;i<dept_noAll.length;i++){
					if((dept_noAll[i]!="")||(dept_noAll[i]!=null)){
						dept_no3+="'"+dept_noAll[i]+"',";
					}
				}
				int lastIndex = dept_no3.lastIndexOf(',');
				if (lastIndex > -1) {
					dept_no2= dept_no3.substring(0, lastIndex) + dept_no3.substring(lastIndex + 1, dept_no3.length());
			    }
				String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_NO+" in("+dept_no2+") order by length(C_ABA),length(C_ABF),C_ABF";
				list=dept_dao.listDepartment(sql);
				for(int i=0;i<dept_noAll.length;i++){
					if(dept_noAll[i].equals(Constants.UNIT_DEPT_NO)){
						SysUnit unit = unit_dao.showSysUnit();
						SysDepartment top = new SysDepartment();
						top.setDept_no(unit.getDept_no());
						top.setPriv_no(unit.getDept_no());
						top.setDept_lever(0);
						top.setDept_name(unit.getUnit_name());
						top.setDept_parent("-1");
						list.add(0, top);
					}
				}					
			}
		}if(rang_type.equals("1")){ // 全体
			String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" order by length(C_ABA),length(C_ABF),C_ABF";	
		    list = dept_dao.listDepartment(sql);
			SysUnit unit = unit_dao.showSysUnit();
			SysDepartment top = new SysDepartment();
			top.setDept_no(unit.getDept_no());
			top.setPriv_no(unit.getDept_no());
			top.setDept_lever(0);
			top.setDept_name(unit.getUnit_name());
			top.setDept_parent("-1");
			list.add(0, top);
		}
		logger.info("showDeptByUser:userTime"+(System.currentTimeMillis()-startTime));
		return list;
	}
	@Override
	public String getUserList(String userid) throws GeneralException {
		String userIdStr = "";
		long start1 = System.currentTimeMillis();
		List<SysUser> UserList = new ArrayList<SysUser>();
		SysUser objUser=user_dao.showSysUserByUser_id(userid);
		long start2 = System.currentTimeMillis();
		logger.info("showSysUserByUser_id::"+(start2-start1));
		UserList = user_dao.showSysUsersByDept_no(objUser.getIs_junior(),objUser.getDept_no());// 查询管理下的用户
		long start3 = System.currentTimeMillis();
		logger.info("showSysUsersByDept_no::"+(start3-start2));
		for (SysUser sysuser : UserList) {
			userIdStr += sysuser.getUser_id() + ",";
		}
		long start4 = System.currentTimeMillis();
		logger.info("foreach::"+(start4-start3));
		return userIdStr;
	}
	@Override
	public List<SysUser> showUsersbyDept(String dept_no) throws Exception {
		List<SysUser> userList = new ArrayList<SysUser>();
		String is_jouner="1";
		userList = user_dao.showSysUsersByDept_no(is_jouner,dept_no);
		return userList;
	}
	//以下为展示部门列表所用代码，对应new_dept_tree.jsp、new_dept_tree.js
	//author:zyl
	
	/**
	 * 根据部门私有编号获得部门信息
	 * 
	 * @param priv_no
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showDeptByPrivNo(String priv_no) throws GeneralException {
		if (Constants.UNIT_DEPT_NO.equals(priv_no)) {
			SysDepartment dept = new SysDepartment();
			dept.setDept_lever(0);
			dept.setDept_name(unit_dao.showSysUnit().getUnit_name());
			dept.setDept_no(Constants.UNIT_DEPT_NO);
			dept.setFax_no(unit_dao.showSysUnit().getFax_no());
			dept.setTel_no(unit_dao.showSysUnit().getTel_no());
			return dept;
		}
		return dept_dao.showSysDepartmentByPriv_no(priv_no);
	}
	
	//根据用户ID获得部门列表
	public List<SysDepartment> showNewDeptTreeByUser(String user_id, String priv_no,
			boolean io) throws Exception {
//		logger.info("showNewDeptTreeByUser:"+user_id);
//		logger.info("showNewDeptTreeByUser:"+priv_no);
//		logger.info("showNewDeptTreeByUser:"+io);
		SysUser user = user_dao.showSysUserByUser_id(user_id);// 用户对象
		SysDepartment dept = showDeptByPrivNo(priv_no);// 部门对象
		String rang_type = user.getRang_type();
		String is_junior = user.getIs_junior();//0为不包含下级，1为包含下级
		String user_dept = user.getDept_no();
//		logger.info(rang_type+"  "+is_junior+"  "+user_dept);
		// 本部门
		if (rang_type.equals("0")) {
			return deptTree0(user_id, dept, io,is_junior,user_dept);
		}
		// 指定部门
		else if (rang_type.equals("2")) {
			return deptTree2(user_id, dept, io,is_junior);
		}
		// 全体
		else if (rang_type.equals("1")) {
			return deptTree1(user_id, dept, io);
		}
		return getDeptTree(showDeptByUser(user_id), priv_no, io);
	}
	
	
	private String getSubStrSql(String p_no){
		StringBuffer sb = new StringBuffer();
		if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){
			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
			sb.append("=substr('").append(p_no).append("',0,length(");
			sb.append(SysDepartmentUtil.DEPT_PARENT).append(")) ");
		}else if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){
			//mysql4不支持substr，支持substring
			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
			sb.append("=substring('").append(p_no).append("',1,length(");
			sb.append(SysDepartmentUtil.DEPT_PARENT).append(")) ");
		}else if(Constants.DB_TYPE==DBTypeUtil.DT_DB2){
			//db2 substr函数是从1开始计算
			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
			sb.append("=substr('").append(p_no).append("',1,length(");
			sb.append(SysDepartmentUtil.DEPT_PARENT).append(")) ");
		}
		return sb.toString();
	}
	
	
	/**
	 * 本部门
	 * 
	 * @param user_id
	 * @param dept
	 * @param io
	 * @return
	 * @throws Exception
	 */
	private List<SysDepartment> deptTree0(String user_id, SysDepartment dept,
			boolean io,String is_junior,String user_dept) throws Exception {
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME);
		if (dept == null) {
			if(user_dept.length()<9){
				if(is_junior.equals("1")){
					sb.append(" where length(").append(SysDepartmentUtil.DEPT_NO);
					sb.append(")<10 order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
					list = dept_dao.listDepartment(sb.toString());
					list = addUnit(list);
				}else{
					sb.append(" where ").append(SysDepartmentUtil.DEPT_NO);
					sb.append("='").append(user_dept).append("'");
					list = dept_dao.listDepartment(sb.toString());
					if(user_dept.equals(Constants.UNIT_DEPT_NO)){
						list = addUnit(list);
					}
				}
			}else{
				if(is_junior.equals("1")){
					sb.append(" where ").append(SysDepartmentUtil.DEPT_NO);
					sb.append(" like '%").append(user_dept).append("%'");
					sb.append(" and (length(").append(SysDepartmentUtil.DEPT_NO);
					sb.append(")=").append(user_dept.length()+3);
					sb.append(" or length(").append(SysDepartmentUtil.DEPT_NO);
					sb.append(")=").append(user_dept.length()).append(")");
					sb.append(" order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
					list = dept_dao.listDepartment(sb.toString());
				}else{
					sb.append(" where ").append(SysDepartmentUtil.DEPT_NO);
					sb.append("='").append(user_dept).append("'");
					list = dept_dao.listDepartment(sb.toString());
				}
			}
		} else {
			int len = dept.getDept_no().length();
			String p_no = dept.getDept_parent();
			// 平辈和前辈:查找同父兄弟、嫡系前辈及嫡系前辈兄弟
			sb.append(" where (length(").append(SysDepartmentUtil.DEPT_NO)
					.append(")<=");
			sb.append(len).append(" and ").append("length(").append(
					SysDepartmentUtil.DEPT_NO);
			sb.append(")>").append(user_dept.length());
			sb.append(getSubStrSql(p_no));
//			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
//			sb.append("=substr('").append(p_no).append("',0,length(");
//			sb.append(SysDepartmentUtil.DEPT_PARENT).append(")) ");
			sb.append(" or ").append(SysDepartmentUtil.DEPT_NO).append(" = '")
					.append(user_dept).append("')");
			if (io) {
				// 晚辈:查找子级
				sb.append(" or (length(").append(SysDepartmentUtil.DEPT_NO)
						.append(")=");
				sb.append(len + 4).append(" and ").append(
						SysDepartmentUtil.DEPT_PARENT);
				sb.append("='").append(dept.getDept_no()).append("')");
			}
			sb.append(" order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
			list = dept_dao.listDepartment(sb.toString());
		}
		return list;
	}

	/**
	 * 指定部门
	 * 
	 * @param user_id
	 * @param dept
	 * @param io
	 * @return
	 * @throws Exception
	 */
	private List<SysDepartment> deptTree2(String user_id, SysDepartment dept,
			boolean io,String is_junior) throws Exception {
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME);
		if (dept == null) {
			String dept_sql = "select " + SysUserDeptUtil.DEPT_NO + " from "
					+ SysUserDeptUtil.TABLE_NAME + " where "
					+ SysUserDeptUtil.USER_ID + " ='" + user_id + "'";
			String dept_str = dao.getStr(dept_sql, SysUserDeptUtil.DEPT_NO);
			String middle_sql = " where 1=2 ";
			if(dept_str==null||dept_str.equals("")){
				return null;
			}else{
				String[] dept_nos = dept_str.split(",");
				for(int i=0;i<dept_nos.length;i++){
					if(dept_nos[i].length()<9){
						if(is_junior.equals("1")){
							middle_sql = " where length("+SysDepartmentUtil.DEPT_NO+")<10 ";
						}else{
							middle_sql = " where "+SysDepartmentUtil.DEPT_NO+"='"+dept_nos[i]+"' ";
						}
						break;
					}else{
						if(is_junior.equals("1")){
							middle_sql+=" or ("+SysDepartmentUtil.DEPT_NO+" like '%"+dept_nos[i]+"%' ";
							middle_sql+=" and (length("+SysDepartmentUtil.DEPT_NO+")="+(dept_nos[i].length()+4);
							middle_sql+=" or length("+SysDepartmentUtil.DEPT_NO+")="+(dept_nos[i].length())+"))";
						}else{
							middle_sql+=" or "+SysDepartmentUtil.DEPT_NO+" = '"+dept_nos[i]+"' ";
						}
					}
				}
			}
			sb.append(middle_sql);
			sb.append(" order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
			list = dept_dao.listDepartment(sb.toString());
		} else {
			String dept_sql = "select " + SysUserDeptUtil.DEPT_NO + " from "
					+ SysUserDeptUtil.TABLE_NAME + " where "
					+ SysUserDeptUtil.USER_ID + " ='" + user_id + "'";
			String dept_str = dao.getStr(dept_sql, SysUserDeptUtil.DEPT_NO);
			String[] dept_nos = dept_str.split(",");
			int mindeptlength = 20;
			if(dept_nos.length!=0){
				for(int i=0;i<dept_nos.length;i++){
					if(dept_nos[i].length()<mindeptlength){
						mindeptlength = dept_nos[i].length();
					}
				}
			}
	//logger.info(dept.getDept_no());
	//logger.info(dept.getDept_name());
			int len = dept.getDept_no().length();
			String p_no = dept.getDept_parent();
			// 平辈和前辈:查找同父兄弟、嫡系前辈及嫡系前辈兄弟
			sb.append(" where (length(").append(SysDepartmentUtil.DEPT_NO)
					.append(")<=");
			sb.append(len);
			sb.append(" and ");
			sb.append(" length(").append(SysDepartmentUtil.DEPT_NO)
			.append(")>");
			sb.append(mindeptlength);
			sb.append(getSubStrSql(p_no));
//			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
//			sb.append("=substr('").append(p_no).append("',0,length(");
//			sb.append(SysDepartmentUtil.DEPT_PARENT).append("))) ");
			
			String middle_sql = "";
			if(dept_str==null||dept_str.equals("")){
				return null;
			}else{
				for(int i=0;i<dept_nos.length;i++){
					if(dept_nos[i].length()<9){
						if(is_junior.equals("1")){
							middle_sql = " and length("+SysDepartmentUtil.DEPT_NO+")<10 ";
						}else{
							middle_sql = " and "+SysDepartmentUtil.DEPT_NO+"='"+dept_nos[i]+"' ";
						}
						break;
					}else{
						if(is_junior.equals("1")){
							middle_sql+=" or ("+SysDepartmentUtil.DEPT_NO+" like '%"+dept_nos[i]+"%' ";
							middle_sql+=" and (length("+SysDepartmentUtil.DEPT_NO+")="+(dept_nos[i].length()+4);
							middle_sql+=" or length("+SysDepartmentUtil.DEPT_NO+")="+(dept_nos[i].length())+"))";
						}else{
							middle_sql+=" or "+SysDepartmentUtil.DEPT_NO+" = '"+dept_nos[i]+"' ";
						}
					}
				}
			}
			
			sb.append(middle_sql);
			
			if (io) {
				// 晚辈:查找子级
				sb.append(" or (length(").append(SysDepartmentUtil.DEPT_NO)
						.append(")=");
				sb.append(len + 4).append(" and ").append(
						SysDepartmentUtil.DEPT_PARENT);
				sb.append("='").append(dept.getDept_no()).append("')");
			}
			sb.append(" order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
	//logger.info(sb.toString());
			list = dept_dao.listDepartment(sb.toString());
		}
		return list;
	}

	/**
	 * 全体
	 * 
	 * @param user_id
	 * @param dept
	 * @param io
	 * @return
	 * @throws Exception
	 */
	private List<SysDepartment> deptTree1(String user_id, SysDepartment dept,
			boolean io) throws Exception {
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysDepartmentUtil.TABLE_NAME);
		if (dept == null) {
			sb.append(" where length(").append(SysDepartmentUtil.DEPT_NO);
			sb.append(")<9 order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
		//logger.info("deptTree1--dept == null:"+sb.toString());
			list = dept_dao.listDepartment(sb.toString());
		} else {
			int len = dept.getDept_no().length();
			String p_no = dept.getDept_parent();
			// 平辈和前辈:查找同父兄弟、嫡系前辈及嫡系前辈兄弟
			sb.append(" where (length(").append(SysDepartmentUtil.DEPT_NO)
					.append(")<=");
			sb.append(len);
			sb.append(getSubStrSql(p_no));
//			sb.append(" and ").append(SysDepartmentUtil.DEPT_PARENT);
//			sb.append("=substr('").append(p_no).append("',0,length(");
//			sb.append(SysDepartmentUtil.DEPT_PARENT).append(")) ");
			sb.append(")");
			if (io) {
				// 晚辈:查找子级
				sb.append(" or (length(").append(SysDepartmentUtil.DEPT_NO)
						.append(")=");
				sb.append(len + 4).append(" and ").append(
						SysDepartmentUtil.DEPT_PARENT);
				sb.append("='").append(dept.getDept_no()).append("')");
			}
			sb.append(" order by length(C_ABA),length(C_ABF),C_ABF,C_ABB");
//			logger.info("deptTree1:"+sb.toString());
			list = dept_dao.listDepartment(sb.toString());
		}
		list = addUnit(list);
		return list;
	}
	
	
	/**
	 * 在已有的部门列表中根据某部门的私有编号得到针对此部门的部门主干树列表
	 * 
	 * @param list
	 *            已经部门列表
	 * @param priv_no
	 *            部门私有编号
	 * @param io
	 *            是否包含下级
	 * @return
	 * @throws Exception
	 */
	public List<SysDepartment> getDeptTree(List<SysDepartment> list,
			String priv_no, boolean io) throws Exception {
		List<SysDepartment> new_list = new ArrayList<SysDepartment>();
		for (SysDepartment dept : list) {
			if (dept.getDept_no().length() > 5) {
				if (dept.getDept_parent().length() == 4) {
					new_list.add(dept);
				} else {
					SysDepartment d = showDeptByPrivNo(priv_no);
					// 小辈
					if (dept.getDept_no().length() > d.getDept_no().length()) {
						if (dept.getDept_parent().equals(d.getDept_no()) && io) {
							new_list.add(dept);
						} else {
							// list.remove(dept);
						}
					} else if (dept.getDept_no().length() == d.getDept_no().length()) {// 同辈
						if (dept.getDept_parent().equals(d.getDept_parent())) {
							new_list.add(dept);
						} else {
							// list.remove(dept);
						}
					} else {// 长辈
						if (d.getDept_no().indexOf(dept.getDept_parent()) != -1) {
							new_list.add(dept);
						} else {
							// list.remove(dept);
						}
					}
				}
			} else {
				new_list.add(dept);
			}
		}
		return new_list;
		// return list;
	}
	
	//获得顶级部门
	private List<SysDepartment> addUnit(List<SysDepartment> list)
			throws Exception {
		SysUnit unit = unit_dao.showSysUnit();
		SysDepartment top = new SysDepartment();
		top.setDept_no(unit.getDept_no());
		top.setPriv_no(unit.getDept_no());
		top.setDept_lever(0);
		top.setDept_name(unit.getUnit_name());
		top.setDept_parent("-1");
		top.setTree_id(DeptUtil.change(Constants.UNIT_DEPT_NO));
		top.setP_tree_id(-1);
		list.add(0, top);
		return list;
	}

	@Override
	public SysDepartment showDeptByBankNo(String bankNo) {
			if (Constants.UNIT_DEPT_NO.equals(bankNo)) {
				SysDepartment dept = new SysDepartment();
				dept.setDept_lever(0);
				dept.setDept_name(unit_dao.showSysUnit().getUnit_name());
				dept.setDept_no(Constants.UNIT_DEPT_NO);
				dept.setFax_no(unit_dao.showSysUnit().getFax_no());
				dept.setTel_no(unit_dao.showSysUnit().getTel_no());
				return dept;
			}
			return dept_dao.showSysDepartmentByBank_no(bankNo);
		}

	@Override
	public String getMaxDeptTab(String deptNo) {
		String ret = "";
		try {
			ret = dao.getDeptMaxTabNo(SysDepartmentUtil.DEPT_TAB, deptNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public SysDepartment showDeptByDept_No(String dept_no){
		SysDepartment dept = dept_dao.showSysDepartmentByDept_no(dept_no);
		return dept;
	}

	@Override
	public SysDepartment showSysDepartmentByDept_no(String dept_no)
			throws DAOException {
		return dept_dao.showSysDepartmentByDept_no(dept_no);
	}

	@Override
	public SysDepartment getParentNo(String orgUnit){
		SysDepartment dept=null;
		if (Constants.UNIT_DEPT_NO.equals(orgUnit)) {
			dept = new SysDepartment();
			SysUnit su = unit_dao.showSysUnit();
			dept.setDept_lever(0);
			dept.setDept_name(su.getUnit_name());
			dept.setDept_no(Constants.UNIT_DEPT_NO);
			dept.setFax_no(su.getFax_no());
			dept.setTel_no(su.getTel_no());
			return dept;
		}
		return dept_dao.getParentNo(orgUnit);
	}

	@Override
	public SysDepartment getDeptNo(String parentNo) {
		return dept_dao.getDeptNo(parentNo);
	}
}
