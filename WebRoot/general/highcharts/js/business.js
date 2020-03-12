var deptNo = document.getElementById("deptno").value;
var sTime = document.getElementById("startTime").value;
var eTime = document.getElementById("endTime").value;
var getJsonUrl = "/Seal/general/interface/GetRecordsData.jsp?deptNo="+deptNo+"&sTime="+sTime+"&eTime="+eTime;

//图表的全局配置
Highcharts.setOptions({
    lang: {
        contextButtonTitle: "图表导出菜单",
        decimalPoint: ".",
        downloadJPEG: "下载JPEG图片",
        downloadPDF: "下载PDF文件",
        downloadPNG: "下载PNG文件",
        downloadSVG: "下载SVG文件",
        drillUpText: "返回 {series.name}",
        loading: "加载中",
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        noData: "没有数据",
        numericSymbols: ["千", "兆", "G", "T", "P", "E"],
        printChart: "打印图表",
        resetZoom: "恢复缩放",
        resetZoomTitle: "恢复图表",
        shortMonths: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        thousandsSep: ",",
        weekdays: ["星期一", "星期二", "星期三", "星期三", "星期四", "星期五", "星期六", "星期天"]
    },
    colors: ['#3498db', '#2ecc71', '#f1c40f', '#f39c12', '#9b59b6', '#c0392b', '#1abc9c', '#d35400', '#bdc3c7', '#34495e', '#7f8c8d', '#16a085', '#27ae60', '#e74c3c', '#8e44ad', '#2980b9', '#95a5a6', '#2c3e50'],
    legend : {
        itemStyle :{
            'fontSize' : '12px',
            'fontFamily': '微软雅黑'
        }
    },
    chart: {
        style: {
            'fontSize' : '12px',
            'fontFamily': '微软雅黑'
        }
    },
    tooltip: {
        style: {
            'fontSize' : '12px',
            'fontFamily': '微软雅黑'
        }
    },
    labels:{
        style: {
            'fontSize' : '12px',
            'fontFamily': '微软雅黑'
        }
    }
});

//图表颜色配置数据


//数据准备
$.getJSON(getJsonUrl, function (data) {
    //银行各个营业点业务量数据
    var businessTypes = {},
        businessTypesData = [];
    var sum = data.length;

    $.each(data, function (infoIndex, info) {
        var type;//业务类型
        type = '"' + info["businessType"] + '"';

        if (!businessTypes[type]) {
            businessTypes[type] = 1 / sum;
        } else {
            businessTypes[type] += 1 / sum;
        }
    });
    $.each(businessTypes, function (name, y) {
        businessTypesData.push({
            name: name,
            y: y
        });
    });
    businessPie(businessTypesData);
    
    //业务满意度统计数据
    var satisfactionType = {},
        satisfactionTypeData = [];

    $.each(data, function (infoIndex, info) {
        var type;//满意度类型
        type = info["satisfaction"];

        if (!satisfactionType[type]) {
            satisfactionType[type] = 1 / sum;
        } else {
            satisfactionType[type] += 1 / sum;
        }
    });

    var satisfactionTF = function(type){
        var satisfactionT = null;
        if(type == 0){
            satisfactionT = "非常满意";
        }else if(type == 1){
            satisfactionT = "满意";
        }else if(type == 2){
            satisfactionT = "一般";
        }else if(type == 3){
            satisfactionT = "不满意";
        }
        return satisfactionT;
    }

    $.each(satisfactionType, function (name, y) {
        name = satisfactionTF(name);
        satisfactionTypeData.push({
            name: name,
            y: y
        });
    });
    businessSatisfaction(satisfactionTypeData);

    //各个业务员的业务量统计
    var salesmans = {},
        salesmansData = [],
        businessTypes1 = {},
        businessTypesData1 = [];

    $.each(data, function (infoIndex, info) {
        var salesman = info["salesman"];
        if (!salesmans[salesman]) {
            salesmans[salesman] = salesman;
        }
        var businessType = info["businessType"];
        if (!businessTypes1[businessType]) {
            businessTypes1[businessType] = businessType;
        }
    });

    $.each(salesmans, function (infoIndex, info) {
        salesmansData.push(info);
    });
    $.each(businessTypes1, function (index, dat) {
        var arr = [];
        $.each(salesmans, function (infoIndex1, info1) {
            var val = 0;
            $.each(data, function (infoIndex, info) {
                if (dat == info["businessType"]) {
                    if (info1 == info["salesman"]) {
                        val += 1;
                    }
                    ;
                }

            });
            arr.push(val);
        });
        businessTypesData1.push({
            name: dat,
            data: arr
        });
    });
    salesmanColumn(salesmansData, businessTypesData1);

 
    
    //统计存款业务数据
    var times = {},
        timesData = [],
        moneyData = [];

    $.each(data, function (infoIndex, info) {
        if (info["businessType"] == '存款') {
            var time = info["time"]
            if (!times[time]) {
                times[time] = time;
            }
        }
    });

    $.each(times, function (infoIndex, info) {
        var money = 0;
        $.each(data, function (index, inf) {
            if (inf["businessType"] == '存款') {
                if (inf["time"] == info) {
                    money += parseInt(inf["money"]);
                }
            }
        });
        moneyData.push(money);
    });

    $.each(times, function (infoIndex, info) {
        timesData.push(info);
    });

    depositLine1(timesData, moneyData);

    //统计取款业务数据
    var times1 = {},
        timesData1 = [],
        moneyData1 = [];

    $.each(data, function (infoIndex, info) {
        if (info["businessType"] == '取款') {
            var time = info["time"]
            if (!times1[time]) {
                times1[time] = time;
            }
        }
    });

    $.each(times1, function (infoIndex, info) {
        var money = 0;
        $.each(data, function (index, inf) {
            if (inf["businessType"] == '取款') {
                if (inf["time"] == info) {
                    money += parseInt(inf["money"]);
                }
            }
        });
        moneyData1.push(money);
    });

    $.each(times1, function (infoIndex, info) {
        timesData1.push(info);
    });

    depositLine2(timesData1, moneyData1);

});

