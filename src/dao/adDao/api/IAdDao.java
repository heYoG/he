package dao.adDao.api;

import java.util.List;

import util.CommenClass;
import vo.adVo.AdVo;

public interface IAdDao {
	/**
	 * 新增广告图片
	 * @param	adVo	广告图片实例
	 * @return
	 */
	public int newAd(AdVo adVo);
	
	/**
	 * 分页查询广告数据
	 * @param cc		分页类实例对象
	 * @return			广告类list
	 */
	public List<AdVo> getAds(CommenClass cc);
	
	/**
	 * 统计广告记录数
	 * @return		记录数
	 */
	public int getAdCount();
}
