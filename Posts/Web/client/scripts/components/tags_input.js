define(["../core/config", "tagsinput"], function(config, tagsinputControl) {
  return function(element, notificationArea) {
    var self = {
      notificationArea: notificationArea
    };

    self.element = element;
    self.getTags = function() {
      return self.element.val();
    };
    self.setTags = function(tags) {
      self.element.tagsinput('removeAll');
      for (var i=0; i<tags.length; i++) {
        self.element.tagsinput('add', tags[i]);
      }
    };
    self.setReadonly = function() {
      self.element.attr("disabled", "disabled");
      var tagsinputElement = self.element.siblings(".bootstrap-tagsinput");
      tagsinputElement.addClass("readonly");
      tagsinputElement.find("input").attr("disabled", "disabled");
    };

    element.tagsinput({
      typeahead: {
        afterSelect: function(val) {
          this.$element.val(""); //clear input when tag is added
        },
        source: function(query) {
          var result = [];

          $.ajax({
            url: config.apiRoot + "/tag/" + query,
            dataType: "json",
            async: false
          }).done(function(data) {
            result = data;
          }).fail(function(result) {
            self.notificationArea.error();
          });

          return result;
        }
      }
    });

    var tagsinput = element.data("tagsinput");

    //copy i18n attribute from source input to tagsinput generated input for localization
    tagsinput.$input.attr("data-i18n", element.data("i18n"));

    tagsinput.currentShift = 0;
    var updateTagsPosition = function() {
      var shiftStepPixels = 100;
      var inputRight = tagsinput.$input.position().left + tagsinput.$input.width();
      var containerRight = tagsinput.$container.position().left + tagsinput.$container.width();
      if (inputRight > containerRight) {
        tagsinput.currentShift -= shiftStepPixels;
      } else if (tagsinput.currentShift < 0) {
        var restoreStepsCount = (containerRight - inputRight) / shiftStepPixels;
        tagsinput.currentShift += restoreStepsCount * shiftStepPixels;
      }
      if (tagsinput.currentShift > 0)
      tagsinput.currentShift = 0;
      //update left css property for input and tags within the tagsinput
      tagsinput.$container.children('span.tag, input')
      .css("left", tagsinput.currentShift.toString() + "px");
    }

    element.on("itemAdded", function() {
      updateTagsPosition();
      tagsinput.$input.attr('placeholder', ''); //remove placeholder if at least one tag is added
    });

    element.on("itemRemoved", function() {
      updateTagsPosition();
      if (tagsinput.itemsArray.length === 0)
        tagsinput.$input.attr('placeholder', tagsinput.placeholderText); //restore placeholder if there are no tags again
    });

    tagsinput.$input.keypress(function() {
      updateTagsPosition();
    });

    tagsinput.$input.keydown(function(e) {
      if (e.which === 37 && tagsinput.$input.val() === '') { //LEFT ARROW and input is empty
        e.stopPropagation(); //avoid moving input between tags, see keydown event handler in bootstrap-tagsinput.js
      }
    });

    return self;
  }
});
