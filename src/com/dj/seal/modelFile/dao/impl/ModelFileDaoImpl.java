package com.dj.seal.modelFile.dao.impl;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.modelFile.dao.api.ModelFileDao;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.modelFile.po.ModelXieyi;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.table.ModelFileUtil;
import com.dj.seal.util.table.ModelXieyiUtil;

public class ModelFileDaoImpl extends BaseDAOJDBC implements ModelFileDao {

	static Logger logger = LogManager.getLogger(ModelFileDaoImpl.class
			.getName());

	ModelFileUtil table = new ModelFileUtil();

	// 添加
	@Override
	public int addModelFile(ModelFile modelFile) throws Exception {
		int model_id = Integer.parseInt(getNo(ModelFileUtil.TABLE_NAME,
				ModelFileUtil.MODEL_ID));
		modelFile.setModel_id(model_id);
		if (modelFile.getCreate_time() == null) {
			Timestamp create_time = new Timestamp(new Date().getTime());
			modelFile.setCreate_time(create_time);
		}
		if (modelFile.getModify_time() == null) {
			Timestamp modify_time = new Timestamp(new Date().getTime());
			modelFile.setModify_time(modify_time);
		}
		if (modelFile.getApprove_time() == null) {
			Timestamp approve_time = new Timestamp(new Date().getTime());
			modelFile.setApprove_time(approve_time);
		}
		modelFile.setModel_state("1");// 默认模板可用
		logger.info("长度：" + modelFile.getContent_data().length());
		String sql = ConstructSql.composeInsertSql(modelFile, table);
		if (sql.indexOf(modelFile.getField_data()) > sql.indexOf(modelFile
				.getContent_data())) {
			String str1 = sql.substring(0,
					sql.indexOf(modelFile.getContent_data()) - 1);
			String str2 = sql.substring(
					sql.indexOf(modelFile.getContent_data())
							+ modelFile.getContent_data().length() + 1,
					sql.indexOf(modelFile.getField_data()) - 1);
			String str3 = sql.substring(sql.indexOf(modelFile.getField_data())
					+ modelFile.getField_data().length() + 1, sql.length());
			sql = str1 + "?" + str2 + "?" + str3;
			logger.info("--sql--" + sql);
			Connection conn = getDataSource().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Reader contentDataReader = new StringReader(
					modelFile.getContent_data()); // 将text转成流形式
			stmt.setCharacterStream(1, contentDataReader, modelFile
					.getContent_data().length());// 替换sql语句中的？
			Reader fieldDataReader = new StringReader(modelFile.getField_data()); // 将text转成流形式
			stmt.setCharacterStream(2, fieldDataReader, modelFile
					.getField_data().length());// 替换sql语句中的？
			stmt.executeUpdate();// 执行SQL
			stmt.close();
			conn.close();
		} else {
			String str1 = sql.substring(0,
					sql.indexOf(modelFile.getField_data()) - 1);
			String str2 = sql.substring(sql.indexOf(modelFile.getField_data())
					+ modelFile.getField_data().length() + 1,
					sql.indexOf(modelFile.getContent_data()) - 1);
			String str3 = sql.substring(
					sql.indexOf(modelFile.getContent_data())
							+ modelFile.getContent_data().length() + 1,
					sql.length());
			sql = str1 + "?" + str2 + "?" + str3;
			//logger.info("--sql--" + sql);
			Connection conn = getDataSource().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Reader contentDataReader = new StringReader(
					modelFile.getContent_data()); // 将text转成流形式
			stmt.setCharacterStream(2, contentDataReader, modelFile
					.getContent_data().length());// 替换sql语句中的？
			Reader fieldDataReader = new StringReader(modelFile.getField_data()); // 将text转成流形式
			stmt.setCharacterStream(1, fieldDataReader, modelFile
					.getField_data().length());// 替换sql语句中的？
			stmt.executeUpdate();// 执行SQL
			stmt.close();
			conn.close();
		}
		return model_id;
	}

	@Override
	public int addModelXieyi(ModelXieyi modelxieyi) {
		String sql = ConstructSql.composeInsertSql(modelxieyi,
				new ModelXieyiUtil());
		add(sql);
		return 0;
	}

	// 根据id查找模板
	@Override
	public ModelFile getModelFileById(Integer id) throws Exception {
		String sql = "select * from " + ModelFileUtil.TABLE_NAME + " where "
				+ ModelFileUtil.MODEL_ID + " = " + id;
		return modelFile(sql);
	}

	// 根据sql语句查找模板
	@Override
	public ModelFile getModelFileBySql(String sql) throws Exception {
		return modelFile(sql);
	}

