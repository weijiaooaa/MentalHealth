$(function () {
    initCounters();
    initCharts();
});

//Widgets count plugin
function initCounters() {
    $('.count-to').countTo();
}

//Charts
function initCharts() {
    //Chart Bar
    $('.chart.chart-bar').sparkline(undefined, {
        type: 'bar',
        barColor: '#fff',
        negBarColor: '#fff',
        barWidth: '4px',
        height: '34px'
    });

    //Chart Pie
    $('.chart.chart-pie').sparkline(undefined, {
        type: 'pie',
        height: '50px',
        sliceColors: ['rgba(255,255,255,0.70)', 'rgba(255,255,255,0.85)', 'rgba(255,255,255,0.95)', 'rgba(255,255,255,1)']
    });

    //Chart Line
    $('.chart.chart-line').sparkline(undefined, {
        type: 'line',
        width: '60px',
        height: '45px',
        lineColor: '#fff',
        lineWidth: 1.3,
        fillColor: 'rgba(0,0,0,0)',
        spotColor: 'rgba(255,255,255,0.40)',
        maxSpotColor: 'rgba(255,255,255,0.40)',
        minSpotColor: 'rgba(255,255,255,0.40)',
        spotRadius: 3,
        highlightSpotColor: '#fff'
    });

    $('#linecustom1').sparkline('html',{
        height: '35px',
        width: '100%',
        lineColor: '#e5d1e4',
        fillColor: '#f3e8f2',
        minSpotColor: true,
        maxSpotColor: true,
        spotColor: '#e2a8df',
        spotRadius: 1
    });

    $('#linecustom2').sparkline('html',{
        height: '35px',
        width: '100%',
        lineColor: '#c9e3f4',
        fillColor: '#dfeefa',
        minSpotColor: true,
        maxSpotColor: true,
        spotColor: '#8dbfe0',
        spotRadius: 1
    });

    $('#linecustom3').sparkline('html',{
        height: '35px',
        width: '100%',
        lineColor: '#efded3',
        fillColor: '#f8f0ea',
        minSpotColor: true,
        maxSpotColor: true,
        spotColor: '#e0b89d',
        spotRadius: 1
    });

    $('#linecustom4').sparkline('html',{
        height: '35px',
        width: '100%',
        lineColor: '#efded3',
        fillColor: '#f8f0ea',
        minSpotColor: true,
        maxSpotColor: true,
        spotColor: '#e0b89d',
        spotRadius: 1
    });
}