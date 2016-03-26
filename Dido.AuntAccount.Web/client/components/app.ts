/// <reference path="../../typings/tsd.d.ts" />

import {Component, View} from 'angular2/core';
import {RouteConfig, Router, RouterOutlet, RouterLink, CanActivate, OnActivate, ComponentInstruction} from 'angular2/router';

import {Home} from './home';
import {Search} from './search';
import {Expense} from './expense';

@RouteConfig([
  {path: '/', as: 'Home', component: Home},
  {path: '/Search', as: 'Search', component: Search},
  {path: '/expense', as: 'Expense', component: Expense}
])

@Component({
  selector: 'app'
})
@View({
  directives: [RouterOutlet, RouterLink],
  template: `
  <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <div class="aa-header mdl-layout__header mdl-layout__header--waterfall">
      <div class="mdl-layout__header-row">
        <span class="aa-title mdl-layout-title">
          <a [routerLink]="['/Home']" class="aa-home-link"><img class="aa-logo-image" src="client/images/android-logo.png"></a>
        </span>
        <!-- Add spacer, to align navigation to the right in desktop -->
        <div class="aa-header-spacer mdl-layout-spacer"></div>
        <div class="aa-search-box mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-textfield--full-width">
          <label class="mdl-button mdl-js-button mdl-button--icon" for="search-field">
            <i class="material-icons">search</i>
          </label>
          <div class="mdl-textfield__expandable-holder">
            <input class="mdl-textfield__input" type="text" id="search-field">
          </div>
        </div>
        <!-- Navigation -->
        <div class="aa-navigation-container">
          <nav class="aa-navigation mdl-navigation">
            <a class="mdl-navigation__link mdl-typography--text-uppercase" [routerLink]="['/Search']">Search</a>
            <a class="mdl-navigation__link mdl-typography--text-uppercase" [routerLink]="['/Expense']">Expense</a>
          </nav>
        </div>
        <span class="aa-mobile-title mdl-layout-title">
          <img class="aa-logo-image" src="client/images/android-logo.png">
        </span>
      </div>
    </div>

    <div class="aa-drawer mdl-layout__drawer">
      <span class="mdl-layout-title">
        <img class="aa-logo-image" src="client/images/android-logo-white.png">
      </span>
      <nav class="mdl-navigation">
        <a class="mdl-navigation__link" [routerLink]="['/Search']">Search</a>
        <a class="mdl-navigation__link" [routerLink]="['/Expense']">Expense</a>
      </nav>
    </div>

    <div class="aa-content mdl-layout__content">
      <router-outlet></router-outlet>
    </div>
  </div>
  `
})
export class App {
}
