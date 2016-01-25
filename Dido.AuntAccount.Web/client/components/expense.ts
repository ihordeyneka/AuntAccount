/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive, NgIf} from 'angular2/angular2';
import {FORM_BINDINGS, FORM_DIRECTIVES, FormBuilder} from 'angular2/angular2';

@Component({
  selector: 'expense',
  viewBindings: [FORM_BINDINGS]
})
@View({
  template: `
  <div class="expense-grid mdl-grid">
    <div class="mdl-cell mdl-cell--4-col"></div>
    <div class="mdl-cell mdl-cell--4-col">
      <form [ng-form-model]="expenseForm" (submit)="addExpense()">
        <input ng-control="stock" class="mdl-textfield__input" type="text" placeholder="Add Stock" />
      </form>
    </div>
    <div class="mdl-cell mdl-cell--4-col"></div>
  </div>
  `
})
export class Expense {
  expenseForm: any;

  constructor(){
    let builder = new FormBuilder();
    this.expenseForm = builder.group({

    });
  }

  addExpense() {
    console.log('expense added!');
  }
}
