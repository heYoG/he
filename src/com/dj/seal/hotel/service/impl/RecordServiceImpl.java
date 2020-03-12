package com.dj.seal.hotel.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.RecordDaoImpl;
import com.dj.seal.hotel.po.HotelTDic;
import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.hotel.service.api.RecordService;
import com.dj.seal.hotel.vo.RecordTongJi;
import com.dj.seal.hotel.vo.RecordVO;
import com.dj.seal.hotel.vo.ShowAffiliateValue;
import com.dj.seal.modelFile.service.impl.ModelFileServiceImpl;
import com.dj.seal.modelFile.vo.ModelFileVo;
import com.dj.seal.organise.dao.impl.SysUserDaoImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.web.SearchForm;

public class RecordServiceImpl implements RecordService {
	
	static Logger logger = LogManager.getLogger(RecordServiceImpl.class.getName());

	private RecordDaoImpl recordDao;
	private SysUserDaoImpl usersDao;
	private HotelTDicHotelUtilServiceImpl hotelTDicSrv;
	private ModelFileServiceImpl modelFileSrv;
	private SysDeptService dept_srv;

	public ModelFileServiceImpl getModelFileSrv() {
		return modelFileSrv;
	}

	public void setModelFileSrv(ModelFileServiceImpl modelFileSrv) {
		this.modelFileSrv = modelFileSrv;
	}

	public HotelTDicHotelUtilServiceImpl getHotelTDicSrv() {
		return hotelTDicSrv;
	}

	public void setHotelTDicSrv(HotelTDicHotelUtilServiceImpl hotelTDicSrv) {
		this.hotelTDicSrv = hotelTDicSrv;
	}

	public SysUserDaoImpl getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(SysUserDaoImpl usersDao) {
		this.usersDao = usersDao;
	}

