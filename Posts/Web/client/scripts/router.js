define(["core/didoauth"], function(didoauth) {
  var self = {};

  var menu = [
    { hash: "#home", route: "recent", text: "Home" },
    { hash: "#post", route: "post", text: "Post" },
    { hash: "#notifications", route: "notifications", text: "Notifications" },
    { hash: "#login", route: "login", text: "Login", hideWhen: function() { return didoauth.user.signedIn; } },
    { hash: "#signup", route: "signup", text: "Sign Up", hideWhen: function() { return didoauth.user.signedIn; } },
    { hash: "#signout", route: "signout", text: "Sign Out", hideWhen: function() { return !didoauth.user.signedIn; } },
  ];

  self.refreshMenu = function() {
    var element = $("#navMenu");
    element.empty();
    for (var i=0; i<menu.length; i++) {
      var menuItem = menu[i];
      var hide = menuItem.hideWhen && menuItem.hideWhen();
      if (!hide) {
        element.append($.templates("#templateMenuItem").render({
          hash: menuItem.hash,
          route: menuItem.route,
          text: menuItem.text
        }));
      }
    }
  }

  self.init = function() {
    var hash = location.hash != "" ? location.hash : "#home";
    navigate(hash);
  }

  self.home = function() {
    self.refreshMenu();
    location.hash = "";
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

      if (existsRouteHandler(route))
        return;
    }

    var routeParts = route.split("/");

    //default route is #page/{param1}/{param2}/... where id is optional
    var page = "recent";
    if (routeParts.length > 0)
      page = routeParts[0];

    var href = "client/views/" + page + ".html";

    $.ajax({
      url: href
    }).done(function(html) {
      require(["core/globals"], function(globals) { // make sure String.replaceAll is defined
        for(var i = 1; i < routeParts.length; i++) {
          var token = "{{:" + (i - 1) + "}}"
          html = html.replaceAll(token, routeParts[i]);
        }
        $("#router").html(html);
      });
    });
  }

  var existsRouteHandler = function(route) {
    if (route == "signout") {
      didoauth.signOut();
      return true;
    }
    return false;
  }

  window.onhashchange = self.init;

  return self;
});
