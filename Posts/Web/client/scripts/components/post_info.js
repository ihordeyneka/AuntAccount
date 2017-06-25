define(["../core/globals"], function(globals) {
  return function(element) {
    var self = {};
    self.element = element;

    var html =
       '<div class="panel panel-default">\
          <div class="panel-heading">\
            <h3 class="panel-title">\
              <span class="right aa-post-info-time"></span>\
              <a class="aa-post-info-user"></a>\
            </h3>\
          </div>\
          <div class="panel-body">\
            <div class="aa-post-info-description"></div>\
            <div class="aa-post-info-photo"></div>\
          </div>\
        </div>';

    element.html(html);

    self.init = function(post) {
      var profileUrl = "#profile/" + post.user.id;
      var userName = post.user.firstName.concat(" ").concat(post.user.lastName);

      self.element.find(".aa-post-info-user").attr("href", profileUrl).text(userName);
      self.element.find(".aa-post-info-time").text(globals.formatDateTime(post.creationDate));
      self.element.find(".aa-post-info-description").text(post.description);
      if (post.photo) {
        $("<img>").addClass("aa-post-picture").attr("src", post.photo).appendTo(self.element.find(".aa-post-info-photo"));
      }
    }

    return self;
  }
});
