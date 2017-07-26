define([], function() {
  return function(element) {
    var self = {};
    var currentLocation = null;

    self.element = element;
    self.getLocationString = function() {
      return self.element.val();
    };
    self.getLocation = function() {
      return currentLocation;
    };
    self.setLocation = function(location) {
      currentLocation = location;
      var name = location != null ? location.name : "";
      self.element.val(name);
    }

    require(["googleapi"], function() { self.initAutocomplete(); });

    self.initAutocomplete = function() {
      var input = self.element.get(0);
      var autocomplete = new google.maps.places.Autocomplete(input);

      google.maps.event.addListener(autocomplete, 'place_changed', function (e) {
        var googleLocation = autocomplete.getPlace();
        //convert google location to our format
        currentLocation = {};
        currentLocation.name = self.element.val();
        currentLocation.place = googleLocation.name;
        currentLocation.placeId = googleLocation.place_id;
        //TODO: add another property to currentLocation where we would store user entered name

        if (googleLocation.geometry) {
          currentLocation.latitude = googleLocation.geometry.location.lat();
          currentLocation.longitude = googleLocation.geometry.location.lng();
        }

        if (googleLocation.address_components) {
          for (var i=0; i<googleLocation.address_components.length; i++) {
            var addressComponent = googleLocation.address_components[i];
            for (var j=0; j<addressComponent.types.length; j++) {
              switch (addressComponent.types[j]) {
                case "country":
                  currentLocation.country = addressComponent.long_name;
                  break;
                case "administrative_area_level_1":
                  currentLocation.region1 = addressComponent.long_name;
                  break;
                case "administrative_area_level_2":
                  currentLocation.region2 = addressComponent.long_name;
                  break;
                case "locality":
                  currentLocation.city = addressComponent.long_name;
                  break;
                case "neighborhood":
                  currentLocation.neighborhood = addressComponent.long_name;
                  break;
                case "route":
                  currentLocation.route = addressComponent.long_name;
                  break;
                case "street_number":
                  currentLocation.street_number = addressComponent.long_name;
                  break;
              }
            }
          }
        }

        self.element.trigger("place_changed");
      });
    }

    self.setReadonly = function() {
      self.element.attr("disabled", "disabled");
    };

    self.element.keypress(function(e) {
      if (e.which == 13) //key is Enter
        self.element.trigger("place_changed");
    });

    return self;
  }
});
