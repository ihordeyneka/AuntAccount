define(["../core/globals"], function(globals) {
  return function(element) {
    var self = {};
    self.element = element;

    var html =
       '<div class="aa-language-selector btn-group">\
          <a href="javascript:void(0)" class="header-nav__navigation-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>\
          <ul class="dropdown-menu">\
            <li><a href="javascript:void(0)" class="header-nav__navigation-link">en</a></li>\
            <li><a href="javascript:void(0)" class="header-nav__navigation-link">ua</a></li>\
          </ul>\
         </div>';

    element.html(html);
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
