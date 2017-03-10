define(["jquery"], function ($) {
  var root = Function('return this')();

  if (root.didoauth) {
    return root.didoauth;
  }

  var ACCESS_TOKEN_KEY = 'access-token';
  var USER_DATA_KEY = 'current-user';
  var ERROR_EMAIL_SIGNIN = 1;

  var isApiRequest = function(url) {
    return (url.match(root.didoauth.config.apiUrl));
  };

  var unescapeQuotes = function(val) {
    return val && val.replace(/("|')/g, '');
  };

  var Auth = function () {
    // set flag so we know when plugin has been configured.
    this.configured = false;

    // save reference to user
    this.user = {};

    // config
    this.config = {};

    // base config from which other configs are extended
    this.configBase = {
      apiUrl:                '/api',
      signOutPath:           '/auth/sign_out',
      emailSignInPath:       '/token',
      emailRegistrationPath: '/users',
      signIn: function() {},
      error: function(err, data) {},

      authProviderPaths: {
        github:    '/auth/github',
        facebook:  '/auth/facebook',
        google:    '/auth/google_oauth2'
      }
    };
  };

  Auth.prototype.configure = function(opts, reset) {
    // destroy all session data
    if (reset) {
      this.reset();
    }

    //if already configured, do nothing
    if (this.configured)
      return;

    // normalize opts into object object
    if (!opts) {
      opts = {};
    }

    var user = root.didoauth.retrieveData(USER_DATA_KEY);
    if (user)
      root.didoauth.setCurrentUser(user, false);

    // set configured
    this.config = $.extend({}, this.configBase, opts);
    this.configured = true;

    // intercept requests to the API, append auth headers
    $.ajaxSetup({beforeSend: root.didoauth.appendAuthHeaders});
  };

  Auth.prototype.reset = function() {
    // clean up session without relying on `getConfig`
    this.destroySession();

    this.config = {};
    this.configured = false;

    // remove global ajax "interceptors"
    $.ajaxSetup({beforeSend: undefined});
  };

  Auth.prototype.destroySession = function() {
    //clear user object, leave root in case of any bindings
    for (var key in this.user) {
      delete this.user[key];
    }

    root.didoauth.deleteData(ACCESS_TOKEN_KEY);
    root.didoauth.deleteData(USER_DATA_KEY);
  };

  Auth.prototype.setCurrentUser = function(user, persist) {
    // clear user object of any existing attributes
    for (var key in this.user) {
      delete this.user[key];
    }

    // save user data, preserve bindings to original user object
    $.extend(this.user, user);
    this.user.signedIn = true;

    if (persist)
      root.didoauth.persistData(USER_DATA_KEY, user);

    return this.user;
  };

  Auth.prototype.emailSignUp = function(data) {
    // normalize data
    if (!data) {
      data = {};
    }

    var config = this.config;
    var url = config.apiUrl + config.emailRegistrationPath;

    $.ajax({
      url: url,
      context: this,
      method: 'POST',
      data: data,

      success: function(resp) {
        //TODO: do something
      },

      error: function(resp) {
        //TODO: do something
      }
    });
  };

  Auth.prototype.emailSignIn = function(data) {
    // normalize data
    if (!data) {
      data = {};
    }

    var config = this.config;
    var url = config.apiUrl + config.emailSignInPath;

    $.ajax({
      url: url,
      context: this,
      method: 'POST',
      data: data,

      success: function(resp, textStatus, request) {
        // save user data, preserve bindings to original user object
        this.setCurrentUser(resp, true);

        //update token information
        root.didoauth.updateAuthHeaders(request);

        //trigger signIn event handler
        this.config.signIn();
      },

      error: function(resp) {
        //trigger error event handler
        this.config.error(ERROR_EMAIL_SIGNIN, resp);
      }
    });
  };

  Auth.prototype.oAuthSignIn = function(opts) {
    // normalize opts
    if (!opts) {
      opts = {};
    }

    if (!opts.provider) {
      throw 'didoauth: provider expected';
    }

    var providerPath = this.config.authProviderPaths[opts.provider];
    var oAuthUrl = this.buildOAuthUrl(opts.params, providerPath);

    // open link to provider auth screen
    this.openAuthWindow(oAuthUrl);
  };

  Auth.prototype.buildOAuthUrl = function(params, providerPath) {
    var oAuthUrl = this.config.apiUrl + providerPath +
        '?auth_origin_url='+encodeURIComponent(root.location.href) +
        "&omniauth_window_type=newWindow";

    if (params) {
      for(var key in params) {
        oAuthUrl += '&';
        oAuthUrl += encodeURIComponent(key);
        oAuthUrl += '=';
        oAuthUrl += encodeURIComponent(params[key]);
      }
    }

    return oAuthUrl;
  };

  Auth.prototype.openAuthWindow = function(url) {
    root.location = url;
  };

  Auth.prototype.signOut = function() {
    var signOutUrl = this.config.apiUrl + this.config.signOutPath;

    $.ajax({
      url: signOutUrl,
      context: this,
      method: 'DELETE',

      success: function(resp) {
        //TODO: do something
      },

      error: function(resp) {
        //TODO: do something
      },

      complete: function() {
        this.destroySession();
      }
    });
  };

  Auth.prototype.updateAuthHeaders = function(request) {
    var accessToken = request.getResponseHeader(ACCESS_TOKEN_KEY);
    if (accessToken)
      root.didoauth.persistData(ACCESS_TOKEN_KEY, accessToken);
  }

  Auth.prototype.appendAuthHeaders = function(xhr, settings) {
    // fetch current auth headers from storage
    var accessToken = root.didoauth.retrieveData(ACCESS_TOKEN_KEY);

    // check config apiUrl matches the current request url
    if (isApiRequest(settings.url) && accessToken) {

      // bust IE cache
      xhr.setRequestHeader(
        'If-Modified-Since',
        'Mon, 26 Jul 1997 05:00:00 GMT'
      );
      xhr.setRequestHeader("Authentication", "Bearer " + accessToken);
    }
  };

  // abstract storing of session data
  Auth.prototype.persistData = function(key, val) {
    if (root.localStorage)
      root.localStorage.setItem(key, val);
  };

  // abstract reading of session data
  Auth.prototype.retrieveData = function(key) {
    var val = null;

    if (root.localStorage)
      val = root.localStorage.getItem(key);

    // if value is a simple string, the parser will fail. in that case, simply
    // unescape the quotes and return the string.
    try {
      // return parsed json response
      return $.parseJSON(val);
    } catch (err) {
      // unescape quotes
      return unescapeQuotes(val);
    }
  };

  // abstract deletion of session data
  Auth.prototype.deleteData = function(key) {
    if (root.localStorage)
      root.localStorage.removeItem(key);
  };

  // export service
  root.didoauth = $.didoauth = new Auth();

  return root.didoauth;
});