$('.datepicker').datepicker({
    format: "yyyy-mm-dd",
    endDate: "new Date()",
    todayBtn: "linked",
    language: "zh-CN",
    clearBtn: true
});

$('.tabs_statistics .nav-tabs a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
})

$.ajax({
    url: "user/statisticsChartData",
    type: 'get',
    success: function (data) {
        console.log(data);
        initOrderStateChart(data.orderStates, data.orderStatesData);
        initShopTypeChart(data.shopTypes, data.shopTypesData);
        initNear7DaysOrderNumChart(data.near7Days, data.near7DaysOrderNum);
    }
})

/**
 * 初始化订单状态图
 * @param xData
 * @param yData
 */
function initOrderStateChart(xData, yData) {
    var data = [];
    for(var i = 0; i < yData.length; i++){
        data.push({name: xData[i], value: yData[i]});
    }
    var dom = document.getElementById("orderStateChart");
    var myChart = echarts.init(dom);
    var option = {
        title : {
            text: '订单状态',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            // orient: 'vertical',
            // left: 'left',
            top: 30,
            data: xData
        },
        series : [
            {
                name: '订单状态',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

/**
 * 初始化商店种类图
 * @param xData
 * @param yData
 */
function initShopTypeChart(xData, yData) {
    var data = [];
    for(var i = 0; i < yData.length; i++){
        data.push({name: xData[i], value: yData[i]});
    }
    var dom = document.getElementById("shopTypeChart");
    var myChart = echarts.init(dom);
    var option = {
        title : {
            text: '商店种类',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            // orient: 'vertical',
            // left: 'left',
            top: 30,
            data: xData
        },
        series : [
            {
                name: '商店种类',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: data,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

/**
 * 初始化近7天订单数量图
 * @param xData
 * @param yData
 */
function initNear7DaysOrderNumChart(xData, yData) {
    var dom = document.getElementById("near7DaysOrderNum");
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: '近7日订单数量',
            x: 'center'
        },
        xAxis: {
            type: 'category',
            data: xData
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: yData,
            type: 'bar',
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            }
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}