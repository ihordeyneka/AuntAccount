define(["../../views/post.html", "../core/globals", "../core/config", "typeahead", "fileinput", "slider", "../components/google_autocomplete", "../components/tags_input"],
  function(source, globals, config, typeaheadControl, fileinputControl, sliderControl, googleAutocompleteControl, tagsInputControl) {
  var self = {};

  self.source = source;

  var DEF_POSITION = { lat: 40.75773, lng: -73.985708  }; //New York Times Square by default
  var MIN_ZOOM_FOR_PLACE = 15;
  var MIN_RADIUS = 100;

  self.map = null;
  self.marker = null;
  self.circle = null;
  self.userPosition = null;
  self.radiusSlider = null;
  self.locationTypeahead = null;
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
    self.tagsInput = new tagsInputControl($("#inputKeywords"));
    self.initLocationTypeahead();
    self.initRadiusSlider();
    self.initAttachmentUpload();
    self.addButtonHandlers();

    import("../components/google_api" /* webpackChunkName: "chunk-googleapi" */).then(self.initMap);

    self.validatorForm = $("#formPost");
    self.validatorForm.validator({ focus: false });
    self.notificationArea = $(".aa-notification-area").notificationArea();
  }

  self.initMap = function() {
    self.map = new google.maps.Map($(".aa-post-map").get(0), {
      zoom: MIN_ZOOM_FOR_PLACE
    });
    self.marker = new google.maps.Marker({
      map: self.map
    });
    self.circle = new google.maps.Circle({
        radius: MIN_RADIUS,
        map: self.map,
        strokeColor: '#888877',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#888877',
        fillOpacity: 0.35,
    });
    self.circle.bindTo('center', self.marker, 'position');

    self.updatePosition();
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
          self.updateSlider();
        }
      });
    }

    self.locationTypeahead = new googleAutocompleteControl($("#inputLocation"));
    self.locationTypeahead.element.bind("place_changed", function() {
      processPlace(self.locationTypeahead.getLocationString());
    })

    $(".btn-location-search").click(function() {
      processPlace(self.locationTypeahead.getLocationString());
    });

    $(".btn-location-nearby").popover({
      placement: "bottom",
      container: ".aa-post-location-container",
      trigger: "manual",
      content: $.i18n('GeolocationAccessDenied')
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

    $(".btn-location-globe").click(function(e) {
      $("#inputLocation").val("");
      self.updatePosition(self.userPosition || DEF_POSITION);
      self.map.setZoom(1);
    });
  }

  self.initRadiusSlider = function() {
    self.radiusSlider = $("#inputRadius").slider({
      handle: 'square',
      value: 0,
      min: MIN_RADIUS,
      max: 5000,
      step: 100,
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
      self.map.fitBounds(self.circle.getBounds());
    });

    self.updateSlider = function() {
      var zoom = self.map.getZoom();
      if (zoom < MIN_ZOOM_FOR_PLACE) {
        self.radiusSlider.setValue(0);
        self.circle.setRadius(0);
        self.radiusSlider.disable();
      } else {
        self.circle.setRadius(self.radiusSlider.getValue() || MIN_RADIUS);
        self.radiusSlider.enable();
      }
      self.radiusSlider.relayout();
    };
  }

  self.initAttachmentUpload = function () {
    self.attachmentUpload = $("#inputAttachment").fileinput({
      browseLabel: $.i18n('Browse'),
      browseIcon: '<i class="fa fa-search"></i>',
      msgPlaceholder: $.i18n('SelectFile'),
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

          var location = $.extend(true, {}, self.locationTypeahead.getLocation());
          location.point = {
            lat: self.marker.position.lat(),
            lon: self.marker.position.lng()
          };
          location.radius = self.radiusSlider.isEnabled() ? self.radiusSlider.getValue() : 0;

          var postData = {
            postTags: self.tagsInput.getTags(),
            description: $("#inputPost").val(),
            location: location,
            userId: $.didoauth.user.id
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
              message: $.i18n('MessagePosted')
            });
          }).fail(function(result) {
            self.notificationArea.error();
          }).always(function() {
            globals.loading($('body'), false);
            //TODO: if user was anonymous we would reset the user here
          });
        }
      });
    });
  }

  return self;
});
