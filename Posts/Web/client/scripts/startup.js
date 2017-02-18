requirejs.config({
  baseUrl: '/client/scripts',
  map: {
    '*': {
      'communication_client': 'communication/mock_client'
    }
  }
});

require(["core/config"], function(config) {
  $.auth.configure({
    apiUrl:                config.apiRoot,
    signOutPath:           '/auth/sign_out',
    emailSignInPath:       '/auth/sign_in',
    emailRegistrationPath: '/auth',
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

    parseExpiry: function(headers){
      return (parseInt(headers['expiry'], 10) * 1000) || null;
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
      google:    '/auth/google_oauth2',
      facebook:  '/auth/facebook'
    }
  });
});
