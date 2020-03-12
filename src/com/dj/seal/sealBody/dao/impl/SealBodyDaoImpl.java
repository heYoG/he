package com.dj.seal.sealBody.dao.impl;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.sealBody.dao.api.ISealBodyDao;
import com.dj.seal.structure.dao.po.RoleSeal;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SealBodyBak;
import com.dj.seal.structure.dao.po.SealUser;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.RoleSealUtil;
import com.dj.seal.util.table.SealBodyBakUtil;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.UserSealUtil;

public class SealBodyDaoImpl extends BaseDAOJDBC implements ISealBodyDao {

	static Logger logger = LogManager.getLogger(SealBodyDaoImpl.class.getName());

	private SealBodyUtil table = new SealBodyUtil();
	private UserSealUtil table1 = new UserSealUtil();
	private RoleSealUtil table2 = new RoleSealUtil();
	private ISysRoleDao role_dao;
	private ISysUserDao user_dao;

	public ISysRoleDao getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(ISysRoleDao role_dao) {
		this.role_dao = role_dao;
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}
	@Override
	public void deleteSeal(String sql)throws DAOException{
		delete(sql);
	}
	@Override
	public void addIsealBodyBak(SealBodyBak sealBody_bak) throws DAOException{
		String sql = ConstructSql.composeInsertSql(sealBody_bak, new SealBodyBakUtil());
		logger.info("base64:"+sealBody_bak.getSeal_base64());
		String str1 = sql.substring(0,
				sql.indexOf(sealBody_bak.getSeal_base64()) - 1);
		String str2 = sql.substring(sql.indexOf(sealBody_bak.getSeal_base64())
				+ sealBody_bak.getSeal_base64().length() + 1, sql.length());
//		logger.info("--str3--" + str3);
		sql = str1 + "?" + str2 ;
		logger.info("--sql--" + sql);
		try {
			Connection conn = getDataSource().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Reader clobReader = new StringReader(sealBody_bak.getSeal_base64()); // 将text转成流形式
			stmt.setCharacterStream(1, clobReader,sealBody_bak.getSeal_base64()
					.length());// 替换sql语句中的？
			stmt.executeUpdate();// 执行SQL
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
    }
	@Override
	public void addIsealBody(SealBody sealBody) throws DAOException {
		String sql = ConstructSql.composeInsertSql(sealBody, table);
		if(sql.indexOf(sealBody.getSeal_data())>sql.indexOf(sealBody.getPreview_img())){
			String str1 = sql.substring(0, sql.indexOf(sealBody
					.getPreview_img()) - 1);
			String str2 = sql.substring(sql.indexOf(sealBody.getPreview_img())
					+ sealBody.getPreview_img().length() + 1, sql
					.indexOf(sealBody.getSeal_data()) - 1);
			String str3 = sql.substring(sql.indexOf(sealBody.getSeal_data())
					+ sealBody.getSeal_data().length() + 1, sql.length());
			sql = str1 + "?" + str2 + "?" + str3;
			//logger.info("--sql--" + sql);
			try {
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader clobReader = new StringReader(sealBody.getSeal_data()); // 将text转成流形式
				Reader clobReaderImg = new StringReader(sealBody
						.getPreview_img());
				stmt.setCharacterStream(2, clobReader, sealBody.getSeal_data()
						.length());// 替换sql语句中的？
				stmt.setCharacterStream(1, clobReaderImg, sealBody
						.getPreview_img().length());
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}else{
			String str1 = sql.substring(0,
					sql.indexOf(sealBody.getSeal_data()) - 1);
			
//			logger.info("--str1--" + str1);
			String str2 = sql.substring(sql.indexOf(sealBody.getSeal_data())
					+ sealBody.getSeal_data().length() + 1, sql.indexOf(sealBody
					.getPreview_img()) - 1);
//			logger.info("--str2--" + str2);
			String str3 = sql.substring(sql.indexOf(sealBody.getPreview_img())
					+ sealBody.getPreview_img().length() + 1, sql.length());
//			logger.info("--str3--" + str3);
			sql = str1 + "?" + str2 + "?" + str3;
			logger.info("--sql--" + sql);
			try {
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader clobReader = new StringReader(sealBody.getSeal_data()); // 将text转成流形式
				Reader clobReaderImg = new StringReader(sealBody.getPreview_img());
				stmt.setCharacterStream(1, clobReader, sealBody.getSeal_data()
						.length());// 替换sql语句中的？
				stmt.setCharacterStream(2, clobReaderImg, sealBody.getPreview_img()
						.length());
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		
	}
//	public void addIsealBodyCZ(SealBody sealBody) throws DAOException {
//		String sql = ConstructSql.composeInsertSql(sealBody, table);
//		String str1 = sql.substring(0,
//				sql.indexOf(sealBody.getSeal_base64()) - 1);
//		logger.info("--sealdata--" + sql.indexOf(sealBody.getSeal_data()));
//		logger.info("--Preview_img--" + sql.indexOf(sealBody.getPreview_img()));
//		logger.info("--base64--" + sql.indexOf(sealBody.getSeal_base64()));
//		String str2 = sql.substring(sql.indexOf(sealBody.getSeal_base64())
//				+ sealBody.getSeal_base64().length() + 1, sql.indexOf(sealBody
//				.getSeal_data()) - 1);
//		//logger.info("--str2--" + str2);
//		String str3 = sql.substring(sql.indexOf(sealBody.getSeal_data())
//				+ sealBody.getSeal_data().length() + 1, sql.indexOf(sealBody.getPreview_img())-1);
//		//logger.info("--str3--" + str3);
//		String str4 = sql.substring(sql.indexOf(sealBody.getPreview_img())
//				+ sealBody.getPreview_img().length() + 1, sql.length());
//		//logger.info("--str4--" + str4);
//		sql = str1 + "?" + str2 + "?" + str3+ "?" + str4;
//		logger.info("--sql--" + sql);
//		try {
//			Connection conn = getDataSource().getConnection();
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			Reader clobReader = new StringReader(sealBody.getSeal_data()); // 将text转成流形式
//			Reader clobReaderImg = new StringReader(sealBody.getPreview_img());
//			Reader clobReaderImgCZ = new StringReader(sealBody.getSeal_base64());
//			stmt.setCharacterStream(1, clobReaderImgCZ, sealBody.getSeal_base64()
//					.length());//图片的base64值财政
//			stmt.setCharacterStream(2, clobReader, sealBody.getSeal_data()
//					.length());// 替换sql语句中的？
//			stmt.setCharacterStream(3, clobReaderImg, sealBody.getPreview_img()
//					.length());
//			
//			stmt.executeUpdate();// 执行SQL
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getMessage());
//		}
//	}
	@Override
	public void updateIsealBody(String seal_id,String sealData) throws DAOException {
		String  sql="update "+SealBodyUtil.TABLE_NAME+" set "+SealBodyUtil.SEAL_DATA+"=? where "+SealBodyUtil.SEAL_ID+"='"+seal_id+"'";	
		Connection conn=null;
		try {
			conn = getDataSource().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Reader clobReader = new StringReader(sealData); // 将text转成流形式	
			stmt.setCharacterStream(1, clobReader,sealData
					.length());// 替换sql语句中的？
			stmt.executeUpdate();// 执行SQL
			stmt.close();
//			conn.close();//modify on 20190304
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{//modify on 20190304
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public List<SealBody> showBodyList(String sql) throws DAOException {
		List<SealBody> list_sealbody = new ArrayList<SealBody>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SealBody sealbody = new SealBody();
			sealbody = (SealBody) DaoUtil.setPo(sealbody, map, table);
			list_sealbody.add(sealbody);
		}
		logger.info("list_sealbody:"+list_sealbody.size());
		return list_sealbody;
	}

	@Override
	public SealBody getSealBodys(Integer seal_id) throws DAOException {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_ID + " =" + seal_id;
		//logger.info("sql:"+sql);
		return seal(sql);
	}
	@Override
	public SealBody getSealBodyID(String seal_id) throws DAOException {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_ID + " ='"+seal_id+"'";
		//logger.info("sql:"+sql);
		return seal(sql);
	}
//	/**
//	 * 根据财政国库印章id查找印章信息
//	 * 
//	 * @param seal_id
//	 * @return
//	 * @throws DAOException
//	 */
//	public SealBody getSealBodysBYSealCZid(String seal_id) throws DAOException {
//		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
//				+ SealBodyUtil.SEAL_CZID+ " ='" + seal_id+"'";
//		return seal(sql);
//	}

	@SuppressWarnings("unchecked")
	private SealBody seal(String sql) {
		SealBody sealBody = new SealBody();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sealBody = (SealBody) DaoUtil.setPo(sealBody, map, table);
			return sealBody;
		} else {
			return null;
		}
	}

	@Override
	public void addUserSeal(String seal_id, String user_id) throws DAOException {
		String sql = "insert into " + UserSealUtil.TABLE_NAME + " values ('"
				+ seal_id + "','" + user_id + "')";
		//logger.info(sql);
		add(sql);
	}

	@Override
	public void addRoleSeal(String seal_id, String role_id) throws DAOException {
		String sql = "insert into " + RoleSealUtil.TABLE_NAME + " values ('"
				+ seal_id + "','" + role_id + "')";
		add(sql);
	}

	@Override
	public void deleteUserSeal(String seal_id) throws DAOException {
		String sql = "delete from " + UserSealUtil.TABLE_NAME + " where "
				+ UserSealUtil.SEAL_ID + "='" + seal_id + "'";
		delete(sql);
	}

	@Override
	public void deleteRoleSeal(String seal_id) throws DAOException {
		String sql = "delete from " + RoleSealUtil.TABLE_NAME + " where "
				+ RoleSealUtil.SEAL_ID + "='" + seal_id + "'";
		delete(sql);
	}

	@Override
	public List<SysUser> getUser(String sql) throws DAOException {
		List<Map> list = select(sql);// 调用父类方法
		List<SysUser> userlist = new ArrayList<SysUser>();
		if (list.size() > 0) {
			for (Map map : list) {
				SealUser sealuser = new SealUser();
				sealuser = (SealUser) DaoUtil.setPo(sealuser, map, table1);
				SysUser objUser = user_dao.showSysUserByUser_id(sealuser
						.getUser_id());
				userlist.add(objUser);
			}
		} else {
			return null;
		}
		return userlist;
	}

	@Override
	public List<SysRole> getRole(String sql) throws DAOException {
		List<Map> list = select(sql);// 调用父类方法
		List<SysRole> rolelist = new ArrayList<SysRole>();
		if (list.size() > 0) {
			for (Map map : list) {
				RoleSeal roleseal = new RoleSeal();
				roleseal = (RoleSeal) DaoUtil.setPo(roleseal, map, table2);
				SysRole objRole = role_dao.showSysRoleByRole_id(Integer
						.parseInt(roleseal.getRole_id()));
				rolelist.add(objRole);
			}
		} else {
			return null;
		}
		return rolelist;
	}

	@Override
	public void objbind(String seal_id, String cert_id) throws DAOException {
//		String sql="";
//			sql = "update " + SealBodyUtil.TABLE_NAME + " set "
//			+ SealBodyUtil.KEY_SN + " = '" + cert_id + "' where "
//			+ SealBodyUtil.SEAL_ID + " = '" + seal_id + "'";
//		}else if(type.equals("2")){//解除绑定证书
//			sql = "update " + SealBodyUtil.TABLE_NAME + " set "
//			+ SealBodyUtil.KEY_SN + " = '' where "
//			+ SealBodyUtil.SEAL_ID + " = '" + seal_id + "'";
//		}
//		logger.info("sql:"+sql);
//		update(sql);
	}
	@Override
	public SealBody getSealBodys(String seal_name) throws DAOException{
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
		+ SealBodyUtil.SEAL_NAME + " ='"+seal_name+"'";
       // logger.info("sql:"+sql);
        return seal(sql);
	}
	@Override
	public List<SealBody> showSealBodysByPageSplit(int pageIndex, int pageSize,
			String sql) throws DAOException {
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<SealBody> list = showBodyList(str);

		return list;
	}

	@Override
	public void updateSealBody(String sql) throws DAOException {
		update(sql);

	}

	@Override
	public boolean isExistSealName(String seal_name) throws DAOException {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.SEAL_NAME + "='" + seal_name + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String logOff(String seal_id) {
		String sql = "update "+SealBodyUtil.TABLE_NAME +" set "+SealBodyUtil.SEAL_STATUS+
			" = '"+Constants.SEAL_STATUS_DISABLE+"' where "+SealBodyUtil.SEAL_ID+" = '"+
			seal_id+"'";
		try {
			update(sql);
			return "1";
		} catch (Exception e) {
			return "0";
		}
		
	}
	
	@Override
	public String activate(String seal_id){
		String sql = "update "+SealBodyUtil.TABLE_NAME +" set "+SealBodyUtil.SEAL_STATUS+
			" = '"+Constants.IS_MAKED+"' where "+SealBodyUtil.SEAL_ID+" = '"+
			seal_id+"'";
		try {
			update(sql);
			return "1";
		} catch (Exception e) {
			return "0";
		}
		
	}
}
