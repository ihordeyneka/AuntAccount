$(function(){

  var init = function () {
    var page = location.hash != "" ? location.hash : "#home";
    var element = $("a[href='" + page + "']");
    if (element != null) {
        navigate(element);
    }
  }

  var navigate = function(element) {
    $(".router-link.active").removeClass("active");
    element.addClass("active");
    var href = element.data("url");

    var toggle = $(".navbar-toggle");
    if (toggle.is(":visible") && $(".navbar-collapse").hasClass("in"))
      toggle.click();

    $.ajax({
      url: href
    }).done(function(html) {
      $("#router").html(html);
    });
  }

  init();
  window.onhashchange = init;
});
