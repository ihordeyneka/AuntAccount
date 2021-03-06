define(["../../views/postdetails.html", "../core/globals", "../core/config", "../components/message_input", "../components/post_info"], function(source, globals, config, messageInputControl, postInfoControl) {
  var self = {};

  self.source = source;

  self.init = function(postId, sellerId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId,
        dataType: "json"
    }).done(function(data) {
      var postInfo = new postInfoControl($(".aa-post-container"));
      postInfo.init(data);
      initNewReply(data, sellerId);
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  };

  var initNewReply = function(post, sellerId) {
    var input = new messageInputControl($(".aa-input-container"), {
      uploadUrl: config.apiRoot + "/offers/upload",
      uploadExtraData: function() { return { postId: post.id, sellerId: sellerId }; }
    });

    var navigateToOffer = function(offerId) {
      window.location = "#offer/" + offerId;
    };
    var sendReply = function(e, data) {
      var messageData = {
        description: data.description,
        postId: post.id,
        seller: {
            id: sellerId
        }
      };
      $.post({
          url: config.apiRoot + "/offers/",
          dataType: "json",
          contentType: "application/json",
          data: JSON.stringify(messageData),
      }).done(function(data) {
        navigateToOffer(data.id);
      }).fail(function(result) {
        self.notificationArea.clear();
        self.notificationArea.error();
      });
    };

    input.element.on("replied", sendReply);
    input.element.on("uploadsuccess", function(e, data) {
      navigateToOffer(data.response.id);
    });
    input.element.on("uploaderror", function(e, data) {
      self.notificationArea.error();
    });
  }

  return self;
});
