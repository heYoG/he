$.get('api/demo.json', function (data) {
    var instance;
    for (var i = 0; i < data.instances.length; i++) {
        if (data.instances[i].id === getUrlParam('id')) {
            instance = data.instances[i];
            break;
        }
    }
    vendorDetail(instance);
    changeInfoModal(instance);
    vendorDefaultChartsOptions();
    vendorCPUCharts();
    vendorMemoryCharts();
});
/**
 * Format Date to string like 20xx-xx-xx xx:xx:xx
 * @returns {string}
 */
Date.prototype.format = function () {
    var smallerThanTen = function (number) {
        return number < 10 ? "0" + number : number;
    };
    return this.getFullYear() + "-" + smallerThanTen(this.getMonth() + 1) + "-" + smallerThanTen(this.getDate()) + " " + smallerThanTen(this.getHours()) + ":" + smallerThanTen(this.getMinutes()) + ":" + smallerThanTen(this.getSeconds());
};
function vendorDetail(instance) {
    var status = '', statusIcon = '', statusText = '',
        serviceUrl = instance.serviceUrl === null ? (instance.publicIP + ':' + instance.port) : instance.serviceUrl;
    switch (instance.status) {
        case 1:
            status = 'success';
            statusIcon = 'circle';
            statusText = '运行中';
            break;
        case 0:
            status = 'warning';
            statusIcon = 'play-circle';
            statusText = '已关闭';
            break;
        case -1:
            status = 'danger';
            statusIcon = 'exclamation-circle';
            statusText = '故障中';
            break;
    }
    $('#instanceDetailName').empty().append(instance.name);
    $('#instanceDetailTitleBtns').empty().append(
        '<button class="btn btn-default" onclick="location.href=\'index.html\'"><i class="fa fa-backward margin-right"></i>返回</button>' +
        '<button class="btn btn-default margin-left"><i class="fa fa-refresh margin-right"></i>刷新</button>' +
        '<button class="btn btn-success' + (instance.status === 0 ? ' margin-left' : ' hide') + '"><i class="fa fa-play margin-right"></i>启动</button>' +
        '<button class="btn btn-danger' + (instance.status === 1 ? ' margin-left' : ' hide') + '"><i class="fa fa-stop margin-right"></i>停止</button>' +
        '<button class="btn btn-warning' + (instance.status === 1 ? ' margin-left' : ' hide') + '"><i class="fa fa-backward margin-right"></i>重启</button>' +
    '<button class="btn btn-warning' + (instance.status === -1 ? ' margin-left' : ' hide') + '"><i class="fa fa-stethoscope margin-right"></i>诊断</button>'
    );
    $('.instance-basic-table.table-default-viewer').empty().append(
        '<tr><td><span class="text-muted">型号：</span>' + instance.id + '</td></tr>' +
        '<tr><td><span class="text-muted">名称：</span>' + instance.name + '</td></tr>' +
        '<tr><td><span class="text-muted">所在位置：</span>' + instance.area + '</td></tr>' +
        '<tr><td><span class="text-muted">描述：</span><span title="' + instance.description + '">' +
        instance.description + '</span></td></tr>'
    );
    $('#changeConfigurationBtn').empty().append('<button type="button" class="btn btn-default btn-sm margin-right"' +
        (instance.status === 1 ? '  disabled' : '') + ' data-toggle="modal" data-target="#changeConfigModal"  data-backdrop="static">修改配置</button>')
        .attr('title', (instance.status === 1 ? '已停止的实例才能进行该操作' : ''));
    $('.instance-configuration-table.table-default-viewer').empty().append(
        '<tr><td><span class="text-muted">CPU：</span>' + instance.CPU + '核</td></tr>' +
        '<tr><td><span class="text-muted">内存：</span>' + instance.memory + 'G</td></tr>' +
        '<tr><td><span class="text-muted">操作系统：</span>' + instance.system + ' ' +
        instance.systemVersion + ' ' + instance.systemBits + '位</td></tr>' +
        '<tr><td><span class="text-muted">公网IP：</span>' + instance.publicIP + '</td></tr>' +
        '<tr><td><span class="text-muted">内网IP：</span>' + instance.intranetIP + '</td></tr>' +
        //'<tr><td><span class="text-muted">宽带计费方式：</span>' + instance.broadbandWay + '</td></tr>' +
        '<tr><td><span class="text-muted">宽带：</span>' + instance.broadband + 'Mbps(峰值)</td></tr>'
    );
    $('.instance-basic-charts').empty().append(
        '<div class="instance-icon"><img src="assets/' + instance.system + '.png" /></div><ul class="instance-basics">' +
        '<li>运行状态：<span class="text-' + status + '"><i class="fa fa-' + statusIcon + '"></i>' + '<span class="margin-left">' + statusText + '</span></span></li>' +
        '<li>服务地址：<a href="' + serviceUrl + '">' + serviceUrl + '</a></li>' +
        '<li>创建时间：' + new Date(instance.createdAt).format() + '</li>' +
        '<li>更新时间：' + new Date(instance.updatedAt).format() + '</li></ul>'
    );

}
function vendorDefaultChartsOptions() {
    Highcharts.setOptions({
        credits: {enabled: false},
        title: {x: -20},
        subtitle: {x: -20},
        legend: {layout: 'vertical', align: 'right', verticalAlign: 'middle', borderWidth: 0},
        yAxis: {plotLines: [{value: 0, width: 1, color: '#808080'}]}
    });
}
function vendorCPUCharts() {
    $('#cpuUsageCharts').highcharts({
        title: {text: 'Weekly Average CPU Usage'},
        //subtitle: {text: 'Source: dianju.com'},
        xAxis: {categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']},
        yAxis: {title: {text: '%'}},
        tooltip: {valueSuffix: '%'},
        series: [{name: 'CPU使用率', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 50.0]}]
    });
}
function vendorMemoryCharts() {
    $('#memoryUsageCharts').highcharts({
        title: {text: 'Weekly Average Memory Usage'},
        //subtitle: {text: 'Source: dianju.com'},
        xAxis: {categories: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri']},
        yAxis: {title: {text: '%'}},
        tooltip: {valueSuffix: '%'},
        series: [{name: '内存使用率', data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 50.0]}]
    });
}
function changeInfoModal(instance) {
    $('#changeBasicModal').on('show.bs.modal', function () {
        modalStyleInit($(this));
        $(this).find('#inputBasicName').val(instance.name);
        $(this).find('#inputBasicDescription').val(instance.description);
    });
    $('#changePasswdModal').on('show.bs.modal', function () {
        var modal = $(this);
        modalStyleInit(modal);
        modal.find('input').val('');
    });
}
function modalStyleInit(modal) {
}
/**
 * Get param value by name from URL
 * @param name
 * @returns {*}
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return r[2];
    return null; //返回参数值
}

/**
 * change statistics by duration
 * @param element
 * @param time
 */
function updateMonitorCharts(element, time) {
    $(element).removeClass('btn-default').addClass('btn-primary')
        .siblings().removeClass('btn-primary').addClass('btn-default');
}