import config from "../core/config"
import didoauth from "../core/didoauth"
import $ from "jquery"
import "jquery-deparam"

$.didoauth.configure({
  apiUrl: config.apiRoot,
  provideAuthCodePath: "/token/client",
  signIn: function () {
    window.location = "/";
  },
  error: function () {
    $("#status").html('<div class="alert alert-danger" role="alert">Authentication failed. <br/><a href="/">Go to home.</a></div>');
  }
});
$.didoauth.provideAuthCode($.deparam(window.location.search.slice(1)));
