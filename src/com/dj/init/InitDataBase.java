package com.dj.init;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.impl.SysUnitService;
import com.dj.seal.structure.dao.po.SysFunc;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.table.SysFuncUtil;
import com.dj.seal.util.table.ViewMenuUtil;
import com.dj.seal.util.table.ViewTableModuleUtil;

public class InitDataBase {
	private ApplicationContext ac = null;
	private ISysUnitService unit_srv = null;
	private BaseDAOJDBC dao = null;

	@Before
	public void init() {
		ac = new ClassPathXmlApplicationContext(new String[]{"ApplicationContext-config.xml","ApplicationContext-dao.xml","ApplicationContext-service.xml","/com/dj/seal/gaizhangRule/ApplicationContext-gaizhangRule.xml","/com/dj/seal/modelFile/ApplicationContext-modelFile.xml","/com/dj/seal/hotel/ApplicationContext-hotel.xml"});
		unit_srv = (SysUnitService) ac.getBean("ISysUnitService");
		dao = (BaseDAOJDBC) ac.getBean("IBaseDao");
	}

	@After
	public void destory() {
		System.out.println("恭喜～～测试成功!");
		ac = null;
	}

	@Test
	public void initAll() throws Exception {
		initDB();// 数据库
		initMenu();// 菜单
		initFunc();// 权限
		initDeskTop();// 桌面模块
		initUnit();// 单位
		initInterFace();// 界面
		initRole();// 角色
		initUser();// 用户
		initUserRole();// 用户角色
		initBackup();//数据备份
		initCommit();
	}

	@Test
	public void initDB() throws Exception {

	}

	@Test
	public void initCommit() throws Exception {
		dao.update("commit");
	}

	private String addStr(Object obj, Object objUtil) {
		return ConstructSql.composeInsertSql(obj, objUtil);
	}

	@Test
	public void initMenu() throws Exception {
		ViewMenuUtil objU = new ViewMenuUtil();
//基础模块
		dao.add(addStr(new ViewMenu("1", "18", "组织机构管理", "@system", "1","1"),objU));
		dao.add(addStr(new ViewMenu("2", "17", "用户管理", "comm", "1","1"), objU));
		dao.add(addStr(new ViewMenu("3", "16", "系统管理", "system", "1","1"), objU));
		dao.add(addStr(new ViewMenu("5","05", "印模管理", "seal_orig", "1","1"), objU));
		dao.add(addStr(new ViewMenu("6","07", "印章管理", "seal", "2","1"), objU));
		dao.add(addStr(new ViewMenu("8","08", "日志清理", "seal_log", "1","2"), objU));
		dao.add(addStr(new ViewMenu("9","09", "日志管理", "seal_log", "1","1"), objU));
		dao.add(addStr(new ViewMenu("10","04", "文档打印设置", "print", "1","1"), objU));
//服务器签章模块
		dao.add(addStr(new ViewMenu("11","12", "应用系统管理", "product", "1","1"), objU));
		dao.add(addStr(new ViewMenu("12","13", "模板规则管理", "work_stat", "1","1"), objU));
		dao.add(addStr(new ViewMenu("7","19", "证书管理", "seal_model", "1","1"), objU));
//无纸化管理模块
//		dao.add(addStr(new ViewMenu("13","20", "无纸化管理", "sys", "1","1"), objU));
	}

