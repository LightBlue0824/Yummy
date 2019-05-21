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
    url: "shop/statisticsChartData",
    type: "get",
    success: function (data) {
        console.log(data);
        initOrderStateChart(data.orderStates, data.orderStatesData);
        initUserLevelChart(data.userLevels, data.userLevelsData);
        initNear7DaysIncomeChart(data.near7Days, data.near7DaysIncome);
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
 * 初始化用户等级图
 * @param xData
 * @param yData
 */
function initUserLevelChart(xData, yData) {
    var data = [];
    for(var i = 0; i < yData.length; i++){
        data.push({name: xData[i], value: yData[i]});
    }
    var dom = document.getElementById("userLevelChart");
    var myChart = echarts.init(dom);
    var option = {
        title : {
            text: '各等级用户订单数',
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
            // data: xData
            data: ['普通会员', '初级会员', '高级会员']
        },
        series : [
            {
                name: '各等级用户订单数',
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
 * 初始化近7天收入图
 * @param xData
 * @param yData
 */
function initNear7DaysIncomeChart(xData, yData) {
    var dom = document.getElementById("near7DaysIncomeChart");
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: '近7日餐厅收入',
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
            type: 'line',
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
                    position: 'top'
                }
            }
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}