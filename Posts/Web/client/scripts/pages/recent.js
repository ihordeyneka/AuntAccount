define(["../core/globals", "../core/config", "underscore", "moment"], function(globals, config, _, moment) {
  var self = {};

  self.init = function() {

    globals.loading($('body'), true);
    var userId = $.didoauth.user.id;
    $.ajax({
        url: config.apiRoot + "/users/"+ userId +"/posts",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-recent-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3 class='center'>You haven't added any posts yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var post = data[i];
          element.append($.templates("#templatePost").render({
            postId: post.id,
            time: moment(post.creationDate).format("DD-MM-YYYY"),
            title: post.postTags,
            content: post.description,
            offers: post.offerCount,
            badgeCss: post.offerCount == 0 ? "is-hidden" : (post.newMessages ? "badge-highlighted" : "")
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
