$(function(){
  $(document).click(".router-link", function (e) {
    e.preventDefault();
    var href = $(e.target).attr("href");
    $.ajax({
      url: href
    }).done(function(html) {
      $("#router").html(html);
    });
  });
});
