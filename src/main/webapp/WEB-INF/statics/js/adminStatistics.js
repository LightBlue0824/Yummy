$.ajax({
    url: "admin/statisticsChartData",
    type: "get",
    success: function (data) {
        console.log(data);
        initUserLevelChart(data.userLevels, data.userLevelData);
        initShopTypeChart(data.shopTypes, data.shopTypeData);
        initNear7DaysIncomeChart(data.near7Days, data.near7DaysIncome);
    }
})

function initUserLevelChart(xData, yData) {
    var data = [];
    for(var i = 0; i < yData.length; i++){
        data.push({name: xData[i], value: yData[i]});
    }
    var dom = document.getElementById("userLevelChart");
    var myChart = echarts.init(dom);
    var option = {
        title : {
            text: '用户等级',
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
            data: ['普通会员', '初级会员', '高级会员']
            // data: xData
        },
        series : [
            {
                name: '用户等级',
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

function initShopTypeChart(xData, yData) {
    var data = [];
    for(var i = 0; i < yData.length; i++){
        data.push({name: xData[i], value: yData[i]});
    }
    var dom = document.getElementById("shopTypeChart");
    var myChart = echarts.init(dom);
    var option = {
        title : {
            text: '餐厅分类',
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
                name: '餐厅分类',
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

function initNear7DaysIncomeChart(xData, yData) {
    var dom = document.getElementById("near7DaysIncomeChart");
    var myChart = echarts.init(dom);
    var option = {
        title: {
            text: '近7日平台收入',
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