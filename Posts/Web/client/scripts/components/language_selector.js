define(["../core/globals"], function(globals) {
  return function(element, supportedLocales) {
    var self = {};
    self.element = element;

    var html =
       '<div class="aa-language-selector btn-group">\
          <a href="javascript:void(0)" class="header-nav__navigation-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>\
          <ul class="dropdown-menu">\
          </ul>\
         </div>';

    element.html(html);
    var dropdownMenu = element.find(".dropdown-menu");
    for (var i = 0; i < supportedLocales.length; i++) {
      var menuItem = $.templates("<li><a href='javascript:void(0)'' class='header-nav__navigation-link'>{{:locale}}</a></li>").render({ locale: supportedLocales[i] });
      dropdownMenu.append(menuItem);
    }
    element.find(".dropdown-menu li a").click(function(e) {
      var locale = $(e.target).text();
      self.setLanguage(locale);
      self.element.trigger("localeChanged", { locale: locale });
    });

    self.setLanguage = function(value) {
      self.element.find(".dropdown-toggle").text(value);
    }

    return self;
  }
});
