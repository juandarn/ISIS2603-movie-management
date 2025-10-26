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
import co.edu.uniandes.dse.premiospeliculas.dto.PersonaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PersonaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PeliculaPersonaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaPersonaController {
    @Autowired
	private PeliculaPersonaService peliculaPersonaService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{peliculaId}/personas/{personaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PersonaDetailDTO addPersona(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException { 
		PersonaEntity personaEntity = this.peliculaPersonaService.addPersona(peliculaId, personaId);
		return modelMapper.map(personaEntity, PersonaDetailDTO.class);
	}


	@GetMapping(value = "/{peliculaId}/personas/{personaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PersonaDetailDTO getPersona(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		PersonaEntity personaEntity = this.peliculaPersonaService.getPersona(peliculaId, personaId);
		return modelMapper.map(personaEntity, PersonaDetailDTO.class);
	}


	@PutMapping(value = "/{peliculaId}/personas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PersonaDetailDTO> addPersonas(@PathVariable("peliculaId") Long peliculaId, @RequestBody List<PremiacionDTO> personas) throws EntityNotFoundException {
		List<PersonaEntity> entities = modelMapper.map(personas, new TypeToken<List<PersonaEntity>>() {}.getType());
		List<PersonaEntity> personasList = this.peliculaPersonaService.replacePersonas(peliculaId, entities);
		return modelMapper.map(personasList, new TypeToken<List<PersonaDetailDTO>>() {
		}.getType());
	}


	@GetMapping(value = "/{peliculaId}/personas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PersonaDetailDTO> getPersonas(@PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException {
		List<PersonaEntity> personaEntity = this.peliculaPersonaService.getPersonas(peliculaId);
		return modelMapper.map(personaEntity, new TypeToken<List<PersonaDetailDTO>>() {
		}.getType());
	}


	@DeleteMapping(value = "/{peliculaId}/personas/{personaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePremiacion(@PathVariable("personaId") Long personaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		this.peliculaPersonaService.removePersona(peliculaId, personaId);
	}
    
}
