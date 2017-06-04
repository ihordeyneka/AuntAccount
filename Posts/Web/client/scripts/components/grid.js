define(["bootstraptable"], function(table) {
  var originalOnPageNumber = $.fn.bootstrapTable.Constructor.prototype.onPageNumber;
  $.fn.bootstrapTable.Constructor.prototype.onPageNumber = function(event) {
    //do nothing if we click the current page number
    if (this.options.pageNumber === +$(event.currentTarget).text()) {
        return false;
    }
    return originalOnPageNumber.call(this, event);
  };
});
