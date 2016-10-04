$(function(){

  var init = function () {
    if (location.hash != "") {
      var element = $("a[href='" + location.hash + "']");
      if (element != null) {
          navigate(element);
      }
    }
  }

  var navigate = function(element) {
    $(".router-link.active").removeClass("active");
    element.addClass("active");
    var href = element.data("url");

    var toggle = $(".navbar-toggle");
    if (toggle.is(":visible"))
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
