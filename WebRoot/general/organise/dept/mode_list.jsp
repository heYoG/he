<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css" href="theme/2/style.css" />
		<link rel="stylesheet" type="text/css" href="theme/2/menu_left.css" />
		<script language="JavaScript" src="js/menu_left.js"></script>
		<script language="JavaScript" src="js/hover_tr.js"></script>

		<script language="JavaScript" src="dtree/dtree.js"></script>
	</head>
	<body onunload="ret();">
		<ul>
			<li>
				<a href="javascript:;" onclick="return false;"
					target="dept_main" title="点击伸缩列表" id="link_1" class="active"><span>部门列表</span>
				</a>
			</li>
			<li>
				<div id="module_1" class="moduleContainer treeList" style="">
					<div id="tree"></div>
					<script language='JavaScript'>
			d = new dTree('d');
			//设置状态栏
			//d.config.useStatusText=true;
			//设置是不是关闭同一层的其他节点
			//d.config.closeSameLevel=true;
			//是不是可以使用cookie
			//d.config.useCookies=false;
	
			d.add(change('${unit.dept_no}'),-1,'${unit.unit_name}','#','${unit.unit_name}','dept_main','returnf(\'${unit.unit_name}\',\'0000\');return false;');
			<c:forEach items="${depts}" var="dept">
				d.add(change('${dept.dept_no}'),change('${dept.dept_parent }'),'${dept.dept_name }','#','${dept.dept_name }','dept_main','returnf(\'${dept.dept_name}\',\'${dept.dept_no}\');return false;');
			</c:forEach>	
			document.write(d);		
			
			function change(str){
				var strSource ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
				var re=0;
				var j=1;
				for(i=str.length-1;i>=0;i--){
					re=re+strSource.indexOf(str.charAt(i))*j;
					j=j*100;
				}
				return re;
			}	
			
			function ret(){
				if(!window.returnValue)
					window.returnValue='default';
			}
			function returnf(dept_name,dept_no) {
				var no=window.dialogArguments;
				if(no!=undefined){
					no.value=dept_no;
				}
				//模式窗口的返回值
				window.returnValue=dept_name;
				self.close();
				return;
			}
				</script>

				</div>
			</li>

		</ul>

	</body>
</html>

