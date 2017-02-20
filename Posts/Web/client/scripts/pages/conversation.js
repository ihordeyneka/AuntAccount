define(["../core/globals", "communication_client"], function(globals, client) {
  var self = {};

  self.init = function(conversationId) {

    globals.loading($('body'), true);
    client.getConversationReplies({conversationId: conversationId}, function(res) {
      globals.loading($('body'), false);
      if (res.success) {
        var element = $(".aa-conversation-container");
        element.empty();
        if (res.data.length == 0) {
          element.append("<h3>There are no replies yet.</h3>");
        } else {
          for (var i=0; i<res.data.length; i++) {
            var reply = res.data[i];
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
      }
      else {
        self.notificationArea = $(".aa-notification-area").notificationArea();
        self.notificationArea.error();
      }
    });
  }

    return self;
});