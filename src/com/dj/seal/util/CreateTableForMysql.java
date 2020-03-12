package com.dj.seal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.dj.seal.util.table.AppSystemUtil;
import com.dj.seal.util.table.CertUtil;
import com.dj.seal.util.table.DocPrintRoleUserUtil;
import com.dj.seal.util.table.DocmentBodyUtil;
import com.dj.seal.util.table.GaiZhangRuleUtil;
import com.dj.seal.util.table.HotelAdvertUtil;
import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelScannerRecordUtil;
import com.dj.seal.util.table.HotelTClientOperLogUtil;
import com.dj.seal.util.table.HotelTDicHotelUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;
import com.dj.seal.util.table.HotelTmasterplateJudgeUtil;
import com.dj.seal.util.table.HotelTmasterplateTRoleUtil;
import com.dj.seal.util.table.HotelTmasterplateUtil;
import com.dj.seal.util.table.HotelTversionUtil;
import com.dj.seal.util.table.LogSealWriteUtil;
import com.dj.seal.util.table.LogSysUtil;
import com.dj.seal.util.table.ModelDeptUtil;
import com.dj.seal.util.table.ModelFileRuleUtil;
import com.dj.seal.util.table.ModelFileUtil;
import com.dj.seal.util.table.ModelXieyiUtil;
import com.dj.seal.util.table.RoleRuleUtil;
import com.dj.seal.util.table.RoleSealUtil;
import com.dj.seal.util.table.SealBodyBakUtil;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SealLogUtil;
import com.dj.seal.util.table.SealTemplateUtil;
import com.dj.seal.util.table.ServerSealLogUtil;
import com.dj.seal.util.table.SysBackupUtil;
import com.dj.seal.util.table.SysDepartmentUtil;
import com.dj.seal.util.table.SysFuncUtil;
import com.dj.seal.util.table.SysRoleFuncUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.SysUnitUtil;
import com.dj.seal.util.table.SysUserDeptUtil;
import com.dj.seal.util.table.SysUserRoleUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.table.UserCertUtil;
import com.dj.seal.util.table.UserSealUtil;
import com.dj.seal.util.table.ViewInterfaceUtil;
import com.dj.seal.util.table.ViewMenuUtil;
import com.dj.seal.util.table.ViewTableModuleUtil;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.neusoft.util.table.HotelRecordMergeUtil;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;
import com.neusoft.util.table.TableMonthConfigUtil;

