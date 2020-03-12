package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

/**
 * 
 * @description 界面表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class ViewInterface extends BasePO{
    
	private static final long serialVersionUID = 1L;
	private String ie_title;           // 浏览器标题
	private String status_text;        // 状态设置
	private String banner_text;        // 顶部大标题
	private String banner_font;        // 标题字体
	private String attachment_id;
	private String attachment_name;	  // 顶部图标名
	private Integer img_width;	      // 顶部图标宽度
	private Integer img_height;       // 顶部图标高度
	private String attachment_id1;	
	private String attachment_name1;  // 登录界面图片
	private String avatar_upload;  // 是否允许上传头像
	private Integer avatar_width;	  // 头像最大宽度
	private Integer avatar_height;    // 头像最大高度
	private String theme_select;      // 是否允许选择主题
	private String view_theme;        // 界面主题
	private String view_template;     // 登录界面模板
	
	public ViewInterface(){
		
	}
	
	public String getIe_title() {
		return ie_title;
	}

	public void setIe_title(String ie_title) {
		this.ie_title = ie_title;
	}

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public String getBanner_text() {
		return banner_text;
	}

	public void setBanner_text(String banner_text) {
		this.banner_text = banner_text;
	}

	public String getBanner_font() {
		return banner_font;
	}

	public void setBanner_font(String banner_font) {
		this.banner_font = banner_font;
	}

	public String getAttachment_id() {
		return attachment_id;
	}

	public void setAttachment_id(String attachment_id) {
		this.attachment_id = attachment_id;
	}

	public String getAttachment_name() {
		return attachment_name;
	}

	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}

	public Integer getImg_width() {
		return img_width;
	}

	public void setImg_width(Integer img_width) {
		this.img_width = img_width;
	}

	public Integer getImg_height() {
		return img_height;
	}

	public void setImg_height(Integer img_height) {
		this.img_height = img_height;
	}

	public String getAttachment_id1() {
		return attachment_id1;
	}

	public void setAttachment_id1(String attachment_id1) {
		this.attachment_id1 = attachment_id1;
	}

	public String getAttachment_name1() {
		return attachment_name1;
	}

	public void setAttachment_name1(String attachment_name1) {
		this.attachment_name1 = attachment_name1;
	}

	public String getAvatar_upload() {
		return avatar_upload;
	}

	public void setAvatar_upload(String avatar_upload) {
		this.avatar_upload = avatar_upload;
	}

	public Integer getAvatar_width() {
		return avatar_width;
	}

	public void setAvatar_width(Integer avatar_width) {
		this.avatar_width = avatar_width;
	}

	public Integer getAvatar_height() {
		return avatar_height;
	}

	public void setAvatar_height(Integer avatar_height) {
		this.avatar_height = avatar_height;
	}

	public String getTheme_select() {
		return theme_select;
	}

	public void setTheme_select(String theme_select) {
		this.theme_select = theme_select;
	}

	public String getView_theme() {
		return view_theme;
	}

	public void setView_theme(String view_theme) {
		this.view_theme = view_theme;
	}

	public String getView_template() {
		return view_template;
	}

	public void setView_template(String view_template) {
		this.view_template = view_template;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ViewInterface other = (ViewInterface) obj;
		if (ie_title == null) {
			if (other.ie_title != null)
				return false;
		} else if (!ie_title.equals(other.ie_title))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((ie_title == null) ? 0 : ie_title.hashCode());
		return result;
	}

}
