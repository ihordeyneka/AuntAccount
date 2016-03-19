/// <reference path="../../typings/tsd.d.ts"/>

import {Injectable} from 'angular2/core';

@Injectable()
export class ConfigService {
  public rootUrl: string;

  constructor() {
    this.rootUrl = "http://localhost:4000/api";
  }
}
