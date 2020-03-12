package com.dj.seal.util.dao;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.Constants;
import com.neusoft.po.RecordRoomMergePO;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;

/**
 * @description JDBC插入和修改对象的为工具类
 * @author oyxy
 * @since 2009-11-4
 */
public class ConstructSql {
	static Logger logger = LogManager.getLogger(ConstructSql.class.getName());

	/**
	 * @description 返回插入对象的SQL语句
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String composeInsertSql(Object obj, Object objUtil) {
		// SQL语句
		StringBuilder sb = new StringBuilder("insert into ");
		// 根据静态变量工具类得到表名
		Map mapUtil = objReflect(objUtil);
		sb.append(mapUtil.get("TABLE_NAME")).append("(");
		// 字段列表串
		StringBuilder fields = new StringBuilder();
		// 内容列表串
		StringBuilder paras = new StringBuilder();
		// 得到（字段，内容）对
		Map map = pojoUtil(objReflect(obj), objReflect(objUtil));
		// 遍历，分别拼接成字段和内容的字符串
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			if (mapEntry.getKey() != null) {
				// 拼接字段的字符串
				fields.append(mapEntry.getKey()).append(",");
				// 拼接内容的字符串
				if (mapEntry.getValue() != null) {//表工具类的值为属性
					// 如果是oracle数据库，时间类需要进行转换
					if (mapEntry.getValue().getClass().equals(Timestamp.class)
							&& Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
						StringBuffer time_sb = new StringBuffer();
						time_sb.append("to_date('")
								.append(
										mapEntry.getValue().toString()
												.substring(0, 19));
						time_sb.append("','yyyy-mm-dd hh24:mi:ss')");
						paras.append(time_sb).append(",");
					}else if(mapEntry.getValue().getClass().equals(Date.class)
							&& Constants.DB_TYPE == DBTypeUtil.DT_ORCL){
						StringBuffer time_sb = new StringBuffer();
						time_sb.append("to_date('")
								.append(
										mapEntry.getValue().toString());
						time_sb.append("','yyyy-mm-dd')");
						paras.append(time_sb).append(",");
					} else {
						if (String.class.equals(mapEntry.getValue().getClass())) {
							String str = (String) mapEntry.getValue();
							str = str.replaceAll("'", "‘");
							paras.append("'").append(str).append("',");
						} else {
							paras.append("'").append(mapEntry.getValue())
									.append("',");
						}
					}
				} else {
					paras.append(mapEntry.getValue()).append(",");
				}
			}
		}
		// 去掉最后的","
		if(fields.lastIndexOf(",")!=-1){
			fields.deleteCharAt(fields.lastIndexOf(","));
		}
		if(paras.lastIndexOf(",")!=-1){
			paras.deleteCharAt(paras.lastIndexOf(","));
		}
		// 得到最终的SQL语句
		sb.append(fields).append(") VALUES(").append(paras).append(")");
		//logger.info(sb.toString());
		return sb.toString();
	}

	
	/**
	 * @description 返回插入对象的SQL语句  月表
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String composeInsertSqlNew(Object obj, Object objUtil,String tablename) {
		// SQL语句
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(tablename).append("(");
		// 字段列表串
		StringBuilder fields = new StringBuilder();
		// 内容列表串
		StringBuilder paras = new StringBuilder();
		// 得到（字段，内容）对
		Map map = pojoUtil(objReflect(obj), objReflect(objUtil));
		// 遍历，分别拼接成字段和内容的字符串
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			if (mapEntry.getKey() != null) {
				// 拼接字段的字符串
				fields.append(mapEntry.getKey()).append(",");
				// 拼接内容的字符串
				if (mapEntry.getValue() != null) {
					// 如果是oracle数据库，时间类需要进行转换
					if (mapEntry.getValue().getClass().equals(Timestamp.class)
							&& Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
						StringBuffer time_sb = new StringBuffer();
						time_sb.append("to_date('")
								.append(
										mapEntry.getValue().toString()
												.substring(0, 19));
						time_sb.append("','yyyy-mm-dd hh24:mi:ss')");
						paras.append(time_sb).append(",");
					} else {
						if (String.class.equals(mapEntry.getValue().getClass())) {
							String str = (String) mapEntry.getValue();
							str = str.replaceAll("'", "‘");
							paras.append("'").append(str).append("',");
						} else {
							paras.append("'").append(mapEntry.getValue())
									.append("',");
						}
					}
				} else {
					paras.append(mapEntry.getValue()).append(",");
				}
			}
		}
		// 去掉最后的","
		if(fields.lastIndexOf(",")!=-1){
			fields.deleteCharAt(fields.lastIndexOf(","));
		}
		if(paras.lastIndexOf(",")!=-1){
			paras.deleteCharAt(paras.lastIndexOf(","));
		}
		// 得到最终的SQL语句
		sb.append(fields).append(") VALUES(").append(paras).append(")");
		return sb.toString();
	}
	
	
	/**
	 * @description 返回修改对象的SQL语句
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String composeUpdateSql(Object obj, Object objUtil,
			String parasStr) {
		// SQL语句
		StringBuilder sb = new StringBuilder("update ");
		// 根据静态变量工具类得到表名
		Map mapUtil = objReflect(objUtil);
		sb.append(mapUtil.get("TABLE_NAME")).append(" set ");
		// 更新列表串
		StringBuilder updates = new StringBuilder();

		// 得到（字段，内容）对
		Map map = pojoUtil(objReflect(obj), objReflect(objUtil));
		// 遍历，拼接成更新内容的字符串
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			// 拼接字符串
			if (mapEntry.getValue() != null && mapEntry.getKey() != null) {
				// 如果是oracle数据库，时间类需要进行转换
				if (mapEntry.getValue().getClass().equals(Timestamp.class)
						&& Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
					StringBuffer time_sb = new StringBuffer();
					time_sb.append("to_date('").append(
							mapEntry.getValue().toString().substring(0, 19));
					time_sb.append("','yyyy-mm-dd hh24:mi:ss')");
					updates.append(mapEntry.getKey()).append("=").append(
							time_sb).append(",");
				} else {
					
					if (String.class.equals(mapEntry.getValue().getClass())) {
						String str = (String) mapEntry.getValue();
						str = str.replaceAll("'", "''");
						updates.append(mapEntry.getKey()).append("='").append(
								str).append("',");
					} else {
						updates.append(mapEntry.getKey()).append("='").append(
								mapEntry.getValue()).append("',");
					}
				}
			}
		}
		// 去掉最后的","
		updates.deleteCharAt(updates.lastIndexOf(","));
		// 得到最终的SQL语句
		sb.append(updates).append(" where ").append(parasStr);
		return sb.toString();
	}

	/**
	 * @description 返回修改对象的SQL语句(重载)
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String composeUpdateSql(Object obj, Object objUtil) {
		// SQL语句
		StringBuilder sb = new StringBuilder("update ");
		// 根据静态变量工具类得到表名
		Map mapUtil = objReflect(objUtil);
		sb.append(mapUtil.get("TABLE_NAME")).append(" set ");
		// 更新列表串
		StringBuilder updates = new StringBuilder();

		// 得到（字段，内容）对
		Map map = pojoUtil(objReflect(obj), objReflect(objUtil));
		// 遍历，拼接成更新内容的字符串
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			// 拼接字符串
			if (mapEntry.getValue() != null) {
				updates.append(mapEntry.getKey()).append("='").append(
						mapEntry.getValue()).append("',");
			}
		}
		// 去掉最后的","
		updates.deleteCharAt(updates.lastIndexOf(","));
		return sb.append(updates).toString();
	}

	/**
	 * @description 根据对象返回有get()方法的属性 （名称，值）对
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map objReflect(Object obj) {
		Map hashMap = new HashMap();
		try {
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					String name = m[i].getName();
					name = name.substring(3, name.length());
					hashMap.put(name, m[i].invoke(obj, new Object[0]));
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		return hashMap;
	}

	/**
	 * @description 返回与数据库对应的字段和内容的Map对
	 * @param map
	 *            PO的反射对象
	 * @param mapUtil
	 *            静态变量类的反射对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map pojoUtil(Map map, Map mapUtil) {
		Map hashMap = new HashMap();
		Iterator it = map.entrySet().iterator();
		// 遍历PO反射对象的属性
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			String key = (String) mapEntry.getKey();
			key = key.toUpperCase();
			// 根据PO的属性名找到静态变量类中对应的值
			String field = (String) mapUtil.get(key);
			// 构造与数据库中对应的（字段，内容）对
			hashMap.put(field, mapEntry.getValue());
		}
		return hashMap;
	}

	/**
	 * @description 测试的例子
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SysUnit sysUnit = new SysUnit();
//		sysUnit.setBank_name("Bank_name");
//		sysUnit.setBank_no("band_no");
//		sysUnit.setDept_no("dept_no");
//		sysUnit.setFax_no("fax_no");
//		sysUnit.setPost_no("post_no");
//		sysUnit.setTel_no("tel_no");
//		sysUnit.setUnit_address("unit_address");
//		sysUnit.setUnit_email("fasd'fasdf'fasdsdf'");
//		// sysUnit.setDddddd("ddddd");
//		SysUnitUtil sysUnitUtil = new SysUnitUtil();
//		pojoUtil(objReflect(sysUnit), objReflect(sysUnitUtil));
//		logger.info(composeInsertSql(sysUnit, sysUnitUtil));
//		// String parasStr="1=1";
//		// logger.info(composeUpdateSql(sysUnit, sysUnitUtil, parasStr));
		
		RecordRoomMergePO recordRooomMergePO = new RecordRoomMergePO();
		recordRooomMergePO.setCid("1111");
		recordRooomMergePO.setMergedate("20190908");
		HotelRecordRoomMergeUtil hotelRecordRoomMergeUtil = new HotelRecordRoomMergeUtil();
		logger.info(composeInsertSql(recordRooomMergePO, hotelRecordRoomMergeUtil));
		logger.info(composeInsertSqlNew(recordRooomMergePO, hotelRecordRoomMergeUtil, HotelRecordRoomMergeUtil.TABLE_NAME));
	}

}
