//===============================================================================
$(window).on('scroll',function() {
    $('.card .sparkline').each(function() {
        var imagePos = $(this).offset().top;

        var topOfWindow = $(window).scrollTop();
        if (imagePos < topOfWindow + 400) {
            $(this).addClass("pullUp");
        }
    });
});

//===============================================================================

Morris.Area({
    element: 'm_area_chart2',
    data: [{
            period: '2012',
            SiteA: 10,
            SiteB: 0,

        }, {
            period: '2013',
            SiteA: 105,
            SiteB: 110,

        }, {
            period: '2014',
            SiteA: 78,
            SiteB: 92,

        }, {
            period: '2015',
            SiteA: 89,
            SiteB: 185,

        }, {
            period: '2016',
            SiteA: 175,
            SiteB: 149,

        }, {
            period: '2017',
            SiteA: 126,
            SiteB: 98,

        }
    ],
    xkey: 'period',
    ykeys: ['SiteA', 'SiteB'],
    labels: ['Site A', 'Site B'],
    pointSize: 0,
    fillOpacity: 0.4,
    pointStrokeColors: ['#b6b8bb', '#a890d3'],
    behaveLikeLine: true,
    gridLineColor: '#e0e0e0',
    lineWidth: 0,
    smooth: false,
    hideHover: 'auto',
    lineColors: ['#b6b8bb', '#a890d3'],
    resize: true

});

//===============================================================================
$(".dial1").knob();
$({animatedVal: 0}).animate({animatedVal: 66}, {
    duration: 4000,
    easing: "swing", 
    step: function() { 
        $(".dial1").val(Math.ceil(this.animatedVal)).trigger("change"); 
    }
});
$(".dial2").knob();
$({animatedVal: 0}).animate({animatedVal: 26}, {
    duration: 4500,
    easing: "swing", 
    step: function() { 
        $(".dial2").val(Math.ceil(this.animatedVal)).trigger("change"); 
    }
});
$(".dial3").knob();
$({animatedVal: 0}).animate({animatedVal: 76}, {
    duration: 3800,
    easing: "swing", 
    step: function() { 
        $(".dial3").val(Math.ceil(this.animatedVal)).trigger("change"); 
    }
});
$(".dial4").knob();
$({animatedVal: 0}).animate({animatedVal: 88}, {
    duration: 5200,
    easing: "swing", 
    step: function() { 
        $(".dial4").val(Math.ceil(this.animatedVal)).trigger("change"); 
    }
});