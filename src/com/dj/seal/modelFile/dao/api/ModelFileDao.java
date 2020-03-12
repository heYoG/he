package com.dj.seal.modelFile.dao.api;

import java.util.List;
import java.util.Map;

import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.po.ModelXieyi;
import com.dj.seal.util.exception.DAOException;

public interface ModelFileDao {

	/**
	 * 新增模板文件
	 * 
	 * @param modelFileForm
	 * @throws DAOException
	 */ 
	public int addModelFile(ModelFile modelFile) throws Exception;
	/**
	 * 新增模板协议配置关系
	 * 
	 * @param modelFileForm
	 * @throws DAOException
	 */ 
	public int addModelXieyi(ModelXieyi modelxieyi);
	
	/**
	 * 根据id查询模板文件
	 * 
	 * @param id
	 * @return ModelFile
	 * @throws DAOException
	 */
	public ModelFile getModelFileById(Integer id) throws Exception;
	
	/**
	 * 根据语句查询模板文件
	 * 
	 * @param sql
	 * @return modelFile
	 * @throws DAOException
	 */
	public ModelFile getModelFileBySql(String sql) throws Exception;
	
	/**
	 * 根据语句查询所有模板文件
	 * 
	 * @param sql
	 * @return List<ModelFile>
	 * @throws DAOException
	 */
	public List<ModelFile> getModelFilesBySql(String sql) throws Exception;
	
	/**
	 * 根据语句修改模板文件
	 * 
	 * @param modelFile
	 * @throws DAOException
	 */ 
	public void updateModelFile(ModelFile modelFile) throws Exception;
	
	/**
	 * 根据id删除模板文件
	 * 
	 * @param id
	 * @throws DAOException
	 */ 
	public void deleteModelFile(Integer id);
	public List<ModelFile> getmodelFileList(String sql);
	/**
	 * 根据id注销模板文件
	 * 
	 * @param id
	 * @throws DAOException
	 */ 
	public void zhuxiaoModelFile(Integer id);
	/**
	 * 根据id激活模板文件
	 * 
	 * @param id
	 * @throws DAOException
	 */ 
	public void jihuoModelFile(Integer id);

	
	public int showCount(String sql) throws Exception;
	
	public List<Map> selects(String sql);		// add by liuph
	
	public void approveModel(ModelFile modelfile,String ID);
	
}
