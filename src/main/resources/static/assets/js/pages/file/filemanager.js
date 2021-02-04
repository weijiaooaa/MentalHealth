Morris.Bar({
    element: 'm_bar_chart',
    data: [{
        y: '2011',
        Documents: 80,
        Media: 56,
        Images: 89
    }, {
        y: '2012',
        Documents: 75,
        Media: 65,
        Images: 38
    }, {
        y: '2013',
        Documents: 59,
        Media: 30,
        Images: 37
    }, {
        y: '2014',
        Documents: 75,
        Media: 65,
        Images: 40
    }, {
        y: '2015',
        Documents: 55,
        Media: 40,
        Images: 45
    }, {
        y: '2016',
        Documents: 75,
        Media: 65,
        Images: 40
    }, {
        y: '2017',
        Documents: 87,
        Media: 88,
        Images: 36
    }],
    xkey: 'y',
    ykeys: ['Documents', 'Media', 'Images'],
    labels: ['Documents', 'Media', 'Images'],
    barColors: ['#6a7885', '#67d7e5', '#ffde78'],
    hideHover: 'auto',
    gridLineColor: '#eef0f2',
    resize: true
});
