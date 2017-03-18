exports.init = function(router) {

  var mockApiRoot = '/api/mock';

  //api
  router.post(mockApiRoot + '/posts/upload', function(req, res) {
    res.statusCode = 200;
    res.send('OK');
  });

  router.post(mockApiRoot + '/posts', function(req, res) {
    var result = { id: 0 };

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/posts/:post/offers', function(req, res) {
    var result = [
      { id: 1, time: "03-02-2017", supplier: { id: 1, firstName: "Jack", lastName: "Shephard" }, replyCount: 0 },
      { id: 2, time: "03-02-2017", supplier: { id: 2, firstName: "Kate", lastName: "Austen" }, replyCount: 1 },
      { id: 3, time: "03-02-2017", supplier: { id: 3, firstName: "John", lastName: "Locke" }, replyCount: 1 },
      { id: 4, time: "03-02-2017", supplier: { id: 4, firstName: "Dharma", lastName: "Initiative" }, replyCount: 1 }
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/offers/:offer/messages', function(req, res) {
    var result = [
      { id: 1, sender: { id: 1, firstName: "Jack", lastName: "Shephard" }, creationDate: "03-02-2017 12:05", description: "I've got some food for you. Fruits, fish, even boar." },
      { id: 2, sender: { id: 8, firstName: "Hugo", lastName: "Reyes" }, creationDate: "03-02-2017 12:37", description: "Awesome, I love fish." },
      { id: 3, sender: { id: 1, firstName: "Jack", lastName: "Shephard" }, creationDate: "03-02-2017 15:09", description: "Sorry, I had to go. So?" },
      { id: 4, sender: { id: 1, firstName: "Jack", lastName: "Shephard" }, creationDate: "03-02-2017 16:37", description: "Are you still interested?" }
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/users/:user/posts', function(req, res) {
    var result = [
      { id: 1, creationDate: "15-12-2016", offerCount: 5, newMessages: true, postTags: [{"id":1,"tag":"Food"}, {"id":2,"tag":"Gamburger"}], description: "We've just survived a plane crash<br/>We need to find something to eat..." },
      { id: 2, creationDate: "29-01-2017", offerCount: 0, newMessages: false, postTags: [{"id":3,"tag":"Shelter"}], description: "Looks like we're stuck here." },
      { id: 3, creationDate: "03-02-2017", offerCount: 1, newMessages: false, postTags: [{"id":4,"tag":"Gun"}, {"id":5,"tag":"AK-47"}], description: "We need to defend ourselves against the Others<br/>Beretta will also work." },
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/tags/:query', function(req, res) {
    var result = [
      "Shoes",
      "Boots",
      "Socks",
      "Trousers",
      "Jeans",
      "Shorts",
      "Pants",
      "Belt",
      "Skirt",
      "Shirt",
      "T-Shirt",
      "Sweater",
      "Polo",
      "Hoodie",
      "Jacket",
      "Watch",
      "Glasses",
      "Scarf",
      "Hat",
      "Cap"
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/locations/:query', function(req, res) {
    var result = [
      {id: 1, type: 3, name: "Lviv"},
      {id: 2, type: 3, name: "New York"},
      {id: 3, type: 4, name: "KingCross Leopolis Lviv", location: { lat: 49.773807, lng: 24.011436} },
      {id: 4, type: 3, name: "Rome"},
      {id: 5, type: 3, name: "Paris"},
      {id: 6, type: 1, name: "Ukraine"},
      {id: 7, type: 1, name: "New Zeland"},
      {id: 8, type: 1, name: "Portugal"},
      {id: 9, type: 1, name: "Romania"},
      {id: 10, type: 4, name: "Victoria Gardens", location: { lat: 49.807049, lng: 23.978466} }
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  //authentication
  router.post(mockApiRoot + '/users', function(req, res) {
    res.statusCode = 200;
    res.send('OK');
  });

  var signIn = function(req, res) {
    var result = {
      userId: 1,
      name: "Jack Shephard"
    }

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('access-token', 'ACCESS-TOKEN-SECRET');
    res.setHeader('refresh-token', 'REFRESH-TOKEN-SECRET');
    res.send(JSON.stringify(result));
  };

  router.post(mockApiRoot + '/token', signIn);

  router.post(mockApiRoot + '/authcode/provide', signIn);

  router.get(mockApiRoot + "/auth/fb*", function(req, res) {
    res.redirect("/authcode?provider=fb&authcode=BBB")
  })

  router.get(mockApiRoot + "/auth/google*", function(req, res) {
    res.redirect("/authcode?provider=google&authcode=CCC")
  })

  router.post(mockApiRoot + '/token/refresh', function(req, res) {
    res.statusCode = 200;
    res.setHeader('access-token', 'NEW-ACCESS-TOKEN-SECRET');
    res.setHeader('refresh-token', 'NEW-REFRESH-TOKEN-SECRET');
    res.send('OK');
  });

  router.delete(mockApiRoot + '/auth/sign_out', function(req, res) {
    var data = req.get('Authentication');
    res.statusCode = data == "Bearer NEW-ACCESS-TOKEN-SECRET" ? 200 : 401;
    res.send(data);
  });
}
