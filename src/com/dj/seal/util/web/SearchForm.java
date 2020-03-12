package com.dj.seal.util.web;

import java.sql.Timestamp;

import org.apache.struts.action.ActionForm;

public class SearchForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String etpr_name;// 企业名称
	private String etpr_owner;
	private String etpr_addr;
	private String etpr_phone;
	private String etpr_email;
	private String etpr_website;
	private String etpr_fax;
	private String area_no;
	private String seal_name;// 印章名称
	private Integer status_seal;// 印章状态（正常、停用、废除）
	private Integer type_seal;// 印章类型（市民、企业合同、企业法人、企业公章）
	private Integer cert_band;
	private Integer cert_num;
	private String seal_user;// 印章受理员
	private String begin_time;// 起始时间
	private String end_time;// 结束时间
	private String cert_dn;// 
	private Integer app_type;// 业务类型
	private String begin_time_2;// 起始时间2
	private String end_time_2;// 结束时间2
	private String user_no;
	private String user_name;// 用户名
	private String user_ip;// IP地址
	private String obj_type;// 对象类型
	private String obj_name;// 对象名称
	private String obj_no;// 对象编号
	private String module_no;// 模块号
	private String fbd_login;
	private String busi_name;// 业务名称
	private Integer type_busi;// 业务类型
	private Integer status_busi;// 业务状态
	private String busi_discript;// 业务描述
	private String desc;
	
	private String create_user;
	
	private String rule_name;
	private String rule_state;
	
	//业务高级搜索
	private String start_time;
	private String flow_doc;
	private String flow_status;
	private String pre_user;
	private String next_user;
	private String banli_ret_2;
	private String begin_user;
	private String flow_doc_2;
	
	// 证书Po
	private String cert_no;// 证书号
	private String cert_name;// 证书别名
	private String cert_src;// 证书来源:客户端、服务器文件证书、签名服务器
	private String cert_detail;// 证书详细：路径或别名
	private String cert_psd;// 证书密码
	private String cert_type;// 证书类型：个人证书、公证书
	private String cert_status;// 证书状态
	private String dept_no;// 证书所属部门
	private String reg_user;// 登记人
	private String reg_time;// 登记时间
	private String status_cert;// 证书状态
	private String sign_cert;// 签名证书
	private String cardNo;//证件号码
	private String deptId; //部门编号
	
	private String shouliNo;//受理编号
	

	// 盖章业务
	private String busi_no;// 业务号
	private String model_no;// 模板编号

	/**
	 * 角色查询
	 */	
	private String role_tab;// 角色排序号
	private String role_name;// 角色名称
	
	
	/**
	 * 权限查询
	 */
	private String menu_name;// 菜单名
	private String func_name;// 权限名
	private String user_num;// 可见用户数
	private String role_num;// 可见角色数

	/**
	 * 请求日志
	 */
	private String req_type;// 请求类型
	private String sys_id;//系统名
	private String create_name;//用户id
	
	/**
	 *应用系统查询 
	 */
	private String app_no;//应用系统编号
	private String app_name;//应用系统名称 
	
	/**
	 * 通用字典查询
	 */
	private String cname;//名称
	private String cshowname;//值
	private String cdatatype;//字符类型(整数、小数、大写金额、日期等)
	
	private String c_status;//显示状态。0显示，1隐藏
	private int c_sort;//排列顺序
	/**
	 * 判定条件查询
	 */
	private String c_name; //属性名
	private String c_value;	//属性值
	private String master_platecid;//模版ID
	
	
	private String cid;// 编号
	private String cip;// ip
	private Timestamp ccreatetime;// 单据创建时间
	private Timestamp cuploadtime;// 单据上传时间
	private String mtplid;// 模板编号
	private String createuserid;// 创建用户
	private String uploaduserid;// 上传用户
	private String cfilefilename;// 文件展示名称
	private String cdata;// 文件保存
	private String cstatus; // 状态
	
	private String checkstatus;//稽核状态（1为待一级稽核，2一级稽核通过待二级稽核，3二级稽核通过待三级稽核，4三级稽核通过待四级稽核，5四级稽核通过)（a为一级稽核未通过，b为二级稽核未通过，c为三级稽核未通过，d为四级稽核未通过）
	
	private String date_type;//查询时的日期类型，当天，当月等
	private String phoneNo;//手机号码
	private String createName;//创建单据用户名称
	private String roomId;//房间号
	private String cusName1;//客户姓名1
	private String insTime;//入住时间
	private String ineTime;//入住结束时间
	private String outsTime;//离店开始时间
	private String outeTime;//离店结束时间
	private String monery;//消费金额
	private String keyword;//关键字查询
	
	private String ceid;//农商行影像平台返回的ID
	private String serialNo;//交易流水
	private String tellerNo;//柜员号
	
	
	
	
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTellerNo() {
		return tellerNo;
	}

	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}

	public String getCeid() {
		return ceid;
	}

	public void setCeid(String ceid) {
		this.ceid = ceid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	//4S店
	private String dyrq;//打印日期
	private String gdh;//工单号
	private String cph;//车牌号
	private String fwzy;//服务专员
	
	
	//客户端操作日志
	private String operuserid;// 用户ID
	private String coperstime;// 操作时间
	private String cmac;// MAC地址
	private String opertype;// 操作类型 (1登录，2上传文件)
	private String objid;// 对象ID
	private String objname;// 对象名称
	private String result;// 结果 (0成功，1失败)
	private String coperetime;//结束时间
	
	//模板规则
	private String model_id;
	private String rule_no;
	private String model_name;
	
	//广告管理
	private String ad_id;//广告ID
	private String ad_title;//广告标题
	private String ad_ctime;//广告创建时间
	private String ad_filename;//广告文件名称
	private String ad_state;//广告状态
	private String ad_dept;//广告部门
	private String ad_deptname;//广告部门名称
	private String approve_user;//申请人
	
	private String selectTime;
	private String selectType;
	
	private String scannerContext;
	
	private String feed_id;//评价服务排号的ID
	
	private String orgunit;//银行机构号
	
	
	
	public String getOrgunit() {
		return orgunit;
	}

	public void setOrgunit(String orgunit) {
		this.orgunit = orgunit;
	}

	public String getFeed_id() {
		return feed_id;
	}

	public void setFeed_id(String feed_id) {
		this.feed_id = feed_id;
	}

	public String getScannerContext() {
		return scannerContext;
	}

	public void setScannerContext(String scannerContext) {
		this.scannerContext = scannerContext;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approveUser) {
		approve_user = approveUser;
	}

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getAd_deptname() {
		return ad_deptname;
	}

	public void setAd_deptname(String adDeptname) {
		ad_deptname = adDeptname;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String adId) {
		ad_id = adId;
	}

	public String getAd_dept() {
		return ad_dept;
	}

	public void setAd_dept(String adDept) {
		ad_dept = adDept;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String adTitle) {
		ad_title = adTitle;
	}

	public String getAd_ctime() {
		return ad_ctime;
	}

	public void setAd_ctime(String adCtime) {
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

	public String getCmac() {
		return cmac;
	}

	public void setCmac(String cmac) {
		this.cmac = cmac;
	}


	public String getOperuserid() {
		return operuserid;
	}

	public void setOperuserid(String operuserid) {
		this.operuserid = operuserid;
	}

	public String getCoperstime() {
		return coperstime;
	}

	public void setCoperstime(String coperstime) {
		this.coperstime = coperstime;
	}

	public String getCoperetime() {
		return coperetime;
	}

	public void setCoperetime(String coperetime) {
		this.coperetime = coperetime;
	}

	public String getOpertype() {
		return opertype;
	}

	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public Timestamp getCcreatetime() {
		return ccreatetime;
	}

	public void setCcreatetime(Timestamp ccreatetime) {
		this.ccreatetime = ccreatetime;
	}

	public Timestamp getCuploadtime() {
		return cuploadtime;
	}

	public void setCuploadtime(Timestamp cuploadtime) {
		this.cuploadtime = cuploadtime;
	}

	public String getMtplid() {
		return mtplid;
	}

	public void setMtplid(String mtplid) {
		this.mtplid = mtplid;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getUploaduserid() {
		return uploaduserid;
	}

	public void setUploaduserid(String uploaduserid) {
		this.uploaduserid = uploaduserid;
	}

	public String getCfilefilename() {
		return cfilefilename;
	}

	public void setCfilefilename(String cfilefilename) {
		this.cfilefilename = cfilefilename;
	}

	public String getCdata() {
		return cdata;
	}

	public void setCdata(String cdata) {
		this.cdata = cdata;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCusName1() {
		return cusName1;
	}

	public void setCusName1(String cusName1) {
		this.cusName1 = cusName1;
	}

	public String getInsTime() {
		return insTime;
	}

	public void setInsTime(String insTime) {
		this.insTime = insTime;
	}

	public String getIneTime() {
		return ineTime;
	}

	public void setIneTime(String ineTime) {
		this.ineTime = ineTime;
	}

	public String getOutsTime() {
		return outsTime;
	}

	public void setOutsTime(String outsTime) {
		this.outsTime = outsTime;
	}

	public String getOuteTime() {
		return outeTime;
	}

	public void setOuteTime(String outeTime) {
		this.outeTime = outeTime;
	}

	public String getMonery() {
		return monery;
	}

	public void setMonery(String monery) {
		this.monery = monery;
	}

	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

	private String cvalue; //属性值
	
	public String getC_name() {
		return c_name;
	}

	public void setC_name(String cName) {
		c_name = cName;
	}

	public String getC_value() {
		return c_value;
	}

	public void setC_value(String cValue) {
		c_value = cValue;
	}

	public String getMaster_platecid() {
		return master_platecid;
	}

	public void setMaster_platecid(String masterPlatecid) {
		master_platecid = masterPlatecid;
	}

	public String getApp_no() {
		return app_no;
	}

	public void setApp_no(String app_no) {
		this.app_no = app_no;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getReq_type() {
		return req_type;
	}

	public void setReq_type(String req_type) {
		this.req_type = req_type;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getReg_time() {
		return reg_time;
	}

	private String flow_no;// 业务注流程编号
	private String banli_ret;// 办理结果
	private String banli_user;// 办理人

	private String survey_no;// 调查号

	public String getObj_no() {
		return obj_no;
	}

	public void setObj_no(String obj_no) {
		this.obj_no = obj_no;
	}

	public String getModule_no() {
		return module_no;
	}

	public void setModule_no(String module_no) {
		this.module_no = module_no;
	}

	public String getSurvey_no() {
		return survey_no;
	}

	public void setSurvey_no(String survey_no) {
		this.survey_no = survey_no;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getObj_type() {
		return obj_type;
	}

	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getBusi_no() {
		return busi_no;
	}

	public void setBusi_no(String busi_no) {
		this.busi_no = busi_no;
	}

	public String getModel_no() {
		return model_no;
	}

	public void setModel_no(String model_no) {
		this.model_no = model_no;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public String getCert_name() {
		return cert_name;
	}

	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	public String getCert_src() {
		return cert_src;
	}

	public void setCert_src(String cert_src) {
		this.cert_src = cert_src;
	}

	public String getCert_detail() {
		return cert_detail;
	}

	public void setCert_detail(String cert_detail) {
		this.cert_detail = cert_detail;
	}

	public String getCert_psd() {
		return cert_psd;
	}

	public void setCert_psd(String cert_psd) {
		this.cert_psd = cert_psd;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_status() {
		return cert_status;
	}

	public void setCert_status(String cert_status) {
		this.cert_status = cert_status;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}

	public String getBanli_ret() {
		return banli_ret;
	}

	public void setBanli_ret(String banli_ret) {
		this.banli_ret = banli_ret;
	}

	public String getBanli_user() {
		return banli_user;
	}

	public void setBanli_user(String banli_user) {
		this.banli_user = banli_user;
	}

	public String getBusi_discript() {
		return busi_discript;
	}

	public void setBusi_discript(String busi_discript) {
		this.busi_discript = busi_discript;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getFbd_login() {
		return fbd_login;
	}

	public void setFbd_login(String fbd_login) {
		this.fbd_login = fbd_login;
	}

	public String getCert_dn() {
		return cert_dn;
	}

	public void setCert_dn(String cert_dn) {
		this.cert_dn = cert_dn;
	}

	public Integer getApp_type() {
		return app_type;
	}

	public void setApp_type(Integer app_type) {
		this.app_type = app_type;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getEtpr_name() {
		return etpr_name;
	}

	public void setEtpr_name(String etpr_name) {
		this.etpr_name = etpr_name;
	}

	public String getEtpr_owner() {
		return etpr_owner;
	}

	public void setEtpr_owner(String etpr_owner) {
		this.etpr_owner = etpr_owner;
	}

	public String getEtpr_addr() {
		return etpr_addr;
	}

	public void setEtpr_addr(String etpr_addr) {
		this.etpr_addr = etpr_addr;
	}

	public String getEtpr_phone() {
		return etpr_phone;
	}

	public void setEtpr_phone(String etpr_phone) {
		this.etpr_phone = etpr_phone;
	}

	public String getEtpr_email() {
		return etpr_email;
	}

	public void setEtpr_email(String etpr_email) {
		this.etpr_email = etpr_email;
	}

	public String getEtpr_website() {
		return etpr_website;
	}

	public void setEtpr_website(String etpr_website) {
		this.etpr_website = etpr_website;
	}

	public String getEtpr_fax() {
		return etpr_fax;
	}

	public void setEtpr_fax(String etpr_fax) {
		this.etpr_fax = etpr_fax;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getSeal_name() {
		return seal_name;
	}

	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}

	public Integer getStatus_seal() {
		return status_seal;
	}

	public void setStatus_seal(Integer status_seal) {
		this.status_seal = status_seal;
	}

	public Integer getType_seal() {
		return type_seal;
	}

	public void setType_seal(Integer type_seal) {
		this.type_seal = type_seal;
	}

	public Integer getCert_band() {
		return cert_band;
	}

	public void setCert_band(Integer cert_band) {
		this.cert_band = cert_band;
	}

	public Integer getCert_num() {
		return cert_num;
	}

	public void setCert_num(Integer cert_num) {
		this.cert_num = cert_num;
	}

	public String getSeal_user() {
		return seal_user;
	}

	public void setSeal_user(String seal_user) {
		this.seal_user = seal_user;
	}

	public String getBegin_time_2() {
		return begin_time_2;
	}

	public void setBegin_time_2(String begin_time_2) {
		this.begin_time_2 = begin_time_2;
	}

	public String getEnd_time_2() {
		return end_time_2;
	}

	public void setEnd_time_2(String end_time_2) {
		this.end_time_2 = end_time_2;
	}

	public String getBusi_name() {
		return busi_name;
	}

	public void setBusi_name(String busi_name) {
		this.busi_name = busi_name;
	}

	public Integer getType_busi() {
		return type_busi;
	}

	public void setType_busi(Integer type_busi) {
		this.type_busi = type_busi;
	}

	public Integer getStatus_busi() {
		return status_busi;
	}

	public void setStatus_busi(Integer status_busi) {
		this.status_busi = status_busi;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFlow_no() {
		return flow_no;
	}

	public void setFlow_no(String flow_no) {
		this.flow_no = flow_no;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public String getUser_num() {
		return user_num;
	}

	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}

	public String getRole_num() {
		return role_num;
	}

	public void setRole_num(String role_num) {
		this.role_num = role_num;
	}

	public String getStatus_cert() {
		return status_cert;
	}

	public void setStatus_cert(String status_cert) {
		this.status_cert = status_cert;
	}

	public String getSign_cert() {
		return sign_cert;
	}

	public void setSign_cert(String sign_cert) {
		this.sign_cert = sign_cert;
	}

	public String getRole_tab() {
		return role_tab;
	}

	public void setRole_tab(String role_tab) {
		this.role_tab = role_tab;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRule_name() {
		return rule_name;
	}

	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}

	public String getRule_state() {
		return rule_state;
	}

	public void setRule_state(String rule_state) {
		this.rule_state = rule_state;
	}

	public String getFlow_doc() {
		return flow_doc;
	}

	public void setFlow_doc(String flow_doc) {
		this.flow_doc = flow_doc;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getFlow_status() {
		return flow_status;
	}

	public void setFlow_status(String flow_status) {
		this.flow_status = flow_status;
	}

	public String getPre_user() {
		return pre_user;
	}

	public void setPre_user(String pre_user) {
		this.pre_user = pre_user;
	}

	public String getNext_user() {
		return next_user;
	}

	public void setNext_user(String next_user) {
		this.next_user = next_user;
	}

	public String getBanli_ret_2() {
		return banli_ret_2;
	}

	public void setBanli_ret_2(String banli_ret_2) {
		this.banli_ret_2 = banli_ret_2;
	}

	public String getBegin_user() {
		return begin_user;
	}

	public void setBegin_user(String begin_user) {
		this.begin_user = begin_user;
	}

	public String getFlow_doc_2() {
		return flow_doc_2;
	}

	public void setFlow_doc_2(String flow_doc_2) {
		this.flow_doc_2 = flow_doc_2;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCshowname() {
		return cshowname;
	}

	public void setCshowname(String cshowname) {
		this.cshowname = cshowname;
	}

	public String getCdatatype() {
		return cdatatype;
	}

	public void setCdatatype(String cdatatype) {
		this.cdatatype = cdatatype;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String modelId) {
		model_id = modelId;
	}

	public String getRule_no() {
		return rule_no;
	}

	public void setRule_no(String ruleNo) {
		rule_no = ruleNo;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String modelName) {
		model_name = modelName;
	}

	public String getC_status() {
		return c_status;
	}

	public void setC_status(String cStatus) {
		c_status = cStatus;
	}

	public int getC_sort() {
		return c_sort;
	}

	public void setC_sort(int cSort) {
		c_sort = cSort;
	}

	public String getDyrq() {
		return dyrq;
	}

	public void setDyrq(String dyrq) {
		this.dyrq = dyrq;
	}

	public String getGdh() {
		return gdh;
	}

	public void setGdh(String gdh) {
		this.gdh = gdh;
	}

	public String getCph() {
		return cph;
	}

	public void setCph(String cph) {
		this.cph = cph;
	}

	public String getFwzy() {
		return fwzy;
	}

	public void setFwzy(String fwzy) {
		this.fwzy = fwzy;
	}

	public String getShouliNo() {
		return shouliNo;
	}

	public void setShouliNo(String shouliNo) {
		this.shouliNo = shouliNo;
	}

	public String getDate_type() {
		return date_type;
	}

	public void setDate_type(String date_type) {
		this.date_type = date_type;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
}
