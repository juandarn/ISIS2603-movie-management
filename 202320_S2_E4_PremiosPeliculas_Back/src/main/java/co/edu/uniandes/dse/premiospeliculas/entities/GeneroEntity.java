package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class GeneroEntity extends BaseEntity {
    private String nombre;

    // Relación película-género
    @PodamExclude
    @ManyToMany(mappedBy = "generos")
    private List<PeliculaEntity> peliculas = new ArrayList<PeliculaEntity>();

    public static boolean isEmpty() {
        return false;
    }

    public static GeneroEntity get() {
        return null;
    }

}
