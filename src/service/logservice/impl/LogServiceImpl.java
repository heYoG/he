package service.logservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.log.ILogDao;
import service.logservice.api.ILogService;
import vo.logVo.LogVo;

@Service
public class LogServiceImpl implements ILogService {
	@Autowired
	private ILogDao logDao;
	@Override
	public int saveLog(LogVo log) {
		int saveLogRet = logDao.saveLog(log);
		return saveLogRet;
	}

}
