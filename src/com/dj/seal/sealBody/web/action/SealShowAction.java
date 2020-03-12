package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.api.IUserService;
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
public class SealShowAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealShowAction.class.getName());

	   private ISealBodyService seal_body;
	   private IUserService user_srv;
	   private ISysDeptService dept_srv;
	   private ISysUnitService unit_srv;
	   private SealBodyForm sealForm;
	   
	   

	public SealBodyForm getSealForm() {
		return sealForm;
	}

	public void setSealForm(SealBodyForm sealForm) {
		this.sealForm = sealForm;
	}

		public ISysUnitService getUnit_srv() {
		return unit_srv;
	}

	public void setUnit_srv(ISysUnitService unit_srv) {
		this.unit_srv = unit_srv;
	}

		public ISysDeptService getDept_srv() {
			return dept_srv;
		}

		public void setDept_srv(ISysDeptService dept_srv) {
			this.dept_srv = dept_srv;
		}

		public IUserService getUser_srv() {
			return user_srv;
		}

		public void setUser_srv(IUserService user_srv) {
			this.user_srv = user_srv;
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
   		String type=request.getParameter("type");
   		PageSplit pageSplit; 
   		if(type.equals("flag2")){
   			String seal_name=request.getParameter("seal_name");//印章名称
   			String seal_type=request.getParameter("seal_type");//印章类别
   			String dept_no=request.getParameter("dept_no");//所属单位
   			String approve_begintime=request.getParameter("create_time_start");//开始时间
   			String approve_endtime=request.getParameter("create_time_end");//结束时间
   			String is_junior=request.getParameter("is_junior");//是否允许下级
   			sealForm.setSeal_name(seal_name);
   			sealForm.setSeal_type(seal_type);
   			sealForm.setDept_no(dept_no);
   			sealForm.setApprove_begintime(approve_begintime);
   			sealForm.setApprove_endtime(approve_endtime);
   			request.setAttribute("is_junior", is_junior);// 封装数据
		    request.setAttribute("approve_begintime", approve_begintime);
			request.setAttribute("approve_endtime", approve_endtime);
			request.setAttribute("seal_name", seal_name);
			request.setAttribute("seal_type", seal_type);
			request.setAttribute("dept_no", dept_no);
   			pageSplit = seal_body.showBodyQueryList(sealForm, pageSize, pageIndex, is_junior);
   			
   		}else{
   			String is_junior=request.getParameter("is_junior");//是否允许下级
   			pageSplit = seal_body.showBodyList(is_junior,user.getUser_id(),pageSize,pageIndex);	
   		}	
   		request.setAttribute("pageSplit", pageSplit);// 封装数据
   		return mapping.findForward("successSer");
        }
}
