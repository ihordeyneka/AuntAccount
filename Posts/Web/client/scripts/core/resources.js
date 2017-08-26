define(["i18n",
        "i18n_messagestore",
        "i18n_fallbacks",
        "i18n_parser",
        "i18n_emitter",
        "i18n_language",
      ], function() {
  var self = {};

  self.load = function() {
    $.i18n().load({
          'en': './client/scripts/i18n/en.json',
          'ua': './client/scripts/i18n/ua.json'
        }).done(function() {
          self.setLocale("ua");
          console.log($.i18n("Key"));
        });
  };

  self.setLocale = function(locale) {
    $.i18n( {
        locale: locale
    } );
  };

  self.load();

  return self;
});
