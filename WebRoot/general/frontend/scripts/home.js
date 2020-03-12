(function () {
    /**
     * Format number to size string
     * @returns {string}
     */
    Number.prototype.formatSize = function () {
        var units = ['B', 'K', 'M', 'G', 'T', 'PB'], unit = 0, value = this.valueOf();
        while (value >= 1024 && unit < 6) {
            value /= 1024;
            unit++;
        }
        return value.toFixed(2) + ' ' + units[unit];
    };
    function insertInstances (instances) {
        var instanceCards = $('.instance-card');
        instanceCards.each(function () {
            if (!$(this).hasClass('instance-add')) {
                console.log($(this));
                $(this).remove();
            }
        });
        instances.forEach(function (instance, index) {
            insertInstance(instance);
        })
    }
    function insertInstance (instance) {
        var status = '',statusIcon = '',statusText = '';
        switch (instance.status) {
            case 1: status = 'success';statusIcon = 'circle';statusText = '运行中';break;
            case 0: status = 'warning';statusIcon = 'play-circle';statusText = '已关闭';break;
            case -1: status = 'danger';statusIcon = 'exclamation-circle';statusText = '故障中';break;
        }
        var instanceCard = '<li class="instance-card instance-info"><div class="up">' +
            '<a href="instanceDetail.html?id=' + instance.id + '" class="instance-name" title="查看详细信息">' +
            '<img class="instance-icon" src="assets/' + instance.system + '.png">' +
            '<span class="instance-name-span"><span class="name">' + instance.name +'</span>' +
            '<span class="system">' + instance.system + ' ' + instance.systemVersion + '</span></span></a>' +
            '<div class="instance-actions"><div class="instance-action"><span class="instance-action-title">' +
            '<span class="instance-status text-' + status + '">' +
            '<i class="fa fa-' + statusIcon + '"></i><span class="instance-status-text">' + statusText + '</span></span></span>' +
            '<span class="instance-action-btns instance-status-btns">' +
            '<button type="button" class="btn btn-success fa fa-play" title="启动"' + (instance.status !== 0 ? ' disabled' : '') + '></button>' +
            '<button type="button" class="btn btn-warning fa fa-rotate-left" title="重启"' + (instance.status === 1 ? '' : ' disabled') + '></button>' +
            '<button type="button" class="btn btn-danger fa fa-power-off" title="停止"' + (instance.status === 1 ? '' : ' disabled') + '></button>' +
            '</span></div><div class="instance-action"><span class="instance-action-title instance-action-info">' +
            '<span class="instance-info-cpu">CPU：' + instance.CPU + '核</span>' +
            '<span class="instance-info-memory">内存：' + instance.memory + 'G</span>' +
            '<span class="instance-info-broadband">宽带：' + instance.broadband + 'Mbps</span></span>' +
            '<span class="instance-action-btns">' +
            '<a href="instanceDetail.html?id=' + instance.id + '" class="margin-left-3">管理</a>' +
            '</span></div></div>' +
            '</div><div class="down">' +
            '<div class="instance-data"><p class="instance-data-name">当月流量</p>' +
            '<p class="instance-data-amount">' + instance.statistics.monthTraffic.formatSize() + '</p></div>' +
            '<div class="instance-data"><p class="instance-data-name">当月访问量</p>' +
            '<p class="instance-data-amount">' + instance.statistics.monthVisitTimes + ' 次</p></div>' +
            '<div class="instance-data"><p class="instance-data-name">当月请求数</p>' +
            '<p class="instance-data-amount">' + instance.statistics.monthRequest + ' 次</p></div>' +
            '</div></li>';
        $('.instance-cards').prepend(instanceCard);
    }
    function vendorClusterCharts () {
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
            }
        });
        $('#clusterInfoCharts').highcharts({
            chart: {type: 'column'},
            credits: {enabled: false},
            title: {text: 'Recent Week Usage'},
            xAxis: {categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'], crosshair: true},
            yAxis: {min: 0, title: {text: '使用率 (%)'}},
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
                footerFormat: '</table>',shared: true, useHtml: true},
            plotOptions: {column: {pointPadding: 0.2, borderWidth: 0}},
            series: [
                {name: 'CPU', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 50.0]},
                {name: '内存', data: [15.0, 34.9, 21.5, 14.5, 18.2, 21.5, 34.0]},
                {name: '磁盘', data: [15.0, 16.9, 15.5, 14.5, 18.2, 21.5, 22.0]}
            ]
        });
    }
    $(function(){
        $.get('api/demo.json', function (data) {
            insertInstances(data.instances);
            vendorClusterCharts();
        });
    });

})();