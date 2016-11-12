define(["communication/client"], function(client){
  var self = this;
  self.map = null;
  self.marker = null;
  self.userPosition = { lat: 40.75773, lng: -73.985708  }; //New York Times Square by default

  self.updateUserPosition = function (position) { //executed when user geolocation data is processed
    self.userPosition.lat = position.coords.latitude;
    self.userPosition.lng = position.coords.longitude;
    if(self.map && self.marker) {
      $("#inputLocation").val("");
      updatePosition();
    }
  }

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(self.updateUserPosition);
  }

  self.init = function(){
    //first load map
    require(["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&callback=initMap&libraries=places"]);
    //load slider
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
        self.processPlace(item.name);
      }
    });

    typeahead.keypress(function(e) {
      if (e.which == 13) //key is Enter
        self.processPlace(typeahead.val());
    });

    $(".btn-location-search").click(function() {
      self.processPlace(typeahead.val());
    });
  }

  self.processPlace = function(place) {
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

  window.initMap = function() {
    self.map = new google.maps.Map($(".aa-post-map").get(0), {
      zoom: 10
    });
    self.marker = new google.maps.Marker({
      map: map
    });
    updatePosition();
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
