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

  self.defaultLocale = "en";
  self.localeMapping = {
        'en': './client/scripts/i18n/en.json',
        'ua': './client/scripts/i18n/ua.json'
      };

  self.load = function() {
    var locale = persistence.retrieveData(LOCALE_KEY);
    if (locale)
      $.i18n({ locale: locale });
    else
      locale = self.getLocale();

    var store = {};
    store[locale] = self.localeMapping[locale];
    return $.i18n().load(store);
  };

  self.getSupportedLocales = function() {
    return Object.keys(self.localeMapping);
  };

  self.getLocale = function() {
    return $.i18n().locale;
  };

  self.setLocale = function(locale) {
    if (!self.localeMapping[locale]) {
      locale = self.defaultLocale;
    }
    persistence.persistData(LOCALE_KEY, locale);
    return self.load();
  };

  self.translate = function() {
    $("body").i18n();
  };

  return self;
});
