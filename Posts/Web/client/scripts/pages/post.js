define(["../core/globals", "../core/config", "tagsinput", "typeahead", "fileinput", "slider"],
  function(globals, config, tagsinputControl, typeaheadControl, fileinputControl, sliderControl) {
  var self = {};
  var DEF_POSITION = { lat: 40.75773, lng: -73.985708  }; //New York Times Square by default
  var MIN_ZOOM_FOR_PLACE = 15;

  self.googleApiLoaded = false;
  self.map = null;
  self.marker = null;
  self.circle = null;
  self.userPosition = null;
  self.radiusSlider = null;
  self.typeahead = null;
  self.selectedLocationId = null;
  self.attachmentUpload = null;
  self.lastPostId = null;

  self.updateUserPosition = function (position) { //executed when user geolocation data is processed
    self.userPosition = { lat: position.coords.latitude, lng: position.coords.longitude };
    if(self.map && self.marker) {
      $("#inputLocation").val("");
      self.updatePosition();
    }
  }

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(self.updateUserPosition);
  }

  self.init = function(){
    self.initSearchTagsInput();
    self.initLocationTypeahead();
    self.initRadiusSlider();
    self.initAttachmentUpload();
    self.addButtonHandlers();
    if (!self.googleApiLoaded) {
      require(["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&callback=initMap&libraries=places"]);
    }
    else {
      initMap();
    }
    self.validatorForm = $("#formPost");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();
  }

  window.initMap = function() {
    self.googleApiLoaded = true;
    self.map = new google.maps.Map($(".aa-post-map").get(0), {
      zoom: MIN_ZOOM_FOR_PLACE
    });
    self.map.addListener('zoom_changed', function() {
      var zoom = self.map.getZoom();
      if (zoom < MIN_ZOOM_FOR_PLACE) {
        self.radiusSlider.setValue(0);
        self.circle.setRadius(0);
        self.radiusSlider.disable();
      } else {
        self.radiusSlider.enable();
      }
      self.radiusSlider.relayout();
    });
    self.marker = new google.maps.Marker({
      map: self.map
    });
    self.circle = new google.maps.Circle({
        radius: 1000, // 1 mile
        strokeColor: '#0000FF',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#0000FF',
        fillOpacity: 0.35,
    });
    self.circle.bindTo('center', self.marker, 'position');
    self.updatePosition();
  }

  self.initSearchTagsInput = function() {
    $("#inputKeywords").tagsinput({
      typeahead: {
        afterSelect: function(val) {
          this.$element.val(""); //clear input when tag is added
        },
        source: function(query) {
          var result = [];

          $.ajax({
              url: config.apiRoot + "/tags/" + query,
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

    var tagsinput = $("#inputKeywords").data("tagsinput");

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

    $("#inputKeywords").on("itemAdded", function() {
      updateTagsPosition();
      tagsinput.$input.attr('placeholder', ''); //remove placeholder if at least one tag is added
    });
    $("#inputKeywords").on("itemRemoved", function() {
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

  self.initLocationTypeahead = function() {
    var processPlace = function(place) {
      if (!place)
        return;

      var request = {
        query: place
      };

      var service = new google.maps.places.PlacesService(self.map);
      service.textSearch(request, function(results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
          var data = results[0].geometry;
          self.map.fitBounds(data.viewport);
          var location = { lat: data.location.lat(), lng: data.location.lng() };
          self.marker.setPosition(location);
        }
      });
    }

    self.typeahead = $("#inputLocation").typeahead({
      items: "all",
      minLength: 3,
      delay: 300,
      fitToElement: true,
      source: function(query, process) {
        $.ajax({
            url: config.apiRoot + "/locations/" + query,
            dataType: "json"
        }).done(function(data) {
            process(data);
        }).fail(function(result) {
          self.notificationArea.error();
        });
      },
      afterSelect: function(item) {
        self.selectedLocationId = item.id;
        if (item.location) {
          self.updatePosition(item.location);
        }
        processPlace(item.name);
      }
    });

    self.typeahead.keypress(function(e) {
      if (e.which == 13) //key is Enter
        processPlace(self.typeahead.val());
    });

    $(".btn-location-search").click(function() {
      processPlace(self.typeahead.val());
    });

    $(".btn-location-nearby").popover({
      placement: "bottom",
      container: ".aa-post-location-container",
      trigger: "manual",
      content: "Sorry, you've denied access to your geolocation."
    })
    .data("bs.popover")
    .tip()
    .addClass("aa-nearby-popover");

    $(".btn-location-nearby").click(function(e) {
      if (self.userPosition) {
        $("#inputLocation").val("");
        self.updatePosition(self.userPosition);
        self.map.setZoom(MIN_ZOOM_FOR_PLACE);
      } else {
        $(this).popover("show");
        e.stopPropagation();
        $("body").one("click", function() {
          $(".btn-location-nearby").popover("hide");
        });
      }
    });
  }

  self.initRadiusSlider = function() {
    self.radiusSlider = $("#inputRadius").slider({
      handle: 'square',
      value: 0,
      min: 0,
      max: 5000,
      step: 500,
      tooltip: 'always',
      tooltip_position: 'bottom',
      formatter: function(value) {
        return self.radiusSlider == null || self.radiusSlider.isEnabled() ?
          value + 'm' :
          'Disabled';
      }
    }).data('slider');

    self.radiusSlider.on('change', function(e) {
      var radius = e.newValue;
      self.circle.setRadius(radius);
      self.circle.setMap(radius > 0 ? self.map : null); //toggle visibility
    });
  }

  self.initAttachmentUpload = function() {
    self.attachmentUpload = $("#inputAttachment").fileinput({
      browseLabel: 'Browse',
      browseIcon: '<i class="fa fa-search"></i>',
      showUpload: false,
      showRemove: false,
      showPreview: false,
      uploadUrl: config.apiRoot + "/posts/upload",
      uploadAsync: false,
      layoutTemplates: {
        progress: '', //hide progress
      },
      uploadExtraData: function () {
        return { postId: self.lastPostId };
      },
      maxFileCount: 1,
      allowedFileExtensions: ["jpg", "jpeg", "bmp", "gif", "png"]
    });
  }

  self.updatePosition = function(location) {
    if (!location) {
      location = self.userPosition || DEF_POSITION;
    }

    var latLng = new google.maps.LatLng(location.lat, location.lng);

    self.marker.setPosition(latLng);
    self.map.panTo(latLng);
  }

  self.addButtonHandlers = function() {
    $("#btnPost").click(function() {
      globals.validate({
        notificationArea: self.notificationArea,
        validatorForm: self.validatorForm,
        success: function() {
          globals.loading($('body'), true);
          var postData = {
            postTags: $("#inputKeywords").val(),
            description: $("#inputPost").val(),
            location: {
                id: self.selectedLocationId,
                latitude: self.marker.position.lat(),
                longitude: self.marker.position.lng(),
                radius: self.radiusSlider.getValue()
            },
            userId: $.didoauth.user.id
           // place: $("#inputLocation").val(),

          };

          $.post({
              url: config.apiRoot + "/posts",
              dataType: "json",
              contentType: "application/json",
              data: JSON.stringify(postData)
          }).done(function(data) {
            self.lastPostId = data.id;
            if (self.attachmentUpload.fileinput("getFilesCount") > 0)
              self.attachmentUpload.fileinput("upload");

            self.notificationArea.success({
              message: "Your message has been posted, we've notified our subscribers."
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
