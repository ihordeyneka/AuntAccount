define(["../core/config"], function(config) {
  var self = this;

  self.attachmentUploadUrl = config.apiRoot; //TODO:update this

  self.getTags = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.getLocations = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.savePost = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.signUp = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.getMyRecentPosts = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.getPostConversations = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.getConversationReplies = function(parameters, callback) {
    throw "Not Implemented";
  }

  return self;
});
