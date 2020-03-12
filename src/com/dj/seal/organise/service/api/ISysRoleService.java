package com.dj.seal.organise.service.api;

import java.util.List;

import com.dj.seal.organise.web.form.RoleForm;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUserRole;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.RoleDetail;

/**
 * @title ISysRoleService
 * @description 角色Service接口
 * @author oyxy
 * @since 2009-11-13
 * 
 */
public interface ISysRoleService {

	/**
	 * 根据角色id得到用户角色表信息
	 * 
	 * @param user_status
	 * @return
	 * @throws GeneralException
	 */
	public List<SysUserRole> showRoleList(String sql)
			throws GeneralException;

	/**
	 * 根据角色排序号的高低顺序得到角色列表
	 * 
	 * @param user_status
	 * @return
	 * @throws GeneralException
	 */
	public List<SysRole> showRolesByTab(String user_status)
			throws GeneralException;
	/**
	 * 新增角色到角色印章表
	 * 
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void addSysRole2(String role_nos,String seal_id) throws GeneralException;
	
	
	/**
	 * 根据用户名称获得用户角色状态
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public String getUserRoleStatus(String user_id) throws GeneralException;

	/**
	 * 为role_ids列表中的角色增加roleForm里的权限
	 * 
	 * @param role_ids
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void addFunc(String role_ids, RoleForm roleForm)
			throws GeneralException;

	/**
	 * 为role_ids列表中的角色删除roleForm里的权限
	 * 
	 * @param role_ids
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void deleteFunc(String role_ids, RoleForm roleForm)
			throws GeneralException;

	/**
	 * 根据分布信息得到角色列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showSysRolesByPageSplit(int pageIndex, int pageSize)
			throws GeneralException;

	/**
	 * 新增角色
	 * 
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void addSysRole(RoleForm roleForm) throws GeneralException;

	/**
	 * 根据角色ID删除角色
	 * 
	 * @param role_id
	 * @throws GeneralException
	 */
	public void deleteSysRole(Integer role_id) throws GeneralException;

	/**
	 * 更新角色
	 * 
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void updateSysRole(RoleForm roleForm) throws GeneralException;

	/**
	 * 更新角色权限业务
	 * 
	 * @param role
	 * @throws GeneralException
	 */
	public void updateRoleFunc(SysRole role) throws GeneralException;

	/**
	 * 判断是否存在角色名
	 * 
	 * @param role_name
	 * @return
	 * @throws GeneralException
	 */
	public int isExistRole(String role_name) throws GeneralException;
	/**
	 * 判断是否存在角色名
	 * 
	 * @param role_name
	 * @return
	 * @throws GeneralException
	 */
	public int isExistRoleno(String role_no) throws GeneralException;

	/**
	 * 根据角色ID获得角色信息
	 * 
	 * @param role_id
	 * @return
	 * @throws GeneralException
	 */
	public SysRole showSysRoleByRole_id(Integer role_id)
			throws GeneralException;

	/**
	 * 根据用户名得到角色信息
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public SysRole showSysRoleByUser_id(String user_id) throws GeneralException;

	/**
	 * 根据用户名得到角色信息列表
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysRole> showSysRolesByUser_id(String user_id)
			throws GeneralException;

	/**
	 * 为角色设置权限
	 * 
	 * @param roleForm
	 * @throws GeneralException
	 */
	public void setRoleFunc(RoleForm roleForm) throws GeneralException;

	/**
	 * 根据角色ID，角色排序号，角色名克隆一个新角色
	 * 
	 * @param role_id
	 * @param role_tab
	 * @param role_name
	 * @throws GeneralException
	 */
	public void cloneRole(Integer role_id, String role_tab, String role_name)
			throws GeneralException;

	/**
	 * 根据角色ID获得角色详细信息集合
	 * 
	 * @param role_id
	 * @return
	 * @throws GeneralException
	 */
	public List<RoleDetail> showRoleDetail(Integer role_id)
			throws GeneralException;

	/**
	 * 根据角色ID和TAB为角色排序
	 * 
	 * @param role_ids
	 * @param role_tabs
	 * @throws GeneralException
	 */
	public void sortRoleTab(String role_ids, String role_tabs)
			throws GeneralException;

	/**
	 * 
	 * @param userId 用户姓名
	 * @return       用户角色
	 */

	public String getRoleName(String userId);
}
