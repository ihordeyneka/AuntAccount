requirejs.config({
  baseUrl: '/client/scripts',
  paths: {
    domReady: ["../../lib/dom_ready/dom_ready"],
    jquery: ["../../lib/jquery/jquery.min"],
    bootstrap: ["../../lib/bootstrap/js/bootstrap.min"],
    jsrender: ["../../lib/jsrender/jsrender.min"],
    validator: ["../../lib/bootstrap_validator/validator.min"],
    "jquery.cookie": ["../../lib/jquery_cookie/jquery.cookie"],
    "jquery-deparam": ["../../lib/jquery_deparam/jquery-deparam"],
    "pubsub-js": ["../../lib/pubsub_js/pubsub"],
    jtoker: ["../../lib/j_toker/jquery.j-toker.min"],
    tagsinput: ["../../lib/bootstrap_tagsinput/bootstrap-tagsinput.min"],
    underscore: ["../../lib/underscore/underscore-min"],
    typeahead: ["../../lib/bootstrap3_typeahead/bootstrap3-typeahead.min"],
    fileinput: ["../../lib/bootstrap_fileinput/js/fileinput.min"],
    slider: ["../../lib/bootstrap_slider/bootstrap-slider.min"]
  },
  shim: {
    bootstrap: { deps: ["jquery"] },
    jsrender: { deps: ["jquery"] },
    validator: { deps: ["jquery"] },
    "jquery.cookie": { deps: ["jquery"] },
    "jquery-deparam": { deps: ["jquery"] },
    jtoker: { deps: ["jquery.cookie", "jquery-deparam", "pubsub-js"] }
  }
});

require(["core/config", "router", "domReady", "jquery", "bootstrap", "jsrender", "validator",
  "jquery.cookie", "jquery-deparam", "pubsub-js", "jtoker"],
  function(config, router, domReady) {
  $.auth.configure({
    apiUrl:                config.apiRoot,
    signOutPath:           '/auth/sign_out',
    emailSignInPath:       '/token',
    emailRegistrationPath: '/users',
    accountUpdatePath:     '/auth',
    accountDeletePath:     '/auth',
    passwordResetPath:     '/auth/password',
    passwordUpdatePath:    '/auth/password',
    tokenValidationPath:   '/auth/validate_token',
    proxyIf:               function() { return false; },
    proxyUrl:              '/proxy',
    validateOnPageLoad:    false,
    forceHardRedirect:     false,
    storage:               'localStorage',
    cookieExpiry:          14,
    cookiePath:            '/',

    passwordResetSuccessUrl: function() {
      return window.location.href;
    },

    confirmationSuccessUrl:  function() {
      return window.location.href;
    },

    tokenFormat: {
      "access-token": "{{ access-token }}",
      "token-type":   "Bearer",
      client:         "{{ client }}",
      expiry:         "{{ expiry }}",
      uid:            "{{ uid }}"
    },

    handleLoginResponse: function(resp) {
      return resp.data;
    },

    handleAccountUpdateResponse: function(resp) {
      return resp.data;
    },

    handleTokenValidationResponse: function(resp) {
      return resp.data;
    },

    authProviderPaths: {
      google:    '/auth/google',
      facebook:  '/auth/fb'
    }
  });

  $.auth.createPopup = function(url) {
    window.location = url;
  };

  domReady(function(){
    router.init();
  });
});
