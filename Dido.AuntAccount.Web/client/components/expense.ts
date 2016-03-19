/// <reference path="../../typings/tsd.d.ts" />

import {Component, View, Directive, ElementRef} from 'angular2/core';
import {NgIf, NgFor, Control} from 'angular2/common'
import {FORM_BINDINGS, FORM_DIRECTIVES, FormBuilder, Validators} from 'angular2/common';
import {ChipCollection} from './controls/chipCollection';
import {ValidationService} from '../services/validationService'
import {ExpenseService} from '../services/expenseService'
import {ExpenseModel} from '../models/expenseModel';
import {BaseView} from './base/baseView';

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
      <form (submit)="addExpense()">
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label aa-required">
          <input [(ngModel)]="model.amount" class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="amount">
          <label class="mdl-textfield__label" for="amount">Amount...</label>
          <span class="mdl-textfield__error">Amount must be a number!</span>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label aa-required">
          <input [(ngModel)]="model.expenseDateStr" class="mdl-textfield__input" type="date" id="expenseDateStr">
          <label class="mdl-textfield__label aa-mdl-floated" for="expenseDateStr">Date...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label aa-required">
          <input [(ngModel)]="model.category" class="mdl-textfield__input" type="text" id="category">
          <label class="mdl-textfield__label" for="category">Category...</label>
        </div>
        <chipCollection [chips]="model.tags" [label]="'Tags...'"></chipCollection>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
          <textarea [(ngModel)]="model.notes" class="mdl-textfield__input" type="text" rows= "3" id="notes"></textarea>
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
  element: ElementRef;
  model: ExpenseModel;
  validationService: ValidationService;
  expenseService: ExpenseService;

  constructor(fb: FormBuilder, element: ElementRef, validationService: ValidationService, expenseService: ExpenseService){
    super();
    this.model = new ExpenseModel();
    this.validationService = validationService;
    this.expenseService = expenseService;
    this.element = element;
  }

  ngOnInit() {
    this.validationService.init(this.element);
  }

  addExpense() {
    if(this.validationService.isValid()){
      this.expenseService.postExpense(this.model);
    }
  }

}
