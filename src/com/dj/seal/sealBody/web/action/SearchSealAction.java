package com.dj.seal.sealBody.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.util.struts.BaseAction;
/**
 *  跳转到印章查询页面
 * @author Hp
 *
 */
public class SearchSealAction extends BaseAction{
	

	static Logger logger = LogManager.getLogger(SearchSealAction.class.getName());


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("success");
	}
  
}
