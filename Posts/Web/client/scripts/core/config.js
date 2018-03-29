define([], function() {
  var config = {};

  config.apiRoot = "https://www.didocube.com/api/service";
  //config.apiRoot = "http://localhost:8282/api/mock";
  config.clientSecret = "CLIENT_SECRET"; //TODO: update with real value
  config.clientId = "CLIENT_ID"; //TODO: update with real value

  return config;
});
