requirejs.config({
  baseUrl: '/client/scripts',
  map: {
    '*': {
      'communication_client': 'communication/mock_client'
    }
  }
});

window.globals = {
  loading: function(element, show) {
    if (show) {
      var loadingContainer = $(
        '<div class="loading-container">\
        </div>');

      var loading = $(
          '<div class="loading">\
            <span class="spinner" />\
          </div>');

      loading.width(element.width());
      loading.height(element.height());
      loadingContainer.append(loading);
      element.prepend(loadingContainer);
    }
    else {
      element.children(".loading-container").remove();
    }
  }
}
