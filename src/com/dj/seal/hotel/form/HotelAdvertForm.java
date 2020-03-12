package com.dj.seal.hotel.form;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class HotelAdvertForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682803277992986915L;

	private String ad_id;// 广告ID
	private String ad_title;// 广告标题
	private Timestamp ad_ctime;// 广告创建时间
	private String ad_filename;// 广告文件名称
	private String ad_state;// 广告状态
	private String ad_dept;// 广告部门
	private String approve_user;// 广告审批人
	private Timestamp ad_updatetime;// 广告修改时间
	private String ad_opinion;// 审批意见
	private Timestamp ad_approvetime;// 审批时间
	private String ad_version;// 版本号
	private Timestamp ad_starttime;// 广告生效时间
	private Timestamp ad_endtime;// 广告失效时间
	private String ad_scorlltime;// 广告轮训播放时间
	private String ad_advertdata;
	private String imagename;

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getAd_advertdata() {
		return ad_advertdata;
	}

	public void setAd_advertdata(String ad_advertdata) {
		this.ad_advertdata = ad_advertdata;
	}

	public String getAd_scorlltime() {
		return ad_scorlltime;
	}

	public void setAd_scorlltime(String ad_scorlltime) {
		this.ad_scorlltime = ad_scorlltime;
	}

	public String getAd_version() {
		return ad_version;
	}

	public void setAd_version(String adVersion) {
		ad_version = adVersion;
	}

	public Timestamp getAd_approvetime() {
		return ad_approvetime;
	}

	public void setAd_approvetime(Timestamp adApprovetime) {
		ad_approvetime = adApprovetime;
	}

	public String getAd_opinion() {
		return ad_opinion;
	}

	public void setAd_opinion(String adOpinion) {
		ad_opinion = adOpinion;
	}

	public Timestamp getAd_updatetime() {
		return ad_updatetime;
	}

	public void setAd_updatetime(Timestamp adUpdatetime) {
		ad_updatetime = adUpdatetime;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approveUser) {
		approve_user = approveUser;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String adTitle) {
		ad_title = adTitle;
	}

	public String getAd_dept() {
		return ad_dept;
	}

	public void setAd_dept(String adDept) {
		ad_dept = adDept;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String adId) {
		ad_id = adId;
	}

	public Timestamp getAd_ctime() {
		return ad_ctime;
	}

	public void setAd_ctime(Timestamp adCtime) {
		ad_ctime = adCtime;
	}

	public String getAd_filename() {
		return ad_filename;
	}

	public void setAd_filename(String adFilename) {
		ad_filename = adFilename;
	}

	public String getAd_state() {
		return ad_state;
	}

	public void setAd_state(String adState) {
		ad_state = adState;
	}

	public Timestamp getAd_starttime() {
		return ad_starttime;
	}

	public void setAd_starttime(Timestamp ad_starttime) {
		this.ad_starttime = ad_starttime;
	}

	public Timestamp getAd_endtime() {
		return ad_endtime;
	}

	public void setAd_endtime(Timestamp ad_endtime) {
		this.ad_endtime = ad_endtime;
	}

}
