package co.edu.uniandes.dse.premiospeliculas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class OrganizacionEntity extends BaseEntity {
    private String nombre;

    @PodamExclude
    @OneToMany(mappedBy = "organizacion")
    private List<PremioEntity> premios = new ArrayList<>();

}
