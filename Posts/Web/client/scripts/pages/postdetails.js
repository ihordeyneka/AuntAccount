define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function(postId) {

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId + "/offers", //TODO: udpate URL
        dataType: "json"
    }).done(function(data) {

    }).fail(function(result) {
      self.notificationArea = $(".aa-notification-area").notificationArea();
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });

  }

  return self;
});
