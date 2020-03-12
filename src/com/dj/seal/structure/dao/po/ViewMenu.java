package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 菜单表对应的po
 * @author yc,oyxy
 * @since 2009-11-2
 *
 */
public class ViewMenu extends BasePO{
	
	private static final long serialVersionUID = 1L;
	private String menu_id;         //菜单id
	private String menu_no;         //菜单编号
	private String menu_name;       //菜单名称
	private String menu_image;      //菜单图片
	private String func_group;      //权限组名
    private String func_type;       //菜单类型
	public ViewMenu(){
		
	}
	
	public ViewMenu(String id,String no,String name,String image,String group,String func_type){
		setMenu_id(id);
		setFunc_group(group);
		setMenu_image(image);
		setMenu_name(name);
		setMenu_no(no);
		setFunc_type(func_type);
	}
	
	
	
	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getMenu_no() {
		return menu_no;
	}

	public void setMenu_no(String menu_no) {
		this.menu_no = menu_no;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_image() {
		return menu_image;
	}

	public void setMenu_image(String menu_image) {
		this.menu_image = menu_image;
	}

	public String getFunc_group() {
		return func_group;
	}

	public void setFunc_group(String func_group) {
		this.func_group = func_group;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ViewMenu other = (ViewMenu) obj;
		if (menu_no == null) {
			if (other.menu_no != null)
				return false;
		} else if (!menu_no.equals(other.menu_no))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((menu_no == null) ? 0 : menu_no.hashCode());
		return result;
	}

	public String getFunc_type() {
		return func_type;
	}

	public void setFunc_type(String func_type) {
		this.func_type = func_type;
	}

	
}
