package com.dj.seal.util.service;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.table.AppSystemUtil;
import com.dj.seal.util.table.CertUtil;
import com.dj.seal.util.table.FeedLogSysUtil;
import com.dj.seal.util.table.LogSealWriteUtil;
import com.dj.seal.util.table.LogSysUtil;
import com.dj.seal.util.table.SysFuncUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.ViewMenuUtil;
import com.dj.seal.util.web.SearchForm;

public class SearchUtil {
	static Logger logger = LogManager.getLogger(SearchUtil.class.getName());
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	private static boolean chk2(String s) {
		return s != null && !"".equals(s) && !"0".equals(s);
	}

	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}

	public static String roleSch(SearchForm f) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(SysRoleUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		
		if (chk(f.getRole_tab())){
			sb.append(" and ").append(SysRoleUtil.ROLE_TAB);
			sb.append(" = ").append(f.getRole_tab());
		}
		if (chk(f.getRole_name())){
			sb.append(" and ").append(SysRoleUtil.ROLE_NAME);
			sb.append(" like '%").append(f.getRole_name());
			sb.append("%' ");
		}
		sb.append(" order by length(");
		sb.append(SysRoleUtil.ROLE_TAB).append("),");
		sb.append(SysRoleUtil.ROLE_TAB);
		return sb.toString();
	}
	
	public static String appSch(SearchForm f) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(AppSystemUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		
		if (chk(f.getApp_name())){
			sb.append(" and ").append(AppSystemUtil.APP_NAME);
			sb.append(" like  '%").append(f.getApp_name());
			sb.append("%'");
		}
		if (chk(f.getApp_no())){
			sb.append(" and ").append(AppSystemUtil.APP_NO);
			sb.append(" = '").append(f.getApp_no());
			sb.append("'");
		}
		sb.append(" order by ");
		sb.append(AppSystemUtil.CREATE_DATE);
		sb.append(" desc");
		return sb.toString();
	}


	public static String funcSch(SearchForm f) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.* from ");
		sb.append(SysFuncUtil.TABLE_NAME).append(" t1,");
		sb.append(ViewMenuUtil.TABLE_NAME).append(" t2 ");
		sb.append(" where 1=1 ");
		sb.append(" and t1.").append(SysFuncUtil.MENU_NO);
		sb.append("=t2.").append(ViewMenuUtil.MENU_NO);
		if (chk(f.getMenu_name())) {
			sb.append(" and t2.").append(ViewMenuUtil.MENU_NAME);
			sb.append(" like '%").append(f.getMenu_name());
			sb.append("%' ");
		}
		if (chk(f.getFunc_name())) {
			sb.append(" and t1.").append(SysFuncUtil.FUNC_NAME);
			sb.append(" like '%").append(f.getFunc_name());
			sb.append("%' ");
		}
		if (chk(f.getUser_num())) {
			// sb.append(" and ");
		}
		if (chk(f.getRole_num())) {

		}
		sb.append(" order by ");
		sb.append(SysFuncUtil.MENU_NO).append(",");
		sb.append(SysFuncUtil.FUNC_ID);
		return sb.toString();
	}
	// 搜索证书表信息
	public static String certSch(SearchForm f) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" order by " +CertUtil.REG_TIME+" desc");
	//	logger.info(sb.toString());
