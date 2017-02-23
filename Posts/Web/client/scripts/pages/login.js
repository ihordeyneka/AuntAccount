define(["../core/globals", "../core/config", "communication_client"], function(globals, config, client) {
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
        email: $("#inputEmail").val(),
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
