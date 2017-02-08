define(["../core/globals", "communication_client"], function(globals, client) {
  var self = {};

  self.init = function() {

    globals.loading($('body'), true);
    client.getMyRecentPosts({}, function(res) {
      globals.loading($('body'), false);
      if (res.success) {
        var element = $(".aa-recent-container");
        element.empty();
        if (res.data.length == 0) {
          element.append("<h3>You haven't added any posts yet.</h3>");
        } else {
          for (var i=0; i<res.data.length; i++) {
            var post = res.data[i];
            element.append($.templates("#templatePost").render({
              postId: post.postId,
              time: post.time,
              title: post.tags.join(),
              content: post.description,
              conversations: post.conversations,
              badgeCss: post.conversations == 0 ? "is-hidden" : (post.newMessages ? "badge-highlighted" : "")
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
