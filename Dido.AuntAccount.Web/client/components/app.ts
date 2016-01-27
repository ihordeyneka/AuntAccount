/// <reference path="../../typings/tsd.d.ts" />

import {Component, View} from 'angular2/angular2';
import {RouteConfig, Router, RouterOutlet, RouterLink, CanActivate, OnActivate, ComponentInstruction} from 'angular2/router';

import {Search} from './search';
import {Expense} from './expense';

@RouteConfig([
  {path: '/', as: 'search', component: Search},
  {path: '/expense', as: 'expense', component: Expense}
])

@Component({
  selector: 'app'
})
@View({
  directives: [RouterOutlet, RouterLink],
  template: `
  <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
      <div class="mdl-layout__header-row">
        <span class="mdl-layout-title">AuntAccount</span>
        <div class="mdl-layout-spacer"></div>
        <nav class="mdl-navigation mdl-layout--large-screen-only">
          <a class="mdl-navigation__link" [router-link]="['/search']">Search</a>
          <a class="mdl-navigation__link" [router-link]="['/expense']">Expense</a>
        </nav>
      </div>
    </header>
    <main class="mdl-layout__content" style="padding: 20px;">
      <router-outlet></router-outlet>
    </main>
  </div>
  `
})
export class App {
}
