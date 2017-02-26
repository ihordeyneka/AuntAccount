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

  self.getMyRecentPosts = function(parameters, callback) {
      $.ajax({
          url: config.apiRoot + "/users/2/posts",
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
      var ref = config.apiRoot + "/offers/{param}/messages";
      $.ajax({
          url: ref.replace("{param}", parameters.conversationId),
          dataType: "json"
      }).done(function(result) {
          callback({
              success: true,
              data: result
          });
      });

  }

  return self;
});
