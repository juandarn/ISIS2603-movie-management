package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;


@Data
@Entity
public class CinefiloEntity extends BaseEntity {
    private String nombre;
    private Integer identificador;
    
    // Un cinefilo tiene varias reseñas
    @PodamExclude
    @OneToMany(mappedBy = "cinefilo")
    private List<ReseniaEntity> resenias;
    
}