	// 根据sql语句查找模板列表
	@Override
	public List<ModelFile> getModelFilesBySql(String sql) throws Exception {
		return modelFileList(sql);
	}

	// 更新
	@Override
	public void updateModelFile(ModelFile modelFile) throws Exception {
		Timestamp modify_time = new Timestamp(new Date().getTime());
		modelFile.setModify_time(modify_time);
		String pastr = ModelFileUtil.MODEL_ID + " = " + modelFile.getModel_id();
		String sql = ConstructSql.composeUpdateSql(modelFile, table, pastr);
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			if (sql.indexOf(modelFile.getField_data()) > sql.indexOf(modelFile
					.getContent_data())) {
				String str1 = sql.substring(0,
						sql.indexOf(modelFile.getContent_data()) - 1);
				String str2 = sql.substring(
						sql.indexOf(modelFile.getContent_data())
								+ modelFile.getContent_data().length() + 1,
						sql.indexOf(modelFile.getField_data()) - 1);
				String str3 = sql.substring(
						sql.indexOf(modelFile.getField_data())
								+ modelFile.getField_data().length() + 1,
						sql.length());
				sql = str1 + "?" + str2 + "?" + str3;
				logger.info("--sql--" + sql);
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader contentDataReader = new StringReader(
						modelFile.getContent_data()); // 将text转成流形式
				stmt.setCharacterStream(1, contentDataReader, modelFile
						.getContent_data().length());// 替换sql语句中的？
				Reader fieldDataReader = new StringReader(
						modelFile.getField_data()); // 将text转成流形式
				stmt.setCharacterStream(2, fieldDataReader, modelFile
						.getField_data().length());// 替换sql语句中的？
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} else {
				String str1 = sql.substring(0,
						sql.indexOf(modelFile.getField_data()) - 1);
				String str2 = sql.substring(
						sql.indexOf(modelFile.getField_data())
								+ modelFile.getField_data().length() + 1,
						sql.indexOf(modelFile.getContent_data()) - 1);
				String str3 = sql.substring(
						sql.indexOf(modelFile.getContent_data())
								+ modelFile.getContent_data().length() + 1,
						sql.length());
				sql = str1 + "?" + str2 + "?" + str3;
				logger.info("--sql--" + sql);
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader contentDataReader = new StringReader(
						modelFile.getContent_data()); // 将text转成流形式
				stmt.setCharacterStream(2, contentDataReader, modelFile
						.getContent_data().length());// 替换sql语句中的？
				Reader fieldDataReader = new StringReader(
						modelFile.getField_data()); // 将text转成流形式
				stmt.setCharacterStream(1, fieldDataReader, modelFile
						.getField_data().length());// 替换sql语句中的？
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			}
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
			if (sql.indexOf(modelFile.getField_data()) > sql.indexOf(modelFile
					.getContent_data())) {
				String str1 = sql.substring(0,
						sql.indexOf(modelFile.getContent_data()) - 1);
				String str2 = sql.substring(
						sql.indexOf(modelFile.getContent_data())
								+ modelFile.getContent_data().length() + 1,
						sql.indexOf(modelFile.getField_data()) - 1);
				String str3 = sql.substring(
						sql.indexOf(modelFile.getField_data())
								+ modelFile.getField_data().length() + 1,
						sql.length());
				sql = str1 + "?" + str2 + "?" + str3;
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader contentDataReader = new StringReader(
						modelFile.getContent_data()); // 将text转成流形式
				stmt.setCharacterStream(1, contentDataReader, modelFile
						.getContent_data().length());// 替换sql语句中的？
				Reader fieldDataReader = new StringReader(
						modelFile.getField_data()); // 将text转成流形式
				stmt.setCharacterStream(2, fieldDataReader, modelFile
						.getField_data().length());// 替换sql语句中的？
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} else {
				String str1 = sql.substring(0,
						sql.indexOf(modelFile.getField_data()) - 1);
				String str2 = sql.substring(
						sql.indexOf(modelFile.getField_data())
								+ modelFile.getField_data().length() + 1,
						sql.indexOf(modelFile.getContent_data()) - 1);
				String str3 = sql.substring(
						sql.indexOf(modelFile.getContent_data())
								+ modelFile.getContent_data().length() + 1,
						sql.length());
				sql = str1 + "?" + str2 + "?" + str3;
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader contentDataReader = new StringReader(
						modelFile.getContent_data()); // 将text转成流形式
				stmt.setCharacterStream(2, contentDataReader, modelFile
						.getContent_data().length());// 替换sql语句中的？
				Reader fieldDataReader = new StringReader(
						modelFile.getField_data()); // 将text转成流形式
				stmt.setCharacterStream(1, fieldDataReader, modelFile
						.getField_data().length());// 替换sql语句中的？
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			}
		} else {
			update(sql);
		}
	}

	// 删除模板文件
	@Override
	public void deleteModelFile(Integer id) {
		String sql = "delete from " + ModelFileUtil.TABLE_NAME + " where "
				+ ModelFileUtil.MODEL_ID + " = " + id;
		delete(sql);
	}

	// 注销模板文件
	@Override
	public void zhuxiaoModelFile(Integer id) {
		String sql = "update " + ModelFileUtil.TABLE_NAME + " set "
				+ ModelFileUtil.MODEL_STATE + "='2'" + " where "+ ModelFileUtil.MODEL_ID + " = " + id;
		update(sql);
	}

	// 激活模板文件
	@Override
	public void jihuoModelFile(Integer id) {
		String sql = "update " + ModelFileUtil.TABLE_NAME + " set "
				+ ModelFileUtil.MODEL_STATE + "='1'" + " where "
				+ ModelFileUtil.MODEL_ID + " = " + id;
		update(sql);
	}

	private ModelFile modelFile(String sql) throws Exception {
		ModelFile modelFile = new ModelFile();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			modelFile = (ModelFile) DaoUtil.setPo(modelFile, map, table);
			StringBuffer sb = new StringBuffer();
			sb.append("select ").append(ModelFileUtil.CONTENT_DATA);
			sb.append(" from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" where ").append(ModelFileUtil.MODEL_ID).append("=");
			sb.append(modelFile.getModel_id());
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				modelFile.setContent_data(getClobStr(sb.toString(),
						ModelFileUtil.CONTENT_DATA));
			}
			return modelFile;
		} else {
			return null;
		}
	}

	private List<ModelFile> modelFileList(String sql) throws Exception {
		List<ModelFile> modelFileList = new ArrayList<ModelFile>();
		List<Map> list = select(sql);
		for (Map map : list) {
			ModelFile modelFile = new ModelFile();
			modelFile = (ModelFile) DaoUtil.setPo(modelFile, map, table);
			StringBuffer sb = new StringBuffer();
			sb.append("select ").append(ModelFileUtil.CONTENT_DATA);
			sb.append(" from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" where ").append(ModelFileUtil.MODEL_ID).append("=");
			sb.append(modelFile.getModel_id());
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				modelFile.setContent_data(getClobStr(sb.toString(),
						ModelFileUtil.CONTENT_DATA));
			}
			modelFileList.add(modelFile);
		}
		return modelFileList;
	}

	@Override
	public List<ModelFile> getmodelFileList(String sql) {
		List<ModelFile> modelFileList = new ArrayList<ModelFile>();
		List<Map> list = select(sql);
		for (Map map : list) {
			ModelFile modelFile = new ModelFile();
			modelFile = (ModelFile) DaoUtil.setPo(modelFile, map, table);
			modelFileList.add(modelFile);
		}
		return modelFileList;
	}

	@Override
	public int showCount(String sql) throws Exception {
		return count(sql);
	}
	
	/**
	 * add by liuph
	 * 2017-12-22
	 */
	@Override
	public List<Map> selects(String sql){
		return select(sql);
	}

	@Override
	public void approveModel(ModelFile modelPO, String ID) {
		StringBuffer sb = new StringBuffer();
		String[] ids = ID.split(",");
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			sb = new StringBuffer(ModelFileUtil.MODEL_ID + " = '");
			sb.append(id).append("'");
			String sql = ConstructSql.composeUpdateSql(modelPO, table,
					sb.toString());
			logger.info("模板审批：" + sql);
			update(sql);
		}
	}

	/*@Override
	public int backupModel() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("Data_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "insert into " + ModelFileUtil.TABLE_NAME
				+ "_history select*from "+ModelFileUtil.TABLE_NAME+" where (to_date('" + nowTime
				+ "','yyyy-mm-dd hh24:mi:ss')-" + ModelFileUtil.MODIFY_TIME
				+ ")>" + saveTime;
		logger.info("备份模板数据sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}*/

	/*@Override
	public int delModel() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("Data_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "delete from " + ModelFileUtil.TABLE_NAME
				+ " where (to_date('" + nowTime + "','yyyy-mm-dd hh24:mi:ss')-"
				+ ModelFileUtil.MODIFY_TIME + ")>" + saveTime;
		logger.info("删除已备份模板sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}*/
}
