import { PremiacionDTO } from "../premiacion/premiacion";
import { OrganizacionDTO } from "../organizacion/organizacion";
import { PremiacionDetail } from "../premiacion/premiacionDetail";
import { OrganizacionDetail } from "../organizacion/organizacionDetail";
export class PremioDTO {
  id: number;
  fecha: Date;
  pais: string;
  premiacion: PremiacionDTO;
  organizacion: OrganizacionDTO;

  constructor(
    id: number,
    fecha: Date,
    pais: string,
    premiacion: PremiacionDTO,
    organizacion: OrganizacionDTO
  ) {
    this.id = id;
    this.fecha = fecha;
    this.pais = pais;
    this.premiacion = premiacion;
    this.organizacion = organizacion;
  }
}
