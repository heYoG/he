package mapper.notice;

import vo.notice.NoticeVo;
import java.util.*;

import org.apache.ibatis.annotations.Param;

public interface INoticeDao {
	/**
	 * 新发公告
	 * @param nv	实例变量
	 * @return
	 */
	public int sendNotice(NoticeVo nv);
	
	/**
	 * 分页查询所有已发布公告,多个参数时需要用@Param
	 * @param cc	分页变量
	 * @return
	 */
	public List<NoticeVo> noticePageList(@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize);
	
	/**
	 * 删除公告记录,单个参数直接传
	 * @param id	删除条件
	 * @return
	 */
	public int deleteNotice(int id);
	
	/**
	 * 修改公告状态
	 * @param id	修改条件
	 * @param type	修改类型 0：注销;1：激活
	 * @return
	 */
	public int updateNoticeStatus(@Param("id")int id,@Param("type")int type);
	
	/**
	 * 获取公告总记录数
	 * @return
	 */
	public int getNoticeCount();
	
}
