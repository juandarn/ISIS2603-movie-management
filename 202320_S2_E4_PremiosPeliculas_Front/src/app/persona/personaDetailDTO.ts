import { GanadorDTO } from '../ganador/ganador';
import { Nominado } from '../nominado/nominado';
import { PeliculaDTO } from '../pelicula/pelicula';
import { PersonaDTO } from './personaDTO';

export class PersonaDetailDTO extends PersonaDTO {
    peliculas: PeliculaDTO[];
    ganados: GanadorDTO[];
    nominaciones: Nominado[];

    constructor(
        id: number,
        nombre: string,
        nacionalidad: string,
        fechaNacimiento: Date,
        biografia: string,
        rol: string,
        peliculas:PeliculaDTO[],
        ganados: GanadorDTO[],
        nominaciones: Nominado[],
        imagen: string
    ){
        super(id,
            nombre,
            nacionalidad,
            fechaNacimiento,
            biografia,
            rol,
            imagen
        )
        this.peliculas = peliculas;
        this.ganados = ganados;
        this.nominaciones = nominaciones;

    }
}