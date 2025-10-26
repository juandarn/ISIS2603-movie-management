package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReseniaDTO {
    private Long id;
    private String texto;
    private Integer clasificacion;
    private Date fecha_resenia;
    
    private CinefiloDTO cinefilo;
    private PeliculaDTO pelicula;
    private PremioDTO premio; 
}
