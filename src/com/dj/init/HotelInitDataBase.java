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

public class HotelInitDataBase {
	private ApplicationContext ac = null;
	private ISysUnitService unit_srv = null;
	private BaseDAOJDBC dao = null;

	//无纸化管理系统所用的初始化数据代码
	
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
		initHotelDic();//无纸化通用字典
		initDJ_LIC();
		initCommit();
	}
	
	@Test
	public void initDJ_LIC() throws Exception{
		dao.add("INSERT INTO dj_lic VALUES ('1', '45yhfcvXPRocp/8nHtyuzz/hYFZXlzcfvbjD/09H7S/vAHGGagBapg==', 'BgcMAwUDBQMG', 'TEw=', '', '742164991', '742164991', 'kxAcI6hBZV1SiZBdRj0YS4qU2fWG', '9Fb45yhfXPRocp/8nHBFzzz/yhjkzYtkJD/09H7S9Rk5pP3N8acqPQ+bK8OqIs9j7uj5tMaMrVVZ6xTqYS9g==')");
		dao.add("INSERT INTO dj_lic VALUES ('2', '45yhfcvXPRocp/8nHtyuzz/hYFZXlzcfvbjD/09H7S/vAHGGagBapg==', 'BgcMAwUDBQMG', 'TEw=', '', '742164991', '742164991', 'kxAcI6hBZV1SiZBdRj0YS4qU2fWG', '9Fb45yhfXPRocp/8nHBFzzz/yhjkzYtkJD/09H7S9Rk5pP3N8acqPQ+bK8OqIs9j7uj5tMaMrVVZ6xTqYS9g==')");
		dao.add("INSERT INTO dj_lic VALUES ('3', '45yhfcvXPRocp/8nHtyuzz/hYFZXlzcfvbjD/09H7S/vAHGGagBapg==', 'BgcMAwUDBQMG', 'TEw=', '', '742164991', '742164991', 'kxAcI6hBZV1SiZBdRj0YS4qU2fWG', '9Fb45yhfXPRocp/8nHBFzzz/yhjkzYtkJD/09H7S9Rk5pP3N8acqPQ+bK8OqIs9j7uj5tMaMrVVZ6xTqYS9g==')");
		dao.add("INSERT INTO dj_lic VALUES ('4', '45yhfcvXPRocp/8nHtyuzz/hYFZXlzcfvbjD/09H7S/vAHGGagBapg==', 'BgcMAwUDBQMG', 'TEw=', '', '742164991', '742164991', 'kxAcI6hBZV1SiZBdRj0YS4qU2fWG', '9Fb45yhfXPRocp/8nHBFzzz/yhjkzYtkJD/09H7S9Rk5pP3N8acqPQ+bK8OqIs9j7uj5tMaMrVVZ6xTqYS9g==')");
		dao.add("INSERT INTO dj_lic VALUES ('5', '45yhfcvXPRocp/8nHtyuzz/hYFZXlzcfvbjD/09H7S/vAHGGagBapg==', 'BgcMAwUDBQMG', 'TEw=', '', '742164991', '742164991', 'kxAcI6hBZV1SiZBdRj0YS4qU2fWG', '9Fb45yhfXPRocp/8nHBFzzz/yhjkzYtkJD/09H7S9Rk5pP3N8acqPQ+bK8OqIs9j7uj5tMaMrVVZ6xTqYS9g==')");
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
		dao.add(addStr(new ViewMenu("8","08", "日志清理", "seal_log", "1","2"), objU));
		dao.add(addStr(new ViewMenu("1", "18", "组织机构管理", "@system", "1","1"),objU));
		dao.add(addStr(new ViewMenu("2", "17", "用户管理", "comm", "1","1"), objU));
		dao.add(addStr(new ViewMenu("5","05", "印模管理", "seal_orig", "1","1"), objU));
		dao.add(addStr(new ViewMenu("6","07", "印章管理", "seal", "2","1"), objU));
		dao.add(addStr(new ViewMenu("9","09", "日志管理", "seal_log", "1","1"), objU));
		dao.add(addStr(new ViewMenu("12","13", "规则管理", "sys", "1","1"), objU));
		dao.add(addStr(new ViewMenu("7","19", "证书管理", "sys", "1","1"), objU));
		dao.add(addStr(new ViewMenu("3", "16", "系统管理", "system", "1","1"), objU));
//无纸化管理模块
		dao.add(addStr(new ViewMenu("3","20", "无纸化管理", "sys", "1","1"), objU));
		dao.add(addStr(new ViewMenu("4","21", "单据管理", "sys", "1","1"), objU));
		dao.add(addStr(new ViewMenu("8","22", "扫描单据管理", "sys", "1","1"), objU));
		dao.add(addStr(new ViewMenu("10","23", "集群管理", "sys", "1","1"), objU));
	}

	@Test
	public void initFunc() throws Exception {
		SysFuncUtil objU = new SysFuncUtil();
		dao.add(addStr(new SysFunc(18, "08", 0x20000, "日志清理", "1", "seal_log",
				"/Seal/general/log/clean_data/dataClear.jsp"), objU));
				dao.add(addStr(new SysFunc(19, "16", 0x40000, "数据备份", "1", "system",
				"../backupManage.do"), objU));
		//基础模块
		dao.add(addStr(new SysFunc(2, "18", 0x2, "部门管理", "1", "system",
				"../manageDept.do"), objU));
		dao.add(addStr(new SysFunc(3, "18", 0x4, "角色管理", "1", "system",
				"/Seal/general/organise/role_mng/index.jsp"), objU));
		dao.add(addStr(new SysFunc(4, "18", 0x8, "权限查询", "1", "system",
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
		dao.add(addStr(new SysFunc(11, "07", 0x400, "印章制作", "1",
				"seal", "../tempManage.do"), objU));
		dao.add(addStr(new SysFunc(12, "05", 0x800, "我的印模", "1", "seal_orig",
				"../mytempReg.do"), objU));
		dao.add(addStr(new SysFunc(14, "07", 0x1000, "印章查询", "1", "seal",
				"../tempAppselect.do"), objU));
		dao.add(addStr(new SysFunc(15, "07", 0x2000, "印章管理", "1", "seal",
				"../sealManage.do"), objU));
		
		dao.add(addStr(new SysFunc(16, "07", 0x4000, "可用印章", "1", "seal",
				"../tempAppuse.do?type=flag"), objU));
		dao.add(addStr(new SysFunc(23, "09", 0x8000, "系统操作日志", "1", "seal_log",
		"/Seal/general/log/log_oper/log_list.jsp"), objU));
//		dao.add(addStr(new SysFunc(28, "09", 0x10000, "章使用日志", "1", "seal_log",
//		"/Seal/general/log/seal_oper/seal_operLog.jsp"), objU));
//服务器模块
//		dao.add(addStr(new SysFunc(30, "13", 0x20000, "规则管理", "1", "sys",
//		"/Seal/general/gaizhangRule/rule_mng/index.jsp"), objU));
//无纸化管理模块
		//dao.add(addStr(new SysFunc(33, "20", 0x40000, "模板管理", "1", "sys",
		//"/Seal/general/hotel/masterplate/newmodel.jsp"), objU));
		dao.add(addStr(new SysFunc(31, "20", 0x40000, "模板申请", "2", "sys",
		"/Seal/general/hotel/masterplate/newmodel.jsp"), objU));
		dao.add(addStr(new SysFunc(32, "20", 0x80000, "模板审批", "2", "sys",
		"/Seal/general/hotel/masterplate/index.jsp"), objU));
		dao.add(addStr(new SysFunc(33, "20", 0x100000, "模板管理", "2", "sys",
		"/Seal/general/hotel/masterplate/showModel.jsp"), objU));
		dao.add(addStr(new SysFunc(34, "20", 0x200000, "协议管理", "1", "sys",
		"/Seal/general/hotel/masterplate/newxieyi.jsp"), objU));
		dao.add(addStr(new SysFunc(35, "20", 0x400000, "判定条件管理", "1", "sys",
		"/Seal/general/hotel/judge/index.jsp"), objU));
		dao.add(addStr(new SysFunc(36, "20", 0x800000, "通用字典管理", "1", "sys",
		"/Seal/general/hotel/dictionary/index.jsp"), objU));
		
		
		dao.add(addStr(new SysFunc(37, "21", 0x2, "单据管理", "2", "sys",
		"/Seal/general/hotel/record/index.jsp"), objU));
		
		dao.add(addStr(new SysFunc(38, "21", 0x4, "我的单据", "2", "sys",
		"/Seal/general/hotel/record/myrecord.jsp"), objU));
		dao.add(addStr(new SysFunc(39, "09", 0x8, "客户端操作日志", "2", "seal_log",
		"/Seal/general/hotel/log/log_list.jsp"), objU));
		
		dao.add(addStr(new SysFunc(40, "23", 0x10, "集群管理", "2", "sys",
				"/Seal/general/frontend/index.html"), objU));
		
//		dao.add(addStr(new SysFunc(41, "20", 0x20, "规则模板管理", "2", "sys",
//		"/Seal/general/modelFileRule/index.jsp"), objU));
		dao.add(addStr(new SysFunc(42, "20", 0x40, "广告申请", "2", "sys",
		"/Seal/general/hotel/advert/index.jsp"), objU));

		dao.add(addStr(new SysFunc(43, "20", 0x80, "广告审批", "2", "sys",
		"/Seal/general/hotel/advert/approve_list.jsp"), objU));
		dao.add(addStr(new SysFunc(44, "20", 0x100, "广告管理", "2", "sys",
		"/Seal/general/hotel/advert/ad_list.jsp"), objU));

		dao.add(addStr(new SysFunc(45, "21", 0x200, "单据稽核", "2", "sys",
		"/Seal/general/hotel/record/jiheindex.jsp"), objU));
//		dao.add(addStr(new SysFunc(46, "21", 0x400, "二级稽核", "2", "sys",
//		"/Seal/general/hotel/record/index.jsp"), objU));
//		dao.add(addStr(new SysFunc(47, "21", 0x800, "三级稽核", "2", "sys",
//		"/Seal/general/hotel/record/index.jsp"), objU));
//		dao.add(addStr(new SysFunc(48, "21", 0x1000, "四级稽核", "2", "sys",
//		"/Seal/general/hotel/record/index.jsp"), objU));
		dao.add(addStr(new SysFunc(49, "21", 0x2000, "统计图表", "2", "sys",
		"/Seal/general/highcharts/index.html"), objU));
		dao.add(addStr(new SysFunc(50, "21", 0x4000, "本人单据统计", "2", "sys",
				"/Seal/general/hotel/record/tongji1.jsp"), objU));
		dao.add(addStr(new SysFunc(51, "21", 0x8000, "本厅单据统计", "2", "sys",
		"/Seal/general/hotel/record/tongji2.jsp"), objU));
		dao.add(addStr(new SysFunc(52, "21", 0x10000, "市分单据统计", "2", "sys",
		"/Seal/general/hotel/record/tongji3.jsp"), objU));
		dao.add(addStr(new SysFunc(53, "22", 0x10, "扫描单据", "2", "sys",
		"/Seal/general/hotel/scanner/scannerFile.jsp"), objU));
		dao.add(addStr(new SysFunc(54, "22", 0x20, "扫描单据管理", "2", "sys",
		"/Seal/general/hotel/scanner/scanner_list.jsp"), objU));
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
		dao.add("INSERT INTO T_FB VALUES ('无纸化管理平台','无纸化管理平台欢迎您','无纸化管理平台','宋体','','',100,50,'','','1',0,0,'1','1','')");
	}

	@Test
	public void initRole() throws Exception {
		dao.add("insert into T_AD values(1,'0000','0','系统管理员',1073741823,1073741823,0,0,0,'1')");
	}

	@Test
	public void initUser() throws Exception {

		// oracle
//		dao.add("insert into t_ac values('1','admin','系统管理员','0','123456','','','','','1',to_date('1980-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),to_date('2009-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),'北京','100000','1234567','13800138000','1','dj@gmail.com','0000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin',to_date('2009-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),'1','1','111111','111111')");
//		dao.add("insert into t_ac values('2','logger','日志审计员','0','123456','','','','','1',to_date('1980-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),to_date('2009-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),'北京','100000','1234567','13800138000','1','dj@gmail.com','0000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin',to_date('2009-01-01 10:10:10','yyyy-mm-dd hh24:mi:ss'),'1','1','111111','111111')");
		 //mysql
		dao.add("insert into T_AC values('1','admin','系统管理员','0','','','','','','1','1980-01-01 10:10:10','2009-01-01 00:00:00','北京','100000','1234567','13800138000','1','dj@gmail.com','0000','1234567','1',1073741823,1073741823,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin','2009-01-01 10:10:10','1','1','111111','111111')");
		dao.add("insert into T_AC values('2','logger','日志审计员','0','','','','','','1','1980-01-01 10:10:10','2009-01-01 00:00:00','北京','100000','1234567','13800138000','1','dj@gmail.com','0000','1234567','1',1073741823,2359295,0,0,0,'2','','','1','1','0.0.0.0','','1','1','1','1','1','192.168.1.23','admin','2009-01-01 10:10:10','1','1','111111','111111')");

	}
	@Test
	public void initUserRole() throws Exception {
		dao.add("insert into T_AF values('admin',1,'1')");
	}
	
	@Test
	public void initBackup() throws Exception {
		dao.add("insert into T_BU values(0,'','','','')");
	}

	@Test
	public void initHotelDic() throws Exception {
		dao.add("INSERT INTO T_HotelTDicHotel VALUES ('1', 'guestname', '客户名称', '文本', '1', '0', '1')");
		dao.add("INSERT INTO T_HotelTDicHotel VALUES ('2', 'cardNo', '身份证号', '文本', '1', '1', '0')");
		dao.add("INSERT INTO T_HotelTDicHotel VALUES ('3', 'shouliNo', '受理编号', '文本', '1', '1', '0')");
		dao.add("INSERT INTO T_HotelTDicHotel VALUES ('4', 'phoneNo', '手机号码', '文本', '1', '1', '0')");
	}
	
	@Test
	public void initTemp() throws Exception {

	}

	@Test
	public void initSeal() throws Exception {

	}
}
