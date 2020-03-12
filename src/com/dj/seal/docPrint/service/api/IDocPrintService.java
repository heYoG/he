package com.dj.seal.docPrint.service.api;

import java.util.List;

import com.dj.seal.structure.dao.po.DocPrintRoleUser;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.web.SearchForm;

public interface IDocPrintService {
	/**
	 * 设置文档打印份数
	 * @param doc_no 文档编号
	 * @param user_id 以逗号分割的用户id列表
	 * @param role_nos 以逗号分割的角色id列表
	 * @param printNum 可打印份数
	 */
	public void addRoleUser(String doc_no, String user_id, String role_nos,int printNum);
	/**
	 * 根据文档编号查询文档的打印设置情况，返回按用户设置的结果,分页查询
	 * @param doc_no 文档编号
	 * @param pageIndex 当前页码
	 * @param pageSize 每页数量
	 * @return vo
	 */
	public PageSplit showUserSet(int pageIndex,int pageSize,String doc_no,SearchForm form);
	/**
	 * 根据文档编号查询文档的打印设置情况，返回按角色设置的结果,分页查询
	 * @param doc_no 文档编号
	 * @param pageIndex 当前页码
	 * @param pageSize 每页数量
	 * @return vo
	 */
	public PageSplit showRoleSet(int pageIndex,int pageSize,String doc_no,SearchForm form);
	/**
	 * 根据文档编号和用户id获取对象
	 * @param doc_no 文档编号
	 * @param user_id 用户id
	 * @return
	 */
	public DocPrintRoleUser getDocPrintRoleUserByUserid(String doc_no,String user_id);
	/**
	 * 更新打印情况
	 * @return
	 */
	public void updateDocPrintRoleUser(DocPrintRoleUser printInfo);
	/**
	 * 根据文档编号和角色id获取对象
	 * @param doc_no 文档编号
	 * @param role_id 角色id[]
	 * @return
	 */
	public DocPrintRoleUser getDocPrintRoleUserByRoleid(String doc_no,List<SysRole> rols);
}
