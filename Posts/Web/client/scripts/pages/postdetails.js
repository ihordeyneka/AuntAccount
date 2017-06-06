define(["../core/globals", "../core/config", "../components/message_input"], function(globals, config, messageInputControl) {
  var self = {};

  self.init = function(postId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId,
        dataType: "json"
    }).done(function(data) {
      initDetails(data);
      initNewReply(data);
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  };

  var initDetails = function(post) {
    $("#spanUser").text(post.user.firstName.concat(" ").concat(post.user.lastName));
    $("#spanTime").text(globals.formatDateTime(post.creationDate));
    $("#divDescription").text(post.description);
    if (post.picture) {
      $("<img>").addClass("aa-post-picture").attr("src", post.picture).appendTo("#divPicture");
    }
  };

  var initNewReply = function(post) {
    var input = new messageInputControl($(".aa-input-container"), {
      uploadUrl: config.apiRoot + "/posts/" + post.postId + "/offers/upload"
    });

    var navigateToOffer = function(offerId) {
      window.location = "#offer/" + offerId;
    };
    var sendReply = function(e, data) {
      var messageData = {
        description: data.description,
        postId: post.postId
      };
      $.post({
          url: config.apiRoot + "/offer/",
          dataType: "json",
          contentType: "application/json",
          data: JSON.stringify(messageData),
      }).done(function(data) {
        navigateToOffer(data.offerId);
      }).fail(function(result) {
        self.notificationArea.clear();
        self.notificationArea.error();
      });
    };

    input.element.on("replied", sendReply);
    input.element.on("uploadsuccess", function(e, data) {
      navigateToOffer(data.response.offerId);
    });
    input.element.on("uploaderror", function(e, data) {
      self.notificationArea.error();
    });
  }

  return self;
});
