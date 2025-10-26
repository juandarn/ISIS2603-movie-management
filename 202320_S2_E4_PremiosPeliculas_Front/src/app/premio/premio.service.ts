import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PremioDetailDTO } from './premioDetail';

@Injectable({
  providedIn: 'root'
})
export class PremioService {

  private apiUrl: string = environment.baseUrl + 'premios';
constructor(private http: HttpClient) { }

get premios(): Observable<PremioDetailDTO[]>{
  return this.http.get<PremioDetailDTO[]>(this.apiUrl);
}

getPremio(id: string) : Observable<PremioDetailDTO>{
  console.log(id);
  return this.http.get<PremioDetailDTO>(this.apiUrl + "/" + id);
  }

}
