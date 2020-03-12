package com.dj.seal.hotel.action;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.hotel.form.HotelAdvertForm;
import com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.util.struts.BaseAction;

public class NewHotelAdvertAction extends BaseAction {

	private HotelAdvertServiceImpl advertService;
	private UserService user_dao;

	public UserService getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(UserService userDao) {
		user_dao = userDao;
	}

	public HotelAdvertServiceImpl getAdvertService() {
		return advertService;
	}

	public void setAdvertService(HotelAdvertServiceImpl advertService) {
		this.advertService = advertService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HotelAdvertForm f = (HotelAdvertForm) form;
		String approve_user = request.getAttribute("approve_user").toString();
		f.setApprove_user(approve_user);
		f.setAd_dept(request.getAttribute("dept_no").toString());
		f.setAd_filename(request.getSession().getAttribute("name").toString());
		f.setAd_title(request.getAttribute("title").toString());
		f.setAd_advertdata(request.getAttribute("ad_advertdata").toString());
		f.setAd_state("0");
		f.setAd_version("0");
		String start_time = request.getAttribute("start_time").toString();
		String end_time = request.getAttribute("end_time").toString();
		String start_time1 = start_time.toString() + " " + "00:00:00";
		String end_time1 = end_time.toString() + " " + "00:00:00";
		f.setAd_starttime(Timestamp.valueOf(start_time1));
		f.setAd_endtime(Timestamp.valueOf(end_time1));
		f.setAd_scorlltime(request.getAttribute("scorlltime").toString());
		advertService.addHotelAdvert(f);
		request.getSession().removeAttribute("name");
		return mapping.findForward("success");
	}

}