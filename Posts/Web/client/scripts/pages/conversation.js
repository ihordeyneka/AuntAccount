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
            element.append($.templates("#templateReply").render({
              replyId: reply.replyId,
              time: reply.time,
              header: reply.author.name,
              content: reply.content,
              replyOffset: reply.author.isMe ? "5" : "2" //bootstrap offsets
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
