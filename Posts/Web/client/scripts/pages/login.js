define(["../../views/login.html", "../core/globals", "../core/config"], function(source, globals, config) {
  var self = {};

  self.source = source;

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
      });
    });

    $("#btnRecoveryEmail").click(function() {
      var validatorForm = $("#formPasswordReset");
      validatorForm.validator({ focus: false });
      var notificationArea = $(".password-reset-notification-area").notificationArea();

      globals.validate({
        notificationArea: notificationArea,
        validatorForm: validatorForm,
        success: function() {
          var passwordData = {
            email: $("#inputRecoveryEmail").val()
          };
          globals.loading(validatorForm, true);
          $.post({
              url: config.apiRoot + "/users/passwordReset",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(passwordData)
          }).done(function(data) {
            notificationArea.success({
              message: $.i18n('PasswordResetConfirmed')
            });
          }).fail(function(result) {
            notificationArea.error();
          }).always(function() {
            globals.loading(validatorForm, false);
          });
        }
      });
    });
  }

  return self;
});
