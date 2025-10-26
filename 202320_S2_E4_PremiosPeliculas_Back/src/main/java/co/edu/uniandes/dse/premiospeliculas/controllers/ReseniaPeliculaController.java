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

import co.edu.uniandes.dse.premiospeliculas.dto.PeliculaDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.ReseniaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.ReseniaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.services.ReseniaPeliculaService;


@RestController
@RequestMapping("/resenias")
public class ReseniaPeliculaController {
     @Autowired
    private ReseniaPeliculaService reseniaPeliculaService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping(value = "/{reseniaId}/pelicula")
    @ResponseStatus(code = HttpStatus.OK)
    public ReseniaDetailDTO replacePelicula(@PathVariable("reseniaId") Long reseniaId, @RequestBody PeliculaDTO peliculaDTO)
            throws EntityNotFoundException {
        ReseniaEntity reseniaEntity = reseniaPeliculaService.replacePelicula(reseniaId, peliculaDTO.getId());
        return modelMapper.map(reseniaEntity, ReseniaDetailDTO.class);
    }
}




