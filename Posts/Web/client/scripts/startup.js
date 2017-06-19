requirejs.config({
  waitSeconds: 30,
  baseUrl: '/client/scripts',
  paths: {
    domReady: ["../../lib/dom_ready/dom_ready"],
    jquery: ["../../lib/jquery/jquery.min"],
    jqueryRaty: ["../../lib/jquery_raty/jquery.raty-fa"],
    maskedinput: ["../../lib/jquery_maskedinput/jquery.maskedinput"],
    bootstrap: ["../../lib/bootstrap/js/bootstrap.min"],
    bootstraptable: ["../../lib/bootstrap_table/bootstrap-table.min"],
    jsrender: ["../../lib/jsrender/jsrender.min"],
    validator: ["../../lib/bootstrap_validator/validator"],
    tagsinput: ["../../lib/bootstrap_tagsinput/bootstrap-tagsinput.min"],
    underscore: ["../../lib/underscore/underscore-min"],
    typeahead: ["../../lib/bootstrap3_typeahead/bootstrap3-typeahead.min"],
    fileinput: ["../../lib/bootstrap_fileinput/js/fileinput.min"],
    slider: ["../../lib/bootstrap_slider/bootstrap-slider.min"],
    moment: ["../../lib/moment/min/moment.min"],
    googleapi: ["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&libraries=places"]
  },
  shim: {
    bootstrap: { deps: ["jquery"] },
    jsrender: { deps: ["jquery"] },
    validator: { deps: ["jquery"] }
  }
});

require(["core/config", "core/didoauth", "navigation/router", "navigation/menu", "domReady", "jquery", "bootstrap", "jsrender", "validator"],
  function(config, didoauth, router, menu, domReady) {
  didoauth.configure({
    apiUrl:                config.apiRoot,
    signOutPath:           '/auth/sign_out',
    emailSignInPath:       '/token',
    emailRegistrationPath: '/users',

    signIn: function() {
      router.home();
    },
    signOut: function() {
      router.home();
    },

    authProviderPaths: {
      google:    '/auth/google',
      facebook:  '/auth/fb'
    }
  });

  domReady(function(){
    menu.refresh();
    router.init();
  });
});
