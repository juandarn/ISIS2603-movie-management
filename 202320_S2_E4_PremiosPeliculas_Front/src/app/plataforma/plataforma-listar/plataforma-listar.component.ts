import { Component, OnInit } from '@angular/core';
import { PlataformaService } from '../plataforma.service';
import { PlataformaDetail } from '../plataformaDetail';
import { PaginatePipe } from 'ngx-pagination';
@Component({
  selector: 'app-plataforma-listar',
  templateUrl: './plataforma-listar.component.html',
  styleUrls: ['./plataforma-listar.component.css']
})
export class PlataformaListarComponent implements OnInit {

  selectedPlataforma!: PlataformaDetail;
  selected: Boolean = false;
  p: number = 1; 
  itemsPerPage: number = 5;

  onSelected(plataforma: PlataformaDetail): void {
    this.selected = true;
    this.selectedPlataforma = plataforma;
  }

  plataformas: Array<PlataformaDetail> = [];

  constructor(private PlataformaService: PlataformaService) { }

  getPlataformas(): void {
    this.PlataformaService.plataformas.subscribe((plataformas) =>
      {this.plataformas = plataformas});
  }

  getPlataformasAlfabeticamente(): void {
    this.PlataformaService.getPlataformasAlfabeticamente().subscribe({
      next: apiData => this.plataformas = apiData,
    });
  }

  getPlataformasAntiAlfabeticamente(): void {
    this.PlataformaService.getPlataformasAntiAlfabeticamente().subscribe({
      next: apiData => this.plataformas = apiData,
    });
  }


  ngOnInit() {
    this.getPlataformas();
  }

}


