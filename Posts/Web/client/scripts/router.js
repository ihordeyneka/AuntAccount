$(function(){

  var init = function () {
    if (location.hash != "") {
      var element = $("a[href='" + location.hash + "']");
      if (element != null) {
          navigate(element);
      }
    } else {
      $(".aa-home-link").addClass("active");
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

  $(document).on("click", ".router-link", function (e) {
    navigate($(e.target));
  });
});
