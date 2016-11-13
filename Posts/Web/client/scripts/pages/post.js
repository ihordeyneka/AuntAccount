define(["communication/client"], function(client){
  var self = this;
  self.map = null;
  self.marker = null;
  self.circle = null;
  self.userPosition = { lat: 40.75773, lng: -73.985708  }; //New York Times Square by default

  self.updateUserPosition = function (position) { //executed when user geolocation data is processed
    self.userPosition.lat = position.coords.latitude;
    self.userPosition.lng = position.coords.longitude;
    if(self.map && self.marker) {
      $("#inputLocation").val("");
      self.updatePosition();
    }
  }

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(self.updateUserPosition);
  }

  self.init = function(){
    require(["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&callback=initMap&libraries=places"]);
    self.initLocationTypeahead();
    self.initRadiusSlider();
  }

  window.initMap = function() {
    self.map = new google.maps.Map($(".aa-post-map").get(0), {
      zoom: 10
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
    updatePosition();
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

    var typeahead = $("#inputLocation").typeahead({
      items: "all",
      minLength: 3,
      delay: 300,
      fitToElement: true,
      source: function(query, process) {
        var result = client.getLocations(query);
        return process(result);
      },
      afterSelect: function(item) {
        if (item.location) {
          self.updatePosition(item.location);
        }
        processPlace(item.name);
      }
    });

    typeahead.keypress(function(e) {
      if (e.which == 13) //key is Enter
        processPlace(typeahead.val());
    });

    $(".btn-location-search").click(function() {
      processPlace(typeahead.val());
    });
  }

  self.initRadiusSlider = function() {
    $("#inputRadius").slider({
      handle: 'square',
      value: 0,
      min: 0,
      max: 5000,
      step: 500,
      tooltip: 'always',
      tooltip_position: 'bottom',
      formatter: function(value) {
        return value + 'm';
      }
    });

    $("#inputRadius").slider().on("change", function(e) {
      var radius = e.value.newValue;
      self.circle.setRadius(radius);
      self.circle.setMap(radius > 0 ? self.map : null); //toggle visibility
    });
  }

  self.updatePosition = function(location) {
    if (!location) {
      location = userPosition;
    }

    var latLng = new google.maps.LatLng(location.lat, location.lng);

    self.marker.setPosition(latLng);
    self.map.panTo(latLng);
  }

  return self;
});
