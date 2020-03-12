<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>调整用户</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/menu_left.css" />
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="theme/${current_user.user_theme }/tree.css">

		<script type="text/javascript" src="dtree/dtree.js"></script>
		<script language="JavaScript" src="js/menu_left.js"></script>
		<script language="JavaScript" src="js/hover_tr.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUnit.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>

		<script>
		    var $=function(id){return document.getElementById(id);}//定义获得表单对象的快速方法
	        var d=new dTree('d');//定义一颗全局树
			var doc_no="";
			var list=new Array();dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
			/*页面装载*/
		  function ret(){
			if(!window.returnValue)
			window.returnValue='default';
			}
		 /*页面装载*/
           function load(){
          	    dwr.engine.setAsync(false);
				SysDept.isInManageList('000','${current_user.user_id}',call);//设置单位是否在管理范围内
          	    SysUnit.showSysUnit(callBack);//获得单位信息
				var ptextid = window.dialogArguments;
				var user_id;
				var user_name;
				if(ptextid != undefined){
					user_id=ptextid.user_id.value.split(",");
					user_name=ptextid.user_name.value.split(",");
				}
				for(i=0;i<user_id.length;i++){
					var no = new Option();
		        	no.value = user_id[i];
		       	 	no.text = user_name[i];
		       	 	no.label = user_name[i];
		        	no.selected="selected";
		        	if(!no.label=="")
		        		form1.dog.add(no);
				}
			}
			
			/*回调函数*/
			function call(data){
				if(data==0){
					$("inManage").innerHTML="false";
				}
				else
				{
				$("inManage").innerHTML="true";
				}
			}
				/*获得树的回调函数1*/
			function callBack(unit){
				if(unit){
					/*为树加入根节点*/
					d.add(change(unit.dept_no),-1,unit.unit_name,
						' ',unit.dept_no,'dept_main',
						'return selectDept(\''+unit.dept_no+'\',\''+unit.unit_name+'\',\''+$('inManage').innerHTML+'\');');			
					/*根据用户名称获得其拥有的部门树*/
					SysDept.deptTreeByUser('${current_user.user_id }',callTree);
				}
			}
			/*获得树的回调函数2*/
			function callTree(depts){
				if(depts){
					for(var i=depts.length-1;i>=0;i--){
						d.add(change(depts[i].dept_no),
							change(depts[i].dept_parent),
							depts[i].dept_name,
							' ',depts[i].dept_no,
							'dept_main',
							'return selectDept(\''+depts[i].dept_no+'\',\''+depts[i].dept_name+'\',\''+depts[i].inManage+'\');');
					}
					$("tree").innerHTML=d.toString();
				}
			}
			/*单击部门时触发事件*/
			function selectDept(dept_no,dept_name,dept_inManage){
				if(!dept_inManage||dept_inManage=="false"){
					alert("对不起，您没有管理这个部门的权限！");
					return false;
				}
				SysUser.showSysUsersByDept_no(dept_no,callUser);
			   document.getElementById("dept_name").value=dept_name;
			   document.getElementById("dept_name").style.color="red";   
			   return false;
			}
			/*回调函数*/
			function callUser(data)
			{
			var del=document.getElementById("pig");	
			del.options.length=0;	
			dwr.util.addOptions("pig",data,'user_id','user_name');	
			}
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
		        no.label = fbox.options[i].text;		        
		        no.selected="selected";
		        if(!isExist(tbox,no.value))
		        	tbox.add(no);	
		     }
		   }
		}
		//用来移出选中的列表项,将box中的项移出至fbox
		function remove(box,fbox)  {
		   for(var i=0; i<box.options.length; i++) 
		    {
		     if(box.options[i].selected) 
		     {
	     		if(isExist(fbox,box.options[i].value)){
		     	   box.remove(i);
		     	}else{
		     		alert('对不起，您没有权限移除这个用户！');
		     	}
		     }
		   }
		}
		//判断fbox里是否已经存在label为l的option
		function isExist(fbox,l){
			for(i=0;i<fbox.options.length;i++){		
				if(fbox.options[i].value==l){
					return true;
				}
			}
			return false;
		}
/*确认提交*/
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
    
  var ptextid = window.dialogArguments;
	if(ptextid != undefined){
		ptextid.user_id.value = ret2;
		ptextid.user_name.value=ret;
	}
	self.close();
	return;
}
	/*把字符类型转换成数字类型*/
			function change(str){
				var strSource ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
				var re=0;
				var j=1;
				for(var i=str.length-1;i>=0;i--){
					re=re+strSource.indexOf(str.charAt(i))*j;
					j=j*100;
				}
				return re;
			}		
</script>
	</head>
	<body class="bodycolor" topmargin="5" onunload="ret()" onload="load()">

		<table width="300" align="center">
			<tr>
				<td>
					<div id="tree" class="moduleContainer treeList"></div>

				</td>
			</tr>
			<tr>
				<td>
					<form action="" name="form1">

						<table class="TableBlock" width="300" align="center">


							<tr class="TableHeader">
								<td align="center" colspan="3">
									<b>用户调整</b>
								</td>
							</tr>
							<tr class="TableHeader">
								<td align="left" colspan="3">
									<b><input type="text" name="dept_name" readonly>
									</b>
								</td>
							</tr>
							<tr class="TableData">
								<td valign="top" align="center">
									备选用户：
									<br />
									<select name="pig" id="pig" MULTIPLE
										style="width: 200; height: 280">

									</select>
									<select name="userAll" id="userAll" MULTIPLE
										style="width: 200; height: 280; display: none;">
										<c:forEach var="user" items="${list_user}">
											<option value="${user.user_id }" label="${user.user_name }">
												${user.user_name }
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
										onclick="javascript:remove(this.form.dog,this.form.userAll);"
										style="font: 9pt 宋体; width: 30px; height: 30px; margin: 0px; padding: 0px; color: #333333; background: #CCCCCC; border: 0px solid #0000FF; border: 3px double #DDDDDD; cursor: pointer;" />
								</td>
								<td valign="top" align="center">
									已选用户：
									<br />
									<select name="dog" MULTIPLE style="width: 200; height: 280">
									</select>
								</td>
							</tr>
							<tr class="TableControl">
								<td align="center" valign="top" colspan="3">
									<div id="inManage" style="display: none;"></div>
									<input type="button" class="BigButton" value="确 定"
										onclick="mysubmit();">
									&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</form>
				</td>
			<tr>
	</body>
</html>
