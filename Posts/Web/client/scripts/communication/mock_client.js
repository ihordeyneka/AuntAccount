define(function() {
  var self = this;

  self.getAttachmentUploadUrl = function () {
    return "http://localhost:7000/test/upload";
  };

  self.getLocations = function(query) {
    //type: 1-country, 2-province, 3-city, 4-place
    var result = [
      {id: 1, type: 3, name: "Lviv"},
      {id: 2, type: 3, name: "New York"},
      {id: 3, type: 4, name: "KingCross Leopolis Lviv", location: { lat: 49.773807, lng: 24.011436} },
      {id: 4, type: 3, name: "Rome"},
      {id: 5, type: 3, name: "Paris"},
      {id: 6, type: 1, name: "Ukraine"},
      {id: 7, type: 1, name: "New Zeland"},
      {id: 8, type: 1, name: "Portugal"},
      {id: 9, type: 1, name: "Romania"},
      {id: 10, type: 4, name: "Victoria Gardens", location: { lat: 49.807049, lng: 23.978466} }
    ];

    return result;
  }

  self.savePost = function(post, callback) {
    console.log(post);
    callback({
      success: true,
      data: { id: 0 }
    });
  }

  return self;
});
