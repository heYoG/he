package hibernate.service.emailService.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hibernate.dao.emailDao.impl.EmailDaoImpl;
import hibernate.service.emailService.api.IEmailService;
import util.CommenClass;
import vo.emailVo.EmailVo;

@Service(value="emailService")
public class EmailServiceImpl implements IEmailService<EmailVo> {
	
	@Autowired//或Resource(name="emailDao")
	private EmailDaoImpl emailDao;//按类型装配注入dao层bean,ref="emailDao"(dao实现层的bean)

	@Override
	public Serializable newEmail(EmailVo ev) {
		Serializable save = emailDao.newEmail(ev);
		return save;
	}

	@Override
	public int deleteEmail(EmailVo t, Serializable id) {
	
		return emailDao.deleteEmail(t, id);
	}

	@Override
	public int update(EmailVo t, int id) {
	
		return 	emailDao.update(t, id);
	}

	@Override
	public EmailVo emailInfo(EmailVo t, int id) {
		// EmailVoODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailVo> emailPageList(EmailVo t, CommenClass cc, String flag) {
		List<EmailVo> emailPageList = emailDao.emailPageList(t, cc, flag);
		return emailPageList;
	}

	@Override
	public long getEmailCount(String flag) {
		return emailDao.getEmailCount(flag);
	}

}
