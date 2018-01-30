define([], function () {

  if (window.googleApiPromise)
    return window.googleApiPromise;

  var module = require("load-google-maps-api-2");

  module.key = 'AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU';
  module.libraries = "places";
  module.language = 'en-US';

  window.googleApiPromise = module(); //return promise

  return window.googleApiPromise;
});