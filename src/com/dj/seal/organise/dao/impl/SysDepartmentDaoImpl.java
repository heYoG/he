package com.dj.seal.organise.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.DeptUtil;
import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.SysUserDept;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SealTemplateUtil;
import com.dj.seal.util.table.SysDepartmentUtil;
import com.dj.seal.util.table.SysUnitUtil;
import com.dj.seal.util.table.SysUserDeptUtil;
import com.dj.seal.util.table.SysUserUtil;

public  class SysDepartmentDaoImpl extends BaseDAOJDBC implements
		ISysDepartmentDao {
	static Logger logger = LogManager.getLogger(SysDepartmentDaoImpl.class.getName());
	private SysDepartmentUtil table = new SysDepartmentUtil();
	private SysUserDeptUtil table1=new SysUserDeptUtil();
	private SysUnitUtil table2=new SysUnitUtil();
	
	@Override
	@SuppressWarnings("unchecked")
	public SysDepartment showSysDepartmentByPriv_no(String priv_no)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.PRIV_NO + "='" + priv_no + "'";
		return department(sql);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public  List<SysDepartment> listDepartment(String sql) {
		List<SysDepartment> list_dept = new ArrayList<SysDepartment>();
		List<Map> list = select(sql);
		for (Map map : list) {
//			SysDepartment dept = new SysDepartment();
//			dept = (SysDepartment) DaoUtil.setPo(dept, map, table);
//			list_dept.add(dept);
			SysDepartment dept = new SysDepartment();
			dept = (SysDepartment) DaoUtil.setPo(dept, map, table);
//			logger.info(dept.getPriv_no());
			dept.setTree_id(DeptUtil.change(dept.getPriv_no()));
//			logger.info(dept.getTree_id());
			String p_no=dept.getDept_parent().substring(dept.getDept_parent().length()-4);
			dept.setP_tree_id(DeptUtil.change(p_no));
//			logger.info(dept.getP_tree_id());
			list_dept.add(dept);
		}
		return list_dept;
	}
	@Override
	@SuppressWarnings("unchecked")
	public SysDepartment showSysDepartmentByDept_no(String dept_no)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_NO + "='" +dept_no + "'";
		//logger.info("根据部门编号查询信息sql："+sql);
		return department(sql);
	}
	
	@Override
	public SysDepartment showSysDepartmentByBank_no(String bank_no)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.BANK_DEPT + "='" +bank_no + "'";
		return department(sql);
	}
	
	@Override
	public SysDepartment showSysDepartmentByDept_noSimple(String dept_no)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_NO + "='" +dept_no + "'";
		return departmentSimple(sql);
	}
	
	@Override
	public String getDepartName(String dept_no)
	       throws DAOException {
		String sql ="";
		if(dept_no.equals(Constants.UNIT_DEPT_NO)){
			sql = "select * from " + SysUnitUtil.TABLE_NAME
				+ " where " + SysUnitUtil.DEPT_NO + "='" +dept_no + "'";
			List<Map> list=select(sql);
			Map map = list.get(0);
			SysUnit sysunit=new SysUnit();
			sysunit= (SysUnit) DaoUtil.setPo(sysunit, map,
					table2);
			return sysunit.getUnit_name();
		}else{
			sql = "select * from " + SysDepartmentUtil.TABLE_NAME
			+ " where " + SysDepartmentUtil.DEPT_NO + "='" +dept_no + "'";
			//logger.info("bad::"+sql);
			List<Map> list=select(sql);
			Map map = list.get(0);
			SysDepartment sysdepart=new SysDepartment();
			sysdepart= (SysDepartment) DaoUtil.setPo(sysdepart, map,
					table);
			return sysdepart.getDept_name();
		}
		
	}
	//判断部门下是否存在子部门
	@Override
	public boolean serSysDepartment(String deptno){
		String sql="select * from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.DEPT_PARENT+"='"+deptno+"'";
		List list_dept = select(sql);// 调用父类方法
		if(list_dept.size()>0){
			return true;
		}else{
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	private SysDepartment department(String sql) {
		SysDepartment sysDepartment = new SysDepartment();
		List list_dept = select(sql);// 调用父类方法
		if (list_dept.size() != 0) {			
			Map map = (Map) list_dept.get(0);		
			sysDepartment = (SysDepartment) DaoUtil.setPo(sysDepartment, map,
					table);
			boolean fla=serSysDepartment(sysDepartment.getDept_no());//部门编号作为父部门编号查询判断是否有子部门
			if(fla){
				sysDepartment.setIs_detpflow("1");//有下级
			}else{
				sysDepartment.setIs_detpflow("2");//无下级
			}
			boolean fla1=serSysUser(sysDepartment.getDept_no());
			if(fla1){
				sysDepartment.setIs_deptuser("1");//有用户
			}else{
				sysDepartment.setIs_deptuser("2");//无用户
			}
			boolean fla2=serSysTemp(sysDepartment.getDept_no());
			if(fla2){
				sysDepartment.setIs_depttemp("1");//有印模
			}else{
				sysDepartment.setIs_depttemp("2");//无印模
			}
			boolean fla3=serSysSeal(sysDepartment.getDept_no());
			if(fla3){
				sysDepartment.setIs_deptseal("1");//有印章
			}else{
				sysDepartment.setIs_deptseal("2");//无印章
			}
			return sysDepartment;
		} else {
			return null;
		}
	}
	
	private SysDepartment departmentSimple(String sql) {
		SysDepartment sysDepartment = new SysDepartment();
		List list_dept = select(sql);// 调用父类方法
		if (list_dept.size() != 0) {
			Map map = (Map) list_dept.get(0);		
			sysDepartment = (SysDepartment) DaoUtil.setPo(sysDepartment, map,
					table);
			return sysDepartment;
		} else {
			return null;
		}
	}
	
	//判断部门下是否有用户
	public boolean serSysUser(String deptno){
		//用1存在问题打开后登录出错
	    String sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.DEPT_NO+"='"+deptno+"'";
	    //String sql="select * from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.DEPT_NO+"='"+deptno+"'";
		List list_user=select(sql);
		if(list_user.size()>0){
			return true;
		}else{
			return false;
		}
	}
	//判断部门下是否有印模
	public boolean serSysTemp(String deptno){
		String sql="select * from "+SealTemplateUtil.TABLE_NAME+" where "+SealTemplateUtil.DEPT_NO+"='"+deptno+"'";
		List list_temp=select(sql);
		if(list_temp.size()>0){
			return true;
		}else{
			return false;
		}
	}
	//判断部门下是否有印章
	public boolean serSysSeal(String deptno){
		String sql="select * from "+SealBodyUtil.TABLE_NAME+" where "+SealBodyUtil.DEPT_NO+"='"+deptno+"'";
		List list_seal=select(sql);
		if(list_seal.size()>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public List<SysDepartment> showSysDepartmentsByUser_id(String user_id)
			throws DAOException {
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysDepartmentUtil.TABLE_NAME + ","
				+ SysUserDeptUtil.TABLE_NAME + " where "
				+ SysDepartmentUtil.DEPT_NO + "=" + SysUserDeptUtil.DEPT_NO
				+ " and " + SysUserDeptUtil.USER_ID + "='" + user_id
				+ "' order by " + SysDepartmentUtil.DEPT_LEVER + ","
				+ SysDepartmentUtil.DEPT_TAB;
		if (Constants.USER_NAME_ADMIN.equals(user_id)
				|| Constants.USER_NAME_LOGGER.equals(user_id)) {
			return showSubSysDepartmentsByDept_no(Constants.UNIT_DEPT_NO);

		}
		return listDepartment(sql);
    }
	@Override
	@SuppressWarnings("unchecked")
	public List<SysDepartment> showSubSysDepartmentsByDept_no(String dept_no)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_PARENT + " like '%"
				+ dept_no + "%' order by " + SysDepartmentUtil.DEPT_LEVER + ","
				+ SysDepartmentUtil.DEPT_TAB;
		return listDepartment(sql);
	}
	@Override
	public void addSysDepartment(SysDepartment sysDepartment)
			throws DAOException {
		String sql = ConstructSql.composeInsertSql(sysDepartment, table);
		//logger.info("addSql:"+sql);
		add(sql);
	}
	@Override
	public void deleteSysDepartment(SysDepartment sysDepartment)
			throws DAOException {
		String sql = "delete from " + SysDepartmentUtil.TABLE_NAME + " where "
		+ SysDepartmentUtil.DEPT_NO + "='" + sysDepartment.getDept_no()
		+ "'";
        delete(sql);
	}
	@Override
	public void deleteSysDepartmentBySql(String sql) throws DAOException {
		delete(sql);
	}
	@Override
	public boolean isExistDept(String dept_name) throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_NAME + "='" + dept_name
				+ "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public boolean isExistDept(String dept_name, String old_name)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_NAME + "='" + dept_name
				+ "' and " + SysDepartmentUtil.DEPT_NAME + "<>'" + old_name
				+ "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean isSysDepartmentExist(String dept_name) throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
				+ " where " + SysDepartmentUtil.DEPT_NAME + "='" + dept_name
				+ "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public String listSysUserDept(String sql) {
		List<SysUserDept> list_userdept = new ArrayList<SysUserDept>();
		List<Map> list = select(sql);
		String dept_no="";
		for (Map map : list) {
			SysUserDept userdept = new SysUserDept();
			userdept = (SysUserDept) DaoUtil.setPo(userdept, map, table1);
			dept_no+=userdept.getDept_no()+",";
		}
		return dept_no;
	}
	@Override
	public String manageRangeOfUser(String user_id) throws DAOException {
		StringBuffer sb = new StringBuffer();
		List<SysDepartment> depts = showSysDepartmentsByUser_id(user_id);
		List<SysDepartment> zong = new ArrayList<SysDepartment>();

		for (SysDepartment dept : depts) {
			zong.add(dept);
			List<SysDepartment> list = showSubSysDepartmentsByDept_no(dept
					.getDept_no());
			zong = DaoUtil.bingJi(zong, list);
		}
		// 如果是‘admin’或者‘logger’，则返回所有的管理范围
		if (Constants.USER_NAME_ADMIN.equals(user_id)
				|| Constants.USER_NAME_LOGGER.equals(user_id)) {
			zong = showSubSysDepartmentsByDept_no(Constants.UNIT_DEPT_NO);
			SysDepartment dept = new SysDepartment();
			dept.setDept_no(Constants.UNIT_DEPT_NO);
			zong.add(dept);
		}
		for (SysDepartment dept : zong) {
			sb.append("'").append(dept.getDept_no()).append("',");
		}
		if (!"".equals(sb.toString())) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		} else {
			sb.append("'00'");
		}
		return sb.toString();
	}
	@Override
	public String newPriv_no() throws DAOException {
		String priv_no = "0001";// 默认为“0001”
		// 查询数据库获得目前最大的编号
		String sql = "select max(" + SysDepartmentUtil.PRIV_NO + ") "
				+ SysDepartmentUtil.PRIV_NO + " from "
				+ SysDepartmentUtil.TABLE_NAME;
		List list = select(sql);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){ //mysql5得到的map没有key值 map为 {=最大值} key为null
				Set<Entry<Object, Object>>  entrySet = map.entrySet();
				Object value = null;			 
				for (Entry object : entrySet) {
					 if(object.getValue()!=null){
					 priv_no=object.getValue().toString();
					 }
				}
			}else{
				priv_no = (String) map.get(SysDepartmentUtil.PRIV_NO);
			}	
			if (priv_no == null || "".equals(priv_no)) {
				String temp = Constants.UNIT_DEPT_NO;
				char ch = temp.charAt(temp.length() - 1);
				return temp.substring(0, temp.length() - 1) + (++ch);
			}else{
				return nextNo4(priv_no);
			}
		}
		return priv_no;
	}

	//生成4位的部门编号
	public static String nextNo4(String no){
		String key=Constants.DEPT_KEY_STR;
		if(no == null || "".equals(no)){
			char[] cs = new char[4];
			cs[0] = cs[1] = cs[2] = cs[3] = key.charAt(0);
			return new String(cs);
		}else{
			int[] is = new int[key.length()];
			for (int i = 0; i < is.length; i++) {
				is[i] = key.charAt(i);
			}
			char[] cs = new char[4];
			cs = no.toCharArray();
			int p4 = key.indexOf(cs[3]);
			int p3 = key.indexOf(cs[2]);
			int p2 = key.indexOf(cs[1]);
			int p1 = key.indexOf(cs[0]);
			if (p4 + 1 < key.length()) {
				cs[3] = key.charAt(p4 + 1);
			}else if (p3 + 1 < key.length()) {
				cs[3] = key.charAt(0);
				cs[2] = key.charAt(p3 + 1);
			}else if(p2 + 1 < key.length()){
				cs[3] = key.charAt(0);
				cs[2] = key.charAt(0);
				cs[1] = key.charAt(p2 + 1);
			}else if(p1 + 1 < key.length()){
				cs[3] = key.charAt(0);
				cs[2] = key.charAt(0);
				cs[1] = key.charAt(0);
				cs[0] = key.charAt(p1 + 1);
			}else{
				logger.info("部门编号已超出数量");
			}
			return new String(cs);
		}
	}
	
	
	@Override
	public List<SysDepartment> showNextLeverDepts(SysDepartment sysDepartment)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_LEVER + "="
		+ sysDepartment.getDept_lever() + "+1 and "
		+ SysDepartmentUtil.DEPT_NO + " like '"
		+ sysDepartment.getDept_no() + "%' order by "
		+ SysDepartmentUtil.DEPT_LEVER + ","
		+ SysDepartmentUtil.DEPT_TAB;
        return listDepartment(sql);
	}
	@Override
	public List<SysDepartment> showNextLeverDepts() throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_LEVER + "='1'";
        return listDepartment(sql);
	}
	@Override
	public List<SysDepartment> showSameLeverDepts(SysDepartment sysDepartment)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_PARENT + "='"
		+ sysDepartment.getDept_parent() + "'";
        return listDepartment(sql);
	}
	@Override
	public SysDepartment showSysDepartmentByDept_name(String dept_name)
			throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_NAME + "='" + dept_name
		+ "'";
       return department(sql);
	}
	
	@Override
	public SysDepartment showSysDepartmentByDept_nameSimple(String dept_name)
		throws DAOException {
		String sql = "select * from " + SysDepartmentUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_NAME + "='" + dept_name
		+ "'";
		return departmentSimple(sql);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SysDepartment showSysDepartmentBySql(String sql) throws DAOException {
		return department(sql);
	}
	@Override
	@SuppressWarnings("unchecked")
	public SysDepartment showSysDepartmentByUser_id(String user_id)
			throws DAOException {
		String sql = "select " + DaoUtil.allFields(table) + " from "
		+ SysDepartmentUtil.TABLE_NAME + "," + SysUserUtil.TABLE_NAME
		+ " where " + SysDepartmentUtil.DEPT_NO + "="
		+ SysUserUtil.DEPT_NO + " and " + SysUserUtil.USER_ID + "='"
		+ user_id + "'";
		//logger.info("sql--"+sql);
        return department(sql);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<SysDepartment> showSysDepartmentsByDoc_no(String doc_no)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SysDepartment> showSysDepartmentsBySql(String sql)
			throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateSysDepartment(SysDepartment sysDepartment)
			throws DAOException {
			
		String parasStr = SysDepartmentUtil.BANK_DEPT + "='"
				+ sysDepartment.getBank_dept() + "'";
		String sql = ConstructSql.composeUpdateSql(sysDepartment, table,
				parasStr);
		//logger.info("updateSql:"+sql);
		update(sql);
	}
	@Override
	public void updateSysDepartment(SysDepartment sysDepartment, String old_no)
			throws DAOException {
		String parasStr = SysDepartmentUtil.DEPT_NO + "='" + old_no + "'";
		String sql = ConstructSql.composeUpdateSql(sysDepartment, table,
				parasStr);
		update(sql);
		
	}

	@Override
	public void updateSysDepartmentBySql(String sql) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getIsnull(String id) {
		String sql="select count(*) from "+SysDepartmentUtil.TABLE_NAME+" where "+SysDepartmentUtil.BANK_DEPT+" ='"+id+"'";
		return count(sql);
	}

	@Override
	public SysDepartment getDeptByBankId(String bankId) {
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysDepartmentUtil.TABLE_NAME +" where "+SysDepartmentUtil.BANK_DEPT +" ='"+bankId+"'";
		//logger.info("getDeptByBankIdsql--"+sql);
		        return department(sql);
	}

	@Override
	public SysDepartment getParentNo(String orgUnit) {
		String sql="select C_ABA,C_ABG from "+ SysDepartmentUtil.TABLE_NAME +" where "+SysDepartmentUtil.BANK_DEPT +" ='"+orgUnit+"'";
		//logger.info("orgUnitsql:"+sql);
		return getParentNo_assist(sql);
	}
	
	/**
	 * by hyg20171227
	 * @param sql 根据sql查询
	 * @return 部门对象
	 */
	private SysDepartment getParentNo_assist(String sql) {
		SysDepartment sysDepartment = new SysDepartment();
		List list_dept = select(sql);// 调用父类方法
		if (list_dept.size() != 0) {
			Map map = (Map) list_dept.get(0);		
			sysDepartment = (SysDepartment) DaoUtil.setPo(sysDepartment, map,
					table);
			return sysDepartment;
		} else {
			return null;
		}
	}

	@Override
	public SysDepartment getDeptNo(String parentNo) {
		String sql="select C_ABG,C_HXDEPTID,C_ABC,C_ABA from "+ SysDepartmentUtil.TABLE_NAME +" where "+SysDepartmentUtil.DEPT_NO +" ='"+parentNo+"'";
		//logger.info("deptSql:"+sql);
		return getParentNo_assist(sql);
	}
}
