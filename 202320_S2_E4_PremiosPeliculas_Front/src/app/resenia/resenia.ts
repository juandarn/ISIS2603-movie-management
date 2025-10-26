import { CinefiloDTO } from "../cinefilo/cinefiloDTO";
import { PeliculaDTO } from "../pelicula/pelicula";
import { PeliculaDetail } from "../pelicula/peliculaDetail";
import { PremioDTO } from "../premio/premio";
import { PremioDetailDTO } from "../premio/premioDetail";
export class Resenia {
    id: number;
    texto: string;
    clasificacion: number;
    fecha_estreno: Date;
    cinefilo: CinefiloDTO;
    pelicula: PeliculaDetail;
    premio: PremioDTO;

    constructor(id: number, texto: string, clasificacion: number, fecha_estreno: Date, pelicula: PeliculaDetail, premio: PremioDTO, cinefilo: CinefiloDTO) {
        this.id = id;
        this.texto = texto;
        this.clasificacion = clasificacion;
        this.fecha_estreno = fecha_estreno;
        this.cinefilo = cinefilo;

        if ((pelicula && premio) || (!pelicula && !premio)) {
            throw new Error("Debe proporcionar solo pelicula o premio, no ambos.");
          }
          
        this.pelicula = pelicula;
        this.premio = premio;
    }

}
