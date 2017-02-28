define([], function() {
  var config = {};

  //config.apiRoot = "http://192.168.1.111:8080/api/service";
  config.apiRoot = "http://localhost:8282/api/mock";
  config.clientSecret = "CLIENT_SECRET"; //TODO: update with real value
  config.clientId = "CLIENT_ID"; //TODO: update with real value

  return config;
});
