define(["fileinput", "../core/globals"], function(fileinputControl, globals) {
  return function(element, settings) {
    var self = {};
    settings = settings || {};

    self.element = element;

    var html =
      '<div class="input-group form-group message-input">\
        <input type="text" class="form-control aa-new-reply-input" placeholder="Type your reply here">\
        <span class="input-group-btn">\
          <button class="btn btn-default aa-send-reply-button" type="button" data-toggle="tooltip" title="Reply">\
            <i class="fa fa-paper-plane"></i>\
          </button>\
          <input type="file" class="form-control input aa-message-upload-picture" placeholder="Upload" />\
        </span>\
      </div>';

    element.html(html);

    self.input = self.element.find(".aa-new-reply-input");
    self.button = self.element.find(".aa-send-reply-button");
    self.uploader = self.element.find(".aa-message-upload-picture");
    self.purge = function() {
      self.input.val("").blur().focus(); //clear input and focus it
    };

    if (!settings.uploadUrl) {
      self.uploader.hide();
    } else {
      self.uploader.fileinput({
        browseLabel: '',
        browseIcon: '<i class="fa fa-picture-o"></i>',
        showUpload: false,
        showRemove: false,
        showPreview: false,
        uploadUrl: settings.uploadUrl,
        uploadAsync: false,
        layoutTemplates: {
          progress: '', //hide progress
        },
        maxFileCount: 1,
        allowedFileExtensions: ["jpg", "jpeg", "bmp", "gif", "png"]
      }).on("filebatchselected", function(event, files) {
        // trigger upload method immediately after files are selected
        globals.loading($('body'), true);
        self.uploader.fileinput("upload");
      }).on("filebatchuploadcomplete", function(event, data) {
        globals.loading($('body'), false);
      }).on("filebatchuploadsuccess", function(event, data) {
        self.element.trigger("uploadsuccess", data);
      }).on("filebatchuploaderror", function(event, data) {
        self.element.trigger("uploaderror", data);
      });
    }

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
