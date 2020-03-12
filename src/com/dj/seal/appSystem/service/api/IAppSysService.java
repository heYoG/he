package com.dj.seal.appSystem.service.api;

import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.web.SearchForm;

/**
 * @title appSysService
 * @description 应用系统管理
 * @author zbl
 * @since 2013-5-10
 * 
 */
public interface IAppSysService {
   
   /**
    * 添加应用系统
    * @param appSystem
    * @return true(成功) or false(失败)
    */
   public boolean addAppSystem(AppSystemForm app) throws Exception;
   
   /**
    * 应用系统列表
    * 
    * */
   public PageSplit showAppSystems(int pageIndex, int pageSize,SearchForm appSystemForm) throws GeneralException;
   /**
    * 根据应用系统编号判断是否存在此应用系统
    * */
   public boolean isExistServer(String app_no);
   
   /**
    *根据应用系统编号删除对应应用系统
    */
   public String delAppSystem(String app_id);
   /**
    * 根据应用系统id获取应用系统实体
    * */
   public AppSystemForm getAppById(String app_id);
   /**
    * 根据应用系统编号获取应用系统实体
    * */
   public AppSystemForm getAppSystemByAPP_NO(String app_no);
   /**
    *更新系统 
    */
   public void upAppSystem(String app_no,String app_name,String app_ip,String app_id,String app_pwd);
}
