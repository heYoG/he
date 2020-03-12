function initLog(){
	var log={};
	log.user_no=user_no;
	log.user_ip=user_ip;
	log.module_no=findPath(5);
	if(log.module_no=="seal_model"){
	log.module_no="业务规则管理";
	}
	if(log.module_no=="gaizhang_oper"){
	log.module_no="业务流程办理";
	}
	if(log.module_no=="gaizhang_app"){
	log.module_no="业务类型管理";
	}
	if(log.module_no=="app_survey"){
	log.module_no="业务调查办理";
	}
	if(log.module_no=="log"){
	log.module_no="日志管理";
	}
	if(log.module_no=="cert"){
	log.module_no="证书管理";
	}
	if(log.module_no=="log"){
	log.module_no="日志管理";
	}
	if(log.module_no=="organise"){
	log.module_no="角色管理";
	}
	return log;
}
function initLog2(model){
	var log={};
	log.user_no=user_no;
	log.user_ip=user_ip;
	log.module_no=model;
	return log;
}
function logAdd2(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("新增",type,no,name,remarks,model);
}
function logUpd2(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("更新",type,no,name,remarks,model);
}
function logDel2(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("删除",type,no,name,remarks,model);
}
function logAdd(type,no,name,remarks){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	log("新增",type,no,name,remarks);
}
function logUpd(type,no,name,remarks){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	log("更新",type,no,name,remarks);
}
function logPlUpd(type,no,name,remarks){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	log("批量更新",type,no,name,remarks);
}
function logDel(type,no,name,remarks){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	log("删除",type,no,name,remarks);
}
function logPlDel(type,no,name,remarks){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	log("批量删除",type,no,name,remarks);
}
function log(l_type,type,no,name,remarks){
	var log=initLog();
	log.obj_type=type;
	log.oper_type=l_type;
	log.obj_no=no;
	log.obj_name=name;
	log.oper_remarks=remarks;
	LogSrv.addLogByVo(log);
}
function logsPl(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("审批印模",type,no,name,remarks,model);
}
function logs(l_type,type,no_sel,name_sel,remarks){
	var log=initLog();
	log.obj_type=type;
	log.oper_type=l_type;
	log.no_sel=no_sel;
	log.name_sel=name_sel;
	log.oper_remarks=remarks;
	LogSrv.addLogs(log);
}

function logs2(l_type,type,no_sel,name_sel,remarks,model){
	var log=initLog2(model);
	log.obj_type=type;
	log.oper_type=l_type;
	log.no_sel=no_sel;
	log.name_sel=name_sel;
	log.oper_remarks=remarks;
	LogSrv.addLogs(log);
}
function logsPls(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("通过申请使用印章",type,no,name,remarks,model);
}
function RlogsPls(type,no,name,remarks,model){
	if (typeof (remarks) == "undefined") {
		remarks = "";
	}
	if (typeof (model) == "undefined") {
		model = "";
	}
	logs2("拒绝通过申请使用印章",type,no,name,remarks,model);
}


