define(["../core/globals", "../core/config", "tagsinput", "typeahead", "fileinput"],
function(globals, config, tagsinputControl, typeaheadControl, fileinputControl) {
  var self = {};

  self.typeahead = null;
  self.selectedLocationId = null;
  self.attachmentUpload = null;
  self.savedSellerId = null;

  self.init = function() {
    initAutocomplete();
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

  var initAutocomplete = function() {
    self.primaryLocation = null;
    require(["googleapi"], function() {
      self.initPrimaryLocationTypeahead();
    });
  }

  self.initPrimaryLocationTypeahead = function() {
    var input = document.getElementById('inputPrimaryLocation');
    var autocomplete = new google.maps.places.Autocomplete(input);

    google.maps.event.addListener(autocomplete, 'place_changed', function(e) {
      var googleLocation = autocomplete.getPlace();
      //convert google location to our format
      self.location = {};
      self.location.name = googleLocation.name;
      self.location.latitude = googleLocation.geometry.location.lat();
      self.location.longitude = googleLocation.geometry.location.lng();
      for (var i = 0; i < googleLocation.address_components.length; i++) {
        var addressComponent = googleLocation.address_components[i];
        for (var j = 0; j < addressComponent.types.length; j++) {
          switch (addressComponent.types[j]) {
            case "country":
            self.location.country = addressComponent.long_name;
            break;
            case "administrative_area_level_1":
            self.location.region1 = addressComponent.long_name;
            break;
            case "administrative_area_level_2":
            self.location.region2 = addressComponent.long_name;
            break;
            case "locality":
            self.location.city = addressComponent.long_name;
            break;
            case "neighborhood":
            self.location.neighborhood = addressComponent.long_name;
            break;
            case "route":
            self.location.route = addressComponent.long_name;
            break;
            case "street_number":
            self.location.street_number = addressComponent.long_name;
            break;
          }
        }
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

  self.addButtonHandlers = function() {
    $("#btnRegisterSeller").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var sellerData = {
            name: $("#inputName").val(),
            phone: $("#inputPhone").val(),
            website: $("#inputWebsite").val(),
            keywords: $("#inputTags").val(),
            location: self.location,
            userId: $.didoauth.user.id
          };

          $.post({
            url: config.apiRoot + "/suppliers",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(sellerData)
          }).done(function(data) {
            self.savedSellerId = data.id;
            if (self.attachmentUpload.fileinput("getFilesCount") > 0)
            self.attachmentUpload.fileinput("upload");
            self.notificationArea.success({
              message: "Seller is registered."
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
