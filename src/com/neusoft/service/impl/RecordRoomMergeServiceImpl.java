/**    
 * @{#} RecordRoomMergeServiceImpl.java Create on 2015-7-20 上午09:30:10    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.util.CollectionUtils;

import com.dj.seal.hotel.dao.impl.RecordDaoImpl;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.web.SearchForm;
import com.neusoft.dao.impl.RecordRoomMergeDaoImpl;
import com.neusoft.po.RecordRoomMergePO;
import com.neusoft.service.RecordRoomMergeService;
import com.neusoft.util.date.DateTimeUtil;
import com.neusoft.util.file.PDFUtil;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;
import com.neusoft.vo.RecordRoomMergeVO;

/**    
 * 房间单据合并Service实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class RecordRoomMergeServiceImpl implements RecordRoomMergeService {
	static Logger logger = LogManager.getLogger(RecordRoomMergeServiceImpl.class.getName());
	private RecordRoomMergeDaoImpl recordRoomMergeDao;
	private RecordDaoImpl recordDao;
	private TableMonthConfigServiceImpl tableMonthConfigService;
	
	public RecordRoomMergeDaoImpl getRecordRoomMergeDao() {
		return recordRoomMergeDao;
	}

	public void setRecordRoomMergeDao(RecordRoomMergeDaoImpl recordRoomMergeDao) {
		this.recordRoomMergeDao = recordRoomMergeDao;
	}

	public RecordDaoImpl getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDaoImpl recordDao) {
		this.recordDao = recordDao;
	}

	public TableMonthConfigServiceImpl getTableMonthConfigService() {
		return tableMonthConfigService;
	}

	public void setTableMonthConfigService(
			TableMonthConfigServiceImpl tableMonthConfigService) {
		this.tableMonthConfigService = tableMonthConfigService;
	}

	/**
	 * 处理离店单据，并和入住单据进行合并
	 * @param checkOutRecordid 离店单据id
	 * @author 陈钜珩
	 * Create on 2015-7-21 上午10:05:12
	 */
	@Override
	public void handleRoomCheckOut(String checkOutRecordid) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		List<String> hoteltrecordaffiliatedList = tableMonthConfigService.getTableMonthNameList("t_hoteltrecordaffiliated");
		sb.append("select distinct t.*  from (");
		for (int i = 0; i < hoteltrecordaffiliatedList.size(); i++) {
			if (i > 0) {
				sb.append(" UNION ALL ");
			}
			sb.append("select * from ").append(hoteltrecordaffiliatedList.get(i)).append(" where RECORDID = '").append(checkOutRecordid).append("'");
		}
		sb.append(") t ");
		
		List list = recordRoomMergeDao.select(sb.toString());
		String roomno = null;
		String guestname = null;
		if (!CollectionUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				String cname = (String)map.get("CNAME");
				if ("roomno".equals(cname)) {
					roomno = (String)map.get("CVALUE");
				} else if ("guestname".equals(cname)) {
					guestname = (String)map.get("CVALUE");
				}
			}
		}
		if (roomno != null && guestname != null) {
			// 获取离店单据对应的入住单据
			String checkInRecordid = null; // 入住单据id
			String checkInRecordPath = null; // 入住单据相对路径
			String checkOutRecordPath = null; // 离店单据相对路径
			Timestamp checkOutRecordTime = null; // 离店单据创建时间
			String checkInModelId = Constants.getProperty("check_in_model_id");
			sb = new StringBuffer();
			List<String> hotelrecordList = tableMonthConfigService.getTableMonthNameList("t_hotelrecord");
			sb.append("select distinct t.*  from (");
			for (int i = 0; i < hoteltrecordaffiliatedList.size(); i++) {
				if (i > 0) {
					sb.append(" UNION ALL ");
				}
				sb.append("select * from ").append(hotelrecordList.get(i)).append(" r, ").append(hoteltrecordaffiliatedList.get(i)).append(" a");
				sb.append(" where r.cid = a.RECORDID and r.mtplId = '").append(checkInModelId).append("'");
				sb.append(" and (a.CNAME = 'roomno' and a.CVALUE = '").append(roomno).append("' or a.CNAME = 'guestname' and a.CVALUE = '").append(guestname).append("')");
			}
			sb.append(") t order by t.ccreateTime desc");
			list = recordRoomMergeDao.select(sb.toString());
			if (!CollectionUtils.isEmpty(list)) {
				Map map = (Map)list.get(0);
				checkInRecordid = (String)map.get("cid");
				checkInRecordPath = (String)map.get("saveFileName");
			}
			if (checkInRecordid != null) {		
				sb = new StringBuffer();
				sb.append("select distinct t.*  from (");
				for (int i = 0; i < hotelrecordList.size(); i++) {
					if (i > 0) {
						sb.append(" UNION ALL ");
					}
					sb.append("select * from ").append(hotelrecordList.get(i)).append(" where cid = '").append(checkOutRecordid).append("'");
				}
				sb.append(") t ");
				list = recordRoomMergeDao.select(sb.toString());
				if (!CollectionUtils.isEmpty(list)) {
					Map map = (Map)list.get(0);
					checkOutRecordPath = (String)map.get("saveFileName");
					checkOutRecordTime = (Timestamp)map.get("ccreateTime");
				}

				String hotelFileSavePath = Constants.getProperty("hotelFileSavePath");
				List<InputStream> pdfList = new ArrayList<InputStream>();
				pdfList.add(new FileInputStream(hotelFileSavePath + checkInRecordPath));
				pdfList.add(new FileInputStream(hotelFileSavePath + checkOutRecordPath));

				Date curDate = new Date(checkOutRecordTime.getTime());
				String year = DateTimeUtil.formatDateTime(curDate, "yyyy");
				String month = DateTimeUtil.formatDateTime(curDate, "MM");
				if (month.startsWith("0")) {
					month = month.substring(1, month.length());
				}
				String date = DateTimeUtil.formatDateTime(curDate, "dd");
				if (date.startsWith("0")) {
					date = date.substring(1, date.length());
				}
				String mergeFileName = "mergeroom_" + guestname + "_" + roomno + "_" + DateTimeUtil.getCurDateTime("yyyyMMddHHmmss") + ".pdf";
				String mergeSaveFileName = "mergeroom" + "/" + year + "/" + month + "/" + date + "/" + mergeFileName;
				String outoutPath = hotelFileSavePath + "mergeroom" + "/" + year + "/" + month + "/" + date;
				File outoutFolder = new File(outoutPath);
				if (!outoutFolder.exists() && !outoutFolder.isDirectory()) {
					outoutFolder.mkdirs();
				}
				OutputStream output = new FileOutputStream(outoutPath + "/" + mergeFileName);
				PDFUtil.mergePDFs(pdfList, output, true); // 入住和离店单据合并
				
				SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd"); 
				String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
				RecordRoomMergePO recordRoomMergePO = new RecordRoomMergePO();
				recordRoomMergePO.setCid(id);
				recordRoomMergePO.setGuestname(guestname);
				recordRoomMergePO.setRoomno(roomno);
				recordRoomMergePO.setMergedate(DateTimeUtil.formatDateTime(curDate, "yyyy-MM-dd"));
				recordRoomMergePO.setFilename(mergeFileName);
				recordRoomMergePO.setSavefilename(mergeSaveFileName);
				recordRoomMergePO.setCreatetime(new Timestamp(new Date().getTime()));
				recordRoomMergeDao.addNew(recordRoomMergePO);
			}
		}
	}

	@Override
	public PageSplit getRecordRoomMergeList(int pageIndex, int pageSize, SearchForm searchForm) throws Exception {
		// TODO Auto-generated method stub		
		String selectSql = getSqlByForm(searchForm);
		String sql = DBTypeUtil.splitSql(selectSql, pageIndex, pageSize, Constants.DB_TYPE);
		List<RecordRoomMergeVO> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordRoomMergeDao.showCount(selectSql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	 
	private String getSqlByForm(SearchForm searchForm) {
		StringBuffer sb = new StringBuffer();
		String dateType = searchForm.getDate_type();
		Date curDate = new Date();
		String yearMonth = new SimpleDateFormat("yyyyMM").format(curDate);
		if (dateType == null || dateType.equals("1")) { // 当天
			String date = DateTimeUtil.formatDateTime(curDate, "yyyy-MM-dd");
			sb.append("select * from ").append(HotelRecordRoomMergeUtil.TABLE_NAME).append(yearMonth);
			sb.append(" where ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" = '").append(date).append("'");
			sb.append(" order by ").append(HotelRecordRoomMergeUtil.ROOMNO).append(", ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" desc");
		} else if (dateType.equals("2")) { // 当月
			String curMonth = DateTimeUtil.formatDateTime(new Date(), "yyyy-MM");
			sb.append("select * from ").append(HotelRecordRoomMergeUtil.TABLE_NAME).append(yearMonth);
			sb.append(" where substring(").append(HotelRecordRoomMergeUtil.MERGEDATE).append(", 1, 7) = '").append(curMonth).append("'");
			sb.append(" order by ").append(HotelRecordRoomMergeUtil.ROOMNO).append(", ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" desc");
		} else if (dateType.equals("7")) { // 自定义
			String beginDate = searchForm.getBegin_time();
			String endDate = searchForm.getEnd_time();
			String[] arr = beginDate.split("-");
			String bYear = arr[0];  // 开始年
			String bMonth = arr[1]; // 开始月
			String bDate = arr[2];  // 开始日
			arr = endDate.split("-");
			String eYear = arr[0];  // 结束年
			String eMonth = arr[1]; // 结束月
			String eDate = arr[2];  // 结束日
			
			if (yearMonth.equals(bYear + bMonth) && yearMonth.equals(eYear + eMonth)) { // 当月
				sb.append("select * from ").append(HotelRecordRoomMergeUtil.TABLE_NAME).append(yearMonth);
				sb.append(" where ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" >= '").append(beginDate).append("'");
				sb.append(" and ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" <= '").append(endDate).append("'");
				sb.append(" order by ").append(HotelRecordRoomMergeUtil.ROOMNO).append(", ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" desc");
			} else { // 跨年月
				List<String> tableNameList = new ArrayList<String>();
				String tempMonth = "";
				for (int i = Integer.valueOf(bYear); i <= Integer.valueOf(eYear); i++) {
					int start = 0;
					int end = 0;
					if (i < Integer.valueOf(eYear)) {
						start = Integer.valueOf(bMonth);
						end = 12; // 12月
					} else {
						start = 1; // 1月
						end = Integer.valueOf(eMonth);
					}
					for (int j = start; j <= end; j++) {
						if (j < 10) {
							tempMonth = "0" + j;
						} else {
							tempMonth = String.valueOf(j);
						}
						tableNameList.add(HotelRecordRoomMergeUtil.TABLE_NAME + i + tempMonth);
					}
				}
				if (!CollectionUtils.isEmpty(tableNameList)) {
					sb.append("select distinct t.*  from (");
					StringBuffer subSb = new StringBuffer();
					for (int i = 0; i < tableNameList.size(); i++) {
						try {
							recordRoomMergeDao.select("select * from " + tableNameList.get(i) + " where 1=0"); // 测试表名是否存在
							if (i > 0 && subSb.length() > 0) {
								subSb.append(" UNION ALL ");
							}
							subSb.append("select * from ").append(HotelRecordRoomMergeUtil.TABLE_NAME).append(yearMonth);
							subSb.append(" where ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" >= '").append(beginDate).append("'");
							subSb.append(" and ").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" <= '").append(endDate).append("'");
						} catch (Exception e) {
							continue;
						}
					}
					sb.append(subSb.toString());
					sb.append(") t order by t.").append(HotelRecordRoomMergeUtil.ROOMNO).append(", t.").append(HotelRecordRoomMergeUtil.MERGEDATE).append(" desc");
				}
			}
		}
		
		return sb.toString();
	}
	
	private List<RecordRoomMergeVO> listObjs(String sql) throws Exception {
		List<RecordRoomMergeVO> voList = new ArrayList<RecordRoomMergeVO>();
		List<Map> list = recordRoomMergeDao.select(sql);
		for (Map map : list) {
			RecordRoomMergePO po = new RecordRoomMergePO();
			po = (RecordRoomMergePO)DaoUtil.setPo(po, map, new HotelRecordRoomMergeUtil());
			voList.add(poToVo(po));
		}
		
		return voList;
	}
	
	private RecordRoomMergeVO poToVo(RecordRoomMergePO po) throws Exception {
		RecordRoomMergeVO vo = new RecordRoomMergeVO();
		BeanUtils.copyProperties(vo, po);
		return vo;
	}
}

