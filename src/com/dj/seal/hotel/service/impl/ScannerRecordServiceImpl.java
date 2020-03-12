package com.dj.seal.hotel.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.ScannerRecordDaoImpl;
import com.dj.seal.hotel.po.ScannerRecordPO;
import com.dj.seal.hotel.service.api.ScannerRecordService;
import com.dj.seal.hotel.vo.ScannerRecordVO;
import com.dj.seal.modelFile.service.impl.ModelFileServiceImpl;
import com.dj.seal.organise.dao.impl.SysUserDaoImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelScannerRecordUtil;
import com.dj.seal.util.web.SearchForm;

public class ScannerRecordServiceImpl implements ScannerRecordService {
	
	static Logger logger  = LogManager.getLogger(ScannerRecordServiceImpl.class.getName());

	private ScannerRecordDaoImpl scannerRecordDao;
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


	
	/**
	 * 添加方法
	 */
	@Override
	public String addScannerRecord(ScannerRecordPO record) {
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
		record.setId(id);
		logger.info(record.getDeptno());
		if(record.getDeptno()==null||record.getDeptno().equals("")){
			SysUser user = usersDao.showSysUserByUser_id(record.getCreateuserid());
			if(user!=null){
				record.setDeptno(user.getDept_no());
			}
		}
		scannerRecordDao.addScannerRecord(record);
		return id;
	}

	

	public List<ScannerRecordPO> ShowScannerRecord() {
		String sql = "";
		return scannerRecordDao.getAllScannerRecordPoBy(sql);
	}


	
	public String getSqlByForm(SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ");
		sb.append(HotelScannerRecordUtil.getTABLE_NAME());
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
		return sb.toString();
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

	
	@Override
	public PageSplit showScannerRecordBySch(int pageIndex, int pageSize, SearchForm f)
	throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		logger.info("搜索关键字：：：："+f.getScannerContext());
		logger.info("selectsql:"+selectsql);
		if(f.getScannerContext()!=null&&!f.getScannerContext().trim().equals("")){
			selectsql += " and " + HotelScannerRecordUtil.getCONTEXT() + " like '%"+f.getScannerContext()+"%'";
		}
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		logger.info("关键字查询：：："+sql);
		
		List<ScannerRecordVO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = scannerRecordDao.showCount(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	private List<ScannerRecordVO> listObjs(String sql) throws Exception {
		List<ScannerRecordVO> list_obj = new ArrayList<ScannerRecordVO>();
		List<Map> list = scannerRecordDao.select(sql);
		for (Map map : list) {
			ScannerRecordPO obj = new ScannerRecordPO();
			obj =  (ScannerRecordPO) DaoUtil.setPo(obj, map, new HotelScannerRecordUtil());
			list_obj.add(poToVo(obj));
		}
		return list_obj;
	}

	public ScannerRecordPO getScannerRecord(String cid) throws Exception {
		return scannerRecordDao.getScannerRecordPOById(cid);
	}

	public ScannerRecordVO getRuleVo(String no) throws Exception {
		ScannerRecordPO obj = getScannerRecord(no);
		return poToVo(obj);
	}

	public ScannerRecordVO poToVo(ScannerRecordPO obj) throws Exception {
		ScannerRecordVO vo = new ScannerRecordVO();
		BeanUtils.copyProperties(vo, obj);
		return vo;
	}


	public SysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(SysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	@Override
	public ScannerRecordPO getScannerRecordPoBySql(String sql)
			throws DAOException {
		return scannerRecordDao.getScannerRecordPoBySql(sql);
	}

	@Override
	public List<ScannerRecordPO> getAllScannerRecordPoBy(String sql)
			throws DAOException {
		return scannerRecordDao.getAllScannerRecordPoBy(sql);
	}

	@Override
	public ScannerRecordPO getScannerRecordPOById(String id)
			throws DAOException {
		return scannerRecordDao.getScannerRecordPOById(id);
	}
	
	public ScannerRecordDaoImpl getScannerRecordDao() {
		return scannerRecordDao;
	}

	public void setScannerRecordDao(ScannerRecordDaoImpl scannerRecordDao) {
		this.scannerRecordDao = scannerRecordDao;
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

}
