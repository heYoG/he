package com.dj.seal.hotel.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class HotelAdvertPO extends BasePO {

	/**
	 * 鑷姩鐢熸垚鐨�
	 */
	private static final long serialVersionUID = -9007049455173239495L;

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	private String ad_id;// 骞垮憡ID
	private String ad_title;// 骞垮憡鏍囬
	private Timestamp ad_ctime;// 骞垮憡鍒涘缓鏃堕棿
	private String ad_filename;// 骞垮憡鏂囦欢鍚嶇О
	private String ad_state;// 骞垮憡鐘舵�
	private String ad_dept;// 骞垮憡閮ㄩ棬
	private String approve_user;// 骞垮憡瀹℃壒浜�
	private Timestamp ad_updatetime;// 骞垮憡淇敼浣垮姴
	private String ad_version;
	private String ad_opinion;
	private Timestamp ad_approvetime;
	private Timestamp ad_starttime;//有效起始时间
	private Timestamp ad_endtime;//有效截止时间
	private String ad_scorlltime;

	public String getAd_scorlltime() {
		return ad_scorlltime;
	}

	public void setAd_scorlltime(String ad_scorlltime) {
		this.ad_scorlltime = ad_scorlltime;
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

	public String getAd_version() {
		return ad_version;
	}

	public void setAd_version(String adVersion) {
		ad_version = adVersion;
	}

	public String getAd_opinion() {
		return ad_opinion;
	}

	public void setAd_opinion(String adOpinion) {
		ad_opinion = adOpinion;
	}

	public Timestamp getAd_approvetime() {
		return ad_approvetime;
	}

	public void setAd_approvetime(Timestamp adApprovetime) {
		ad_approvetime = adApprovetime;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approveUser) {
		approve_user = approveUser;
	}

	public Timestamp getAd_updatetime() {
		return ad_updatetime;
	}

	public void setAd_updatetime(Timestamp adUpdatetime) {
		ad_updatetime = adUpdatetime;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String adId) {
		ad_id = adId;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String adTitle) {
		ad_title = adTitle;
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

	public String getAd_dept() {
		return ad_dept;
	}

	public void setAd_dept(String adDept) {
		ad_dept = adDept;
	}

}
