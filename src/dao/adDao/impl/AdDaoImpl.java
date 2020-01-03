package dao.adDao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.adDao.api.IAdDao;
import util.BaseDao;
import util.CommenClass;
import util.TableManager;
import vo.adVo.AdVo;

public class AdDaoImpl extends BaseDao implements IAdDao {

	AdVo av = new AdVo();

	@Override
	public int newAd(AdVo adVo) {
		String sql = "insert into " + TableManager.ADTABLE + " values(null,?,?,?,?,?,?)";
		int i = executeUpdate(sql, adVo.getAdName(), adVo.getSaveName(), adVo.getAdSize(), adVo.getUploadtime(),
				adVo.getOperator(),adVo.getUser_id());
		return i;
	}

	@Override
	public List<AdVo> getAds(CommenClass cc) {
		String sql = null;
		List<AdVo> list = new ArrayList<AdVo>();
		ResultSet rs = null;
		try {
			sql = "select t2.* from " + TableManager.USERTABLE + " t1," + TableManager.ADTABLE
					+ " t2 where t1.id=t2.user_id limit " + ((cc.getCurrentPage() - 1) * cc.getPageSize()) + ","
					+ cc.getPageSize();
			rs = executeQuery(sql, 0);
			while (rs.next()) {
				av = new AdVo(rs.getInt("id"), rs.getString("adName"), rs.getString("saveName"), rs.getLong("adSize"),
						rs.getTimestamp("uploadtime"), rs.getString("operator"),rs.getInt("user_id"));
				list.add(av);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getAdCount() {//此处连接不能先于分页查询前关闭
		String sql = null;
		ResultSet rs = null;
		int ret=0;
		try {
			sql="select count(id) from "+TableManager.ADTABLE;
			rs=executeQuery(sql, 0);
			while(rs.next()) {
				ret=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
