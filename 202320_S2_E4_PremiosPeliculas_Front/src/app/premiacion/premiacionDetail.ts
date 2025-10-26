import { GanadorDTO } from '../ganador/ganador';
import { Nominado } from '../nominado/nominado';
import { PeliculaDTO } from '../pelicula/pelicula';
import { PremiacionDTO } from './premiacion';
import { PremioDTO } from '../premio/premio';

export class PremiacionDetail extends PremiacionDTO {
  premios: PremioDTO[];
  ganador: GanadorDTO;
  nominados: Nominado[];
  peliculas: PeliculaDTO[];

  constructor(
    id: number,
    categoria: string,
    historia: string,
    peliculaGanadora: PeliculaDTO,
    premios: PremioDTO[],
    ganador: GanadorDTO,
    nominados: Nominado[],
    peliculas: PeliculaDTO[]
  ) {
    super(id, categoria, historia, peliculaGanadora);
    this.premios = premios;
    this.ganador = ganador;
    this.nominados = nominados;
    this.peliculas = peliculas;
  }
}
