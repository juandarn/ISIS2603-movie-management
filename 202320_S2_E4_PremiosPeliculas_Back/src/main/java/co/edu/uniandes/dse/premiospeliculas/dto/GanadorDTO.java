package co.edu.uniandes.dse.premiospeliculas.dto;

import lombok.Data;

@Data 
public class GanadorDTO {
    private Long id;
    private PremiacionDTO premiacion;
    private PersonaDTO persona;
    
}
