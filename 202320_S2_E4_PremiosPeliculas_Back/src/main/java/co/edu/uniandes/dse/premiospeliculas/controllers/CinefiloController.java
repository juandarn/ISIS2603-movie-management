package co.edu.uniandes.dse.premiospeliculas.controllers;

import java.util.List;

import org.modelmapper.TypeToken;
import org.modelmapper.ModelMapper;
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

import co.edu.uniandes.dse.premiospeliculas.dto.CinefiloDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.CinefiloDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.CinefiloEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.CinefiloService;

@RestController
@RequestMapping("/cinefilos")
public class CinefiloController {
    @Autowired
    private CinefiloService cinefiloService;
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CinefiloDetailDTO> findAll(){
        List<CinefiloEntity> cinefilos = this.cinefiloService.getCinefilos();
        return this.modelMapper.map(cinefilos, new TypeToken<List<CinefiloDetailDTO>>(){}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CinefiloDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        CinefiloEntity cinefilo = this.cinefiloService.getCinefilo(id);
        return modelMapper.map(cinefilo, CinefiloDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CinefiloDTO create(@RequestBody CinefiloDTO cinefiloDTO) throws IllegalOperationException {
        CinefiloEntity cinefiloEntity = this.cinefiloService.createCinefilo(modelMapper.map(cinefiloDTO, CinefiloEntity.class));
        return modelMapper.map(cinefiloEntity, CinefiloDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CinefiloDTO update(@PathVariable("id") Long id, @RequestBody CinefiloDTO cinefiloDTO) throws EntityNotFoundException, IllegalOperationException {
        CinefiloEntity cinefiloEntity = this.cinefiloService.updateCinefilo(id,modelMapper.map(cinefiloDTO, CinefiloEntity.class));
        return modelMapper.map(cinefiloEntity, CinefiloDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        this.cinefiloService.deleteCinefilo(id);
    }
}
