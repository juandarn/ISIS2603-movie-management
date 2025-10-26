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
import co.edu.uniandes.dse.premiospeliculas.dto.PeliculaDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.PeliculaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PeliculaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PeliculaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PeliculaDetailDTO> findAll() {
        List<PeliculaEntity> peliculas = this.peliculaService.getPeliculas();
        return modelMapper.map(peliculas, new TypeToken<List<PeliculaDetailDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PeliculaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PeliculaEntity pelicula = this.peliculaService.getPelicula(id);
        return modelMapper.map(pelicula, PeliculaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PeliculaDTO create(@RequestBody PeliculaDTO peliculaDTO) throws IllegalOperationException {
        PeliculaEntity peliculaEntity = this.peliculaService.createPelicula(modelMapper.map(peliculaDTO, PeliculaEntity.class));
        return modelMapper.map(peliculaEntity, PeliculaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PeliculaDTO update(@PathVariable("id") Long id, @RequestBody PeliculaDTO peliculaDTO) throws EntityNotFoundException, IllegalOperationException {
        PeliculaEntity peliculaEntity = this.peliculaService.updatePelicula(id,
                modelMapper.map(peliculaDTO, PeliculaEntity.class));
        return modelMapper.map(peliculaEntity, PeliculaDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        this.peliculaService.deletePelicula(id);
    }
}
