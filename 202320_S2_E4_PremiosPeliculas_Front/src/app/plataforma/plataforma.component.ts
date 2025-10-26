import { Component, OnInit } from '@angular/core';
import { Plataforma } from './plataforma';

@Component({
  selector: 'app-plataforma',
  templateUrl: './plataforma.component.html',
  styleUrls: ['./plataforma.component.css']
})
export class PlataformaComponent implements OnInit {

  plataformas: Array<Plataforma> = [];

  constructor() { }

  ngOnInit() {
  }

}
