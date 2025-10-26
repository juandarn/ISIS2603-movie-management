package co.edu.uniandes.dse.premiospeliculas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import co.edu.uniandes.dse.premiospeliculas.dto.PremiacionDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PremioDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PremioEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.services.PremioPremiacionService;

@RestController
@RequestMapping("/premios")
public class PremioPremiacionController {
    @Autowired
    private PremioPremiacionService premioPremiacionService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{premioId}/premiacion")
    @ResponseStatus(code = HttpStatus.OK)
    public PremioDetailDTO replacePremiacion(@PathVariable("premioId") Long premioId, @RequestBody PremiacionDTO premiacionDTO)
            throws EntityNotFoundException {
        PremioEntity premioEntity = premioPremiacionService.replacePremiacion(premioId, premiacionDTO.getId());
        return modelMapper.map(premioEntity, PremioDetailDTO.class);
    }
}
