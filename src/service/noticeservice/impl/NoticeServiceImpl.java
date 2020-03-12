package service.noticeservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.notice.INoticeDao;
import service.noticeservice.api.INoticeService;
import vo.notice.NoticeVo;

@Service
public class NoticeServiceImpl implements INoticeService {
	@Autowired
	private INoticeDao noticeDao;
	
	@Override
	public int sendNotice(NoticeVo nv) {
		return noticeDao.sendNotice(nv);
	}

	@Override
	public List<NoticeVo> noticePageList(int pageIndex,int pageSize) {
		return noticeDao.noticePageList(pageIndex,pageSize);
	}

	@Override
	public int deleteNotice(int id) {
		return noticeDao.deleteNotice(id);
	}

	@Override
	public int updateNoticeStatus(int id,int type) {
		return noticeDao.updateNoticeStatus(id, type);
	}

	@Override
	public int getNoticeCount() {
		return noticeDao.getNoticeCount();
	}

}
