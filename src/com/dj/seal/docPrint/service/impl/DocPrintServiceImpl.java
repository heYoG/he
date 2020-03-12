package com.dj.seal.docPrint.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.docPrint.dao.api.IDocPrintDao;
import com.dj.seal.docPrint.service.api.IDocPrintService;
import com.dj.seal.docPrint.vo.DocPrintSetVo;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.structure.dao.po.DocPrintRoleUser;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.table.DocPrintRoleUserUtil;
import com.dj.seal.util.web.SearchForm;

public class DocPrintServiceImpl implements IDocPrintService{
	IDocPrintDao docPrint_dao;
	IUserService user_service;
	ISysRoleService role_service;
	
	static Logger logger = LogManager.getLogger(DocPrintServiceImpl.class.getName());
	
	public void setRole_service(ISysRoleService role_service) {
		this.role_service = role_service;
	}

	public void setUser_service(IUserService user_service) {
		this.user_service = user_service;
	}

	public void setDocPrint_dao(IDocPrintDao docPrint_dao) {
		this.docPrint_dao = docPrint_dao;
	}

	@Override
	public void addRoleUser(String doc_no, String user_id, String role_nos,int printNum) {
		String[] struser = user_id.split(",");
		String[] strrole = role_nos.split(",");
		if (!user_id.equals("")) {
			for (int i = 0; i < struser.length; i++) {
				if (!struser[i].equals("") || !struser[i].equals(null)) {
					DocPrintRoleUser printInfo=getDocPrintRoleUserByUserid(doc_no,struser[i]);
					if(printInfo==null){
						docPrint_dao.addDocUser(doc_no, struser[i],printNum);
					}else{
						printInfo.setPrintnum(printNum);
						docPrint_dao.updateDocPrintRoleUser(printInfo);
					}
				}
			}
		}
		if (!role_nos.equals("")) {
			for (int i = 0; i < strrole.length; i++) {
				if (!strrole[i].equals("") || !strrole[i].equals(null)) {
					DocPrintRoleUser printInfo=getDocPrintRoleUserByRoleid(doc_no,strrole[i]);
					if(printInfo==null){
						docPrint_dao.addDocRole(doc_no, strrole[i],printNum);
					}else{
						printInfo.setPrintnum(printNum);
						docPrint_dao.updateDocPrintRoleUser(printInfo);
					}
				}
			}
		}
		
	}
	/**
	 * 查询打印设置按照角色
	 * @param pageIndex 当前页
	 * @param pageSize 每页个数
	 * @param doc_no 文档编号
	 * @return
	 */
	@Override
	public PageSplit showRoleSet(int pageIndex, int pageSize,
			String doc_no,SearchForm form) {
		String sql="select * from "+DocPrintRoleUserUtil.TABLE_NAME +" where "+DocPrintRoleUserUtil.DOC_NO+"='"+doc_no+"' and " +
				DocPrintRoleUserUtil.TYPE+ "=1";
		return getDocPrintSet(sql,pageIndex,pageSize,1);
	}
	
