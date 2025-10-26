
export class CinefiloDTO {
    id: number;
    nombre: string;
    identificador: number;

    constructor(id: number, nombre: string, identificador: number) {
        this.id = id;
        this.nombre = nombre;
        this.identificador = identificador;
    }
}