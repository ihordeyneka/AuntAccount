define(["core/globals", "core/didoauth", "core/config", "fileinput"], function(globals, didoauth, config, fileinput) {
  var self = {};

  self.init = function() {
    $(".btn-signout").click(function() {
      didoauth.signOut();
    });

    self.validatorForm = $("#formProfile");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    initPictureUpload();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/users/" + didoauth.user.id,
        dataType: "json"
    }).done(function(data) {
      if (data == null) {
        self.notificationArea.error({
          message: "User profile was not found."
        });
      } else {
        $("#inputFirst").val(data.firstName);
        $("#inputLast").val(data.lastName);
        $(".aa-profile-picture").empty();
        if (data.profilePicture) {
          $("img").width("100%").attr("src", data.profilePicture).appendTo(".aa-profile-picture");
        }
        initButtonHandlers();
      }
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  }

  var initPictureUpload = function() {
    self.pictureUpload = $("#inputPicture").fileinput({
      browseLabel: 'Change Picture',
      browseIcon: '',
      showUpload: false,
      showRemove: false,
      showPreview: false,
      uploadUrl: config.apiRoot + "/users/picture",
      uploadAsync: false,
      layoutTemplates: {
        progress: '', //hide progress
      },
      uploadExtraData: function () {
        return { userId: didoauth.user.id };
      },
      maxFileCount: 1,
      allowedFileExtensions: ["jpg", "jpeg", "bmp", "gif", "png"]
    });
  }

  var initButtonHandlers = function() {
    $("#btnProfileUpdate").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var profileData = {
            firstName: $("#inputFirst").val(),
            lastName: $("#inputLast").val()
          };

          $.post({
              url: config.apiRoot + "/users/profile",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(profileData)
          }).done(function(data) {
            if (self.pictureUpload.fileinput("getFilesCount") > 0)
              self.pictureUpload.fileinput("upload");

            self.notificationArea.success({
              message: "Profile is being updated, you should be able to see updated changes soon."
            });
          }).fail(function(result) {
            self.notificationArea.error();
          }).always(function() {
            globals.loading($('body'), false);
          });
        }
      });
    });
  };

  return self;
});