	@Test
	public void initFunc() throws Exception {
		SysFuncUtil objU = new SysFuncUtil();
		//基础模块
		dao.add(addStr(new SysFunc(2, "18", 0x2, "部门管理", "1", "@system",
				"../manageDept.do"), objU));
		dao.add(addStr(new SysFunc(3, "18", 0x4, "角色管理", "1", "@system",
				"/Seal/general/organise/role_mng/index.jsp"), objU));
		dao.add(addStr(new SysFunc(4, "18", 0x8, "权限查询", "1", "@system",
				"/Seal/general/organise/func_query/index.jsp"), objU));
		dao.add(addStr(new SysFunc(5, "17", 0x10, "新增用户", "1", "comm",
				"../frameuser.do"), objU));
		dao.add(addStr(new SysFunc(6, "17", 0x20, "用户查询", "1", "comm",
				"../serUser.do"), objU));
		dao.add(addStr(new SysFunc(7, "17", 0x40, "用户管理", "1", "comm",
                "../serUserManager.do"), objU));
		dao.add(addStr(new SysFunc(8, "05", 0x80, "印模申请", "1", "seal_orig",
		"../tempRegGuide.do"), objU));
		dao.add(addStr(new SysFunc(9, "05", 0x100, "印模审批", "1", "seal_orig",
				"../tempAppGuide.do"), objU));
		dao.add(addStr(new SysFunc(10, "05", 0x200, "印模查询", "1", "seal_orig",
				"../searchTempGuide.do"), objU));
		dao.add(addStr(new SysFunc(11, "07", 0x400, "印章制作", "2",
				"seal", "../tempManage.do"), objU));
		dao.add(addStr(new SysFunc(12, "05", 0x800, "我的印模", "2", "seal_orig",
				"../mytempReg.do"), objU));
		dao.add(addStr(new SysFunc(14, "07", 0x2000, "印章查询", "2", "seal",
				"../tempAppselect.do"), objU));
		dao.add(addStr(new SysFunc(15, "07", 0x4000, "印章管理", "2", "seal",
				"../sealManage.do"), objU));
		
		dao.add(addStr(new SysFunc(16, "07", 0x8000, "可用印章", "1", "seal",
				"../tempAppuse.do?type=flag"), objU));
		dao.add(addStr(new SysFunc(18, "08", 0x20000, "日志清理", "1", "seal_log",
		"/Seal/general/log/clean_data/dataClear.jsp"), objU));
		dao.add(addStr(new SysFunc(19, "16", 0x40000, "数据备份", "1", "system",
		"../backupManage.do"), objU));
		dao.add(addStr(new SysFunc(23, "09", 0x1000, "系统操作日志", "1", "seal_log",
		"/Seal/general/log/log_oper/log_list.jsp"), objU));
		dao.add(addStr(new SysFunc(24, "09", 0x400000, "印章写入key日志", "1", "seal_log",
		"/Seal/general/log/log_sealwrite/sealwritelog_list.jsp"), objU));
		dao.add(addStr(new SysFunc(26, "04", 0x10000, "文档打印设置", "1", "print",
		"/Seal/general/docPrint/manage/query/query.jsp"), objU));
		dao.add(addStr(new SysFunc(28, "09", 0x10000000, "章使用日志", "1", "seal_log",
		"/Seal/general/log/seal_oper/seal_operLog.jsp"), objU));
//服务器模块

		dao.add(addStr(new SysFunc(20, "19", 0x80000, "证书登记", "1", "seal_model",
		"/Seal/general/cert/cert_reg/index.jsp"), objU));
		dao.add(addStr(new SysFunc(21, "16", 0x100000, "授权信息查询", "1", "system",
				"/Seal/general/license/license.jsp"), objU));
		dao.add(addStr(new SysFunc(22, "19", 0x200000, "证书管理", "1", "seal_model",
				"/Seal/general/cert/cert_mng/cert_list.jsp"), objU));
		dao.add(addStr(new SysFunc(25, "09", 0x1000000, "服务端盖章日志", "1", "seal_log",
		"/Seal/general/log/log_sealserver/sealserverlog_list.jsp"), objU));
		dao.add(addStr(new SysFunc(27, "12", 0x2000000, "应用系统管理", "1", "product",
		"/Seal/general/appSystem/index.jsp"), objU));
		dao.add(addStr(new SysFunc(29, "13", 0x4000000, "模板管理", "1", "work_stat",
		"/Seal/general/model_file/model_mng/index.jsp"), objU));
		dao.add(addStr(new SysFunc(30, "13", 0x8000000, "规则管理", "1", "work_stat",
		"/Seal/general/gaizhangRule/rule_mng/index.jsp"), objU));
		dao.add(addStr(new SysFunc(32, "16", 0x20000000, "文件备份配置", "1", "system",
		"/Seal/general/bakupFile/index.jsp"), objU));
	//	dao.add(addStr(new SysFunc(33, "07", 0x8000, "导入印章数据", "2", "seal",
	//	"../importSeal.do?type=0"), objU));		
//无纸化管理模块

		dao.add(addStr(new SysFunc(34, "20", 0x10000, "无纸化模板管理", "2", "sys",
		"/Seal/general/hotel/masterplate/index.jsp"), objU));
		dao.add(addStr(new SysFunc(35, "20", 0x20000, "判定条件管理", "2", "sys",
		"/Seal/general/hotel/judge/index.jsp"), objU));
		dao.add(addStr(new SysFunc(36, "20", 0x40000, "通用字典管理", "2", "sys",
		"/Seal/general/hotel/dictionary/index.jsp"), objU));
		
		dao.add(addStr(new SysFunc(37, "20", 0x80000, "单据管理", "2", "sys",
		"/Seal/general/hotel/record/index.jsp"), objU));
		
		dao.add(addStr(new SysFunc(38, "20", 0x100000, "我的单据", "2", "sys",
		"/Seal/general/hotel/record/myrecord.jsp"), objU));
		dao.add(addStr(new SysFunc(39, "09", 0x200000, "无纸化操作日志", "2", "seal_log",
		"/Seal/general/hotel/log/index.jsp"), objU));
		
		
		dao.add(addStr(new SysFunc(40, "20", 0x400000, "规则模板管理", "2", "work_stat",
		"/Seal/general/modelFileRule/index.jsp"), objU));
		
	}

