<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
	<head>
		<title>调整角色序号高低</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv=“X-UA-Compatible” content=“IE=6″>
		<link rel="stylesheet" type="text/css" href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
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
		        no.label = fbox.options[i].label;
		        no.selected="selected";
		        //下面来判断在tbox中间是不是已经有了这个值
		        if(!isExist(tbox,no.label))
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
	var xieyi_id=document.getElementById("xieyi_id").value;
	var xieyi_dept_no=document.getElementById("xieyi_dept_no").value;
	var ret2="";
  sel_count=dog.options.length;
  for (i=0; i<dog.options.length; i++)
  {
	   ret2=ret2+dog.options(i).value+',';
  }
  	dwr.engine.setAsync(false);
   	modelFileSrv.setModelFileXieyi(xieyi_id,ret2,xieyi_dept_no);
 
  //模式窗口的返回值
	self.close();
	return;
}
</script>
	</head>
	<body class="bodycolor" topmargin="5"  >
		<form action="" name="form1">

			<table class="TableBlock" width="300" align="center">
				<tr class="TableHeader">
					<td align="center" colspan="3">
						<b>协议模版对应关系配置</b>
						<input type="hidden" id="xieyi_id" name="xieyi_id" value="${xieyi_id}">
						<input type="hidden" id="xieyi_dept_no" name="xieyi_dept_no" value="${xieyi_dept_no}">
					</td>
				</tr>
				<tr class="TableData">
					<td valign="top" align="center">
						备选模版：
						<br />
						<select name="pig" MULTIPLE style="width: 200; height: 280">
							<c:forEach var="aa" items="${list1 }" varStatus="status">
								<option value="${aa.model_id }" label="${aa.model_name }">
									${aa.model_name }
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
						已选模版：
						<br />
						<select name="dog" MULTIPLE style="width: 200; height: 280">
							<c:forEach var="aa" items="${list2 }" varStatus="status">
								<option value="${aa.model_id }" label="${aa.model_name }">
									${aa.model_name }
								</option>
							</c:forEach>
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
