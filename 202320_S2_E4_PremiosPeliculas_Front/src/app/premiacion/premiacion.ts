import { PeliculaDTO } from "../pelicula/pelicula";

export class PremiacionDTO {
  id: number;
  categoria: string;
  historia: string;
  peliculaGanadora : PeliculaDTO;

  constructor(id: number, categoria: string, historia: string, peliculaGanadora: PeliculaDTO) {
    this.id = id;
    this.categoria = categoria;
    this.historia = historia;
    this.peliculaGanadora = peliculaGanadora;
  }
}
