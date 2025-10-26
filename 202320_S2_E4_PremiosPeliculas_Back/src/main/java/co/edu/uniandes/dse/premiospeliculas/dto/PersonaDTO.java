package co.edu.uniandes.dse.premiospeliculas.dto;

import java.util.Date;


import lombok.Data;

@Data
public class PersonaDTO {
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Date fechaNacimiento;
    private String biografia;
    private String rol;
}
