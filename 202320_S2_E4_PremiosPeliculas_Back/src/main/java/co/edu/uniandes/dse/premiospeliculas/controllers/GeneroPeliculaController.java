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
// import co.edu.uniandes.dse.premiospeliculas.entities.GeneroEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.GeneroPeliculaService;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generos")
public class GeneroPeliculaController {

	@Autowired
	private GeneroPeliculaService generoPeliculaService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/{generoId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO getPelicula(@PathVariable("generoId") Long generoId,
			@PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity peliculaEntity = generoPeliculaService.getPelicula(generoId, peliculaId);
		return modelMapper.map(peliculaEntity, PeliculaDetailDTO.class);
	}

	@GetMapping(value = "/{generoId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> getPeliculas(@PathVariable("generoId") Long generoId)
			throws EntityNotFoundException {
		List<PeliculaEntity> peliculaEntity = generoPeliculaService.getPeliculas(generoId);
		return modelMapper.map(peliculaEntity, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());
	}

	@PostMapping(value = "/{generoId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO addPelicula(@PathVariable("generoId") Long generoId,
			@PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		PeliculaEntity peliculaEntity = generoPeliculaService.addPelicula(generoId, peliculaId);
		return modelMapper.map(peliculaEntity, PeliculaDetailDTO.class);
	}

	@PutMapping(value = "/{generoId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> replacePeliculas(@PathVariable("generoId") Long generoId,
			@RequestBody List<PeliculaDTO> peliculas)
			throws EntityNotFoundException {
		List<PeliculaEntity> entities = modelMapper.map(peliculas, new TypeToken<List<PeliculaEntity>>() {
		}.getType());
		List<PeliculaEntity> peliculasList = generoPeliculaService.addPeliculas(generoId, entities);
		return modelMapper.map(peliculasList, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());

	}

	@DeleteMapping(value = "/{generoId}/peliculas/{generoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePelicula(@PathVariable("generoId") Long generoId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		generoPeliculaService.removePelicula(generoId, peliculaId);
	}

}
