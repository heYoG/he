package com.dj.seal.highcharts.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.highcharts.form.ChartServiceForm;
import com.dj.seal.highcharts.service.aip.ChartService;
import com.dj.seal.hotel.dao.api.RecordDao;
import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.modelFile.dao.api.ModelFileDao;
import com.dj.seal.modelFile.po.ModelFile;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.structure.dao.po.ChartReport;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;
import com.dj.seal.util.table.ModelFileUtil;
import com.dj.seal.util.table.SysUserUtil;

public class ChartServiceImpl implements ChartService {
	
	static Logger logger = LogManager.getLogger(ChartServiceImpl.class.getName());
	
	private ISysUserDao user_dao;
	
	private ModelFileDao model_dao;
	
	private RecordDao record_dao;
	
	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

	
	
	public ModelFileDao getModel_dao() {
		return model_dao;
	}

	public void setModel_dao(ModelFileDao model_dao) {
		this.model_dao = model_dao;
	}

	public HotelRecordUtil getTable() {
		return table;
	}

	public void setTable(HotelRecordUtil table) {
		this.table = table;
	}


	public RecordDao getRecord_dao() {
		return record_dao;
	}

	public void setRecord_dao(RecordDao record_dao) {
		this.record_dao = record_dao;
	}
	
	private HotelRecordUtil table = new HotelRecordUtil();
	
