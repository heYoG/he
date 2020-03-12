package com.dj.seal.sysmgr.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.structure.dao.po.SysBackup;
import com.dj.seal.sysmgr.service.api.IBackupService;
import com.dj.seal.sysmgr.util.common.ResourceUtil;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 进入数据备份页面
 * 
 * @author lzl
 * @since 2013-4-16
 * 
 */
public class BackupManageAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(BackupManageAction.class.getName());
	
	private IBackupService backup_srv;
	
	public IBackupService getBackup_srv() {
		return backup_srv;
	}

	public void setBackup_srv(IBackupService backup_srv) {
		this.backup_srv = backup_srv;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		// 判断用户是否已经登录，如果未登录，则返回用户登录页面
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		SysBackup backup = backup_srv.getBackup();
		request.setAttribute("backup", backup);
		request.setAttribute("backuppath", ResourceUtil.getResourceName("backup_path"));
		return mapping.findForward("success");
	}

}
