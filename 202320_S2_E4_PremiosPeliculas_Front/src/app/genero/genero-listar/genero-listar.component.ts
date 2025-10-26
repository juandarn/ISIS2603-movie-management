import { GeneroDetail } from './../generoDetail';
import { Component, OnInit } from '@angular/core';
import { GeneroService } from './genero.service';
import { GeneroDTO } from '../genero';

@Component({
  selector: 'app-genero-listar',
  templateUrl: './genero-listar.component.html',
  styleUrls: ['./genero-listar.component.css']
})
export class GeneroListarComponent implements OnInit {


  generos: Array<GeneroDetail>=[];
  generoSeleccionado!: GeneroDetail;
  selected: boolean= false;

  constructor(private generoService:GeneroService ) { }

  getGeneros(): void{
    this.generoService.getGeneros().subscribe((generos) => {this.generos = generos;})
  }

  onSelected(_t11: GeneroDetail) {
    this.generoSeleccionado = _t11;
    this.selected = true;
  }

  ngOnInit() {
    this.getGeneros();
  }

}
