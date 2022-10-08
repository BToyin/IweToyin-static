const gulp = require('gulp');
const sass = require('gulp-sass');
const sourceMaps = require('gulp-sourcemaps');
const autoprefixer = require('gulp-autoprefixer');
const browserSync = require('browser-sync');
const del = require('del');
var connect = require('gulp-connect');

//SCSS compilation

function style() {
    return gulp.src('/scss/*.scss')
    .pipe(sourceMaps.init())
    .pipe(sass().on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(sourceMaps.write('/'))
    .pipe(gulp.dest('/css'))
    .pipe(browserSync.stream());
}

function watch() {
    browserSync.init({
        server: {
            baseDir: './',
        },
        startPath: 'index.html',
        ghostMode: false,
        notify: false
    });
    style();
    gulp.watch('/scss/**/*.scss', style);
    gulp.watch('/*.html').on('change', browserSync.reload);
    gulp.watch('/js/*.js').on('change', browserSync.reload);

}

function cleanVendors(){
    return del('/vendors/**/*');
}

function buildVendors() {
    var addon1 = gulp.src('/node_modules/bootstrap/**/*')
                     .pipe(gulp.dest('/vendors/bootstrap'));
    var addon2 = gulp.src('/node_modules/jquery/dist/**/*')
                     .pipe(gulp.dest('/vendors/jquery'));
    var addon3 = gulp.src('/node_modules/popper.js/dist/umd/**/*')
                     .pipe(gulp.dest('/vendors/popper.js'));
    var addon4 = gulp.src('/node_modules/animate.css/**/**')
                     .pipe(gulp.dest('/vendors/animate.css'));
    var addon5 = gulp.src('/node_modules/wowjs/dist/*')
                     .pipe(gulp.dest('/vendors/wowjs'));

    return (addon1, addon2, addon3, addon4, addon5);
}

var gulp = require('gulp');


gulp.task('myStyles', function () {
    gulp.src('sass/*.scss')
        .pipe(scssPlugin())
        .pipe(gulp.dest('css'))
        .pipe(connect.reload());
});

gulp.task('connect', function() {
    connect.server({
        livereload: true
    });
});

gulp.task('watchMyStyles', function() {
    gulp.watch('sass/*.scss', ['myStyles']);
});

gulp.task('default', ['watchMyStyles', 'connect']);

exports.style = style;
exports.watch = watch;
exports.buildVendors = gulp.series(cleanVendors, buildVendors);