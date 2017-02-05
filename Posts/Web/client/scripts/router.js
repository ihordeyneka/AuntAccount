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

    //default route is #page/{id} where id is optional
    var page = "recent";
    if (routeParts.length > 0)
      page = routeParts[0];

    var href = "client/views/" + page + ".html";
    if (routeParts.length > 1)
      href += "?id=" + routeParts[1];

    $.ajax({
      url: href
    }).done(function(html) {
      $("#router").html(html);
    });
  }

  init();
  window.onhashchange = init;
});
