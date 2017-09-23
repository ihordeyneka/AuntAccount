requirejs.config({
  waitSeconds: 30,
  baseUrl: '/client/scripts',
  paths: {
    domReady: ["../../lib/dom_ready/dom_ready"],
    jquery: ["../../lib/jquery/jquery.min"],
    jqueryRaty: ["../../lib/jquery_raty/jquery.raty-fa"],
    i18n: ["../../../lib/jquery_i18n/jquery.i18n"],
    i18n_messagestore: ["../../../lib/jquery_i18n/jquery.i18n.messagestore"],
    i18n_fallbacks: ["../../../lib/jquery_i18n/jquery.i18n.fallbacks"],
    i18n_parser: ["../../../lib/jquery_i18n/jquery.i18n.parser"],
    i18n_emitter: ["../../../lib/jquery_i18n/jquery.i18n.emitter"],
    i18n_language: ["../../../lib/jquery_i18n/jquery.i18n.language"],
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
    googleapi: ["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&libraries=places&language=en-US"]
  },
  shim: {
    bootstrap: { deps: ["jquery"] },
    jsrender: { deps: ["jquery"] },
    validator: { deps: ["jquery"] },
    i18n: { deps: ["jquery"] },
    i18n_messagestore: { deps: ["i18n"] },
    i18n_fallbacks: { deps: ["i18n"] },
    i18n_parser: { deps: ["i18n"] },
    i18n_emitter: { deps: ["i18n"] },
    i18n_language: { deps: ["i18n"] }
  }
});

require(["core/config", "core/didoauth", "core/resources", "navigation/router", "navigation/menu", "components/language_selector", "domReady", "jquery", "bootstrap", "jsrender", "validator"],
  function(config, didoauth, resources, router, menu, languageSelectorControl, domReady) {
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
    resources.load().done(function() {
      menu.refresh();
      router.init();

      var languageSelector = new languageSelectorControl($(".aa-language-selector-container"), resources.getSupportedLocales());
      languageSelector.setLanguage(resources.getLocale());
      languageSelector.element.on("localeChanged", function(e, data) {
        resources.setLocale(data.locale);
        resources.translate();
      });
    });
  });
});
