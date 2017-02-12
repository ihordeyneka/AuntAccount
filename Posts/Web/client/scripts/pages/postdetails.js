define(["../core/globals", "communication_client"], function(globals, client) {
  var self = {};

  self.init = function(postId) {

    globals.loading($('body'), true);
    client.getPostConversations({postId: postId}, function(res) {
      globals.loading($('body'), false);
      if (res.success) {
        var element = $(".aa-postdetails-container");
        element.empty();
        if (res.data.length == 0) {
          element.append("<h3>Nobody added any offers yet.</h3>");
        } else {
          for (var i=0; i<res.data.length; i++) {
            var conversation = res.data[i];
            element.append($.templates("#templateConversation").render({
              conversationId: conversation.conversationId,
              time: conversation.time,
              supplier: conversation.supplier.name,
              newReplies: conversation.newReplies,
              badgeCss: conversation.newReplies == 0 ? "is-hidden" : "badge-highlighted"
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
