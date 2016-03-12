/// <reference path="../../typings/tsd.d.ts" />

import {Component, View} from 'angular2/core';
import {BaseView} from './base/baseView';

@Component({
  selector: 'search'
})
@View({
  template: `
    <div class="mdl-grid">
      SEARCH PAGE
    </div>
  `
})
export class Search extends BaseView {

}
