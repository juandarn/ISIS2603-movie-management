package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.List;

import lombok.Data;

@Data
public class PremiacionDetailDTO extends PremiacionDTO {

    private List<PremioDTO> premios;
    private List<GanadorDTO> ganadores;
    private List<NominadoDTO> nominados;
    private List<PeliculaDTO> peliculas;

}
