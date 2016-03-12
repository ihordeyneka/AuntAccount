/// <reference path="../../../typings/tsd.d.ts" />

import {Component, View, Directive, Input, ElementRef, Renderer} from 'angular2/core'
import {NgIf, NgFor} from 'angular2/common'

declare var jQuery:JQueryStatic;

@Component({
  selector: 'chipCollection'
})
@View({
  directives: [NgIf, NgFor],
  template: `
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label aa-chip-input-container">
      <input class="mdl-textfield__input" type="text" id="chip" [(ngModel)]="chip" (keyup.enter)="addChip()">
      <label class="mdl-textfield__label" for="chip">{{ label }}</label>
      <label class="mdl-button mdl-js-button mdl-button--icon mdl-button--colored aa-mdl-input-button">
        <i class="material-icons" (click)="addChip()">add</i>
      </label>
    </div>
    <div *ngIf="chips.length > 0" class="aa-current-chips">
      <span *ngFor="#chip of chips" class="aa-mdl-chip mdl-color--blue">{{ chip }}</span>
    </div>
  `
})
export class ChipCollection {
  @Input() chips: Array<string> = [];
  @Input() label: string;
  chip: string;


  private renderer: Renderer;
  private element: ElementRef;

  constructor(renderer: Renderer, element: ElementRef){
    this.renderer = renderer;
    this.element = element;
  }

  addChip() {
    if(this.chip) {
      this.chips.push(this.chip);
      this.chip = '';

      //hack: removing is-dirty class manually, so the MDL container can float back
      this.renderer.setElementClass(
        jQuery(this.element.nativeElement).find('.aa-chip-input-container').get(0),
        'is-dirty',
        false);
    }
  }

}
