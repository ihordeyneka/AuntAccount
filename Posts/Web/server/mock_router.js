exports.init = function(router) {

  var mockApiRoot = '/api/mock';

  //api
  router.post(mockApiRoot + '/posts/upload', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send('{}');
  });

  router.post(mockApiRoot + '/posts', function(req, res) {
    var result = { id: 0 };

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/posts/:post/offers', function(req, res) {
    var result = [
      { id: 1, time: "03-02-2017", seller: { id: 1, firstName: "Jack", lastName: "Shephard" }, replyCount: 0 },
      { id: 2, time: "03-02-2017", seller: { id: 2, firstName: "Kate", lastName: "Austen" }, replyCount: 1 },
      { id: 3, time: "03-02-2017", seller: { id: 3, firstName: "John", lastName: "Locke" }, replyCount: 1 },
      { id: 4, time: "03-02-2017", seller: { id: 4, firstName: "Dharma", lastName: "Initiative" }, replyCount: 1 }
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

  router.post(mockApiRoot + '/offers/:offer/messages', function(req, res) {
    var result = {};
    result.description = req.body.description;
    result.id = 5;
    result.sender = { id: 8, firstName: "Hugo", lastName: "Reyes" };
    result.creationDate = "03-05-2017 12:37";

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/users/:user/posts', function(req, res) {
    var result = [
      { id: 1, creationDate: "15-12-2016", offerCount: 5, newMessages: true, postTags: "Food, Gamburger", description: "We've just survived a plane crash<br/>We need to find something to eat..." },
      { id: 2, creationDate: "29-01-2017", offerCount: 0, newMessages: false, postTags: "Shelter", description: "Looks like we're stuck here." },
      { id: 3, creationDate: "03-02-2017", offerCount: 1, newMessages: false, postTags: "Gun, AK-47", description: "We need to defend ourselves against the Others<br/>Beretta will also work." },
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/users/:user/sellers', function(req, res) {
    var result = [
      { id: 1, name: "BigBurger" },
      { id: 2, name: "Gelato" }
    ];

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.get(mockApiRoot + '/sellers/:seller', function(req, res) {
    var result = {
      id: 1,
      name: "BigBurger",
      phone: "+380681857379",
      website: "http://www.google.com.ua",
      tags: ["Tag1", "Jeans"],
      location: "Lviv, Dudajeva 11",
      picture: "data:image/png;base64,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",
      rate: 2
    };

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.delete(mockApiRoot + '/sellers/:id', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send("{}");
  });

  router.post(mockApiRoot + '/sellers', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send("{}");
  });

  router.get(mockApiRoot + '/tag/:query', function(req, res) {
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

  //authentication
  router.post(mockApiRoot + '/users', function(req, res) {
    res.statusCode = 200;
    res.send('OK');
  });

  router.get(mockApiRoot + '/users/:userId', function(req, res) {
    var result = {
      id: 1,
      firstName: "Jack",
      lastName: "Shephard",
      sellers: [{ id: 1, name: "BigBurger"}],
      profilePicture: "data:image/png;base64,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"
    };
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.post(mockApiRoot + '/users/profile', function(req, res) {
    var result = { id: 0, first: 'dummy', last: 'user' };

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send(JSON.stringify(result));
  });

  router.post(mockApiRoot + '/users/picture', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send('{}');
  });

  router.post(mockApiRoot + '/users/password', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send("{}");
  });

  router.post(mockApiRoot + '/users/passwordReset', function(req, res) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.send("{}");
  });

  var signIn = function(req, res) {
    var result = {
      id: 1,
      firstName: "Jack",
      lastName: "Shephard"
    }

    res.statusCode = 200;
    res.setHeader('Content-Type', 'application/json');
    res.setHeader('access-token', 'ACCESS-TOKEN-SECRET');
    res.setHeader('refresh-token', 'REFRESH-TOKEN-SECRET');
    res.send(JSON.stringify(result));
  };

  router.post(mockApiRoot + '/token', signIn);

  router.post(mockApiRoot + '/token/client', signIn);

  router.get(mockApiRoot + "/auth/fb*", function(req, res) {
    res.redirect("/authcode?provider=fb&code=BBB")
  })

  router.get(mockApiRoot + "/auth/google*", function(req, res) {
    res.redirect("/authcode?provider=google&code=CCC")
  })

  router.post(mockApiRoot + '/token/refresh', function(req, res) {
    res.statusCode = 200;
    res.setHeader('access-token', 'NEW-ACCESS-TOKEN-SECRET');
    res.setHeader('refresh-token', 'NEW-REFRESH-TOKEN-SECRET');
    res.send('OK');
  });

  router.delete(mockApiRoot + '/auth/sign_out', function(req, res) {
    var data = req.get('Authorization');
    res.statusCode = data == "Bearer NEW-ACCESS-TOKEN-SECRET" ? 200 : 401;
    res.send(data);
  });
}
