define(["core/globals", "core/didoauth", "core/config", "fileinput", "components/google_autocomplete"],
  function(globals, didoauth, config, fileinput, googleAutocompleteControl) {
  var self = {};
  self.profileId = null;

  self.init = function(profileId) {
    self.profileId = profileId || didoauth.user.id;

    $(".btn-signout").click(function() {
      didoauth.signOut();
    });

    self.validatorForm = $("#formProfile");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    initPictureUpload();
    self.locationTypeahead = new googleAutocompleteControl($("#inputPrimaryLocation"));

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/users/profile/" + self.profileId,
        dataType: "json"
    }).done(function(data) {
      if (data == null) {
        self.notificationArea.error({
          message: "User profile was not found."
        });
      } else {
        self.profileData = data;
        $("#inputFirst").val(data.firstName);
        $("#inputLast").val(data.lastName);
        $(".aa-profile-picture").empty();
        self.locationTypeahead.setLocation(data.location);
        if (data.photo) {
          $("<img>").attr("src", data.photo).appendTo(".aa-profile-picture");
        }
        initButtonHandlers();
        initSellersButtons(data);
        var viewOnly = self.profileId !== didoauth.user.id;
        if (viewOnly) {
          switchToReadOnly();
        }
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
      browseIcon: '<i class="fa fa-user"></i>',
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
          self.profileData.firstName = $("#inputFirst").val();
          self.profileData.lastName = $("#inputLast").val();
          self.profileData.location = self.locationTypeahead.getLocation();

          $.post({
              url: config.apiRoot + "/users/profile",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(self.profileData)
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

  var initSellersButtons = function(user) {
    var hasSellers = user.hasSellers;
    if (hasSellers) {
      $("#btnRegisterSeller").hide();
      $("#btnViewSellers").show();
    } else {
      $("#btnRegisterSeller").show();
      $("#btnViewSellers").hide();
    }
  }

  var switchToReadOnly = function() {
    $("#inputFirst").attr("disabled", "disabled");
    $("#inputLast").attr("disabled", "disabled");
    self.locationTypeahead.setReadonly();
    $(".aa-profile-picture-container").hide();
    $(".aa-profile-buttons").hide();
  };

  return self;
});
