<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css" />
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/menu_left.css" />
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/tree.css">
		<script type="text/javascript">
		var a="${loginUser.area_type}";
		var area_no="${loginUser.area_no}";
		if(a==""){
			//top.location="/Seal/login.jsp";
		}
		</script>
		<script language="JavaScript" src="/Seal/js/menu_left.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script language="JavaScript" src="/Seal/dtree/dtree.js"></script>
		<script language="JavaScript" src="/Seal/js/util.js"></script>
		<script language="JavaScript" src="/Seal/js/dept_tree.js"></script>
	</head>
	<body onload="load();" class="bodycolor">
		<CENTER>
			<table width="993" height="75">
				<tr>
					<td>
						<ul>
							<li>
								<a href="" onclick="return false;" target="right_main"
									title="点击伸缩列表" id="link_1"><span><img alt="部门列表"
											src="/Seal/images/menu/comm.gif" height="18"></img>&nbsp;部门列表</span>
								</a>
							</li>
							<li id="mng_list" style="overflow-x: scroll;">
								<div id="tree" class="moduleContainer treeList"></div>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</CENTER>
	</body>
</html>