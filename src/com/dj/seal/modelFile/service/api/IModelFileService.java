package com.dj.seal.modelFile.service.api;

import java.util.List;

import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.vo.ModelFileVo;


public interface IModelFileService {

	/**
	 * 得到模板文件记录集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ModelFileVo> showModelFiles() throws Exception;


	/**
	 * 根据ID得到模板文件记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ModelFileVo getModelFile(Integer id) throws Exception;


	/**
	 * 新增模板文件记录
	 * 
	 * @param obj
	 * @return 1为成功，0为失败
	 * @throws Exception
	 */
	public int addModelFile(ModelFile obj) throws Exception;


	/**
	 * 修改模板文件记录
	 * 
	 * @param obj
	 * @return 1为成功，0为失败
	 * @throws Exception
	 */
	public int updModelFile(ModelFile obj) throws Exception;


	/**
	 * 根据模板编号删除模板文件记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delModelFile(Integer id) throws Exception;

	/**
	 * 根据模板编号删除无纸化模板文件记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delHotelModelFile(Integer id) throws Exception;
	
	public int showCount(String sql) throws Exception;
	
	/**
	 * 模板数据备份
	 */
	//public int backupModel();
	
	/**
	 * 删除原表已备份模板
	 */
	//public int delModel();
	
}
