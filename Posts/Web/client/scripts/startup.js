requirejs.config({
  baseUrl: '/client/scripts',
  paths: {
    domReady: ["../../lib/dom_ready/dom_ready"],
    jsrender: ["../../lib/jsrender/jsrender.min"],
    underscore: ["../../lib/underscore/underscore-min"]
  },
  map: {
    '*': {
      'communication_client': 'communication/api_client'
    }
  }
});

require(["core/config", "router", "domReady", "jsrender"],
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
