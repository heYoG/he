package dao.deptDao.api;

import java.io.Serializable;
import java.util.List;

import util.CommenClass;

public interface IDeptDao<T> {
	
	/**
	 * hibernate分页查询所有部门信息
	 * @param	cc	封装分页数据		
	 * @return
	 */
	public List<T> getDeptInfos(T t,CommenClass cc);
	
	/**
	 * 普通查询所有部门信息
	 * @return
	 */
	public List<T> getDeptInfos();
	
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
	
	/**
	 * hibernate查询记录总数
	 * @param	t 实例变量
	 * @return
	 */
	public long getCount(T t);
}
