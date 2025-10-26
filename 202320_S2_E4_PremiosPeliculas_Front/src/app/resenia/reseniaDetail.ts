import { PeliculaDetail } from "../pelicula/peliculaDetail";
import { PremioDetailDTO } from "../premio/premioDetail";
import { Resenia } from "./resenia";

export class ReseniaDetail extends Resenia{

    constructor(id: number, texto: string, calificacion: number, fecha_resenia: Date, pelicula: PeliculaDetail, premio: PremioDetailDTO, cinefilo: any) {
        super(id, texto, calificacion, fecha_resenia, pelicula, premio, cinefilo);
    }

}
