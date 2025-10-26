import { Component, OnInit } from '@angular/core';
import { GanadorDTO } from '../ganador';
import { GanadorService } from '../ganador.service';

@Component({
  selector: 'app-ganador-listar',
  templateUrl: './ganador-listar.component.html',
  styleUrls: ['./ganador-listar.component.css']
})
export class GanadorListarComponent implements OnInit {
 
  selectdGanador!: GanadorDTO;
  selected: Boolean = false;

  onSelected(GanadorDTO: GanadorDTO): void {
    this.selected = true;
    this.selectdGanador = GanadorDTO;
  }

  ganadores: Array<GanadorDTO>=[];

  constructor(private GanadorService: GanadorService) { }

  getGanadores(): void{
    this.GanadorService.ganadores.subscribe((ganadores) =>
     {this.ganadores = ganadores});
  }




  ngOnInit() {
    this.getGanadores();
  }

}
