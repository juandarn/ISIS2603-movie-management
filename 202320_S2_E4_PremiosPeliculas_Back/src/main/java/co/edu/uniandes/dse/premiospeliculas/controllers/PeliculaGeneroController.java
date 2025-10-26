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

import co.edu.uniandes.dse.premiospeliculas.dto.PremiacionDTO;
import co.edu.uniandes.dse.premiospeliculas.dto.GeneroDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PeliculaGeneroService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaGeneroController {
    @Autowired
	private PeliculaGeneroService peliculaGeneroService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{peliculaId}/generos/{generoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GeneroDetailDTO addGenero(@PathVariable("generoId") Long generoId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException { 
		GeneroEntity generoEntity = this.peliculaGeneroService.addGenero(peliculaId, generoId);
		return modelMapper.map(generoEntity, GeneroDetailDTO.class);
	}


	@GetMapping(value = "/{peliculaId}/generos/{generoId}")
	@ResponseStatus(code = HttpStatus.OK)
	public GeneroDetailDTO getGenero(@PathVariable("generoId") Long generoId, @PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		GeneroEntity generoEntity = this.peliculaGeneroService.getGenero(peliculaId, generoId);
		return modelMapper.map(generoEntity, GeneroDetailDTO.class);
	}


	@PutMapping(value = "/{peliculaId}/generos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<GeneroDetailDTO> addGeneros(@PathVariable("peliculaId") Long peliculaId, @RequestBody List<PremiacionDTO> generos) throws EntityNotFoundException {
		List<GeneroEntity> entities = modelMapper.map(generos, new TypeToken<List<GeneroEntity>>() {}.getType());
		List<GeneroEntity> generosList = this.peliculaGeneroService.replaceGeneros(peliculaId, entities);
		return modelMapper.map(generosList, new TypeToken<List<GeneroDetailDTO>>() {
		}.getType());
	}


	@GetMapping(value = "/{peliculaId}/generos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<GeneroDetailDTO> getGeneros(@PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException {
		List<GeneroEntity> generoEntity = this.peliculaGeneroService.getGeneros(peliculaId);
		return modelMapper.map(generoEntity, new TypeToken<List<GeneroDetailDTO>>() {
		}.getType());
	}


	@DeleteMapping(value = "/{peliculaId}/generos/{generoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeGenero(@PathVariable("generoId") Long generoId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		this.peliculaGeneroService.removeGenero(peliculaId, generoId);
	}
    
}