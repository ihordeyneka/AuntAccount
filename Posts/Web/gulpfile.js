var changed = require('gulp-changed');
var gulp = require('gulp');
var runSequence = require('run-sequence');
var sourcemaps = require('gulp-sourcemaps');
var concat = require('gulp-concat');
var merge = require('merge-stream')
var spawn = require('child_process').spawn;
var del = require('del');
var nodeServer = null;

var webpack = require('webpack');
var webpackStream = require('webpack-stream');
var webpackConfig = require('./webpack.config.js');

var packageJson = require('./package.json');
var PATHS = {
  lib: [
    { name: 'jquery', files: 'node_modules/jquery.1/node_modules/jquery/dist/**/*' },
    { name: 'jquery_deparam', files: 'node_modules/jquery-deparam/jquery-deparam.js' },
    { name: 'jsrender', files: 'node_modules/jsrender/*.{js,map}' },
    { name: 'jquery_maskedinput', files: 'node_modules/jquery.maskedinput/src/**/*' },
    { name: 'bootstrap', files: 'node_modules/bootstrap/dist/**/*' },
    { name: 'bootstrap_table', files: 'node_modules/bootstrap-table/dist/**/*' },
    { name: 'bootstrap_slider', files: 'node_modules/bootstrap-slider/dist/**/*' },
    { name: 'bootstrap3_typeahead', files: 'node_modules/bootstrap-3-typeahead/**/*' },
    { name: 'bootstrap_fileinput', files: 'node_modules/bootstrap-fileinput/{css,js}/**/*' },
    { name: 'bootstrap_validator', files: 'node_modules/bootstrap-validator/dist/**/*' },
    { name: 'bootstrap_tagsinput', files: 'node_modules/bootstrap-tagsinput/dist/**/*' },
    { name: 'requirejs', files: 'node_modules/requirejs/require.js' },
    { name: 'font_awesome', files: 'node_modules/font-awesome/**/*' },
    { name: 'underscore', files: 'node_modules/underscore/**/*' },
    { name: 'moment', files: 'node_modules/moment/**/*' },
    { name: 'dom_ready', files: 'other_modules/dom_ready/**/*' },
    { name: 'jquery_raty', files: 'other_modules/jquery_raty/**/*' },
    { name: 'jquery_i18n', files: 'other_modules/jquery_i18n/**/*' }
  ],
  client: {
    js: ['client/**/*.{js,json}'],
    html: 'client/**/*.html',
    css: 'client/**/*.css',
    img: 'client/**/*.{svg,jpg,png,ico}',
    fonts: 'client/**/*.{ttf,otf,woff,woff2}'
  },
  pwa: 'client/{manifest.json,sw.js}',
  dist: 'dist',
  distClient: 'dist/client',
  distLib: 'dist/lib',
  distPwa: 'dist/{manifest.json,sw.js}',
  port: 8282
};

gulp.task('server', function() {
  nodeServer = spawn('node', [packageJson.main, '--port', PATHS.port]);
});

gulp.task('abandon', function(){
  if (nodeServer)
    nodeServer.kill();
});

gulp.task('html', function() {
  return gulp
    .src(PATHS.client.html)
    .pipe(changed(PATHS.distClient))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('css', function() {
  return gulp
    .src(PATHS.client.css)
    .pipe(changed(PATHS.distClient, {
      extension: '.css'
    }))
    .pipe(sourcemaps.init())
    .pipe(concat('app.css'))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(PATHS.distClient + '/css'));
});

gulp.task('libs', function() {
  var streams = [];

  for (var i=0; i<PATHS.lib.length; i++) {
    var lib = PATHS.lib[i];
    streams.push(gulp
      .src(lib.files)
      .pipe(gulp.dest(PATHS.distLib + '/' + lib.name)));
  }

  return merge(streams);
});

gulp.task('webpack', function () {
  return gulp.src('./client/scripts/startup.js')
    .pipe(webpackStream(webpackConfig), webpack)
    .pipe(gulp.dest(PATHS.distClient + "/scripts"));
});

gulp.task('js', function() {
  return gulp
    .src(PATHS.client.js)
    .pipe(changed(PATHS.distClient))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('img', function() {
  return gulp
    .src(PATHS.client.img)
    .pipe(changed(PATHS.distClient))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('fonts', function() {
  return gulp
    .src(PATHS.client.fonts)
    .pipe(changed(PATHS.distClient))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('clean', function() {
  del([PATHS.distClient], function(){ });
  del([PATHS.distLib], function(){ });
  del([PATHS.distPwa], function(){ });
});

gulp.task('watch', function() {
  gulp.watch(PATHS.client.js, ['webpack']);
  gulp.watch(PATHS.client.html, ['html']);
  gulp.watch(PATHS.client.css, ['css']);
  gulp.watch(PATHS.client.img, ['img']);
});

gulp.task('pwa', function() {
  return gulp
    .src(PATHS.pwa)
    .pipe(changed(PATHS.dist))
    .pipe(gulp.dest(PATHS.dist));
});


gulp.task('bundle', function() {
  runSequence(['html', 'css', 'webpack', 'libs', 'img', 'fonts', 'pwa']);
});

gulp.task('default', ['server', 'bundle'], function() {
  runSequence('watch');
});
