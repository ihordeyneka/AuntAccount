define(["core/didoauth"], function(didoauth) {

  var ShowFor = {
    ALL: 0,
    ANONYMOUS: 1,
    AUTHENTICATED: 2
  };

  var menu = [
    { hash: "#home", route: "recent", text: "Home" },
    { hash: "#post", route: "post", text: "Post" },
    { hash: "#notifications", route: "notifications", text: "Notifications" },
    { hash: "#login", route: "login", text: "Login", showFor: ShowFor.ANONYMOUS },
    { hash: "#signup", route: "signup", text: "Sign Up", showFor: ShowFor.ANONYMOUS },
    { hash: "#profile", route: "profile", text: "{{:user}}", showFor: ShowFor.AUTHENTICATED }
  ];

  var menuItemVisible = function(menuItem) {
    if (menuItem.showFor == ShowFor.ANONYMOUS)
      return !didoauth.user.signedIn;
    if (menuItem.showFor == ShowFor.AUTHENTICATED)
      return didoauth.user.signedIn;
    return true;
  }

  var processMenuItem = function(menuItem) {
    if (didoauth.user.signedIn)
      menuItem.text = menuItem.text.replace("{{:user}}", didoauth.user.firstName + " " + didoauth.user.lastName);
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
