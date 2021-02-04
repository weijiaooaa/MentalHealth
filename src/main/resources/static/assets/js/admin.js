if (typeof jQuery === "undefined") {
    throw new Error("jQuery plugins need to be before this file");
}
"use strict";
$.AdminOreo = {};
$.AdminOreo.options = {
    colors: {
        red: '#ec3b57',
        pink: '#E91E63',
        purple: '#ba3bd0',
        deepPurple: '#673AB7',
        indigo: '#3F51B5',
        blue: '#2196f3',
        lightBlue: '#03A9F4',
        cyan: '#00bcd4',
        green: '#4CAF50',
        lightGreen: '#8BC34A',
        yellow: '#ffe821',
        orange: '#FF9800',
        deepOrange: '#f83600',
        grey: '#9E9E9E',
        blueGrey: '#607D8B',
        black: '#000000',
        blush: '#dd5e89',
        white: '#ffffff'
    },
    leftSideBar: {
        scrollColor: 'rgba(0,0,0,0.5)',
        scrollWidth: '4px',
        scrollAlwaysVisible: false,
        scrollBorderRadius: '0',
        scrollRailBorderRadius: '0'
    },
    dropdownMenu: {
        effectIn: 'fadeIn',
        effectOut: 'fadeOut'
    }
}

/* Left Sidebar - Function 
 *  You can manage the left sidebar menu options
 *  
 */
$.AdminOreo.leftSideBar = {
    activate: function() {
        var _this = this;
        var $body = $('body');
        var $overlay = $('.overlay');

        //Close sidebar
        $(window).on('click',function(e) {
            var $target = $(e.target);
            if (e.target.nodeName.toLowerCase() === 'i') {
                $target = $(e.target).parent();
            }

            if (!$target.hasClass('bars') && _this.isOpen() && $target.parents('#leftsidebar').length === 0) {
                if (!$target.hasClass('js-right-sidebar')) $overlay.fadeOut();
                $body.removeClass('overlay-open');
            }
        });

        $.each($('.menu-toggle.toggled'), function(i, val) {
            $(val).next().slideToggle(0);
        });

        //When page load
        $.each($('.menu .list li.active'), function(i, val) {
            var $activeAnchors = $(val).find('a:eq(0)');

            $activeAnchors.addClass('toggled');
            $activeAnchors.next().show();
        });

        //Collapse or Expand Menu
        $('.menu-toggle').on('click', function(e) {
            var $this = $(this);
            var $content = $this.next();

            if ($($this.parents('ul')[0]).hasClass('list')) {
                var $not = $(e.target).hasClass('menu-toggle') ? e.target : $(e.target).parents('.menu-toggle');

                $.each($('.menu-toggle.toggled').not($not).next(), function(i, val) {
                    if ($(val).is(':visible')) {
                        $(val).prev().toggleClass('toggled');
                        $(val).slideUp();
                    }
                });
            }

            $this.toggleClass('toggled');
            $content.slideToggle(320);
        });

        //Set menu height

        _this.checkStatuForResize(true);
        $(window).resize(function() {

            _this.checkStatuForResize(false);
        });

        //Set Waves
        Waves.attach('.menu .list a', ['waves-block']);
        Waves.init();
    },
    checkStatuForResize: function(firstTime) {
        var $body = $('body');
        var $openCloseBar = $('.navbar .navbar-header .bars');
        var width = $body.width();

        if (firstTime) {
            $body.find('.content, .sidebar').addClass('no-animate').delay(1000).queue(function() {
                $(this).removeClass('no-animate').dequeue();
            });
        }

        if (width < 1170) {
            $body.addClass('ls-closed');
            $openCloseBar.fadeIn();
        } else {
            $body.removeClass('ls-closed');
            $openCloseBar.fadeOut();
        }
    },
    isOpen: function() {
        return $('body').hasClass('overlay-open');
    }
};
//=============

/* Right Sidebar - Function 
 * You can manage the right sidebar menu options  */
