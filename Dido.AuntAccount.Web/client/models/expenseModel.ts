/// <reference path="../../typings/tsd.d.ts" />

import {Globals} from '../components/common/globals';

export class ExpenseModel {

  amount: number = null;
  expenseDate: Date = new Date();
  category: string = "";
  tags: Array<string> = [];
  notes: string = "";

  get expenseDateStr() {
    return Globals.DateToStr(this.expenseDate);
  }

}
