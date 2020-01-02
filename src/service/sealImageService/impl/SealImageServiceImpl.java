package service.sealImageService.impl;

import java.util.List;

import dao.sealImageDao.impl.SealImageDaoImpl;
import service.sealImageService.api.ISealImageService;
import util.BaseDao;
import util.CommenClass;
import util.TableManager;
import vo.sealImage.SealImageVo;
import vo.userVo.UserVo;

public class SealImageServiceImpl extends BaseDao implements ISealImageService<SealImageVo, CommenClass,UserVo> {
	private SealImageDaoImpl sdi;
	
	public SealImageDaoImpl getSdi() {
		return sdi;
	}

	public void setSdi(SealImageDaoImpl sdi) {//至少要有setter方法,否则IOC启动异常
		this.sdi = sdi;
	}

	@Override
	public int newSealImage(SealImageVo t,UserVo uv) {
		int addRet = sdi.newSealImage(t,uv);
		return addRet;
	}

	@Override
	public int deleteSealImage(int id) {
		return sdi.deleteSealImage(id);
	}

	@Override
	public int deleteSealImage(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateSealImage(String id,String status) {
		return sdi.updateSealImage(id, status);
	}

	@Override
	public SealImageVo selectSealImage(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SealImageVo> pageSelectSealImage(CommenClass g,String status) {
		return sdi.pageSelectSealImage(g,status);
	}

	@Override
	public int getSealImageCount(String status) {
		
		return sdi.getSealImageCount(status);
	}

}
