package com.dj.seal.system.dao.api;

import java.util.List;

import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.ViewInterface;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;


/**
 * @title ISystemDao
 * @description 系统DAO接口
 * @author yc
 * @since 2009-11-3
 * @version 1.0
 *
 */
public interface ISystemDao {
	/**
	 * 初始化系统设置，向界面表插入数据
	 * @param view
	 * @throws DAOException
	 */
	public void addViewInterface(ViewInterface view)throws DAOException;
	
	/**
	 * 修改界面表
	 * @param view
	 * @throws DAOException
	 */
	public void updateViewInterface(ViewInterface view)throws DAOException;
	
	/**
	 * 查询界面表
     * @throws GeneralException
	 */
	public ViewInterface selectViewInterface()throws DAOException;
	
	/**
	 * 向桌面模块表中插入数据
	 * @param viewTableModule
	 * @throws DAOException
	 */
    public void addViewTableModule(ViewTableModule viewTableModule)throws DAOException;
    
    /**
	 * 修改桌面模块表中的数据
	 * @param viewTableModule
	 * @throws DAOException
	 */
    public void updateViewTableModule(ViewTableModule viewTableModule)throws DAOException;
    
    /**
	 * 根据模块表编号查询单条模块表信息
	 * @param str
	 * @throws DAOException
	 */
    public ViewTableModule showViewTableModule(String str)throws DAOException;
    
    /**
	 * 增加菜单
	 * @param viewMenu
	 * @throws DAOException
	 */
    public void addViewMenu(ViewMenu viewMenu)throws DAOException;
    
    /**
	 * 删除菜单
	 * @param str
	 * @throws DAOException
	 */
    public void deleteViewMenu(String str)throws DAOException;
    
    /**
	 * 修改菜单
	 * @param viewMenu
	 * @throws DAOException
	 */
    public void updateViewMenu(ViewMenu viewMenu)throws DAOException;
    
    /**
	 * 查询单条菜单记录
	 * @param str
	 * @throws DAOException
	 */
    public ViewMenu showViewMenu(String str)throws DAOException;
    
    /**
	 * 查询所有菜单记录
	 * @param 
	 * @throws DAOException
	 */
    public List<ViewMenu> selectViewMenu(String str)throws DAOException;
       
    /**
	 * 增加系统日志记录
	 * @param viewMenu
	 * @throws DAOException
	 */
    public void addLogSys(LogSys logSys)throws DAOException;
    
    /**
	 * 分页查询系统日志记录
	 * @param str
	 * @throws DAOException
	 */
    public List<LogSys> showLogSys(PageSplit pageSplit,String[]sql)throws DAOException;
    
    /**
	 * 删除系统日志记录
	 * @param 
	 * @throws DAOException
	 */
    public void deleteLogSys(String str)throws DAOException;
   
    
}
