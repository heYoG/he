package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 桌面模块表对应的po
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class ViewTableModule extends BasePO {

	private static final long serialVersionUID = 1L;
	private String module_no; // 模块编号
	private String module_name; // 模块名称
	private String module_file; // 模块文件名
	private String module_url; // 模块url
	private String module_scroll; // 是否可滚动
	private Integer module_lines; // 显示行数

	public ViewTableModule(){
		
	}
	
	public ViewTableModule(String no,String name,String file,String url,String scroll,Integer lines){
		setModule_file(file);
		setModule_lines(lines);
		setModule_name(name);
		setModule_no(no);
		setModule_scroll(scroll);
		setModule_url(url);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ViewTableModule other = (ViewTableModule) obj;
		if (module_no == null) {
			if (other.module_no != null)
				return false;
		} else if (!module_no.equals(other.module_no))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result
				+ ((module_no == null) ? 0 : module_no.hashCode());
		return result;
	}

	public String getModule_no() {
		return module_no;
	}

	public void setModule_no(String module_no) {
		this.module_no = module_no;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getModule_file() {
		return module_file;
	}

	public void setModule_file(String module_file) {
		this.module_file = module_file;
	}

	public String getModule_url() {
		return module_url;
	}

	public void setModule_url(String module_url) {
		this.module_url = module_url;
	}

	public String getModule_scroll() {
		return module_scroll;
	}

	public void setModule_scroll(String module_scroll) {
		this.module_scroll = module_scroll;
	}

	public Integer getModule_lines() {
		return module_lines;
	}

	public void setModule_lines(Integer module_lines) {
		this.module_lines = module_lines;
	}

}
