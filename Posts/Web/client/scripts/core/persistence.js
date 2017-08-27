define([], function() {
  var self = {};

  var unescapeQuotes = function(val) {
    return val && val.replace(/("|')/g, '');
  };

  // abstract storing of session data
  self.persistData = function(key, val) {
    if (window.localStorage)
      window.localStorage.setItem(key, val);
  };

  // abstract reading of session data
  self.retrieveData = function(key) {
    var val = null;

    if (window.localStorage)
      val = window.localStorage.getItem(key);

    // if value is a simple string, the parser will fail. in that case, simply
    // unescape the quotes and return the string.
    try {
      // return parsed json response
      return $.parseJSON(val);
    } catch (err) {
      // unescape quotes
      return unescapeQuotes(val);
    }
  };

  // abstract deletion of session data
  self.deleteData = function(key) {
    if (window.localStorage)
      window.localStorage.removeItem(key);
  };

  return self;
});
