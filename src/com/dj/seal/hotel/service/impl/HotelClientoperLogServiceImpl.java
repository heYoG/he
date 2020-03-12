package com.dj.seal.hotel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.HotelTClientOperLogDaoImpl;
import com.dj.seal.hotel.form.HotelTClientOperLogsForm;
import com.dj.seal.hotel.po.HotelTClientOperLogPO;
import com.dj.seal.hotel.service.api.IHotelClientOperLogService;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTClientOperLogUtil;
import com.dj.seal.util.web.SearchForm;

public class HotelClientoperLogServiceImpl implements
		IHotelClientOperLogService {
	
	static Logger logger = LogManager.getLogger(HotelClientoperLogServiceImpl.class.getName());

	private HotelTClientOperLogDaoImpl clientOperLogDao;

	public HotelTClientOperLogDaoImpl getClientOperLogDao() {
		return clientOperLogDao;
	}

	public void setClientOperLogDao(HotelTClientOperLogDaoImpl clientOperLogDao) {
		this.clientOperLogDao = clientOperLogDao;
	}

	@Override
	public int addClientOperLog(HotelTClientOperLogPO clientOperLog) {
		try {
			clientOperLogDao.addTClientOperLog(clientOperLog);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	//添加日志   月表
	public int addClientOperLogNew(HotelTClientOperLogPO clientOperLog) {
		try {
			clientOperLogDao.addTClientOperLogNew(clientOperLog);
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	
	@Override
	public int addClientOperLogWithForm(
			HotelTClientOperLogsForm clientOperLogForm) throws Exception {

		HotelTClientOperLogPO obj = new HotelTClientOperLogPO();

		BeanUtils.copyProperties(obj, clientOperLogForm);

		return addClientOperLog(obj);
	}

	@Override
	public HotelTClientOperLogPO getClientOperLogByCid(String cid)
			throws DAOException {

		return clientOperLogDao.getClientOperLogById(cid);
	}

	@Override
	public List<HotelTClientOperLogPO> showAllHotelTClientOperLog()
			throws Exception {
		String sql = "";
		return clientOperLogDao.showAllClientOperLogsBySql(sql);
	}

	@Override
	public List<HotelTClientOperLogPO> showHotelClientOperLogByCid(String cid)
			throws DAOException {

		String sql = "select * from " + HotelTClientOperLogUtil.getTABLE_NAME()
				+ " where " + HotelTClientOperLogUtil.getCID() + " in(" + cid
				+ ")";
		return clientOperLogDao.showAllClientOperLogsBySql(sql);
	}

	public PageSplit showLogBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<HotelTClientOperLogPO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = clientOperLogDao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	private void addWhereLianTong(StringBuffer sb,SearchForm f,String yuestr){
		
		//操作人
		if(chk(f.getOperuserid())){
			sb.append(" and x.").append(HotelTClientOperLogUtil.OPERUSERID);
			sb.append(" like '%").append(f.getOperuserid()).append("%' ");
		}
		if(chk(f.getCip())){
			sb.append(" and x.").append(HotelTClientOperLogUtil.CIP);
			sb.append(" ='").append(f.getCip()).append("' ");
		}
		
		if(chk(f.getOpertype())){
			sb.append(" and x.").append(HotelTClientOperLogUtil.OPERTYPE);
			sb.append(" ='").append(f.getOpertype()).append("' ");
		}
		if(chk(f.getObjname())){
			sb.append(" and x.").append(HotelTClientOperLogUtil.OBJNAME);
			sb.append(" like '%").append(f.getObjname()).append("%' ");
		}
		if(chk(f.getResult())){
			sb.append(" and x.").append(HotelTClientOperLogUtil.RESULT);
			sb.append(" ='").append(f.getResult()).append("' ");
		}
	}
	public String getSqlByForm(SearchForm f){
		StringBuffer sb = new StringBuffer();
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate)+" 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate)+"-01 00:00:00";
		if(chk(f.getDate_type())){
			String dateType = f.getDate_type();
			if(dateType.equals("1")){//当天
				sb.append("select x.*  from ");
				sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
				sb.append(" where x.");
				sb.append(HotelTClientOperLogUtil.COPERTIME).append(">'");
				sb.append(todaydate0).append("'");
				addWhereLianTong(sb, f, yuestr);
				sb.append(" order by x.").append(HotelTClientOperLogUtil.COPERTIME).append(" desc");
			}else if(dateType.equals("2")){//当月
				sb.append("select x.*  from ");
				sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
				sb.append(" where x.");
				sb.append(HotelTClientOperLogUtil.COPERTIME).append(">'");
				sb.append(yuedate0).append("'");
				addWhereLianTong(sb, f, yuestr);
				sb.append(" order by x.").append(HotelTClientOperLogUtil.COPERTIME).append(" desc");
			}else if(dateType.equals("7")){//自定义
				if(!chk(f.getBegin_time())||!chk(f.getEnd_time())){//开始时间或结束时间为空
					
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begin_date = null;
					Date end_date = null;
					try {
						begin_date = sdf.parse(f.getBegin_time()+" 00:00:00");
						end_date = sdf.parse(f.getEnd_time()+" 23:59:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					int begindateyear = begin_date.getYear()+1900;
					int begindatemonth = begin_date.getMonth()+1;
					int enddateyear = end_date.getYear()+1900;
					int enddatemonth = end_date.getMonth()+1;
					int yue2 = enddatemonth + (enddateyear - begindateyear)*12;
					int yuecha = yue2 - begindatemonth;
					if(yuecha==0){//查询同一个月的数据
						String yueStr = "";
						if(begindatemonth<10){
							yueStr = enddateyear + "0" + Integer.toString(begindatemonth);
						}else{
							yueStr = enddateyear + Integer.toString(begindatemonth);
						}
						sb.append("select x.*  from ");
						sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
						
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType("x."+HotelTClientOperLogUtil.COPERTIME, sdf.format(begin_date), sdf.format(end_date)));
						addWhereLianTong(sb, f, yuestr);
						sb.append(" order by x.").append(HotelTClientOperLogUtil.COPERTIME).append(" desc");
					}else{//查询多月的数据
						sb.append("select a.*  from (");
						if(enddatemonth<10){
							yuestr = enddateyear + "0" + Integer.toString(enddatemonth);
						}else{
							yuestr = enddateyear + Integer.toString(enddatemonth);
						}
						sb.append("select x.*  from ");
						sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType("x."+HotelTClientOperLogUtil.COPERTIME, sdf.format(begin_date), sdf.format(end_date)));
						addWhereLianTong(sb, f, yuestr);
						int thisMonth = enddatemonth;
						for(int i=0;i<yuecha;i++){
							thisMonth = thisMonth -1;
							if(thisMonth==0){
								thisMonth = 12;
								enddateyear = enddateyear - 1;
							}
							if(thisMonth<10){
								String newsss = enddateyear + "0" + Integer.toString(thisMonth);
								yuestr = newsss;
							}else{
								String newsss = enddateyear + Integer.toString(thisMonth);
								yuestr = newsss;
							}
							sb.append(" UNION ALL ");
							sb.append("select x.*  from ");
							sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
							sb.append(" where ");
							sb.append(DBTypeUtil.dateSqlByDBType("x."+HotelTClientOperLogUtil.COPERTIME, sdf.format(begin_date), sdf.format(end_date)));
							addWhereLianTong(sb, f, yuestr);
						}
						sb.append(") a order by a.").append(HotelTClientOperLogUtil.COPERTIME).append(" desc");
					}
				}
			}
		}else{
			//如果查询时限类型为空，则默认查当天的
			sb.append("select x.*  from ");
			sb.append(HotelTClientOperLogUtil.TABLE_NAME).append(yuestr).append(" x");
			sb.append(" where x.");
			sb.append(HotelTClientOperLogUtil.COPERTIME).append(">'");
			sb.append(todaydate0).append("'");
			addWhereLianTong(sb, f, yuestr);
			sb.append(" order by x.").append(HotelTClientOperLogUtil.COPERTIME).append(" desc");
		}
		
		logger.info("单据操作日志sql："+sb.toString());
		return sb.toString();
	}
	
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}
	
	@SuppressWarnings("unchecked")
	private List<HotelTClientOperLogPO> listObjs(String sql) throws Exception {


		List<HotelTClientOperLogPO> list_obj;
		list_obj = new ArrayList<HotelTClientOperLogPO>();
		List<Map> list = clientOperLogDao.select(sql);

		for (Map map : list) {
			HotelTClientOperLogPO obj = new HotelTClientOperLogPO();
			obj = (HotelTClientOperLogPO) DaoUtil
					.setPo(obj, map, new HotelTClientOperLogUtil());
			list_obj.add(obj);
		}
		return list_obj;
	}

}
