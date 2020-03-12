package service.userservice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.userDao.impl.UserDaoImplHib;
import service.userservice.api.IUserService;
import util.CommenClass;
import vo.userVo.UserVo;

@Service
public class UserServiceImpl implements IUserService<UserVo> {
	@Autowired
	private UserDaoImplHib<UserVo> udi;
	
	@Override
	public int createTable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addUser(UserVo entity) {
		int addUser = udi.addUser(entity);
		return addUser;
	}

	@Override
	public int delUser(UserVo t, int userID, int flag) {
		int delUser = udi.delUser(t, userID, flag);
		return delUser;
	}

	@Override
	public List<UserVo> selectUser(UserVo entity, int... isLogin) {
		List<UserVo> selectUser = udi.selectUser(entity, isLogin);
		return selectUser;
	}

	@Override
	public List<UserVo> pageSelectUser(UserVo entity, CommenClass cc) {
		List<UserVo> pageSelectUser = udi.pageSelectUser(entity, cc);
		return pageSelectUser;
	}

	@Override
	public long getCount(String type) {
		long count = udi.getCount(type);
		return count;
	}

	@Override
	public int updateUser(UserVo user) {
		int updateUser = udi.updateUser(user);
		return updateUser;
	}

	@Override
	public int updateStatus(UserVo user, int userID) {
		int updateStatus = udi.updateStatus(user, userID);
		return updateStatus;
	}

	@Override
	public List<UserVo> pageApproveUser(UserVo vo, CommenClass cc) {
		List<UserVo> pageApproveUser = udi.pageApproveUser(vo, cc);
		return pageApproveUser;
	}

	@Override
	public long getApproveCount() {
		long approveCount = udi.getApproveCount();
		return approveCount;
	}

	@Override
	public int updateApproveUserStatus(UserVo user) {
		int updateApproveUserStatus = udi.updateApproveUserStatus(user);
		return updateApproveUserStatus;
	}

}
