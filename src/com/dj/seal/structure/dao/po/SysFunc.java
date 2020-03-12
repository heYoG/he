package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SysFunc extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer func_id;				//权限id
	private String menu_no;				//菜单编号
	private Integer func_value;			//权限值
	private String func_name;			//权限名
	private String func_group;			//权限组名
	private String func_image;			//菜单图片
	private String func_url;				//链接地址
	
	private Integer checked=0;				//是否被选，“1”为被选，“0”不被选
	
	public SysFunc(){
		
	}
	
	public SysFunc(Integer id,String no,Integer value,String name,String group,String image,String url){
		setFunc_group(group);
		setFunc_id(id);
		setFunc_image(image);
		setFunc_name(name);
		setFunc_url(url);
		setFunc_value(value);
		setMenu_no(no);
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Integer getFunc_id() {
		return func_id;
	}

	public void setFunc_id(Integer func_id) {
		this.func_id = func_id;
	}

	public String getMenu_no() {
		return menu_no;
	}

	public void setMenu_no(String menu_no) {
		this.menu_no = menu_no;
	}

	public Integer getFunc_value() {
		return func_value;
	}

	public void setFunc_value(Integer func_value) {
		this.func_value = func_value;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public String getFunc_group() {
		return func_group;
	}

	public void setFunc_group(String func_group) {
		this.func_group = func_group;
	}

	public String getFunc_image() {
		return func_image;
	}

	public void setFunc_image(String func_image) {
		this.func_image = func_image;
	}

	public String getFunc_url() {
		return func_url;
	}

	public void setFunc_url(String func_url) {
		this.func_url = func_url;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysFunc other = (SysFunc) obj;
		if (func_id == null) {
			if (other.func_id != null)
				return false;
		} else if (!func_id.equals(other.func_id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((func_id == null) ? 0 : func_id.hashCode());
		return result;
	}

}
