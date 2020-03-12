document.onkeydown=function (){
	if(event.keyCode==13)
		document.getElementById("btID").click();
}

function login(){
	var user=document.getElementById("userId").value;
	var pwd=document.getElementById("pwdId").value;
	if(user==null||user==""){
		alert("用户名不能为空!");
		return false;
	}
	if(pwd==null||pwd==""){
		alert("用户密码不能为空!")
		return false;
	}
	form_login.submit();
}