import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrganizacionDetail } from './organizacionDetail';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrganizacionService {

  private apiUrl = environment.baseUrl + 'organizaciones';

constructor(private http: HttpClient) { }


getOrganizaciones(): Observable<OrganizacionDetail[]>{
  return this.http.get<OrganizacionDetail[]>(this.apiUrl);
}
getOrganizacion(id: string) : Observable<OrganizacionDetail>{
  return this.http.get<OrganizacionDetail>(this.apiUrl + "/" + id);
  }
}
