import { Component, OnInit } from '@angular/core';
import { PeliculaDTO } from '../pelicula/pelicula';
import { PeliculaService } from '../pelicula/pelicula.service';
import { GeneroDetail } from '../genero/generoDetail';
import { PlataformaDetail } from '../plataforma/plataformaDetail';
import { PlataformaService } from '../plataforma/plataforma.service';
import { GeneroService } from '../genero/genero.service';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {
  movies : Array<PeliculaDTO> = [];
  plataformas: Array<PlataformaDetail> = [];
  generos: Array<GeneroDetail> = [];
  currentSlide = 0;
  ordenacionGenero: string = '';

  constructor(private peliculaService: PeliculaService, private plataformaservice: PlataformaService, private generoService: GeneroService) { }


  ngOnInit(): void {
    this.peliculaService.getPeliculas().subscribe(movies => this.movies = movies);
    this.plataformaservice.plataformas.subscribe(plataformas => this.plataformas = plataformas);
    this.generoService.getGeneros().subscribe(generos => {
      this.generos = generos;
      this.ordenarGeneros(); // Llamada a la función de ordenación
    });
  }

  setOrdenacionGenero(event: Event): void {
  const selectElement = event.target as HTMLSelectElement; // Asegura el tipo correcto
  const orden = selectElement.value;
  this.ordenacionGenero = orden;
  this.ordenarGeneros();
}

  ordenarGeneros(): void {
    if (this.ordenacionGenero === 'asc') {
      this.generos.sort((a, b) => a.nombre.localeCompare(b.nombre));
    } else if (this.ordenacionGenero === 'desc') {
      this.generos.sort((a, b) => b.nombre.localeCompare(a.nombre));
    }
  }

  onNextClick(): void {
    if (this.currentSlide < this.movies.length - 1) {
      this.currentSlide++;
    } else {
      this.currentSlide = 0;
    }
  }

  onPrevClick(): void {
    if (this.currentSlide > 0) {
      this.currentSlide--;
    } else {
      this.currentSlide = this.movies.length - 1;
    }
  }

  getYearFromReleaseDate(releaseDate: Date | string): number | string {
    const date = new Date(releaseDate);
    return date.getFullYear();
  }

  
  getCantidadPeliculasPorPlataforma(plataformaId: number): number {
    const plataformaSeleccionada = this.plataformas.find(plataforma => plataforma.id === plataformaId);

    if (plataformaSeleccionada) {
      return plataformaSeleccionada.peliculas.length;
    } else {
      return 0; 
    }
  }

  getCantidadPeliculasPorGenero(generoId: number): number {
    const generoSeleccionado = this.generos.find(genero => genero.id === generoId);
    if (generoSeleccionado) {
      return generoSeleccionado.peliculas.length;
    } else {
      return 0; 
    }
  }
}
