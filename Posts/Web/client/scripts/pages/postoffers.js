define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function(postId) {

    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId + "/offers",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-postoffers-container");
      element.empty();
      if (data.length == 0) {
        element.append("<h3>Nobody added any offers yet.</h3>");
      } else {
        for (var i=0; i<data.length; i++) {
          var offer = data[i];
          var seller = offer.seller;
          var sellerDisplayName = seller.firstName + " " + seller.lastName;
          element.append($.templates("#templateOffer").render({
            offerId: offer.id,
            time: globals.formatDateTime(offer.time),
            seller: sellerDisplayName,
            newReplies: offer.replyCount,
            badgeCss: offer.replyCount == 0 ? "is-hidden" : "badge-highlighted"
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
