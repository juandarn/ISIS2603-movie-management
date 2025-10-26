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

import co.edu.uniandes.dse.premiospeliculas.dto.NominadoDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.NominadoDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.NominadoEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PremiacionNominadoService;

@RestController
@RequestMapping("/premiaciones")
public class PremiacionGanadorController {

    // @Autowired
    // private PremiacionNominadoService premiacionNominadoService;

    // @Autowired
    // private ModelMapper modelMapper;

    // @GetMapping(value = "/{premiacionId}/nominados/{nominadoId}")
    // @ResponseStatus(code = HttpStatus.OK)
    // public NominadoDetailDTO getNominado(@PathVariable("premiacionId") Long
    // premiacionId,
    // @PathVariable("nominadoId") Long nominadoId) throws EntityNotFoundException,
    // IllegalOperationException {
    // NominadoEntity nominado = premiacionNominadoService.getNominado(premiacionId,
    // nominadoId);
    // return modelMapper.map(nominado, NominadoDetailDTO.class);
    // }

    // @GetMapping(value = "/{premiacionId}/nominados")
    // @ResponseStatus(code = HttpStatus.OK)
    // public NominadoDetailDTO getNominados(@PathVariable("premiacionId") Long
    // premiacionId)
    // throws EntityNotFoundException, IllegalOperationException {
    // List<NominadoEntity> nominados =
    // premiacionNominadoService.getNominados(premiacionId);
    // return modelMapper.map(nominados, new TypeToken<List<NominadoDetailDTO>>() {
    // }.getType());
    // }

    // @PostMapping(value = "/{premiacionId}/nominados/{nominadoId}")
    // @ResponseStatus(code = HttpStatus.OK)
    // public NominadoDetailDTO addNominado(@PathVariable("premiacionId") Long
    // premiacionId,
    // @PathVariable("nominadoId") Long nominadoId) throws EntityNotFoundException {
    // NominadoEntity nominado = premiacionNominadoService.addNominado(premiacionId,
    // nominadoId);
    // return modelMapper.map(nominado, NominadoDetailDTO.class);
    // }

    // @PutMapping(value = "/{premiacionId}/nominados")
    // @ResponseStatus(code = HttpStatus.OK)
    // public List<NominadoDetailDTO> replacePremios(@PathVariable("premiacionId")
    // Long premiacionId,
    // @RequestBody List<NominadoDTO> nominados) throws EntityNotFoundException {
    // List<NominadoEntity> entities = modelMapper.map(nominados, new
    // TypeToken<List<NominadoEntity>>() {
    // }.getType());
    // List<NominadoEntity> nominadoList =
    // premiacionNominadoService.replaceNominados(premiacionId, entities);
    // return modelMapper.map(nominadoList, new TypeToken<List<NominadoDetailDTO>>()
    // {
    // }.getType());
    // }

    // @DeleteMapping(value = "/{premiacionId}/nominados/{nominadoId}")
    // @ResponseStatus(code = HttpStatus.NO_CONTENT)
    // public void removePremio(@PathVariable("premiacionId") Long premiacionId,
    // @PathVariable("nominadoId") Long nominadoId) throws EntityNotFoundException {
    // premiacionNominadoService.removeNominado(premiacionId, nominadoId);
    // }

}
