package co.edu.uniandes.dse.premiospeliculas.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PersonaEntity extends BaseEntity {
    private String nombre;
    private String nacionalidad;

    private Date fechaNacimiento;

    private String biografia;
    private String rol;

    @PodamExclude
    @ManyToMany(mappedBy = "personas")
    private List<PeliculaEntity> peliculas = new ArrayList<>();

}
