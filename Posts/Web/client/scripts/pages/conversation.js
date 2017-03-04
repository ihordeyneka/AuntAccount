define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function(conversationId) {

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/offers/" + conversationId + "/messages",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-conversation-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3>There are no replies yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var reply = data[i];
          var displayName = reply.sender.firstName.concat(" ").concat(reply.sender.lastName);
          element.append($.templates("#templateReply").render({
            replyId: reply.id,
            time: reply.creationDate,
            header: displayName,
            content: reply.description,
            ///TODO: check if user is logged in
            /* replyOffset: reply.sender.id ? "4" : "2", //bootstrap offsets
            replyCss: reply.sender.id ? "aa-reply-my" : "aa-reply-their"*/
            replyOffset: "4",
            replyCss: "aa-reply-my"
          }));
        }
      }
    }).fail(function(result) {
      self.notificationArea = $(".aa-notification-area").notificationArea();
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

  }

    return self;
});
