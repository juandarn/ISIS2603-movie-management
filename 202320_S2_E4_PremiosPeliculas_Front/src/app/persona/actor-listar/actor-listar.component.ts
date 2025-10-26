import { Component, OnInit } from '@angular/core';
import { PersonaService } from '../persona.service';
import { PersonaDetailDTO } from '../personaDetailDTO';
import { PersonaDTO } from '../personaDTO';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-actor-listar',
  templateUrl: './actor-listar.component.html',
  styleUrls: ['./actor-listar.component.css']
})
export class ActorListarComponent implements OnInit {

  rol!:string;
  actorSelecionado!:PersonaDetailDTO;
  selected:boolean = false;
  actores:Array<PersonaDetailDTO> = [];
  isHomePage: boolean = false;
  nacionalidadesUnicas: Set<string> = new Set();
  orden: String = "";
  nacionalidad: String = "Todas";
  numlimitesPeliculas: number[] = [];
  numPeliculas: number = -1;


  constructor(private personaService: PersonaService, private router: Router, private route: ActivatedRoute ) { 
  }

  ngOnInit() {
    this.getActores();
    this.isHomePage = this.router.url === '/principal/list';
  }

  getActores(){
    this.personaService.getActores(this.orden).subscribe(apiData=>{
      this.actores = apiData;
      this.cargarFiltro();
      this.filtros();
    });
  }

  cambiarOrden(event: any) {
    const valorSeleccionado = event.target.value;
    this.orden = valorSeleccionado;
    this.getActores();

  }

  cargarFiltro() {
    this.actores.forEach(persona => {
      this.nacionalidadesUnicas.add(persona.nacionalidad);
      if (this.numlimitesPeliculas.length == 0) {
        this.numlimitesPeliculas = [persona.peliculas.length, persona.peliculas.length]
      } else if (persona.peliculas.length < this.numlimitesPeliculas[0]) {
        this.numlimitesPeliculas[0] = persona.peliculas.length;
      } else if (persona.peliculas.length > this.numlimitesPeliculas[1]) {
        this.numlimitesPeliculas[1] = persona.peliculas.length;
      }

    });


  }

  filtros() {
    let actores: Array<PersonaDetailDTO> = [];
    this.actores.forEach(persona => {
      this.nacionalidadesUnicas.add(persona.nacionalidad);
      let add = true;
      if (add && this.nacionalidad != "Todas") {
        add = persona.nacionalidad === this.nacionalidad;
      }
      if (add && this.numPeliculas >= 0) {
        add = persona.peliculas.length <= this.numPeliculas
      }
      if (add) {
        actores.push(persona);
      }

    });

    this.actores = actores;

  }

  cambiarNumPeli(event: any) {
    const valorSeleccionado = event.target.value;
    this.numPeliculas = valorSeleccionado;
    this.getActores();
  }

  cambiarNacionalidades(event: any) {
    const valorSeleccionado = event.target.value;
    this.nacionalidad = valorSeleccionado;
    this.getActores();
  }
}