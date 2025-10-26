import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { GanadorDTO } from './ganador';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GanadorService {
  private apiUrl: string = environment.baseUrl + 'ganadores';
constructor(private http: HttpClient) { }

get ganadores(): Observable<GanadorDTO[]>{
  return this.http.get<GanadorDTO[]>(this.apiUrl);
}

getGanadorDetail(id: string): Observable<GanadorDTO>{
  return this.http.get<GanadorDTO>(`${this.apiUrl}/${id}`);
}
}