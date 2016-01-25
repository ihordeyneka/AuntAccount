/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive, NgIf} from 'angular2/angular2';
import {FORM_BINDINGS, FORM_DIRECTIVES, FormBuilder, Validators} from 'angular2/angular2';

@Component({
  selector: 'expense',
  viewBindings: [FORM_BINDINGS]
})
@View({
  directives: [FORM_DIRECTIVES],
  template: `
  <div class="expense-grid mdl-grid">
    <div class="mdl-cell mdl-cell--4-col"></div>
    <div class="mdl-cell mdl-cell--4-col">
      <form [ng-form-model]="expenseForm" (submit)="addExpense()">
        <div class="mdl-textfield mdl-js-textfield">
          <input ng-control="amount" class="mdl-textfield__input" type="text" id="amount">
          <label class="mdl-textfield__label" for="amount">Amount...</label>
        </div>
        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect">Add Expense</button>
      </form>
    </div>
    <div class="mdl-cell mdl-cell--4-col"></div>
  </div>
  `
})
export class Expense {
  expenseForm: any;

  constructor(fb: FormBuilder){
    this.expenseForm = fb.group({
      amount: ["", Validators.required]
    });
  }

  addExpense() {
    console.log('expense added!');
  }
}