	@Test
	public void initDeskTop() throws Exception {
		ViewTableModuleUtil objU = new ViewTableModuleUtil();
		dao.add(addStr(new ViewTableModule("1", "待批印模", "sealTempApp.jsp",
				"../tempAppGuide.do", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("2", "待批印章", "sealApp.jsp",
				"../sealApprove.do", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("3", "我的印模申请", "myTempReg.jsp",
				"../tempRegGuide.do", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("4", "印章列表", "sealTempApp.jsp",
				"../sealManage.do", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("5", "待审批用户", "sealTempApp.jsp",
				"../userApprove.do", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("6", "我的印章使用申请", "sealTempApp.jsp",
				"url", "0", 5), objU));
		dao.add(addStr(new ViewTableModule("7", "待批印章使用申请", "sealTempApp.jsp",
				"url", "0", 5), objU));
	}

	/**
	 * 初始化单位信息
	 * 
	 * @throws Exception
	 */
	@Test
	public void initUnit() throws Exception {
		SysUnit sysUnit = new SysUnit();
		sysUnit.setUnit_name("XXX公司");
		unit_srv.addSysUnit(sysUnit);
	}

	@Test
	public void initInterFace() throws Exception {
		dao.add("INSERT INTO t_fb VALUES ('电子印章平台','电子印章平台欢迎您','电子印章平台','宋体','','',100,50,'','','1',0,0,'1','1','')");
	}

	@Test
	public void initRole() throws Exception {
		dao.add("insert into t_ad values(1,'000','0','系统管理员',1073741823,1073741823,0,0,0,'1')");
	}

	@Test
	public void initUser() throws Exception {

		// oracle

//		dao.add("insert into t_ac values('1','admin','系统管理员','0','123456','','','','','1',to_date('1980-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),to_date('2009-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),'北京','100000','1234567','13800138000','1','dj@gmail.com','000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin',to_date('2009-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),'1','1','111111','111111')");
//		dao.add("insert into t_ac values('2','logger','日志审计员','0','123456','','','','','1',to_date('1980-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),to_date('2009-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),'北京','100000','1234567','13800138000','1','dj@gmail.com','000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin',to_date('2009-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),'1','1','111111','111111')");
		 //mysql
		dao.add("insert into t_ac values('1','admin','系统管理员','0','','','','','','1','1980-01-01 10:10:10','2009-01-01 00:00:00','北京','100000','1234567','13800138000','1','dj@gmail.com','000','1234567','1',1073741823,1073741823,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin','2009-01-01 10:10:10','1','1','111111','111111')");
		dao.add("insert into t_ac values('2','logger','日志审计员','0','','','','','','1','1980-01-01 10:10:10','2009-01-01 00:00:00','北京','100000','1234567','13800138000','1','dj@gmail.com','000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin','2009-01-01 10:10:10','1','1','111111','111111')");

	}
	@Test
	public void initUserRole() throws Exception {
		dao.add("insert into t_af values('admin',1,'1')");
	}
	
	@Test
	public void initBackup() throws Exception {
		dao.add("insert into T_BU values(0,'','','','')");
	}

	@Test
	public void initTemp() throws Exception {

	}

	@Test
	public void initSeal() throws Exception {

	}

}
