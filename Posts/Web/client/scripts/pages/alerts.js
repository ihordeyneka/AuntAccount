define(["../core/globals", "../core/config", "../components/grid", "underscore"], function(globals, config, grid, _) {
  var self = {};

  self.init = function() {

    var userId = $.didoauth.user.id;
    $("#gridAlerts").bootstrapTable({
      uniqueId: 'id',
      pagination: true,
      pageSize: 5,
      pageList: [5],
      sidePagination: 'server',
      url: config.apiRoot + '/posts/notifications/' + userId
    });
    $("#gridAlerts").on("click", "tr", function() {
      var id = $(this).data("uniqueid");
      window.location = "#postdetails/" + id;
    });
  }

  return self;
});
