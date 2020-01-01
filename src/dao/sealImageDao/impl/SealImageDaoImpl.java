package dao.sealImageDao.impl;

import java.util.List;

import dao.sealImageDao.api.ISealImageDao;
import util.BaseDaoJDBC;
import util.CommenClass;
import util.TableManager;
import vo.sealImage.SealImageVo;
import vo.userVo.UserVo;

/**
 * dao层实现,仅涉及与数据库连接操作,不参与业务逻辑判断
 * 
 * @author Administrator
 *
 */
public class SealImageDaoImpl extends BaseDaoJDBC implements ISealImageDao<SealImageVo, CommenClass, UserVo> {
	@Override
	public int newSealImage(SealImageVo sv, UserVo uv) {
		String sql = "insert into " + TableManager.SEALIMAGE + " values(null,'" + sv.getOriginalName() + "','"
				+ sv.getSaveName() + "'," + sv.getImgSize() + ",'" + sv.getUploadtime() + "','" + uv.getUserNo() + "',"
				+ uv.getId() + ",null,2)";// 默认为待审批状态
//		System.out.println("sql:"+sql);
		return update(sql);
	}

	@Override
	public int deleteSealImage(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteSealImage(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSealImage(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SealImageVo selectSealImage(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SealImageVo> pageSelectSealImage(CommenClass g,String status) {
		String sql = "select*from " + TableManager.SEALIMAGE + " where status!="+status+" limit " + (g.getCurrentPage() - 1) * g.getPageSize()
				+ "," + g.getPageSize();
		List<SealImageVo> list = queryForList(sql);
		return list;
	}

	@Override
	public int getSealImageCount(String status) {
		String sql="select count(imgId) from "+TableManager.SEALIMAGE+" where status!="+status;
//		System.out.println("countSql:"+sql);
		return queryForInt(sql);
	}

}
