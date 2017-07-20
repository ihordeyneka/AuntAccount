define(["core/globals", "core/config", "underscore", "typeahead", "fileinput", "maskedinput", "jqueryRaty", "components/google_autocomplete", "components/tags_input"],
function(globals, config, _, typeaheadControl, fileinputControl, maskedinputControl, jqueryRaty, googleAutocompleteControl, tagsInputControl) {
  var self = {};

  self.locationTypeahead = null;
  self.pictureUpload = null;
  self.savedSellerId = null;
  self.sellerId = null;
  self.seller = null;

  self.init = function(sellerId) {
    self.sellerId = sellerId || 0;
    self.locationTypeahead = new googleAutocompleteControl($("#inputPrimaryLocation"));
    self.tagsInput = new tagsInputControl($("#inputTags"));
    $("#inputPhone").mask("(999) 999-99-99");

    initPictureUpload();
    addButtonHandlers();

    self.validatorForm = $("#formSellerRegister");
    self.validatorForm.validator({
      focus: false
    });
    self.notificationArea = $(".aa-notification-area").notificationArea();

    if (self.sellerId) {
      fetchCurrentSeller();
    }
  }

  var fetchCurrentSeller = function() {
    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/sellers/" + self.sellerId,
        dataType: "json"
    }).done(function(data) {
      if (data == null) {
        self.notificationArea.error({
          message: "Seller was not found."
        });
      } else {
        $("#inputName").val(data.name);
        $("#inputPhone").val(data.phone);
        $("#inputWebsite").val(data.website);
        self.locationTypeahead.setLocation(data.location);
        self.tagsInput.setTags(data.tagList);
        if (data.photo) {
          $("<img>").width("100%").attr("src", data.photo).appendTo(".aa-seller-picture");
        }

        var viewOnly = data.userId !== $.didoauth.user.id;
        if (viewOnly) {
          switchToReadOnly();
        }
        initRating(data.rate, !viewOnly);
      }
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  }

  var initRating = function(rating, readonly) {
    var DEF_RATING = 5;
    self.sellerRating = $(".aa-seller-rating");
    self.sellerRating.raty({
      score: rating || DEF_RATING,
      readOnly: readonly,
      half: true,
      size: 24,
      hints: [null, null, null, null, null]
    });
  };

  var initPictureUpload = function() {
    self.pictureUpload = $("#inputPicture").fileinput({
      browseLabel: 'Change Picture',
      browseIcon: '<i class="fa fa-user"></i>',
      showUpload: false,
      showRemove: false,
      showPreview: false,
      uploadUrl: config.apiRoot + "/sellers/picture",
      uploadAsync: false,
      layoutTemplates: {
        progress: '', //hide progress
      },
      uploadExtraData: function () {
        return { sellerId: self.savedSellerId };
      },
      maxFileCount: 1,
      allowedFileExtensions: ["jpg", "jpeg", "bmp", "gif", "png"]
    });
  }

  var addButtonHandlers = function() {
    $("#btnSaveSeller").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var sellerData = {
            id: self.sellerId,
            name: $("#inputName").val(),
            phone: $("#inputPhone").val(),
            website: $("#inputWebsite").val(),
            tags: self.tagsInput.getTags(),
            location: self.locationTypeahead.getLocation(),
            userId: $.didoauth.user.id
          };

          $.post({
            url: config.apiRoot + "/sellers/profile",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(sellerData)
          }).done(function(data) {
            self.savedSellerId = data.id;
            self.notificationArea.success({
              message: "Seller successfully registered."
            });
            if (self.pictureUpload.fileinput("getFilesCount") > 0) {
              self.pictureUpload
                .on('filebatchuploadsuccess', goBackDelayed)
                .fileinput("upload");
            } else {
              goBackDelayed();
            }
          }).fail(function(result) {
            self.notificationArea.error();
          }).always(function() {
            globals.loading($('body'), false);
          });
        }
      });
    });

    $("#btnBackSeller").click(goBack);
  }

  var switchToReadOnly = function() {
    $("#inputName").attr("disabled", "disabled");
    $("#inputPhone").attr("disabled", "disabled");
    $("#inputWebsite").attr("disabled", "disabled");
    self.locationTypeahead.setReadonly();
    self.tagsInput.setReadonly();
    $(".aa-seller-picture-container").hide();
    $(".aa-seller-buttons").hide();
  };

  var goBack = function() {
    window.location = "#sellers";
  };

  var goBackDelayed = function() {
    _.delay(goBack, 2000);
  };

  return self;
});
