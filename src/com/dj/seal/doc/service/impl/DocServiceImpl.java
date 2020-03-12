package com.dj.seal.doc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.doc.dao.api.IDocDao;
import com.dj.seal.doc.service.api.IDocService;
import com.dj.seal.doc.web.form.SearchDocForm;
import com.dj.seal.structure.dao.po.DocmentBody;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.DocmentBodyUtil;

public class DocServiceImpl implements IDocService{
	
	static Logger logger = LogManager.getLogger(IDocService.class.getName());
	
	private IDocDao docDao;
	/**
	 * 新增文档
	 * 
	 * @param form
	 * @throws GeneralException
	 */
	@Override
	public void addDoc(DocmentBody doc) {
		docDao.addDoc(doc);
	}
	/**
	 * 根据文档编号判断文档是否在系统中已存在
	 * @param doc_id 文档编号
	 * @return
	 */
	@Override
	public boolean isExitDoc(String doc_id){
		return docDao.isExitDoc(doc_id);
	}
	/**
	 * 根据查询form查询文档列表
	 * @param docForm 表单
	 * @param pageSize 每页显示多少行
	 * @param pageIndex 当前第几页
	 * @return
	 */
	@Override
	public PageSplit getDocList(SearchDocForm docForm, int pageSize, int pageIndex) {
		String doc_no=docForm.getDoc_no();
		String doc_name=docForm.getDoc_name();
		String doc_title=docForm.getDoc_title();
		String dept_no=docForm.getDept_no();
		String start_time=docForm.getStart_time();
		String end_time=docForm.getEnd_time();
		String is_junior=docForm.getIs_junior();
		StringBuffer sb=new StringBuffer("select * from "+ DocmentBodyUtil.TABLE_NAME + " where 1=1 ");
		if(doc_no!=null&&!doc_no.equals("")){
			sb.append("and "+DocmentBodyUtil.DOC_NO +" like '%"+doc_no+"%' ");
		}
		if(doc_name!=null&&!doc_name.equals("")){
			sb.append("and "+DocmentBodyUtil.DOC_NAME +" like '%"+doc_name+"%' ");
		}
		if(doc_title!=null&&!doc_title.equals("")){
			sb.append("and "+DocmentBodyUtil.DOC_TITLE +" like '%"+doc_title+"%' ");
		}
		if(dept_no!=null&&!dept_no.equals("")){
			if(is_junior!=null&&!is_junior.equals("")&&is_junior.equals("1")){		
				sb.append("and "+DocmentBodyUtil.DEPT_NO+" like '"+dept_no+"%' ");
			}else{
				sb.append("and "+DocmentBodyUtil.DEPT_NO+" = '"+dept_no+"' ");
			}
		}
		if (start_time != null && !"".equals(start_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + DocmentBodyUtil.CREATE_TIME + " > to_date('"
						+ start_time + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + DocmentBodyUtil.CREATE_TIME + " > '"
						+ start_time + " 00:00:00'");
			}
		}
		if (end_time != null && !"".equals(end_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + DocmentBodyUtil.CREATE_TIME + " < to_date('"
						+ end_time + " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + DocmentBodyUtil.CREATE_TIME + " < '" + end_time
						+ " 23:59:59'");
			}

		}
		sb.append(" order by " + DocmentBodyUtil.CREATE_TIME + " desc");
		List<DocmentBody> list=docDao.showDocByPageSplit(pageIndex, pageSize, sb.toString());
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(list);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(docDao.showCount(sb.toString()));
		return pageSplit;
	}
	
	@Override
	public PageSplit getDocById(int pageSize, int pageIndex,String doc_id) {
		StringBuffer sb=new StringBuffer("select * from "+ DocmentBodyUtil.TABLE_NAME + " where ");
		sb.append(DocmentBodyUtil.DOC_NO).append("='").append(doc_id).append("'");
		List<DocmentBody> list=docDao.showDocByPageSplit(pageIndex, pageSize, sb.toString());
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(list);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(docDao.showCount(sb.toString()));
		return pageSplit;
	}
	public IDocDao getDocDao() {
		return docDao;
	}

	public void setDocDao(IDocDao docDao) {
		this.docDao = docDao;
	}
	
}
