define(["core/didoauth"], function(didoauth) {
  var self = {};

  self.init = function() {
    $(".btn-signout").click(function() {
      didoauth.signOut();
    });
  }

  return self;
});
