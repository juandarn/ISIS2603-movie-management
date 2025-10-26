export class PeliculaDTO {
    id: number;
    nombre: string;
    duracion: number;
    pais: string;
    idiomaOriginal: string;
    fechaEstreno: Date;
    linkTrailer: string;
    imagen: string;

    constructor(id: number, nombre: string, duracion: number, pais: string, idiomaOriginal: string, fechaEstreno: Date, linkTrailer: string
        , imagen: string) {
        this.id = id;
        this.nombre = nombre;   
        this.duracion = duracion;
        this.pais = pais;
        this.idiomaOriginal = idiomaOriginal;
        this.fechaEstreno = fechaEstreno;
        this.linkTrailer = linkTrailer;
        this.imagen = imagen;
    }
}