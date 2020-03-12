package mapper.user;

import org.apache.ibatis.annotations.Param;

import vo.userVo.UserVo;

public interface IUserDao {
	
	/**
	 * 用户登录同时获取用户全部信息
	 * @param userNo	用户名称
	 * @param pwd		用户密码
	 * @return
	 */
	public UserVo login(@Param("userNo") String userNo,@Param("password") String pwd);
}
