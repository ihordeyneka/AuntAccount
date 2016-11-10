define("post", function(){
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
    require(["https://maps.googleapis.com/maps/api/js?key=AIzaSyANyEK-JVHb9DFlEN1igkGQUD0cT6deZkU&callback=initMap"]);
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
