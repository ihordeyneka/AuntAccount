/// <reference path="../../../typings/tsd.d.ts" />

import {OnActivate, ComponentInstruction} from 'angular2/router';

export class BaseView implements OnActivate {

  onActivate(next: ComponentInstruction, prev: ComponentInstruction) {
    componentHandler.upgradeDom();
  }

}
