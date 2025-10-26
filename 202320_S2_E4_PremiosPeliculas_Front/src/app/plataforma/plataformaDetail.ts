import { PeliculaDTO } from "../pelicula/pelicula";
import { Plataforma } from "./plataforma";

export class PlataformaDetail extends Plataforma{

    peliculas: Array<PeliculaDTO> = [];

    constructor(id: number, nombre: string, imagen: string, peliculas: Array<PeliculaDTO>){
        super(id, nombre, imagen);
        this.peliculas = peliculas;
    }

}
