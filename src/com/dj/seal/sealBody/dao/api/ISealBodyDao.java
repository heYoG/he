package com.dj.seal.sealBody.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SealBodyBak;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;

public interface ISealBodyDao {
	public void deleteSeal(String sql)throws DAOException;
	/**
	 * 更新印章
	 * 
	 * @param sealBody
	 * @return
	 * @throws GeneralException
	 */
	public void updateIsealBody(String seal_id,String sealData) throws DAOException;
	/**
	 * 新增印章
	 * 
	 * @param sealBody
	 * @return
	 * @throws GeneralException
	 */
	public void addIsealBody(SealBody sealBody) throws DAOException;
	
	/**
	 * 新增印章备份
	 * 
	 * @param sealBody
	 * @return
	 * @throws GeneralException
	 */
	public void addIsealBodyBak(SealBodyBak sealBody_bak) throws DAOException;
	/**
	 * 根据sql得到用户管理范围内可管理的印章列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SealBody> showBodyList(String sql) throws DAOException;
	/**
	 * 根据印章id查找印章信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public SealBody getSealBodyID(String seal_id) throws DAOException;
	/**
	 * 根据印章id查找印章信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public SealBody getSealBodys(Integer seal_id) throws DAOException;
//	/**
//	 * 根据财政国库印章id查找印章信息
//	 * 
//	 * @param seal_id
//	 * @return
//	 * @throws DAOException
//	 */
//	public SealBody getSealBodysBYSealCZid(String seal_id) throws DAOException;
	/**
	 * 根据印章名称查找印章信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public SealBody getSealBodys(String seal_name) throws DAOException;
	/**
	 * 添加用户印章表
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public void addUserSeal(String seal_id,String user_id) throws DAOException;
	/**
	 * 删除用户印章表
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public void deleteUserSeal(String seal_id) throws DAOException;
	/**
	 * 删除角色印章表
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public void deleteRoleSeal(String seal_id) throws DAOException;
	/**
	 * 添加角色印章表
	 * 
	 * @param seal_id
	 * @return
	 * @throws DAOException
	 */
	public void addRoleSeal(String seal_id,String role_id) throws DAOException;
	/**
	 * 根据sql得到用户信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysUser> getUser(String sql) throws DAOException;
	/**
	 * 根据sql得到角色信息
	 * 
	 * @param seal_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysRole> getRole(String sql) throws DAOException;
	
	/**
	 * 印章绑定证书
	 * @param seal_id
	 * @param cert_id
	 * @throws DAOException
	 */
	public void objbind(String seal_id,String cert_id) throws DAOException;
	/**
	 * 根据分页信息得到印章集合
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<SealBody> showSealBodysByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException;
	/**
	 * 通过对象修改印章
	 * 
	 * @param seal
	 * @param seal_id
	 */
	public void updateSealBody(String sql) throws DAOException;
	
	/**
	 * 判断是否存在印模名称为temp_name的印模
	 * 
	 * @param temp_name
	 * @return
	 * @throws DAOException
	 */
	public boolean isExistSealName(String seal_name) throws DAOException;
	/**
	 * 注销印章  返回1成功  0 失败
	 * @param seal_id
	 * @return
	 */
	public String logOff(String seal_id);
	/**
	 * 激活印章  返回1成功  0 失败
	 * @param seal_id
	 */
	public String activate(String seal_id);
}
