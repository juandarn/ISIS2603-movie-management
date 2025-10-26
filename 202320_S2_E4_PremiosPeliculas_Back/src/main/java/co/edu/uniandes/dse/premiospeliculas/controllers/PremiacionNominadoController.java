package co.edu.uniandes.dse.premiospeliculas.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class PremiacionNominadoController {

    @Autowired
    private PremiacionNominadoService premiacionNominadoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/{premiacionId}/nominados/{nominadoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public NominadoDTO addNominado(@PathVariable("premiacionId") Long premiacionId,
            @PathVariable("nominadoId") Long nominadoId)
            throws EntityNotFoundException {
        NominadoEntity nominadoEntity = premiacionNominadoService.addNominado(premiacionId, nominadoId);
        return modelMapper.map(nominadoEntity, NominadoDTO.class);
    }

    @GetMapping(value = "/{premiacionId}/nominados")
    @ResponseStatus(code = HttpStatus.OK)
    public List<NominadoDetailDTO> getNominados(@PathVariable("premiacionId") Long premiacionId)
            throws EntityNotFoundException {
        List<NominadoEntity> nominadoList = premiacionNominadoService.getNominados(premiacionId);
        return modelMapper.map(nominadoList, new TypeToken<List<NominadoDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{premiacionId}/nominados/{nominadoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public NominadoDetailDTO getNominado(@PathVariable("premiacionId") Long premiacionId,
            @PathVariable("nominadoId") Long nominadoId)
            throws EntityNotFoundException, IllegalOperationException {
        NominadoEntity nominadoEntity = premiacionNominadoService.getNominado(premiacionId, nominadoId);
        return modelMapper.map(nominadoEntity, NominadoDetailDTO.class);
    }

    @PutMapping(value = "/{premiacionId}/nominados")
    @ResponseStatus(code = HttpStatus.OK)
    public List<NominadoDetailDTO> replaceNominados(@PathVariable("premiacionId") Long premiacionId,
            @RequestBody List<NominadoDetailDTO> nominados) throws EntityNotFoundException {
        List<NominadoEntity> nominadoList = modelMapper.map(nominados, new TypeToken<List<NominadoEntity>>() {
        }.getType());
        List<NominadoEntity> result = premiacionNominadoService.replaceNominados(premiacionId, nominadoList);
        return modelMapper.map(result, new TypeToken<List<NominadoDetailDTO>>() {
        }.getType());
    }

}
