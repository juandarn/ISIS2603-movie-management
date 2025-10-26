import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PersonaDTO } from './personaDTO';
import { PersonaDetailDTO } from './personaDetailDTO';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private apiUrl:string = environment.baseUrl + 'personas'
constructor(private http:HttpClient) { }

getPersona(id:string):Observable<PersonaDetailDTO>{
  return this.http.get<PersonaDetailDTO>(this.apiUrl+'/'+id);
}
getActores(orden:String = ""):Observable<PersonaDetailDTO[]>{
  return this.http.get<PersonaDetailDTO[]>(this.apiUrl+'/actores?'+'orden='+orden);
}

getDirectores(orden:String = ""):Observable<PersonaDetailDTO[]>{
  return this.http.get<PersonaDetailDTO[]>(this.apiUrl+'/directores?'+'orden='+orden);
}

}
