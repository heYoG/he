package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.sealBody.web.form.SealBodyForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.struts.BaseAction;
/**
 * 印章管理中显示印章列表
 * @author YC
 *
 */
public class SealBodymanageAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealBodymanageAction.class.getName());

	private ISealBodyService seal_body;
	private SealBodyForm sealForm;
	   
    
	public SealBodyForm getSealForm() {
		return sealForm;
	}

	public void setSealForm(SealBodyForm sealForm) {
		this.sealForm = sealForm;
	}

	public ISealBodyService getSeal_body() {
		return seal_body;
	}

	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}
      @Override
      public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	  //判断用户是否登陆
	  if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
		return mapping.findForward("no_login");
	  }
		// 获得当前页
		int pageIndex = 1;
		if (request.getParameter("pageIndex") != null) {
			pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		}
		// 获得每页显示数
		int pageSize = Constants.PAGESIZE;
		// 获得当前用户
		HttpSession session = request.getSession();
		SysUser user = (SysUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		String type=request.getParameter("type");//flag1是查询页面，All是管理页面
		PageSplit pageSplit; 
		if(type.equals("flag1")){
			String seal_name=request.getParameter("seal_name");//印章名称
			String seal_type=request.getParameter("seal_type");//印章类别
			String dept_no=request.getParameter("dept_no");//所属单位
			String approve_begintime=request.getParameter("approve_begintime");//开始时间
			String approve_endtime=request.getParameter("approve_endtime");//结束时间
			String is_junior=request.getParameter("is_junior");//是否允许下级
			sealForm.setSeal_name(seal_name);
			sealForm.setSeal_type(seal_type);
			sealForm.setDept_no(dept_no);
			sealForm.setApprove_begintime(approve_begintime);
			sealForm.setApprove_endtime(approve_endtime);
			
			long t=System.currentTimeMillis();
			
			pageSplit = seal_body.showBodyQueryList(sealForm, pageSize, pageIndex, is_junior);
			
			logger.info("ActionTime:"+(System.currentTimeMillis()-t));
			
			request.setAttribute("is_junior", is_junior);// 封装数据
			request.setAttribute("approve_begintime", approve_begintime);
			request.setAttribute("approve_endtime", approve_endtime);
			request.setAttribute("seal_name", seal_name);
			request.setAttribute("seal_type", seal_type);
			request.setAttribute("dept_no", dept_no);
		}else{
			String is_junior=request.getParameter("is_junior");//是否允许下级
			logger.info("is_junior:"+is_junior);
			if(is_junior==null){
				is_junior="1";
			}
			logger.info("is_junior:"+is_junior);
			pageSplit = seal_body.showBodyList(is_junior,user.getUser_id(),pageSize,pageIndex);
		}	
		request.setAttribute("pageSplit", pageSplit);// 封装数据
		return mapping.findForward("success");
     }
}
