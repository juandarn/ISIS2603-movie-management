import { Component, OnInit } from '@angular/core';
import { PremioDTO } from '../premio';
import { PremioService } from '../premio.service';
import { PremioDetailDTO } from '../premioDetail';

@Component({
  selector: 'app-premio-listar',
  templateUrl: './premio-listar.component.html',
  styleUrls: ['./premio-listar.component.css']
})
export class PremioListarComponent implements OnInit {
  premioSeleccionado !: PremioDetailDTO;
  selected : boolean = false;
  premios : Array<PremioDetailDTO> = [];
  constructor(private premioService : PremioService) { }

  getpremios(): void{
    this.premioService.premios.subscribe(premios => this.premios = premios);
  }

  onSelected(premio:PremioDetailDTO):void{
    this.selected = true;
    this.premioSeleccionado = premio;
  }

  ngOnInit() {
    this.getpremios();
  }

}
