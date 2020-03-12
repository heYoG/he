package com.dj.seal.organise.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.exception.DAOException;

public interface ISysDepartmentDao {
	  
	/**
	 * 根据部门私有编号获得部门信息
	 * 
	 * @param priv_no
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showSysDepartmentByPriv_no(String priv_no)
			throws DAOException;

	/**
	 * 查询部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public List<SysDepartment> listDepartment(String sql);
	/**
	 * 查询用户部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public String listSysUserDept(String sql);

	/**
	 * 增加部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public void addSysDepartment(SysDepartment sysDepartment)
			throws DAOException;
	/**
	 * 根据父部门编号查询子部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public boolean serSysDepartment(String parentdeptno)
			throws DAOException;
	/**
	 * 判断部门名是否存在
	 * 
	 * @param dept_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistDept(String dept_name) throws DAOException;

	/**
	 * 判断除了old_name外部门名dept_name是否与其他部门重名
	 * 
	 * @param dept_name
	 * @param old_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistDept(String dept_name, String old_name)
			throws DAOException;

	/**
	 * 根据部门名称得到部门信息
	 * 
	 * @param dept_name
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showSysDepartmentByDept_name(String dept_name)
			throws DAOException;

	public SysDepartment showSysDepartmentByDept_nameSimple(String dept_name)
	throws DAOException;
	
	/**
	 * 修改部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public void updateSysDepartment(SysDepartment sysDepartment)
			throws DAOException;

	/**
	 * 根据旧部门编号修改部门
	 * 
	 * @param sysDepartment
	 * @param old_no
	 * @throws DAOException
	 */
	public void updateSysDepartment(SysDepartment sysDepartment, String old_no)
			throws DAOException;

	/**
	 * 根据SQL语句修改部门表
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void updateSysDepartmentBySql(String sql) throws DAOException;

	/**
	 * 根据对象删除部门
	 * 
	 * @param sysDepartment
	 * @throws DAOException
	 */
	public void deleteSysDepartment(SysDepartment sysDepartment)
			throws DAOException;

	/**
	 * 根据SQL语句删除部门
	 * 
	 * @param sql
	 * @throws DAOException
	 */
	public void deleteSysDepartmentBySql(String sql) throws DAOException;

	/**
	 * 根据部门编号获得部门信息
	 * 
	 * @param dept_no
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showSysDepartmentByDept_no(String dept_no)
			throws DAOException;
	
	/**
	 * 根据银行序列号获取相应的部门
	 * @param bank_no
	 * @return
	 */
	public SysDepartment showSysDepartmentByBank_no(String bank_no)throws DAOException;
	
	public SysDepartment showSysDepartmentByDept_noSimple(String dept_no)
	throws DAOException;
	 /** 根据部门编号获得部门名称
	 * 
	 * @param dept_no
	 * @return
	 * @throws DAOException
	 */
	public String getDepartName(String dept_no)
			throws DAOException;


	/**
	 * 根据SQL语句获得部门信息
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showSysDepartmentBySql(String sql) throws DAOException;

	/**
	 * 根据部门编号获得子部门的集合
	 * 
	 * @param dept_no
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showSubSysDepartmentsByDept_no(String dept_no)
			throws DAOException;

	/**
	 * 返回用户管理的所有部门集合
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showSysDepartmentsByUser_id(String user_id)
			throws DAOException;

	/**
	 * 返回对文档可见的所有部门集合
	 * 
	 * @param doc_no
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showSysDepartmentsByDoc_no(String doc_no)
			throws DAOException;

	/**
	 * 根据SQL语句获得部门集合
	 * 
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showSysDepartmentsBySql(String sql)
			throws DAOException;

	/**
	 * 返回系统自动生成的部门自身编号
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String newPriv_no() throws DAOException;

	/**
	 * 判断此部门名是否存在
	 * 
	 * @param dept_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isSysDepartmentExist(String dept_name) throws DAOException;

	/**
	 * 返回部门的下级部门集合
	 * 
	 * @param sysDepartment
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showNextLeverDepts(SysDepartment sysDepartment)
			throws DAOException;

	/**
	 * 返回一级部门集合
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showNextLeverDepts() throws DAOException;

	/**
	 * 返回同级部门集合
	 * 
	 * @param sysDepartment
	 * @return
	 * @throws DAOException
	 */
	public List<SysDepartment> showSameLeverDepts(SysDepartment sysDepartment)
			throws DAOException;

	/**
	 * 根据用户名称得到部门信息
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public SysDepartment showSysDepartmentByUser_id(String user_id)
			throws DAOException;

	/**
	 * 根据用户名得到用户的管理范围
	 * 
	 * @param user_id
	 * @return
	 * @throws DAOException
	 */
	public String manageRangeOfUser(String user_id) throws DAOException;
	
	/**
	 * 根据银行的ID获取相应的部门
	 * @param bankId
	 * @return
	 */
	public SysDepartment getDeptByBankId(String bankId);
	
	
	public int getIsnull(String id);
	
	/**
	 * by hyg20171227
	 * 获取部门编号、父部门编号
	 * @param orgUnit 组织机构号
	 * @return 部门对象
	 */
	public SysDepartment getParentNo(String orgUnit);
	
	/**
	 * by hyg20171227
	 * 获取部门信息
	 * @param parentNo 父部门编号作为部门编号向上查询
	 * @return 部门对象
	 */
	public SysDepartment getDeptNo(String parentNo);

}
