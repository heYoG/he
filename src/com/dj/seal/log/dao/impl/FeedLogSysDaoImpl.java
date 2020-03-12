package com.dj.seal.log.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.log.dao.api.IFeedLogSysDao;
import com.dj.seal.structure.dao.po.FeedLogSys;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.LogSealWriteUtil;
import com.dj.seal.util.table.FeedLogSysUtil;

public class FeedLogSysDaoImpl  extends BaseDAOJDBC implements IFeedLogSysDao {
	
	static Logger logger = LogManager.getLogger(FeedLogSysDaoImpl.class.getName());

	/**
	 * 新增评价日志
	 */
	@Override
	public void addFeedLogSys(FeedLogSys FeedLogSys) throws DAOException {
		try {
				String log_id = getNo(FeedLogSysUtil.TABLE_NAME, FeedLogSysUtil.LOG_ID);
				FeedLogSys.setLog_id(Integer.parseInt(log_id));
				String sql=ConstructSql.composeInsertSql(FeedLogSys, new FeedLogSysUtil());
				add(sql);			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	public void delFeedLogSys(String seal_id) throws DAOException {
		try {
		   String sql="delete  from "+LogSealWriteUtil.TABLE_NAME+" where "+LogSealWriteUtil.SEAL_ID+"='"+seal_id+"'";
		   delete(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
	}
	@Override
	public List<FeedLogSys> logSel(String sql)throws DAOException{
		List<FeedLogSys> list_log = new ArrayList<FeedLogSys>();
		List<Map> list = select(sql);
		for (Map map : list) {
			FeedLogSys FeedLogSys = new FeedLogSys();
			FeedLogSys = (FeedLogSys) DaoUtil.setPo(FeedLogSys, map, new FeedLogSysUtil());
			list_log.add(FeedLogSys);
		}
		return list_log;
	}
}