//业务类型占比饼状图
var businessPie = function (typesData) {
    $('#business-pie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '<h4>业务类型统计结果</h4>',
            align: 'left',
            floating: false,
            x: 10,
            y: 10,
            useHTML: true
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true
                },
                showInLegend: true
            }
        },
        credits: {
            enabled: false // 禁用版权信息
        },
        series: [{
            name: '占',
            data: typesData
        }]
    });
};

//满意度统计饼状图
var businessSatisfaction = function (typesData) {
    $('#business-satisfaction').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: '<h4>满意度统计结果</h4>',
            align: 'left',
            floating: false,
            x: 10,
            y: 10,
            useHTML: true
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true
                },
                showInLegend: true
            }
        },
        credits: {
            enabled: false // 禁用版权信息
        },
        series: [{
            name: '占',
            data: typesData
        }]
    });
};

//各营业员业务量柱状图
var salesmanColumn = function (data, data1) {
    $('#business-c').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '<h4>营业员业务量统计图</h4>',
            align: 'left',
            floating: false,
            x: 10,
            y: 10,
            useHTML: true
        },
        xAxis: {
            categories: data
        },
        yAxis: {
            min: 0,
            title: {
                text: '业务量'
            },
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
        },
        legend: {
            align: 'right',
            x: -30,
            verticalAlign: 'top',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
            borderColor: '#CCC',
            borderWidth: 1,
            shadow: false
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    '总共: ' + this.point.stackTotal;
            }
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                dataLabels: {
                    enabled: false,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                    style: {
                        textShadow: '0 0 3px black'
                    }
                }
            }
        },
        credits: {
            enabled: false // 禁用版权信息
        },
        series: data1
    });

};

//存款业务折线图
var depositLine1 = function (data, data1) {
    $('#business-l1').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '<h4>存款业务统计图</h4>',
            align: 'left',
            floating: false,
            x: 10,
            y: 10,
            useHTML: true
        },
        xAxis: {
            categories: data
        },
        yAxis: {
            title: {
                text: '金额'
            }
        },
        credits: {
            enabled: false // 禁用版权信息
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        series: [{
            name: '存款',
            data: data1
        }]
    });
};

//取款业务折线图
var depositLine2 = function (data, data1) {
    $('#business-l2').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '<h4>取款业务统计图</h4>',
            align: 'left',
            floating: false,
            x: 10,
            y: 10,
            useHTML: true
        },
        xAxis: {
            categories: data
        },
        yAxis: {
            title: {
                text: '金额'
            }
        },
        credits: {
            enabled: false // 禁用版权信息
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        tooltip: {//提示框
            enabled: true //是否启用提示框
        },
        series: [{
            name: '取款',
            data: data1
        }]
    });
};
//获取表单数据
$("#buttonForm").click( function () {
	
    var counter = $("#deptno").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    
    getJsonUrl = "/Seal/general/interface/GetRecordsData.jsp?deptNo="+counter+"&sTime="+startTime+"&eTime="+endTime;

    //console.log(counter+startTime+endTime);

});
//日期控件初始化
$('.datetimepicker').datetimepicker(
    {
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    }
);
