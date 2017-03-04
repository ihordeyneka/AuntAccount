define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function() {
    self.notificationArea = $(".aa-notification-area").notificationArea();

    $("#btnGoogleSignIn").click(function() {
      $.auth.oAuthSignIn({
        provider: 'google',
        params: {}
      });
    });

    $("#btnFbSignIn").click(function() {
      $.auth.oAuthSignIn({
        provider: 'facebook',
        params: {}
      });
    });

    $("#btnLogin").click(function() {
      $.auth.emailSignIn({
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
