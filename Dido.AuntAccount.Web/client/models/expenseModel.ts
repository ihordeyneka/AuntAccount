/// <reference path="../../typings/tsd.d.ts" />

import {Globals} from '../common/globals';

export class ExpenseModel {

  amount: number = null;
  expenseDate: Date = new Date();
  category: string = "";
  tags: Array<string> = [];
  notes: string = "";

  get expenseDateStr() {
    return Globals.DateToStr(this.expenseDate);
  }

  constructor() {
    this.amount = 10;
    this.category = "Beverages";
    this.tags.push("Glory Cafe");
    this.notes = "morning coffee";
  }
}
