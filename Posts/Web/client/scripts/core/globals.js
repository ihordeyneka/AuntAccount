define(["jquery", "moment"], function($, moment) {
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

  self.formatDate = window.formatDate = function(unix) {
    return moment(unix).format("MM/DD/YYYY");
  }

  self.formatDateTime = window.formatDateTime = function(unix) {
    return moment(unix).format("MM/DD/YYYY HH:mm");
  }

  self.formatString = function (str) {
    var args = arguments,
      flag = true,
      i = 1;

    str = str.replace(/%s/g, function () {
      var arg = args[i++];

      if (typeof arg === 'undefined') {
        flag = false;
        return '';
      }
      return arg;
    });
    return flag ? str : '';
  };

  $.fn.notificationArea = function(options) {
    var settings = {
      element: this,
      successHeader: $.i18n('Success'),
      successMessage: $.i18n('OperationSuccessful'),
      validationWarningHeader: $.i18n('Warning'),
      validationWarningMessage: $.i18n('ValidationError'),
      errorHeader: $.i18n('Error'),
      errorMessage: $.i18n('ErrorOccurred')
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

  String.prototype.replaceAll = function(search, replacement) {
      var target = this;
      return target.replace(new RegExp(search, 'g'), replacement);
  };

  return self;
});
