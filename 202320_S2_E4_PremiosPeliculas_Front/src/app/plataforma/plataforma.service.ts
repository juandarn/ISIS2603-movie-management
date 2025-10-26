import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PlataformaDetail } from './plataformaDetail';

@Injectable({
  providedIn: 'root'
})
export class PlataformaService {
  private apiUrl: string = environment.baseUrl + 'plataformas';
constructor(private http: HttpClient) { }

get plataformas(): Observable<PlataformaDetail[]>{
  return this.http.get<PlataformaDetail[]>(this.apiUrl);
}

getPlataforma(id: string): Observable<PlataformaDetail> {
  return this.http.get<PlataformaDetail>(this.apiUrl + "/" + id);
}

getPlataformasAlfabeticamente(): Observable<PlataformaDetail[]> {
  return this.http
    .get<PlataformaDetail[]>(this.apiUrl+'/alfabeticamente');
}

getPlataformasAntiAlfabeticamente(): Observable<PlataformaDetail[]> {
  return this.http
    .get<PlataformaDetail[]>(this.apiUrl+'/antiAlfabeticamente');
}






}