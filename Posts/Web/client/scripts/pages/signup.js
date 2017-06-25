define(["../core/globals", "../core/didoauth", "../navigation/router", "underscore"], function(globals, didoauth, router, _) {
  var self = {};

  didoauth.config.signUp = function() {
    if (self.notificationArea) {
      self.notificationArea.success({
        message: "Successfully signed up. You can now log in with your account."
      });
    }

    _.delay(function() {
      router.toLogin();
    }, 2000);
  }

  self.init = function() {
    self.validatorForm = $("#formSignUp");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    $("#btnSignup").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          $.didoauth.emailSignUp({
            email: $("#inputEmail").val(),
            firstName: $("#inputFirst").val(),
            lastName: $("#inputLast").val(),
            password: $("#inputPassword").val(),
            password_confirmation: $("#inputPassword").val()
          });
        }
      });
    });
  }

  return self;
});
