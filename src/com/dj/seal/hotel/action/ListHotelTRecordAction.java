package com.dj.seal.hotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.hotel.service.api.RecordService;
import com.dj.seal.util.struts.BaseAction;

public class ListHotelTRecordAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(ListHotelTRecordAction.class.getName());
	
	private RecordService recordService ;
	
	public RecordService getRecordService() {
		return recordService;
	}


	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}



	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("success");

	}


	
}
