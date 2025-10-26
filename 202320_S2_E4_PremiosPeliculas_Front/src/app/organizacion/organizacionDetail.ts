import { OrganizacionDTO } from "./organizacion";
import { PremioDTO } from "../premio/premio";


export class OrganizacionDetail extends OrganizacionDTO {
    premios: PremioDTO[];

    constructor(id: number, nombre: string, premios: PremioDTO[]) {
        super(id, nombre);
        this.premios = premios;
    }

}
