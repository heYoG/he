<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<title>调整角色序号高低</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script>

  	 // 用于将fbox下拉框的值移到tbox下面，
	function move(fbox,tbox){
		//对fbox遍历
		   for(var i=0; i<fbox.options.length; i++) 
		    {
		    	//判断是不是选中且值不为空
		     if(fbox.options[i].selected && fbox.options[i].value != ""){
		     	//创建一个新的下拉项
		    	var no = new Option();
		        no.value = fbox.options[i].value;
		        no.text = fbox.options[i].text;
		        no.label = fbox.options[i].label;
		        no.selected="selected";
		        //下面来判断在tbox中间是不是已经有了这个值
		        if(no.label=="系统管理员" && isExist(tbox,"日志审计员")){
		        	alert("不能同时赋于用户'系统管理员'和'日志审计员'角色！");
		        }else if(no.label=="日志审计员" && isExist(tbox,"系统管理员")){
		        	alert("不能同时赋于用户'系统管理员'和'日志审计员'角色！");
		        }else if(!isExist(tbox,no.label))
		        	tbox.add(no);
		     }
		   }
		}
		//用来移出选中的列表项
		function remove(box,fbox)  {
		   for(var i=0; i<box.options.length; i++) 
		    {
		     if(box.options[i].selected) 
		     {
	     		if(isExist(fbox,box.options[i].label)){
		     	   box.remove(i);
		     	}else{
		     		alert('对不起，您没有权限删除这个角色！');
		     	}
		     }
		   }
		}
		//判断fbox里是否已经存在label为l的option
		function isExist(fbox,l){
			for(i=0;i<fbox.options.length;i++){
				if(fbox.options[i].label==l){
					return true;
				}
			}
			return false;
		}

function mysubmit()
{
	var dog=document.getElementById("dog");
	var ret="";
	var ret2="";
  sel_count=dog.options.length;
  for (i=0; i<dog.options.length; i++)
  {
	    ret=ret+dog.options(i).label+',';
	    ret2=ret2+dog.options(i).value+',';
  }
    if(sel_count==0)
  {
     alert("请至少选择其中一项！");
     return;
  }
  var ptextid = window.dialogArguments;
	if(ptextid != undefined){
	   //  alert(ret2);
		ptextid.role_nos.value = ret2;
	}
  
  //模式窗口的返回值
	window.returnValue=ret;
	self.close();
	return;
}
			function ret(){
				if(!window.returnValue)
					window.returnValue='default';
			}
			function load(){
				var ptextid = window.dialogArguments;
				var role_nos;
				var role_names;
				if(ptextid != undefined){
					role_nos=ptextid.role_nos.value.split(",");
					role_names=ptextid.role_names.value.split(",");
					  // alert(role_nos+role_names);
				}
				for(i=0;i<role_nos.length;i++){
					var no = new Option();
		        	no.value = role_nos[i];
		       	 	no.text = role_names[i];
		       	 	no.label = role_names[i];
		        	no.selected="selected";
		        	if(!no.label=="")
		        		form1.dog.add(no);
				}
			}
</script>
	</head>
	<body class="bodycolor" topmargin="5" onunload="ret()" onload="load()">
		<form action="" name="form1">

			<table class="TableBlock" width="300" align="center">
				<tr class="TableHeader">
					<td align="center" colspan="3">
						<b>角色调整</b>
					</td>
				</tr>
				<tr class="TableData">
					<td valign="top" align="center">
						备选角色：
						<br />
						<select id="select1" name="pig" MULTIPLE style="width: 200; height: 280">
							<c:forEach var="aa" items="${roles1 }" varStatus="status">
								<option value="${aa.role_id }" no="${aa.role_tab }"
									label="${aa.role_name }">
									${aa.role_name }
									
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="center" width="10">
						<input type="button" value=&gt;
							onclick="javascript:move(this.form.pig,this.form.dog);"
							style="font: 9pt 宋体; width: 30px; height: 30px; margin: 0px; padding: 0px; color: #333333; background: #CCCCCC; border: 0px solid #0000FF; border: 3px double #DDDDDD; cursor: pointer;" />
						<br>
						<input type="button" value=&lt;
							onclick="javascript:remove(this.form.dog,this.form.pig);"
							style="font: 9pt 宋体; width: 30px; height: 30px; margin: 0px; padding: 0px; color: #333333; background: #CCCCCC; border: 0px solid #0000FF; border: 3px double #DDDDDD; cursor: pointer;" />
					</td>
					<td valign="top" align="center">
						已选角色：
						<br />
						<select id="dog" name="dog" MULTIPLE style="width: 200; height: 280">
						</select>
					</td>
				</tr>
				<tr class="TableControl">
					<td align="center" valign="top" colspan="3">
						<input type="button" class="BigButton" value="确 定"
							onclick="mysubmit();">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
