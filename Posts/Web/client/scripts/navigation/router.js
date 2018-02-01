define(["../core/globals", "../core/didoauth", "./menu", "../core/resources"], function(globals, didoauth, menu, resources) {
  var self = {};

  var cleanHtml = function(html) {
    //TODO: implement a dynamic and fast way of cleaning HTML from replacement tokens
    return html.replaceAll("{{:0}}", "").replaceAll("{{:1}}", "").replaceAll("{{:2}}", "");
  }

  self.init = function() {
    var hash = location.hash != "" ? location.hash : "#home";
    navigate(hash);
  }

  self.home = function() {
    menu.refresh();
    location.hash = "";
  }

  self.toLogin = function() {
    menu.refresh();
    location.hash = "#login";
  }

  var navigate = function(hash) {
    var route = hash.slice(1) || "/";

    var element = $("a[href='" + hash + "']");
    if (element != null) {
      $(".router-link.active").removeClass("active");
      element.addClass("active");

      var toggle = $(".navbar-toggle");
      if (toggle.is(":visible") && $(".navbar-collapse").hasClass("in"))
        toggle.click();

      if (element.data("route"))
        route = element.data("route");
    }

    var routeParts = route.split("/");
    
    //default route is #page/{param1}/{param2}/... where id is optional
    var page = "recent";
    if (routeParts.length > 0)
      page = routeParts[0];

    routeParts.shift(); //remove the first entry

    import("../pages/" + page  /* webpackChunkName: "chunk-[request]" */ )
      .then(function (script) {
        $("#router").html(cleanHtml(script.source));
        script.init.apply(this, routeParts);
        resources.translate();
      }, function () {
        window.location = "/notfound.html";
      });
  }

  window.onhashchange = self.init;

  return self;
});
