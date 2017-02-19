define(["../core/globals", "communication_client"], function(globals, client) {
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
        password: $("#inputPassword").val()
      });
    });
  }

  return self;
});
