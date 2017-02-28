define(["../core/globals", "../core/config", "underscore"], function(globals, config, _) {
  var self = {};

  self.init = function() {

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/users/2/posts", //TODO: use actual user id here
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-recent-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3>You haven't added any posts yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var post = data[i];
          element.append($.templates("#templatePost").render({
            postId: post.id,
            time: post.creationDate,
            title: _.map(post.postTags, function(i) {return i.tag;}).join(),
            content: post.description,
            conversations: post.conversations,
            badgeCss: post.conversations == 0 ? "is-hidden" : (post.newMessages ? "badge-highlighted" : "")
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
