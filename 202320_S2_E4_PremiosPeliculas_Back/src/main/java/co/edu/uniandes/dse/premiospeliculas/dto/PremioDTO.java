package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PremioDTO {
    private Long id;
    private Date fecha;
    private String pais;

    private PremiacionDTO premiacion;
    private OrganizacionDTO organizacion;

}
