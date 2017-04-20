define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function(recoveryToken) {
    if (recoveryToken != "") {
      $("#inputOldPassword").val("XXXXXXXX"); //creating an illusion for user as if the old password is already entered
      $("#btnBack").hide();
    } else {
      $("#btnBack").show();
    }


    self.validatorForm = $("#formPassword");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    $("#btnUpdatePassword").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var passwordData = {
            oldPassword: $("#inputOldPassword").val(),
            newPassword: $("#inputNewPassword").val(),
            recoveryToken: recoveryToken
          };

          $.post({
              url: config.apiRoot + "/users/password",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(passwordData)
          }).done(function(data) {
            self.notificationArea.success({
              message: "Password has been changed."
            });
          }).fail(function(result) {
            self.notificationArea.error();
          }).always(function() {
            globals.loading($('body'), false);
          });
        }
      });
    });
  }

  return self;
});
