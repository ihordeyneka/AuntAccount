define(["../../views/recent.html", "../core/globals", "../core/config", "../core/resources", "underscore"], function(source, globals, config, resources, _) {
  var self = {};

  self.source = source;

  self.init = function() {

    var element = $(".aa-recent-container");
    var userId = $.didoauth.user.id;

    if (!userId)
      showWelcome(element);
    else {
      globals.loading($('body'), true);
      $.ajax({
        url: config.apiRoot + "/users/" + userId + "/posts",
        dataType: "json"
      }).done(function (data) {
        element.empty();
        if (data.length == 0) {
          showWelcome(element);
        } else {
          for (var i = 0; i < data.length; i++) {
            var post = data[i];
            element.append($.templates("#templatePost").render({
              postId: post.id,
              time: globals.formatDate(post.creationDate),
              title: post.postTags,
              content: post.description,
              offers: post.offerCount,
              badgeCss: post.newMessages ? "badge-highlighted" : ""
            }));
          }
        }
        resources.translate();
      }).fail(function (result) {
        self.notificationArea = $(".aa-notification-area").notificationArea();
        self.notificationArea.error();
      }).always(function () {
        globals.loading($('body'), false);
      });
    }
  }

  function showWelcome(element) {
    element.append("<h3 class='center' data-i18n='NoPosts'>Welcome to DIDO! Whatever you want to buy, start by simply adding a new post and that's it, the sellers will contact you.</h3>");
  }

  return self;
});
