package com.dj.seal.uploadPdf.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class PdfForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rule_no;// 规则号
	private String pdf_name;// pdf名称
	private FormFile pdf_path;// pdf文件
	
	
	public String getPdf_name() {
		return pdf_name;
	}
	public void setPdf_name(String pdf_name) {
		this.pdf_name = pdf_name;
	}
	public String getRule_no() {
		return rule_no;
	}
	public void setRule_no(String rule_no) {
		this.rule_no = rule_no;
	}
	public FormFile getPdf_path() {
		return pdf_path;
	}
	public void setPdf_path(FormFile pdf_path) {
		this.pdf_path = pdf_path;
	}
}
