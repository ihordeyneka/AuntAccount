define(["core/globals", "core/config", "typeahead", "fileinput", "jqueryRaty", "components/google_autocomplete", "components/tags_input"],
function(globals, config, typeaheadControl, fileinputControl, jqueryRaty, googleAutocompleteControl, tagsInputControl) {
  var self = {};

  self.locationTypeahead = null;
  self.pictureUpload = null;
  self.savedSellerId = null;

  self.init = function(sellerId) {
    self.sellerId = sellerId || 0;
    self.locationTypeahead = new googleAutocompleteControl($("#inputPrimaryLocation"));
    self.tagsInput = new tagsInputControl($("#inputTags"));

    initRating();
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
        self.sellerRating.raty('score', data.rate);
        self.locationTypeahead.setLocation(data.location);
        self.tagsInput.setTags(data.tagList);
        if (data.picture) {
          $("<img>").width("100%").attr("src", data.picture).appendTo(".aa-seller-picture");
        }
      }
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  }

  var initRating = function(rating) {
    var DEF_RATING = 5;
    self.sellerRating = $(".aa-seller-rating");
    self.sellerRating.raty({
      score: DEF_RATING,
      readOnly: true,
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
    $("#btnSaveSeller")
    .click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var sellerData = {
            name: $("#inputName").val(),
            phone: $("#inputPhone").val(),
            website: $("#inputWebsite").val(),
            tags: self.tagsInput.getTags(),
            location: self.locationTypeahead.getLocation(),
            userId: $.didoauth.user.id
          };

          $.post({
            url: config.apiRoot + "/sellers",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(sellerData)
          }).done(function(data) {
            self.savedSellerId = data.id;
            if (self.pictureUpload.fileinput("getFilesCount") > 0)
            self.pictureUpload.fileinput("upload");
            self.notificationArea.success({
              message: "Seller successfully registered."
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
