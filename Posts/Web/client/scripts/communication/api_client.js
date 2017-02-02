define(["../core/config"], function(config) {
  var self = this;

  self.getAttachmentUploadUrl = function () {
    alert(config.apiRoot);
    throw "Not Implemented";
  };

  self.getTags = function(query) {
    throw "Not Implemented";
  }

  self.getLocations = function(query) {
    throw "Not Implemented";
  }

  self.savePost = function(post) {
    throw "Not Implemented";
  }

  self.signUp = function(model, callback) {
    throw "Not Implemented";
  }

  return self;
});
