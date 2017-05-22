define(["core/globals", "core/config", "tagsinput", "typeahead", "fileinput", "components/google_autocomplete"],
function(globals, config, tagsinputControl, typeaheadControl, fileinputControl, google_autocomplete) {
  var self = {};

  self.locationTypeahead = null;
  self.pictureUpload = null;
  self.savedSellerId = null;

  self.init = function(sellerId) {
    self.sellerId = sellerId || 0;
    self.locationTypeahead = new google_autocomplete($("#inputPrimaryLocation"));

    initPictureUpload();
    self.initSearchTagsInput();
    self.addButtonHandlers();

    self.validatorForm = $("#formSellerRegister");
    self.validatorForm.validator({
      focus: false
    });
    self.notificationArea = $(".aa-notification-area").notificationArea();
  }

  self.initSearchTagsInput = function() {
    $("#inputTags").tagsinput({
      typeahead: {
        afterSelect: function(val) {
          this.$element.val(""); //clear input when tag is added
        },
        source: function(query) {
          var result = [];

          $.ajax({
            url: config.apiRoot + "/tag/" + query,
            dataType: "json",
            async: false
          }).done(function(data) {
            result = data;
          }).fail(function(result) {
            self.notificationArea.error();
          });

          return result;
        }
      }
    });

    var tagsinput = $("#inputTags").data("tagsinput");

    //normally shifting of controls should be possible to reuse in other instances of tags input
    //this code should be moved to a separate place to override the behavior of tags input control
    tagsinput.currentShift = 0;
    var updateTagsPosition = function() {
      var shiftStepPixels = 100;
      var inputRight = tagsinput.$input.position().left + tagsinput.$input.width();
      var containerRight = tagsinput.$container.position().left + tagsinput.$container.width();
      if (inputRight > containerRight) {
        tagsinput.currentShift -= shiftStepPixels;
      } else if (tagsinput.currentShift < 0) {
        var restoreStepsCount = (containerRight - inputRight) / shiftStepPixels;
        tagsinput.currentShift += restoreStepsCount * shiftStepPixels;
      }
      if (tagsinput.currentShift > 0)
      tagsinput.currentShift = 0;
      //update left css property for input and tags within the tagsinput
      tagsinput.$container.children('span.tag, input')
      .css("left", tagsinput.currentShift.toString() + "px");
    }

    $("#inputTags").on("itemAdded", function() {
      updateTagsPosition();
      tagsinput.$input.attr('placeholder', ''); //remove placeholder if at least one tag is added
    });
    $("#inputTags").on("itemRemoved", function() {
      updateTagsPosition();
      if (tagsinput.itemsArray.length === 0)
      tagsinput.$input.attr('placeholder', tagsinput.placeholderText); //restore placeholder if there are no tags again
    });
    tagsinput.$input.keypress(function() {
      updateTagsPosition();
    });
    tagsinput.$input.keydown(function(e) {
      if (e.which === 37 && tagsinput.$input.val() === '') { //LEFT ARROW and input is empty
        e.stopPropagation(); //avoid moving input between tags, see keydown event handler in bootstrap-tagsinput.js
      }
    });
  }

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

  self.addButtonHandlers = function() {
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
            tags: $("#inputTags").val(),
            location: self.locationTypeahead.location,
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
