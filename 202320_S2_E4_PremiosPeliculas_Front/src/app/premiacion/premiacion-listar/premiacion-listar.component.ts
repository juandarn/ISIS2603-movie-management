import { Component, OnInit } from '@angular/core';
import { PremiacionService } from '../premiacion.service';
import { PremiacionDetail } from '../premiacionDetail';
import { MapService } from './map.service';
import { GeocodeService } from './geolocalizacion.service';
import { PremioDTO } from 'src/app/premio/premio';
import { catchError, concatMap, forkJoin, of } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-premiacion-listar',
  templateUrl: './premiacion-listar.component.html',
  styleUrls: ['./premiacion-listar.component.css']
})
export class PremiacionListarComponent implements OnInit {
  
  premiacionSeleccionada !: PremiacionDetail;
  selected : boolean = false;
  premiaciones : Array<PremiacionDetail> = [];
  isModalOpen = false;
  filtroCategoria: string = '';
  filtroGanadorPelicula: boolean | 'ambos' = 'ambos'; // 'true' para sí, 'false' para no, 'ambos' para ambos
  mapImageUrl : string = '';
  isMapModalOpen = false;
  // Agrega las propiedades para las coordenadas y los pushpins del mapa
  mapCenterLatitude: number = 47.6062; // Ejemplo de coordenadas para el centro
  mapCenterLongitude: number = -122.3321;
  pushpins: string = ''; // Para los pushpins
  premios : Array<PremioDTO> = [];
  isHomePage: boolean = false;
  filtroOrganizacion: string = '';

  constructor(private premiacionService:PremiacionService, private mapService : MapService, private geocodeService: GeocodeService, private router: Router, private route: ActivatedRoute) { }

  getPremiaciones():void{
    this.premiacionService.getPremiaciones().subscribe(premiaciones => this.premiaciones = premiaciones);
    this.loadMap();
  }

  setFiltroOrganizacion(organizacion: string): void {
    this.filtroOrganizacion = organizacion;
  }
  
  get premiacionesFiltradas(): PremiacionDetail[] {
    return this.premiaciones.filter(premiacion => {
      const categoriaMatch = this.filtroCategoria ? premiacion.categoria === this.filtroCategoria : true;
      const ganadorPeliculaMatch = this.filtroGanadorPelicula === 'ambos' ? true :
                                  (this.filtroGanadorPelicula === true && premiacion.peliculaGanadora) ||
                                  (this.filtroGanadorPelicula === false && !premiacion.peliculaGanadora);
      const organizacionMatch = this.filtroOrganizacion ? premiacion.premios.some(premio => premio.organizacion.nombre === this.filtroOrganizacion) : true;

      return categoriaMatch && ganadorPeliculaMatch && organizacionMatch;
    });
  }



    setGanadorFilter(valorSeleccionado: string): void {
      if (valorSeleccionado === 'true') {
        this.filtroGanadorPelicula = true;
      } else if (valorSeleccionado === 'false') {
        this.filtroGanadorPelicula = false;
      } else {
        this.filtroGanadorPelicula = 'ambos';
      }
    }




  onSelected(premiacion:PremiacionDetail):void{
    this.selected = true;
    this.premiacionSeleccionada = premiacion;
    this.isModalOpen = true;
  }

  closeModal() {
    this.selected = false;
    this.isModalOpen = false;
  }

  setFiltroCategoria(categoria: string): void {
    this.filtroCategoria = categoria;
  }

  scrollToMap(): void {
  document.getElementById('mapa')?.scrollIntoView({ behavior: 'smooth' });
}

  

  ngOnInit() {
    this.getPremiaciones();
    setTimeout(() =>{
      this.loadMap();
    }, 1000);
    this.isHomePage = this.router.url === '/principal/list';
    
}



  private actualizarPushpins(pais: string, lat: number, lon: number) {
    const pushpin = `&pp=${lat},${lon};66;${pais}`;
    this.pushpins += pushpin;
  }

private cacheCoordenadas = new Map<string, { lat: number, lon: number }>();

    loadMap(){
    // Utiliza forkJoin para manejar múltiples observables
    const requests = this.premiaciones
      .flatMap(premiacion => premiacion.premios)
      .map(premio => premio.pais)
      .filter((pais, index, self) => self.indexOf(pais) === index)
      .map(pais => 
        this.cacheCoordenadas.has(pais) 
          ? of(this.cacheCoordenadas.get(pais))
          : this.geocodeService.getCoordinates(pais).pipe(
              catchError(error => {
                console.error(`Error al obtener coordenadas para ${pais}:`, error);
                return of(null); // Manejo del error para seguir con la ejecución
              }),
              concatMap(coordenadas => {
                if (coordenadas) {
                  this.cacheCoordenadas.set(pais, coordenadas);
                  this.actualizarPushpins(pais, coordenadas.lat, coordenadas.lon);
                }
                return of(coordenadas);
              })
            )
      );

    forkJoin(requests).subscribe(() => {
      this.actualizarImagenMapa(); // Actualiza la imagen del mapa al final de todas las solicitudes
    });
  }

  private actualizarImagenMapa() {
    this.mapImageUrl = this.mapService.getMapImage(
      `${this.mapCenterLatitude},${this.mapCenterLongitude}`,
      10,
      '1000,900',
      this.pushpins
    );
  }


}
