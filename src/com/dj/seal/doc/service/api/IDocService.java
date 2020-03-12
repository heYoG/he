package com.dj.seal.doc.service.api;

import com.dj.seal.doc.web.form.SearchDocForm;
import com.dj.seal.structure.dao.po.DocmentBody;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;

public interface IDocService {
	/**
	 * 新增文档
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	public void addDoc(DocmentBody doc);
	/**
	 * 根据文档编号判断文档是否在系统中已存在
	 * @param doc_id 文档编号
	 * @return
	 */
	public boolean isExitDoc(String doc_id);
	
	/**
	 * 根据查询form查询文档列表
	 * @param docForm 表单
	 * @param pageSize 每页显示多少行
	 * @param pageIndex 当前第几页
	 * @return
	 */
	public PageSplit getDocList(SearchDocForm docForm,int pageSize,int pageIndex);
	
	/**
	 * 根据doc_id查询文档列表
	 * @param doc_id 表单
	 * @param pageSize 每页显示多少行
	 * @param pageIndex 当前第几页
	 * @return
	 */
	public PageSplit getDocById(int pageSize,int pageIndex,String doc_id);

}
