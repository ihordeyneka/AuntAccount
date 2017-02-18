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
      $.ajax({
          url: "http://localhost:8080/api/service/users/2/posts",
          dataType: "json"
      }).done(function(result) {
          callback({
              success: true,
              data: result
          });
      });

  }

  self.getPostConversations = function(parameters, callback) {
    throw "Not Implemented";
  }

  self.getConversationReplies = function(parameters, callback) {
    throw "Not Implemented";
  }

  return self;
});
