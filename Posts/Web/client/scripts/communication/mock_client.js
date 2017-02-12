define(function() {
  var self = this;

  self.attachmentUploadUrl = "/test/upload";

  self.getTags = function(parameters, callback) {
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
    callback({
      success: true,
      data: result
    });
  }

  self.getLocations = function(parameters, callback) {
    //type: 1-country, 2-province, 3-city, 4-place
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
    callback({
      success: true,
      data: result
    });
  }

  self.savePost = function(parameters, callback) {
    console.log(post);
    callback({
      success: true,
      data: { id: 0 }
    });
  }

  self.signUp = function(parameters, callback) {
    console.log(model);
    callback({
      success: true
    });
  }

  self.getMyRecentPosts = function(parameters, callback) {
    var result = [
      { postId: 1, time: "15-12-2016", conversations: 5, newMessages: true, tags: ["Food", "Gamburger"], description: "We've just survived a plane crash<br/>We need to find something to eat..." },
      { postId: 2, time: "29-01-2017", conversations: 0, newMessages: false, tags: ["Shelter"], description: "Looks like we're stuck here." },
      { postId: 3, time: "03-02-2017", conversations: 1, newMessages: false, tags: ["Gun", "AK-47"], description: "We need to defend ourselves against the Others<br/>Beretta will also work." },
    ];
    callback({
      success: true,
      data: result
    });
  }

  self.getPostConversations = function(parameters, callback) {
    var result = [
      { conversationId: 1, time: "03-02-2017", supplier: { id: 1, name: "Jack Shephard" }, newReplies: 0 },
      { conversationId: 2, time: "03-02-2017", supplier: { id: 2, name: "Kate Austen" }, newReplies: 1 },
      { conversationId: 3, time: "03-02-2017", supplier: { id: 3, name: "John Locke" }, newReplies: 1 },
      { conversationId: 4, time: "03-02-2017", supplier: { id: 4, name: "Dharma Initiative" }, newReplies: 1 }
    ];
    callback({
      success: true,
      data: result
    });
  }

  self.getConversationReplies = function(parameters, callback) {
    var result = [
      { replyId: 1, author: { id: 1, name: "Jack Shephard", isMe: false }, time: "03-02-2017 12:05", content: "I've got some food for you. Fruits, fish, even boar." },
      { replyId: 2, author: { id: 8, name: "Hugo Reyes", isMe: true }, time: "03-02-2017 12:37", content: "Awesome, I love fish." },
      { replyId: 3, author: { id: 1, name: "Jack Shephard", isMe: false }, time: "03-02-2017 15:09", content: "Sorry, I had to go. So?" },
      { replyId: 4, author: { id: 1, name: "Jack Shephard", isMe: false }, time: "03-02-2017 16:37", content: "Are you still interested?" }
    ];
    callback({
      success: true,
      data: result
    });  }

  return self;
});
