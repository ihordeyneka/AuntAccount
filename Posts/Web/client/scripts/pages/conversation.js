define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};
  self.element = null;

  self.init = function(conversationId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/offers/" + conversationId + "/messages",
        dataType: "json"
    }).done(function(data) {

      self.element = $(".aa-conversation-container");
      self.element.empty();
      if (data.length == 0) {
        self.element.append("<h3>There are no replies yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var reply = data[i];
          appendReply(reply);
        }
      }

      self.initNewReply(conversationId);

    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

  }

  self.initNewReply = function(conversationId) {
    var sendReply = function() {
      var description = $("#inputNewReply").val();
      var messageData = {
                  description: description,
                  offerId: conversationId
             };
      $.post({
          url: config.apiRoot + "/messages",
          dataType: "json",
          contentType: "application/json",
          data: JSON.stringify(messageData),
      }).done(function(reply) {
        appendReply(reply);
        $("#inputNewReply").val("").blur().focus(); //clear input and focus it
      }).fail(function(result) {
        self.notificationArea.clear();
        self.notificationArea.error();
      });
    };

    $("#btnSendReply").click(sendReply);

    $("#inputNewReply")
      .focus()
      .keypress(function(event) {
        if(event.keyCode == 13) { //Enter Key Press event handler
          sendReply();
        }
      });
  }

  var appendReply = function(reply) {
    var userId = $.didoauth.user.id;
    self.element.append($.templates("#templateReply").render({
      replyId: reply.id,
      time: reply.creationDate,
      header: reply.sender.firstName.concat(" ").concat(reply.sender.lastName),
      content: reply.description,
      replyOffset: reply.sender.id === userId ? "4" : "2", //bootstrap offsets
      replyCss: reply.sender.id === userId  ? "aa-reply-my" : "aa-reply-their"
    }));
  }

  return self;
});
