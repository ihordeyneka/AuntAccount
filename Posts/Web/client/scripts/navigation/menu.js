define(["core/didoauth"], function(didoauth) {

  var ShowFor = {
    ALL: 0,
    ANONYMOUS: 1,
    AUTHENTICATED: 2
  };

  var menu = [
    { hash: "#home", route: "recent", key: "Home" },
    { hash: "#post", route: "post", key: "Post" },
    { hash: "#notifications", route: "alerts", key: "Notifications" },
    { hash: "#login", route: "login", key: "Login", showFor: ShowFor.ANONYMOUS },
    { hash: "#signup", route: "signup", key: "SignUp", showFor: ShowFor.ANONYMOUS },
    { hash: "#profile", route: "profile", key: "", template: "{{:user}}", showFor: ShowFor.AUTHENTICATED }
  ];

  var menuItemVisible = function(menuItem) {
    var route = menuItem.route;
    if (route == "login" || route == "signup")
      return !didoauth.user.signedIn;
    if (route == "profile")
      return didoauth.user.signedIn;
    if (route == "alerts")
      return didoauth.user.signedIn && didoauth.user.hasSellers;
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
          key: menuItem.key,
          text: menuItem.text
        }));
      }
    }
  }

  return self;
});
