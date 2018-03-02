define(["../../views/post.html", "../core/globals", "../core/config", "../components/grid"], function(source, globals, config, grid) {
  var self = {};

  self.source = source;

  self.init = function(postId) {

    self.notificationArea = $(".aa-notification-area").notificationArea();

    var userId = $.didoauth.user.id;
    window.grid = $("#gridOffers").bootstrapTable({
      uniqueId: 'id',
      pagination: true,
      pageSize: 5,
      pageList: [5],
      sidePagination: 'client',
      url: config.apiRoot + "/posts/" + postId + "/offers",
      formatShowingRows: function (pageFrom, pageTo, totalRows) { return globals.formatString($.i18n('FormatShowingRows'), pageFrom, pageTo, totalRows); },
      formatLoadingMessage: function () { return $.i18n('FormatLoadingMessage'); },
      formatNoMatches: function () { return $.i18n('FormatNoMatches'); }
    });

    $("#gridOffers").on("click-row.bs.table", function(row, $element, field) {
      window.location = "#offer/" + $element.id;
    });

    window.formatReplyCount = function(replyCount) {
      return replyCount ? '<span class="badge">' + replyCount + '</span>' : '';
    }
  }

  return self;
});
