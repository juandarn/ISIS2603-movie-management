import { GeneroDTO } from "../genero/genero";
import { OrganizacionDTO } from "../organizacion/organizacion";
import { PersonaDTO } from "../persona/personaDTO";
import { Plataforma } from "../plataforma/plataforma";
import { PremiacionDTO } from "../premiacion/premiacion";
import { Resenia } from "../resenia/resenia";
import { PeliculaDTO } from "./pelicula";
//import { OrganizacionDTO } from "../organizacion/organizacionDTO";

export class PeliculaDetail extends PeliculaDTO {
    premiaciones:Array<PremiacionDTO> = [ ] ;
    //Cambiar luego por plataforma DTO
    organizaciones: Array<OrganizacionDTO> = [ ];
    personas: Array<PersonaDTO> = [ ];
    plataformas: Array<Plataforma> = [ ];
    generos: Array<GeneroDTO> = [ ];
    premiacionesGanadas: Array<PremiacionDTO> = [ ];
    resenias: Array<Resenia> = [];
    

    constructor(id: number, nombre: string, duracion: number, pais: string, idiomaOriginal: string, fechaEstreno: Date, linkTrailer: string, imagen: string, 
        premiaciones: Array<PremiacionDTO>, plataformas: Array<Plataforma> , personas: Array<PersonaDTO>, generos: Array<GeneroDTO>, premiacionesGanadas: Array<PremiacionDTO>,
        resenias: Array<Resenia>) {
        super(id, nombre, duracion, pais, idiomaOriginal, fechaEstreno, linkTrailer, imagen);
        this.premiaciones = premiaciones;
        this.plataformas = plataformas;
        this.personas = personas;
        this.generos = generos;
        this.premiacionesGanadas = premiacionesGanadas;
        this.resenias = resenias;
    }
}