	public RecordDaoImpl getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDaoImpl recordDao) {
		this.recordDao = recordDao;
	}

	
	//营业员查看自己办理的单据数量
	public List<RecordTongJi> getTongJi1(SearchForm f){
		String user_id=f.getUser_no();
		String dateType = f.getDate_type();
		List<ModelFileVo> modelList = new ArrayList<ModelFileVo>();
		List<RecordTongJi> list_obj = new ArrayList<RecordTongJi>();
		try {
			modelList = modelFileSrv.showModelFiles();
		} catch (Exception e) {
			logger.info("统计时获取模板信息出错");
			logger.error(e.getMessage());
		}
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate)+" 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate)+"-01 00:00:00";
		if(modelList.size()!=0){
			for(ModelFileVo mfvo : modelList){
					StringBuffer sb = new StringBuffer();
					if(dateType==null||dateType.equals("1")){//当天
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(todaydate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.UPLOADUSERID).append("='").append(user_id).append("'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}else if(dateType.equals("2")){//当月
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(yuedate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.UPLOADUSERID).append("='").append(user_id).append("'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}
						int num = recordDao.showCount(sb.toString());
						RecordTongJi rtj = new RecordTongJi();
						rtj.setNumber(num);
						rtj.setModel_name(mfvo.getModel_name());
						list_obj.add(rtj);
			}
		}
		StringBuffer sb = new StringBuffer();
		if(dateType==null||dateType.equals("1")){//当天
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(todaydate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.UPLOADUSERID).append("='").append(user_id).append("'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}else if(dateType.equals("2")){//当月
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(yuedate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.UPLOADUSERID).append("='").append(user_id).append("'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}
		int num = recordDao.showCount(sb.toString());
		RecordTongJi rtj = new RecordTongJi();
		rtj.setNumber(num);
		rtj.setModel_name("全部");
		list_obj.add(rtj);
		return list_obj;
	}
	
	//营业厅管理员查看本厅的单据数量
	public List<RecordTongJi> getTongJi2(SearchForm f){
		String dept_no=f.getDeptId();
		String dateType = f.getDate_type();
		List<ModelFileVo> modelList = new ArrayList<ModelFileVo>();
		List<RecordTongJi> list_obj = new ArrayList<RecordTongJi>();
		try {
			modelList = modelFileSrv.showModelFiles();
		} catch (Exception e) {
			logger.info("统计时获取模板信息出错");
			logger.error(e.getMessage());
		}
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate)+" 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate)+"-01 00:00:00";
		if(modelList.size()!=0){
			for(ModelFileVo mfvo : modelList){
					StringBuffer sb = new StringBuffer();
					if(dateType==null||dateType.equals("1")){//当天
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(todaydate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.DEPTNO).append("='").append(dept_no).append("'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}else if(dateType.equals("2")){//当月
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(yuedate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.DEPTNO).append("='").append(dept_no).append("'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}
						int num = recordDao.showCount(sb.toString());
						RecordTongJi rtj = new RecordTongJi();
						rtj.setNumber(num);
						rtj.setModel_name(mfvo.getModel_name());
						list_obj.add(rtj);
			}
		}
		StringBuffer sb = new StringBuffer();
		if(dateType==null||dateType.equals("1")){//当天
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(todaydate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.DEPTNO).append("='").append(dept_no).append("'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}else if(dateType.equals("2")){//当月
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(yuedate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.DEPTNO).append("='").append(dept_no).append("'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}
		int num = recordDao.showCount(sb.toString());
		RecordTongJi rtj = new RecordTongJi();
		rtj.setNumber(num);
		rtj.setModel_name("全部");
		list_obj.add(rtj);
		return list_obj;
	}
	
	//市分公司统计下属营业厅的单据
	public List<RecordTongJi> getTongJi3(SearchForm f){
		String dept_no=f.getDeptId();
		String dateType = f.getDate_type();
		List<ModelFileVo> modelList = new ArrayList<ModelFileVo>();
		List<RecordTongJi> list_obj = new ArrayList<RecordTongJi>();
		try {
			modelList = modelFileSrv.showModelFiles();
		} catch (Exception e) {
			logger.info("统计时获取模板信息出错");
			logger.error(e.getMessage());
		}
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate)+" 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate)+"-01 00:00:00";
		if(modelList.size()!=0){
			for(ModelFileVo mfvo : modelList){
					StringBuffer sb = new StringBuffer();
					if(dateType==null||dateType.equals("1")){//当天
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(todaydate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.DEPTNO).append(" like '").append(dept_no).append("%'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}else if(dateType.equals("2")){//当月
						sb.append("select count(*)  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						sb.append(" where ");
						sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
						sb.append(yuedate0).append("'");
						sb.append(" and ").append(HotelRecordUtil.DEPTNO).append(" like '").append(dept_no).append("%'");
						sb.append(" and ").append(HotelRecordUtil.MTPLID).append("='").append(mfvo.getModel_id()).append("'");
						sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
					}
						int num = recordDao.showCount(sb.toString());
						RecordTongJi rtj = new RecordTongJi();
						rtj.setNumber(num);
						rtj.setModel_name(mfvo.getModel_name());
						list_obj.add(rtj);
			}
		}
		StringBuffer sb = new StringBuffer();
		if(dateType==null||dateType.equals("1")){//当天
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(todaydate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.DEPTNO).append(" like '").append(dept_no).append("%'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}else if(dateType.equals("2")){//当月
			sb.append("select count(*)  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(yuedate0).append("'");
			sb.append(" and ").append(HotelRecordUtil.DEPTNO).append(" like '").append(dept_no).append("%'");
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("!='0'");//状态不是作废
		}
		int num = recordDao.showCount(sb.toString());
		RecordTongJi rtj = new RecordTongJi();
		rtj.setNumber(num);
		rtj.setModel_name("全部");
		list_obj.add(rtj);
		return list_obj;
	}
	
	
	//显示稽核列表
	public PageSplit showJiHeBySch(int pageIndex, int pageSize, SearchForm f)
	throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByFormNew(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<RecordVO> datas = listObjsJiHe(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordDao.showCount(selectsql);
		logger.info(totalCount);
		ps.setTotalCount(totalCount);
		return ps;
	}
	//供稽核使用
	@SuppressWarnings("unchecked")
	private List<RecordVO> listObjsJiHe(String sql) throws Exception {
		List<RecordVO> list_obj = new ArrayList<RecordVO>();
		List<Map> list = recordDao.select(sql);
		List<HotelTDic> hotelTDicList =  hotelTDicSrv.getPageShowHotelDics();
		for (Map map : list) {
			RecordPO obj = new RecordPO();
			obj = (RecordPO) DaoUtil.setPo(obj, map, new HotelRecordUtil());
			//list_obj.add(poToVoJiHe(obj));
			list_obj.add(poToVo2(obj,hotelTDicList));
		}
		return list_obj;
	}
	
	//供稽核使用
	public RecordVO poToVoJiHe(RecordPO obj) throws Exception {
		RecordVO vo = new RecordVO();
		if(obj.getCcreatetime()==null||obj.getCcreatetime().equals("")){
			obj.setCcreatetime(obj.getCuploadtime());
		}
		BeanUtils.copyProperties(vo, obj);
		String mtplid = vo.getMtplid();
		if(mtplid==null||mtplid.equals("")){
			vo.setMtplname("");
		}else{
			ModelFileVo mfvo = modelFileSrv.getModelFile(Integer.parseInt(mtplid));
			if(mfvo==null){
				vo.setMtplname("");
			}else{
				vo.setMtplname(mfvo.getModel_name());
			}
		}
		String dept_no = obj.getDeptno();
		if(dept_no==null||dept_no.equals("")){
			vo.setDeptname("");
		}else{
			SysDepartment dept = dept_srv.showDeptByNoSimple(dept_no);
			if(dept!=null){
				vo.setDeptname(dept.getDept_name());
			}else{
				vo.setDeptname("");
			}
		}
		return vo;
	}
	
	
	/**
	 * 添加方法
	 */
	@Override
	public String addRecord(RecordPO record) {
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
		record.setCid(id);
		if(record.getDeptno()==null||record.getDeptno().equals("")){
			SysUser user = usersDao.showSysUserByUser_id(record.getUploaduserid());
			if(user!=null){
				record.setDeptno(user.getDept_no());
			}
		}
		recordDao.addRecord(record);
		return id;
	}

	/**
	 * 添加方法  生成月表
	 */
	public String addRecordNew(RecordPO record) {
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
		record.setCid(id);
		if(record.getDeptno()==null||record.getDeptno().equals("")){
			SysUser user = usersDao.showSysUserByUser_id(record.getUploaduserid());
			if(user!=null){
				record.setDeptno(user.getDept_no());
			}
		}
		recordDao.addRecordNew(record);
		return id;
	}
	
	//处理单据
	public String dealRecord(String user_no,String oldRecordId,String filename,String user_ip,String role_name) {
		RecordPO record = null;
		try {
			record = this.getRecord(oldRecordId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(record==null){
			return "error:未找到该记录!";
		}
		String updatestatus = "";
		//如果单据已被处理则返回错误
		if(role_name!=null&&role_name.equals("yiji")){
			if(record.getHasdone().equals("1")){
				return "error:该文件已被处理!";
			}else{
				updatestatus="1";
			}
		}else if(role_name!=null&&role_name.equals("erji")){
			if(record.getHasdone().equals("2")){
				return "error:该文件已被处理!";
			}else{
				updatestatus="2";
			}
		}else{
			if(record.getHasdone().equals("1")){
				return "error:该文件已被处理!";
			}else{
				updatestatus="1";
			}
		}
		
		//添加新单据
//		record.setCreateuserid(user_no);
//		record.setCip(user_ip);
//		Timestamp create_time = new Timestamp(new Date().getTime());
//		record.setCuploadtime(create_time);
//		record.setCcreatetime(create_time);
//		record.setCfilefilename(filename);
//		record.setCdata(filename);
//		record.setHasdone("1");
//		String newid = this.addRecord(record);
		//将旧单据的单据属性复制到新单据
//		List<RecordAffiliatePO> alist = this.getRecordAffiliatePOByRid(oldRecordId);
//		for(RecordAffiliatePO recordAff : alist){
//			recordAff.setRecordid(newid);
//			this.addRecordAffiliatePo(recordAff);
//		}
		int result = this.updateRecordHasDone(oldRecordId, updatestatus);//更新单据状态为已处理
		if(result==1){
			return "success";
		}else{
			return "error:更新单据状态失败";
		}
	}
	
	public int updateRecordHasDone(String rid,String hasdone) {
		try {
			recordDao.updateRecordHasDone(rid,hasdone);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}

	@SuppressWarnings("unused")
	private static boolean chk(Timestamp t) {
		return t != null && !"".equals(t);
	}


	@Override
	public List<RecordPO> ShowRecord() {
		String sql = "";
		return recordDao.getAllRecordPoBy(sql);
	}


	@Override
	public List<RecordAffiliatePO> getRecordAffiliatePOByRid(String rid) {
		return recordDao.getRecordAffiliatePOByRecordId(rid);
	}


	@Override
	public int updateRecordStatusIsOff(String rid) {
		try {
			recordDao.updateRecordStatusIsOff(rid);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int deleteRecord(String rid) {
		try {
			recordDao.deleteRecord(rid);
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}
	
	//更改稽核状态
	public int updateRecordCheckStatus(String rid,String user_no,String status,String reason) {
		try {
			recordDao.updateRecordCheckStatus(rid, user_no, status, reason);
			return 1;
		} catch (DAOException e) {
			logger.error(e.getMessage());
			return 0;
		}
		
	}
	
	@Override
	public int updateRecordStatusIsOn(String rid) {
		try {
			recordDao.updateRecordStatusIsOn(rid);
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<RecordAffiliatePO> shwoRecordAffiliate() {

		String sql = "";
		return recordDao.getAllRecordAffiliatePOBySql(sql);
	}

	
	//加入月表功能，查询单据表
	public String getSqlByFormNew(SearchForm f) {
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
			logger.info("类型-----"+dateType);
			if(dateType.equals("1")){//当天
				sb.append("select *  from ");
				sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
				if(f.getKeyword()!=null&&f.getKeyword()!=""){
					sb.append(" INNER JOIN ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(" ON ").
					append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(".").append(HotelRecordUtil.CID).append("=").
					append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.RID);
				}
				sb.append(" where ");
				sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
				sb.append(todaydate0).append("'");
				addWhereLianTong(sb, f, yuestr);
				sb.append(" order by ").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
			}else if(dateType.equals("2")){//当月
				sb.append("select *  from ");
				sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
				if(f.getKeyword()!=null&&f.getKeyword()!=""){
					sb.append(" INNER JOIN ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(" ON ").
					append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(".").append(HotelRecordUtil.CID).append("=").
					append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.RID);
				}
				sb.append(" where ");
				sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
				sb.append(yuedate0).append("'");
				addWhereLianTong(sb, f, yuestr);
				sb.append(" order by ").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
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
						logger.error(e.getMessage());
					}
					int begindateyear = begin_date.getYear()+1900;
					int begindatemonth = begin_date.getMonth()+1;
//					int begindatedate = begin_date.getDate();
					int enddateyear = end_date.getYear()+1900;
					int enddatemonth = end_date.getMonth()+1;
//					int enddatedate = end_date.getDate();
					int yue2 = enddatemonth + (enddateyear - begindateyear)*12;
					int yuecha = yue2 - begindatemonth;
					if(yuecha==0){//查询同一个月的数据
						String yueStr = "";
						if(begindatemonth<10){
							yueStr = enddateyear + "0" + Integer.toString(begindatemonth);
						}else{
							yueStr = enddateyear + Integer.toString(begindatemonth);
						}
						sb.append("select *  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yueStr);
						if(f.getKeyword()!=null&&f.getKeyword()!=""){
							sb.append(" INNER JOIN ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(" ON ").
							append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(".").append(HotelRecordUtil.CID).append("=").
							append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.RID);
						}
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType(HotelRecordUtil.CUPLOADTIME, sdf.format(begin_date), sdf.format(end_date)));
						addWhereLianTong(sb, f, yueStr);
						sb.append(" order by ").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
					}else{//查询多月的数据
						sb.append("select distinct a.*  from (");
						if(enddatemonth<10){
							yuestr = enddateyear + "0" + Integer.toString(enddatemonth);
						}else{
							yuestr = enddateyear + Integer.toString(enddatemonth);
						}
						sb.append("select *  from ");
						sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
						if(f.getKeyword()!=null&&f.getKeyword()!=""){
							sb.append(" INNER JOIN ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(" ON ").
							append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(".").append(HotelRecordUtil.CID).append("=").
							append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.RID);
						}
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType(HotelRecordUtil.CUPLOADTIME, sdf.format(begin_date), sdf.format(end_date)));
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
							sb.append("select *  from ");
							sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
							if(f.getKeyword()!=null&&f.getKeyword()!=""){
								sb.append(" INNER JOIN ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(" ON ").
								append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(".").append(HotelRecordUtil.CID).append("=").
								append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.RID);
							}
							sb.append(" where ");
							sb.append(DBTypeUtil.dateSqlByDBType(HotelRecordUtil.CUPLOADTIME, sdf.format(begin_date), sdf.format(end_date)));
							addWhereLianTong(sb, f, yuestr);
						}
						sb.append(") a order by a.").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
					}
				}
			}
		}else{
			//如果查询时限类型为空，则默认查当天的
			sb.append("select *  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(HotelRecordUtil.CUPLOADTIME).append(">'");
			sb.append(todaydate0).append("'");
			addWhereLianTong(sb, f, yuestr);
			sb.append(" order by ").append(HotelRecordUtil.CUPLOADTIME).append(" desc");
		}
		
		
		logger.info("多表：：：："+sb.toString());
		return sb.toString();
	}
	
	
	//联通  单据月表 查询条件 
	public void addWhereLianTong(StringBuffer sb,SearchForm f,String yuestr){
		if(chk(f.getKeyword())){
			sb.append( " and ").append(HotelRecordContent.TABLE_NAME).append(yuestr).append(".").append(HotelRecordContent.CONTENT);
			sb.append(" like '%").append(f.getKeyword()).append("%'");
		}
		//增加部门查询
		if (chk(f.getDeptId()) ) {
			sb.append(" and ").append(HotelRecordUtil.DEPTNO);
			sb.append(" like '").append(f.getDeptId()).append("%'");
		}
		//增加稽核状态查询
		if (chk(f.getCheckstatus()) ) {
			if(f.getCheckstatus().equals("2a")){
				sb.append(" and (").append(HotelRecordUtil.CHECKSTATUS);
				sb.append(" = '2'");
				sb.append(" or ").append(HotelRecordUtil.CHECKSTATUS);
				sb.append(" = 'a')");
			}else{
				sb.append(" and ").append(HotelRecordUtil.CHECKSTATUS);
				sb.append(" = '").append(f.getCheckstatus()).append("'");
			}
		}
		//受理人  创建人
		if (chk(f.getCreateuserid())) {
			sb.append(" and ").append(HotelRecordUtil.CREATEUSERID).append(
					" = '").append(f.getCreateuserid()).append("'");
		}
		//IP
		if (chk(f.getCip())) {
			sb.append(" and ").append(HotelRecordUtil.CIP).append(
					" like '%").append(f.getCip()).append("%'");
		}
		//模板ID
		if (chk(f.getMtplid())) {
			sb.append(" and ").append(HotelRecordUtil.MTPLID)
					.append(" = '").append(f.getMtplid()).append("'");
		}
		//状态
		if (chk(f.getCstatus())) {
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append(
					" = '").append(f.getCstatus()).append("'");
		}
		// 增加证件号码查询
		if (chk(f.getCardNo())) {
			sb.append(" and ").append(HotelRecordUtil.CID).append(" in(");
			sb.append("select ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append(" from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(HotelTRecordAffiliatedUtil.CNAME);
			sb.append(" like  'cardNo%' ");
			sb.append("and ").append(HotelTRecordAffiliatedUtil.CVALUE);
			sb.append(" like '%").append(f.getCardNo()).append("%')");
		}
		// 增加受理编号查询
		if (chk(f.getShouliNo())) {
			sb.append(" and ").append(HotelRecordUtil.CID).append(" in(");
			sb.append("select ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append(" from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(HotelTRecordAffiliatedUtil.CNAME);
			sb.append(" like  'shouliNo%' ");
			sb.append("and ").append(HotelTRecordAffiliatedUtil.CVALUE);
			sb.append(" like '%").append(f.getShouliNo()).append("%')");
		}
		// 增加证件客户姓名查询
		if (chk(f.getCusName1())) {
			sb.append(" and ").append(HotelRecordUtil.CID).append(" in(");
			sb.append("select ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append(" from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(HotelTRecordAffiliatedUtil.CNAME);
			sb.append(" like  'guestname%' ");
			sb.append("and ").append(HotelTRecordAffiliatedUtil.CVALUE);
			sb.append(" like '%").append(f.getCusName1()).append("%')");
		}
		// 增加手机号码查询
		if (chk(f.getPhoneNo())) {
			sb.append(" and ").append(HotelRecordUtil.CID).append(" in(");
			sb.append("select ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append(" from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(HotelTRecordAffiliatedUtil.CNAME);
			sb.append(" like  'phoneNo%' ");
			sb.append("and ").append(HotelTRecordAffiliatedUtil.CVALUE);
			sb.append(" like '%").append(f.getPhoneNo()).append("%')");
		}
		// 增加属性号查询条件
		if (chk(f.getCname()) && chk(f.getCvalue())) {
			sb.append(" and ").append(HotelRecordUtil.CID).append(" in(");
			sb.append("select ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append(" from ").append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yuestr);
			sb.append("where ").append(HotelTRecordAffiliatedUtil.CNAME);
			sb.append(" ='").append(f.getCname()).append("'");
			sb.append("and ").append(HotelTRecordAffiliatedUtil.CVALUE);
			sb.append(" ='").append(f.getCvalue()).append("')");
		}
	}
	
	
	
	
	
	
	
	public String getSqlByForm(SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ");
		sb.append(HotelRecordUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if (chk(f.getCreateuserid())) {
			sb.append(" and ").append(HotelRecordUtil.CREATEUSERID).append(
					" = '").append(f.getCreateuserid()).append("'");
		}
		if (chk(f.getCip())) {
			sb.append(" and ").append(HotelRecordUtil.CIP).append(
					" like '%").append(f.getCip()).append("%'");
		}
		if (chk(f.getBegin_time())) {
			sb.append(" and ").append(HotelRecordUtil.CUPLOADTIME);
			sb.append(" > '");
			sb.append(f.getBegin_time()).append(" 00:00:00'");
		}
		if (chk(f.getEnd_time())) {
			sb.append(" and ").append(HotelRecordUtil.CUPLOADTIME);
			sb.append(" < '");
			sb.append(f.getEnd_time()).append(" 23:59:59'");
		}
		if (chk(f.getMtplid())) {
			sb.append(" and ").append(HotelRecordUtil.MTPLID)
					.append(" = '").append(f.getMtplid()).append("'");
		}
		if (chk(f.getCstatus())) {
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append(
					" = '").append(f.getCstatus()).append("'");
		}
		//增加查询条件
		addWhere(sb,f);
		return sb.toString();
	}
	
	
	
	public String getWaitDealSqlByForm(SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ");
		sb.append(HotelRecordUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if(chk(f.getRole_name())){
			if(f.getRole_name().equals("yiji")){
				sb.append(" and ").append(HotelRecordUtil.HASDONE).append("='0'");//待处理
			}else if(f.getRole_name().equals("erji")){
				sb.append(" and ").append(HotelRecordUtil.HASDONE).append("='1'");//已处理，已进行过一级稽核
			}else{
				sb.append(" and ").append(HotelRecordUtil.HASDONE).append("='0'");//待处理
			}
		}else{
			sb.append(" and ").append(HotelRecordUtil.HASDONE).append("='0'");//待处理
		}
		sb.append(" and ").append(HotelRecordUtil.CSTATUS).append("='1'");//正常状态
		if (chk(f.getCreateuserid())) {
			sb.append(" and ").append(HotelRecordUtil.CREATEUSERID).append(
					" = '").append(f.getCreateuserid()).append("'");
		}
		if (chk(f.getMtplid())) {
			sb.append(" and ").append(HotelRecordUtil.MTPLID)
					.append(" = '").append(f.getMtplid()).append("'");
		}
		if (chk(f.getCstatus())) {
			sb.append(" and ").append(HotelRecordUtil.CSTATUS).append(
					" = '").append(f.getCstatus()).append("'");
		}
		//增加查询条件
		addWhere(sb,f);
		return sb.toString();
	}
	
	
	public void addWhere(StringBuffer sb,SearchForm f){
		if (chk(f.getGdh())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME + " = "
					+ " 'gdh' and " + HotelTRecordAffiliatedUtil.CVALUE
					+ " = '" + f.getGdh() + "'";
			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
				
			} else {
				sb.append(" and 1=2 ");
			}
		}
		
		if (chk(f.getCph())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME + " = "
					+ " 'cph' and " + HotelTRecordAffiliatedUtil.CVALUE
					+ " = '" + f.getCph() + "'";
			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
				
			} else {
				sb.append(" and 1=2 ");
			}
		}
		
		if (chk(f.getFwzy())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME + " = "
					+ " 'fwzy' and " + HotelTRecordAffiliatedUtil.CVALUE
					+ " = '" + f.getFwzy() + "'";
			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
				
			} else {
				sb.append(" and 1=2 ");
			}
		}
		
		if (chk(f.getRoomId())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME + " = "
					+ " 'roomid' and " + HotelTRecordAffiliatedUtil.CVALUE
					+ " = '" + f.getRoomId() + "'";
			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
				
			} else {
				sb.append(" and 1=2 ");
			}
		}
		// 入住时间条件查询
		if (chk(f.getInsTime()) && chk(f.getIneTime())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME
					+ " = 'inDate' and str_to_date( "
					+ HotelTRecordAffiliatedUtil.CVALUE
					+ ",'%Y年%m月%d日') between '" + f.getInsTime() + "' and '"
					+ f.getIneTime() + "' ";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}
		}

		// 离店时间条件查询
		if (chk(f.getOutsTime()) && chk(f.getOuteTime())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME
					+ " = 'outDate' and str_to_date( "
					+ HotelTRecordAffiliatedUtil.CVALUE
					+ ",'%Y年%m月%d日') between '" + f.getOutsTime() + "' and '"
					+ f.getOuteTime() + "' ";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}
		}

		// 增加证件号码查询
		if (chk(f.getCardNo())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME
					+ " like  'cardNo%' and "
					+ HotelTRecordAffiliatedUtil.CVALUE + " like '%"
					+ f.getCardNo() + "%'";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}

		}

		// 增加受理编号查询
		if (chk(f.getShouliNo())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME
					+ " like  'shouliNo%' and "
					+ HotelTRecordAffiliatedUtil.CVALUE + " like '%"
					+ f.getShouliNo() + "%'";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}

		}
		
		// 增加证件客户姓名查询
		if (chk(f.getCusName1())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME
					+ " like  'guestname%' and "
					+ HotelTRecordAffiliatedUtil.CVALUE + " like '%"
					+ f.getCusName1() + "%'";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}

		}

		// 增加属性号查询条件
		if (chk(f.getCname()) && chk(f.getCvalue())) {
			String sql = "select * from "
					+ HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
					+ HotelTRecordAffiliatedUtil.CNAME + " = " + " '"
					+ f.getCname() + "' and "
					+ HotelTRecordAffiliatedUtil.CVALUE + " = '"
					+ f.getCvalue() + "'";

			List<RecordAffiliatePO> listR = recordDao
					.getAllRecordAffiliatePOBySql(sql);
			if (listR.size() != 0) {
				sb.append(" and ").append(HotelRecordUtil.CID).append(
						" in (");

				String strs = "";
				for (RecordAffiliatePO ra : listR) {
					strs += "'" + ra.getRecordid() + "',";
				}
				strs = strs.substring(0, strs.length() - 1);
				sb.append(strs).append(")");
			} else {
				sb.append(" and 1=2 ");
			}

		}
		
		//增加部门查询
		if (chk(f.getDeptId()) ) {
			sb.append(" and ").append(HotelRecordUtil.DEPTNO);
			sb.append(" ='").append(f.getDeptId()).append("'");
//			String sql = "select * from "
//					+ SysUserUtil.TABLE_NAME + " where "
//					+ SysUserUtil.DEPT_NO + " = " + " '"
//					+ f.getDeptId() + "'";
//			
//			List<SysUser> listR = usersDao.showSysUsersBySql(sql);
//			logger.info(listR);
//			try {
//				if (listR.size() != 0) {
//					sb.append(" and ")
//							.append(HotelRecordUtil.CREATEUSERID).append(
//									" in (");
//					String strs = "";
//					for (SysUser ra : listR) {
//						strs += "'" + ra.getUser_id() + "',";
//					}
//					strs = strs.substring(0, strs.length() - 1);
//					sb.append(strs).append(")");
//				} else {
//					sb.append(" and 1=2 ");
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}
		}
		sb.append(" order by ").append(HotelRecordUtil.CCREATETIME).append(" desc ");

	}

//	public PageSplit showRecordBySch(int pageIndex, int pageSize, SearchForm f)
//			throws Exception {
//		PageSplit ps = new PageSplit();
//		String selectsql = getSqlByForm(f);
//		logger.info("selectsql:"+selectsql);
//		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
//				Constants.DB_TYPE);
//		List<RecordVO> datas = listObjs(sql);
//		ps.setDatas(datas);
//		ps.setNowPage(pageIndex);
//		ps.setPageSize(pageSize);
//		int totalCount = recordDao.showCount(selectsql);
//		ps.setTotalCount(totalCount);
//		return ps;
//	}

	
	public PageSplit showRecordBySch(int pageIndex, int pageSize, SearchForm f)
	throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByFormNew(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<RecordVO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordDao.showCount(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	
	
	//查询待处理的记录
	public PageSplit showWaitDealRecordBySch(int pageIndex, int pageSize, SearchForm f)
	throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getWaitDealSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<RecordVO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordDao.showCount(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<RecordVO> listObjs(String sql) throws Exception {
		List<RecordVO> list_obj = new ArrayList<RecordVO>();
		List<Map> list = recordDao.select(sql);
		List<HotelTDic> hotelTDicList =  hotelTDicSrv.getPageShowHotelDics();
		for (Map map : list) {
			RecordPO obj = new RecordPO();
			obj = (RecordPO) DaoUtil.setPo(obj, map, new HotelRecordUtil());
//			list_obj.add(poToVo(obj));//稽核，不查询关联的属性信息。
			list_obj.add(poToVo2(obj,hotelTDicList));//东软酒店新增，要求在稽核菜单中添加一个合并文档查看的功能，要求按照roomId号查询。所以需要查询账单对应的属性表。
		}
		return list_obj;
	}

	public RecordPO getRecord(String cid) throws Exception {
		return recordDao.getRecordPOById(cid);
	}

	public RecordVO getRuleVo(String no) throws Exception {
		RecordPO obj = getRecord(no);
		return poToVo(obj);
	}

	public RecordVO poToVo(RecordPO obj) throws Exception {
		RecordVO vo = new RecordVO();
		if(obj.getCcreatetime()==null||obj.getCcreatetime().equals("")){
			obj.setCcreatetime(obj.getCuploadtime());
		}
		BeanUtils.copyProperties(vo, obj);
		List<RecordAffiliatePO> rfList = recordDao.getRecordAffiliatePOByRecordId(obj.getCid());
		if (rfList.size() != 0) {
			for (int i = 0; i < rfList.size(); i++) {
				RecordAffiliatePO rapo = rfList.get(i);
				if (rapo.getCname().equals("roomid")) {
					vo.setRoomId(rfList.get(i).getCvalue());
				}else if (rapo.getCname().equals("guestname1")) {
					vo.setGuestname1(rfList.get(i).getCvalue());
				}else if (rapo.getCname().equals("indate")) {
					vo.setIndate(rfList.get(i).getCvalue());
				}else if (rapo.getCname().equals("outdate")) {
					vo.setOutdate(rfList.get(i).getCvalue());
				}else if (rapo.getCname().equals("totalmoney")) {
					vo.setTotalmoney(rfList.get(i).getCvalue());
				}
			}
		} 
		return vo;
	}

	
	public RecordVO poToVo2(RecordPO obj,List<HotelTDic> hotelTDicList) throws Exception {
		RecordVO vo = new RecordVO();
		if(obj.getCcreatetime()==null||obj.getCcreatetime().equals("")){
			obj.setCcreatetime(obj.getCuploadtime());
		}
		BeanUtils.copyProperties(vo, obj);
		List<ShowAffiliateValue> affiliateList = new ArrayList();
		if(hotelTDicList!=null&&hotelTDicList.size()!=0){
//			List<RecordAffiliatePO> rfList = recordDao.getRecordAffiliatePOByRecordId(obj.getCid());
			List<RecordAffiliatePO> rfList = recordDao.getRecordAffiliatePOByRecordIdNew(obj.getCid());
			for(int j = 0; j < hotelTDicList.size(); j++){
				ShowAffiliateValue sv = new ShowAffiliateValue();
				sv.setName(hotelTDicList.get(j).getCname());
				if (rfList.size() != 0) {
//					logger.info("rfList.size():"+rfList.size()+"   j="+j);
					for (int i = 0; i < rfList.size(); i++) {
						if (rfList.get(i).getCname().equals(hotelTDicList.get(j).getCname())) {
							sv.setValue(rfList.get(i).getCvalue());
							rfList.remove(i);
							break;
						}
					}
				}
				affiliateList.add(sv);
			} 
		}
		vo.setAffiliateList(affiliateList);
		String rid = vo.getCid();
		List<RecordAffiliatePO> reas =recordDao.getRecordAffiliatePOByRecordIdNew(rid);  
		for (RecordAffiliatePO recordAffiliatePO : reas) {
			if(recordAffiliatePO.getCname().equals("roomId")){
				vo.setRoomId(recordAffiliatePO.getCvalue());
			}
		}
		String mtplid = vo.getMtplid();
		if(mtplid==null||mtplid.equals("")){
			vo.setMtplname("");
		}else{
			ModelFileVo mfvo = modelFileSrv.getModelFile(Integer.parseInt(mtplid));
			if(mfvo==null){
				vo.setMtplname("");
			}else{
				vo.setMtplname(mfvo.getModel_name());
			}
		}
		String createUserId = vo.getCreateuserid();
		if(createUserId==null||createUserId.equals("")){
			vo.setCreateUserName("");
		}else{
			String sql = "select * from "
				+ SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID + " = " + " '"
				+ createUserId + "'";
			List<SysUser> userlist = usersDao.showSysUsersBySql(sql);
			if(userlist.size()==0){
				vo.setCreateUserName("");
			}else{
				vo.setCreateUserName(userlist.get(0).getUser_name());
			}
		}
		return vo;
	}
	
	/**
	 * 增加RecordAffiliatePo
	 * @return 1成功，0失败
	 */
	@Override
	public int addRecordAffiliatePo(RecordAffiliatePO recordAffiliate) {
		
		try {
			recordDao.addRecordAffileate(recordAffiliate);
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	
	/**
	 * 增加RecordAffiliatePo   月表
	 * @return 1成功，0失败
	 */
	public int addRecordAffiliatePoNew(RecordAffiliatePO recordAffiliate) {
		
		try {
			recordDao.addRecordAffileateNew(recordAffiliate);
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	
	@Override
	public List<RecordPO> getRecordPos(String strs) {
		return recordDao.getRecordPos(strs);
	}

	@Override
	public int addRecordContent(RecordContentPO rContent) {
		try {
			recordDao.addRecordContent(rContent);
			return 1;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
		
	}

	public SysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(SysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

}
