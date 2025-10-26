import { Resenia } from "../resenia/resenia";
import { CinefiloDTO } from "./cinefiloDTO";

export class CinefiloDetailDTO extends CinefiloDTO {
    resenias:Resenia[];

    constructor(id: number, nombre: string, identificador: number, resenias:Resenia[]) {
        super(id, nombre, identificador)

        this.resenias = resenias;

    }
}