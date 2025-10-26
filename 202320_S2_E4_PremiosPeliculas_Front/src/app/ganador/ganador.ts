import { PersonaDTO } from "../persona/personaDTO";
import { PremiacionDTO } from "../premiacion/premiacion";
import { PremiacionDetail } from "../premiacion/premiacionDetail";

export class GanadorDTO {
    id: number;
    persona: PersonaDTO;
    premiacion: PremiacionDTO;

    constructor(id: number, persona: PersonaDTO, premiacion: PremiacionDTO) {
        this.id = id;
        this.persona = persona;
        this.premiacion = premiacion;
    }
}
