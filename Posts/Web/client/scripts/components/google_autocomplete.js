define([], function() {
  return function(element) {
    var self = {};

    self.element = element;
    self.location = null;

    require(["googleapi"], function() { self.initAutocomplete(); });

    self.initAutocomplete = function() {
      var input = self.element.get(0);
      var autocomplete = new google.maps.places.Autocomplete(input);

      google.maps.event.addListener(autocomplete, 'place_changed', function (e) {
        var googleLocation = autocomplete.getPlace();
        //convert google location to our format
        self.location = {};
        self.location.name = googleLocation.name;

        if (googleLocation.geometry) {
          self.location.latitude = googleLocation.geometry.location.lat();
          self.location.longitude = googleLocation.geometry.location.lng();
        }

        if (googleLocation.address_components) {
          for (var i=0; i<googleLocation.address_components.length; i++) {
            var addressComponent = googleLocation.address_components[i];
            for (var j=0; j<addressComponent.types.length; j++) {
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
        }        

        self.element.trigger("place_changed");
      });
    }

    return self;
  }
});
