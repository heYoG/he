package com.dj.seal.organise.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysFuncDao;
import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.service.api.ISysFuncService;
import com.dj.seal.organise.web.SysFuncVo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.compara.CapSysFunc;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.struts.SysMenu;
import com.dj.seal.util.table.SysFuncUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.table.ViewMenuUtil;
import com.dj.seal.util.web.SearchForm;
import com.dj.seal.structure.dao.po.SysFunc;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.system.dao.api.ISystemDao;
public class SysFuncService implements ISysFuncService {
	
	static Logger logger = LogManager.getLogger(SysDeptService.class.getName());

	private ISysUserDao su_dao;
	private ISysFuncDao sf_dao;
	private ISysRoleDao role_dao;
	private ISystemDao isy;
	private BaseDAOJDBC dao;
	public ISysUserDao getSu_dao() {
		return su_dao;
	}
	public void setSu_dao(ISysUserDao su_dao) {
		this.su_dao = su_dao;
	}
	public ISysFuncDao getSf_dao() {
		return sf_dao;
	}
	public void setSf_dao(ISysFuncDao sf_dao) {
		this.sf_dao = sf_dao;
	}
	public ISysRoleDao getRole_dao() {
		return role_dao;
	}
	public void setRole_dao(ISysRoleDao role_dao) {
		this.role_dao = role_dao;
	}
	public ISystemDao getIsy() {
		return isy;
	}
	public void setIsy(ISystemDao isy) {
		this.isy = isy;
	}
	
