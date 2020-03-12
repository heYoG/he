package com.dj.seal.sealTemplate.service.api;


import java.util.List;

import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.sealTemplate.web.form.SealTempForm;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;

public interface ISealTemplateService {

	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempList(String is_junior,String user_id,int pageSize,int pageIndex) throws GeneralException;
	/**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit LocalshowTempList(String user_id,int pageSize,int pageIndex) throws GeneralException;

	/**
	 * 根据分页信息得到印模集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */						
	public List<SealTemplate> showSealTempsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException;



	/**
	 * 根据印模Form新增印模
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void addSealTemp(SealTempForm form) throws GeneralException;
	/**
	 * 判断是否存在印模名称为temp_name的印模
	 * 
	 * @param temp_name
	 * @return 1为存在，0为不存在
	 * @throws GeneralException
	 */
	public int isExistTempName(String temp_name) throws GeneralException;
	
	/**
	 * 批准印模
	 * 
	 * @param temp
	 * @param temps
	 * @throws GeneralException
	 */
	public void approveTemp(SealTempForm temp, String temps)
			throws GeneralException;
	
	/**
	 * 根据印模ID获得印模记录
	 * 
	 * @param temp_id
	 * @return
	 * @throws GeneralException
	 */
	public String showTempByTemp_id(String temp_id)
			throws GeneralException;
	/**
	 * 根据印模ID获得印模记录
	 * 
	 * @param temp_id
	 * @return
	 * @throws GeneralException
	 */
	public SealTemplate showTempByTemp(String temp_id)
			throws GeneralException;
	/**
	 * 根据分页信息和用户名得到需该用户审核的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showSealTempByApp(int pageIndex, int pageSize,
			String user_id) throws GeneralException;
	
	/**
	 * 根据分页信息和用户名得到此用户已经审核的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showSealTempByAppHis(int pageIndex, int pageSize,
			String user_id) throws GeneralException;

	
	/**

	 * 查询印模管理根据用户管理下的部门编号
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempsByDeptManageSel(int pageIndex, int pageSize,
			String dept_no) throws GeneralException;
	
	/**
	 * 根据分页信息和部门名得到该部门内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param dept_no
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempsByDeptManage(int pageIndex, int pageSize,
			List<SysDepartment> list) throws GeneralException;
	/**
	 * 根据印模表单Form中的查询条件返回印模记录集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param form
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit findSealTemplate(int pageIndex, int pageSize,
			SealTempForm form,String is_junior) throws GeneralException;

	/**

	 * 更新印模
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void updateTemp(SealTempForm form) throws GeneralException;
	/**
	 * 删除印模
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void deleteTemp(String tempId) throws GeneralException;
    /**
	 * 根据分页信息和用户名得到用户管理范围内可管理的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit showTempQueryList(SealTempForm sealtemp,int pageSize,int pageIndex,String is_junior) throws GeneralException;
	/**
	 * 根据分页信息和用户名得到当前用户的印模列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public PageSplit LocalshowTempListSu(String user_id,int pageSize,int pageIndex) throws GeneralException;
	
	/**
	 * 得到数据库某一列最大值
	 * @param tableName
	 * @param colName
	 * @return
	 */
	public String getMaxIDs(String tableName ,String colName) throws GeneralException;
	
	
	
	/**
	 * 根据当前用户得到所在部门的一个审批人
	 * @param user_id  当前用户
	 * @return 审批人姓名
	 * @throws Exception
	 */
	public SysUser getAppMan(String user_id) throws Exception;
	
	/**
	 *  从印章的bmp路径得到印章的初始默认名称
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String getBmpName(String path) throws Exception;
	
}
