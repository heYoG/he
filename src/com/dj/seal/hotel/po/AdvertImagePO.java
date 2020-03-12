package com.dj.seal.hotel.po;

import com.dj.seal.util.dao.BasePO;

public class AdvertImagePO extends BasePO {

	private static final long serialVersionUID = -9007049455173239495L;

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	private String ai_id;
	private String ai_imagename;
	private String ai_imagedata;
	private String ad_id;

	public String getAi_id() {
		return ai_id;
	}

	public void setAi_id(String ai_id) {
		this.ai_id = ai_id;
	}

	public String getAi_imagename() {
		return ai_imagename;
	}

	public void setAi_imagename(String ai_imagename) {
		this.ai_imagename = ai_imagename;
	}

	public String getAi_imagedata() {
		return ai_imagedata;
	}

	public void setAi_imagedata(String ai_imagedata) {
		this.ai_imagedata = ai_imagedata;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

}
