package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadDeptAndUserForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;// 标识是部门还是用户（dept、user)
	private FormFile file_path;// 证书文件
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public FormFile getFile_path() {
		return file_path;
	}
	public void setFile_path(FormFile file_path) {
		this.file_path = file_path;
	}

}
