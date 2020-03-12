package com.dj.seal.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.HotelTDicHotelUtilImpl;
import com.dj.seal.hotel.form.HotelTDicForm;
import com.dj.seal.hotel.po.HotelTDic;
import com.dj.seal.hotel.service.api.IHotelTDicHotelUtilService;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTDicHotelUtil;
import com.dj.seal.util.web.SearchForm;

public class HotelTDicHotelUtilServiceImpl implements
		IHotelTDicHotelUtilService {
	
	static Logger logger = LogManager.getLogger(HotelTDicHotelUtilServiceImpl.class.getName());
	
	 private HotelTDicHotelUtilImpl hotelTDicDao ;


	public HotelTDicHotelUtilImpl getHotelTDicDao() {
		return hotelTDicDao;
	}

	public void setHotelTDicDao(HotelTDicHotelUtilImpl hotelTDicDao) {
		this.hotelTDicDao = hotelTDicDao;
	}
	
	
	
	/**
	 * 判断名称是否重复
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isNameExist(String name) throws Exception{
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelTDicHotelUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTDicHotelUtil.CNAME);
		sb.append(" ='").append(name).append("'");
		int num = hotelTDicDao.count(sb.toString());
		
		
		if(num==0){
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 判断显示名称是否重复
	 * @param showName
	 * @return
	 * @throws Exception
	 */
	public boolean isShowNameExist(String showName) throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelTDicHotelUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTDicHotelUtil.CSHOWNAME);
		sb.append(" ='").append(showName).append("'");
		int num = hotelTDicDao.count(sb.toString());
		
		if(num==0){
			return false;
		}
		return true;
	}
	
	

	@Override
	public int addHotelDic(HotelTDic obj) throws DAOException {
	
		try {
			hotelTDicDao.addHotelTDicHotelUtil(obj);
			return 1;
		} catch (Exception e) {
//			logger.error(e.getMessage());
			logger.error(e.getMessage());
			return 0;
		}
		
	}
	
	
	@Override
	public int addHotelTDic(HotelTDicForm f) throws Exception{
		
		HotelTDic obj = new HotelTDic();

		
		BeanUtils.copyProperties(obj, f);
		obj.setSys("1");
		obj.setCid(hotelTDicDao.getNo(HotelTDicHotelUtil.TABLE_NAME, HotelTDicHotelUtil.CID));
		return addHotelDic(obj);
		
	}

	
	@Override
	public int delHotelDic(String cid) throws DAOException {
		try {
	
			hotelTDicDao.delHotelDicHotelUtil(cid);
			return 1;
		} catch (Exception e) {

			return 0;
		}
	
	}
	
	public int regSysData(String data){
		
		return 0;
		
	}

	
	@Override
	public HotelTDic getHotelDic(String cid) throws DAOException {

		return hotelTDicDao.getHotelDicHotelUtil(cid);
	}

	
	@Override
	public List<HotelTDic> showHotelDics() throws Exception {

		String sql = "select * from "+HotelTDicHotelUtil.TABLE_NAME;
		
		return hotelTDicDao.showAllHotelDicHotelUtil(sql);
	}

	//获取页面上需显示的字典项，即c_status为0显示
	public List<HotelTDic> getPageShowHotelDics() throws Exception {
		String sql = "select * from "+HotelTDicHotelUtil.TABLE_NAME
					+" where "+HotelTDicHotelUtil.C_STATUS+"='0'"+" order by "+HotelTDicHotelUtil.C_SORT;
		return hotelTDicDao.showAllHotelDicHotelUtil(sql);
	}
	

	@Override
	public List<HotelTDic> shwoHotelDicById(String cid) throws DAOException {

		String sql = "select * from " + HotelTDicHotelUtil.TABLE_NAME
			+" where "+HotelTDicHotelUtil.CID+" in("+cid+")";
		return hotelTDicDao.showAllHotelDicHotelUtil(sql);
	}


	@Override
	public int updateHotelDic(HotelTDic obj) throws DAOException {
		try {

			hotelTDicDao.updateHotelDicHotelUtil(obj);
			return 0;
		} catch (Exception e) {
		
			return 1;
		}
	}
	
	
	public String getSqlByForm(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(HotelTDicHotelUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if(chk(f.getCname())){
			sb.append(" and ").append(HotelTDicHotelUtil.CNAME).append(" like '%").append(f.getCname()).append("%'");
		}
		if(chk(f.getCshowname())){
			sb.append(" and ").append(HotelTDicHotelUtil.CSHOWNAME).append(" like '%").append(f.getCshowname()).append("%'");
		}
		if(chk(f.getCdatatype())){
			sb.append(" and ").append(HotelTDicHotelUtil.CDATATYPE).append(" like '%").append(f.getCdatatype()).append("%'");
		}
		sb.append(" order by c_status,c_sort asc");
		return sb.toString();
	}
	
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}
	
	
	public PageSplit showDicBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		
		
		System.out.println("showDicBySch:"+sql);
	
		List<HotelTDic> datas = listObjs(sql);

		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = hotelTDicDao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	@SuppressWarnings("unchecked")
	private List<HotelTDic> listObjs(String sql) throws Exception {


		List<HotelTDic> list_obj;
		list_obj = new ArrayList<HotelTDic>();
		List<Map> list = hotelTDicDao.select(sql);
	
		for (Map map : list) {
			HotelTDic obj = new HotelTDic();
			obj = (HotelTDic) DaoUtil
					.setPo(obj, map, new HotelTDicHotelUtil());	
			list_obj.add(obj);
		}
		return list_obj;
	}
	
	/**
	 * 批量删除
	 * @param selStr
	 * @throws Exception
	 */
	public void plDelDic(String selStr) throws Exception {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(HotelTDicHotelUtil.TABLE_NAME).append(" where 1=2 ");
		for (String str : r_nos) {
			sb.append(" or ").append(HotelTDicHotelUtil.CID);
			sb.append("='").append(str).append("'");
		}
		hotelTDicDao.delete(sb.toString());
	}

	@Override
	public void changeStatusIsBlock(String id) {
		try {
			hotelTDicDao.changeDicIsBlock(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void changeStatusIsDisplay(String id) {
		try {
			hotelTDicDao.changeDicIsDisplay(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public boolean isExistSort(int sort){
		
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelTDicHotelUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTDicHotelUtil.C_SORT);
		sb.append(" =").append(sort);
		
		int num = hotelTDicDao.count(sb.toString());
		
		
		if(num==0){
			return false;
		}
		
		return true;
	
	}
	

}
