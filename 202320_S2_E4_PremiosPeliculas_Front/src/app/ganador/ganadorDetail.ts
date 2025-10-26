
import { PersonaDTO } from "../persona/personaDTO";
import { PremiacionDetail } from "../premiacion/premiacionDetail";
import { GanadorDTO } from "./ganador";

export class GanadorDetail extends GanadorDTO {

    constructor(id: number, actor: PersonaDTO, premiacion: PremiacionDetail) {
        super(id, actor, premiacion);
    }
}