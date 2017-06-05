define(["../core/globals", "../core/config", "../components/message_input"], function(globals, config, messageInputControl) {
  var self = {};
  self.element = null;

  self.init = function(offerId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/offers/" + offerId + "/messages",
        dataType: "json"
    }).done(function(data) {

      self.element = $(".aa-offer-container");
      self.element.empty();
      if (data.length == 0) {
        self.element.append("<h3>There are no replies yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var reply = data[i];
          appendReply(reply);
        }
      }

      self.initNewReply(offerId);

    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

  }

  self.initNewReply = function(offerId) {
    var input = new messageInputControl($(".aa-input-container"), {
      uploadUrl: config.apiRoot + "/offers/" + offerId + "/messages/upload"
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
    });
    input.element.on("uploaderror", function(e, data) {
      self.notificationArea.error();
    });
  }

  var appendReply = function(reply) {
    var userId = $.didoauth.user.id;
    self.element.append($.templates("#templateReply").render({
      replyId: reply.id,
      time: reply.creationDate,
      header: reply.sender.firstName.concat(" ").concat(reply.sender.lastName),
      content: reply.description,
      replyOffset: reply.sender.id === userId ? "2" : "4", //bootstrap offsets
      replyCss: reply.sender.id === userId  ? "aa-reply-my" : "aa-reply-their",
      picture: reply.picture != null ? '<img class="aa-post-picture" src="' + reply.picture + '"></img>' : ''
    }));
  }

  return self;
});
