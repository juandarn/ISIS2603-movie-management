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

import co.edu.uniandes.dse.premiospeliculas.dto.PremioDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PremioDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.OrganizacionPremioService;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionPremioController {

    @Autowired
    private OrganizacionPremioService organizacionPremioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{organizacionId}/premios/{premioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDetailDTO getPremio(@PathVariable("organizacionId") Long organizacionId,
            @PathVariable("premioId") Long premioId) throws EntityNotFoundException, IllegalOperationException {
        PremioEntity premio = organizacionPremioService.getPremio(organizacionId, premioId);
        return modelMapper.map(premio, PremioDetailDTO.class);
    }

    @GetMapping(value = "/{organizacionId}/premios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PremioDetailDTO> getPremios(@PathVariable("organizacionId") Long organizacionId)
            throws EntityNotFoundException, IllegalOperationException {
        List<PremioEntity> premios = organizacionPremioService.getPremios(organizacionId);
        return modelMapper.map(premios, new TypeToken<List<PremioDetailDTO>>() {
        }.getType());
    }

    @PostMapping(value = "/{organizacionId}/premios/{premioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDTO addPremio(@PathVariable("organizacionId") Long organizacionId,
            @PathVariable("premioId") Long premioId) throws EntityNotFoundException {
        PremioEntity premio = organizacionPremioService.addPremio(organizacionId, premioId);
        return modelMapper.map(premio, PremioDTO.class);
    }

    @PutMapping(value = "/{organizacionId}/premios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PremioDetailDTO> replacePremios(@PathVariable("organizacionId") Long organizacionId,
            @RequestBody List<PremioDTO> premios) throws EntityNotFoundException {
        List<PremioEntity> entities = modelMapper.map(premios, new TypeToken<List<PremioEntity>>() {
        }.getType());
        List<PremioEntity> premiosList = organizacionPremioService.replacePremios(organizacionId, entities);
        return modelMapper.map(premiosList, new TypeToken<List<PremioDetailDTO>>() {
        }.getType());
    }

    @DeleteMapping(value = "/{organizacionId}/premios/{premioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removePremio(@PathVariable("organizacionId") Long organizacionId,
            @PathVariable("premioId") Long premioId) throws EntityNotFoundException {
        organizacionPremioService.removePremio(organizacionId, premioId);
    }
}
