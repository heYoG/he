<%@page contentType="text/html;charset=utf-8"%>
<html>
	<head>
		<title>电子印章平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/style.css" />
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/2/menu_left.css" />
		<link rel="stylesheet" type="text/css"
			href="/Seal/dtree/dtree.css">
		<script type="text/javascript">
		var a="${loginUser.area_type}";
		var area_no="${loginUser.area_no}";
		if(a==""){
			//top.location="/Seal/login.jsp";
		}
		function doButton(){
		afrom.manage_range.value=$("MdeptNo").value;
		afrom.name_range.value=$("MdeptName").value;
		this.close();
		}
		function doClear(){
		$("MdeptNo").value="";
		$("MdeptName").value="";
		}
		</script>
		<script language="JavaScript" src="/Seal/js/menu_left.js"></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SealBody.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysDept.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/SysFunc.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script language="JavaScript" src="/Seal/dtree/dtree.js"></script>
		<script language="JavaScript" src="/Seal/js/util.js"></script>
		<script language="JavaScript" src="/Seal/depttree/dept_tree.js"></script>
		
	</head>
	<body id="bd" onload="load();" class="bodycolor" onunload="ret();">
		<CENTER>
			<table width="100%">
				<tr>
					<td>
						<ul>
							<li>
								<a href="" onclick="return false;" target="right_main"
									title="点击伸缩列表" id="link_1"><span><img alt="部门列表"
											src="/Seal/images/menu/comm.gif" height="18"></img>&nbsp;部门列表</span>
								</a>
								<input type="text" id="bank_id" value=""><input type="button" value="查找" onclick="selectNode()">
							</li>
							<li id="mng_list">
							<div id="tree" class="moduleContainer treeList"></div>
							<div style="display:none" id="MdeptNameDiv">
							<input  type="hidden" id="MdeptNo" name="MdeptNo">
			                <textarea id="MdeptName" name="MdeptName" rows="4" cols="40" ></textarea>
							</div>
							</li>
						</ul>									
					</td>
				</tr>
				<tr style="display:none" id="MdeptNameDiv2">
				<td>
					<input  id="button" name="button" type="button" value="确定" class="BigButton" onclick="doButton();">
			        <input  id="button" name="button" type="button" value="清空" class="BigButton" onclick="doClear();">
					</td>
				</tr>
			</table>	
		</CENTER>
	</body>
</html>