<%@page contentType="text/html;charset=gbk"%>
<html>
	<head>
		<title>电子印章平台</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/menu_top.css">
		<script language="JavaScript" src="/Seal/js/menu_top.js"></script>
	</head>
	<body>
		<div id="navPanel">
			<div id="navMenu">
			<a href="/Seal/general/hotel/advert/ad_list_history.jsp"
					target="ctz_bi_main" title="广告记录"><!-- 点击后在ctz_bi_main框架打开 -->
					<span>
					<img src="/Seal/images/menu/seal.gif" width="16" height="16">
						广告记录
					</span>
			</a>
				<a href="/Seal/general/hotel/advert/new_ad.jsp"
					target="ctz_bi_main" title="新增广告"><!-- 点击后在ctz_bi_main框架打开 -->
					<span><img src="/Seal/images/menu/seal.gif" width="16" height="16">
						新增广告
					</span>
				</a>
			</div>
		</div>
	</body>
</html>