package com.dj.seal.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.MasterplatejudgeDaoImpl;
import com.dj.seal.hotel.form.MasterplatejudgeForm;
import com.dj.seal.hotel.po.Masterplatejudgep;
import com.dj.seal.hotel.service.api.MasterplatejudgeService;
import com.dj.seal.hotel.vo.Masterplatejudgev;
import com.dj.seal.modelFile.vo.ModelFileVo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTmasterplateJudgeUtil;
import com.dj.seal.util.table.ModelFileUtil;
import com.dj.seal.util.web.SearchForm;

public class MasterplatejudgeServiceImpl implements MasterplatejudgeService {
	
	static Logger logger = LogManager.getLogger(MasterplatejudgeServiceImpl.class.getName());
	
	private MasterplatejudgeDaoImpl masterplatejudgeDao;
	private VersionServiceImpl version_srv;

	public MasterplatejudgeDaoImpl getMasterplatejudgeDao() {
		return masterplatejudgeDao;
	}

	public void setMasterplatejudgeDao(MasterplatejudgeDaoImpl masterplatejudgeDao) {
		this.masterplatejudgeDao = masterplatejudgeDao;
	}

	/**
	 * 判断属性名是否重复
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isNameExist(String name) throws Exception{
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ").append(HotelTmasterplateJudgeUtil.TABLE_NAME);
		sb.append(" where ").append(HotelTmasterplateJudgeUtil.C_NAME);
		sb.append(" ='").append(name).append("'");
		int num = masterplatejudgeDao.count(sb.toString());
		
		
		if(num==0){
			return false;
		}
		
		return true;
		
	}
	@Override
	public int addMasterplatejudge(Masterplatejudgep mj) throws DAOException {
		try {
			if(mj.getC_valuetype()!=null&&!mj.getC_valuetype().equals("")){
				if(mj.getC_valuetype().equals("1")){
					mj.setC_value("DJ_UNEMPTY_VALUE");//非空特定字符串
				}else if(mj.getC_valuetype().equals("2")){
					mj.setC_value("DJ_EMPTY_VALUE");//空特定字符串
				}
			}
			masterplatejudgeDao.addMasterplatejudge(mj);
			version_srv.update(mj.getMaster_platecid());
			return 1;
		} catch (Exception e) {
//			logger.error(e.getMessage());
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public int addMasterplatejudge(MasterplatejudgeForm f) throws Exception {
		Masterplatejudgep mj = new Masterplatejudgep();
		BeanUtils.copyProperties(mj, f);
		mj.setC_id(masterplatejudgeDao.getNo(HotelTmasterplateJudgeUtil.TABLE_NAME, HotelTmasterplateJudgeUtil.C_ID));
		version_srv.update(f.getMaster_platecid());
		return addMasterplatejudge(mj);
	}

	@Override
	public int delMasterplatejudge(String cid) throws DAOException {
		try {
			Masterplatejudgep mj = getMasterplatejudge(cid);
			masterplatejudgeDao.delMasterplatejudge(cid);
			version_srv.update(mj.getMaster_platecid());
			return 1;
		} catch (Exception e) {
			return 0;
		}
	
	}

	@Override
	public Masterplatejudgep getMasterplatejudge(String cid)
			throws DAOException {
		// TODO Auto-generated method stub
		return masterplatejudgeDao.getMasterplatejudge(cid);
	}

	public Masterplatejudgev getMasterplatejudgeVo(String cid)
			throws Exception {
		// TODO Auto-generated method stub
		Masterplatejudgep obj = masterplatejudgeDao.getMasterplatejudge(cid);
		if(obj==null){
			return null;
		}
		return toVo(obj);
	}
	
	@Override
	public List<Masterplatejudgep> showMasterplatejudges() throws Exception {
		String sql = "";
		
		return masterplatejudgeDao.showAllMasterplatejudge(sql);
	}

	public List<Masterplatejudgep> getJudgesByMasterplateId(String masterplateid) throws Exception {
		String sql = "select * from "+HotelTmasterplateJudgeUtil.TABLE_NAME+" where "+HotelTmasterplateJudgeUtil.MASTER_PLATECID+"='"+masterplateid+"'";
		return masterplatejudgeDao.showAllMasterplatejudge(sql);
	}
	
	public List<Masterplatejudgep> getJudgesByMasterplateList(List<ModelFileVo> mfList) throws Exception {
		if(mfList.size()==0){
			return null;
		}
		String sql = "select * from "+HotelTmasterplateJudgeUtil.TABLE_NAME+" where "+HotelTmasterplateJudgeUtil.MASTER_PLATECID+" in(";
		for(ModelFileVo vo : mfList){
			sql+="'"+vo.getModel_id()+"',";
		}
		sql+="'')";
		return masterplatejudgeDao.showAllMasterplatejudge(sql);
	}
	
	@Override
	public List<Masterplatejudgep> shwoMasterplatejudgeById(String cid)
			throws DAOException {
		String sql = "select * from " +HotelTmasterplateJudgeUtil.getTABLE_NAME()
		+" where "+HotelTmasterplateJudgeUtil.getC_ID()+" in("+cid+")";
		return masterplatejudgeDao.showAllMasterplatejudge(sql);
	}

	@Override
	public int updateMasterplatejudge(Masterplatejudgep mjp)
			throws DAOException {
		try {
			if(mjp.getC_valuetype()!=null&&!mjp.getC_valuetype().equals("")){
				if(mjp.getC_valuetype().equals("1")){
					mjp.setC_value("DJ_UNEMPTY_VALUE");//非空特定字符串
				}else if(mjp.getC_valuetype().equals("2")){
					mjp.setC_value("DJ_EMPTY_VALUE");//空特定字符串
				}
			}
			masterplatejudgeDao.updateMasterplatejudge(mjp);
			version_srv.update(mjp.getMaster_platecid());
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}
	public String getSqlByForm(SearchForm f){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(HotelTmasterplateJudgeUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if(chk(f.getC_name())){
			sb.append(" and ").append(HotelTmasterplateJudgeUtil.C_NAME).append(" like '%").append(f.getC_name()).append("%'");
		}
		if(chk(f.getC_value())){
			sb.append(" and ").append(HotelTmasterplateJudgeUtil.C_VALUE).append(" like '%").append(f.getC_value()).append("%'");
		}
		//if(chk(f.getMaster_platecid())){
		//	sb.append(" and ").append(HotelTmasterplateJudgeUtil.MASTER_PLATECID).append(" like '%").append(f.getMaster_platecid()).append("%'");
		//}
		return sb.toString();
	}
	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}

	@SuppressWarnings("unchecked")
	public PageSplit showJudBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
//		System.out.println("showDicBySch:"+sql);
		List<Masterplatejudgev> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = masterplatejudgeDao.count(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}
	@SuppressWarnings("unchecked")
	private List<Masterplatejudgev> listObjs(String sql) throws Exception {
		List<Masterplatejudgev> list_obj = new ArrayList<Masterplatejudgev>();
		List<Map> list = masterplatejudgeDao.select(sql);
		for (Map map : list) {
			Masterplatejudgep obj = new Masterplatejudgep();
			obj = (Masterplatejudgep) DaoUtil.setPo(obj, map, new HotelTmasterplateJudgeUtil());
			Masterplatejudgev vo = new Masterplatejudgev();
			BeanUtils.copyProperties(vo, obj);
			StringBuffer sb=new StringBuffer();
			sb.append("select ").append(ModelFileUtil.MODEL_NAME).append(" from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" where ").append(ModelFileUtil.MODEL_ID).append("=").append(vo.getMaster_platecid());
			String name = masterplatejudgeDao.getStr(sb.toString(), ModelFileUtil.MODEL_NAME);
			vo.setMasterplate_name(name);
			list_obj.add(vo);
		}
		return list_obj;
	}
	
	private Masterplatejudgev toVo(Masterplatejudgep obj) throws Exception {
			Masterplatejudgev vo = new Masterplatejudgev();
			BeanUtils.copyProperties(vo, obj);
			StringBuffer sb=new StringBuffer();
			sb.append("select ").append(ModelFileUtil.MODEL_NAME).append(" from ").append(ModelFileUtil.TABLE_NAME);
			sb.append(" where ").append(ModelFileUtil.MODEL_ID).append("=").append(vo.getMaster_platecid());
			String name = masterplatejudgeDao.getStr(sb.toString(), ModelFileUtil.MODEL_NAME);
			vo.setMasterplate_name(name);
			return vo;
	}
	
	
	/**
	 * 批量删除
	 * @param selStr
	 * @throws Exception
	 */
	public void plDelJud(String selStr) throws Exception {
		System.out.println(selStr+"字符串");
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(HotelTmasterplateJudgeUtil.TABLE_NAME).append(" where 1=2 ");
		for (String str : r_nos) {
			sb.append(" or ").append(HotelTmasterplateJudgeUtil.C_ID);
			sb.append("='").append(str).append("'");
		}
		masterplatejudgeDao.delete(sb.toString());
	}

	public VersionServiceImpl getVersion_srv() {
		return version_srv;
	}

	public void setVersion_srv(VersionServiceImpl version_srv) {
		this.version_srv = version_srv;
	}

}
