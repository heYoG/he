package com.dj.seal.system.service.api;

import java.util.List;

import org.aspectj.lang.JoinPoint;

import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.ViewInterface;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.web.form.InterfaceForm;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;



	/**
	 * @title ISystemDao
	 * @description 系统DAO接口
	 * @author yc
	 * @since 2009-11-3
	 * @version 1.0
	 *
	 */
public interface ISystemService {
		/**
		 * 初始化系统设置，向界面表插入数据
		 * @param view
		 * @throws GeneralException
		 */
		public void addViewInterface(ViewInterface view)throws GeneralException;
		
		/**
		 * 修改界面表
		 * @param view
		 * @throws GeneralException
		 */
		public void updateViewInterface(ViewInterface view)throws GeneralException;
		/**
		 * 查询界面表
	     * @throws GeneralException
		 */
		public ViewInterface selectViewInterface()throws GeneralException;
		
		/**
		 * 向桌面模块表中插入数据
		 * @param viewTableModule
		 * @throws GeneralException
		 */
	    public void addViewTableModule(ViewTableModule viewTableModule)throws GeneralException;
	    
	    /**
		 * 修改桌面模块表中的数据
		 * @param viewTableModule
		 * @throws GeneralException
		 */
	    public void updateViewTableModule(ViewTableModule viewTableModule)throws GeneralException;
	    
	    /**
		 * 根据模块表编号查询单条模块表信息
		 * @param str
		 * @throws GeneralException
		 */
	    public ViewTableModule showViewTableModule(String str)throws GeneralException;
	    
	    /**
		 * 增加菜单
		 * @param viewMenu
		 * @throws GeneralException
		 */
	    public void addViewMenu(ViewMenu viewMenu)throws GeneralException;
	    
	    /**
		 * 删除菜单
		 * @param str
		 * @throws GeneralException
		 */
	    public void deleteViewMenu(String str)throws GeneralException;
	    
	    /**
		 * 修改菜单
		 * @param viewMenu
		 * @throws GeneralException
		 */
	    public void updateViewMenu(ViewMenu viewMenu)throws GeneralException;
	    
	    /**
		 * 查询单条菜单记录
		 * @param str
		 * @throws GeneralException
		 */
	    public ViewMenu showViewMenu(String str)throws GeneralException;
	    
	    /**
		 * 查询所有菜单记录
		 * @param 
		 * @throws GeneralException
		 */
	    public List<ViewMenu> selectVewMenu(String str)throws GeneralException;
	       
	    /**
		 * 增加系统日志记录
		 * @param viewMenu
		 * @throws GeneralException
		 */
	    public void addLogSys(JoinPoint jp,SysUser sysUser)throws GeneralException;
	    
	    /**
		 * 查询系统日志记录
		 * @param str
		 * @throws GeneralException
		 */
	    public List<LogSys> showLogSys(PageSplit pageSplit,String[]sql)throws GeneralException;
	    
	    /**
		 * 删除所有系统日志记录
		 * @param 
		 * @throws GeneralException
		 */
	    public void deleteLogSys(String str)throws GeneralException;
	   
	    
	    /**
	     * 更新界面设置
	     * @param face_form
	     * @throws GeneralException
	     */
	    public void updateInterface(InterfaceForm face_form) throws GeneralException; 
}
