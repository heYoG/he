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
import com.dj.seal.hotel.dao.impl.HotelAdvertDaoImpl;
import com.dj.seal.hotel.form.HotelAdvertForm;
import com.dj.seal.hotel.po.AdvertImagePO;
import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.hotel.service.api.IHotelAdvertService;
import com.dj.seal.hotel.vo.HotelAdvertVO;
import com.dj.seal.organise.dao.impl.SysDepartmentDaoImpl;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelAdvertUtil;
import com.dj.seal.util.web.SearchForm;

public class HotelAdvertServiceImpl implements IHotelAdvertService {
	static Logger loger = LogManager.getLogger(HotelAdvertServiceImpl.class.getName());
	private HotelAdvertDaoImpl hotelAdvertDao;
	private SysDepartmentDaoImpl sysDepartmentDao;

	public SysDepartmentDaoImpl getSysDepartmentDao() {
		return sysDepartmentDao;
	}

	public void setSysDepartmentDao(SysDepartmentDaoImpl sysDepartmentDao) {
		this.sysDepartmentDao = sysDepartmentDao;
	}

	public HotelAdvertDaoImpl getHotelAdvertDao() {
		return hotelAdvertDao;
	}

	public void setHotelAdvertDao(HotelAdvertDaoImpl hotelAdvertDao) {
		this.hotelAdvertDao = hotelAdvertDao;
	}

	@Override
	public int addAdvert(HotelAdvertPO advert) throws DAOException {
		try {
			hotelAdvertDao.addAdvert(advert);
			return 0;
		} catch (Exception e) {
			loger.info(e.getMessage());
			return 1;
		}

	}

	@Override
	public int addHotelAdvert(HotelAdvertForm advertForm) throws Exception {
		HotelAdvertPO obj = new HotelAdvertPO();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String nowTime = sf.format(date);
		String adId = sf.format(date) + "-" + UUID.randomUUID().toString();
		advertForm.setAd_ctime(Timestamp.valueOf(nowTime));
		advertForm.setAd_updatetime(Timestamp.valueOf(nowTime));
		advertForm.setAd_approvetime(Timestamp.valueOf(nowTime));
		advertForm.setAd_id(adId.replace(" ", "").replace(":", "").replace("-", "").trim());
		BeanUtils.copyProperties(obj, advertForm);
		
		obj.setAd_state("0");// ���״̬
		addAdvertImage(advertForm);//保存广告图片数据(含大数据字段)
		return addAdvert(obj);//保存广告其他数据
	}
	
	public void addAdvertImage(HotelAdvertForm advertForm) throws Exception{
		AdvertImagePO obj=new AdvertImagePO();
		obj.setAd_id(advertForm.getAd_id());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
//		String nowTime = sf.format(date);
		String[] imagenames = null;
		imagenames=advertForm.getAd_filename().split(",");//拆分每个文件单独保存一条记录
		String[] imagedatas=advertForm.getAd_advertdata().split(",");//拆分每个图片数据对应一个文件名保存
		for (int i = 0; i < imagedatas.length; i++) {
			obj.setAi_imagename(imagenames[i]);
			obj.setAi_imagedata(imagedatas[i]);
			String id=sf.format(date) + "-" + UUID.randomUUID().toString();
			id=id.replaceAll(" ", "").replace(":", "").replace("-", "").trim();
			obj.setAi_id(id);
			hotelAdvertDao.addAdvertImage(obj);
		}
		
	}
	
	public AdvertImagePO getAdvertImage(String filename) throws Exception{
		AdvertImagePO adimage=new AdvertImagePO();
		adimage=hotelAdvertDao.getAdvertImage(filename);
		return adimage;
	}

