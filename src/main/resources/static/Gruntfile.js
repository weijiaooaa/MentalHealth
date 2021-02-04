module.exports = function(grunt) {
    grunt.initConfig({
		sass: {
			options: {
                includePaths: ['node_modules/bootstrap-sass/assets/stylesheets']
            },
            dist: {
				options: {
					outputStyle: 'compressed'
				},
                files: [{
                    'assets/css/main.css': 'assets/scss/main.scss',  							/* 'All main SCSS' */
                    'assets/css/color_skins.css': 'assets/scss/color_skins.scss', 				/* 'All Color Option' */
                    'assets/css/timeline.css': 'assets/scss/pages/timeline.scss', 				/* 'Timeline SCSS to CSS' */
                    'assets/css/blog.css': 'assets/scss/pages/blog.scss', 						/* 'Blog page' */
                    'assets/css/chatapp.css': 'assets/scss/pages/chatapp.scss', 				/* 'Chat App Page SCSS to CSS' */
                    'assets/css/authentication.css': 'assets/scss/pages/authentication.scss', 	/* 'Authentication Page SCSS to CSS' */
                    'assets/css/inbox.css': 'assets/scss/pages/inbox.scss', 					/* 'Email App' */
                    'assets/css/rtl.css': 'assets/scss/pages/rtl.scss', 						/* 'RTL Support Dashboard scss' */
                    'assets/css/ecommerce.css': 'assets/scss/pages/ecommerce.scss', 			/* 'eCommerce scss' */
				}]
            }
        },
        uglify: {
          my_target: {
            files: {
                'assets/bundles/libscripts.bundle.js': ['assets/plugins/jquery/jquery-v3.2.1.min.js','assets/plugins/bootstrap/js/bootstrap.js'], /* main js*/              
                'assets/bundles/vendorscripts.bundle.js':
                ['assets/plugins/bootstrap-select/js/bootstrap-select.js','assets/plugins/jquery-slimscroll/jquery.slimscroll.js','assets/plugins/node-waves/waves.js','assets/plugins/fullscreen/screenfull.js','assets/plugins/jquery-sparkline/jquery.sparkline.js','assets/plugins/jquery-countto/jquery.countTo.js'], /* coman js*/
                'assets/bundles/mainscripts.bundle.js':
                ['assets/js/admin.js','assets/js/demo.js','assets/js/fullscreen.js'], /*coman js*/
				                
                'assets/bundles/index.jvectormap.bundle.js': ['assets/plugins/jvectormap/jquery-jvectormap-2.0.3.min.js','assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js','assets/plugins/jvectormap/jquery-jvectormap-in-mill.js','assets/plugins/jvectormap/jquery-jvectormap-us-aea-en.js'], /* ChartJs js*/

                'assets/bundles/morrisscripts.bundle.js': ['assets/plugins/raphael/raphael.min.js','assets/plugins/morrisjs/morris.js'], /* Morris Plugin Js */
                'assets/bundles/flotscripts.bundle.js': ['assets/plugins/flot-charts/jquery.flot.js','assets/plugins/flot-charts/jquery.flot.resize.js','assets/plugins/flot-charts/jquery.flot.pie.js','assets/plugins/flot-charts/jquery.flot.categories.js','assets/plugins/flot-charts/jquery.flot.time.js'], /* Flot Chart js*/                
                'assets/bundles/chartscripts.bundle.js': ['assets/plugins/chartjs/Chart.bundle.js'], /* ChartJs js*/
                'assets/bundles/jvectormap.bundle.js': ['assets/plugins/jvectormap/jquery-jvectormap-2.0.3.min.js','assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js'], /* ChartJs js*/
                'assets/bundles/sparkline.bundle.js': ['assets/plugins/jquery-sparkline/jquery.sparkline.js'], /* sparkline js*/               
                'assets/bundles/countTo.bundle.js': ['assets/plugins/jquery-countto/jquery.countTo.js'], /* CountTo js*/               
                'assets/bundles/knob.bundle.js': ['assets/plugins/jquery-knob/jquery.knob.min.js'], /* knob js*/
                'assets/bundles/fullcalendarscripts.bundle.js': ['assets/plugins/fullcalendar/lib/moment.min.js','assets/plugins/fullcalendar/lib/jquery-ui.min.js','assets/plugins/fullcalendar/fullcalendar.min.js'],   /* calender page js */
                'assets/bundles/datatablescripts.bundle.js': ['assets/plugins/jquery-datatable/jquery.dataTables.min.js','assets/plugins/jquery-datatable/dataTables.bootstrap4.min.js'], /* Jquery DataTable Plugin Js  */
                'assets/bundles/footable.bundle.js': ['assets/plugins/footable-bootstrap/js/footable.js'], /* knob js*/
              }
            }
        }                
    });
    grunt.loadNpmTasks("grunt-sass");
    grunt.loadNpmTasks('grunt-contrib-uglify');
    
    grunt.registerTask("buildcss", ["sass"]);	
    grunt.registerTask("buildjs", ["uglify"]);
};


