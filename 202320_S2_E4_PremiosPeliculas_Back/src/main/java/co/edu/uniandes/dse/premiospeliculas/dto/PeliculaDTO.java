package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class PeliculaDTO {
    private Long id;
    private String nombre;
    private Integer duracion;
    private String pais;
    private String idiomaOriginal;
    @Temporal(TemporalType.DATE)
    private Date fechaEstreno;
    private String linkTrailer;
    
}
