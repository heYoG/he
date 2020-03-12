<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
	<link rel="stylesheet" href="../../../adverts/css/jquery.fullpage.min.css">
  </head>
  <body onload="ReImgSize()">
  	<div id="dowebok">
  		<div class="section" id="dv">
  		<!-- 设置文件名称值 -->
  			<c:set var="varName">
  				<%=request.getParameter("name") %>
  			</c:set>
  			<c:if test="${varName!=null }">
  				<c:set var="filenames">
  					${varName},
  				</c:set>
  				<c:set var="basepath">
  					<%=basePath %>
  				</c:set>
  				<c:forEach var="filename" items="${filenames}"><!-- 多张图片时循环查找 -->
  					<div class="slide">
  						<img src="${basepath}servlet/ShowAdvertsServlet?filename=${filename}"><!-- 获取图片并展示 -->
  						<div style="color:blue;font-size:30px" align="center"><c:out value="${filename }"></c:out></div><!-- 显示图片名称 -->
  					</div>
  				</c:forEach>
  			</c:if>
  		</div>
  	</div>
  <script src="../../../adverts/js/jquery-1.11.1.min.js"></script>
  <script src="../../../adverts/js/jquery.fullPage.min.js"></script>
  <script>
    $(function(){
        $("#dowebok").fullpage({
            css3:true,
            controlArrows:true,
            scrollingSpeed:-1000,
            continuousVertical:true,  
            resize:true,
            keyboardScrolling:true,
            anchors:[],
            controlArrowColor:'green',
            slidesNavigation:true,
            slidesNavPosition:'top',
            Navigation:true
        });
        setInterval(function(){//根据设置的广告滚动时间调用向右滚动方法
            $.fn.fullpage.moveSlideRight();//图片向右滚动
        }, <%=request.getParameter("scorlltime") %>);//根据设置的时间滚动
    });
    function ReImgSize(){
        var j = 0;
         var clientWidth = document.documentElement.clientWidth;
        for (j=0;j<document.images.length;j++)
        {
            document.images[j].width=(document.images[j].width>clientWidth)?clientWidth+30:document.images[j].width;
        }
    }
</script>
  </body>
</html>
