define(function() {
  var self = this;

  self.attachmentUploadUrl = "/test/upload";

  self.getTags = function(parameters, callback) {
    var result = [
      "Shoes",
      "Boots",
      "Socks",
      "Trousers",
      "Jeans",
      "Shorts",
      "Pants",
      "Belt",
      "Skirt",
      "Shirt",
      "T-Shirt",
      "Sweater",
      "Polo",
      "Hoodie",
      "Jacket",
      "Watch",
      "Glasses",
      "Scarf",
      "Hat",
      "Cap"
    ];
    callback({
      success: true,
      data: result
    });
  }

  self.getLocations = function(parameters, callback) {
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
    callback({
      success: true,
      data: result
    });
  }

  self.savePost = function(parameters, callback) {
    console.log(post);
    callback({
      success: true,
      data: { id: 0 }
    });
  }

  self.signUp = function(parameters, callback) {
    console.log(model);
    callback({
      success: true
    });
  }

  self.getMyRecentPosts = function(parameters, callback) {
    var result = [
      { postId: 1, time: "03-02-2017", conversations: 5, newMessages: true, tags: ["Blue Jeans", "New"], description: "I want to buy blue jeans, size: 32/30" },
      { postId: 2, time: "29-01-2017", conversations: 0, newMessages: false, tags: ["Red Ferrari"], description: "Rrrrrrrrrrrrr" },
      { postId: 3, time: "15-12-2016", conversations: 1, newMessages: false, tags: ["Ball Pen"], description: "I just need a simple pen<br/>Blue or black<br/>Doesn't matter..." },
    ];
    callback({
      success: true,
      data: result
    });
  }

  return self;
});
