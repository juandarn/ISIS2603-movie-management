export class PersonaDTO {
    id: number;
    nombre: string;
    nacionalidad: string;
    fechaNacimiento: Date;
    biografia: string;
    rol: string;
    imagen:string;

    constructor(
        id: number,
        nombre: string,
        nacionalidad: string,
        fechaNacimiento: Date,
        biografia: string,
        rol: string,
        imagen:string
        
    ) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.biografia = biografia;
        this.rol = rol;
        this.imagen = imagen;
    }
}
