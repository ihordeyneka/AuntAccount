/// <reference path="../../typings/tsd.d.ts"/>

import {Injectable, ElementRef} from 'angular2/core';

declare var jQuery:JQueryStatic;

@Injectable()
export class ValidationService {
  element: ElementRef;

  constructor() {
  }

  public init(element: ElementRef) {
    this.element = element;

    jQuery(this.element.nativeElement).find(".aa-required .mdl-textfield__input").blur(function (){
      if( !this.value ){
        jQuery(this).prop('required', true);
        jQuery(this).parent().addClass('is-invalid');
      }
    });
  }

  public isValid() : boolean {
    var el = jQuery(this.element.nativeElement);
    el.find(".aa-required.mdl-textfield").children(".mdl-textfield__input").prop("required", true);
    el.find(".mdl-textfield__input").blur();
    return el.find(".is-invalid").length == 0;
  }
}
