package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;

import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PlataformaEntity extends BaseEntity {
    private String nombre;

    // Relación película-plataforma
    @PodamExclude
    @ManyToMany(mappedBy = "plataformas")
    private List<PeliculaEntity> peliculas = new ArrayList<>();

    public static boolean isEmpty() {
        return false;
    }
}
