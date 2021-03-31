$(function () {
    "use strict";
    initDonutChart();
    initCounters();
    getMorris('line', 'line_chart');
});

function getMorris(type, element) {
    
    if (type === 'line') {
        Morris.Line({
            element: element,
            data: [
                {
                    period: '2012',
                    WebDesign: 458,
                    Photography: 450,
                    Technology: 501,
                    Lifestyle: 410,
                    Sports: 400
                },{
                    period: '2013',
                    WebDesign: 340,
                    Photography: 201,
                    Technology: 150,
                    Lifestyle: 214,
                    Sports: 250
                },{
                    period: '2014',
                    WebDesign: 458,
                    Photography: 450,
                    Technology: 501,
                    Lifestyle: 410,
                    Sports: 400
                }, {
                    period: '2015',
                    WebDesign: 265,
                    Photography: 521,
                    Technology: 265,
                    Lifestyle: 220,
                    Sports: 436
                }, {
                    period: '2016',
                    WebDesign: 200,
                    Photography: 215,
                    Technology: 280,
                    Lifestyle: 300,
                    Sports: 249
                },
                {
                    period: '2017',
                    WebDesign: 865,
                    Photography: 450,
                    Technology: 501,
                    Lifestyle: 410,
                    Sports: 400
                }
            ],
            xkey: 'period',
            ykeys: ['WebDesign', 'Photography', 'Technology', 'Lifestyle', 'Sports'],
            labels: ['WebDesign', 'Photography', 'Technology', 'Lifestyle', 'Sports'],
            pointSize: 2,
            fillOpacity: 0,
            pointStrokeColors: ['#fe6283', '#359ef0', '#fcce56', '#48c2c2', '#9d67ff'],
            behaveLikeLine: true,
            gridLineColor: '#e0e0e0',
            lineWidth: 2,
            hideHover: 'auto',
            lineColors: ['#fe6283', '#359ef0', '#fcce56', '#48c2c2', '#9d67ff'],
            resize: true
        });
    }
}
//===============================================================================
function initCounters() {
    $('.count-to').countTo();
}
//===============================================================================
function initDonutChart() {
    Morris.Donut({
        element: 'donut_chart',
        data: [{
            label: 'Chrome',
            value: 30
        }, {
            label: 'Firefox',
            value: 25
        }, {
            label: 'Safari',
            value: 20
        }, {
            label: 'Opera',
            value: 10        
        }, {
            label: 'IE',
            value: 10
        },{
            label: 'Other',
            value: 5
        }],
        colors: ['#7cd2dc', '#a989bf', '#afc966', '#f99d4a', '#f28c85', '#768c95 '],
        formatter: function (y) {
            return y + '%'
        }
    });
}
//===============================================================================
$(window).on('scroll',function() {
    $('.card .sparkline').each(function(){
    var imagePos = $(this).offset().top;

    var topOfWindow = $(window).scrollTop();
        if (imagePos < topOfWindow+400) {
            $(this).addClass("pullUp");
        }
    });
});
//===============================================================================
$(function () {
    $('#world-map-markers').vectorMap({
        map: 'world_mill_en',
        normalizeFunction: 'polynomial',
        hoverOpacity: 0.7,
        hoverColor: false,
        backgroundColor: 'transparent',
        showTooltip: true,        

        regionStyle: {
            initial: {
                fill: 'rgba(210, 214, 222, 1)',
                "fill-opacity": 1,
                stroke: 'none',
                "stroke-width": 0,
                "stroke-opacity": 1
            },
            hover: {
                fill: 'rgba(255, 193, 7, 2)',
                cursor: 'pointer'
            },
            selected: {
                fill: 'yellow'
            },
            selectedHover: {}
        },
        markerStyle: {
            initial: {
                fill: '#fff',
                stroke: '#FFC107 '
            }
        },
        markers: [
            { latLng: [37.09,-95.71], name: 'America' },
            { latLng: [51.16,10.45], name: 'Germany' },
            { latLng: [-25.27, 133.77], name: 'Australia' },
            { latLng: [56.13,-106.34], name: 'Canada' },
            { latLng: [20.59,78.96], name: 'India' },
            { latLng: [55.37,-3.43], name: 'United Kingdom' },
        ]
    });
});

$('#usa').vectorMap({
    map : 'us_aea_en',
    backgroundColor : 'transparent',
    regionStyle : {
        initial : {
            fill : '#72c2ff'
        }
    }
}); 