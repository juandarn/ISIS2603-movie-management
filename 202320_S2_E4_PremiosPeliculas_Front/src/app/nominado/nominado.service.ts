import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Nominado } from './nominado';
import { NominadoDetail } from './nominadoDetail';

@Injectable({
  providedIn: 'root'
})
export class NominadoService {
  private apiUrl: string = environment.baseUrl + 'nominados';
constructor(private http: HttpClient) { }

get nominados(): Observable<Nominado[]>{
  return this.http.get<Nominado[]>(this.apiUrl);
}

getNominado(id: string): Observable<NominadoDetail> {
  return this.http.get<NominadoDetail>(this.apiUrl + "/" + id);
}

}


