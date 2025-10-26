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

import co.edu.uniandes.dse.premiospeliculas.dto.PremioDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.services.ReseniaPremioService;

@RestController
@RequestMapping("/resenias")
public class ReseniaPremioController {
        @Autowired
    private ReseniaPremioService reseniaPremioService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{reseniaId}/premio")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDetailDTO replacePremio(@PathVariable("reseniaId") Long reseniaId, @RequestBody PremioDTO premioDTO)
            throws EntityNotFoundException {
        ReseniaEntity reseniaEntity = reseniaPremioService.replacePremio(reseniaId, premioDTO.getId());
        return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
    }
}




    
