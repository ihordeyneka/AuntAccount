//bundle css
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap-table/dist/bootstrap-table.min.css"
import "bootstrap-slider/dist/css/bootstrap-slider.min.css"
import "bootstrap-fileinput/css/fileinput.min.css"
import "bootstrap-tagsinput/dist/bootstrap-tagsinput.css"
import "bootstrap-tagsinput/dist/bootstrap-tagsinput-typeahead.css"
import "font-awesome/css/font-awesome.min.css"
import "../../fonts/_fonts.css"
import "../../css/bootstrap.css"
import "../../css/global.css"
import "../../css/reset.css"
import "../../css/site.css"
import "../../css/input/awesome-bootstrap-checkbox.css"
import "../../css/input/message_input.css"
import "../../css/pages/login.css"
import "../../css/pages/offer.css"
import "../../css/pages/post.css"
import "../../css/pages/postoffers.css"
import "../../css/pages/profile.css"
import "../../css/pages/recent.css"
import "../../css/pages/seller.css"
import "../../css/pages/sellers.css"


//bundle js
import "../core/polyfills"
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

import "../pages/subscription.js"

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
        //resources.translate();
        //globals.loading($('body'), false);
        window.location.reload();
      });
    });

    resources.translate();
  });
});