	public BaseDAOJDBC getDao() {
		return dao;
	}
	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}
	public boolean isFuncAble(int group, int fv, String user_id)
			throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ").append(SysUserUtil.TABLE_NAME);
			sb.append(" where ").append(SysUserUtil.USER_ID).append("='");
			sb.append(user_id).append("'");
			String vstr = "0";
			if (group == 1) {
				vstr = dao.getStrOfInt(sb.toString(),
						SysUserUtil.USER_FUN1);
			} else if (group == 2) {
				vstr = dao.getStrOfInt(sb.toString(),
						SysUserUtil.USER_FUN2);
			} else if (group == 3) {
				vstr = dao.getStrOfInt(sb.toString(),
						SysUserUtil.USER_FUN3);
			} else if (group == 4) {
				vstr = dao.getStrOfInt(sb.toString(),
						SysUserUtil.USER_FUN4);
			} else if (group == 5) {
				vstr = dao.getStrOfInt(sb.toString(),
						SysUserUtil.USER_FUN5);
			}
			if (vstr == null) {
				return false;
			}
			int value = Integer.valueOf(vstr);
			if ((fv & value) == fv) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<SysFunc> showFuncsByUser_id(String user_id)
			throws GeneralException {
		SysUser user = su_dao.showSysUserByUser_id(user_id);
		List<SysFunc> list = new ArrayList<SysFunc>();
		Set<Integer> set1 = showFuncs(user.getUser_fun1());
		for (Integer i : set1) {
			SysFunc func = sf_dao.showSysFuncByFunc_value(i, "1");
			list.add(func);
		}
		Set<Integer> set2 = showFuncs(user.getUser_fun2());
		for (Integer i : set2) {
			SysFunc func = sf_dao.showSysFuncByFunc_value(i, "2");
			list.add(func);
		}
		Set<Integer> set3 = showFuncs(user.getUser_fun3());
		for (Integer i : set3) {
			SysFunc func = sf_dao.showSysFuncByFunc_value(i, "3");
			list.add(func);
		}
		Set<Integer> set4 = showFuncs(user.getUser_fun4());
		for (Integer i : set4) {
			SysFunc func = sf_dao.showSysFuncByFunc_value(i, "4");
			list.add(func);
		}
		Set<Integer> set5 = showFuncs(user.getUser_fun5());
		for (Integer i : set5) {
			SysFunc func = sf_dao.showSysFuncByFunc_value(i, "5");
			list.add(func);
		}
		return list;
	}
	/**
	 * 根据用户得到系统菜单
	 */
	@Override
	public List<SysMenu> showMenusByUser_id(String user_id)
			throws GeneralException {
		// 得到该用户的所有权限集合
		List<SysFunc> list_func = showFuncsByUser_id(user_id);
		return showMemusByFuns(user_id,list_func);
	}
	@SuppressWarnings("unchecked")
	private List<SysMenu> showMemusByFuns(String user_id,List<SysFunc> list_func) {
		// 得到已经初始化好的菜单集合
		List<SysMenu> list_menu = getSysMenuFirst(user_id,list_func);
		// 填充权限记录
		for (SysFunc sysFunc : list_func) {
			if (sysFunc == null)
				continue;
			for (SysMenu sysMenu : list_menu) {
				// 如果是权限所属菜单
				if (sysMenu.getView_menu().getMenu_no().equals(
						sysFunc.getMenu_no())) {
					if (sysMenu.getList_func() == null) {
						List<SysFunc> list = new ArrayList<SysFunc>();
						list.add(sysFunc);
						sysMenu.setList_func(list);
					} else {
						sysMenu.getList_func().add(sysFunc);
					}
				}
			}
		}
		for (SysMenu sm : list_menu) {// 排序
			Collections.sort(sm.getList_func(), new CapSysFunc());
		}
		return list_menu;
	}
	/**
	 * 根据权限集合得到只有菜单项的菜单集合
	 * 
	 * @param list_func
	 * @return
	 */
	private List<SysMenu> getSysMenuFirst(String userid,List<SysFunc> list_func) {
		StringBuffer sb_sql = new StringBuffer();
		if(Constants.USER_NAME_LOGGER.equals(userid)){
			sb_sql.append("select * from ").append(ViewMenuUtil.TABLE_NAME)
					.append(" where ").append(ViewMenuUtil.MENU_NO).append(
							" in (");
			StringBuffer temp = new StringBuffer();
			for (SysFunc sysFunc : list_func) {
				if (sysFunc != null)
					temp.append("'").append(sysFunc.getMenu_no()).append("',");
			}
			if (temp.lastIndexOf(",") == -1) {
				temp.append("'dd'");
			} else {
				temp.deleteCharAt(temp.lastIndexOf(","));
			}
			sb_sql.append(temp).append(
					") and " + ViewMenuUtil.FUNC_TYPE + "='2' order by "
							+ ViewMenuUtil.MENU_ID + " asc");
		}else{
			sb_sql.append("select * from ").append(ViewMenuUtil.TABLE_NAME)
					.append(" where ").append(ViewMenuUtil.MENU_NO).append(
							" in (");
			StringBuffer temp = new StringBuffer();
			for (SysFunc sysFunc : list_func) {
				if (sysFunc != null)
					temp.append("'").append(sysFunc.getMenu_no()).append("',");
			}
			if (temp.lastIndexOf(",") == -1) {
				temp.append("'dd'");
			} else {
				temp.deleteCharAt(temp.lastIndexOf(","));
			}
			sb_sql.append(temp).append(
					") and " + ViewMenuUtil.FUNC_TYPE + "='1' order by "
					+"length("+ViewMenuUtil.MENU_ID+") , "+ ViewMenuUtil.MENU_ID + " asc");
		}	
		//logger.info("菜单sql:"+sb_sql);
		// 获得权限覆盖的菜单表记录集合
		// logger.info("sb_sql.toString()"+sb_sql.toString());
		List<ViewMenu> list_view = isy.selectViewMenu(sb_sql.toString());
		// 初始化包括权限的菜单集合
		List<SysMenu> list_menu = new ArrayList<SysMenu>();
		for (ViewMenu viewMenu : list_view) {
			SysMenu sysMenu = new SysMenu();
			sysMenu.setView_menu(viewMenu);
			// 填充菜单表记录
			list_menu.add(sysMenu);
		}
		return list_menu;
	}
	/**
	 * 根据总权限值获得拥有的子权限的权限值集合
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set showFuncs(int value) {
		Set set = new HashSet();
		for (int i = 0; i < 31; i++) {
			int temp = 1 << i;
			if ((value & temp) == temp) {
				set.add(temp);
			}
		}
		return set;
	}
	@SuppressWarnings("unchecked")
	public PageSplit showObjBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		String str = SearchUtil.funcSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<SysFuncVo> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}
	@SuppressWarnings("unchecked")
	private List<SysFuncVo> listObjs(String sql) throws Exception {
		List<SysFuncVo> list_obj = new ArrayList<SysFuncVo>();
		List<Map> list = dao.select(sql);
		for (Map map : list) {
			SysFunc obj = new SysFunc();
			obj = (SysFunc) DaoUtil.setPo(obj, map, new SysFuncUtil());
			SysFuncVo vo = new SysFuncVo();
			BeanUtils.copyProperties(vo, obj);
			list_obj.add(resetVo(vo));
		}
		return list_obj;
	}
	private SysFuncVo resetVo(SysFuncVo vo) throws Exception {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("select ").append(ViewMenuUtil.MENU_NAME).append(" from ").append(ViewMenuUtil.TABLE_NAME);
		sb1.append(" where ").append(ViewMenuUtil.MENU_NO).append("='");
		sb1.append(vo.getMenu_no()).append("'");
		String menu_name = (String) dao.getObject(sb1.toString(),
				ViewMenuUtil.MENU_NAME, false);
		vo.setMenu_name(menu_name);
		// 设置用户
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select * from ").append(SysUserUtil.TABLE_NAME);
		String func = SysUserUtil.getUSER_FUN1();
		if ("1".equals(vo.getFunc_group())) {
			func = SysUserUtil.getUSER_FUN1();
		} else if ("2".equals(vo.getFunc_group())) {
			func = SysUserUtil.getUSER_FUN2();
		} else if ("3".equals(vo.getFunc_group())) {
			func = SysUserUtil.getUSER_FUN3();
		} else if ("42".equals(vo.getFunc_group())) {
			func = SysUserUtil.getUSER_FUN4();
		} else if ("5".equals(vo.getFunc_group())) {
			func = SysUserUtil.getUSER_FUN5();
		}
		sb2.append(" where ").append(
				DBTypeUtil.bitOper(func, vo.getFunc_value().toString(), "&",
						Constants.DB_TYPE));
		sb2.append("=").append(vo.getFunc_value());
		sb2.append(" and ").append("C_ACA != 'logger'");
		int user_num = dao.count(sb2.toString());
		vo.setUser_num(user_num);
		String sel_users = dao.getStr(sb2.toString(), SysUserUtil.USER_ID);
		vo.setSel_users(sel_users);
		// 设置角色
		StringBuffer sb3 = new StringBuffer();
		sb3.append("select * from ").append(SysRoleUtil.TABLE_NAME);
		if("1".equals(vo.getFunc_group())){
			sb3.append(" where ").append(
					DBTypeUtil.bitOper(SysRoleUtil.getROLE_FUN1(), vo
							.getFunc_value().toString(), "&", Constants.DB_TYPE));
			sb3.append("=").append(vo.getFunc_value());
		}else if("2".equals(vo.getFunc_group())){
			sb3.append(" where ").append(
					DBTypeUtil.bitOper(SysRoleUtil.getROLE_FUN2(), vo
							.getFunc_value().toString(), "&", Constants.DB_TYPE));
			sb3.append("=").append(vo.getFunc_value());
			
		}
		
		int role_num = dao.count(sb3.toString());
		vo.setRole_num(role_num);
		String sel_roles = dao.getStrOfInt(sb3.toString(),
				SysRoleUtil.ROLE_ID);
		vo.setSel_roles(sel_roles);
		return vo;
	}
}
