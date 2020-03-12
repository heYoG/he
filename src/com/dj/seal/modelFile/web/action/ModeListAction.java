package com.dj.seal.modelFile.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.modelFile.service.impl.ModelFileServiceImpl;
import com.dj.seal.modelFile.vo.ModelFileVo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.BaseAction;

/**
 * 跳转到模式窗口时所用
 * 
 * @author oyxy
 * @since2009-11-24
 * 
 */
public class ModeListAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ModeListAction.class.getName());

	private ModelFileServiceImpl model_srv;
	
	public ModelFileServiceImpl getModel_srv() {
		return model_srv;
	}
	public void setModel_srv(ModelFileServiceImpl model_srv) {
		this.model_srv = model_srv;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		String user_status = Constants.USER_STATUS_NOMOR;
		if (request.getParameter("user_status") != null) {
			user_status = request.getParameter("user_status");
		}
		String xieyi_dept_no = "";
		if (request.getParameter("xieyi_dept_no") != null) {
			xieyi_dept_no = request.getParameter("xieyi_dept_no");
		}
		String xieyi_id = "";
		int xieyi_id_int=0;
		if (request.getParameter("xieyi_id") != null) {
			xieyi_id = request.getParameter("xieyi_id");
			xieyi_id_int=Integer.valueOf(xieyi_id);
		}
		String user_dept_no = "";
		if (request.getParameter("user_dept_no") != null) {
			user_dept_no = request.getParameter("user_dept_no");
		}
		// 封装备选角色列表
		List<ModelFileVo> list1=model_srv.showModelFiles(user_dept_no,"0");
		request.setAttribute("list1", list1);
		// 封装已选角色列表
		List<ModelFileVo> list2=model_srv.showModelFilesbyXieyi(xieyi_id_int);
		request.setAttribute("list2", list2);
		request.setAttribute("xieyi_dept_no", xieyi_dept_no);
		request.setAttribute("xieyi_id", xieyi_id_int);
		return mapping.findForward("success");
	}
}
