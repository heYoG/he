<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/pageSplit/pageSplit.css">
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/dialog.css">
		<script type='text/javascript' src='/Seal/dwr/interface/LogSys.js'></script>
		<script type='text/javascript' src='/Seal/dwr/interface/CertSrv.js'></script>
		<script type='text/javascript'
			src='/Seal/dwr/interface/GaiZhangApp.js'></script>
		<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
		<script type='text/javascript' src='/Seal/dwr/util.js'></script>
		<script src="/Seal/js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="/Seal/js/logOper.js"></script>
		<script type='text/javascript' src='/Seal/js/String.js'></script>
		<script src="/Seal/js/dateUtil.js"></script>
		<script src="/Seal/js/tableUtil.js"></script>
		<script src="/Seal/js/dialog.js"></script>
		<script type="text/javascript" src="/Seal/js/pageSplit.js"></script>
		<script src="/Seal/general/cert/cert_reg/js/new_cert.js"></script>
		<script type="text/javascript">
		var user_no="${current_user.user_id}";
		var user_name="${current_user.user_name}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
		function bindCert(seal_id,cert_id,cert_name,seal_name){ 
			if(cert_id != ""){
				if(confirm("确定要绑定证书吗？")){
				location="sealBind.do?type=1&&seal_id="+seal_id+"&&cert_id="+cert_id;
				LogSys.logAdd(user_no,user_name,user_ip,"印章管理","印章"+seal_name+"绑定证书："+cert_name);//logOper.js
				}
			}else{
				if(confirm("确定要解除绑定吗？")){
				location="sealBind.do?type=2&&seal_id="+seal_id+"&&cert_id="+cert_id;
				LogSys.logAdd(user_no,user_name,user_ip,"印章管理","印章"+seal_name+"解除绑定证书："+cert_name);//logOper.js
			}
			
	}
}
		</script>
	</head>
	
	<body class="bodycolor" style="margin-top: 0;">
		<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big">&nbsp;<img src="images/menu/comm.gif" align="absmiddle"><span class="big3"> <a name="bottom">证书列表--当前证书：<font color=red>[${cert_name}]</font></span>
    </td>
     <td align="right" style="display: none"><br><br><span class="big3"><a name="bottom" href="serUserManager.do?type=all">[所有证书列表]</a></span>
	<br><br><br><br></td>
  </tr>
</table>
 <c:if test="${pageSplit.datas=='[]' }">
   <table class="MessageBox" align="center" width="290">
			<tr>
				<td class="msg info">
					<div class="content" style="font-size: 12pt">
						暂无相应记录！
					</div>
				</td>
			</tr>
		</table>
   </c:if>
		<c:if test="${pageSplit.datas!='[]' }">
			<table class="TableList" width="100%" style="margin-top: 0;">
				<tr class="TableHeader">
					<td nowrap align="center">
						证书名称
					</td>
					<td nowrap align="center">
						证书来源
					</td>
					<td nowrap align="center">
						登记时间
					</td>
					<td nowrap align="center">
						操作
					</td>
					
				</tr>

				<c:forEach var="aa" items="${pageSplit.datas }" varStatus="status">
					<tr class="TableLine1">
						
						<td nowrap align="center">
					<!--		<c:if test="${aa.cert_name ==cert_name }">
							<font color=red>${aa.cert_name }</font>
							</c:if>
							<c:if test="${aa.cert_name !=cert_name }">
							<font color=black>${aa.cert_name }</font>
							</c:if>   -->	
							${aa.cert_name }	
						</td>
							<td align="center">
							<c:if test="${aa.cert_src =='server' }">
							服务器
							</c:if>
							<c:if test="${aa.cert_src =='sign' }">
							加密机
							</c:if>
							<c:if test="${aa.cert_src =='clientPFX' }">
							客户端pfx证书
							</c:if>
							<c:if test="${aa.cert_src =='clientCert' }">
							客户端公钥证书
							</c:if>
						</td>
						<td nowrap align="center">
							${aa.reg_time_str }
						</td>
						<td nowrap align="center">
						<c:if test="${aa.cert_name ==cert_name }">
						<a href="javascript:;"
									onClick="bindCert('${seal_id }','','${aa.cert_name }','${seal_name }'); return false;"><font color=red>解除绑定</font></a>&nbsp;
					</c:if>
					<c:if test="${aa.cert_name !=cert_name }">
						<a href="javascript:;"
									onClick="bindCert('${seal_id }','${aa.cert_no }','${aa.cert_name }','${seal_name }'); return false;">绑定</a>&nbsp;
					</c:if>
					</td>
					</tr>
				</c:forEach>

				<tr class="TableLine1">
					<td colspan="10" class="pager">
						<div class="pager" align="right">
							共${pageSplit.totalCount }条记录 每页 ${pageSplit.pageSize } 条
							第${pageSplit.nowPage } 页/共${pageSplit.totalPage }页
							<c:if test="${pageSplit.nowPage==1}">
										第一页，上一页，
									</c:if>
							<c:if test="${pageSplit.nowPage!=1}">
								<a href="cerModeList.do?pageIndex=1&&seal_id=${seal_id}">第一页</a>，
										<a
									href="cerModeList.do?pageIndex=${pageSplit.nowPage-1}&&seal_id=${seal_id}">上一页</a>，
									</c:if>
							<c:if test="${pageSplit.nowPage==pageSplit.totalPage}">
										下一页，最后一页
									</c:if>
							<c:if test="${pageSplit.nowPage!=pageSplit.totalPage}">
								<a
									href="cerModeList.do?pageIndex=${pageSplit.nowPage+1}&&seal_id=${seal_id}">下一页</a>，
										<a
									href="cerModeList.do?pageIndex=${pageSplit.totalPage}&&seal_id=${seal_id}">最后一页</a>
							</c:if>
							转到
							<input size="2" id="pageindex" />
							页
							<button width="20" onclick="checkPage();">
								GO
							</button>
						</div>
					</td>
				</tr>
			</table>
		</c:if>
		<table width="100%" style="margin-top: 0;">
				<tr>
						<td align="center">
							<br>
							<input type="button" value="返回列表" class="BigButton"
								onclick="history.back(-1)" />
						</td>
					</tr>
		</table>
	</body>
</html>