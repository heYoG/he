function new_cert_load(){
//	alert();
	//dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//CertSrv.getSignCertStr(cb_certs);
}
function cb_certs(d){
	var certs=d.split(",");
	var sel=$("sign_cert");
	selectClean(sel);
	for(var i=0;i<certs.length;i++){
		selectAdd(sel,certs[i],certs[i]);
	}
}

function certSrcChg(sel){
	if(false){
	}else if(sel.value=="key"){
		disp("tr_key");
		hidden("tr_server");
		hidden("tr_sign");
	}else if(sel.value=="server"){
		hidden("tr_key");
		disp("tr_server");
		hidden("tr_sign");
	}else if(sel.value=="sign"){
		hidden("tr_key");
		hidden("tr_server");
		disp("tr_sign");
	}
}

function newObj(){
	var f=$("f_new");
	if(checkForm(f)){
		LogSys.logAdd(user_no,user_name,user_ip,"证书管理","新增证书："+f.cert_name.value);//logOper.js
		$("f_new").submit();
		
	}
}

var exist=false;
var certExist=false;
function checkForm(f){
	if(f.cert_name.value.Trim()==""){
		alert("证书名称不能为空！");
		f.cert_name.select();
		return false;
	}
	if(f.cert_psd.value.Trim()==""){
		alert("证书密码不能为空！");
		return false;
	}
	if(f.cert_psd1.value.Trim()==""){
		alert("证书确认密码不能为空！");
		return false;
	}
	if(f.cert_psd.value.Trim()!=f.cert_psd1.value.Trim()){
	    alert("两次输入的密码不一样，请重新输入！");
	    f.cert_psd.value="";
	    f.cert_psd1.value="";
		return false;
	}
//	if(f.cert_path.value.Trim()==""){
//		alert("请选择证书！");
//		return false;
//	}else if(f.cert_path.value.Trim().split(".")[1]!="pfx"){
//	    f.cert_path.value="";
//	    alert("请现在pfx证书上传！");
//	    return false;
//	}
	if (typeof (type) == "undefined") {
		type = "new";
	}
	dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
	//CertSrv.isCertExist(f.sign_cert.value,cb_certExist);
	//if(certExist==false){
	//	if(type=="edit"&&f.sign_cert.value==f.old_cert.value){
	//	}else{
		//	alert("此签名证书已经登记过了！");
		//	return false;
		//}
	//}else{
		//if(type=="edit"&&f.cert_name.value==f.old_name.value){
		//}else{
			dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			CertSrv.isExist(f.cert_name.value,cb_exist);
			if(exist){
				alert("已经存在的证书名称，请重输！");
				f.cert_name.select();
				return false;
			}
	//	}
//	}
	if(f.cert_src.value=="key"){
		if(f.cert_no.value==""){
			alert("证书序列号不能为空！");
			return false;
		}
		f.cert_detail.value=f.cert_no.value;
	}else if(f.cert_src.value=="server"){
		if(f.cert_path.value==""){
			alert("请选择证书路径！");
			return false;
		}else if(!chkServerCert(f.cert_path)){
			return false;
		}
		f.cert_detail.value=f.cert_name.value;
	}else{
		if(f.sign_cert.value.Trim()==""){
			alert("指定的签名服务器证书不能为空！");
			return false;
		}
		f.cert_detail.value=f.sign_cert.value;
	}
	f.reg_user.value=user_no;
	return true;
}
function cb_exist(b){
	exist=b;
}
function cb_certExist(b){
	certExist=b;
}

function chkServerCert(f){
	if(f.value.substr(f.value.lastIndexOf("."))!=".pfx"){
		alert("请选择正确的证书文件（.pfx）！");
		return false;
	}
	return true;
}

function getCertNo(){
	alert("请插入KEY！");
}