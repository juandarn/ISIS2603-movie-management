package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrganizacionDetailDTO extends OrganizacionDTO {
    private List<PremioDTO> premios;
}
