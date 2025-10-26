package co.edu.uniandes.dse.premiospeliculas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PremiacionEntity extends BaseEntity {
    private String categoria;
    private String historia;

    // Una premiacion tiene un ganador
    @PodamExclude
    @OneToOne
    private GanadorEntity ganador;

    // Una premiacion tiene muchos nominados
    @PodamExclude
    @OneToMany(mappedBy = "premiacion")
    private List<NominadoEntity> nominados = new ArrayList<>();

    // Una premiacion tiene muchos premios
    @PodamExclude
    @OneToMany(mappedBy = "premiacion")
    private List<PremioEntity> premios = new ArrayList<>();

    // Una premiacion tiene muchas peliculas
    @PodamExclude
    @ManyToMany(mappedBy = "premiaciones")
    private List<PeliculaEntity> peliculas = new ArrayList<>();
}
