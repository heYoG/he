function giveUpUpdate(){
	//location.href="login!userInfo.action";
	window.history.go(-1);//返回上一页面
}

function updateUser(rightPWD){
	var oldP=document.getElementById("oldPWD").value;
	var newP=document.getElementById("newPWD").value;
	var repP=document.getElementById("repeatPWD").value;
	if(formName.userName.value==''){
		alert("姓名不能为空!");
		return false;
	}
	if(formName.userAge.value==''){
		alert("年龄不能为空!");
		return false;
	}
	if(oldP!=rightPWD){
		alert("旧密码输入错误!");
		return false;
	}
	if(newP!=''&&repP!=''){
		if(repP!=newP){
			alert("两次输入的新密码不一致，请重新输入!");
			return false;
		}		
	}else{
		alert("新密码输入不能为空!");
		return false;
	}
	if(confirm("确认修改?")){
		formName.submit();
	}
}