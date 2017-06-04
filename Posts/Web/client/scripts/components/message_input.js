define([], function() {
  return function(element, settings) {
    var self = {};

    self.element = element;

    var html =
      '<div class="input-group form-group">\
        <input type="text" class="form-control aa-new-reply-input" placeholder="Type your reply here">\
        <span class="input-group-btn">\
          <button class="btn btn-default aa-send-reply-button" type="button" data-toggle="tooltip" title="Reply">\
            <i class="fa fa-paper-plane"></i>\
          </button>\
        </span>\
      </div>';

    element.html(html);

    self.input = self.element.find(".aa-new-reply-input");
    self.button = self.element.find(".aa-send-reply-button");
    self.purge = function() {
      self.input.val("").blur().focus(); //clear input and focus it
    };

    var sendReply = function() {
      var description = self.input.val();
      if (description) {
        self.element.trigger("replied", { description: description });
      }
    };

    self.button.click(sendReply);

    self.input
      .focus()
      .keypress(function(event) {
        if(event.keyCode == 13) { //Enter Key Press event handler
          sendReply();
        }
      });

    return self;
  }
});
