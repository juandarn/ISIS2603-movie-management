import { Component, OnInit } from '@angular/core';
import { PeliculaService } from '../pelicula.service';
import { PeliculaDetail } from '../peliculaDetail';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-pelicula-listar',
  templateUrl: './pelicula-listar.component.html',
  styleUrls: ['./pelicula-listar.component.css']
})
export class PeliculaListarComponent implements OnInit {
  peliculas: Array<PeliculaDetail> = [ ];
  selectedPelicula!: PeliculaDetail;
  selected: boolean = false;
  paginaActual: number = 0;
  cantidadMaximaPaginas: number = 0;
  arregloPaginas: Array<number> = [];
  tempCategoria: string = "Nada";
  selectedCategoria: string = "Nada";
  isHomePage: boolean = false;



  constructor(private peliculaService: PeliculaService, private router: Router, private route: ActivatedRoute) { 
    
  }

  getPeliculas(pageNo: Number): void {
    this.peliculaService.getPeliculasPag(pageNo).subscribe(apiData => {this.peliculas = apiData.peliculas; 
    this.cantidadMaximaPaginas = apiData.totalPages; this.generarRangoPaginas();}); 
  }
  

  ngOnInit() {
    this.getPeliculas(0);
    this.isHomePage = this.router.url === '/principal/list';

  }

  onSelected(pelicula: PeliculaDetail) {
    this.selected = true;
    this.selectedPelicula = pelicula;
    //console.log(pelicula.nombre);
  }

  generarRangoPaginas(): void {
  let inicio = Math.max(0, this.paginaActual - 3);
  let fin = Math.min(this.paginaActual + 2, this.cantidadMaximaPaginas);
  console.log("adskasñdkaslñ");
  console.log(this.cantidadMaximaPaginas);
 
  if (fin - inicio < 5) { 
    inicio = Math.max(0, fin - 5);
  }

  if (inicio-fin < 0){
    fin = Math.min(this.cantidadMaximaPaginas, inicio + 5);
  }
  
  this.arregloPaginas = Array.from({length: (fin - inicio)}, (_, i) => i + inicio);
  }

  cambiarPagina(pageNo: number): void {
    if (!(pageNo < 0 || pageNo >= this.cantidadMaximaPaginas)) {
      this.paginaActual = pageNo;
      if (this.selectedCategoria == "Nada") {
        this.getPeliculas(pageNo);
        
      }
      else{
        this.getPeliculasCategoria(pageNo);
      }
      
    }
    
  }

  ordenarPorPremiosGanados(): void {
    this.peliculas.sort((a, b) => b.premiacionesGanadas.length - a.premiacionesGanadas.length);
  }

  filtrarPorCategoria(): void {
  console.log(this.selectedCategoria);
  this.selectedCategoria = this.tempCategoria;
  if (this.selectedCategoria != "Nada") {
    this.getPeliculasCategoria(0);
    
  
  }
  else{
    this.getPeliculas(0);
  }
  this.paginaActual = 0;
}

getPeliculasCategoria(pageNo: Number): void {
  this.peliculaService.getPeliculasCategoria(this.selectedCategoria,pageNo).subscribe(apiData => {this.peliculas = apiData.peliculas; 
  this.cantidadMaximaPaginas = apiData.totalPages; this.generarRangoPaginas();}); 
}




}
