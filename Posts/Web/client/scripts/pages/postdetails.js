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
          var supplier = conversation.supplier;
          var supplierDisplayName = supplier.firstName + " " + supplier.lastName;
          element.append($.templates("#templateConversation").render({
            conversationId: conversation.id,
            time: conversation.time,
            supplier: supplierDisplayName,
            newReplies: conversation.replyCount,
            badgeCss: conversation.replyCount == 0 ? "is-hidden" : "badge-highlighted"
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
