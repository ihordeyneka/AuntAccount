import globals from "../core/globals"
import config from "../core/config"
import didoauth from "../core/didoauth"
import resources from "../core/resources"
import router from "../navigation/router"
import menu from "../navigation/menu"
import languageSelectorControl from "../components/language_selector"
import domReady from "dom_ready/dom_ready"
import $ from "jquery"
import bootstrap from "bootstrap"
import jsrender from "jsrender"
import bootstrapValidator from "bootstrap-validator"

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

