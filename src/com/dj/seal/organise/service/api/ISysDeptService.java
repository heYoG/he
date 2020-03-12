package com.dj.seal.organise.service.api;
import java.util.List;

import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
public interface ISysDeptService {

	//批量导入营业厅   参数为 地市名称（市分公司名称）   营业厅名称
	public String piLiangImport(String dishiname,String yingyetingname) throws Exception;
	
	/**
	 * 根据用户id得到用户管理范围下的部门列表
	 * @param user_id
	 * @return SysDepartment
	 * @throws GeneralException
	 */
	public List<SysDepartment> getDeptList(String user_id) throws GeneralException;

	/**
	 * 根据用户id和用户所在部门层数得到用户列表
	 * @param user_id
	 * @return SysDepartment
	 * @throws GeneralException
	 */
	public String getUserList(String user_id) throws GeneralException;
	/**
	 * 判断是否存在部门名
	 * @param dept_name
	 * @return "1"为存在，"0"为不存在
	 * @throws GeneralException
	 */
	public int isExistDept(String dept_name) throws GeneralException;
	
	/**
	 * 根据用户ID获得用户部门管理列表字符串
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public String getDeptsByUser(String user_id) throws GeneralException;
	
	/**
	 * 判断除了old_name外部门名dept_name是否与其他部门重名
	 * @param dept_name
	 * @param old_name
	 * @return "1"为存在，"0"为不存在
	 * @throws DAOException
	 */
	public int isExistDept(String dept_name,String old_name) throws GeneralException;
	
	/**
	 * 判断parent_no下级是否存在部门名为dept_name的部门
	 * @param dept_name
	 * @param parent_no
	 * @return
	 * @throws DAOException
	 */
	public int isExistSubDept(String dept_name,String parent_no) throws DAOException;
	/**
	 * 判断parent_no下级除了old_name外部门名dept_name是否与其他部门重名
	 * @param dept_name
	 * @param parent_no
	 * @return
	 * @throws DAOException
	 */
	public int isExistSubDept(String dept_name,String parent_no,String old_name) throws DAOException;
	
	/**
	 * 检查部门是否在用户的管理范围内
	 * @param dept_no
	 * @param user_id
	 * @return "1"为在，"0"为不在
	 * @throws GeneralException
	 */
	public int isInManageList(String dept_no,String user_id) throws GeneralException;
	
	/**
	 *  删除部门和部门下属部门
	 * @param dept_no
	 * @throws GeneralException
	 */
	public void deleteDept(String dept_no) throws GeneralException;
	
	/**
	 * 返回所有部门的列表
	 * @return
	 * @throws GeneralException
	 */
	public List<SysDepartment> showAllDepts() throws GeneralException;
	
	/**
	 * 根据部门名称得到部门编号
	 * @param dept_name
	 * @return
	 * @throws GeneralException
	 */
	public String getDeptNoByName(String dept_name) throws GeneralException;
	
	/**
	 * 根据部门编号返回部门的父部门名称
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public String parentName(String dept_no) throws GeneralException;
	
	/**
	 * 根据部门编号返回部门信息
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public SysDepartment showDeptByNo(String dept_no) throws GeneralException;
	
	/**
	 * 新增部门
	 * @param user_id
	 * @param deptForm
	 * @throws GeneralException
	 */
	public void addDept(String user_id,DeptForm deptForm) throws GeneralException;
	
	/**
	 * 更新部门信息
	 * @param deptForm
	 * @throws GeneralException
	 */
	public void updateDept(DeptForm deptForm) throws GeneralException;
	
	/**
	 * 返回用户管理的所有部门
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysDepartment> deptsByUser(String user_id) throws GeneralException;
	
	/**
	 * 根据银行序列号查询相应的部门
	 * @param bankNo
	 * @return
	 */
	public SysDepartment showDeptByBankNo(String bankNo);
	
	/**
	 * 根据用户名得到 部门树 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysDepartment> deptTreeByUser(String user_id) throws GeneralException;
	
	/**
	 * 新增用户部门记录
	 * @param user_id
	 * @param dept_no
	 * @throws GeneralException
	 */
	public void addUserDept(String user_id,String dept_no) throws GeneralException;
    
	public List<SysDepartment> showDeptByUser(String user_no) throws Exception;
	
	/**
	 * 根据部门ID得到下属用户列表
	 * @param dept_no
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> showUsersbyDept(String dept_no) throws Exception;
	
	/**
	 * 更新部门
	 * @param dept SysDepartment
	 * @throws GeneralException
	 */
	public void updateDept(SysDepartment dept) throws GeneralException;
	
	/**
	 * 获取该部门下最大的排序号
	 * @param deptNo
	 * @return
	 */
	public String getMaxDeptTab(String deptNo);
	
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
