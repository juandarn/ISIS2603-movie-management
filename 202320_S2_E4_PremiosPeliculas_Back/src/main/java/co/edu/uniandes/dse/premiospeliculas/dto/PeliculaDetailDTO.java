package co.edu.uniandes.dse.premiospeliculas.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PeliculaDetailDTO extends PeliculaDTO {
    private List<PremiacionDTO> premiaciones = new ArrayList<PremiacionDTO>();
    private List<GeneroDTO> generos = new ArrayList<GeneroDTO>();
    private List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
    private List<PlataformaDTO> plataformas = new ArrayList<PlataformaDTO>();
    private List<ReseniaDTO> resenias = new ArrayList<ReseniaDTO>();
    

}
