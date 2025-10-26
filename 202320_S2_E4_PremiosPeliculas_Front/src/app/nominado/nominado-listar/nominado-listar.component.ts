import { Component, OnInit } from '@angular/core';
import { Nominado } from '../nominado';
import { NominadoService } from '../nominado.service';

@Component({
  selector: 'app-nominado-listar',
  templateUrl: './nominado-listar.component.html',
  styleUrls: ['./nominado-listar.component.css']
})
export class NominadoListarComponent implements OnInit {

  selectedNominado!: Nominado;
  selected: Boolean = false;

  onSelected(Nominado: Nominado): void {
    this.selected = true;
    this.selectedNominado = Nominado;
  }

  nominados: Array<Nominado> = [];

  constructor(private NominadoService: NominadoService) { }

  getNominados(): void {
    this.NominadoService.nominados.subscribe((nominados) =>
      {this.nominados = nominados});
  }

  ngOnInit() {
    this.getNominados();
  }

}
