define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function(postId) {

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId + "/offers",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-postdetails-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3>Nobody added any offers yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var conversation = data[i];
          element.append($.templates("#templateConversation").render({
            conversationId: conversation.conversationId,
            time: conversation.time,
            supplier: conversation.supplier.name,
            newReplies: conversation.newReplies,
            badgeCss: conversation.newReplies == 0 ? "is-hidden" : "badge-highlighted"
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
