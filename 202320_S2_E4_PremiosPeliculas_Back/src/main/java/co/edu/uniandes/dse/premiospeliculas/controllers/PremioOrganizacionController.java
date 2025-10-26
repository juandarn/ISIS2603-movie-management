package co.edu.uniandes.dse.premiospeliculas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.premiospeliculas.dto.OrganizacionDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PremioDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.services.PremioOrganizacionService;

@RestController
@RequestMapping("/premios")
public class PremioOrganizacionController {

    @Autowired
    private PremioOrganizacionService premioOrganizacionService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{premioId}/organizacion")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDetailDTO replaceEditorial(@PathVariable("premioId") Long premioId,
            @RequestBody OrganizacionDTO organizacionDTO)
            throws EntityNotFoundException {
        PremioEntity premioEntity = premioOrganizacionService.replaceOrganizacion(premioId, organizacionDTO.getId());
        return modelMapper.map(premioEntity, PremioDetailDTO.class);
    }

}
