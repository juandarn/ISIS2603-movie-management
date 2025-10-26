import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { GeneroDetail } from './generoDetail';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class GeneroService {
  private apiUrl: string = environment.baseUrl + 'generos';


  constructor(private http: HttpClient) { }

getGeneros(): Observable<GeneroDetail[]> {
  return this.http.get<GeneroDetail[]>(this.apiUrl);
}
getGenero(id: string) : Observable<GeneroDetail>{
  return this.http.get<GeneroDetail>(this.apiUrl + "/" + id);
  }

}
