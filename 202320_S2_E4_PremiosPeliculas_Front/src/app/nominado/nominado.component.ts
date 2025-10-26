import { Component, OnInit } from '@angular/core';
import { Nominado } from './nominado';

@Component({
  selector: 'app-nominado',
  templateUrl: './nominado.component.html',
  styleUrls: ['./nominado.component.css']
})
export class NominadoComponent implements OnInit {

  private nominados: Array<Nominado> = [];

  constructor() { }

  ngOnInit() {
  }

}
