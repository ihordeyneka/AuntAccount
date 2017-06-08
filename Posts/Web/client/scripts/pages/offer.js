define(["../core/globals", "../core/config", "../components/message_input", "../components/post_info"], function(globals, config, messageInputControl, postInfoControl) {
  var self = {};
  self.element = null;

  self.init = function(offerId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/offers/" + offerId + "/messages",
        dataType: "json"
    }).done(function(data) {

      var postInfo = new postInfoControl($(".aa-post-container"));
      postInfo.init(data.post);

      self.element = $(".aa-offer-container");
      self.element.empty();

      for (var i=0; i<data.messages.length; i++) {
        var reply = data.messages[i];
        appendReply(reply);
      }

      self.initNewReply(offerId);

    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

  }

  var initDetails = function(post) {
    $("#spanUser").text(post.user.firstName.concat(" ").concat(post.user.lastName));
    $("#spanTime").text(globals.formatDateTime(post.creationDate));
    $("#divDescription").text(post.description);
    if (post.photo) {
      $("<img>").addClass("aa-post-picture").attr("src", post.photo).appendTo("#divPicture");
    }
  };

  self.initNewReply = function(offerId) {
    var input = new messageInputControl($(".aa-input-container"), {
      uploadUrl: config.apiRoot + "/messages/upload",
      uploadExtraData: function() { return { offerId: offerId }; }
    });
    var sendReply = function(e, data) {
      var messageData = {
        description: data.description,
        offerId: offerId
      };
      $.post({
          url: config.apiRoot + "/messages",
          dataType: "json",
          contentType: "application/json",
          data: JSON.stringify(messageData),
      }).done(function(reply) {
        appendReply(reply);
        input.purge();
      }).fail(function(result) {
        self.notificationArea.clear();
        self.notificationArea.error();
      });
    };

    input.element.on("replied", sendReply);
    input.element.on("uploadsuccess", function(e, data) {
      appendReply(data.response);
      input.purge();
    });
    input.element.on("uploaderror", function(e, data) {
      self.notificationArea.error();
    });
  }

  var appendReply = function(reply) {
    var userId = $.didoauth.user.id;
    self.element.append($.templates("#templateReply").render({
      replyId: reply.id,
      time: globals.formatDateTime(reply.creationDate),
      header: reply.sender.firstName.concat(" ").concat(reply.sender.lastName),
      content: reply.description,
      replyOffset: reply.sender.id === userId ? "2" : "4", //bootstrap offsets
      replyCss: reply.sender.id === userId  ? "aa-reply-my" : "aa-reply-their",
      picture: reply.photo != null ? '<img class="aa-post-picture" src="' + reply.photo + '"></img>' : ''
    }));
  }

  return self;
});
