package hibernate.dao.deptDao.api;

import java.io.Serializable;
import java.util.List;

public interface IDeptDao<T> {
	
	/**
	 * hibernate查询所有部门信息
	 * @return
	 */
	public List<T> getDeptInfos(T t);
	
	/**
	 * 根据条件查询单条数据
	 * @param obj	查询条件
	 * @return
	 */
	public List<T> getDeptInfo(T t,String obj);
	
	/**
	 * 添加部门
	 * @param t  类对象
	 * @return
	 */
	public Serializable addDept(T t);
	
	/**
	 * 根据条件删除部门
	 * @param obj 条件
	 * @return
	 */
	public void delDept(T t,Serializable obj);
}
