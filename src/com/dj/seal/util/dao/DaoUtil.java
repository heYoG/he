package com.dj.seal.util.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibm.db2.jcc.DBTimestamp;

public class DaoUtil {
	static Logger logger = LogManager.getLogger(DaoUtil.class.getName());

	/**
	 * @description 把查询出来的Map对象存储为PO对象
	 * @param obj 需要存储的PO对象
	 * @param map 查询出来的Map对象
	 * @param objUtil 静态变量类
	 * @return    
	 */
	@SuppressWarnings("unchecked")
	public static Object setPo(Object obj, Map map, Object objUtil) {
		Map mapObjUtil = ConstructSql.objReflect(objUtil);
		Iterator it = mapObjUtil.entrySet().iterator();
		Class c = obj.getClass();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			Object value = map.get(mapEntry.getValue());//根据表类值(对应数据库字段)作为键找map找的值(数据库得到的值)
			String key=(String)mapEntry.getKey();//表类属性
			//解决oracle认为空字符串为null的问题
			if(value==null&&!"TABLE_NAME".equals(key)){
				value="";
			}
			if (value != null) {
				try {
					//logger.info(key+":value==="+value +"type:=="+value.getClass());
					key=key.charAt(0)+key.substring(1,key.length()).toLowerCase();//属性第二个字符开始转为小写
					Method mGet=c.getDeclaredMethod("get"+key);
					if(value.getClass().equals(BigDecimal.class)){//如果是oracle里的int转换来的
						//logger.info("DaoUtil----Oracle");
						value=Integer.valueOf(value.toString());//转换成Integer类型
//						System.out.print("value==="+value);
					}
					if(value.getClass().equals(BigDecimal.class)){//如果是db2里的decimal转换来的
						//logger.info("DaoUtil----db2");
						BigDecimal bd=(BigDecimal)value;
						value=bd.doubleValue();//转换成double类型
					}
					if(value.getClass().equals(DBTimestamp.class)){//如果是db2里的timestamp类型
						DBTimestamp dbt=(DBTimestamp)value;
						value=new Timestamp(dbt.getTime());
//						logger.info("value=="+dbt.getTime());
					}
					if(value.getClass().equals(Date.class)){//如果是java.sql.Date类型
						Date date=(Date)value;
						value=new Timestamp(date.getTime());
						
					}
					if(value.getClass().equals(Integer.class)){//如果是Integer类型
						Method mSet = c.getDeclaredMethod("set" + key,mGet.getReturnType());
						mSet.invoke(obj, value);
					}
					if(mGet.getReturnType().equals(Integer.class)&&"".equals(value)){
						continue;
					}
					if(mGet.getReturnType().equals(value.getClass())){//属性设置值
						Method mSet = c.getDeclaredMethod("set" + key,mGet.getReturnType());
						mSet.invoke(obj, value);
					}
//					if(mGet.getReturnType().equals(value.getClass())){
//						Method mSet = c.getDeclaredMethod("set" + key,mGet.getReturnType());
//						mSet.invoke(obj, value);
//					}
					else{
						//logger.info("DaoUtil这里？类型不匹配");
					}
				} catch (Throwable e) {
//					logger.info("DaoUtil这里？");
					System.err.println(e);
				}
			}
		}
		return obj;
	}
	//获取表中的所有字段名，字段名
	@SuppressWarnings("unchecked")
	public static String allFields(Object objUtil){
		StringBuffer sb=new StringBuffer();
		Map map=ConstructSql.objReflect(objUtil);		
		map.remove("TABLE_NAME");
		Collection list=map.values();
		for (Object object : list) {
			sb.append(object).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}
	
	/**
	 * 根据value值拆分成2的N次方数的集合，这些数之和等于value
	 * 给一个用户所拥有的权限值，返回权限集合
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set showFuncs(int value) {
		Set set = new HashSet();		
		for (int i = 0; i < 31; i++) {
			int temp = 1 << i;
			if ((value & temp) == temp) {
				set.add(temp);
			}
		}
		return set;
	}
	
	/**
	 * 返回两个集合的并集
	 * @param set1
	 * @param set2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set bingJi(Set set1,Set set2){
		for (Object object : set2) {
			set1.add(object);
		}
		return set1;
	}
	
	/**
	 * 返回两个集合的交集
	 * @param set1
	 * @param set2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set jiaoJi(Set set1,Set set2){
		Set set=new HashSet<Object>();
		for (Object object : set2) {
			if(set1.contains(object)){
				set.add(object);
			}
		}
		return set;
	}
	
	/**
	 * 返回集合2相对于集合1的补集
	 * @param set1
	 * @param set2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set buJi(Set set1,Set set2){
		for (Object object : set2) {
			set1.remove(object);
		}
		return set1;
	}
	
	/**
	 * 返回集合中所有元素之和
	 * 给出权限集合，求权限和值
	 * @param set
	 * @return
	 */
	public static Integer sumOfSet(Set<Integer> set){
		Integer sum=0;
		for (Integer integer : set) {
			sum+=integer;
		}
		return sum;
	}
	
	/**
	 * 返回两个列表的并集
	 * @param list1
	 * @param list2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List bingJi(List list1,List list2){
		for (Object obj : list2) {
			//contains方法需要对象所属的类重写equals()方法，如两个对象的属性相等，则返回true
			//如果不重写将调用Object类的equals()方法，后果自己想去
			if(!list1.contains(obj))
				list1.add(obj);
		}
		return list1;
	}
	
	
	public static String quotes(String str) {
//		char[] cs=str.toCharArray();
//		for(int i=0;i<cs.length;i++){
//			if(cs[i]=='\\'&&cs[i+1]=='\''){
//				
//			}
//		}
		//String s = str.replaceAll("'", "\\\\'");//替换成‘\'’
		String str1=null;
		//删除引号
		StringBuffer buf=new StringBuffer(str);
		while(buf.indexOf("'")!=-1){
			buf.deleteCharAt(buf.indexOf("'"));
		}
		while(buf.indexOf("\\")!=-1){
			buf.deleteCharAt(buf.indexOf("\\"));
		}
		if(buf.toString().length()>0)
		{		
			str1=buf.toString();
		}
		return str1;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*	quotes("faadfa\'fas");
		Class c=new String("dd").getClass();
		String str="dfa";
		str.charAt(1);
		try {
			String key="DFAD_FADSF";
			logger.info(key);
			key=key.charAt(0)+key.substring(1,key.length()).toLowerCase();
			logger.info(key);
			
			Method m=c.getDeclaredMethod("charAt",int.class);
			logger.info(m.getReturnType());
			logger.info(m.getName());
		} catch (SecurityException e) {
			logger.error(e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage());
		}*/
		
//		logger.info(allFields(new SysDepartmentUtil()));
		BigDecimal bd=new BigDecimal(555);
		Integer it=Integer.valueOf(bd.toString());
		logger.info(bd.toString()+"::"+it);
	}

}
