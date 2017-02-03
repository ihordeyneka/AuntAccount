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
          element.append($.templates("#templatePost").render({
            title: "Alert",
            content: "You haven't added any posts yet."
          }));
        } else {
          for (var i=0; i<res.data.length; i++) {
            var post = res.data[i];
            element.append($.templates("#templatePost").render({
              title: post.title,
              content: post.description
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
