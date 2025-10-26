
import { PremiacionDetail } from "../premiacion/premiacionDetail";
import { PersonaDetailDTO } from "../persona/personaDetailDTO";
import { PersonaDTO } from "../persona/personaDTO";

export class Nominado {

    id: number;
    premiacion: PremiacionDetail;
    persona: PersonaDTO;

    constructor(id: number, premiacion: PremiacionDetail, persona: PersonaDTO) {
        this.id = id;
        this.premiacion = premiacion;
        this.persona = persona;
    }

}
