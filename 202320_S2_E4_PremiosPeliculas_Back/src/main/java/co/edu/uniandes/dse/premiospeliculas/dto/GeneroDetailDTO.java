package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GeneroDetailDTO extends GeneroDTO {
    private List<PeliculaDTO> peliculas = new ArrayList<>();
}