	@Override
	public int deleteAdvert(String adId) throws DAOException {
		try {
			hotelAdvertDao.deleteAdvert(adId);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

	}

	@Override
	public HotelAdvertPO getHotelAdvertById(String adId) throws DAOException {

		return hotelAdvertDao.getAdvert(adId);
	}

	public HotelAdvertVO getHotelAdvertVOById(String adId) throws Exception {

		return poToVo(hotelAdvertDao.getAdvert(adId));
	}

	@Override
	public List<HotelAdvertPO> showAllAdverts() throws DAOException {
		String sql = "select * from " + HotelAdvertUtil.TABLE_NAME;
		return hotelAdvertDao.showAllAdverts(sql);
	}
	
	@Override
	public int updateAdvert(HotelAdvertForm advertForm) throws DAOException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HotelAdvertPO obj=new HotelAdvertPO();
			if(advertForm.getAd_starttime()==null&&advertForm.getAd_endtime()==null&&advertForm.getAd_scorlltime()==null){
				advertForm.setAd_state("1");
			}else {
				advertForm.setAd_state("0");
			}
			HotelAdvertPO oldAdvert =getHotelAdvertById(advertForm.getAd_id());
			String oldVersion = oldAdvert.getAd_version();
			advertForm.setAd_version((Integer.parseInt(oldVersion)+1)+"");
			advertForm.setAd_approvetime(oldAdvert.getAd_approvetime());
			advertForm.setAd_ctime(oldAdvert.getAd_ctime());
			advertForm.setAd_updatetime(new Timestamp(new java.util.Date().getTime()));
			if(advertForm.getImagename()!=null){
				addAdvertImage(advertForm);
			}
			String imagename=getAdvertImageById(advertForm.getAd_id());
			//imagename=imagename.substring(0,imagename.length());
			advertForm.setAd_filename(imagename);
			/*}else if(imagename==null){
				advertForm.setAd_filename(advertForm.getAd_filename());
			}else{
				advertForm.setAd_filename(imagename+advertForm.getAd_filename());
			}*/
			BeanUtils.copyProperties(obj, advertForm);
			obj.setAd_dept(advertForm.getAd_dept());
			hotelAdvertDao.updateAdvert(obj);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
	public int updateAdvertstate(String state,String id) throws DAOException {
		try {
			HotelAdvertPO oldAdvert =getHotelAdvertById(id);
			String oldVersion = oldAdvert.getAd_version();
			oldAdvert.setAd_version((Integer.parseInt(oldVersion)+1)+"");
			oldAdvert.setAd_approvetime(oldAdvert.getAd_approvetime());
			oldAdvert.setAd_ctime(oldAdvert.getAd_ctime());
			oldAdvert.setAd_updatetime(new Timestamp(new java.util.Date().getTime()));
			oldAdvert.setAd_state(state);
			hotelAdvertDao.updateAdvert(oldAdvert);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

	}
	
	public String getAdvertImageById(String id) throws Exception{
		List<AdvertImagePO> list=new ArrayList<AdvertImagePO>();
		list=hotelAdvertDao.getAdvertImageById(id);
		String imageadvert="";
		if(list!=null){
			for (AdvertImagePO advertImagePO : list) {
				imageadvert=imageadvert+advertImagePO.getAi_imagename()+",";
			}
			imageadvert = imageadvert.substring(0, imageadvert.length()-1);
			return imageadvert;
		}else{
			return null;
		}
		
	}
	
	public int updateRegAdvert(HotelAdvertPO advert,String starttime,String endtime,String scorlltime) throws DAOException {
		try {
			advert.setAd_state("0");
			String start_time=starttime.toString()+" "+"00:00:00";
			String end_time=endtime.toString()+" "+"00:00:00";
			advert.setAd_starttime(Timestamp.valueOf(start_time));
			advert.setAd_endtime(Timestamp.valueOf(end_time));
			advert.setAd_scorlltime(scorlltime);
			hotelAdvertDao.updateAdvert(advert);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

	}

	public String getSqlByForm(SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(HotelAdvertUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		//System.out.println("state:"+f.getAd_state());
		if (chk(f.getAd_title())) {
			sb.append(" and ").append(HotelAdvertUtil.AD_TITLE).append(
					" like '%").append(f.getAd_title()).append("%'");
		}
		if (chk(f.getAd_filename())) {
			sb.append(" and ").append(HotelAdvertUtil.AD_FILENAME).append(
					" like '%").append(f.getAd_filename()).append("%'");
		}
		if (chk(f.getAd_dept())) {
			sb.append(" and ").append(HotelAdvertUtil.AD_DEPT).append(" like '")
					.append(f.getAd_dept()).append("%'");
		}
		if (chk(f.getAd_state()) && !f.getAd_state().equals("-1")) {
			if(f.getAd_state().equals("0")){
				sb.append(" and (").append(HotelAdvertUtil.AD_STATE).append(" = '")
				.append(f.getAd_state()).append("')");
			}else{
				sb.append(" and (").append(HotelAdvertUtil.AD_STATE).append(" = '")
					.append(f.getAd_state()).append("' or ").append(HotelAdvertUtil.AD_STATE).append(" ='3')");
			}
		}
		if (chk(f.getAd_ctime())) {
			// String sql = "select * from "
			// + HotelTRecordAffiliatedUtil.TABLE_NAME + " where "
			// + HotelTRecordAffiliatedUtil.CNAME
			// + " = 'outDate' and str_to_date( "
			// + HotelTRecordAffiliatedUtil.CVALUE
			// + ",'%Y��%m��%d��') between '" + f.getOutsTime() + "' and '"
			// + f.getOuteTime() + "' ";
			sb.append(" and ").append("str_to_date(").append(
					HotelAdvertUtil.AD_CTIME).append(",'%Y-%m-%d')").append(
					" = '").append(f.getAd_ctime()).append("'");
		}
		if(chk(f.getApprove_user())){
			if(!f.getApprove_user().equals("admin")){
				sb.append(" and ").append(HotelAdvertUtil.APPROVE_USER).append(" = '")
				.append(f.getApprove_user()).append("'");
			}
		}
		sb.append(" order by " + HotelAdvertUtil.AD_CTIME + " desc");
		return sb.toString();
	}

	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}

	public PageSplit showAdBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,Constants.DB_TYPE);
		System.out.println("showDicBySch:" + sql);
		List<HotelAdvertVO> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = hotelAdvertDao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}

	@SuppressWarnings("unchecked")
	private List<HotelAdvertVO> listObjs(String sql) throws Exception {

		List<HotelAdvertVO> list_obj;
		list_obj = new ArrayList<HotelAdvertVO>();
		List<Map> list = hotelAdvertDao.select(sql);
		for (Map map : list) {
			HotelAdvertPO obj = new HotelAdvertPO();
			obj = (HotelAdvertPO) DaoUtil.setPo(obj, map, new HotelAdvertUtil());
			list_obj.add(poToVo(obj));
		}
		return list_obj;
	}

	private HotelAdvertVO poToVo(HotelAdvertPO obj) throws Exception {

		HotelAdvertVO vo = new HotelAdvertVO();
		BeanUtils.copyProperties(vo, obj);
		String deptName = sysDepartmentDao.getDepartName(vo.getAd_dept());
		if (deptName != null) {
			vo.setAd_deptname(deptName);
		} else {
			vo.setAd_deptname("");
		}
		return vo;
	}

	public boolean checkTitle(String title) {
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelAdvertUtil.TABLE_NAME);
		sb.append(" where ").append(HotelAdvertUtil.AD_TITLE);
		sb.append(" ='").append(title).append("'");
		sb.append(" and ");
		sb.append(HotelAdvertUtil.AD_STATE);
		sb.append(" in('0','1','2')");
		//System.out.println("sb:" + sb.toString());
		int num = hotelAdvertDao.count(sb.toString());
		//System.out.println("num:" + num);
		if (num == 0) {
			return true;
		}

		return false;
	}

