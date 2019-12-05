package dao.fileDao.api;

import java.util.List;

import vo.fileVo.FileManageVo;

public interface IFileManageDao<T,G> {
	
	/**
	 * 查询单条记录
	 * @param id  文件唯一id
	 * @return     文件信息
	 */
	public FileManageVo getFileInfo(int id);
	
	/**
	 * 根据条件分页查询所有文件信息
	 * @param type		查询类型，0：查询已删除文件，1：查询正常文件
	 * @param authValue 权限值，用于区分管理员和普通用户查询
	 * @param t			实例对象
	 * @return
	 */
	public List<T> getFileInfo(String type,int authValue,T t,G g);
	
	/**
	 * 删除单个文件
	 * @param id 文件唯一标识
	 * @param flag 删除标识 0:彻底从数据库删除;1:虚拟删除，更改文件状态
	 * @return
	 */
	public int deleteFile(int id,int flag);
	
	/**
	 * 批量删除文件
	 * @param id 文件唯一标识
	 * @return
	 */
	public int deleteFiles(int ...id);
	
	/**
	 * 在线浏览文件
	 * @param id  文件唯一标识
	 */
	public void browse(int id);
	
	/**
	 * 下载单个文件
	 * @param id  文件唯一标识
	 */
	public void downFile(int id);
	
	/**
	 * 批量下载文件
	 * @param id  文件唯一标识
	 */
	public void downFiles(int ...id);
	
	/**
	 * 上传单个文件
	 */
	public void uploadFile(FileManageVo fileMangeVo);
	
	/**
	 * 批量上传文件
	 */
	public void uploadFiles();
	
	/**
	 * 备份还原文件
	 * @param id 文件唯一标识，删除时是虚拟删除,会定时清理
	 * @return
	 */
	public int updateFileStatus(int id);
	
	/**
	 * 获取总记录数
	 * @param  flag   标识：flag=0:已删除总数;flag=1:未删除总数
	 * @return 记录总数
	 */
	public int getCount(int flag);
}
