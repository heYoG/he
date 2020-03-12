package com.dj.seal.sealBody.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ImportSealForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile seal_path;// pdf文件
	public FormFile getSeal_path() {
		return seal_path;
	}
	public void setSeal_path(FormFile seal_path) {
		this.seal_path = seal_path;
	}
	
	
	
}