	private String getRecordAffilidatedSql(ChartServiceForm serviceForm){
		StringBuffer sb = new StringBuffer();
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		getDate(serviceForm);
		int currDate = 0;
		try {
			 currDate = getCurrDate(serviceForm.getStime(),serviceForm.getEtime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(currDate == 0){
			sb.append("select * from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr).append(" where 1=1 ");
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin_date = null;
			Date end_date = null;
			try {
				begin_date = sdf.parse(serviceForm.getStime()+" 00:00:00");
				end_date = sdf.parse(serviceForm.getEtime()+" 23:59:59");
			} catch (ParseException e) {
//				logger.error(e.getMessage());
			}
			int begindateyear = begin_date.getYear()+1900;
			int begindatemonth = begin_date.getMonth()+1;
			int enddateyear = end_date.getYear()+1900;
			int enddatemonth = end_date.getMonth()+1;
			int yue2 = enddatemonth + (enddateyear - begindateyear)*12;
			int yuecha = yue2 - begindatemonth;
			
			//查询多月的数据
			sb.append("select distinct a.*  from (");
			if(enddatemonth<10){
				yuestr = enddateyear + "0" + Integer.toString(enddatemonth);
			}else{
				yuestr = enddateyear + Integer.toString(enddatemonth);
			}
			sb.append("select *  from ");
			sb.append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
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
				sb.append("select *  from ");
				sb.append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			}
		
		}
		
		return sb.toString();
	}
		
	private String getRecordSql(ChartServiceForm serviceForm){
		StringBuffer sb = new StringBuffer();
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate)+" 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate)+"-01 00:00:00";
		getDate(serviceForm);
		int currDate = 0;
		try {
			 currDate = getCurrDate(serviceForm.getStime(),serviceForm.getEtime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(currDate == 0){
			sb.append("select * from ").append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(" where 1=1 ");
			addWhere(serviceForm, sb);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin_date = null;
			Date end_date = null;
			try {
				begin_date = sdf.parse(serviceForm.getStime()+" 00:00:00");
				end_date = sdf.parse(serviceForm.getEtime()+" 23:59:59");
			} catch (ParseException e) {
//				logger.error(e.getMessage());
			}
			int begindateyear = begin_date.getYear()+1900;
			int begindatemonth = begin_date.getMonth()+1;
			int enddateyear = end_date.getYear()+1900;
			int enddatemonth = end_date.getMonth()+1;
			int yue2 = enddatemonth + (enddateyear - begindateyear)*12;
			int yuecha = yue2 - begindatemonth;
			
			//查询多月的数据
			sb.append("select distinct a.*  from (");
			if(enddatemonth<10){
				yuestr = enddateyear + "0" + Integer.toString(enddatemonth);
			}else{
				yuestr = enddateyear + Integer.toString(enddatemonth);
			}
			sb.append("select *  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(DBTypeUtil.dateSqlByDBType(HotelRecordUtil.CUPLOADTIME, sdf.format(begin_date), sdf.format(end_date)));
			addWhere(serviceForm, sb);
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
				sb.append("select *  from ");
				sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
				sb.append(" where ");
				sb.append(DBTypeUtil.dateSqlByDBType(HotelRecordUtil.CCREATETIME, sdf.format(begin_date), sdf.format(end_date)));
				addWhere(serviceForm, sb);
			}
			sb.append(") a order by a.").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
		
		}
		
		return sb.toString();
	}

	@Override
	public List<ChartReport> showRecordDataByDept(ChartServiceForm serviceForm) {
		SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd" );
		String sql = getRecordSql(serviceForm);
		String recorddaffsql = getRecordAffilidatedSql(serviceForm);
		String modelSql = "select "+ ModelFileUtil.MODEL_ID+","+ModelFileUtil.MODEL_NAME+" from "+ModelFileUtil.TABLE_NAME;
		String usersql = "select " +SysUserUtil.USER_ID+", "+SysUserUtil.USER_NAME+" from "+ SysUserUtil.TABLE_NAME;
		logger.info(sql);
		logger.info("modelSql:"+modelSql);
		logger.info("usersql:"+usersql);
		List<RecordPO> records = record_dao.getAllRecordPoBy(sql);
		List<RecordAffiliatePO> raffis = record_dao.getAllRecordAffiliatePOBySql(recorddaffsql);
		List<ModelFile> models = model_dao.getmodelFileList(modelSql);
		List<SysUser>  users = user_dao.showSysUsersBySql(usersql);
		Map<String,String> userMap = new HashMap<String, String>();
		Map<Integer, String> modelMap = new HashMap<Integer, String>();
		Map<String, RecordAffiliatePO> raffisMap = new HashMap<String, RecordAffiliatePO>();
		for (RecordAffiliatePO raffi : raffis) {
			if(raffi.getCname().equals("money")){
				raffisMap.put(raffi.getRecordid(), raffi);
			}
		}
		for (ModelFile modelFile : models) {
//			logger.info(modelFile.getModel_name());
			modelMap.put(modelFile.getModel_id(), modelFile.getModel_name());
		}
		for (SysUser sysUser : users) {
			userMap.put(sysUser.getUser_id(), sysUser.getUser_name());
		}
		ChartReport report = null;
		List<ChartReport> reports = new ArrayList<ChartReport>();
		String[] saticStr = {"非常满意","满意","一般","不满意"};
		for (RecordPO recordPO : records) {
			Random rand = new Random();
			int index =  rand.nextInt(4);
			logger.info("index::"+index);
			report = new ChartReport();
			logger.info(recordPO.getMtplid()+"::"+modelMap.containsKey(Integer.parseInt(recordPO.getMtplid())));
			if(modelMap.containsKey(Integer.parseInt(recordPO.getMtplid()))){
				report.setBusinessType(modelMap.get(Integer.parseInt(recordPO.getMtplid())));
			}else{
				report.setBusinessType("其它");
			}
			report.setCounter(recordPO.getDeptno());
			report.setId(recordPO.getCid());
			report.setSalesman(userMap.get(recordPO.getUploaduserid()));
			report.setSatisfaction(String.valueOf(index));
			if(raffisMap.containsKey(recordPO.getCid())){
				report.setMoney(raffisMap.get(recordPO.getCid()).getCvalue());
			}else{
				report.setMoney("0");
			}
			report.setTime(sdf.format(recordPO.getCcreatetime()));
			reports.add(report);
		}
		return reports;
	}

	private void addWhere(ChartServiceForm serviceForm, StringBuffer sb) {
		if(serviceForm.getBusinessType()!=null && !"".equals(serviceForm.getBusinessType())){
			sb.append(" and ").append(HotelRecordUtil.MTPLID).append(" = '").append(serviceForm.getBusinessType()).append("'");
		}else if(serviceForm.getCounter()!=null && !"".equals(serviceForm.getCounter())){
			sb.append(" and ").append(HotelRecordUtil.DEPTNO).append(" = '").append(serviceForm.getCounter()).append("'");;
		}else if(serviceForm.getSalesman()!= null && !"".equals(serviceForm.getSalesman())){
			sb.append(" and ").append(HotelRecordUtil.UPLOADUSERID).append("='").append(serviceForm.getSalesman()).append("'");
		}
		sb.append(" order by ").append(HotelRecordUtil.CCREATETIME);
//		getDate(serviceForm);
	}
	
	private int getCurrDate(String sTime ,String etime) throws ParseException{
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date sd = sdf.parse(sTime+" 00:00:00");
		Date ed = sdf.parse(etime+" 00:00:00");
		if((sd.getMonth()-ed.getMonth()==0)&&(sd.getYear()-ed.getYear()==0)){
			return 0;
		}else {
			return 1;
		}
	}
	
	private void getDate(ChartServiceForm serviceForm){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		if(serviceForm.getStime()==null || "".equals(serviceForm.getStime())){
			 //获取前月的第一天
	        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
	        cal_1.add(Calendar.MONTH, 0);
	        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        serviceForm.setStime(format.format(cal_1.getTime()));
		}
		if(serviceForm.getEtime()==null || "".equals(serviceForm.getEtime())){
			//获取前月的最后一天
	        Calendar cale = Calendar.getInstance();   
	        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        serviceForm.setEtime(format.format(cale.getTime()));
		}     
	}

}
