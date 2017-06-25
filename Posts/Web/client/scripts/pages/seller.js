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
        $(".aa-seller-name-header").text(data.name);
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
        initRating(data, viewOnly);
      }
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  }

  var initRating = function(data, enabled) {
    var rating = enabled && data.userRate ? data.userRate : data.averageRate;
    var DEF_RATING = 5;
    self.sellerRating = $(".aa-seller-rating");
    self.sellerRating.raty({
      score: rating || DEF_RATING,
      readOnly: !enabled,
      half: true,
      size: 24,
      hints: [null, null, null, null, null],
      click: function(value, e) {
        placeReview();
      }
    });

    if (!enabled) //for readonly view seller might go to reviews page by clicking anywhere in the stars plugin
      self.sellerRating.click(function() { switchMode(true); });
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

    $("#btnGoToReviews").click(function() { switchMode(true); });
    $("#btnBackFromReviews").click(function() { switchMode(false); });
  }

  var switchToReadOnly = function() {
    $("#inputName").attr("disabled", "disabled");
    $("#inputPhone").attr("disabled", "disabled");
    $("#inputWebsite").attr("disabled", "disabled");
    self.locationTypeahead.setReadonly();
    self.tagsInput.setReadonly();
    $(".aa-seller-picture-container").hide();
    $(".aa-seller-buttons").hide();
    $(".aa-new-review-container").show();
  };

  var goBack = function() {
    window.location = "#sellers";
  };

  var goBackDelayed = function() {
    _.delay(goBack, 2000);
  };

  var switchMode = function(showReviews) {
    if (showReviews) {
      $(".aa-seller-info-container").hide();
      $("#btnGoToReviews").hide();
      $(".aa-seller-reviews-container").show();
      $("#btnBackFromReviews").show();
      initReviews();
    } else {
      $(".aa-seller-info-container").show();
      $("#btnGoToReviews").show();
      $(".aa-seller-reviews-container").hide();
      $("#btnBackFromReviews").hide();
    }
  };

  var initReviews = function() {
    globals.loading($('body'), true);
    $.get({
        url: config.apiRoot + "/sellers/" + self.sellerId + "/reviews",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-reviews-template-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3 class='center'>No reviews added for this seller yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var review = data[i];
          element.append($.templates("#templateReview").render({
            time: globals.formatDate(review.time),
            author: review.author != null ? review.author.firstName + " " + review.author.lastName : "Anonymous",
            content: review.content
          }));
        }
      }
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

    $(".aa-place-review-button").click(function() { placeReview(true) });

    $(".aa-new-review-input")
      .focus()
      .keypress(function(event) {
        if(event.keyCode == 13) { //Enter Key Press event handler
          placeReview(true);
        }
      });
  };

  var placeReview = function(textRequired) {
    var review = $(".aa-new-review-input").val();
    if (textRequired && !review)
      return;

    globals.loading($('body'), true);
    $.post({
        url: config.apiRoot + "/reviews",
        dataType: "json",
        data: {
          sellerId: self.sellerId,
          rate: self.sellerRating.raty("score"),
          review: review
        }
    }).done(function(data) {
      switchMode(true);
      $(".aa-new-review-input").val("");
      initReviews();
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  };

  return self;
});
