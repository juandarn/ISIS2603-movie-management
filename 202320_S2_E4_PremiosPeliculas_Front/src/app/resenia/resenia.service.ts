import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Resenia } from './resenia';
import { ReseniaDetail } from './reseniaDetail';

@Injectable({
  providedIn: 'root'
})
export class ReseniaService {
  private apiUrl: string = environment.baseUrl + 'resenias';
constructor(private http: HttpClient) { }

createResenia(resenia: Resenia): Observable<Resenia>{
  return this.http.post<Resenia>(this.apiUrl, resenia);
}

get resenias(): Observable<Resenia[]>{
  return this.http.get<Resenia[]>(this.apiUrl);
}

getReseniaDetail(id: string): Observable<Resenia>{
  return this.http.get<Resenia>(`${this.apiUrl}/${id}`);
}


getReseniasAlfabeticamente(): Observable<ReseniaDetail[]> {
  return this.http
    .get<ReseniaDetail[]>(this.apiUrl+'/alfabeticamente');
}

getReseniasAntiAlfabeticamente(): Observable<ReseniaDetail[]> {
  return this.http
    .get<ReseniaDetail[]>(this.apiUrl+'/antiAlfabeticamente');
}

}