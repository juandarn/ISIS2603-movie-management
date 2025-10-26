import { OrganizacionDTO } from '../organizacion/organizacion';
import { PremiacionDetail } from '../premiacion/premiacionDetail';
import { PremioDTO } from './premio';
import { Resenia } from '../resenia/resenia'; 
import { PremiacionDTO } from '../premiacion/premiacion';

export class PremioDetailDTO extends PremioDTO {
  resenias: Resenia[];

  constructor(
    id: number,
    fecha: Date,
    pais: string,
    premiacion: PremiacionDTO,
    organizacion: OrganizacionDTO,
    resenias: Resenia[]
  ) {
    super(id, fecha, pais, premiacion, organizacion);
    this.resenias = resenias;
  }
}