	public boolean checkFileName(String fileName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelAdvertUtil.TABLE_NAME);
		sb.append(" where ").append(HotelAdvertUtil.AD_FILENAME);
		sb.append(" like '%").append(fileName).append("%'");
		//System.out.println("sql:"+sb);
		int num = hotelAdvertDao.count(sb.toString());
		if (num == 0) {
			return false;
		}
		return true;
	}

	public String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type = Constants.getProperty("is_type");
			if (is_type.equals("1")) {// �������ļ���ȡ·��
				bpath = Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bpath;
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePath
	 */
	public void deleteFile(String filename,String id) {
		String[] filenames=filename.split(",");
		for (int i = 0; i < filenames.length; i++) {
			String imagename=filenames[i].toString();
			hotelAdvertDao.deleteAdvertImage(imagename, id);
		}
	}

	@Override
	public void approveAd(HotelAdvertForm adForm, String ID) {
		HotelAdvertPO advert = new HotelAdvertPO();
		advert.setAd_approvetime(new Timestamp(new java.util.Date().getTime()));
		advert.setApprove_user(adForm.getApprove_user());
		advert.setAd_opinion(adForm.getAd_opinion());
		advert.setAd_state(adForm.getAd_state());
		// dao.approveTemp(temp, t);
		hotelAdvertDao.approveAd(advert, ID);
	}

	@Override
	public List<HotelAdvertPO> getDeptAdverts(String deptno) {
		String deptParent = "";
		int mark = 4;
		String deptParentl = "";
		for (int i = 0; i < deptno.length() / mark; i++) {
			deptParent = deptno.substring(0, (i + 1) * mark);
			deptParentl += "'" + deptParent + "',";
		}
		deptParent = deptParentl.substring(0, deptParentl.length());
		List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
		List<Map> list = hotelAdvertDao.getAdvertByDept(deptParent);
		if (list.size() > 0) {
			for (Map map : list) {
				HotelAdvertPO advertPo = new HotelAdvertPO();
				advertPo = (HotelAdvertPO) DaoUtil.setPo(advertPo, map,
						new HotelAdvertUtil());
				adverts.add(advertPo);
			}
			return adverts;
		} else {
			return null;
		}
	}

	@Override
	public List<HotelAdvertPO> getBankDeptAdverts(String deptid) {
		SysDepartment dept = sysDepartmentDao.getDeptByBankId(deptid);
		if(dept == null){
			return null;
		}
		SysDepartment villageDeptNo = sysDepartmentDao.getParentNo(Constants.VILLAGE_DEPT_NO);//虚拟机构
		SysDepartment byparent_No=sysDepartmentDao.getDeptNo(dept.getDept_parent());//根据父部门编号向上查询信息	
		while(!byparent_No.getDept_parent().equals(Constants.UNIT_DEPT_NO)){//如果父部门编号不是最上级继续按查出来的父部门编号查询
			byparent_No=sysDepartmentDao.getDeptNo(byparent_No.getDept_parent());//父部门编号作为部门编号查询
		}
		String deptno = dept.getDept_no();
		String deptParent = "";
		int mark = 4;
		String deptParentl = "";
		for (int i = 0; i < deptno.length() / mark; i++) {
			deptParent = deptno.substring(0, (i + 1) * mark);
			deptParentl += "'" + deptParent + "',";
		}	
		if (!"01".equals(byparent_No.getBank_dept())){//村镇银行新增虚拟机构判断是否有广告
			deptParentl+="'"+villageDeptNo.getDept_no()+"',";
		}	
		deptParent = deptParentl.substring(0, deptParentl.length() - 1);
		//System.out.println("deptParent:"+deptParent);
		List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
		List<Map> list = hotelAdvertDao.getAdvertByDept(deptParent);
		if (list.size() > 0) {
			for (Map map : list) {
				HotelAdvertPO advertPo = new HotelAdvertPO();
				advertPo = (HotelAdvertPO) DaoUtil.setPo(advertPo, map,
						new HotelAdvertUtil());
				adverts.add(advertPo);
			}
			return adverts;
		} else {
			return getDefaultAdvert();
		}
	}
	
	@Override
	public List<HotelAdvertPO> getDefaultAdvert(){
		List<HotelAdvertPO> adverts = new ArrayList<HotelAdvertPO>();
		HotelAdvertPO advertPo = new HotelAdvertPO();
		Timestamp sTime = new Timestamp(System.currentTimeMillis()-1000*60*10);
		advertPo.setAd_filename("defaultAdvertOne.jpg,defaultAdvertTwo.jpg,defaultAdvertThree.jpg,defaultAdvertFour.jpg");
		advertPo.setAd_starttime(sTime);
		advertPo.setAd_endtime(new Timestamp(sTime.getTime()+1000*60*60*24));
		advertPo.setAd_dept(Constants.UNIT_DEPT_NO);
		advertPo.setAd_title("默认广告");
		advertPo.setAd_version("0");
		adverts.add(advertPo);
		return adverts;
	}
}
