package co.edu.uniandes.dse.premiospeliculas.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class GanadorEntity extends BaseEntity {

    @PodamExclude
    @OneToOne
    private PremiacionEntity premiacion;

    @PodamExclude
    @OneToOne
    private PersonaEntity persona;

}
