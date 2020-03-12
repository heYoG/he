package com.dj.seal.util.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dj.seal.util.Constants;

/**
 * 根据数据库类型得到相关的SQL语句的工具类
 * 
 * @author oyxy
 * 
 */
public class DBTypeUtil {
	static Logger logger = LogManager.getLogger(DBTypeUtil.class.getName());
	static Logger log = LogManager.getLogger(DBTypeUtil.class.getName());
	static int num_count = 0;
	// 数据库类型
	public final static int DT_MYSQL = 1;
	public final static int DT_ORCL = 2;
	public final static int DT_MSSQL = 3;
	public final static int DT_DB2 = 4;

	/**
	 * 根据数据库类型、查询条件、请求页数和每页显示数，返回符合数据库的sql语句
	 * 
	 * @param sql
	 * @param pageindex
	 * @param pagesize
	 * @param dbtype
	 * @return
	 */
	public static String splitSql(String sql, int pageindex, int pagesize,
			int dbtype) {
		StringBuffer sb = new StringBuffer();
		int left = (pageindex - 1) * pagesize;
		int num_start = (pageindex - 1) * pagesize + 1;// 从第几条记录开始
		int num_end = pageindex * pagesize;// 到第几条记录结束
		switch (dbtype) {
		case DT_MSSQL:
			// 待开发
			// Select top 10 * from t_order where id not in (select id from
			// t_order where id>5 );
			break;
		case DT_MYSQL:
			sb.append(sql).append(" ").append(" limit ").append(left).append(",").append(
					pagesize);
			break;
		case DT_ORCL:

			sb.append("select * from (select rownum rn,ta.* from (");
			sb.append(sql);
			sb.append(") ta where rownum<=").append(num_end);
			sb.append(") where rn>").append(num_start - 1);

			break;
		case DT_DB2:
			num_count++;
			if (num_count > 20000) {
				num_count = 0;
			}
			//有改动，添加排序，需要db2数据库进行测试 ORDER BY C_DEJ DESC 
			sb.append("select * from (SELECT B.*, ROWNUMBER() OVER( ) AS ");
			sb.append("RN").append(num_count);
			sb.append(" FROM(");
			sb.append(sql);
			sb.append(") AS B )AS A WHERE A.RN").append(num_count);
			sb.append(" BETWEEN ").append(num_start);
			sb.append(" AND ").append(num_end);
			break;
		default:
			break;
		}
		//log.info(sb.toString());
		return sb.toString();
	}

	
	//根据数据库类型返回时间类型的比较sql字符串
	public static String dateSqlByDBType(String fieldname, String begindate, String enddate) {
		StringBuffer sb = new StringBuffer();
		if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){//mysql数据库语句
			sb.append(fieldname).append(">'");
			sb.append(begindate).append("' and ");
			sb.append(fieldname).append("<'");
			sb.append(enddate).append("'");
		}else if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){//oracle数据库语句
			sb.append(fieldname).append(" between ");
			sb.append("to_date('").append(begindate).append("','yyyy-mm-dd hh24:mi:ss')");
			sb.append(" and ");
			sb.append("to_date('").append(enddate).append("','yyyy-mm-dd hh24:mi:ss')");
		}else{
			sb.append(fieldname).append(">'");
			sb.append(begindate).append("' and ");
			sb.append(fieldname).append("<'");
			sb.append(enddate).append("'");
		}
		return sb.toString();
	}
	
	
	/**
	 * 二进制与或运算在各种数据库中不同的写法
	 * 
	 * @param c1
	 * @param c2
	 * @param oper
	 * @param dbtype
	 * @return
	 */
	public static String bitOper(String c1, String c2, String oper, int dbtype) {
		StringBuffer sb = new StringBuffer();
		switch (dbtype) {
		case DT_MSSQL:
			// 待开发
			// Select top 10 * from t_order where id not in (select id from
			// t_order where id>5 );
			break;
		case DT_MYSQL:
			if ("&".equals(oper)) {
				sb.append(c1).append("&").append(c2);
			} else if ("|".equals(oper)) {
				sb.append(c1).append("|").append(c2);
			}
			break;
		case DT_DB2:
			if ("&".equals(oper)) {
				sb.append("bit_and(").append(c1).append(",");// 自定义的函数
				sb.append(c2).append(")");
			} else if ("|".equals(oper)) {
				sb.append("bit_or(").append(c1).append(",");
				sb.append(c2).append(")");
			}
			break;
		// 与运算
		// CREATE
		// FUNCTION DB2INST1.Bit_And (srvNum1 INT, srvNum2 INT) RETURNS INT
		// LANGUAGE SQL
		// BEGIN
		// ATOMIC
		// DECLARE V_tmp1 INT;
		// DECLARE V_tmp2 INT;
		// DECLARE V_bitValue1 INT;
		// DECLARE V_bitValue2 INT;
		// DECLARE V_Idx INT;
		// DECLARE V_RtnValue INT;
		// if srcNum1=0 then
		// set V_RtnValue=0;
		// else
		// IF srcNum1 >= srcNum2 THEN
		// SET V_tmp1 = srcNum1;
		// SET V_tmp2 = srcNum2;
		// ELSE
		// SET V_tmp1 = srcNum2;
		// SET V_tmp2 = srcNum1;
		// END IF;
		// SET V_RtnValue = V_tmp2;
		// SET V_Idx = 0;
		// WHILE V_tmp2 > 0 DO
		// SET V_bitValue2 = MOD(V_tmp2,2);
		// IF V_bitValue2 = 1 THEN
		// SET V_bitValue1 = MOD(V_tmp1,2);
		// IF V_bitValue1 = 0 THEN
		// SET V_RtnValue = V_RtnValue - POWER(2,V_Idx);
		// END IF;
		// END IF;
		// SET V_tmp1 = V_tmp1 / 2;
		// SET V_tmp2 = V_tmp2 / 2;
		// SET V_Idx = V_Idx + 1;
		// END WHILE;
		// end if;
		// RETURN V_RtnValue;
		// END
		//
		// 或运算
		// LANGUAGE SQL
		// BEGIN ATOMIC
		// DECLARE V_tmp1 INT;
		// DECLARE V_tmp2 INT;
		// DECLARE V_bitValue1 INT;
		// DECLARE V_bitValue2 INT;
		// DECLARE V_Idx INT;
		// DECLARE V_RtnValue INT;
		// if srcNum1=0 then
		// set V_RtnValue=srcNum2;
		// else
		// IF srcNum1 >= srcNum2 THEN
		// SET V_tmp1 = srcNum1;
		// SET V_tmp2 = srcNum2;
		// ELSE
		// SET V_tmp1 = srcNum2;
		// SET V_tmp2 = srcNum1;
		// END IF;
		// SET V_RtnValue = V_tmp1;
		// SET V_Idx = 0;
		// WHILE V_tmp2 > 0 DO
		// SET V_bitValue1 = MOD(V_tmp1,2);
		// IF V_bitValue1 = 0 THEN
		// SET V_bitValue2 = MOD(V_tmp2,2);
		// IF V_bitValue2 = 1 THEN
		// SET V_RtnValue = V_RtnValue + POWER(2,V_Idx);
		// END IF;
		// END IF;
		// SET V_tmp1 = V_tmp1 / 2;
		// SET V_tmp2 = V_tmp2 / 2;
		// SET V_Idx = V_Idx + 1;
		// END WHILE;
		// end if;
		// RETURN V_RtnValue;
		// END
		case DT_ORCL:
			if ("&".equals(oper)) {
				sb.append("bitand(").append(c1).append(",");
				sb.append(c2).append(")");
			} else if ("|".equals(oper)) {
				sb.append("bitor(").append(c1).append(",");
				sb.append(c2).append(")");
			}
			break;
		default:
			break;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String sql = "select c_aca from t_ac where c_aca='dd'";
		int pageindex = 3;
		int pagesize = 5;
		logger.info(splitSql(sql, pageindex, pagesize, DT_ORCL));
	}
}
