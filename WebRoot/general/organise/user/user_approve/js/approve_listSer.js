var user_no = "${current_user.user_id}";//用户名
function agree_app(unique_id) {// 同意
	var is_junior = document.getElementById("is_juniorS").value;// 是否允许下级
	var typeS = document.getElementById("typeS").value;// 查询类型
	var dept_no = document.getElementById("dept_noS").value;// 部门编号
	if (confirm("审核通过?")) {
		location = "serUserApprove.do?id='" + unique_id + "'&type=" + typeS
				+ "&dept_no=" + dept_no + "&flag=flag1&is_junior=" + is_junior+"&current_user="+user_no;
	}
}

function refuse_app() {// 拒绝
	var msg = "确认要退回此用户申请？请填写退回意见：";
	$("confirm").innerHTML = "<font color=red>" + msg + "</font>";
	ShowDialog('approve');
}

function doButton(unique_id) {
	var is_junior = document.getElementById("is_juniorS").value;
	var typeS = document.getElementById("typeS").value;
	var dept_no = document.getElementById("dept_noS").value;
	if ($("context").innerText == "") {
		alert("请填写审批意见！");
		return false;
	} else {
		var approve_text = document.getElementById("context").value;
		if (approve_text.length > 20) {
			alert("输入的意见长度不能超过20个字符!");
			return false;
		} else {
			approve_text = approve_text.replace(/\'/g, "‘");
			document.getElementById("approver_reason").value = approve_text;
		}
		location = "serUserApprove.do?id='" + unique_id + "'&type=" + typeS
				+ "&dept_no=" + dept_no + "&flag=flag2&is_junior=" + is_junior
				+ "&state=" + approve_text+"&current_user="+user_no;
	}
}

function edit_app(ID) {// 修改
	if (confirm('确认要修改该用户？')) {
		location = "editUser.do?user_id=" + ID + "&is_app=true";
	}
	;
}

function delete_app(ID) {// 删除
	if (confirm('确认要删除该用户？')) {
		dwr.engine.setAsync(true); // 设置方法调用是否同步，false表示同步
		LogSys.logAdd("${current_user.user_id}", "${current_user.user_name}",
				"${user_ip}", "用户管理", "删除用户:'" + ID + "'成功");// logSys.js
		SysUser.deleteSysUser(ID, callback);
	}
}

function callback(data) {
	window.location.href = "/Seal/general/organise/user/user_approve/query.jsp";
}

function approve_reason(reason) {// 审批意见
	alert(reason);
}

function show_sch() {// 返回查询页面
	window.location.href = 'general/organise/user/user_approve/query.jsp';
}
