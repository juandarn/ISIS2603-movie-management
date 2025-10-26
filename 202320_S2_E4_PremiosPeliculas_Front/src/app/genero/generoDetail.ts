import { GeneroDTO } from "./genero";
import { PeliculaDTO } from "../pelicula/pelicula";
export class GeneroDetail extends GeneroDTO {
  peliculas: PeliculaDTO[];

  constructor(id: number,
    nombre: string,
    imagen: string,
    peliculas: PeliculaDTO[]
    ){
      super(id, nombre, imagen);
      this.peliculas = peliculas;
    }
}