//		if (f.getCert_no()!=null) {
//			sb.append(" and " + CertUtil.CERT_NO + " like '%").append(
//					f.getCert_no() + "%'");
//		}
//		if (f.getCert_no().equals(null)) {
//			sb.append(" and " + CertUtil.CERT_NO + " like '%").append(
//					f.getCert_no() + "%'");
//		}
//		if ((f.getCert_name() != "") || (f.getCert_name() != null)) {
//			sb.append(" and " + CertUtil.CERT_NAME + " like '%").append(
//					f.getCert_name() + "%'");
//		}
//		if ((f.getCert_dn() != "") || (f.getCert_dn() != null)) {
//			sb.append(" and " + CertUtil.CERT_DN + " like '%").append(
//					f.getCert_dn() + "%'");
//		}
//		if ((f.getCert_src() != "") || (f.getCert_src() != null)) {
//			sb.append(" and " + CertUtil.CERT_SRC + " like '%").append(
//					f.getCert_src() + "%'");
//		}
//		if ((f.getCert_src() != "") || (f.getCert_src() != null)) {
//			sb.append(" and " + CertUtil.CERT_SRC + " like '%").append(
//					f.getCert_src() + "%'");
//		}
//		if ((f.getCert_detail() != "") || (f.getCert_detail() != null)) {
//			sb.append(" and " + CertUtil.CERT_DETAIL + " like '%").append(
//					f.getCert_detail() + "%'");
//		}
//		if ((f.getCert_type() != "") || (f.getCert_type() != null)) {
//			sb.append(" and " + CertUtil.CERT_TYPE + " like '%").append(
//					f.getCert_type() + "%'");
//		}
//		if ((f.getCert_status() != "") || (f.getCert_status() != null)) {
//			sb.append(" and " + CertUtil.CERT_STATUS + " like '%").append(
//					f.getCert_status() + "%'");
//		}
//		if ((f.getBegin_time() != "") || (f.getBegin_time() != null)) {
//			sb.append(" and " + CertUtil.BEGIN_TIME + " > ").append(
//					"to_date(" + f.getBegin_time()
//							+ ",'yyyy-mm-dd hh24:mi:ss')");
//		}
//		if ((f.getEnd_time() != "") || (f.getEnd_time() != null)) {
//			sb.append(" and " + CertUtil.BEGIN_TIME + " < ").append(
//					"to_date(" + f.getEnd_time() + ",'yyyy-mm-dd hh24:mi:ss')");
//		}
//		if ((f.getDept_no() != "") || (f.getDept_no() != null)) {
//			sb.append(" and " + CertUtil.DEPT_NO + " like '%").append(
//					f.getDept_no() + "%'");
//		}
//		if ((f.getReg_user() != "") || (f.getReg_user() != null)) {
//			sb.append(" and " + CertUtil.REG_USER + " like '%").append(
//					f.getReg_user() + "%'");
//		}
//		if ((f.getReg_time() != "") || (f.getReg_time() != null)) {
//			sb.append(" and " + CertUtil.BEGIN_TIME + " = ").append(
//					"to_date(" + f.getReg_time() + ",'yyyy-mm-dd hh24:mi:ss')");
//		}
		return sb.toString();
	}
	public static String logSch(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(LogSysUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		sb.append(" order by ");
		sb.append(LogSysUtil.OPR_TIME);
		sb.append(" desc");
		return sb.toString();
	}
	public static String feedLogSch(SearchForm f){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(FeedLogSysUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if(chk(f.getCreate_user())){
			sb.append(" and ").append(FeedLogSysUtil.USER_ID).append(" like '%").append(f.getCreate_user()).append("%'");
		}
		if(chk(f.getFeed_id())){
			sb.append(" and ").append(FeedLogSysUtil.FEED_ID).append(" like '%").append(f.getFeed_id()).append("%'");
		}
		if(chk(f.getBegin_time())&&chk(f.getEnd_time())){
			logger.info(DBTypeUtil.dateSqlByDBType(FeedLogSysUtil.OPR_TIME, f.getBegin_time(), f.getEnd_time()));
			sb.append(" and ").append(DBTypeUtil.dateSqlByDBType(FeedLogSysUtil.OPR_TIME, f.getBegin_time(),f.getEnd_time()));
		}
		sb.append(" order by ");
		sb.append(LogSysUtil.OPR_TIME);
		sb.append(" desc");
		return sb.toString();
	}
	public static String logSealSch(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(LogSealWriteUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		sb.append(" order by ");
		sb.append(LogSealWriteUtil.OPR_TIME);
		sb.append(" desc");
		return sb.toString();
	}

	public static String logSealServerSch(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(LogSealWriteUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		sb.append(" order by ");
		sb.append(LogSealWriteUtil.OPR_TIME);
		sb.append(" desc");
		return sb.toString();
	}
}
