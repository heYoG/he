package com.dj.seal.util.struts;

import java.io.Serializable;
import java.util.List;

import com.dj.seal.structure.dao.po.SysFunc;
import com.dj.seal.structure.dao.po.ViewMenu;

/**
 * 页面菜单
 * 
 * @author oyxy
 * @since 2009-11-9
 * 
 */
public class SysMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewMenu view_menu;
	private List<SysFunc> list_func;
	
	public ViewMenu getView_menu() {
		return view_menu;
	}
	public void setView_menu(ViewMenu view_menu) {
		this.view_menu = view_menu;
	}
	public List<SysFunc> getList_func() {
		return list_func;
	}
	public void setList_func(List<SysFunc> list_func) {
		this.list_func = list_func;
	}
	
}
