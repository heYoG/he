package com.dj.seal.sealBody.service.api;


import java.util.List;
import com.dj.seal.sealBody.web.form.SealBodyForm;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;

public interface ISealBodyService {

	public void deleteSeal(String sealId,String sealName)throws Exception;
	/**
	 * 添加印章
	 * 
	 * @param seal
	 */
	public void addIsealBody(SealBody sealBody);

	
	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showBodyQueryList(SealBodyForm sealtemp,int pageSize,int pageIndex,String is_junior) throws GeneralException;
	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印章列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showBodyList(String is_junior,String user_id,int pageSize,int pageIndex) throws GeneralException;
	/**
	 * 根据用户名得到用户可使用的印章列表
	 * 
	 * @param user_id 用户id
	 * @return  印章列表
	 */
	public List<SealBody> getSealListByUser_id(String user_id);
	public List<SealBody> getSealListByUser_id(String seal_type,String user_id);
	/**
	 * 根据角色获取可使用的印章列表
	 * 
	 * @param roles 角色列表
	 * @return  印章列表
	 */
	public List<SealBody> getSealListByRoles(List<SysRole> roles);
	public List<SealBody> getSealListByRoles(String seal_type,List<SysRole> roles);
	
	/**
	 * 根据印章id查找印章信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public SealBody getSealBodys(Integer seal_id) throws GeneralException;
//	/**
//	 * 根据财政印章id查找印章信息
//	 * 
//	 * @param seal_id  财政国库系统的印章id
//	 * @return
//	 * @throws GeneralException
//	 */
//	public SealBody getSealBodysBySealCZId(String seal_id) throws GeneralException;

	/**
	 * 根据印章名称查找印章信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public SealBody getSealBodys(String seal_name) throws GeneralException;
	public SealBody getSealBodyID(String seal_id) throws GeneralException;
	/**
	 * 添加用户，角色权限到印章
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public void addRoleUser(String seal_id,String user_id,String role_id) throws GeneralException;
	/**
	 * 根据印章id得到用户信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysUser> getUser(String seal_id) throws GeneralException;
	/**
	 * 根据印章id得到角色信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysRole> getRole(String seal_id) throws GeneralException;

	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印章列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showBodyListById(String user_id,int pageSize,int pageIndex) throws GeneralException;
	
	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印章列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showSealBySeal_Id(int pageSize,int pageIndex,String seal_id) throws GeneralException;
	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showBodyQueryListByDeptNO(String dept_no,int pageSize,int pageIndex) throws GeneralException;
	/**
	 * 根据分页信息和用户名ID得到用户的可用印章列表
	 * @param pageSize
	 * @param pageIndex
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showAllSealsOfMine(int pageSize,int pageIndex,String user_id) throws Exception;
	
	/**
	 * 绑定证书
	 * 
	 * @param seal
	 * @param seal_id
	 */
	public void updateSealBody(String cert_no,String seal_id) throws GeneralException;
	/**
	 * 备份印章信息
	 * 
	 * @param seal
	 * @param seal_id
	 */
	public String exportSeal(String sealId)throws Exception;
	
	/**
	 * 
	 * @return
	 */
	public List<SealBody>  showSealBodyByDeptNo(String deptNo);
	
	/**
	 * by hyg 20171227
	 * @param deptNo 部门编号
	 * @return 印章对象
	 */
	public List<SealBody> showSealBodyByDeptNo2(String deptNo);
	
}