$.AdminOreo.rightSideBar = {
    activate: function() {
        var _this = this;
        var $sidebar = $('#rightsidebar');
        var $overlay = $('.overlay');

        //Close sidebar
        $(window).on('click',function(e) {
            var $target = $(e.target);
            if (e.target.nodeName.toLowerCase() === 'i') {
                $target = $(e.target).parent();
            }

            if (!$target.hasClass('js-right-sidebar') && _this.isOpen() && $target.parents('#rightsidebar').length === 0) {
                if (!$target.hasClass('bars')) $overlay.fadeOut();
                $sidebar.removeClass('open');
            }
        });

        $('.js-right-sidebar').on('click', function() {
            $sidebar.toggleClass('open');
            if (_this.isOpen()) {
                $overlay.fadeIn();
            } else {
                $overlay.fadeOut();
            }
        });
    },
    isOpen: function() {
        return $('.right-sidebar').hasClass('open');
    }
}
//===============

/* Navbar - Function ========
 *  You can manage the navbar
 *  
 */
$.AdminOreo.navbar = {
    activate: function() {
        var $body = $('body');
        var $overlay = $('.overlay');

        //Open left sidebar panel
        $('.bars').on('click', function() {
            $body.toggleClass('overlay-open');
            if ($body.hasClass('overlay-open')) {
                $overlay.fadeIn();
            } else {
                $overlay.fadeOut();
            }
        });

        //Close collapse bar on click event
        $('.nav [data-close="true"]').on('click', function() {
            var isVisible = $('.navbar-toggle').is(':visible');
            var $navbarCollapse = $('.navbar-collapse');

            if (isVisible) {
                $navbarCollapse.slideUp(function() {
                    $navbarCollapse.removeClass('in').removeAttr('style');
                });
            }
        });
    }
}
//======


/* Form - Select - Function ======
 *  You can manage the 'select' of form elements
 *  
 */
$.AdminOreo.select = {
    activate: function() {
        if ($.fn.selectpicker) {
            $('select:not(.ms)').selectpicker();
        }
    }
}
//=============

$(".boxs-close").on('click', function(){
    var element = $(this);
    var cards = element.parents('.card');
    cards.addClass('closed').fadeOut();
  });

/* Browser - Function =======
 *  You can manage browser
 *  
 */
var edge = 'Microsoft Edge';
var ie10 = 'Internet Explorer 10';
var ie11 = 'Internet Explorer 11';
var opera = 'Opera';
var firefox = 'Mozilla Firefox';
var chrome = 'Google Chrome';
var safari = 'Safari';

$.AdminOreo.browser = {
    activate: function() {
        var _this = this;
        var className = _this.getClassName();

        if (className !== '') $('html').addClass(_this.getClassName());
    },
    getBrowser: function() {
        var userAgent = navigator.userAgent.toLowerCase();

        if (/edge/i.test(userAgent)) {
            return edge;
        } else if (/rv:11/i.test(userAgent)) {
            return ie11;
        } else if (/msie 10/i.test(userAgent)) {
            return ie10;
        } else if (/opr/i.test(userAgent)) {
            return opera;
        } else if (/chrome/i.test(userAgent)) {
            return chrome;
        } else if (/firefox/i.test(userAgent)) {
            return firefox;
        } else if (!!navigator.userAgent.match(/Version\/[\d\.]+.*Safari/)) {
            return safari;
        }

        return undefined;
    },
    getClassName: function() {
        var browser = this.getBrowser();

        if (browser === edge) {
            return 'edge';
        } else if (browser === ie11) {
            return 'ie11';
        } else if (browser === ie10) {
            return 'ie10';
        } else if (browser === opera) {
            return 'opera';
        } else if (browser === chrome) {
            return 'chrome';
        } else if (browser === firefox) {
            return 'firefox';
        } else if (browser === safari) {
            return 'safari';
        } else {
            return '';
        }
    }
}
//========
$(function() {
    $.AdminOreo.browser.activate();
    $.AdminOreo.leftSideBar.activate();
    $.AdminOreo.rightSideBar.activate();
    $.AdminOreo.navbar.activate();
    $.AdminOreo.select.activate();

    setTimeout(function() {
        $('.page-loader-wrapper').fadeOut();
    }, 50);
});

//Google Analiytics =======
addLoadEvent(loadTracking);
var trackingId = 'UA-30038099-6';

function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != 'function') {
        window.onload = func;
    } else {
        window.onload = function() {
            oldonload();
            func();
        }
    }
}

function loadTracking() {
    (function(i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function() {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', trackingId, 'auto');
    ga('send', 'pageview');
}
//==============