	/**
	 * 查询打印设置按照用户
	 * @param pageIndex 当前页
	 * @param pageSize 每页个数
	 * @param doc_no 文档编号
	 * @return
	 */
	@Override
	public PageSplit showUserSet(int pageIndex, int pageSize,
			String doc_no,SearchForm form) {
		String sql="select * from "+DocPrintRoleUserUtil.TABLE_NAME +" where "+DocPrintRoleUserUtil.DOC_NO+"='"+doc_no+"' and " +
		DocPrintRoleUserUtil.TYPE+ "=0";
		return getDocPrintSet(sql,pageIndex,pageSize,0);
	}
	/**
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	private PageSplit getDocPrintSet(String sql,int pageIndex,int pageSize,int type){
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<DocPrintRoleUser> list=docPrint_dao.getRoleUserList(str);
		List<DocPrintSetVo> vos=new ArrayList<DocPrintSetVo>();
		for(int i=0;i<list.size();i++){
			DocPrintRoleUser docSet=list.get(i);
			DocPrintSetVo vo=new DocPrintSetVo();
			vo.setUser_id(docSet.getUser_id());
			if(type==0){
				SysUser user=user_service.showSysUserByUser_id(docSet.getUser_id());
				vo.setUsername(user.getUser_name());
			}	
			if(type==1){
				try {
					SysRole role=role_service.showSysRoleByRole_id(Integer.valueOf(docSet.getUser_id()));
					vo.setUsername(role.getRole_name());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			vo.setCurrNum(docSet.getCurrnum());
			vo.setPrintNum(docSet.getPrintnum());
			vos.add(vo);
		}
		PageSplit pageSplit = new PageSplit();
		pageSplit.setDatas(vos);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(docPrint_dao.showCount(sql));
		return pageSplit;
	}
	/**
	 * 根据文档编号和用户id获取对象
	 * @param doc_no 文档编号
	 * @param user_id 用户id
	 * @return
	 */
	@Override
	public DocPrintRoleUser getDocPrintRoleUserByUserid(String doc_no,String user_id){
		String sql="select * from "+DocPrintRoleUserUtil.TABLE_NAME +" where "+DocPrintRoleUserUtil.DOC_NO+"='"+doc_no+"' and " +
		DocPrintRoleUserUtil.TYPE+"=0 and "+DocPrintRoleUserUtil.USER_ID+ "='"+user_id+"'";
		List<DocPrintRoleUser> list=docPrint_dao.getRoleUserList(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据文档编号和角色id获取对象
	 * @param doc_no 文档编号
	 * @param role_id 角色id
	 * @return
	 */
	public DocPrintRoleUser getDocPrintRoleUserByRoleid(String doc_no,String role_id){
		String sql="select * from "+DocPrintRoleUserUtil.TABLE_NAME +" where "+DocPrintRoleUserUtil.DOC_NO+"='"+doc_no+"' and " +
		DocPrintRoleUserUtil.TYPE+"=1 and "+DocPrintRoleUserUtil.USER_ID+ "='"+role_id+"'";
		List<DocPrintRoleUser> list=docPrint_dao.getRoleUserList(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据文档编号和角色id获取对象
	 * @param doc_no 文档编号
	 * @param role_id 角色id[]
	 * @return
	 */
	@Override
	public DocPrintRoleUser getDocPrintRoleUserByRoleid(String doc_no,List<SysRole> rols){
		StringBuffer sb=new StringBuffer("(");
		for(int i=0;i<rols.size()-1;i++){
			sb.append("'"+rols.get(i).getRole_id()+"',");
		}
		sb.append("'"+rols.get(rols.size()-1).getRole_id()+"'");
		sb.append(")");
		
		String sql="select * from "+DocPrintRoleUserUtil.TABLE_NAME +" where "+DocPrintRoleUserUtil.DOC_NO+"='"+doc_no+"' and " +
		DocPrintRoleUserUtil.TYPE+"=1 and "+DocPrintRoleUserUtil.USER_ID+ " in"+sb.toString();
		logger.info(sql);
		List<DocPrintRoleUser> list=docPrint_dao.getRoleUserList(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 更新打印情况
	 * @return
	 */
	@Override
	public void updateDocPrintRoleUser(DocPrintRoleUser printInfo){
		docPrint_dao.updateDocPrintRoleUser(printInfo);
	}
	/**
	 * 更新打印情况,根据用户
	 * @return
	 */
	public void updateDocPrintRoleUserbyUser(String user_id,String doc_no,int newNum){
		//先根据用户id和文档号查询
		DocPrintRoleUser printInfo=getDocPrintRoleUserByUserid(doc_no, user_id);
	    //设置新属性
		printInfo.setPrintnum(newNum);
		//调用已有的更新方法更新
		updateDocPrintRoleUser(printInfo);
	}
	/**
	 * 更新打印情况,根据角色id
	 * @return
	 */
	public void updateDocPrintRoleUserbyRoleid(String role_id,String doc_no,int newNum){
		//先根据用户id和文档号查询
		DocPrintRoleUser printInfo=this.getDocPrintRoleUserByRoleid(doc_no, role_id);
	    //设置新属性
		printInfo.setPrintnum(newNum);
		//调用已有的更新方法更新
		updateDocPrintRoleUser(printInfo);
	}
}
