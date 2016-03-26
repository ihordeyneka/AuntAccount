/// <reference path="../../typings/tsd.d.ts" />

import {Component, View} from 'angular2/core';
import {BaseView} from './base/baseView';

@Component({
  selector: 'home'
})
@View({
  template: `
    <div class="mdl-grid">
      HOME
    </div>
  `
})
export class Home extends BaseView {

}
