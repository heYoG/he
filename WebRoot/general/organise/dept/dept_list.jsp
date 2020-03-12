<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="../../../inc/tag.jsp"%>
<html>
	<head>
		<title></title>

		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/style.css" />
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/menu_left.css" />
		<script language="JavaScript" src="js/menu_left.js"></script>
		<script language="JavaScript" src="js/hover_tr.js"></script>

		<script language="JavaScript" src="dtree/dtree.js"></script>
		<link rel="stylesheet" type="text/css" href="theme/${current_user.user_theme}/tree.css">
	</head>
	<body>
		<ul>
			<li>
				<a href="" onclick="clickMenu('1');return false;" target="dept_main"
					title="点击伸缩列表" id="link_1" class="active"><span>部门列表</span> </a>
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
				
			d.add(change('${unit.dept_no}'),-1,'${unit.unit_name}','showDept.do','${unit.unit_name}','dept_main','returnf(\'${unit.unit_name}\',\'0000\');return false;');
			<c:forEach items="${depts}" var="dept">
				d.add(change('${dept.dept_no}'),change('${dept.dept_parent }'),
					'${dept.dept_name }','#',
					'${dept.dept_name }','dept_main',
					'return selectDept(\'${dept.dept_no }\',\'${dept.dept_name }\',\'${dept.inManage }\');');
			</c:forEach>		
			document.write(d);	
			//document.write(d.toString());	
			d.closeAll();
						
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
			
			function selectDept(dept_no,dept_name,inManage){
				if(inManage=="false"){
					alert("对不起，您没有管理这个部门的权限！");
				}
				var URL="showDept.do";
				if(dept_no!="a0"){
					URL="showDept.do?dept_no="+dept_no;
				}			
				self.parent.frames["dept_main"].location=URL;	
				return false;
			}
				</script>

				</div>
			</li>


		</ul>
	</body>
</html>

