// map.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  constructor(private http: HttpClient) { }

  getMapImage(centerPoint: string, zoomLevel: number, mapSize: string, pushpins: string): string {
    const bingMapsKey = 'AlKKLdaOgfBH3VvDC6M4ME5ihyvdDXNZXvWNhahOB1mQgjId8vGuh32NBvetMUfd'; // Asegúrate de que sea tu clave real
    const mapImageUrl = `https://dev.virtualearth.net/REST/v1/Imagery/Map/Road/?mapSize=${mapSize}&format=png&key=${bingMapsKey}${pushpins}`;
    return mapImageUrl;
  }
}
