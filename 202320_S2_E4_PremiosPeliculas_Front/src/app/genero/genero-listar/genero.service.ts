import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { GeneroDetail } from '../generoDetail';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GeneroService {

  private apiUrl: string = environment.baseUrl + 'generos';

constructor(private http : HttpClient) { }

getGeneros(): Observable<GeneroDetail[]>{
  return this.http.get<GeneroDetail[]>(this.apiUrl);
}



}
