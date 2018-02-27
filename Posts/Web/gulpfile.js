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
  client: {
    js: ['client/**/*.{js,json}'],
    html: 'client/**/*.html',
    img: 'client/**/*.{svg,jpg,png,ico}',
    fonts: 'client/**/*.{ttf,otf,woff,woff2}'
  },
  pwa: 'client/{manifest.json,sw.js}',
  dist: 'dist',
  distClient: 'dist/client',
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

gulp.task('webpack', function () {
  return gulp.src('./client/scripts/startup.js')
    .pipe(webpackStream(webpackConfig), webpack)
    .pipe(gulp.dest(PATHS.distClient + "/scripts"));
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
  runSequence(['html', 'webpack', 'img', 'fonts', 'pwa']);
});

gulp.task('default', ['server', 'bundle'], function() {
  runSequence('watch');
});
