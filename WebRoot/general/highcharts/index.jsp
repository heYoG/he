<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta charset="utf-8">
        <title>无标题文档</title>
        <meta http-equiv="X-UA-Compatible" content="IE=9" />
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.css"/>
        <link rel="stylesheet" href="css/index.css"/>
    </head>
    <body>
        <div class="chooseForm">
            <form class="form-inline">
                <div class="form-group col-padding-3 remove-padding-l">
                    <label>营业点</label>
                    <select name="deptno" class="form-control" id="deptno">
                    	<option value="0000">全省</option>
                        <option value="00000002">北京西三旗分行</option>
                        <option value="00000003">山西分行</option>
                        <option value="00000004">陕西分行</option>
                        <option value="00000005">河北分行</option>
                    </select>
                </div>
                <div class="form-group col-padding-3 remove-padding-r">
                    <label >时间</label>
                    <input type="text" class="form-control datetimepicker" name="startTime" id="startTime">
                    <label>至</label>
                    <input type="text" class="form-control datetimepicker" name="endTime" id="endTime">
                </div>
                <div class="form-group col-padding-3 remove-padding-r">
                    <button type="submit" class="btn btn-info col-padding-3" id="buttonForm">统计</button>
                </div>
            </form>
        </div>
        <div class="container">
             <div class="row">
                <div class="col-md-6">
                    <div id="business-pie"></div>
                </div>
                <div class="col-md-6">
                    <div id="business-satisfaction"></div>
                </div>
            </div>
            <div class="block-header"></div>
            <div id="business-c"></div>
            <div class="block-header"></div>
            <div id="business-l1"></div>
            <div id="business-l2"></div>
        </div>

        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script type="text/javascript" src="js/highcharts.js"></script>
        <script type="text/javascript" src="js/exporting.js"></script>
        <script type="text/javascript" src="js/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="js/bootstrap-datetimepicker.zh-CN.js"></script>
        <script type="text/javascript" src="js/business.js"></script>
    </body>
</html>