define([], function() {
  var self = {};

  self.loading = function(element, show) {
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

  self.validate = function(options) {
    var settings = {
      notificationArea: null,
      validatorForm: null,
      success: function() {}
    };

    $.extend(true, settings, options); //now we can use settings

    if (settings.notificationArea)
      settings.notificationArea.clear(); //clear all alerts

    if (!settings.validatorForm || settings.validatorForm.validator('validate').has('.has-error').length === 0) {
      if (settings.success)
        settings.success();
    } else {
      if (settings.notificationArea)
        settings.notificationArea.validationWarning();
    }
  }

  $.fn.notificationArea = function(options) {
    var settings = {
      element: this,
      successHeader: "Success!",
      successMessage: "The operation was successful.",
      validationWarningHeader: "Warning!",
      validationWarningMessage: "One or more validation failed, please fix the errors and try again.",
      errorHeader: "Error!",
      errorMessage: "An error has occurred, please try again."
    };
    $.extend(true, settings, options);

    return {
      clear: function() {
        settings.element.find(".alert").remove();
      },
      success: function(options) {
        options = options || {};
        settings.element.append($.templates("#templateSuccess").render({
          header: options.header || settings.successHeader,
          message: options.message || settings.successMessage
        })).focus();
      },
      validationWarning: function(options) {
        options = options || {};
        settings.element.append($.templates("#templateError").render({
          header: options.header || settings.validationWarningHeader,
          message: options.message || settings.validationWarningMessage
        })).focus();
      },
      error: function(options) {
        options = options || {};
        settings.element.append($.templates("#templateError").render({
          header: options.header || settings.errorHeader,
          message: options.message || settings.errorMessage
        })).focus();
      }
    };
  };

  return self;
});
