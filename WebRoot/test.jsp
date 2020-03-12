<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script type="text/javascript">
		function uploadFile(){
		   //var pdfInfo="123";
		    var pdfInfo="<title>中国联合网络通信有限公司昆明市分公司综合业务受理单</><shouliNo>2014719131142883</><companyname>中国联合网络通信有限公司昆明市分公司</><DJ_TEMP_ID>1</><DJ_IN_CRETIME>2014-7-19 13:13:50</><guestname>杨益兰</><cardNo>530103196003060320</><phoneNo>18502450014</><DJ_CONTENT>客户基本信息：客户名称杨益兰客户类型公众证件类型18位身份证证件号码530103196003060320证件有效期20370101证件地址云南省昆明市盘龙区东风东路巷93号2栋1单元203号联系人杨益兰联系电话63384133通信地址昆明市官渡区郭家凹省第一监狱业务受理信息：订单编号：用户类型3G普通用户用户号码（账号）18502450014套餐信息1、套餐名称：A类3G基本套餐66元档。2、套餐当月生效方式为：套餐包外资费：入网当月免收月租费，其他按套餐包外资费执行。3、套餐信息：套餐月费66元，套餐包含国内语音拨打分钟数50分钟，国内流量300MB，国内短信发送条数240条，国内接听免费（含可视电话）。套餐超出后，国内语音拨打0.20元/分钟，国内流量0.0003元/KB，国内可视电话拨打0.60元/分钟。套餐赠送多媒体内容6个M、文本内容10个T、国内可视电话拨打分钟数10分钟，以及来电显示和手机邮箱，其他执行标准资费。4、客户每月使用手机上网流量达到6GB后，达到封顶流量。终端信息1、终端型号：您选择的是（诺基亚NokiaN5235黑）终端。2、终端IMEI号：86N5235094423004优惠活动信息1、优惠活动名称：购手机入网送话费。2、优惠活动协议期：客户承诺自2014年7月至2016年6月，在网24个月。3、优惠活动信息：购手机入网送话费：产品包价格为1837.00元，差额购机款为1637.00元，次月起连续24个月每月赠送话费25.00元。基本业务功能基本通信业务及附加业务名称及描述：国内通话可选业务包名称及描述：无账户信息：账户名称杨益兰付费方式现金本人证实上述资料属实,并已阅读及同意本登记表内容和背面所载之协议。客户（甲方）签字或盖章乙方代理人签字（盖章）受理单位（盖章）担保人签字（盖章）受理人及工号景洪—综合楼-刀美兰H-HJY0702014年6月17日2014年6月17日微型USIM卡需要终端支持，如果您将该卡放入不支持微型USIM卡的终端使用，由此造成的风险及损失将由您自行承担执行机卡比对政策，详见业务协议。第1页/共1页</>";
			var actionurl="./general/interface/uploadzw.jsp?pdfInfo="+pdfInfo+"&&UID=test";
			var form1 = document.getElementById("myForm");
			form1.action=actionurl;
			form1.submit();
		}
	</script>
  </head>
  
  <body>
  	<form method="post" encType="multipart/form-data" id="myForm" action="">
    <input type="file" name="myfile" />
    <input type="button" onclick="uploadFile()" value="上传">
    </form>
  </body>
</html>
