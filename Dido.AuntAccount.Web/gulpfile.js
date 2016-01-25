'use strict';

var changed = require('gulp-changed');
var del = require('del');
var gulp = require('gulp');
var runSequence = require('run-sequence');
var sourcemaps = require('gulp-sourcemaps');
var concat = require('gulp-concat');
var ts = require('gulp-typescript');
var tslint = require('gulp-tslint');
var spawn = require('child_process').spawn;
var nodeServer = null;

var packageJson = require('./package.json');


var PATHS = {
  lib: [
    'node_modules/angular2/node_modules/traceur/bin/traceur-runtime.js',
    'node_modules/angular2/node_modules/rx/dist/rx.js',
    'node_modules/reflect-metadata/Reflect.js',
    'node_modules/zone.js/dist/zone.js',
    'node_modules/zone.js/dist/long-stack-trace-zone.js',
    '!node_modules/systemjs/dist/*.src.js',
    'node_modules/systemjs/dist/*.js'
  ],
  typings: [
    'typings/tsd.d.ts'
  ],
  client: {
    ts: ['client/**/*.ts'],
    html: 'client/**/*.html',
    css: 'client/**/*.css',
    img: 'client/**/*.{svg,jpg,png,ico}'
  },
  dist: 'dist',
  distClient: 'dist/client',
  distLib: 'dist/lib',
  port: 3000
};

var tsProject = ts.createProject('tsconfig.json', {
  typescript: require('typescript')
});

gulp.task('clean', function() {
  del([PATHS.dist], function(){ });
});

gulp.task('angular2', function() {
  return gulp
		.src([
			'!node_modules/angular2/es6/**',
			'!node_modules/angular2/node_modules/**',
			'!node_modules/angular2/angular2.api.js',
			'!node_modules/angular2/angular2_sfx.js',
      '!node_modules/angular2/angular2.api.js',
			'!node_modules/angular2/ts/**',
			'node_modules/angular2/**/*.js'
		])
		.pipe(gulp.dest(PATHS.dist + '/lib/angular2'));
});

gulp.task('libs', ['angular2'], function() {
  return gulp
    .src(PATHS.lib)
    .pipe(gulp.dest(PATHS.distLib));
});

gulp.task('ts', function() {
  return gulp
    .src([].concat(PATHS.typings, PATHS.client.ts)) // instead of gulp.src(...), project.src() can be used
    .pipe(changed(PATHS.distClient, {
      extension: '.js'
    }))
    .pipe(sourcemaps.init())
    .pipe(ts(tsProject))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('lint', function () { // https://github.com/palantir/tslint#supported-rules
	return gulp
		.src(PATHS.client.ts)
		.pipe(tslint())
		.pipe(tslint.report('prose', {
			emitError: false
		}));
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
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('img', function() {
  return gulp
    .src(PATHS.client.img)
    .pipe(changed(PATHS.distClient))
    .pipe(gulp.dest(PATHS.distClient));
});

gulp.task('bundle', ['clean'], function(done) {
  runSequence(['libs'], ['lint', 'ts', 'html', 'css', 'img'], done);
});

gulp.task('watch', function() {
  gulp.watch(PATHS.client.ts, ['ts']);
  gulp.watch(PATHS.client.html, ['html']);
  gulp.watch(PATHS.client.css, ['css']);
  gulp.watch(PATHS.client.img, ['img']);
});

gulp.task('server', function() {
  nodeServer = spawn('node', [packageJson.main, '--port', PATHS.port]);
});

gulp.task('abandon', function(){
  if (nodeServer)
    nodeServer.kill();
});

gulp.task('default', ['bundle', 'watch', 'server']);
