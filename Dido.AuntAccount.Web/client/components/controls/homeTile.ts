/// <reference path="../../../typings/tsd.d.ts" />

import {Component, View, Directive, Input} from 'angular2/core'

@Component({
  selector: 'homeTile'
})
@View({
  template: `
    <div class="aa-card {{ containerClass }} mdl-card mdl-shadow--2dp">
      <div class="mdl-card__title">
        <h2 class="mdl-card__title-text">{{ header }}</h2>
      </div>
      <div class="mdl-card__supporting-text">
        {{ supportingText }}
      </div>
      <div class="mdl-card__actions mdl-card--border">
        <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" href="{{ redirectUrl }}">
          Get Started
        </a>
      </div>
    </div>
  `
})
export class HomeTile {
  @Input() header: string;
  @Input() supportingText: string;
  @Input() redirectUrl: string;
  @Input() containerClass: string;

  constructor() {}
}
