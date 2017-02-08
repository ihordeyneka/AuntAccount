$(function(){

  var init = function () {
    var hash = location.hash != "" ? location.hash : "#home";
    navigate(hash);
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

  init();
  window.onhashchange = init;
});
