package co.edu.uniandes.dse.premiospeliculas.controllers;

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
import co.edu.uniandes.dse.premiospeliculas.services.PersonaPeliculaService;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaPeliculaController {
    @Autowired
    private PersonaPeliculaService personaPeliculaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{personaId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO getPelicula(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity peliculaEntity = personaPeliculaService.getPelicula(personaId, peliculaId);
		return modelMapper.map(peliculaEntity, PeliculaDetailDTO.class);
	}

    @GetMapping(value = "/{personaId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> getPeliculas(@PathVariable("personaId") Long personaId) throws EntityNotFoundException {
		List<PeliculaEntity> peliculaEntity = personaPeliculaService.getPeliculas(personaId);
		return modelMapper.map(peliculaEntity, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());
	}

    @PostMapping(value = "/{personaId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO addPelicula(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		PeliculaEntity bookEntity = personaPeliculaService.addPelicula(personaId, peliculaId);
		return modelMapper.map(bookEntity, PeliculaDetailDTO.class);
	}

    @PutMapping(value = "/{personaId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> replacePeliculas(@PathVariable("personaId") Long personaId, @RequestBody List<PeliculaDTO> peliculas)
			throws EntityNotFoundException {
		List<PeliculaEntity> entities = modelMapper.map(peliculas, new TypeToken<List<PeliculaEntity>>() {
		}.getType());
		List<PeliculaEntity> peliculasList = personaPeliculaService.addPeliculas(personaId, entities);
		return modelMapper.map(peliculasList, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());

	}

    @DeleteMapping(value = "/{personaId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeBook(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		personaPeliculaService.removePelicula(personaId, peliculaId);
	}

    

}
