/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive} from 'angular2/core';
import {BaseView} from './base/baseView';
import {HomeTile} from './controls/homeTile';

@Component({
  selector: 'home'
})
@View({
  directives: [HomeTile],
  template: `
    <div class="mdl-grid">
      <div class="mdl-cell mdl-cell--6-col mdl-cell--8-col-tablet">
        <homeTile [header]="'Wedding'"
                  [redirectUrl]="'http://www.google.com.ua'"
                  [containerClass]="'aa-wedding-card'"
                  [supportingText]="'Plan and manage expenses for the happiest day of your life...'">
        </homeTile>
      </div>
      <div class="mdl-cell mdl-cell--6-col mdl-cell--8-col-tablet">
        <homeTile [header]="'Trip'"
                  [redirectUrl]="'http://www.google.com.ua'"
                  [containerClass]="'aa-trip-card'"
                  [supportingText]="'Everything you need to plan your new journey right on your hand...'">
        </homeTile>
      </div>
      <div class="mdl-cell mdl-cell--6-col mdl-cell--8-col-tablet">
        <homeTile [header]="'House'"
                  [redirectUrl]="'http://www.google.com.ua'"
                  [containerClass]="'aa-house-card'"
                  [supportingText]="'Building a house or started doing some little refurnishment? You will need some help...'">
        </homeTile>
      </div>
      <div class="mdl-cell mdl-cell--6-col mdl-cell--8-col-tablet">
        <homeTile [header]="'Custom'"
                  [redirectUrl]="'http://www.google.com.ua'"
                  [containerClass]="'aa-custom-card'"
                  [supportingText]="'None of the default options suit your needs? Not a problem! Create your custom budget...'">
        </homeTile>
      </div>
    </div>
  `
})
export class Home extends BaseView {

}
