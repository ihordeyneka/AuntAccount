define(["../core/globals", "../core/config", "../components/grid", "underscore"], function(globals, config, grid, _) {
  var self = {};

  self.init = function() {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    var userId = $.didoauth.user.id;
    $("#gridAlerts").bootstrapTable({
      uniqueId: 'postId',
      pagination: true,
      pageSize: 5,
      pageList: [5],
      sidePagination: 'server',
      url: config.apiRoot + '/users/' + userId + '/notifications'
    });

    $("#gridAlerts").on("click-row.bs.table", function(row, $element, field) {
      navigateToAlert($element.postId);
    });
  }

  var navigateToAlert = function(postId) {
    globals.loading($('body'), true);
    $.ajax({
        url: config.apiRoot + "/posts/" + postId + "/offer",
        dataType: "json"
    }).done(function(data) {
      if (data && data.id)
        window.location = "#offer/" + data.id;
      else
        window.location = "#postdetails/" + postId;
    }).fail(function(result) {
      self.notificationArea.error();
    }).always(function() {
      globals.loading($('body'), false);
    });
  };

  return self;
});
