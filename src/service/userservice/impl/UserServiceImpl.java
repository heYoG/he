package service.userservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.user.IUserDao;
import service.userservice.api.IUserService;
import vo.userVo.UserVo;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;

	@Override
	public UserVo login(String userNo, String pwd) {
		// TODO Auto-generated method stub
		return userDao.login(userNo, pwd);
	}

}
