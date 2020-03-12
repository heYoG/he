<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css" />
		<script type='text/javascript' src='/Seal/dwr/interface/modelFileSrv.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/utility.js"></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/module.js"></script>
		<script src="/Seal/js/attach.js"></script>
		<script src="/Seal/general/model_file/model_mng/js/set_filed.js"
			type="text/javascript"></script>
	</head>
	<body  style="margin: 0; padding: 0;">
		<form name="form1">
			<table class="TableList" width="100%" height="100%" align="center"
				style="margin: 0;">
				<tr>
					<td nowrap class="TableControl" colspan=2 align="left">
						<img src="/Seal/images/green_arrow.gif" WIDTH="20" HEIGHT="18">
						请填写属性：
					</td>
				</tr>
				<tr style="display:none">
					<td class="TableHeader" noWrap colSpan="2">区域属性：</td>
				</tr>
				<tr style="display:none">
					<td class="TableData">区域类型</td>
					<td class="TableData">
					<select id="ITEM_TYPE" name="ITEM_TYPE" onchange="change_ITEM_TYPE();"> 
						<option value="3" selected>编辑区域</option>
						<option value="2" >手写区域</option>
					</select>
					</td>
				</tr>
				<tr id="tr_is_tongyong" style="display:none">
					<td nowrap class="TableData" width="100">
						使用通用名称
					</td>
					<td  nowrap class="TableData">
						<input  type="radio" name="is_tongyong" value="1"  onclick="changeTongYong();">是
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input  type="radio" name="is_tongyong" value="2" checked="checked" onclick="changeTongYong();">否
					</td>
				</tr>
				<tr id="tongyong" style="display:none">
					<td class="TableData">通用名称</td>
					<td class="TableData">
						<select id="ITEM_NAME" name="ITEM_NAME">
						</select>
					</td>
				</tr>
				<tr id="nottongyong">
					<td class="TableData">输入名称</td>
					<td class="TableData">
						<input id="writeName" name="writeName" size="13" />
					</td>
				</tr>
				<tr id="tr_ITEM_DATA_TYPE">
					<td class="TableData">字段类型</td>
					<td class="TableData">
						<select id="ITEM_DATA_TYPE" name="ITEM_DATA_TYPE">
							<option value="文本" selected>文本</OPTION>
							<option value="密码">密码</OPTION>
							<option value="整数">整数</OPTION>
							<option value="小数">小数</OPTION>
							<option value="大写金额">大写金额</OPTION>
							<option value="日期">日期</OPTION>
							<option value="图片">图片</OPTION>
						</select>
					</td>
				</tr>
				<tr id="tr_IS_READ_ONLY">
					<td class="TableData">是否只读</td>
					<td class="TableData">
						<select id="IS_READ_ONLY" name="IS_READ_ONLY">
							<option value="0" selected>否</OPTION>
							<option value="3">是</OPTION>
						</select>
					</td>
				</tr>
				<tr id="tr_TIPSINFO">
					<td class="TableData">输入提示</td>
					<td class="TableData">
					<input id="TIPSINFO" name="TIPSINFO" size="13" />
					</td>
				</tr>
				<tr id="tr_FONT_TYPE">
					<td class="TableData">字体</td>
					<td class="TableData">
					<select id="FONT_TYPE" name="FONT_TYPE"> 
						<option value="宋体" selected>宋体</option>
						<option value="黑体" >黑体</option>
						<option value="楷体" >楷体</option>
						<option value="隶书" >隶书</option>
						<option value="幼圆" >幼圆</option>
						<option value="Arial" >Arial</option>
					</select>
					</td>
				</tr>
				<tr id="tr_FONT_SIZE">
					<td class="TableData">文字大小</td>
					<td class="TableData">
					<select id="FONT_SIZE" name="FONT_SIZE"> 
						<option value="五号" selected>五号</option>
						<option value="小四" >小四</option>
						<option value="四号" >四号</option>
						<option value="小三" >小三</option>
						<option value="三号" >三号</option>
						<option value="小二" >小二</option>
						<option value="二号" >二号</option>
						<option value="小一" >小一</option>
						<option value="一号" >一号</option>
					</select>
					</td>
				</tr>
				<tr id="tr_FONT_COLOR">
					<td class="TableData">字体颜色</td>
					<td class="TableData">
					<select id="FONT_COLOR" name="FONT_COLOR"> 
						<option value="0" selected>黑色</option>
						<option value="255">红色</option>
					</select>
					</td>
				</tr>
				<tr id="tr_BORDER_TYPE" style="display:none">
					<td class="TableData">边框样式</td>
					<td class="TableData">
					<select id="BORDER_TYPE" name="BORDER_TYPE"> 
						<option value="0" selected>无边框</option>
						<option value="1">3D边框</option>
						<option value="2">实线边框</option>
						<option value="3">下滑下边框</option>
					</select>
					</td>
				</tr>
				<tr id="tr_FONT_X">
					<td class="TableData">文字水平对齐方式</td>
					<td class="TableData">
					<select id="FONT_X" name="FONT_X"> 
						<option value="0" selected>左对齐</option>
						<option value="1">居中对齐</option>
						<option value="2">右对齐</option>
					</select>
					</td>
				</tr>
				<tr  id="tr_FONT_Y">
					<td class="TableData">文字垂直对齐方式</td>
					<td class="TableData">
					<select id="FONT_Y" name="FONT_YE"> 
						<option value="0">上对齐</option>
						<option value="1" selected>纵居中</option>
						<option value="2">下对齐</option>
					</select>
					</td>
				</tr>
				<tr class="TableControl">
					<td align="center" colspan=2>
						<input type="button" class="SmallButton" onclick="setParent();"
							value="确定">
							&nbsp;&nbsp;
						<input type="button" class="SmallButton"
							onclick="parent.HideDialog('setFrm')" value="关闭">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>