/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive, NgIf, Control} from 'angular2/angular2';
import {FORM_BINDINGS, FORM_DIRECTIVES, FormBuilder, Validators} from 'angular2/angular2';
import {BaseView} from './base/baseView';

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
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="amount" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="amount">
          <label class="mdl-textfield__label" for="amount">Amount...</label>
          <span class="mdl-textfield__error">Amount must be a number!</span>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="expenseDate" class="mdl-textfield__input" type="date" id="expenseDate">
          <label class="mdl-textfield__label aa-mdl-date" for="expenseDate">Date...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="category" class="mdl-textfield__input" type="text" id="category">
          <label class="mdl-textfield__label" for="category">Category...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="tags" class="mdl-textfield__input" type="text" id="tags">
          <label class="mdl-textfield__label" for="tags">Tags...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <textarea ng-control="notes" class="mdl-textfield__input" type="text" rows= "3" id="notes"></textarea>
          <label class="mdl-textfield__label" for="notes">Notes...</label>
        </div>
        <button type="submit" class="mdl-button mdl-js-button mdl-button--raised">Add Expense</button>
      </form>
    </div>
    <div class="mdl-cell mdl-cell--4-col"></div>
  </div>
  `
})
export class Expense extends BaseView {
  expenseForm: any;

  constructor(fb: FormBuilder){
    super();
    this.expenseForm = fb.group({
      amount: ["", Validators.required],
      expenseDate: ["", Validators.required],
      category: ["Test Category", Validators.required],
      tags: ["", Validators.required],
      notes: ["", Validators.required]
    });
  }

  addExpense() {
    console.log('expense added!');
  }

}
