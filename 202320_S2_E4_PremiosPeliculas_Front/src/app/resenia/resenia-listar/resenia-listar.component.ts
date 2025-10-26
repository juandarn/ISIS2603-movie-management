import { Component, Input, OnInit } from '@angular/core';
import { Resenia } from '../resenia';
import { ReseniaService } from '../resenia.service';


@Component({
  selector: 'app-resenia-listar',
  templateUrl: './resenia-listar.component.html',
  styleUrls: ['./resenia-listar.component.css']
})
export class ReseniaListarComponent implements OnInit {

  
  @Input() resenias: Array<Resenia> =[];
  constructor(private ReseniaService: ReseniaService) { }

  selectedResenia!: Resenia;
  selected: Boolean = false;
  p: number = 1; 
  itemsPerPage: number = 5;

  getResenias(): void{
    this.ReseniaService.resenias.subscribe((resenias) =>{
     this.resenias = resenias;
    });
  }

  getReseniasAlfabeticamente(): void {
    this.ReseniaService.getReseniasAlfabeticamente().subscribe({
      next: apiData => this.resenias = apiData,
    });
  }

  getReseniasAntiAlfabeticamente(): void {
    this.ReseniaService.getReseniasAntiAlfabeticamente().subscribe({
      next: apiData => this.resenias = apiData,
    });
  }

  onSelected(Resenia: Resenia): void {
    this.selected = true;
    this.selectedResenia = Resenia;
  }



  ngOnInit() {  
    this.resenias
  }

}
