package service.userservice.api;

import vo.userVo.UserVo;

public interface IUserService {
	/**
	 * 用户登录同时获取用户全部信息
	 * @param userNo	用户名称
	 * @param pwd		用户密码
	 * @return
	 */
	public UserVo login(String userNo,String pwd);
}
