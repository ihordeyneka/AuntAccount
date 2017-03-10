define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function() {
    self.notificationArea = $(".aa-notification-area").notificationArea();

    $("#btnGoogleSignIn").click(function() {
      $.didoauth.oAuthSignIn({
        provider: 'google',
        params: {}
      });
    });

    $("#btnFbSignIn").click(function() {
      $.didoauth.oAuthSignIn({
        provider: 'facebook',
        params: {}
      });
    });

    $("#btnLogin").click(function() {
      $.didoauth.emailSignIn({
        username: $("#inputEmail").val(),
        password: $("#inputPassword").val(),
        grant_type: "password",
        client_secret: config.clientSecret,
        client_id: config.clientId,
        remember_me: $("#inputRememberMe").is(":checked")
      });
    });
  }

  return self;
});
