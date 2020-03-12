var titleExist=false; //字典名称

var fileExixt=false;

function myCheck(f,type){
	
	if(f.ad_title.value.Trim()==""){
		alert("广告标题不能为空！");
		return false;
	}
	if (typeof(type) == "undefined") {
	
		type = "new";
	}
	if(type=="edit"){
	}else{
		dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
		AdSystem.checkTitle(f.ad_title.value,cb_title_exist);
		if(!titleExist){
			alert("已经存在标题，请重输！");
			return false;
		}
	}
	
	if(!checkStr(f.ad_title.value,"广告标题")){
		return false;
	}
	
	/*if(f.ad_filename.value==""){

		alert("广告文件不能为空！");
		return false;
	}*/

	var filenames=document.getElementsByName("ad_filename");
	for(var i=0;i<filenames.length;i++){
		//if(filenames[1].value==""){
			//alert("广告文件不能为空！");
		//}else{
			if(type=="edit"){
			}else{
				dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
				var file = filenames[i].value;
				file = file.substring(file.lastIndexOf("\\")+1);
				//alert(file);
				AdSystem.checkFileName(file,cb_file_exist);
				
				if(fileExixt){
					//alert(file+"图片已经存在，请重新选择！");
					document.getElementsByName("aa")[i].value=file+"图片已经存在，请重新选择！";
					document.getElementsByName("aa")[i].style.display="block";
					return false;
				}
				document.getElementsByName("aa")[i].style.display="none";
				var suffixname=file.substring(file.indexOf("."));
				//alert(suffixname);
				if(suffixname!=".jpg"){
					alert("不支持"+file+"的格式，请重新选择！");
					return false;
				}
			}
	}
	
	
	if(!checkStr(f.ad_dept.value,"部门单位")){
		return false;
	}
	if(f.dept_name.value==""){

		alert("部门单位不能为空！");
		return false;
	}
	if(f.starttime.value==""){
		alert("生效日期不能为空");
		return false;
	}
	if(f.endtime.value==""){
		alert("失效日期不能为空");
		return false;
	}
	var scrollTime = parseInt(f.scorlltime.value);
	if(document.getElementById("scorlltime").value==""){
		alert("请输入广告轮训播放时间");
		return false;
	}
	if(scrollTime <= 1){
		alert("轮播时间必须大于1秒");
		return false;
	}

	if(isNaN(scrollTime)){// add 20180502
		alert("轮播时间必须是数字");
		return false;
	}
	
	return true;
}

function cb_title_exist(ret){
	titleExist = ret;
}

function cb_file_exist(ret){
	fileExixt = ret;
}

/**
 * 上传广告文件
 * @return
 */
function fileUpLoad(){
	var obj =$("f_new");
	var dept = document.getElementById("dept").value;
	var title = document.getElementById("title").value;
	var dept_no = document.getElementById("dept_no").value;
	var approve_user=document.getElementById("approve_user").value;
	var approve_name=document.getElementById("approve_name").value;
	var scorlltime=document.getElementById("scorlltime").value;
	//var filenamepath=document.getElementById("filenamepath").value;
	//alert(filenamepath);
	if(myCheck(obj)){
		//obj.action="uploadFile.jsp?name1="+name1+"&name2="+name2+"&name3="+name3+"&dept="+dept+"&title="+title+"&dept_no="+dept_no+"&approve_user="+approve_user;
		obj.action="uploadFile.jsp?dept="+dept+"&title="+title+"&dept_no="+dept_no+"&approve_user="+approve_user+"&scorlltime="+scorlltime;
		//obj.action="uploadFile.jsp";
		obj.submit();
	}
}
