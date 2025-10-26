package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class PeliculaEntity extends BaseEntity {
    private String nombre;
    private Integer duracion;
    private String pais;
    private String idiomaOriginal;
    @Temporal(TemporalType.DATE)
    private Date fechaEstreno;
    private String linkTrailer;

    @PodamExclude
    @ManyToMany
    private List<PremiacionEntity> premiaciones = new ArrayList<>();

    // Una pelicula tiene muchas personas
    @PodamExclude
    @ManyToMany
    private List<PersonaEntity> personas = new ArrayList<>();

    // Una pelicula tiene muchos generos
    @PodamExclude
    @ManyToMany
    private List<GeneroEntity> generos = new ArrayList<>();

    // Una pelicula tiene muchas plataformas
    @PodamExclude
    @ManyToMany
    private List<PlataformaEntity> plataformas = new ArrayList<>();

    // Una pelicula tiene muchas reseñas, pero las reseñas solo pueden pertenecer a
    // una pelicula
    @PodamExclude
    @OneToMany(mappedBy = "pelicula")
    private List<ReseniaEntity> resenias = new ArrayList<>();

}
