// geocode.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})



export class GeocodeService {

  constructor(private http: HttpClient) {}

  getCoordinates(countryName: string) {
    return this.http.get<any[]>(`/api/search?q=${countryName}`).pipe(
      map(response => {
        const firstResult = response[0];
        return firstResult ? { lat: firstResult.lat, lon: firstResult.lon } : null;
      })
    );
  }
}
