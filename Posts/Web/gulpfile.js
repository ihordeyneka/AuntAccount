var changed = require('gulp-changed');
var gulp = require('gulp');
var runSequence = require('run-sequence');
var sourcemaps = require('gulp-sourcemaps');
var concat = require('gulp-concat');
var merge = require('merge-stream')
var spawn = require('child_process').spawn;
var del = require('del');
var nodeServer = null;

var packageJson = require('./package.json');
var PATHS = {
  lib: {
    jquery: 'node_modules/jquery.1/node_modules/jquery/dist/**/*',
    bootstrap: 'node_modules/bootstrap/dist/**/*',
    font_awesome: 'node_modules/font-awesome/**/*'
  },
  client: {
    js: ['client/**/*.js'],
    html: 'client/**/*.html',
    css: 'client/**/*.css',
    img: 'client/**/*.{svg,jpg,png,ico}',
    fonts: 'client/**/*.{ttf,otf,woff,woff2}'
  },
  dist: 'dist',
  distClient: 'dist/client',
  distLib: 'dist/lib',
  port: 7000
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
  var jquery = gulp
    .src(PATHS.lib.jquery)
    .pipe(gulp.dest(PATHS.distLib + '/jquery'));

  var bootstrap = gulp
    .src(PATHS.lib.bootstrap)
    .pipe(gulp.dest(PATHS.distLib + '/bootstrap'));

  var font_awesome = gulp
    .src(PATHS.lib.font_awesome)
    .pipe(gulp.dest(PATHS.distLib + '/font_awesome'));

  return merge(jquery, bootstrap, font_awesome);
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
});

gulp.task('watch', function() {
  gulp.watch(PATHS.client.js, ['js']);
  gulp.watch(PATHS.client.html, ['html']);
  gulp.watch(PATHS.client.css, ['css']);
  gulp.watch(PATHS.client.img, ['img']);
});

gulp.task('bundle', function() {
  runSequence(['html', 'css', 'libs', 'js', 'img', 'fonts']);
});

gulp.task('default', ['server', 'bundle'], function() {
  runSequence('watch');
});
