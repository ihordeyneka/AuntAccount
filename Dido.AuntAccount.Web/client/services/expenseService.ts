/// <reference path="../../typings/tsd.d.ts"/>

import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {ConfigService} from './configService';
import {ExpenseModel} from '../models/expenseModel';

@Injectable()
export class ExpenseService {
  http: Http;
  rootUrl: string;

  constructor(http: Http, configService: ConfigService) {
    this.http = http;
    this.rootUrl = configService.rootUrl;
  }

  public postExpense(expense: ExpenseModel) {
    alert(this.rootUrl);
    //this.http.post(this.rootUrl + "/expense/post", JSON.stringify(expense));

    //3. fix this.http.post not sending a request in Network Tab
  }
}