/**
 * 
 * @description 为Mysql数据库中建表
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class CreateTableForMysql {

	private static String dbdriver = "com.mysql.jdbc.Driver";
	private static String dburl = "jdbc:mysql://127.0.0.1:3306/xedseal?useUnicode=true&characterEncoding=UTF-8";

	private static String dbuser = "root";
	private static String dbpsd = "123456";


	/**
	 * @description 建表
	 * @return
	 */
	public int createTable() {
		Connection conn = null;
		Statement stm = null;
		try {
			conn = getConn(dburl, dbuser, dbpsd);
			stm = conn.createStatement();
			stm.addBatch(SysUnitUtil.createSql()); //单位表 
			stm.addBatch(SysDepartmentUtil.createSql()); //部门表
			stm.addBatch(SysUserUtil.createSql()); //用户表
			stm.addBatch(SysRoleUtil.createSql()); //角色表
			stm.addBatch(SysFuncUtil.createSql()); //权限表
			stm.addBatch(SysUserRoleUtil.createSql()); //用户角色表
			stm.addBatch(SysRoleFuncUtil.createSql()); //角色权限表
			stm.addBatch(SysUserDeptUtil.createSql()); //用户部门表
			stm.addBatch(SealTemplateUtil.createSql()); //印模表
			stm.addBatch(SealBodyUtil.createSql()); //印章表
			stm.addBatch(SealBodyBakUtil.createSql()); //印章备份表
			stm.addBatch(RoleSealUtil.createSql()); //角色印章表
			stm.addBatch(UserSealUtil.createSql()); //用户印章表
			stm.addBatch(LogSysUtil.createSql()); //系统日志表
			stm.addBatch(ViewMenuUtil.createSql()); // 菜单表
			stm.addBatch(ViewInterfaceUtil.createSql()); // 界面表
			stm.addBatch(ViewTableModuleUtil.createSql()); // 桌面模板表
			stm.addBatch(SysBackupUtil.createSql());//数据备份（author：lzl）
			stm.addBatch(UserCertUtil.createSql());//用户证书关联表	
			stm.addBatch(DocmentBodyUtil.createSql());//19文档表
			stm.addBatch(CertUtil.createSql());//20证书表
			stm.addBatch(DocPrintRoleUserUtil.createSql());//23打印设置表
			stm.addBatch(LogSealWriteUtil.createSql());//26印章写入key表
			stm.addBatch(SealLogUtil.createSql());//27	客户端盖章、打印日志表
			
			//服务器盖章模块
			stm.addBatch(RoleRuleUtil.createSql());// 17角色规则表
			stm.addBatch(AppSystemUtil.createSql4MySql());//22应用系统表	
			stm.addBatch(ModelFileUtil.createSql());//24	模板表
			stm.addBatch(GaiZhangRuleUtil.createSql());//25	盖章规则表
			stm.addBatch(ServerSealLogUtil.createSql());//28	服务端盖章日志表
			
			//Hotel模版
			stm.addBatch(HotelTClientOperLogUtil.createSql());//客户端操作日志表
			stm.addBatch(HotelTDicHotelUtil.createSql());//数据字典表
			stm.addBatch(HotelTmasterplateJudgeUtil.createSql());//模版判定表
			stm.addBatch(HotelTmasterplateUtil.createSql());//模版管理表
			stm.addBatch(HotelRecordUtil.createSql4MySql());
			stm.addBatch(HotelTmasterplateTRoleUtil.createSql4MySql());
			stm.addBatch(HotelTRecordAffiliatedUtil.createSql4MySql());
			stm.addBatch(HotelTversionUtil.createSql4MySql());
			stm.addBatch(HotelAdvertUtil.createSql());//广告管理
			stm.addBatch(ModelDeptUtil.createSql());//
			stm.addBatch(ModelXieyiUtil.createSql());//
			stm.addBatch(HotelRecordContent.createSql4MySql());//模板文字
			String sql = "CREATE TABLE dj_lic (lic_id int(11) DEFAULT NULL,licNO varchar(255) DEFAULT NULL,ip varchar(255) DEFAULT NULL,lic_user varchar(255) DEFAULT NULL,mac varchar(255) DEFAULT NULL,create_time int(11) DEFAULT NULL,update_time int(11) DEFAULT NULL,token varchar(255) DEFAULT NULL,lic varchar(255) DEFAULT NULL);";
			stm.addBatch(sql);
			
			//模板规则表
			stm.addBatch(ModelFileRuleUtil.createSql4MySql());//创建模板规则表
			
			// added by chenjuheng on 2015-7-6 14:32:32
			stm.addBatch(HotelScannerRecordUtil.createSql4MySql());//扫描单据
			
			// 单据合并表
			stm.addBatch(HotelRecordMergeUtil.createSql4MySql());
			
			// 房间单据合并表
			stm.addBatch(HotelRecordRoomMergeUtil.createSql4MySql());
			
			// 月表名配置表
			stm.addBatch(TableMonthConfigUtil.createSql4MySql());
			
			stm.executeBatch();

			System.out.println("建表成功！");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStm(stm);
			closeConn(conn);
		}
		return 1;
	}

	/**
	 * @description 删表
	 * @return
	 */
	public int dropTable() {
		Connection conn = null;
		Statement stm = null;
		try {
			conn = getConn(dburl, dbuser, dbpsd);
			stm = conn.createStatement();
			stm.execute(SysUnitUtil.dropSql()); // 单位表
			stm.execute(SysDepartmentUtil.dropSql()); // 部门表
			stm.execute(SysUserUtil.dropSql()); // 用户表
			stm.execute(SysRoleUtil.dropSql()); // 角色表
			stm.execute(SysFuncUtil.dropSql()); // 权限表
			stm.execute(SysUserRoleUtil.dropSql()); //用户角色表
			stm.execute(SysRoleFuncUtil.dropSql()); // 角色权限表
			stm.execute(SysUserDeptUtil.dropSql()); // 用户部门表
			stm.execute(SealTemplateUtil.dropSql()); // 印模表
			stm.execute(SealBodyUtil.dropSql()); // 印章表
			stm.execute(SealBodyBakUtil.dropSql()); //印章备份表
			stm.execute(LogSysUtil.dropSql()); // 系统日志表
			stm.execute(ViewMenuUtil.dropSql()); // 菜单表
			stm.execute(ViewInterfaceUtil.dropSql()); // 界面表
			stm.execute(ViewTableModuleUtil.dropSql()); //桌面模板表
			stm.execute(RoleSealUtil.dropSql());//角色印章表 
			stm.execute(UserSealUtil.dropSql()); //用户印章表
			stm.execute(SysBackupUtil.dropSql());//数据备份（author：lzl）
			stm.execute(DocmentBodyUtil.dropSql());//19文档表
			stm.execute(CertUtil.dropSql());//20证书表
			stm.execute(UserCertUtil.dropSql());//21用户证书关联表	
			stm.execute(DocPrintRoleUserUtil.dropSql());//23打印设置表
			stm.execute(LogSealWriteUtil.dropSql());//26印章写入key表
			stm.execute(SealLogUtil.dropSql());//27	客户端盖章、打印日志表
			
			//服务器盖章模块
			stm.execute(RoleRuleUtil.dropSql());// 17角色规则表
			stm.execute(AppSystemUtil.dropSql());//22应用系统表	
			stm.execute(ModelFileUtil.dropSql());//24	模板表
			stm.execute(GaiZhangRuleUtil.dropSql());//25	盖章规则表
			stm.execute(ServerSealLogUtil.dropSql());//28	服务端盖章日志表
			
			//Hotel模版
			stm.execute(HotelTClientOperLogUtil.dropSql());//客户端操作日志表
			stm.execute(HotelTDicHotelUtil.dropSql());//数据字典表
			stm.execute(HotelTmasterplateJudgeUtil.dropSql());//模版判定表
			stm.execute(HotelTmasterplateUtil.dropSql());//模版管理表
			stm.execute(HotelRecordUtil.dropSql());
			stm.execute(HotelTmasterplateTRoleUtil.dropSql());
			stm.execute(HotelTRecordAffiliatedUtil.dropSql());
			stm.execute(HotelTversionUtil.dropSql());
			stm.execute(HotelAdvertUtil.dropSql());//广告管理
			stm.execute(ModelDeptUtil.dropSql());//
			stm.execute(ModelXieyiUtil.dropSql());//
			//模板规则管理
			stm.execute(ModelFileRuleUtil.dropSql());
			stm.execute(HotelRecordContent.dropSql());
			
			System.out.println("删表成功！");
		} catch (MySQLSyntaxErrorException e) {
			System.out.println("删除了一个不存在的表。" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStm(stm);
			closeConn(conn);
		}
		return 1;
	}

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		CreateTableForMysql ctfm = new CreateTableForMysql();
        System.out.println(ctfm.getConn(dburl, dbuser, dbpsd).getCatalog());
//		ctfm.dropTable();
		ctfm.createTable();

	}

	/**
	 * @description 获得数据库连接对象
	 * @param username
	 * @param psd
	 * @return
	 */
	public Connection getConn(String url, String username, String psd) {
		try {
			Class.forName(dbdriver);
			Connection conn = DriverManager.getConnection(url, username, psd);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得数据库连接对象
	 * 
	 * @return
	 */
	public static Connection getConn() {
		try {
			Class.forName(dbdriver);
			Connection conn = DriverManager.getConnection(dburl, dbuser, dbpsd);
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 关闭数据库连接
	 * @param conn
	 */
	public void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 关闭数据库操作对象
	 * @param conn
	 */
	public void closeStm(Statement stm) {
		try {
			if (stm != null) {
				stm.close();
				stm = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
