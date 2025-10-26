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

import co.edu.uniandes.dse.premiospeliculas.dto.GeneroDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.GeneroDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.GeneroService;

@RestController
@RequestMapping("/generos")
public class GeneroController {
    @Autowired
	private GeneroService generoService;

	@Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus( code= HttpStatus.OK)
    public List<GeneroDetailDTO> findAll(){
        List<GeneroEntity> plataformas= generoService.getGeneros();
        return modelMapper.map(plataformas, new TypeToken<List<GeneroDetailDTO>>() {
		}.getType());
	}

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GeneroDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
    GeneroEntity generoEntity = generoService.GetGenero(id);
    return modelMapper.map(generoEntity, GeneroDetailDTO.class);
    }   

    @PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GeneroDTO create(@RequestBody GeneroDTO generoDTO) throws IllegalOperationException, EntityNotFoundException {
		GeneroEntity generoEntity = generoService.CreateGenero(modelMapper.map(generoDTO, GeneroEntity.class));
		return modelMapper.map(generoEntity, GeneroDTO.class);
	}

    @PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public GeneroDTO update(@PathVariable("id") Long id, @RequestBody GeneroDTO generoDTO)
			throws EntityNotFoundException, IllegalOperationException {
		GeneroEntity generoEntity = generoService.UpdateGenero(id, modelMapper.map(generoDTO, GeneroEntity.class));
		return modelMapper.map(generoEntity, GeneroDTO.class);
	}

    @DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
		generoService.DeleteGenero(id);
	}
}