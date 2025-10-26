package co.edu.uniandes.dse.premiospeliculas.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.premiospeliculas.dto.OrganizacionDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.OrganizacionDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.OrganizacionEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.OrganizacionService;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrganizacionDetailDTO> findAll() {
        List<OrganizacionEntity> organizaciones = organizacionService.getOrganizaciones();
        return modelMapper.map(organizaciones, new TypeToken<List<OrganizacionDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public OrganizacionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        OrganizacionEntity organizacion = organizacionService.getOrganizacion(id);
        return modelMapper.map(organizacion, OrganizacionDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrganizacionDTO create(@RequestBody OrganizacionDTO organizacionDTO) throws IllegalOperationException {
        OrganizacionEntity organizacionEntity = organizacionService
                .createOrganizacion(modelMapper.map(organizacionDTO, OrganizacionEntity.class));
        return modelMapper.map(organizacionEntity, OrganizacionDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public OrganizacionDTO update(@PathVariable("id") Long id, @RequestBody OrganizacionDTO organizacionDTO)
            throws EntityNotFoundException {
        OrganizacionEntity organizacionEntity = organizacionService.updateOrganizacion(id,
                modelMapper.map(organizacionDTO, OrganizacionEntity.class));
        return modelMapper.map(organizacionEntity, OrganizacionDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        organizacionService.deleteOrganizacion(id);
    }
}
