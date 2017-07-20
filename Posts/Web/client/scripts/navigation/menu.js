define(["core/didoauth"], function(didoauth) {

  var ShowFor = {
    ALL: 0,
    ANONYMOUS: 1,
    AUTHENTICATED: 2
  };

  var menu = [
    { hash: "#home", route: "recent", text: "Home" },
    { hash: "#post", route: "post", text: "Post" },
    { hash: "#notifications", route: "alerts", text: "Notifications" },
    { hash: "#login", route: "login", text: "Login", showFor: ShowFor.ANONYMOUS },
    { hash: "#signup", route: "signup", text: "Sign Up", showFor: ShowFor.ANONYMOUS },
    { hash: "#profile", route: "profile", template: "{{:user}}", showFor: ShowFor.AUTHENTICATED }
  ];

  var menuItemVisible = function(menuItem) {
    var route = menuItem.route;
    if (route == "login" || route == "signup")
      return !didoauth.user.signedIn;
    if (route == "profile")
      return didoauth.user.signedIn;
    if (route == "alerts")
      return didoauth.user.signedIn && didoauth.user.sellers && didoauth.user.sellers.length > 0;
    return true;
  }

  var processMenuItem = function(menuItem) {
    if (menuItem.template && didoauth.user.signedIn)
      menuItem.text = menuItem.template.replace("{{:user}}", didoauth.user.firstName + " " + didoauth.user.lastName);
  }

  self.refresh = function() {
    var element = $("#navMenu");
    element.empty();
    for (var i=0; i<menu.length; i++) {
      var menuItem = menu[i];
      if (menuItemVisible(menuItem)) {
        processMenuItem(menuItem);
        element.append($.templates("#templateMenuItem").render({
          hash: menuItem.hash,
          route: menuItem.route,
          text: menuItem.text
        }));
      }
    }
  }

  return self;
});
