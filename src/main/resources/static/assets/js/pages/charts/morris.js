// Dashboard 1 Morris-chart
Morris.Area({
    element: 'm_area_chart',
    data: [{
            period: '2011',
            iphone: 45,
            ipad: 75,
            itouch: 18
        }, {
            period: '2012',
            iphone: 130,
            ipad: 110,
            itouch: 82
        }, {
            period: '2013',
            iphone: 80,
            ipad: 60,
            itouch: 85
        }, {
            period: '2014',
            iphone: 78,
            ipad: 205,
            itouch: 135
        }, {
            period: '2015',
            iphone: 180,
            ipad: 124,
            itouch: 140
        }, {
            period: '2016',
            iphone: 105,
            ipad: 100,
            itouch: 85
        },
        {
            period: '2017',
            iphone: 210,
            ipad: 180,
            itouch: 120
        }
    ],
    xkey: 'period',
    ykeys: ['iphone', 'ipad', 'itouch'],
    labels: ['iPhone', 'iPad', 'iPod Touch'],
    pointSize: 3,
    fillOpacity: 0,
    pointStrokeColors: ['#222222', '#cccccc', '#379c94'],
    behaveLikeLine: true,
    gridLineColor: '#e0e0e0',
    lineWidth: 2,
    hideHover: 'auto',
    lineColors: ['#222222', '#cccccc', '#379c94'],
    resize: true

});
Morris.Area({
    element: 'm_area_chart2',
    data: [{
            period: '2012',
            SiteA: 10,
            SiteB: 0,

        }, {
            period: '2013',
            SiteA: 126,
            SiteB: 78,

        }, {
            period: '2014',
            SiteA: 78,
            SiteB: 58,

        }, {
            period: '2015',
            SiteA: 89,
            SiteB: 185,

        }, {
            period: '2016',
            SiteA: 175,
            SiteB: 124,

        }, {
            period: '2017',
            SiteA: 126,
            SiteB: 102  ,

        }
    ],
    xkey: 'period',
    ykeys: ['SiteA', 'SiteB'],
    labels: ['Site A', 'Site B'],
    pointSize: 0,
    fillOpacity: 0.4,
    pointStrokeColors: ['#9e9e9e', '#457fca'],
    behaveLikeLine: true,
    gridLineColor: '#e0e0e0',
    lineWidth: 0,
    smooth: false,
    hideHover: 'auto',
    lineColors: ['#9e9e9e', '#457fca'],
    resize: true

});
// LINE CHART
var line = new Morris.Line({
    element: 'm_line_chart',
    resize: true,
    data: [{
            y: '2014 Q1',
            item1: 2356
        },
        {
            y: '2015 Q2',
            item1: 2586
        },
        {
            y: '2015 Q3',
            item1: 4512
        },
        {
            y: '2015 Q4',
            item1: 3265
        },
        {
            y: '2016 Q5',
            item1: 6258
        },
        {
            y: '2016 Q6',
            item1: 5234
        },
        {
            y: '2016 Q7',
            item1: 4725
        },
        {
            y: '2016 Q7',
            item1: 7526
        },
        {
            y: '2017 Q7',
            item1: 8452
        },
        {
            y: '2017 Q7',
            item1: 8931
        }
    ],
    xkey: 'y',
    ykeys: ['item1'],
    labels: ['Item 1'],
    gridLineColor: '#eef0f2',
    lineColors: ['#78b83e'],
    lineWidth: 2,
    pointSize: 3,
    hideHover: 'auto'
});
// Morris donut chart
Morris.Donut({
    element: 'm_donut_chart',
    data: [
    {
        label: "Online Sales",
        value: 45,

    }, {
        label: "Store Sales",
        value: 35
    },{
        label: "Email Sales",
        value: 8
    }, {
        label: "Agent Sales",
        value: 12
    }],

    resize: true,
    colors: ['#ffd97f', '#fab2c0', '#80dad8', '#a1abb8']
});
// Morris bar chart
Morris.Bar({
    element: 'm_bar_chart',
    data: [{
        y: '2011',
        a: 80,
        b: 56,
        c: 89
    }, {
        y: '2012',
        a: 75,
        b: 65,
        c: 38
    }, {
        y: '2013',
        a: 59,
        b: 30,
        c: 37
    }, {
        y: '2014',
        a: 75,
        b: 65,
        c: 40
    }, {
        y: '2015',
        a: 55,
        b: 40,
        c: 45
    }, {
        y: '2016',
        a: 75,
        b: 65,
        c: 40
    }, {
        y: '2017',
        a: 87,
        b: 88,
        c: 36
    }],
    xkey: 'y',
    ykeys: ['a', 'b', 'c'],
    labels: ['A', 'B', 'C'],
    barColors: ['#757575', '#26c6da', '#ffcc80'],
    hideHover: 'auto',
    gridLineColor: '#eef0f2',
    resize: true
});
// Extra chart
Morris.Area({
    element: 'e_area_chart',
        data: [{
            period: '2011',
            iphone: 10,
            ipad: 0,
            itouch: 0
        }, {
            period: '2012',
            iphone: 35,
            ipad: 65,
            itouch: 5
        }, {
            period: '2013',
            iphone: 25,
            ipad: 30,
            itouch: 65
        }, {
            period: '2014',
            iphone: 80,
            ipad: 12,
            itouch: 7
        }, {
            period: '2015',
            iphone: 30,
            ipad: 32,
            itouch: 120
        }, {
            period: '2016',
            iphone: 25,
            ipad: 80,
            itouch: 40
        }, {
            period: '2017',
            iphone: 70,
            ipad: 10,
            itouch: 26
        }


    ],
    lineColors: ['#F15F79', '#424242', '#ffc107'],
    xkey: 'period',
    ykeys: ['iphone', 'ipad', 'itouch'],
    labels: ['Site A', 'Site B', 'Site C'],
    pointSize: 0,
    lineWidth: 0,
    resize: true,
    fillOpacity: 0.8,
    behaveLikeLine: true,
    gridLineColor: '#e0e0e0',
    hideHover: 'auto'

});