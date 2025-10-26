import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PremiacionDetail } from './premiacionDetail';
@Injectable({
  providedIn: 'root'
})
export class PremiacionService {

  private apiUrl = environment.baseUrl + 'premiaciones';



constructor(private http: HttpClient) { }

getPremiaciones(): Observable<PremiacionDetail[]>{
  return this.http.get<PremiacionDetail[]>(this.apiUrl);

}

getPremiacion(id: string) : Observable<PremiacionDetail>{
  return this.http.get<PremiacionDetail>(this.apiUrl + "/" + id);
  }

}