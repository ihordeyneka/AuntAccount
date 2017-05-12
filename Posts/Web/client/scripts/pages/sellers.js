define(["../core/globals", "../core/config"], function(globals, config) {
  var self = {};

  self.init = function() {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    globals.loading($('body'), true);
    var userId = $.didoauth.user.id;
    $.ajax({
        url: config.apiRoot + "/users/"+ userId +"/sellers",
        dataType: "json"
    }).done(function(data) {
      var element = $(".aa-sellers-container");
      element.empty();
      for (var i=0; i<data.length; i++) {
        var seller = data[i];
        element.append($.templates("#templateSeller").render({
          id: seller.id,
          name: seller.name
        }));
      }
      bindEvents(element);
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  }

  var bindEvents = function(element) {
    element.find(".aa-seller-row .aa-remove").click(function(e) {
      var row = $(this).closest(".aa-seller-row");
      var id = row.data("seller-id");
      $.ajax({
          method: "DELETE",
          url: config.apiRoot + "/sellers/"+ id,
          dataType: "json"
      }).done(function(data) {
        row.remove();
      }).fail(function(result) {
        self.notificationArea.clear();
        self.notificationArea.error();
      }).always(function() {
        globals.loading($('body'), false);
      });
    });
  }

  return self;
});
