import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { CinefiloDetailDTO } from './cinefiloDetailDTO';

@Injectable({
  providedIn: 'root'
})
export class CinefiloService {
  private apiUrl: string = environment.baseUrl + 'cinefilos';

constructor(private http:HttpClient) { }

  getCinefilo(id:string):Observable<CinefiloDetailDTO>{
    return this.http.get<CinefiloDetailDTO>(this.apiUrl+'/'+id);
  }
}
