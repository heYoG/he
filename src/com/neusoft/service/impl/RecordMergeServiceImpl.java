/**    
 * @{#} RecordMergeServiceImpl.java Create on 2015-7-20 上午09:30:10    
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

import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.web.SearchForm;
import com.neusoft.dao.impl.RecordMergeDaoImpl;
import com.neusoft.po.RecordMergePO;
import com.neusoft.service.RecordMergeService;
import com.neusoft.util.date.DateTimeUtil;
import com.neusoft.util.file.PDFUtil;
import com.neusoft.util.table.HotelRecordMergeUtil;
import com.neusoft.vo.RecordMergeVO;

/**    
 * 单据合并Service实现类
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class RecordMergeServiceImpl implements RecordMergeService {
	static Logger logger = LogManager.getLogger(RecordMergeServiceImpl.class.getName());
	private RecordMergeDaoImpl recordMergeDao;
	
	
	public RecordMergeDaoImpl getRecordMergeDao() {
		return recordMergeDao;
	}

	public void setRecordMergeDao(RecordMergeDaoImpl recordMergeDao) {
		this.recordMergeDao = recordMergeDao;
	}

	/**
	 * 添加单据合并数据
	 * @param recordMergePO
	 * @return
	 * @author 陈钜珩
	 * Create on 2015-7-20 上午09:38:58
	 */
	@Override
	public String addRecordMerge(RecordMergePO recordMergePO) {
		// TODO Auto-generated method stub
		try {
			Date yesterday = DateTimeUtil.dateAdd(DateTimeUtil.getCurDate(), "D", -1);
			String year = DateTimeUtil.formatDateTime(yesterday, "yyyy");
			String month = DateTimeUtil.formatDateTime(yesterday, "MM");
			if (month.startsWith("0")) {
				month = month.substring(1, month.length());
			}
			String date = DateTimeUtil.formatDateTime(yesterday, "dd");
			if (date.startsWith("0")) {
				date = date.substring(1, date.length());
			}
			String hotelFileSavePath = Constants.getProperty("hotelFileSavePath");
			String dirPath = hotelFileSavePath + year + "/" + month + "/" + date;
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files != null) {
				List<InputStream> pdfList = new ArrayList<InputStream>();
				for (int i = 0; i < files.length; i++) {
					File pdfFile = files[i];
					if (pdfFile.isDirectory()) {
						continue;
					}
					String fileName = pdfFile.getName();
					int index = fileName.indexOf(".");
					if (index != -1) {
						String extName = fileName.substring(index, fileName.length()).toLowerCase();
						if (".pdf".equals(extName)) {
							pdfList.add(new FileInputStream(dirPath + "/" + fileName));
						}
					}
				}
				
				if (!pdfList.isEmpty()) {
					String mergeFileName = "merge_" + year + month + date + ".pdf";
					String mergeSaveFileName = "merge" + "/" + year + "/" + month + "/" + mergeFileName;
					String outoutPath = hotelFileSavePath + "merge" + "/" + year + "/" + month;
					File outoutFolder = new File(outoutPath);
					if (!outoutFolder.exists() && !outoutFolder.isDirectory()) {
						outoutFolder.mkdirs();
					}
					OutputStream output = new FileOutputStream(outoutPath + "/" + mergeFileName);
					PDFUtil.mergePDFs(pdfList, output, true);
					
					SimpleDateFormat time = new SimpleDateFormat("yyyyMMdd"); 
					String id = time.format(new Date()) + "-" + UUID.randomUUID().toString();
					recordMergePO.setCid(id);
					recordMergePO.setMergedate(DateTimeUtil.formatDateTime(yesterday, "yyyy-MM-dd"));
					recordMergePO.setFilename(mergeFileName);
					recordMergePO.setSavefilename(mergeSaveFileName);
					recordMergePO.setCreatetime(new Timestamp(new Date().getTime()));
					recordMergeDao.add(recordMergePO);
					
					return id;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		return  null;
	}

	@Override
	public PageSplit getRecordMergeList(int pageIndex, int pageSize, SearchForm searchForm) throws Exception {
		// TODO Auto-generated method stub
		String selectSql = getSqlByForm(searchForm);
		String sql = DBTypeUtil.splitSql(selectSql, pageIndex, pageSize, Constants.DB_TYPE);
		List<RecordMergeVO> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordMergeDao.showCount(selectSql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	 
	private String getSqlByForm(SearchForm searchForm) {
		StringBuffer sb = new StringBuffer();
		String dateType = searchForm.getDate_type();
		if (dateType == null || dateType.equals("1")) { // 昨天
			Date yesterday = DateTimeUtil.dateAdd(DateTimeUtil.getCurDate(), "D", -1);
			String date = DateTimeUtil.formatDateTime(yesterday, "yyyy-MM-dd");
			sb.append("select * from ").append(HotelRecordMergeUtil.TABLE_NAME);
			sb.append(" where ").append(HotelRecordMergeUtil.MERGEDATE).append(" = '").append(date).append("'");
			sb.append(" order by ").append(HotelRecordMergeUtil.MERGEDATE).append(" desc");
		} else if (dateType.equals("2")) { // 当月
			String curMonth = DateTimeUtil.formatDateTime(new Date(), "yyyy-MM");
			sb.append("select * from ").append(HotelRecordMergeUtil.TABLE_NAME);
			sb.append(" where substring(").append(HotelRecordMergeUtil.MERGEDATE).append(", 1, 7) = '").append(curMonth).append("'");
			sb.append(" order by ").append(HotelRecordMergeUtil.MERGEDATE).append(" desc");
		} else if (dateType.equals("7")) { // 自定义
			String beginDate = searchForm.getBegin_time();
			String endDate = searchForm.getEnd_time();
			sb.append("select * from ").append(HotelRecordMergeUtil.TABLE_NAME);
			sb.append(" where ").append(HotelRecordMergeUtil.MERGEDATE).append(" >= '").append(beginDate).append("'");
			sb.append(" and ").append(HotelRecordMergeUtil.MERGEDATE).append(" <= '").append(endDate).append("'");
			sb.append(" order by ").append(HotelRecordMergeUtil.MERGEDATE).append(" desc");
		}
		
		return sb.toString();
	}
	
	private List<RecordMergeVO> listObjs(String sql) throws Exception {
		List<RecordMergeVO> voList = new ArrayList<RecordMergeVO>();
		List<Map> list = recordMergeDao.select(sql);
		for (Map map : list) {
			RecordMergePO po = new RecordMergePO();
			po = (RecordMergePO)DaoUtil.setPo(po, map, new HotelRecordMergeUtil());
			voList.add(poToVo(po));
		}
		
		return voList;
	}
	
	private RecordMergeVO poToVo(RecordMergePO po) throws Exception {
		RecordMergeVO vo = new RecordMergeVO();
		BeanUtils.copyProperties(vo, po);
		return vo;
	}
}

