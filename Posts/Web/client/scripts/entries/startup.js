require(["../core/globals", "../core/config", "../core/didoauth", "../core/resources", "../navigation/router", "../navigation/menu", "../components/language_selector", "dom_ready/dom_ready", "jquery", "bootstrap", "jsrender", "bootstrap-validator"],
  function(globals, config, didoauth, resources, router, menu, languageSelectorControl, domReady, $, bootstrap, jsrender, bootstrapValidator) {

  window.jQuery = $;
  jsrender($);

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
        globals.loading($('body'), true);
        resources.setLocale(data.locale).done(function() {
          resources.translate();
          globals.loading($('body'), false);
        });
      });

      resources.translate();
    });
  });
});
