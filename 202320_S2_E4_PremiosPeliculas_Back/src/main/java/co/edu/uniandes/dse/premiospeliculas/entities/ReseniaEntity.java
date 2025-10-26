package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ReseniaEntity extends BaseEntity {
    private String texto;
    private Integer clasificacion;
    @Temporal(TemporalType.DATE)
    private Date fechaEstreno;

    // Las reseñas poseen unicamente un cinefilo
    @ManyToOne
    private CinefiloEntity cinefilo;

    @PodamExclude
    @ManyToOne
    private PeliculaEntity pelicula;

    @PodamExclude
    @ManyToOne
    private PremioEntity premio;

}
