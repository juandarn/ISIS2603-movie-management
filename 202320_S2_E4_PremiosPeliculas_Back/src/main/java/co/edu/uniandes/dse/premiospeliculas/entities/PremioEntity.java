package co.edu.uniandes.dse.premiospeliculas.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class PremioEntity extends BaseEntity {
    private Date fecha;
    private String pais;

    @PodamExclude
    @ManyToOne
    private PremiacionEntity premiacion;

    @PodamExclude
    @ManyToOne
    private OrganizacionEntity organizacion;

    @PodamExclude
    @OneToMany(mappedBy = "premio")
    private List<ReseniaEntity> resenias = new ArrayList<>();
}
