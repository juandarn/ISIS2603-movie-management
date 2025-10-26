import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PeliculaDTO } from './pelicula';
import { environment } from 'src/environments/environment';
import { PeliculaDetail } from './peliculaDetail';
import { PaginatedPeliculaResponse } from './page-pelicula';

@Injectable({
  providedIn: 'root'
})
export class PeliculaService {
  private apiUrl: string = environment.baseUrl + 'peliculas';
  constructor(private http: HttpClient) {}

   getPeliculasPag(page: Number): Observable<PaginatedPeliculaResponse> {
    return this.http.get<PaginatedPeliculaResponse>(`${this.apiUrl}/pag?page=${page}&size=6`);
  }

  getPeliculas(): Observable<PeliculaDTO[]> {
    return this.http.get<PeliculaDTO[]>(this.apiUrl);
  }
  getPelicula(peliculaId: String): Observable<PeliculaDetail> {
    return this.http.get<PeliculaDetail>(`${this.apiUrl}/${peliculaId}`);
  }

  createPelicula(pelicula: PeliculaDTO): Observable<PeliculaDTO> {
    return this.http.post<PeliculaDTO>(this.apiUrl, pelicula);
  }

  getPeliculasCategoria(categoria: String, page: Number): Observable<PaginatedPeliculaResponse> {
    return this.http.get<PaginatedPeliculaResponse>(`${this.apiUrl}/cat?categoria=${categoria}&page=${page}&size=6`);
  }

}
