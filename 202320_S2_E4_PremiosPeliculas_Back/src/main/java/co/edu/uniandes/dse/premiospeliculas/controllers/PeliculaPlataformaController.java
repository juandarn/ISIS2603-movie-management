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
import co.edu.uniandes.dse.premiospeliculas.dto.PlataformaDetailDTO;
import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PeliculaPlataformaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaPlataformaController {
    @Autowired
	private PeliculaPlataformaService peliculaPlataformaService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{peliculaId}/plataformas/{plataformaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlataformaDetailDTO addPlataforma(@PathVariable("plataformaId") Long plataformaId, @PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException { 
		PlataformaEntity plataformaEntity = this.peliculaPlataformaService.addPlataforma(peliculaId, plataformaId);
		return modelMapper.map(plataformaEntity, PlataformaDetailDTO.class);
	}


	@GetMapping(value = "/{peliculaId}/plataformas/{plataformaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlataformaDetailDTO getPlataforma(@PathVariable("plataformaId") Long plataformaId, @PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException, IllegalOperationException {
		PlataformaEntity plataformaEntity = this.peliculaPlataformaService.getPlataforma(peliculaId, plataformaId);
		return modelMapper.map(plataformaEntity, PlataformaDetailDTO.class);
	}


	@PutMapping(value = "/{peliculaId}/plataformas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlataformaDetailDTO> addPlataformas(@PathVariable("peliculaId") Long peliculaId, @RequestBody List<PremiacionDTO> plataformas) throws EntityNotFoundException {
		List<PlataformaEntity> entities = modelMapper.map(plataformas, new TypeToken<List<PlataformaEntity>>() {}.getType());
		List<PlataformaEntity> plataformasList = this.peliculaPlataformaService.replacePlataformas(peliculaId, entities);
		return modelMapper.map(plataformasList, new TypeToken<List<PlataformaDetailDTO>>() {
		}.getType());
	}


	@GetMapping(value = "/{peliculaId}/plataformas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlataformaDetailDTO> getPlataformas(@PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException {
		List<PlataformaEntity> plataformaEntity = this.peliculaPlataformaService.getPlataformas(peliculaId);
		return modelMapper.map(plataformaEntity, new TypeToken<List<PlataformaDetailDTO>>() {
		}.getType());
	}


	@DeleteMapping(value = "/{peliculaId}/plataformas/{plataformaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePremiacion(@PathVariable("plataformaId") Long plataformaId, @PathVariable("peliculaId") Long peliculaId) throws EntityNotFoundException {
		this.peliculaPlataformaService.removePlataforma(peliculaId, plataformaId);
	}
    
}