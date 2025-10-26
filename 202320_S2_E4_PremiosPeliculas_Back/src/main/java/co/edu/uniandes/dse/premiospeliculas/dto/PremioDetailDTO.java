package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PremioDetailDTO extends PremioDTO {
    private List<ReseniaDTO> resenias = new ArrayList<>();

}
