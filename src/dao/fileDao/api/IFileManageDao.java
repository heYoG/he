package dao.fileDao.api;

import java.util.List;

import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;

public interface IFileManageDao<T,G,M> {
	
	/**
	 * 查询单条记录
	 * @param id  文件唯一id
	 * @return     文件信息
	 */
	public FileManageVo getFileInfo(int id);
	
	/**
	 * 根据条件分页查询所有文件信息
	 * @param t			实例对象
	 * @return
	 */
	public List<T> getFileInfo(T t,G g,M m);
	
	/**
	 * 删除单个文件
	 * @param id 文件唯一标识
	 * @param flag 删除标识 0:彻底从数据库删除;1:虚拟删除，更改文件状态
	 * @return
	 */
	public int deleteFile(int id,int flag);
	
	/**
	 * 批量删除文件
	 * @param	ids 	文件唯一标识
	 * @param	flag	删除标识 0:彻底删除;1:虚拟删除,更改文件状态
	 * @return
	 */
	public int deleteFiles(String ids,int flag);
	
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
	 * @param ids			文件唯一标识，删除时是虚拟删除,会定时清理
	 * @param batchFiles	恢复标识 0:单个文件恢复;1:批量恢复
	 * @return
	 */
	public int updateFileStatus(String ids,int batchFiles);
	
	/**
	 * 获取总记录数
	 * @param  flag			标识：flag=0:已删除总数;flag=1:未删除总数
	 * @param  m			实例：按个人查询与管理员查询
	 * 						权限：区分管理员(1)和普通用户(2)
	 * @return 记录总数
	 */
	public int getCount(String flag,M m);
}
