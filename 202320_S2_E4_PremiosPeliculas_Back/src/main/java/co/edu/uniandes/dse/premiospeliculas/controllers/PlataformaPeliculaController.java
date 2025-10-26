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
// import co.edu.uniandes.dse.premiospeliculas.entities.PlataformaEntity;
import co.edu.uniandes.dse.premiospeliculas.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.premiospeliculas.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.premiospeliculas.services.PlataformaPeliculaService;

@RestController
@RequestMapping("/plataformas")
public class PlataformaPeliculaController {

	@Autowired
	private PlataformaPeliculaService plataformaPeliculaService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/{plataformaId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO getPelicula(@PathVariable("plataformaId") Long plataformaId,
			@PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException, IllegalOperationException {
		PeliculaEntity peliculaEntity = plataformaPeliculaService.getPelicula(plataformaId, peliculaId);
		return modelMapper.map(peliculaEntity, PeliculaDetailDTO.class);
	}

	@GetMapping(value = "/{plataformaId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> getPeliculas(@PathVariable("plataformaId") Long plataformaId)
			throws EntityNotFoundException {
		List<PeliculaEntity> peliculaEntity = plataformaPeliculaService.getPeliculas(plataformaId);
		return modelMapper.map(peliculaEntity, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());
	}

	@PostMapping(value = "/{plataformaId}/peliculas/{peliculaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PeliculaDetailDTO addPelicula(@PathVariable("plataformaId") Long plataformaId,
			@PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		PeliculaEntity peliculaEntity = plataformaPeliculaService.addPelicula(plataformaId, peliculaId);
		return modelMapper.map(peliculaEntity, PeliculaDetailDTO.class);
	}

	@PutMapping(value = "/{plataformaId}/peliculas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PeliculaDetailDTO> replacePeliculas(@PathVariable("plataformaId") Long plataformaId,
			@RequestBody List<PeliculaDTO> peliculas)
			throws EntityNotFoundException {
		List<PeliculaEntity> entities = modelMapper.map(peliculas, new TypeToken<List<PeliculaEntity>>() {
		}.getType());
		List<PeliculaEntity> peliculasList = plataformaPeliculaService.addPeliculas(plataformaId, entities);
		return modelMapper.map(peliculasList, new TypeToken<List<PeliculaDetailDTO>>() {
		}.getType());

	}

	@DeleteMapping(value = "/{plataformaId}/peliculas/{plataformaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePelicula(@PathVariable("plataformaId") Long plataformaId,
			@PathVariable("peliculaId") Long peliculaId)
			throws EntityNotFoundException {
		plataformaPeliculaService.removePelicula(plataformaId, peliculaId);
	}
}
