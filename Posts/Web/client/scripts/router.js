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
    var href = element.data("url");
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
