package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CinefiloDetailDTO extends CinefiloDTO {
    private List<ReseniaDTO> resenias = new ArrayList<ReseniaDTO>();
    
}
