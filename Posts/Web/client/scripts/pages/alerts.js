define(["../core/globals", "../core/config", "underscore"], function(globals, config, _) {
  var self = {};

  self.init = function() {

    globals.loading($('body'), true);
    var userId = $.didoauth.user.id;
    $.ajax({
        url: config.apiRoot + "/posts/notifications/"+ userId,
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-alerts-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3 class='center'>There were no posts added recently to match your subscriptions.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var post = data[i];
          element.append($.templates("#templateAlert").render({
            postId: post.id,
            time: post.creationDate,
            title: post.postTags,
            content: post.description
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
