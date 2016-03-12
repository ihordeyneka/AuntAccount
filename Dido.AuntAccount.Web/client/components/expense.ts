/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive} from 'angular2/core';
import {NgIf, NgFor, Control} from 'angular2/common'
import {FORM_BINDINGS, FORM_DIRECTIVES, FormBuilder, Validators} from 'angular2/common';
import {ChipCollection} from './controls/chipCollection';
import {BaseView} from './base/baseView';
import {Globals} from './common/globals';

@Component({
  selector: 'expense',
  viewBindings: [FORM_BINDINGS]
})
@View({
  directives: [FORM_DIRECTIVES, NgIf, NgFor, ChipCollection],
  template: `
  <div class="expense-grid mdl-grid">
    <div class="mdl-cell mdl-cell--4-col"></div>
    <div class="mdl-cell mdl-cell--4-col">
      <form [ngFormModel]="expenseForm" (submit)="addExpense()">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="amount" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="amount">
          <label class="mdl-textfield__label" for="amount">Amount...</label>
          <span class="mdl-textfield__error">Amount must be a number!</span>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="expenseDateStr" class="mdl-textfield__input" type="date" id="expenseDateStr">
          <label class="mdl-textfield__label aa-mdl-floated" for="expenseDateStr">Date...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <input ng-control="category" class="mdl-textfield__input" type="text" id="category">
          <label class="mdl-textfield__label" for="category">Category...</label>
        </div>
        <chipCollection [chips]="tags" [label]="'Tags...'"></chipCollection>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <textarea ng-control="notes" class="mdl-textfield__input" type="text" rows= "3" id="notes"></textarea>
          <label class="mdl-textfield__label" for="notes">Notes...</label>
        </div>
        <button type="submit" class="mdl-button mdl-button--colored mdl-button--raised mdl-js-button mdl-js-ripple-effect">Add Expense</button>
      </form>
    </div>
    <div class="mdl-cell mdl-cell--4-col"></div>
  </div>
  `
})
export class Expense extends BaseView {
  expenseForm: any;
  amount: number = null;
  expenseDate: Date = new Date();
  category: string = "";
  tags: Array<string> = [];
  notes: string = "";
  get expenseDateStr() {
    return Globals.DateToStr(this.expenseDate);
  }

  constructor(fb: FormBuilder){
    super();
    this.expenseForm = fb.group({
      amount: [this.amount, Validators.required],
      expenseDateStr: [this.expenseDateStr],
      category: [this.category],
      tags: [this.tags],
      notes: [this.notes]
    });
  }

  addExpense() {
    console.log('expense added!');
  }

}
