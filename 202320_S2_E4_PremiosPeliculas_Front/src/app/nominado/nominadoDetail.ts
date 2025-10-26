import { PremiacionDetail } from "../premiacion/premiacionDetail";
import { Nominado } from "./nominado";
import { PersonaDetailDTO } from "../persona/personaDetailDTO";
import { PersonaDTO } from "../persona/personaDTO";

export class NominadoDetail extends Nominado {

    constructor(id: number, premiacion: PremiacionDetail, persona: PersonaDTO) {
        super(id, premiacion, persona);
    }
}
