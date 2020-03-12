package com.dj.seal.log.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.log.dao.impl.FeedLogSysDaoImpl;
import com.dj.seal.log.service.api.IFeedLogSysService;
import com.dj.seal.organise.dao.impl.SysRoleDaoImpl;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.FeedLogSys;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.table.FeedLogSysUtil;
import com.dj.seal.util.web.SearchForm;

public class FeedLogSysServiceImpl implements IFeedLogSysService {
	
	static Logger logger = LogManager.getLogger(FeedLogSysServiceImpl.class.getName());

	private FeedLogSysDaoImpl flog_dao;
	private SysRoleDaoImpl role_dao;
	private ISealBodyService seal_body;
	
	
	public ISealBodyService getSeal_body() {
		return seal_body;
	}
	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}
	public SysRoleDaoImpl getRole_dao() {
		return role_dao;
	}
	public void setRole_dao(SysRoleDaoImpl role_dao) {
		this.role_dao = role_dao;
	}

	public FeedLogSysDaoImpl getFlog_dao() {
		return flog_dao;
	}
	public void setFlog_dao(FeedLogSysDaoImpl flog_dao) {
		this.flog_dao = flog_dao;
	}
	@Override
	public void logAdd(FeedLogSys logsys) throws GeneralException {
        flog_dao.addFeedLogSys(logsys);
	}
	@Override
	public PageSplit logSel(int pageIndex, int pageSize, SearchForm f)throws GeneralException {
		String str = SearchUtil.feedLogSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<FeedLogSys> datas = flog_dao.logSel(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	public void deleteFeedLog(){
		int savetime=Integer.parseInt(DJPropertyUtil.getPropertyValue("feedLog_saveTime").toString().trim());
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowTime = new Date(System.currentTimeMillis());
		String nowTimeStr = sdfFileName.format(nowTime);
		String sql="delete from "+FeedLogSysUtil.TABLE_NAME+" where (to_date('"+nowTimeStr+"','yyyy-mm-dd hh24:mi:ss')-"+FeedLogSysUtil.OPR_TIME+")>"+savetime;
		logger.info("删除评价日志sql:"+sql);
		flog_dao.delete(sql);
	}
	
	public void cleanData(String syslog,String seallog,String server_log,String client_log)throws GeneralException{
		   String sql="delete from "+FeedLogSysUtil.TABLE_NAME;
		   flog_dao.delete(sql);
	}
}
