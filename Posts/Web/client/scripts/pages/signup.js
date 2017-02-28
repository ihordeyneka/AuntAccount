define(["../core/globals"], function(globals) {
  var self = {};

  self.init = function() {
    self.validatorForm = $("#formSignUp");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    $("#btnSignup").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          $.auth.emailSignUp({
            email: $("#inputEmail").val(),
            firstName: $("#inputFirst").val(),
            lastName: $("#inputLast").val(),
            password: $("#inputPassword").val(),
            password_confirmation: $("#inputPassword").val()
          });

          //TODO: use PubSub to handle events
          //self.notificationArea.success({
          //  message: "Your account has been successfully created. You should receive an email to activate it shortly."
          //});
          //self.notificationArea.error();
        }
      });
    });
  }

  return self;
});
