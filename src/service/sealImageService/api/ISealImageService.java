package service.sealImageService.api;

import java.util.List;

/**
 * 印模业务层接口
 * @author Administrator
 * @param <T>
 *
 */
public interface ISealImageService<T,G,M> {
	
	/**
	 * 	单个新增印模
	 * @return	插入数据返回值
	 */
	public abstract int newSealImage(T t,M m);
	
	/**
	 *	删除印模
	 * @param id	印模id
	 * @return		删除数据返回值
	 */
	public int deleteSealImage(int id);
	
	/**
	 * 	批量删除印模
	 * @param ids	印模id
	 * @return		删除数据返回值
	 */
	public int deleteSealImage(String ids);
	
	/**
	 * 	单个修改印模状态
	 * @param id		印模id
	 * @param status	修改后印模状态
	 * @return			修改返回值
	 */
	public int updateSealImage(String id,String status);
	
	/**
	 * 	根据印模id查询单条数据
	 * @param id	印模id
	 * @return		引用对象
	 */
	public T selectSealImage(int id);
	
	/**
	 * 	分页查询印模数据
	 * @param g		分页类变量
	 * @param status	查询状态 0：已注销;1：正常;2:待审批
	 * @return		T类型集合
	 */
	public List<T> pageSelectSealImage(G g,String status);

	/**
	 * 查询印模总记录数
	 * @param status	查询状态 0：已注销;1：正常;2:待审批
	 * @return		记录数
	 */
	public int getSealImageCount(String status);
}
