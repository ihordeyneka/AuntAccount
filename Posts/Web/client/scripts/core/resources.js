define(["core/persistence",
        "i18n",
        "i18n_messagestore",
        "i18n_fallbacks",
        "i18n_parser",
        "i18n_emitter",
        "i18n_language",
      ], function(persistence) {
  var self = {};
  var LOCALE_KEY = "aa-locale";

  self.load = function() {
    var locale = persistence.retrieveData(LOCALE_KEY);
    if (locale)
      $.i18n({ locale: locale });

    //TODO: do not load all locales at once
    return $.i18n().load({
          'en': './client/scripts/i18n/en.json',
          'ua': './client/scripts/i18n/ua.json'
        });
  };

  self.setLocale = function(locale) {
    persistence.persistData(LOCALE_KEY, locale);
    $.i18n({ locale: locale });
  };

  self.translate = function() {
    $("body").i18n();
  };

  return self;
});
