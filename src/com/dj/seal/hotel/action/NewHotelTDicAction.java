package com.dj.seal.hotel.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.hotel.form.HotelTDicForm;
import com.dj.seal.hotel.service.api.IHotelTDicHotelUtilService;
import com.dj.seal.util.struts.BaseAction;

public class NewHotelTDicAction extends BaseAction {
	
	static Logger logger = LogManager.getLogger(NewHotelTDicAction.class.getName());
	
	private IHotelTDicHotelUtilService hotelTDidService;

	public IHotelTDicHotelUtilService getHotelTDidService() {
		return hotelTDidService;
	}



	public void setHotelTDidService(IHotelTDicHotelUtilService hotelTDidService) {
		this.hotelTDidService = hotelTDidService;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		HotelTDicForm f = (HotelTDicForm)form; 
		
		hotelTDidService.addHotelTDic(f);
		return mapping.findForward("success");

	}

}
