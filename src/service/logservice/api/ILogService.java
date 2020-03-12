package service.logservice.api;

import vo.logVo.LogVo;

public interface ILogService {
	/**
	 * 添加操作日志
	 * @param log	日志实例变量
	 * @return		数据入库返回值
	 */
	public abstract int saveLog(LogVo log);
	
}
