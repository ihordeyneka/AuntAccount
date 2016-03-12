/// <reference path="../typings/tsd.d.ts" />


import {bootstrap} from 'angular2/platform/browser';
import {ROUTER_BINDINGS} from 'angular2/router';
import {HTTP_BINDINGS} from 'angular2/http';
import {ValidationService} from './services/validationService'

import {App} from './components/app';

import {Observable, Subject, ReplaySubject} from 'rxjs';

bootstrap(App, [ROUTER_BINDINGS, HTTP_BINDINGS, ValidationService]);
